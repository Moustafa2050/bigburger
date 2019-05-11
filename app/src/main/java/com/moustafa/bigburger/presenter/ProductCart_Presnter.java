package com.moustafa.bigburger.presenter;

import com.moustafa.bigburger.View.productCart_view;
import com.moustafa.bigburger.module.productCart_module;

import java.util.ArrayList;

public class ProductCart_Presnter {

    private ArrayList<productCart_module> ListProducts;

    private productCart_view productCart_view;

    public ProductCart_Presnter(productCart_view context) {
        this.productCart_view = context;
        ListProducts = new ArrayList<>();

    }

    public void GetProductsFromJSON(ArrayList<productCart_module> response) {
        productCart_view.updateProductsCart(response);

    }

    public void HandelErrorConverter(String error) {

    }

}
