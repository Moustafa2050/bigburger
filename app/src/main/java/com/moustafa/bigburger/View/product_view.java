package com.moustafa.bigburger.View;

import com.moustafa.bigburger.module.product_module;

import java.util.ArrayList;

public interface product_view {
    void updateProducts(ArrayList<product_module> ListProducts);

    void updateErrorProducts(String message);
}
