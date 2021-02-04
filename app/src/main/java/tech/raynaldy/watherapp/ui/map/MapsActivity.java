package tech.raynaldy.watherapp.ui.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tech.raynaldy.watherapp.R;
import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;
import tech.raynaldy.watherapp.ui.base.BaseActivity;
import tech.raynaldy.watherapp.ui.main.MainActivity;
import tech.raynaldy.watherapp.ui.main.MainMvpPresenter;
import tech.raynaldy.watherapp.ui.main.MainMvpView;
import tech.raynaldy.watherapp.utils.WeatherUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MapsActivity extends BaseActivity implements MapsMvpView, OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String TAG = "MAPS_ACTIVITY";

    private FusedLocationProviderClient fusedLocationClient;
    private GoogleMap mMap;

    @Inject
    MapsMvpPresenter<MapsMvpView> mPresenter;

    @BindView(R.id.bottomSheet)
    ConstraintLayout mBottomSheetLayout;

    //bottom sheet
    @BindView(R.id.text_location)
    TextView mTextLocation;
    @BindView(R.id.text_lat_lng)
    TextView mTextLatLng;
    @BindView(R.id.text_weather)
    TextView mTextWeather;
    @BindView(R.id.text_temperature)
    TextView mTextTemp;
    @BindView(R.id.text_unit)
    TextView mTextUnit;
    @BindView(R.id.text_wind_speed)
    TextView mTextWind;
    @BindView(R.id.text_humidity)
    TextView mTextHumidity;
    @BindView(R.id.img_weather)
    ImageView mImageWeather;

    BottomSheetBehavior sheetBehavior;

    @OnClick(R.id.fab_back)
    void onBackClicked() {
        finish();
    }

    @OnClick(R.id.bottomSheet)
    void onSheetClicked() {
        if (sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED)
            sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        else sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
        setUp();
    }

    @Override
    protected void setUp() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        sheetBehavior = BottomSheetBehavior.from(mBottomSheetLayout);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.setOnMapClickListener(latLng -> {
            mPresenter.getCurrentWeather(latLng);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("clicked location"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        });

        getLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && coarseAccepted) {
                        getLocation();
                    } else {
                        Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }

                    }
                }


                break;
        }
    }

    private void getLocation() {
        if (isLocationEnabled()) {
            if (checkPermission()) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
                        mPresenter.getCurrentWeather(loc);
                        mMap.clear();
                        mMap.addMarker(new MarkerOptions().position(loc).title("My Location"));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16));
                    }
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "getLocation: ", e);
                });
            } else {
                requestPermission();
            }
        } else {
            showEnableGPS();
        }
    }

    private boolean isLocationEnabled() {
        LocationManager lm = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gps_enabled || network_enabled;
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    private void showEnableGPS() {
        new AlertDialog.Builder(MapsActivity.this)
                .setMessage("GPS Enable")
                .setPositiveButton("Settings", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            }
                        })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public void onWeatherRetrieved(WeatherResponse response) {
        String strLoc = response.getName() + ", " + WeatherUtil.getCountryFromID(response.getSys().getCountry());
        String strLatLng = response.getCoord().getLat() + ", " + response.getCoord().getLon();
        String temperature = mPresenter.isCelcius() ?
                WeatherUtil.KelvinToCelcius(response.getMain().getTemp()) + "" :
                WeatherUtil.KelvinToFahrenheit(response.getMain().getTemp()) + "";
        String textUnit = mPresenter.isCelcius() ? "℃" : "℉";
        String wind = getString(R.string.wind_speed, response.getWind().getSpeed() + "");
        String humidity = getString(R.string.humidity, response.getMain().getHumidity() + "");


        Glide.with(this).load(WeatherUtil.getWeatherImage(response.getWeather().get(0).getId())).apply(new RequestOptions().fitCenter())
                .into(mImageWeather);
        mTextWeather.setText(response.getWeather().get(0).getMain());
        mTextLocation.setText(strLoc);
        mTextLatLng.setText(strLatLng);
        mTextTemp.setText(temperature);
        mTextWind.setText(wind);
        mTextUnit.setText(textUnit);
        mTextHumidity.setText(humidity);

    }

    @Override
    public void onWeatherRetrieveError() {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
}