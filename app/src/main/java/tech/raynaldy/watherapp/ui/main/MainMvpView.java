package tech.raynaldy.watherapp.ui.main;

import tech.raynaldy.watherapp.data.network.model.response.WeatherResponse;
import tech.raynaldy.watherapp.ui.base.MvpView;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/4/21.
 */

public interface MainMvpView extends MvpView {
    void onWeatherRetrieved(WeatherResponse response);
    void onUnitChecked(boolean isCelcius);
}
