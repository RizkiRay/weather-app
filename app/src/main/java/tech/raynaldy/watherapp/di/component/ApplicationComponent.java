package tech.raynaldy.watherapp.di.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import tech.raynaldy.watherapp.MyApp;
import tech.raynaldy.watherapp.data.DataManager;
import tech.raynaldy.watherapp.di.ApplicationContext;
import tech.raynaldy.watherapp.di.module.ApplicationModule;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public
interface ApplicationComponent {
    void inject(MyApp app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
