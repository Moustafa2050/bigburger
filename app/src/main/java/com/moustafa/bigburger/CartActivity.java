package com.moustafa.bigburger;

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
    float TotalPrice_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initActivity();
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

    }

    public void getListOfProducts() {
        ArrayList<productCart_module> product = MainActivity.productModuleList;
        for (int i = 0; i < product.size(); i++) {
            if (product.get(i).isAdded()) {
                productModuleList.add(product.get(i));
            }
        }
        presenter.GetProductsFromJSON(productModuleList);
    }

    @Override
    public void updateProductsCart(ArrayList<productCart_module> ListProducts) {
        loading_progress.setVisibility(View.GONE);
        productAdapter = new productCart_adapter(this, ListProducts, new productCart_adapter.OnItemClickListener() {
            @Override
            public void onIncrementClick(float totalPrice) {
            /*    productModuleList.remove(index);
                productModuleList.add(index, item);
                GetTotalPrice();*/
                total_price.setText(totalPrice + "");
            }


        });
        recyclerViewCartProducts.setAdapter(productAdapter);
        GetTotalPrice();
    }

    @Override
    public void updateErrorProductsCart(String message) {

    }

    public void GetTotalPrice() {
        for (int i = 0; i < productModuleList.size(); i++) {
            productCart_module product = productModuleList.get(i);
            if (product.isAdded()) {
                TotalPrice_value += product.getPrice() * product.getCount();
            }
        }
        total_price.setText(TotalPrice_value + "");
    }
}
