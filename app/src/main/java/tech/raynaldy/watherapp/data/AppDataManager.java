package tech.raynaldy.watherapp.data;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import tech.raynaldy.watherapp.BuildConfig;
import tech.raynaldy.watherapp.data.network.ApiHelper;
import tech.raynaldy.watherapp.data.network.model.request.WeatherRequestParam;
import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;
import tech.raynaldy.watherapp.data.prefs.PreferencesHelper;
import tech.raynaldy.watherapp.di.ApplicationContext;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */
@Singleton
public class AppDataManager implements DataManager {
    private final Context mContext;
    private final PreferencesHelper mPrefHelper;
    private final ApiHelper mApiHelper;

    @Inject
    AppDataManager(@ApplicationContext Context mContext, PreferencesHelper mPrefHelper, ApiHelper mApiHelper){
        this.mContext = mContext;
        this.mPrefHelper = mPrefHelper;
        this.mApiHelper = mApiHelper;
    }

    @Override
    public String getAppHost() {
        return BuildConfig.BASE_URL;
    }

    @Override
    public Observable<WeatherResponse> getCurrentWeather(String host, WeatherRequestParam param) {
        return mApiHelper.getCurrentWeather(host, param);
    }

    @Override
    public void setCelcius(boolean isCelcius) {
        mPrefHelper.setCelcius(isCelcius);
    }

    @Override
    public boolean isCelcius() {
        return mPrefHelper.isCelcius();
    }
}
