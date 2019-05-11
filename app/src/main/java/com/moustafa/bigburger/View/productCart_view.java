package com.moustafa.bigburger.View;

import com.moustafa.bigburger.module.productCart_module;

import java.util.ArrayList;

public interface productCart_view {
    void updateProductsCart(ArrayList<productCart_module> ListProducts);

    void updateErrorProductsCart(String message);
}
