package com.example.pointofsale;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SalesActivity extends AppCompatActivity implements RecyclerViewInterface{

    String code1 = "";
    String receiptId;
    TextView TotalPrice;
    RecyclerView recyclerView, recyclerView1;
    DatabaseReference database, database1;
    AllProductViewHolder myAdapter;
    MyAdaptor myAdaptor;
    DataPass dataPass;
    ArrayList<Product> list;
    ArrayList<Cart> cartArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        recyclerView = findViewById(R.id.viewAllProductsSale_rv);
        database = FirebaseDatabase.getInstance().getReference("Products");

        recyclerView1 = findViewById(R.id.viewAllProductsCart_rv);
        database1 = FirebaseDatabase.getInstance().getReference("Cart");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        cartArrayList = new ArrayList<>();

        myAdapter = new AllProductViewHolder(this, list, this);
        recyclerView.setAdapter(myAdapter);

        myAdaptor = new MyAdaptor(this, cartArrayList);
        recyclerView1.setAdapter(myAdaptor);

        TotalPrice = findViewById(R.id.totalPriceItems);

        File fileEvents = new File(SalesActivity.this.getFilesDir()+"/text/sample");
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileEvents));
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append(' ');
            }
            br.close();
        } catch (IOException e) { }
        String result = text.toString();

        TotalPrice.setText("Total: R" +result);

        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Cart cart = dataSnapshot.getValue(Cart.class);
                    cartArrayList.add(cart);

                }

                myAdaptor.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


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

        FloatingActionButton fab = findViewById(R.id.fab_btn1);
        FloatingActionButton checkOut = findViewById(R.id.fab_checkout_btn);

        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SalesActivity.this, CheckOutActivity.class);
                intent.putExtra("total", result);
                startActivity(intent);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

    }

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);
        barLauncher.launch(options);

    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->
    {
        if(result.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SalesActivity.this);
            builder.setTitle("Results");
            builder.setMessage(result.getContents());
            String code = result.getContents();
            code1 = code;
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                }
            }).show();
        }
    });

    @Override
    public void onItemClick(int position)
    {

    }


}