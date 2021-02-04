package tech.raynaldy.watherapp.data.network;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import tech.raynaldy.watherapp.BuildConfig;
import tech.raynaldy.watherapp.data.network.model.request.WeatherRequestParam;
import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */
@Singleton
public class AppApiHelper implements ApiHelper{
    @Inject
    AppApiHelper() {
    }

    @Override
    public Observable<WeatherResponse> getCurrentWeather(String host, WeatherRequestParam param) {
        return Rx2AndroidNetworking.post(host + ApiEndpoint.ENDPOINT_CURRENT_WEATHER)
                .addQueryParameter(param)
                .addQueryParameter("appid", BuildConfig.OPEN_WEATHER_KEY)
                .build()
                .getObjectObservable(WeatherResponse.class);
    }
}
