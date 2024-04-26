package com.example.pointofsale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;

public class CheckOutActivity extends AppCompatActivity {

    Button oneCentBtn, twoCentBtn,fiveCentBtn;
    Button oneRandBtn, twoRandBtn, fiveRandBtn;
    Button tenRand, twentyRand, fiftyRand;
    Button oneHundredRandBtn, twoHundredRandBtn;
    Button ClearBtn, doneBtn;
    EditText inputCash;
    TextView TotalCart, TotalChange;
    double buttonValue = 0.0;
    FirebaseDatabase database;
    DatabaseReference productRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        database = FirebaseDatabase.getInstance();
        productRef = database.getReference("Cart");

        oneCentBtn = findViewById(R.id.cash0_1Btn);
        twoCentBtn = findViewById(R.id.cash0_2Btn);
        fiveCentBtn = findViewById(R.id.cash0_5Btn);
        oneRandBtn = findViewById(R.id.cashR1Btn);
        twoRandBtn = findViewById(R.id.cashR2Btn);
        fiveRandBtn = findViewById(R.id.cashR5Btn);
        tenRand = findViewById(R.id.cashR10Btn);
        twentyRand = findViewById(R.id.cashR20Btn);
        fiftyRand = findViewById(R.id.cashR50Btn);
        oneHundredRandBtn = findViewById(R.id.cashR100Btn);
        twoHundredRandBtn = findViewById(R.id.cashR200Btn);
        ClearBtn = findViewById(R.id.cashClearBtn);
        doneBtn = findViewById(R.id.done);
        inputCash = findViewById(R.id.cashEt);
        TotalCart = findViewById(R.id.checkOutItemsTotal);
        TotalChange = findViewById(R.id.checkOutChange);



        String cartTotal = getIntent().getExtras().getString("total");

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Double cash = Double.valueOf(inputCash.getText().toString());
                Double total = Double.valueOf(cartTotal);

                if (cash < total)
                {
                    Toast.makeText(CheckOutActivity.this, "Please make sure cash is equal or more than Total", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    saveToDatabase();
                }


            }
        });

        TotalCart.setText("R" + cartTotal);
        oneCentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 0.10;
                work(buttonValue, cartTotal);
            }
        });
        twoCentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 0.20;
                work(buttonValue, cartTotal);
            }
        });
        fiveCentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 0.50;
                work(buttonValue, cartTotal);
            }
        });
        oneRandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 1.0;
                work(buttonValue, cartTotal);
            }
        });
        twoRandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 2.0;
                work(buttonValue, cartTotal);
            }
        });
        fiveRandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 5.0;
                work(buttonValue, cartTotal);
            }
        });
        tenRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 10.0;
                work(buttonValue, cartTotal);
            }
        });
        twentyRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 20.0;
                work(buttonValue, cartTotal);
            }
        });
        fiftyRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 50.0;
                work(buttonValue, cartTotal);
            }
        });
        oneHundredRandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 100.0;
                work(buttonValue, cartTotal);
            }
        });
        twoHundredRandBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonValue = 200.0;
                work(buttonValue, cartTotal);
            }
        });
        ClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCash.setText("0");
            }
        });

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren())
                {
                    if(ds.child("barcode").exists())
                    {
                        Toast.makeText(CheckOutActivity.this, "" + ds.child("name"), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(CheckOutActivity.this, "not working", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void saveToDatabase()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cart");
        databaseReference.removeValue();
        Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show();

        //--------------------------setting the total back to 0.0
            File file = new File(CheckOutActivity.this.getFilesDir(), "text");
            if (!file.exists()) {
                file.mkdir();
            }
            try {
                File gpxfile = new File(file, "sample");
                FileWriter writer = new FileWriter(gpxfile);
                writer.append("0");
                writer.flush();
                writer.close();
                Toast.makeText(CheckOutActivity.this, "Total set back to 0", Toast.LENGTH_LONG).show();
            }
            catch (Exception e) { }

        // --------------------Storing data into SharedPreferences as empty
        SharedPreferences sharedPreferences = getSharedPreferences("receiptIdPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("receiptId", "");
        myEdit.commit();

        Intent intent = new Intent(CheckOutActivity.this, SalesActivity.class);
        startActivity(intent);

    }

    private void work(double value, String cartTotal)
    {
        String input = inputCash.getText().toString();
        double inputNum = Double.valueOf(input) + value;
        inputCash.setText(String.valueOf(inputNum));

        double cash = Double.valueOf(inputCash.getText().toString()) - Double.valueOf(cartTotal);

        if (cash < 0)
        {
            TotalChange.setText("Short by R" + String.valueOf(cash));
            TotalChange.setTextColor(Color.parseColor("#FF0000"));
        }
        else
        {
            TotalChange.setText("Return R" + String.valueOf(cash));
            TotalChange.setTextColor(Color.parseColor("#008000"));
        }
    }
}