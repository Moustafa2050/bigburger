package com.moustafa.bigburger.adatper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.moustafa.bigburger.R;
import com.moustafa.bigburger.module.product_module;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


public class product_adapter extends RecyclerView.Adapter<product_adapter.MyViewHolder> {

    private Context mContext;

    private java.util.List<product_module> List;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, description, price;
        Button AddToCart;

        MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.product_image);
            name = view.findViewById(R.id.product_name);
            description = view.findViewById(R.id.product_description);
            price = view.findViewById(R.id.product_price);
            AddToCart = view.findViewById(R.id.product_btn);
        }
    }

    public product_adapter(Context mContext, java.util.List<product_module> List) {
        this.mContext = mContext;
        this.List = List;
    }

    @Override
    public product_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_product, parent, false);
        return new product_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final product_adapter.MyViewHolder holder, int position) {
        final product_module product = List.get(position);

        Picasso.get().load(product.getThumbnail())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .placeholder(R.drawable.burger)
                .error(R.drawable.image_error)
                .into(holder.image);
        holder.name.setText(product.getTitle());
        holder.description.setText(product.getDescription());
        holder.price.setText("" + product.getPrice());

        holder.AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return List.size();
    }
}
