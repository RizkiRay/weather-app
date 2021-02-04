package tech.raynaldy.watherapp.data.network;

import io.reactivex.Observable;
import tech.raynaldy.watherapp.data.network.model.request.WeatherRequestParam;
import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public interface ApiHelper {
    Observable<WeatherResponse> getCurrentWeather(String host, WeatherRequestParam param);
}
