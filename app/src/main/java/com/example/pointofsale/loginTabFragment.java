package com.example.pointofsale;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class loginTabFragment extends Fragment
{

    EditText email;
    EditText password;
    Button login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.login_tab_fragment, container, false);

        email = root.findViewById(R.id.userEmail);
        password = root.findViewById(R.id.userPassword);
        login = root.findViewById(R.id.loginBtn);

        email.setTranslationX(800);
        password.setTranslationX(800);
        login.setTranslationX(800);

        email.setAlpha(0);
        password.setAlpha(0);
        login.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        password.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        login.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), dashboard.class);
                startActivity(intent);
            }
        });

        return root;
    }
}
