package com.moustafa.bigburger.adatper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.moustafa.bigburger.R;
import com.moustafa.bigburger.module.productCart_module;
import com.squareup.picasso.Picasso;

import java.util.List;

public class productCart_adapter extends RecyclerView.Adapter<productCart_adapter.MyViewHolder> {

    Context mContext;
    private List<productCart_module> List;
    private final productCart_adapter.OnItemClickListener listener;


    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, remove;
        TextView name, count, price;
        ImageButton Increment, Decrment;

        MyViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.productCart_image);
            name = view.findViewById(R.id.productCart_name);
            count = view.findViewById(R.id.productCart_count);
            price = view.findViewById(R.id.productCart_price);
            Increment = view.findViewById(R.id.productCart_Increment);
            Decrment = view.findViewById(R.id.productCart_Decrement);
            remove = view.findViewById(R.id.remove);
        }

    }

    public productCart_adapter(Context mContext, java.util.List<productCart_module> List, productCart_adapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.List = List;
        this.listener = listener;
    }

    @NonNull
    @Override
    public productCart_adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_prodect_cart, parent, false);
        return new productCart_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final productCart_adapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final productCart_module product = List.get(position);

        Picasso.get().load(product.getThumbnail()).error(R.drawable.image_error)
                .into(holder.image);
        holder.name.setText(product.getTitle());
        double percentage = (product.getPrice());
        holder.price.setText(percentage + " ₺");

        holder.Increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List.get(position).setCount(product.getCount() + 1);
                holder.count.setText(List.get(position).getCount() + "");

                listener.onIncrementClick(GetTotalPrice());
            }
        });
        holder.Decrment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (List.get(position).getCount() > 1) {
                    List.get(position).setCount(product.getCount() - 1);
                    holder.count.setText(List.get(position).getCount() + "");
                    listener.onDecrementClick(GetTotalPrice());
                }

            }
        });
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List.remove(product);
                notifyDataSetChanged();
                listener.onRemoveClick(GetTotalPrice(), product.getRef());
            }
        });

    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public interface OnItemClickListener {
        void onIncrementClick(float totalPrice);

        void onDecrementClick(float totalPrice);

        void onRemoveClick(float totalPrice, String id);
    }

    private float GetTotalPrice() {
        float TotalPrice_value = 0.00f;
        for (int i = 0; i < List.size(); i++) {
            productCart_module product = List.get(i);
            if (product.isAdded()) {
                TotalPrice_value += product.getPrice() * product.getCount();
            }
        }
        return TotalPrice_value;
    }

}
