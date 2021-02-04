package tech.raynaldy.watherapp.ui.main;

import android.location.Location;
import android.util.Log;

import com.google.gson.Gson;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tech.raynaldy.watherapp.data.DataManager;
import tech.raynaldy.watherapp.data.network.model.request.WeatherRequestParam;
import tech.raynaldy.watherapp.ui.base.BasePresenter;
import tech.raynaldy.watherapp.utils.rx.SchedulerProvider;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/4/21.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {
    private static final String TAG = "MAIN_PRESENTER";

    @Inject
    public MainPresenter(DataManager mDataManager, SchedulerProvider mSchedulerProvider, CompositeDisposable mCompositeDisposable) {
        super(mDataManager, mSchedulerProvider, mCompositeDisposable);
    }

    @Override
    public void onAttach(V mvpView) {
        super.onAttach(mvpView);
    }

    @Override
    public void getCurrentWeather(Location location) {
        WeatherRequestParam query = new WeatherRequestParam(location.getLatitude(), location.getLongitude());
        getCompositeDisposable().add(getDataManager().getCurrentWeather(getDataManager().getAppHost(), query)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(weatherResponse -> {
                    getMvpView().onWeatherRetrieved(weatherResponse);
                })
        );
    }

    @Override
    public void checkUnit() {
        getMvpView().onUnitChecked(getDataManager().isCelcius());
    }

    @Override
    public void setUnit(boolean isCelcius) {
        getDataManager().setCelcius(isCelcius);
    }
}
