package tech.raynaldy.watherapp.di.component;

import dagger.Component;
import tech.raynaldy.watherapp.di.PerActivity;
import tech.raynaldy.watherapp.di.module.ActivityModule;
import tech.raynaldy.watherapp.ui.main.MainActivity;
import tech.raynaldy.watherapp.ui.map.MapsActivity;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
    void inject(MapsActivity activity);
}
