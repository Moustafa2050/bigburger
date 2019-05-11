package com.moustafa.bigburger.adatper;

import android.content.Context;
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

public class productCart_adapter extends RecyclerView.Adapter<productCart_adapter.MyViewHolder> {

    private Context mContext;
    private java.util.List<productCart_module> List;
    private final productCart_adapter.OnItemClickListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image, remove;
        TextView name, count, price;
        ImageButton Increment, Decrment;
        float TotalPrice_value;


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

        public void bind(final productCart_module product, final productCart_adapter.OnItemClickListener listener, final int position) {

            Picasso.get().load(product.getThumbnail()).error(R.drawable.image_error)
                    .into(image);
            name.setText(product.getTitle());
            price.setText("" + product.getPrice());
            Increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    List.get(position).setCount(product.getCount() + 1);
                    count.setText(List.get(position).getCount()+ "");
                    GetTotalPrice();
                    listener.onIncrementClick(TotalPrice_value);
                }
            });
            Decrment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (List.get(position).getCount()>1){
                        List.get(position).setCount(product.getCount() - 1);
                        count.setText(List.get(position).getCount()+ "");
                        GetTotalPrice();
                        listener.onIncrementClick(TotalPrice_value);
                    }

                }
            });
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    List.remove(product);
                    notifyDataSetChanged();
                    GetTotalPrice();
                    listener.onIncrementClick(TotalPrice_value);
                }
            });
        }
        public void GetTotalPrice() {
            for (int i = 0; i < List.size(); i++) {
                productCart_module product = List.get(i);
                if (product.isAdded()) {
                    TotalPrice_value += product.getPrice() * product.getCount();
                }
            }
        }
    }

    public productCart_adapter(Context mContext, java.util.List<productCart_module> List, productCart_adapter.OnItemClickListener listener) {
        this.mContext = mContext;
        this.List = List;
        this.listener = listener;
    }

    @Override
    public productCart_adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_prodect_cart, parent, false);
        return new productCart_adapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final productCart_adapter.MyViewHolder holder, int position) {
        holder.bind(List.get(position), listener, position);
    }


    @Override
    public int getItemCount() {
        return List.size();
    }

    public interface OnItemClickListener {
        void onIncrementClick(float totalPrice);

    }


}
