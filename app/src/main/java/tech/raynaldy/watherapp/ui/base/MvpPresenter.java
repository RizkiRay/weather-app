package tech.raynaldy.watherapp.ui.base;

import com.androidnetworking.error.ANError;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public interface MvpPresenter<V extends MvpView> {
    void onAttach(V mvpView);

    void onDetach();

    void handleApiError(ANError error);
}
