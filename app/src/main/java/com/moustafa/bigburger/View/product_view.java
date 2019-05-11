package com.moustafa.bigburger.View;

import com.moustafa.bigburger.module.productCart_module;
import com.moustafa.bigburger.module.product_module;

import java.util.ArrayList;

public interface product_view {
    void updateProducts(ArrayList<productCart_module> ListProducts);

    void updateErrorProducts(String message);
}
