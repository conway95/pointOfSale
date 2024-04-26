package com.example.pointofsale;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



//        try
//        {
//            File dir = getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS);
//            File file = new File(dir,"details.txt");
//
//            FileReader reader = new FileReader(file);
//            int s;
//            StringBuilder builder = new StringBuilder();
//            while ((s = (reader.read())) != 0)
//            {
//                builder.append((char) s);
//            }
//
//            Toast.makeText(this, "date " + builder.toString(), Toast.LENGTH_SHORT).show();
//
//        }
//        catch (Exception exception)
//        {
//            return;
//        }

    }
}