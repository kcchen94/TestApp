package com.example.mytest.MainActivity;

import android.content.Context;

import com.example.mytest.api.TestApiGetPayloadList;
import com.example.mytest.base.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter  {
    @Override
    public void getItemListApi(Context context) {
        TestApiGetPayloadList api = TestApiGetPayloadList.getInstance();
        addDisposable(api.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    itemListRecords -> {
                        getView().getItemListApiResponse(itemListRecords);
                    }, throwable -> {
                        getView().getItemListApiThrowable(throwable);
                    },
                    () -> {}
                )
        );
    }
}
