package ru.mirea.vikulov.osmmaps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.vikulov.osmmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MapView mapView = null;
    private ActivityMainBinding binding;
    boolean isWork;
    private static final int REQUEST_CODE_PERMISSION = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        int posPermissionStatus1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int posPermissionStatus2 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (posPermissionStatus1 == PackageManager.PERMISSION_GRANTED
                && posPermissionStatus2 == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            // Выполняется запрос к пользователь на получение необходимых разрешений
            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            android.Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_PERMISSION);
        }
        mapView = binding.mapView;
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0);
        GeoPoint startPoint = new GeoPoint(55.794229, 37.700772);
        mapController.setCenter(startPoint);

        MyLocationNewOverlay locationNewOverlay = new MyLocationNewOverlay(new
                GpsMyLocationProvider(getApplicationContext()),mapView);
        locationNewOverlay.enableMyLocation();
        mapView.getOverlays().add(locationNewOverlay);

        CompassOverlay compassOverlay = new CompassOverlay(getApplicationContext(), new
                InternalCompassOrientationProvider(getApplicationContext()), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        final Context context = this.getApplicationContext();
        final DisplayMetrics dm = context.getResources().getDisplayMetrics();
        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);


        Marker marker = new Marker(mapView);
        marker.setPosition(new GeoPoint(55.794229, 37.700772));
        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                Toast.makeText(getApplicationContext(),"Кузница величайших умов \n" + "Время работы 8:30 - 21:00",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker);

        marker.setIcon(ResourcesCompat.getDrawable(getResources(), org.osmdroid.library.R.drawable.osm_ic_follow_me_on, null));

        marker.setTitle("Title");

        Marker marker1 = new Marker(mapView);
        marker1.setPosition(new GeoPoint(55.664182, 37.481118));
        marker1.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker1, MapView mapView) {
                Toast.makeText(getApplicationContext(),"Заведение высокой кухни \n" + "Время работы 7:00 - 00:00",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker1);

        marker1.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.mcd, null));

        Marker marker2 = new Marker(mapView);
        marker2.setPosition(new GeoPoint(55.720418, 37.378791));
        marker2.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker2, MapView mapView) {
                Toast.makeText(getApplicationContext(),"Богодельня для отдыха души и тела \n" + "Время работы 10:00 - 22:00",
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        mapView.getOverlays().add(marker2);

        marker2.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.shop, null));
    }


    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(getApplicationContext(),

                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        if (mapView != null) {
            mapView.onPause();
        }
    }
}