package com.moustafa.bigburger.presenter;

import com.moustafa.bigburger.View.product_view;
import com.moustafa.bigburger.module.product_module;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class Products_Presenter {
    private ArrayList<product_module> ListProducts;

    private product_view product_view;

    public Products_Presenter(product_view context) {
        this.product_view = context;
        ListProducts=new ArrayList<>();

    }

    public  void GetProductsFromJSON(Response<ArrayList<product_module>> response) {
        if (response.isSuccessful()){
            for (int i=0;i<response.body().size();i++){
                product_module product=new product_module();
                product.setRef(response.body().get(i).getRef());
                product.setDescription(response.body().get(i).getDescription());
                product.setTitle(response.body().get(i).getTitle());
                product.setThumbnail(response.body().get(i).getThumbnail());
                product.setPrice(response.body().get(i).getPrice());
                ListProducts.add(product);
            }
            product_view.updateProducts(ListProducts);

        }else {
            HandelErrorConverter(response.errorBody());
        }

    }

    public void HandelErrorConnections(Throwable t) {

    }

    public  void HandelErrorConverter(ResponseBody responseBody){

    }


}