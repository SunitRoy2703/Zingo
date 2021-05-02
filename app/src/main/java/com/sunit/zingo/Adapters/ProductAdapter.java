package com.sunit.zingo.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sunit.zingo.Models.Product;
import com.sunit.zingo.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> {

    private List<Product> productList;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onAddClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ProductHolder extends RecyclerView.ViewHolder {
        public TextView textView1, textView2, textView3;
        public ImageView imageView;
        public Button button;

        public ProductHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.text_one);
            textView2 = itemView.findViewById(R.id.text_two);
            textView3 = itemView.findViewById(R.id.text_three);
            imageView = itemView.findViewById(R.id.image);
            button = itemView.findViewById(R.id.add_button);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) ;
                        listener.onItemClick(position);
                    }
                }
            });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION);
                    listener.onAddClick(position);
                }
            }
        });
        }
    }

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        ProductHolder viewHolder = new ProductHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = productList.get(position);

        holder.textView1.setText(product.getModel());
        holder.textView2.setText("Rs. " + product.getPrice());
        Glide.with(context)
                .load(product.getImage1())
                .into(holder.imageView);

        if (product.getAssign_product().equals("no")) {
            holder.button.setVisibility(View.GONE);
            holder.textView3.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}