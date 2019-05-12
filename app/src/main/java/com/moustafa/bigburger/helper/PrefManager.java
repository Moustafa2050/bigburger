package com.moustafa.bigburger.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.moustafa.bigburger.module.productCart_module;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class PrefManager {

    private Context context;

    public PrefManager(Context context) {
        this.context = context;
    }


    public int getCategoryID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Product_List", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("ID", 1);
    }

    public void setCategoryID(int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Product_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("ID", id);
        editor.apply();
    }

    public void clearCategoryID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Product_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    //For  save Product CartList
    public void saveProductCartList(List<String> List) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProductCart_List", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(List);
        editor.remove("List");
        editor.putString("List", json);
        editor.apply();
    }

    public List<String> getProductCartList() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("ProductCart_List", Context.MODE_PRIVATE);

        List<String> arrayItems = new ArrayList<>();
        String serializedObject = sharedPreferences.getString("List", null);
        if (serializedObject != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<String>>() {
            }.getType();
            arrayItems = gson.fromJson(serializedObject, type);
        }

        return arrayItems;
    }


}
