package tech.raynaldy.watherapp.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import butterknife.Unbinder;
import tech.raynaldy.watherapp.di.component.ActivityComponent;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/4/21.
 */

public abstract class BaseFragment extends Fragment implements MvpView {
    private BaseActivity mActivity;
    private Unbinder mUnbinder;

    public interface Callback {
        void onFragmentAttached();

        void onFragmentDetached(String tag);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUp(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseActivity) {
            BaseActivity activity = (BaseActivity) context;
            mActivity = activity;
            activity.onFragmentAttached();
        }
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        hideLoading();
    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void loadImage(ImageView imageView, int imageRes) {
        Glide.with(this).load(imageRes).apply(new RequestOptions().centerCrop()).into(imageView);
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void showError(int resError) {
        if (mActivity != null) {
            mActivity.showError(resError);
        }
    }

    @Override
    public void showError(String error) {
        if (mActivity != null) {
            mActivity.showError(error);
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    protected abstract void setUp(View view);

    public ActivityComponent getActivityComponent() {
        if (mActivity == null) return null;
        return mActivity.getActivityComponent();
    }

    public void setUnbinder(Unbinder mUnbinder) {
        this.mUnbinder = mUnbinder;
    }

    public BaseActivity getBaseActivity() {
        return mActivity;
    }
}
