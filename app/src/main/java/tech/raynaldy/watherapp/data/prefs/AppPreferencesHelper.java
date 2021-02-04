package tech.raynaldy.watherapp.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import tech.raynaldy.watherapp.di.ApplicationContext;
import tech.raynaldy.watherapp.di.PreferenceInfo;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */
@Singleton
public class AppPreferencesHelper implements PreferencesHelper{
    private final SharedPreferences mPrefs;

    private static final String PREF_UNIT = "PREF_UNIT";

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context, @PreferenceInfo String prefName) {
        mPrefs = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public void setCelcius(boolean isCelcius) {
        mPrefs.edit().putBoolean(PREF_UNIT, isCelcius).apply();
    }

    @Override
    public boolean isCelcius() {
        return mPrefs.getBoolean(PREF_UNIT, true);
    }
}
