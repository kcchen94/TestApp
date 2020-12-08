package com.example.mytest.MainActivity;

import android.content.Context;

import com.example.mytest.base.BaseView;
import com.example.mytest.object.PayloadItem;

import java.util.ArrayList;

public class MainActivityContract {
    interface View extends BaseView {
        void getItemListApiResponse(ArrayList<PayloadItem> listRecords);
        void getItemListApiThrowable(Throwable throwable);
    }

    interface Presenter {
        void getItemListApi(Context context);
    }
}
