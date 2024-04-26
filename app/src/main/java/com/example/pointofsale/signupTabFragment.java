package com.example.pointofsale;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signupTabFragment extends Fragment
{
    EditText Email;
    EditText Password;
    EditText BusinessName;
    EditText PasswordConfirm;
    EditText PhoneNumber;
    EditText Name;
    Button register;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_tab_fragment, container, false);

        register = root.findViewById(R.id.registerBtn);
        Email = root.findViewById(R.id.registerUserEmail);
        Password = root.findViewById(R.id.registerPassword);
        PasswordConfirm = root.findViewById(R.id.registerConfirmPassword);
        BusinessName = root.findViewById(R.id.registerBusinessName);
        Name = root.findViewById(R.id.registerUserName);
        PhoneNumber = root.findViewById(R.id.registerPhone);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), dashboard.class);
//                startActivity(intent);

                noMissingText();
            }
        });

        return root;
    }

    private void noMissingText()
    {
        String businessName = BusinessName.getText().toString();
        String name = Name.getText().toString();
        String email = Email.getText().toString();
        String pass = Password.getText().toString();
        String number = PhoneNumber.getText().toString();


            if(BusinessName.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill Business Name", Toast.LENGTH_SHORT).show();
            }
            if(Name.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill Name", Toast.LENGTH_SHORT).show();
            }
            if(Email.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill Email", Toast.LENGTH_SHORT).show();
            }
            if(Password.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill Password", Toast.LENGTH_SHORT).show();
            }
            if (PasswordConfirm.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill Confirm Password", Toast.LENGTH_SHORT).show();
            }
            if(PhoneNumber.getText().toString().isEmpty())
            {
                Toast.makeText(getActivity(), "Please fill Phone Number", Toast.LENGTH_SHORT).show();
            }

            if (Password.getText().toString().equals(PasswordConfirm.getText().toString()))
            {
                addUserDetailsToDb(businessName, name, email,pass, number);
            }
            else
            {
                Toast.makeText(getActivity(), "Pass do not match", Toast.LENGTH_SHORT).show();
            }
    }

    private void addUserDetailsToDb(String businessName, String name, String email, String pass, String number)
    {
        HashMap<String, Object> userHashMap = new HashMap<>();

        userHashMap.put("businessName",businessName);
        userHashMap.put("name",name);
        userHashMap.put("email",email);
        userHashMap.put("password",pass);
        userHashMap.put("number",number);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");

        String key = usersRef.push().getKey();

        usersRef.child(key).setValue(userHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                BusinessName.getText().clear();
                Name.getText().clear();
                Email.getText().clear();
                Password.getText().clear();
                PasswordConfirm.getText().clear();
                PhoneNumber.getText().clear();

                Toast.makeText(getActivity(), "Registration Complete", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), dashboard.class);
                startActivity(intent);

            }
        });
    }

}
