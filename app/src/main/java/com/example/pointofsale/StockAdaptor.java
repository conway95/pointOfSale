package com.example.pointofsale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StockAdaptor extends RecyclerView.Adapter<StockAdaptor.MyViewHolder> {

    Context context;
    ArrayList<Product> list;

    public StockAdaptor(Context context, ArrayList<Product> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stock_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Product product = list.get(position);
        holder.stockProductName.setText(product.getName());
        holder.previousStockQuantity.setText(product.getQuantity());
        holder.stockProductDescription.setText(product.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView stockProductName, stockProductDescription,previousStockQuantity, expectedSales;
        EditText currentStockQuantity;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            stockProductName = itemView.findViewById(R.id.stockPName);
            stockProductDescription = itemView.findViewById(R.id.stockPDescription);
            previousStockQuantity = itemView.findViewById(R.id.prevStockNum);
            expectedSales = itemView.findViewById(R.id.expectedSalesNum);
            currentStockQuantity = itemView.findViewById(R.id.currentStockNum);


        }
    }
}
