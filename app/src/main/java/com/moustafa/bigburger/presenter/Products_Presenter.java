package com.moustafa.bigburger.presenter;

import com.moustafa.bigburger.View.product_view;
import com.moustafa.bigburger.module.productCart_module;
import com.moustafa.bigburger.module.product_module;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class Products_Presenter {
    private ArrayList<productCart_module> ListProducts;

    private product_view product_view;

    public Products_Presenter(product_view context) {
        this.product_view = context;
        ListProducts = new ArrayList<>();
    }

    public void GetProductsFromJSON(Response<ArrayList<product_module>> response) {
        if (response.isSuccessful()) {
            for (int i = 0; i < response.body().size(); i++) {
                productCart_module product = new productCart_module();
                product.setRef(response.body().get(i).getRef());
                product.setDescription(response.body().get(i).getDescription());
                product.setTitle(response.body().get(i).getTitle());
                product.setThumbnail(response.body().get(i).getThumbnail());
                product.setPrice(response.body().get(i).getPrice());
                product.setAdded(false);
                product.setCount(0);
                ListProducts.add(product);
            }
            product_view.updateProducts(ListProducts);

        } else {
            HandelErrorConverter(response.errorBody());
        }

    }

    public void HandelErrorConnections(Throwable t) {

    }

    public void HandelErrorConverter(ResponseBody responseBody) {
        product_view.updateErrorProducts("network failure :( inform the user and possibly retry");
    }


}
