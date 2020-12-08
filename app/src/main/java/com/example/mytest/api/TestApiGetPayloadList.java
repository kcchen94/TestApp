package com.example.mytest.api;

import com.example.mytest.GetPayloadListService;
import com.example.mytest.object.PayloadItem;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestApiGetPayloadList {

    private static final String PAYLOAD_URL = "https://2826536e-d00d-4575-b35e-5562354bf840.mock.pstmn.io/";

    private static TestApiGetPayloadList instance;
    public Observable<ArrayList<PayloadItem>> itemObject;

    public TestApiGetPayloadList() {
    }

    public Observable<ArrayList<PayloadItem>> execute(){
        Observable<Retrofit> retrofit;
        Observable<String> stringObservable = Observable.just("");

        retrofit = stringObservable.map(new Function<String, Retrofit>() {
            @Override
            public Retrofit apply(String s) throws Exception {
                return new Retrofit.Builder()
                        .baseUrl(PAYLOAD_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        });

        Observable<List<PayloadItem>> initiatedService = initRequestService(retrofit);
        itemObject = initiatedService.map(mapToGetData);
        return itemObject;
    }

    private Observable<List<PayloadItem>> initRequestService(Observable<Retrofit> retrofitObservable) {
        return retrofitObservable
                .map(new Function<Retrofit, GetPayloadListService>() {
                    @Override
                    public GetPayloadListService apply(Retrofit retrofit) throws Exception {
                        return retrofit.create(GetPayloadListService.class);
                    }
                })
                .flatMap(new Function<GetPayloadListService, Observable<List<PayloadItem>>>() {
                    @Override
                    public Observable<List<PayloadItem>> apply(GetPayloadListService getPayloadListService) throws Exception {
                        Observable<List<PayloadItem>> responseObservable = getPayloadListService.getPayloadData();
                        return responseObservable;
                    }
                });
    }

    public static TestApiGetPayloadList getInstance() {
        if (instance == null) {
            instance = new TestApiGetPayloadList();
        }
        return instance;
    }

    Function<List<PayloadItem>, ArrayList<PayloadItem>> mapToGetData =  new Function<List<PayloadItem>, ArrayList<PayloadItem>>() {
        @Override
        public ArrayList<PayloadItem> apply(List<PayloadItem> stringResponse) throws Exception {
            try {
                ArrayList<PayloadItem> itemList = new ArrayList<>();

                for(int i = 0 ; i < stringResponse.size() ; i++){
                    itemList.add(PayloadItem.getInstance(stringResponse.get(i)));
                }
                return itemList;
            } catch (Exception ignored) {
                ignored.printStackTrace();
                throw new JSONException("Error Mapping Data:: "+ignored.getMessage());
            }
        }
    };
}