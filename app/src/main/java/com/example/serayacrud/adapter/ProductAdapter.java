package com.example.serayacrud.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serayacrud.R;
import com.example.serayacrud.activities.AddProductActivity;
import com.example.serayacrud.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    List<Product> products;
    Context context;

    public ProductAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

    public ProductAdapter(Context context) {
        this.context = context;
    }

    public void setProductList(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public void deleteProductFromList(Product product){
        this.products.remove(product);
        notifyDataSetChanged();
    }

    public Product getProductAtPos(int pos) {
        return products.get(pos);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Log.d("PRODUCTADAPTER", String.valueOf(products.size()));

        if (products.size() > 0) {
            Product product = products.get(position);

            holder.tvProductName.setText(product.getProductName());
            holder.tvProductPrice.setText("Rp. " + (product.getProductPrice() <= 0 ? "-" : String.valueOf(product.getProductPrice())));
            holder.tvProductDesc.setText(product.getProductDescription());
        } else {
            Toast.makeText(context, "No Products", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductPrice, tvProductDesc;
        LinearLayout llProductList;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductDesc = itemView.findViewById(R.id.tvProductDesc);
        }
    }
}
