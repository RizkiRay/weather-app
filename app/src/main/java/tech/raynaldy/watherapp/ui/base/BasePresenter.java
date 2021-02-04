package tech.raynaldy.watherapp.ui.base;

import com.androidnetworking.error.ANError;

import io.reactivex.disposables.CompositeDisposable;
import tech.raynaldy.watherapp.data.DataManager;
import tech.raynaldy.watherapp.utils.rx.SchedulerProvider;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V>{

    private final DataManager mDataManager;
    private final SchedulerProvider mSchedulerProvider;
    private final CompositeDisposable mCompositeDisposable;

    private V mMvpView;

    public BasePresenter(DataManager mDataManager, SchedulerProvider mSchedulerProvider, CompositeDisposable mCompositeDisposable) {
        this.mDataManager = mDataManager;
        this.mSchedulerProvider = mSchedulerProvider;
        this.mCompositeDisposable = mCompositeDisposable;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public SchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.onAttach(MvpView) before requesting data to the Presenter");
        }
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {

    }

    @Override
    public void handleApiError(ANError error) {

    }
}
