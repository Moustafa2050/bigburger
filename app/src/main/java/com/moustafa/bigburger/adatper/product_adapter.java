package com.moustafa.bigburger.adatper;

import android.content.Context;
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

        public void bind(final productCart_module product, final OnItemClickListener listener, final int index) {

            Picasso.get().load(product.getThumbnail()).error(R.drawable.image_error)
                    .into(image);
            name.setText(product.getTitle());
            description.setText(product.getDescription());
            price.setText( ""+product.getPrice());

            AddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (product.isAdded()) {//item is already add in cart
                        List.get(index).setAdded(false);
                        List.get(index).setCount(0);
                        listener.onItemClick(List.get(index), false,index);
                        AddToCart.setText("Add to cart");
                        AddToCart.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_green));
                    } else {//item is not add in cart
                        List.get(index).setAdded(true);
                        List.get(index).setCount(1);
                        listener.onItemClick(List.get(index), true,index);
                        AddToCart.setText("Remove");
                        AddToCart.setBackground(mContext.getResources().getDrawable(R.drawable.shape_btn_red));
                    }


                }
            });
        }
    }

    public product_adapter(Context mContext, java.util.List<productCart_module> List, OnItemClickListener listener) {
        this.mContext = mContext;
        this.List = List;
        this.listener = listener;
    }

    @Override
    public product_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_product, parent, false);
        return new product_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final product_adapter.MyViewHolder holder, int position) {
        holder.bind(List.get(position), listener, position);
    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public interface OnItemClickListener {
        void onItemClick(productCart_module item, boolean Add,int index);
    }
}
