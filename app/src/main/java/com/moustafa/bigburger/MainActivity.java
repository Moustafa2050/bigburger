package com.moustafa.bigburger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moustafa.bigburger.View.product_view;
import com.moustafa.bigburger.adatper.product_adapter;
import com.moustafa.bigburger.connection.APIService;
import com.moustafa.bigburger.connection.APIUrl;
import com.moustafa.bigburger.module.product_module;
import com.moustafa.bigburger.presenter.Products_Presenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements product_view {

    ImageView products_back, products_cart;
    RecyclerView recyclerViewProducts;
    private ArrayList<product_module> productModuleList;

    Products_Presenter presenter;
    product_adapter productAdapter;
    ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActivity();
        Loading(true);
        getProducts();

    }

    private void initActivity() {
        products_back = findViewById(R.id.products_back);
        products_cart = findViewById(R.id.products_cart);
        loading = findViewById(R.id.loading);
        recyclerViewProducts = findViewById(R.id.recyclerView_products);
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        presenter = new Products_Presenter(this);
        productModuleList = new ArrayList<>();
        productAdapter = new product_adapter(this, productModuleList);

    }


    public void getProducts() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        APIService apis = retrofit.create(APIService.class);
        Call<ArrayList<product_module>> call = apis.products();
        call.enqueue(new Callback<ArrayList<product_module>>() {
            @Override
            public void onResponse(Call<ArrayList<product_module>> call, Response<ArrayList<product_module>> response) {
                presenter.GetProductsFromJSON(response);
            }

            @Override
            public void onFailure(Call<ArrayList<product_module>> call, Throwable t) {
                presenter.HandelErrorConnections(t);
            }
        });
    }

    @Override
    public void updateProducts(ArrayList<product_module> ListProducts) {
        Loading(false);
        productAdapter = new product_adapter(this, ListProducts);
        recyclerViewProducts.setAdapter(productAdapter);
    }

    @Override
    public void updateErrorProducts(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void Loading(boolean state) {
        if (state) {
            recyclerViewProducts.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);
        } else {
            recyclerViewProducts.setVisibility(View.VISIBLE);
            loading.setVisibility(View.GONE);
        }
    }
}
