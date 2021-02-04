package tech.raynaldy.watherapp.ui.base;

import android.widget.ImageView;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public interface MvpView {
    void showLoading();

    void hideLoading();

    void showError(String error);

    void showError(int resError);

    void loadImage(ImageView imageView, int imageRes);

    void hideKeyboard();

    boolean isNetworkConnected();
}
