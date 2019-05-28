package com.moustafa.bigburger;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.moustafa.bigburger.View.productCart_view;
import com.moustafa.bigburger.adatper.productCart_adapter;
import com.moustafa.bigburger.adatper.product_adapter;
import com.moustafa.bigburger.module.productCart_module;
import com.moustafa.bigburger.module.product_module;
import com.moustafa.bigburger.presenter.ProductCart_Presnter;
import com.moustafa.bigburger.presenter.Products_Presenter;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartActivity extends Activity implements productCart_view {
    ProgressBar loading_progress;
    ImageView products_back;
    RecyclerView recyclerViewCartProducts;
    ArrayList<productCart_module> productModuleList;
    productCart_adapter productAdapter;
    ProductCart_Presnter presenter;
    TextView total_price;
    Button btn_checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //initial Activity
        initActivity();
        //get response for API
        getListOfProducts();
    }

    private void initActivity() {
        products_back = findViewById(R.id.products_back);
        loading_progress = findViewById(R.id.loading_progress);
        total_price = findViewById(R.id.total_price);
        btn_checkout = findViewById(R.id.btn_checkout);
        recyclerViewCartProducts = findViewById(R.id.recyclerView_cart);
        recyclerViewCartProducts.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, false));
        presenter = new ProductCart_Presnter(this);
        productModuleList = new ArrayList<>();
        loading_progress.setVisibility(View.VISIBLE);

        products_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void getListOfProducts() {
        ArrayList<productCart_module> product = MainActivity.productModuleList;//get list of Products for Main Activity
        for (int i = 0; i < product.size(); i++) {
            if (product.get(i).isAdded()) {
              //  product.get(i).setPrice((double) (product.get(i).getPrice() ));
                productModuleList.add(product.get(i));
            }
        }
        presenter.GetProductsFromJSON(productModuleList);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateProductsCart(ArrayList<productCart_module> ListProducts) {
        loading_progress.setVisibility(View.GONE);
        productAdapter = new productCart_adapter(this, ListProducts, new productCart_adapter.OnItemClickListener() {

            @Override
            public void onIncrementClick(float totalPrice) {
                total_price.setText(totalPrice + " ₺");
            }

            @Override
            public void onDecrementClick(float totalPrice) {
                total_price.setText(totalPrice + " ₺");
            }

            @Override
            public void onRemoveClick(float totalPrice, String id) {
                total_price.setText(totalPrice + " ₺");
            }

        });
        recyclerViewCartProducts.setAdapter(productAdapter);
        GetTotalPrice();//get the total price for all products in cart
    }

    @Override
    public void updateErrorProductsCart(String message) {

    }

    @SuppressLint("SetTextI18n")
    public void GetTotalPrice() {
        double TotalPrice_value=0.00f;
        for (int i = 0; i < productModuleList.size(); i++) {
            productCart_module product = productModuleList.get(i);
            if (product.isAdded()) {
                TotalPrice_value += product.getPrice() * product.getCount();
            }
        }
        total_price.setText(new DecimalFormat("##.##").format(TotalPrice_value) + " ₺");
    }
}
