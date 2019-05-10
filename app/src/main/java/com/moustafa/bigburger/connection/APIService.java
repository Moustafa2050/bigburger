package com.moustafa.bigburger.connection;

import com.moustafa.bigburger.module.product_module;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("mobiletest1.json")
    Call<ArrayList<product_module>> products ();
}
