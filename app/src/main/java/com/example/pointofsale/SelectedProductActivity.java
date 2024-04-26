package com.example.pointofsale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SelectedProductActivity extends AppCompatActivity {

    TextView ProductName;
    TextView ProductDescription;
    TextView ProductPrice;
    TextView ProductTotalPrice;
    TextView totalNumberOfItems;
    TextView totalPrice;

    Button addToCart;
    Button positive;
    Button negative;
    Button cancel;
    int productNumber = 1;
    String receiptId;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat, simpleTimeFormat;
    String Date;
    String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_product);

        ProductName = findViewById(R.id.productNameSellTv);
        ProductDescription = findViewById(R.id.productDescriptionSellTv);
        ProductPrice = findViewById(R.id.productPriceSellTv);
        ProductTotalPrice = findViewById(R.id.addProductTotalPriceTv);
        addToCart = findViewById(R.id.addProductToCartBtn);
        positive = findViewById(R.id.productAdditionBtn);
        negative = findViewById(R.id.productDecreaseBtn);
        totalNumberOfItems = findViewById(R.id.numberOfItems);
        totalPrice = findViewById(R.id.addProductTotalPriceTv);
        cancel = findViewById(R.id.cancelProductToCartBtn);

        String pName = getIntent().getExtras().getString("name");
        String pDescription = getIntent().getExtras().getString("description");
        String pPrice = getIntent().getExtras().getString("price");


        SharedPreferences sh = getSharedPreferences("receiptIdPref", Context.MODE_PRIVATE);
        String s1 = sh.getString("receiptId", "");

        Toast.makeText(this, "pref " + s1, Toast.LENGTH_SHORT).show();
        if (s1 == "")
        {
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
            simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
            Date = simpleDateFormat.format(calendar.getTime());
            Time = simpleTimeFormat.format(calendar.getTime());
            receiptId = Time;

            Toast.makeText(this, "new " + receiptId, Toast.LENGTH_SHORT).show();

            // Storing data into SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("receiptIdPref",MODE_PRIVATE);
            SharedPreferences.Editor myEdit = sharedPreferences.edit();
            myEdit.putString("receiptId", receiptId);
            myEdit.commit();
        }
        else
        {
            receiptId = s1;
            Toast.makeText(this, "old " + receiptId, Toast.LENGTH_SHORT).show();
        }


        totalPrice.setText("R" + pPrice);

        ProductName.setText(pName);
        ProductDescription.setText(pDescription);
        ProductPrice.setText("R" +pPrice);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectedProductActivity.this, SalesActivity.class);
                startActivity(intent);
            }
        });

        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productNumber = Integer.parseInt(totalNumberOfItems.getText().toString());
                productNumber = productNumber + 1;
                totalNumberOfItems.setText(String.valueOf(productNumber));
                totalPrice.setText("R" + String.valueOf(productNumber * Integer.parseInt(pPrice)));

            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productNumber = Integer.parseInt(totalNumberOfItems.getText().toString());

                if (productNumber <2)
                {
                    Toast.makeText(SelectedProductActivity.this, "Number of items can not be less than 1", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    productNumber = productNumber - 1;

                    totalNumberOfItems.setText(String.valueOf(productNumber));

                    totalPrice.setText("R" + String.valueOf(productNumber * Integer.parseInt(pPrice)));
                }
            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String barcode = getIntent().getExtras().getString("barcode");
                String name = ProductName.getText().toString();
                String price = String.valueOf(productNumber * Integer.parseInt(pPrice));
                String numProducts = totalNumberOfItems.getText().toString();


                //--------------------------Beginning of read------------------
                File fileEvents = new File(SelectedProductActivity.this.getFilesDir()+"/text/sample");
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(fileEvents));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                    }
                    br.close();
                } catch (IOException e) { }
                String PreviousTotal = text.toString();
                Toast.makeText(SelectedProductActivity.this, "" + PreviousTotal, Toast.LENGTH_SHORT).show();
                //-----------------------------End of read-------------------

                int prev = Integer.parseInt(PreviousTotal);
                int newP = Integer.parseInt(price);
                int newTotal = prev + newP;

                String newTotalString = Integer.toString(newTotal);

                addToCartDataBase(barcode, name, price, numProducts, newTotalString);
                addToHistoryDataBase(barcode, name, price, numProducts, newTotalString);

            }
        });

    }

    private void addToHistoryDataBase(String barcode, String name, String price, String numProducts, String newTotalString)
    {
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        String currentDate = simpleDateFormat.format(calendar.getTime());
        Time = simpleTimeFormat.format(calendar.getTime());


        HashMap<String, Object> productHashMap = new HashMap<>();

        productHashMap.put("barcode",barcode);
        productHashMap.put("name",name);
        productHashMap.put("price",price);
        productHashMap.put("numProducts",numProducts);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productRef = database.getReference("Sales");

        productRef.child(currentDate).child(receiptId).child(barcode).setValue(productHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(SelectedProductActivity.this, SalesActivity.class);
                intent.putExtra("receiptId", receiptId);
            }
        });


    }

    private void addToCartDataBase(String barcode, String name, String price, String numProducts, String newTotal)
    {

        HashMap<String, Object> productHashMap = new HashMap<>();

        productHashMap.put("barcode",barcode);
        productHashMap.put("name",name);
        productHashMap.put("price",price);
        productHashMap.put("numProducts",numProducts);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productRef = database.getReference("Cart");

        //String key = productRef.push().getKey();

        productRef.child(barcode).setValue(productHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (!price.isEmpty()) {
                    File file = new File(SelectedProductActivity.this.getFilesDir(), "text");
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    try {
                        File gpxfile = new File(file, "sample");
                        FileWriter writer = new FileWriter(gpxfile);
                        writer.append(newTotal);
                        writer.flush();
                        writer.close();
                        Toast.makeText(SelectedProductActivity.this, "Saved your text", Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e) { }
                }

                Toast.makeText(SelectedProductActivity.this, "Product added to Cart", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SelectedProductActivity.this, SalesActivity.class);
                intent.putExtra("receiptId", receiptId);
                startActivity(intent);
            }
        });


    }
}