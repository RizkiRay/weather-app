package tech.raynaldy.watherapp.di.module;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;
import tech.raynaldy.watherapp.di.ActivityContext;
import tech.raynaldy.watherapp.ui.main.MainMvpPresenter;
import tech.raynaldy.watherapp.ui.main.MainMvpView;
import tech.raynaldy.watherapp.ui.main.MainPresenter;
import tech.raynaldy.watherapp.ui.map.MapsMvpPresenter;
import tech.raynaldy.watherapp.ui.map.MapsMvpView;
import tech.raynaldy.watherapp.ui.map.MapsPresenter;
import tech.raynaldy.watherapp.utils.rx.AppSchedulerProvider;
import tech.raynaldy.watherapp.utils.rx.SchedulerProvider;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

@Module
public class ActivityModule {
    private AppCompatActivity mActivity;
    public ActivityModule(AppCompatActivity activity){
        mActivity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext(){
        return mActivity;
    }

    @Provides
    AppCompatActivity provideActivity(){
        return mActivity;
    }
    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> presenter) {
        return presenter;
    }

    @Provides
    MapsMvpPresenter<MapsMvpView> provideMapsPresenter(MapsPresenter<MapsMvpView> presenter){
        return presenter;
    }
}
