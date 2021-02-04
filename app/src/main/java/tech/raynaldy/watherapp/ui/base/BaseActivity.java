package tech.raynaldy.watherapp.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.snackbar.Snackbar;

import butterknife.Unbinder;
import tech.raynaldy.watherapp.MyApp;
import tech.raynaldy.watherapp.R;
import tech.raynaldy.watherapp.di.component.ActivityComponent;
import tech.raynaldy.watherapp.di.component.DaggerActivityComponent;
import tech.raynaldy.watherapp.di.module.ActivityModule;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public abstract class BaseActivity extends AppCompatActivity implements MvpView, BaseFragment.Callback {
    private ActivityComponent mActivityComponent;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((MyApp) getApplication()).getComponent()).build();
    }

//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

    @Override
    public void showLoading() {
        hideLoading();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadImage(ImageView imageView, int imageRes) {
        Glide.with(this).load(imageRes)
                .apply(new RequestOptions().centerCrop()).into(imageView);
    }

    @Override
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void showError(int resError) {
        showError(getString(resError));
    }

    @Override
    public void showError(String error) {
        showSnackBar(error);
    }

    @Override
    protected void onDestroy() {
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroy();
    }

    public void setUnbinder(Unbinder mUnbinder) {
        this.mUnbinder = mUnbinder;
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

    private void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackView = snackbar.getView();
        TextView textView = snackView
                .findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));
        snackbar.show();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    protected abstract void setUp();
}
