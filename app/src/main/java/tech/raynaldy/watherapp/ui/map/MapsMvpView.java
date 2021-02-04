package tech.raynaldy.watherapp.ui.map;

import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;
import tech.raynaldy.watherapp.ui.base.MvpView;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/5/21.
 */

public interface MapsMvpView extends MvpView {
    void onWeatherRetrieved(WeatherResponse response);
    void onWeatherRetrieveError();
}
