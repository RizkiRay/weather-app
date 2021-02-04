package tech.raynaldy.watherapp.ui.map;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

import tech.raynaldy.watherapp.ui.base.MvpPresenter;
import tech.raynaldy.watherapp.ui.base.MvpView;
import tech.raynaldy.watherapp.ui.main.MainMvpPresenter;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/5/21.
 */

public interface MapsMvpPresenter<V extends MapsMvpView> extends MvpPresenter<V> {
    void getCurrentWeather(LatLng location);
    boolean isCelcius();
}
