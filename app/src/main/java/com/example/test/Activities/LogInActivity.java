package com.example.test.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser;
    private Button btnLogIn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        btnLogIn = findViewById(R.id.signIn);
        btnLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                startActivity(new Intent(this, RegisterUserActivity.class));
                break;
            case R.id.signIn:
                startActivity(new Intent(this, MenuActivity.class));
                break;
        }
    }
}
