package tech.raynaldy.watherapp.ui.map;

import android.location.Location;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tech.raynaldy.watherapp.data.DataManager;
import tech.raynaldy.watherapp.data.network.model.request.WeatherRequestParam;
import tech.raynaldy.watherapp.ui.base.BasePresenter;
import tech.raynaldy.watherapp.utils.rx.SchedulerProvider;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/5/21.
 */

public class MapsPresenter<V extends MapsMvpView> extends BasePresenter<V> implements MapsMvpPresenter<V> {

    @Inject
    public MapsPresenter(DataManager mDataManager, SchedulerProvider mSchedulerProvider, CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void getCurrentWeather(LatLng location) {
        WeatherRequestParam query = new WeatherRequestParam(location.latitude, location.longitude);
        getCompositeDisposable().add(getDataManager().getCurrentWeather(getDataManager().getAppHost(), query)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(weatherResponse -> {
                    getMvpView().onWeatherRetrieved(weatherResponse);
                }, err -> {
                    getMvpView().onWeatherRetrieveError();
                })
        );
    }

    @Override
    public boolean isCelcius() {
        return getDataManager().isCelcius();
    }
}
