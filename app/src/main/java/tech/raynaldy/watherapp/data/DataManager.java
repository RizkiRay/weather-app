package tech.raynaldy.watherapp.data;

import tech.raynaldy.watherapp.data.network.ApiHelper;
import tech.raynaldy.watherapp.data.prefs.PreferencesHelper;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public interface DataManager extends ApiHelper, PreferencesHelper {
    public String getAppHost();
}
