package com.example.mytest.base;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mytest.R;
import com.example.mytest.object.ImageLoaderOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements BaseView {
    public T mPresenter;

    public ProgressBar loadingView;

    public T createPresenter() {
        return null;
    }

    private void setupPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    public T getmPresenter() {
        return mPresenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageLoader.getInstance().init(new ImageLoaderConfiguration
                .Builder(getBaseContext())
                .defaultDisplayImageOptions(ImageLoaderOptions.defaultInit(this))
                .build());
        setupPresenter();
        customActionBar();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    public void customActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        final View vi = getLayoutInflater().inflate(R.layout.custom_action_bar, null);
        ((TextView) vi.findViewById(R.id.title)).setText(getActivityTitle());
        if (actionBar == null) return;
        actionBar.setElevation(0);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(showBackButton());

        actionBar.setCustomView(vi, new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.MATCH_PARENT, (showBackButton() ? Gravity.CENTER : Gravity.START)));
    }

    public String getActivityTitle() {
        return "";
    }

    public boolean showBackButton(){
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void setLoadingView(ProgressBar loadingView) {
        this.loadingView = loadingView;
    }

    public ProgressBar getLoadingView() {
        return this.loadingView;
    }

    protected final void setLoading(boolean isLoading, final boolean blockUI) {
        if (getLoadingView() == null) return;
        getLoadingView().setVisibility(isLoading ? View.VISIBLE : View.GONE);
        blockWindow(isLoading && blockUI);
    }

    public boolean isLoading() {
        return (getLoadingView() != null) && (getLoadingView().getVisibility() == View.VISIBLE);
    }

    protected final void blockWindow(boolean blockWindow) {
        if (isInvalidContext() || getActivity() == null) return;
        final Window window = getActivity().getWindow();
        if (window == null) return;
        if (blockWindow) {
            getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    protected BaseActivity getActivity() {
        return this;
    }

    protected boolean isInvalidContext() {
        try {
            return (getActivity() == null || getActivity().isFinishing());
        } catch (Exception e) {
            return false;
        }
    }
}
