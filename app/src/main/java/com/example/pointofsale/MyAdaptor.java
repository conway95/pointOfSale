package com.example.pointofsale;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdaptor extends RecyclerView.Adapter<MyAdaptor.MyViewHolder>
{
    Context context;
    ArrayList<Cart> cartList;
    public MyAdaptor(Context context, ArrayList<Cart> cartList)
    {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.all_cart_item, parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Cart cart = cartList.get(position);
        holder.productName.setText(cart.getName());
        holder.productPrice.setText("R" + cart.getPrice());
        holder.numberOfProducts.setText("X" + cart.getNumProducts());

        int totalCart = 100;

        Intent intent = new Intent(context, SalesActivity.class);
        intent.putExtra("total", totalCart);

    }

    @Override
    public int getItemCount() {
        return cartList.size();

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView productName, productPrice, numberOfProducts, TotalPriceTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = itemView.findViewById(R.id.viewCartProductNameTv);
            productPrice = itemView.findViewById(R.id.viewCartProductPriceTv);
            numberOfProducts = itemView.findViewById(R.id.viewCartNumber);
            TotalPriceTv = itemView.findViewById(R.id.totalPriceItems);
        }


    }
}
