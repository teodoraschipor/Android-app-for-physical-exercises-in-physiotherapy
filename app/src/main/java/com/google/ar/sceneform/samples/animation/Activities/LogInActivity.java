package com.google.ar.sceneform.samples.animation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.ar.sceneform.samples.animation.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.NotNull;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView registerUser, forgotPassword;
    private Button btnLogIn;
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerUser = findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        btnLogIn = findViewById(R.id.signIn);
        btnLogIn.setOnClickListener(this);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

        forgotPassword = findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.signIn:
                userLogIn();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogIn() {

        String email = editTextEmail.getText().toString().trim(); // trim because the user may provide extra spaces
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length is 6 characters!");
            editTextPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if(task.isSuccessful()){ // if the user has been logged in

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); // get the current logged in user
                    //check if the email is verified:
                    if(user.isEmailVerified()){
                        // redirect to menu
                        startActivity(new Intent(LogInActivity.this, MenuActivity.class));
                        finish();
                    } else{
                        Toast.makeText(LogInActivity.this, "Check your email to verify your account!", Toast.LENGTH_LONG).show();
                        user.sendEmailVerification();
                    }
                } else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(LogInActivity.this, "Failed to login! Please check your credentials!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void onException(int id, Throwable throwable) {
        Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
