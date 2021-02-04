package tech.raynaldy.watherapp.ui.main;

import android.location.Location;

import tech.raynaldy.watherapp.data.network.model.request.WeatherRequestParam;
import tech.raynaldy.watherapp.ui.base.MvpPresenter;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/4/21.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    void getCurrentWeather(Location location);
    void checkUnit();
    void setUnit(boolean isCelcius);
}
