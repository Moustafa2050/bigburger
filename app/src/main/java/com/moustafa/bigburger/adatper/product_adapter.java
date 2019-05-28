package com.moustafa.bigburger.adatper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.moustafa.bigburger.R;
import com.moustafa.bigburger.module.productCart_module;
import com.moustafa.bigburger.module.product_module;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class product_adapter extends RecyclerView.Adapter<product_adapter.MyViewHolder> {

    private Context mContext;
    private java.util.List<productCart_module> List;
    private final OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, description, price, AddToCart;

        MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.product_image);
            name = view.findViewById(R.id.product_name);
            description = view.findViewById(R.id.product_description);
            price = view.findViewById(R.id.product_price);
            AddToCart = view.findViewById(R.id.product_btn);
        }


    }

    public product_adapter(Context mContext, java.util.List<productCart_module> List, OnItemClickListener listener) {
        this.mContext = mContext;
        //list of products
        this.List = List;
        this.listener = listener;
    }

    @NonNull
    @Override
    public product_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_product, parent, false);
        return new product_adapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final product_adapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final productCart_module product = List.get(position);
        //loading image of product by picasso library
        Picasso.get().load(product.getThumbnail()).error(R.drawable.image_error)
                .into(holder.image);
        //get product image
        holder.name.setText(product.getTitle());
        //get  product description
        holder.description.setText(product.getDescription());
        //get product price
        holder.price.setText(product.getPrice() + " ₺");
        //modify add to cart button if product is added to cart or not
        if (!product.isAdded()) {//item is not added in cart
            holder.AddToCart.setText("Add to cart");
            holder.AddToCart.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_green));
        } else {//item already is added  in cart
            holder.AddToCart.setText("Remove");
            holder.AddToCart.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_red));
        }

        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.isAdded()) {//item is already added in cart
                    List.get(position).setAdded(false);
                    List.get(position).setCount(0);
                    listener.onItemClick(List.get(position), false, position);
                    holder.AddToCart.setText("Add to cart");
                    holder.AddToCart.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_green));
                } else {//item is not add in cart
                    List.get(position).setAdded(true);
                    List.get(position).setCount(1);
                    listener.onItemClick(List.get(position), true, position);
                    holder.AddToCart.setText("Remove");
                    holder.AddToCart.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_red));
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public interface OnItemClickListener {
        void onItemClick(productCart_module item, boolean Add, int index);
    }
}
