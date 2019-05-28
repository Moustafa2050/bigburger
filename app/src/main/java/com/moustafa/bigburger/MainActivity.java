package com.moustafa.bigburger;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moustafa.bigburger.View.product_view;
import com.moustafa.bigburger.adatper.product_adapter;
import com.moustafa.bigburger.connection.APIService;
import com.moustafa.bigburger.connection.APIUrl;
import com.moustafa.bigburger.module.productCart_module;
import com.moustafa.bigburger.module.product_module;
import com.moustafa.bigburger.presenter.Products_Presenter;
import com.moustafa.bigburger.secondPartInTest.closestToZeroClass;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements product_view {

    ImageView products_cart;
    RecyclerView recyclerViewProducts;
    public static ArrayList<productCart_module> productModuleList;
    ConstraintLayout layout_error;
    TextView btn_tryagain;
    TextView cart_incrementer;
    Products_Presenter presenter;
    product_adapter productAdapter;
    ProgressBar loading;
    int CountOfOrders = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init activity
        initActivity();
        //set progress bar is loading while get a response
        Loading(true);
        //get data From API
        getProducts();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //refresh data base in list products
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
    }

    private void initActivity() {

        products_cart = findViewById(R.id.products_cart);
        loading = findViewById(R.id.loading);
        layout_error = findViewById(R.id.layout_error);
        btn_tryagain = findViewById(R.id.btn_tryagain);
        cart_incrementer = findViewById(R.id.cart_incrementer);
        recyclerViewProducts = findViewById(R.id.recyclerView_products);

        productModuleList = new ArrayList<>();
        recyclerViewProducts.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
        presenter = new Products_Presenter(this);

        //GO to Cart Activity
        products_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(intent);
            }
        });

    }

    //get products from api
    public void getProducts() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIUrl.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        APIService apis = retrofit.create(APIService.class);
        Call<ArrayList<product_module>> call = apis.products();
        call.enqueue(new Callback<ArrayList<product_module>>() {
            @Override
            public void onResponse(Call<ArrayList<product_module>> call, Response<ArrayList<product_module>> response) {
                if (response.isSuccessful()) {
                    //response is correct with out network error
                    presenter.GetProductsFromJSON(response);
                } else {
                    // error case
                    switch (response.code()) {
                        case 404:
                            Toast.makeText(getApplicationContext(), "not found", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            Toast.makeText(getApplicationContext(), "server broken", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(getApplicationContext(), "unknown error", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            }

            @Override
            public void onFailure(Call<ArrayList<product_module>> call, Throwable t) {
                //network error
                presenter.HandelErrorConnections(t);
            }
        });
    }

    @Override
    public void updateProducts(ArrayList<productCart_module> ListProducts) {
        productModuleList = ListProducts;
        //set progress bar is disable and show recycler view
        Loading(false);
        //modify adapter with onclick item listener
        productAdapter = new product_adapter(MainActivity.this, ListProducts, new product_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(productCart_module item, boolean Add, int index) {

                //replace product after change statues it
                productModuleList.remove(index);
                productModuleList.add(index, item);
                //add item to cart and increment count of product in cart
                if (Add) {
                    startCountIncrementAnimation();
                } else {
                    // Decrement count of product in cart
                    startCountDecrementAnimation();
                }

            }
        });
        recyclerViewProducts.setAdapter(productAdapter);

    }

    @Override
    public void updateErrorProducts(String message) {
        //hide progress bar and recycler view and show layout error
        LoadingError();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void Loading(boolean state) {
        if (state) {// start loading data form api
            recyclerViewProducts.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);

        } else {
            //finish loading data form api
            loading.setVisibility(View.GONE);
            layout_error.setVisibility(View.GONE);
            recyclerViewProducts.setVisibility(View.VISIBLE);
        }
    }

    /**/
    public void LoadingError() {
        //error happend while loading data
        recyclerViewProducts.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        layout_error.setVisibility(View.VISIBLE);
    }

    //increment count of product in cart
    public void startCountIncrementAnimation() {
        cart_incrementer.setText(++CountOfOrders + "");
    }

    //decrement count of product in cart
    public void startCountDecrementAnimation() {
        cart_incrementer.setText(--CountOfOrders + "");
    }
}
