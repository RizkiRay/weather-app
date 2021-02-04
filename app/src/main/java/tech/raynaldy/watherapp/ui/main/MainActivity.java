package tech.raynaldy.watherapp.ui.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import tech.raynaldy.watherapp.R;
import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;
import tech.raynaldy.watherapp.ui.base.BaseActivity;
import tech.raynaldy.watherapp.ui.map.MapsActivity;
import tech.raynaldy.watherapp.utils.DateUtils;
import tech.raynaldy.watherapp.utils.WeatherUtil;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/4/21.
 */

public class MainActivity extends BaseActivity implements MainMvpView {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final String TAG = "MAIN_ACTIVITY";

    private FusedLocationProviderClient fusedLocationClient;

    @Inject
    MainMvpPresenter<MainMvpView> mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.text_date)
    TextView mTextDate;

    @BindView(R.id.rg_option)
    RadioGroup mRgUnit;
    @BindView(R.id.btn_c)
    RadioButton mBtnC;
    @BindView(R.id.btn_f)
    RadioButton mBtnF;


    @BindView(R.id.text_temperature)
    TextView mTextTemperature;
    @BindView(R.id.text_unit)
    TextView mTextUnit;
    @BindView(R.id.img_weather)
    ImageView mImageWeather;
    @BindView(R.id.text_weather_status)
    TextView mTextWeatherStatus;
    @BindView(R.id.text_city)
    TextView mTextCity;
    @BindView(R.id.text_additional_info)
    TextView mTextAdditionalInfo;

    @OnClick(R.id.btn_request_loc)
    void onRequestLocClicked(){
        getLocation();
    }

    @OnClick(R.id.fab_map)
    void onFabClicked(){
        Intent i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    private Double tempInKelvin = 0.0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getActivityComponent().inject(this);
        setUnbinder(ButterKnife.bind(this));
        mPresenter.onAttach(this);
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.checkUnit();
        getLocation();
    }

    @Override
    protected void setUp() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

        //retrieve location & weather

        mTextDate.setText(DateUtils.getStringDate(new Date()));

        mRgUnit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == mBtnC.getId()) mPresenter.setUnit(true);
                else mPresenter.setUnit(false);

                mPresenter.checkUnit();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarseAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && coarseAccepted){
                        getLocation();
                    }
                    else {
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
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
    private void getLocation(){
        if (isLocationEnabled()) {
            if (checkPermission()) {
                fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        Log.i(TAG, "onRequestLocClicked: " + location.toString());
                        mPresenter.getCurrentWeather(location);
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

    private boolean isLocationEnabled () {
        LocationManager lm = (LocationManager)
                getSystemService(Context. LOCATION_SERVICE ) ;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        try {
            gps_enabled = lm.isProviderEnabled(LocationManager. GPS_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager. NETWORK_PROVIDER ) ;
        } catch (Exception e) {
            e.printStackTrace() ;
        }
        return gps_enabled || network_enabled;
    }

    private void showEnableGPS(){
        new AlertDialog.Builder(MainActivity. this )
                .setMessage( "GPS Enable" )
                .setPositiveButton( "Settings" , new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick (DialogInterface paramDialogInterface , int paramInt) {
                                startActivity( new Intent(Settings. ACTION_LOCATION_SOURCE_SETTINGS )) ;
                            }
                        })
                .setNegativeButton( "Cancel" , null )
                .show() ;
    }

    @Override
    public void onWeatherRetrieved(WeatherResponse response) {
        String city = response.getName() + ", " + WeatherUtil.getCountryFromID(response.getSys().getCountry());
        String additionalInfo = getString(R.string.string_additional_info, response.getWind().getSpeed() + " m/s", response.getMain().getHumidity() + " %");
        tempInKelvin = response.getMain().getTemp();

        mPresenter.checkUnit();
        mTextAdditionalInfo.setText(additionalInfo);
        mTextWeatherStatus.setText(response.getWeather().get(0).getMain());
        mTextCity.setText(city);
        Glide.with(this).load(WeatherUtil.getWeatherImage(response.getWeather().get(0).getId())).apply(new RequestOptions().fitCenter())
                .into(mImageWeather);
    }

    @Override
    public void onUnitChecked(boolean isCelcius) {
        int temp;
        String unit;
        if (isCelcius) {
            mRgUnit.check(mBtnC.getId());
            temp = WeatherUtil.KelvinToCelcius(tempInKelvin);
            unit = "℃";
        }
        else {
            mRgUnit.check(mBtnF.getId());
            temp = WeatherUtil.KelvinToFahrenheit(tempInKelvin);
            unit = "℉";
        }
        mTextTemperature.setText(String.valueOf(temp));
        mTextUnit.setText(unit);
    }
}
