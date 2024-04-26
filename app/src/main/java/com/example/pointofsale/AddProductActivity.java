package com.example.pointofsale;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddProductActivity extends AppCompatActivity {

    ImageButton backBtn;
    Button addProduct;
    Button scanBarcodeBtn;
    EditText barcode;
    EditText name;
    EditText description;
    EditText price;
    EditText Quantity;
    AutoCompleteTextView acTextView;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat, simpleTimeFormat;
    String Date;
    String Time;
    String[] Category = { "Food","Beverage","Clothing","Technology", "Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        scanBarcodeBtn = findViewById(R.id.addBarcodeProductBtn);
        backBtn = findViewById(R.id.backIb);
        addProduct = findViewById(R.id.addProductBtn);
        barcode = findViewById(R.id.barcodeEd);
        name = findViewById(R.id.productNameEd);
        description = findViewById(R.id.prodDescriptionEd);
        price = findViewById(R.id.productPriceEd);
        Quantity = findViewById(R.id.productQuantityEd);

        String pName = getIntent().getExtras().getString("name");
        String pDescription = getIntent().getExtras().getString("description");
        String pPrice = getIntent().getExtras().getString("price");
        String pBarcode = getIntent().getExtras().getString("barcode");
        String pCategory = getIntent().getExtras().getString("category");
        String pQuantity = getIntent().getExtras().getString("quantity");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, Category);
        acTextView = (AutoCompleteTextView) findViewById(R.id.productCategoryEd);
        acTextView.setThreshold(1);
        acTextView.setAdapter(adapter);

        barcode.setText(pBarcode);
        name.setText(pName);
        acTextView.setText(pCategory);
        description.setText(pDescription);
        price.setText(pPrice);
        Quantity.setText(pQuantity);

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy");
        simpleTimeFormat = new SimpleDateFormat("HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());
        Time = simpleTimeFormat.format(calendar.getTime());

        checkIfProductExist();


        scanBarcodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanCode();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddProductActivity.this, ProductsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkIfProductExist()
    {
        if(barcode.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Add new Product", Toast.LENGTH_SHORT).show();

            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String pBarcode = barcode.getText().toString();
                    String pName = name.getText().toString();
                    String pDescription = description.getText().toString();
                    String pPrice = price.getText().toString();
                    String pDate = Date;
                    String pTime = Time;
                    String pQuantity = Quantity.getText().toString();
                    String pCategory = acTextView.getText().toString();

                    if (pBarcode.isEmpty() || pName.isEmpty() || pCategory.isEmpty() || pDescription.isEmpty() || pPrice.isEmpty() || pQuantity.isEmpty())
                    {
                        Toast.makeText(AddProductActivity.this, "Please make sure You have completed all fields", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        addProductToDb(pBarcode, pName, pDescription, pPrice, pDate, pTime, pQuantity, pCategory);
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Edit Product", Toast.LENGTH_SHORT).show();

            addProduct.setText("Update");
            scanBarcodeBtn.setVisibility(View.INVISIBLE);
            addProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    updateProductToTheDatabase();

                    Intent intent = new Intent(AddProductActivity.this, ProductsActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

    private void updateProductToTheDatabase()
    {
        String pBarcode = barcode.getText().toString();
        String pName = name.getText().toString();
        String pDescription = description.getText().toString();
        String pPrice = price.getText().toString();
        String pDate = Date;
        String pTime = Time;
        String pQuantity = Quantity.getText().toString();
        String pCategory = acTextView.getText().toString();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Products").child(pBarcode);
        databaseReference.child("name").setValue(pName);
        databaseReference.child("category").setValue(pCategory);
        databaseReference.child("description").setValue(pDescription);
        databaseReference.child("price").setValue(pPrice);
        databaseReference.child("quantity").setValue(pQuantity);
        databaseReference.child("time").setValue(pTime);
        databaseReference.child("date").setValue(pDate);

    }

    private void addProductToDb(String pBarcode, String pName, String pDescription, String pPrice, String pDate, String pTime, String pQuantity, String pCategory)
    {

        HashMap<String, Object> productHashMap = new HashMap<>();

        productHashMap.put("barcode",pBarcode);
        productHashMap.put("name",pName);
        productHashMap.put("description",pDescription);
        productHashMap.put("price",pPrice);
        productHashMap.put("time",pTime);
        productHashMap.put("date",pDate);
        productHashMap.put("quantity",pQuantity);
        productHashMap.put("category",pCategory);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference productRef = database.getReference("Products");

        productRef.child(pBarcode).setValue(productHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(AddProductActivity.this, "Product add Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddProductActivity.this, ProductsActivity.class);
                startActivity(intent);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(AddProductActivity.this);
            builder.setTitle("Results");
            builder.setMessage(result.getContents());
            String code = result.getContents();
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    barcode.setText(code);
                    dialog.dismiss();
                }
            }).show();
        }
    });

}