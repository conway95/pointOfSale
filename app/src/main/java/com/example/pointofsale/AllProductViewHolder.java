package com.example.pointofsale;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class AllProductViewHolder extends RecyclerView.Adapter<AllProductViewHolder.MyViewHolder>
{
    private final RecyclerViewInterface recyclerViewInterface;
    Context context;
    ArrayList<Product> list;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat, simpleTimeFormat;
    String Date;
    String Time;

    public AllProductViewHolder(Context context, ArrayList<Product> list, RecyclerViewInterface recyclerViewInterface)
    {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.all_product_item,parent,false);
        return new MyViewHolder(v, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());
        Time = simpleTimeFormat.format(calendar.getTime());

        Product product = list.get(position);
        holder.Name.setText(product.getName());
        holder.Price.setText("R" + product.getPrice());
        holder.Description.setText(product.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, product.getName(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, SelectedProductActivity.class);
                intent.putExtra("barcode", product.getBarcode());
                intent.putExtra("name", product.getName());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("description", product.getDescription());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Description, Price;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            Name = itemView.findViewById(R.id.viewAllProductNameTv);
            Price = itemView.findViewById(R.id.viewAllProductPriceTv);
            Description = itemView.findViewById(R.id.viewAllProductDescTv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recyclerViewInterface != null)
                    {
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION)
                        {
                            recyclerViewInterface.onItemClick(pos);

                        }
                    }
                }
            });

        }
    }
}
