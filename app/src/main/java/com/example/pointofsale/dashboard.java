package com.example.pointofsale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class dashboard extends AppCompatActivity {

    Calendar calendar;
    SimpleDateFormat simpleDateFormat, simpleTimeFormat;
    String Date;
    String Time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button charts = findViewById(R.id.ChartsBtn);
        Button history = findViewById(R.id.historyBtn);
        Button products = findViewById(R.id.ProductsBtn);
        Button sales = findViewById(R.id.dMakeSaleBtn);
        Button settings = findViewById(R.id.SettingsBtn);
        Button logout = findViewById(R.id.LogoutBtn);

        charts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboard.this, ChartsActivity.class);
                startActivity(intent);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, StockTakingActivity.class);
                startActivity(intent);
            }
        });

        products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, ProductsActivity.class);
                startActivity(intent);
            }
        });

        sales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, SalesActivity.class);
                intent.putExtra("customerNumber", Date + Time);
                startActivity(intent);


            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(dashboard.this, SettingsActivity.class);
                startActivity(intent);

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}