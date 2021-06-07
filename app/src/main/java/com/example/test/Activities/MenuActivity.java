package com.example.test.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener{
    private Button cameraButton;
    private Button logInButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        cameraButton = findViewById(R.id.btnCamera);
        cameraButton.setOnClickListener((View.OnClickListener) this);

        logInButton = findViewById(R.id.btnLogIn);
        logInButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnCamera:
                startActivity(new Intent(this, CameraActivity.class));
                break;
            case R.id.btnLogIn:
                startActivity(new Intent(this, LogInActivity.class));
                break;
        }
    }
}
