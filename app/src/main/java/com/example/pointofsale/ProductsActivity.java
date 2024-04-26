package com.example.pointofsale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    DatabaseReference database;
    ProductViewHolder myAdapter;
    ArrayList<Product> list;
    FloatingActionButton addProduct;
    ImageButton backIb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        addProduct = findViewById(R.id.fabAddBtn);
        backIb = findViewById(R.id.backIb);

        recyclerView = findViewById(R.id.viewProducts_rv);
        database = FirebaseDatabase.getInstance().getReference("Products");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new ProductViewHolder(this, list, this);
        recyclerView.setAdapter(myAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Product product = dataSnapshot.getValue(Product.class);
                    list.add(product);
                }

                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        backIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String empty = "";

                Intent intent = new Intent(ProductsActivity.this, AddProductActivity.class);
                intent.putExtra("barcode", empty);
                intent.putExtra("name", empty);
                intent.putExtra("category", empty);
                intent.putExtra("description", empty);
                intent.putExtra("price", empty);
                intent.putExtra("quantity", empty);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onItemClicked(int position) {

    }
}