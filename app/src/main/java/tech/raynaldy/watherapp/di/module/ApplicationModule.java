package tech.raynaldy.watherapp.di.module;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.raynaldy.watherapp.R;
import tech.raynaldy.watherapp.data.AppDataManager;
import tech.raynaldy.watherapp.data.DataManager;
import tech.raynaldy.watherapp.data.network.ApiHelper;
import tech.raynaldy.watherapp.data.network.AppApiHelper;
import tech.raynaldy.watherapp.data.prefs.AppPreferencesHelper;
import tech.raynaldy.watherapp.data.prefs.PreferencesHelper;
import tech.raynaldy.watherapp.di.ApplicationContext;
import tech.raynaldy.watherapp.di.DatabaseInfo;
import tech.raynaldy.watherapp.di.PreferenceInfo;
import tech.raynaldy.watherapp.utils.AppConstants;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseInfo() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @PreferenceInfo
    String providePrefName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager manager) {
        return manager;
    }

//    @Provides
//    @Singleton
//    DbHelper provideDbHelper(AppDbHelper dbHelper) {
//        return dbHelper;
//    }

    @Provides
    @Singleton
    PreferencesHelper providePrefHelper(AppPreferencesHelper prefHelper) {
        return prefHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper apiHelper) {
        return apiHelper;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideFontRegularConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/raleway/Raleway-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
}
