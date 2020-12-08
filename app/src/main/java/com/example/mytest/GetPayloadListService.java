package com.example.mytest;

import com.example.mytest.object.PayloadItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface GetPayloadListService {
    @GET("get")
    Observable<List<PayloadItem>> getPayloadData();
}
