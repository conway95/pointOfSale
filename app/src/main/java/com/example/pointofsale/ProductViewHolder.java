package com.example.pointofsale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.MyViewHolder>
{
    Context context;
    ArrayList<Product> list;
    private SelectListener listener;
    public ProductViewHolder(Context context, ArrayList<Product> list, SelectListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.product_item,parent,false);
        return new MyViewHolder(v, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Product product = list.get(position);
        holder.Name.setText(product.getName());
        holder.Price.setText("R" + product.getPrice());
        holder.Description.setText(product.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                // Set the message show for the Alert time
                builder.setMessage("Do you want to Edit or Delete Product?");

                // Set Alert Title
                builder.setTitle("Alert !");

                // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                builder.setCancelable(true);

                // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setPositiveButton("Edit", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // When the user click yes button then app will close
//                    finish();

                    Intent intent = new Intent(context, AddProductActivity.class);
                    intent.putExtra("barcode", product.getBarcode());
                    intent.putExtra("name", product.getName());
                    intent.putExtra("price", product.getPrice());
                    intent.putExtra("category", product.getCategory());
                    intent.putExtra("description", product.getDescription());
                    intent.putExtra("quantity", product.getQuantity());
                    context.startActivity(intent);

                });

                // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                builder.setNegativeButton("Remove", (DialogInterface.OnClickListener) (dialog, which) -> {
                    // If user click no then dialog box is canceled.
//------------------------------------------------------------------------------------------------------
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(context);

                    // Set the message show for the Alert time
                    builder1.setMessage("Are you sure you want to remove " + product.getName() + "? ");

                    // Set Alert Title
                    builder1.setTitle("Alert !");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder1.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder1.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog1, which1) -> {
                        // When the user click yes button then app will close

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products");
                        databaseReference.child(product.getBarcode()).removeValue();
                        Intent intent = new Intent(context, ProductsActivity.class);
                        context.startActivity(intent);

                    });

                    // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                    builder1.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog1, which1) -> {
                        // If user click no then dialog box is canceled.
                        dialog1.cancel();
                    });

                    // Create the Alert dialog
                    AlertDialog alertDialog1 = builder1.create();
                    // Show the Alert Dialog box
                    alertDialog1.show();

                });

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Name, Description, Price;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, SelectListener listener) {
            super(itemView);

            Name = itemView.findViewById(R.id.viewProductNameTv);
            Price = itemView.findViewById(R.id.viewProductPriceTv);
            Description = itemView.findViewById(R.id.viewProductDescTv);

            cardView = itemView.findViewById(R.id.addProductCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                    {
                        int pos = getAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION)
                        {
                            listener.onItemClicked(pos);
                        }
                    }
                }
            });

        }
    }
}
