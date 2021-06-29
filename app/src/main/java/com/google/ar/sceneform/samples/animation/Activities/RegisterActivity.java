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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.ar.sceneform.samples.animation.Entities.User;
import com.google.ar.sceneform.samples.animation.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private TextView banner;
    private EditText editTextFullName, editTextAge, editTextEmail, editTextPassword;
    private Button registerUserBtn;
    private ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        banner = findViewById(R.id.banner);
        banner.setOnClickListener(this);

        registerUserBtn = findViewById(R.id.registerUser);
        registerUserBtn.setOnClickListener(this);

        editTextFullName = findViewById(R.id.fullName);

        editTextAge = findViewById(R.id.age);

        editTextEmail = findViewById(R.id.email);

        editTextPassword = findViewById(R.id.password);

        progressBar = findViewById(R.id.progressBar);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.banner:
                startActivity(new Intent(this, MenuActivity.class));
                break;
            case R.id.registerUser:
                registerUserBtn();
                break;
        }
    }

    private void registerUserBtn() {

        String fullName = editTextFullName.getText().toString().trim();
        String age = editTextAge.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // VALIDATE INPUTS:
        if(fullName.isEmpty()){
            editTextFullName.setError("Full name is required!");
            editTextFullName.requestFocus();
            return;
        }

        if(age.isEmpty()){
            editTextAge.setError("Age is required!");
            editTextAge.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextEmail.setError("Email address is required!");
            editTextEmail.requestFocus();
            return;
        }

        // EMAIL VALIDATION:
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide a valid email!");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is requires!");
            editTextPassword.requestFocus();
            return;
        }

        // VERIFY IF THE PASSWORD IS VALID:
        if(password.length() < 6){
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {

                if(task.isSuccessful()){ // if the user has registered successfully
                    User user = new User(fullName, age, email);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                                // Redirect to login layout!
                                startActivity(new Intent(RegisterActivity.this, LogInActivity.class));
                                finish();

                            } else {
                                Toast.makeText(RegisterActivity.this, "Failed to register! Try again!", Toast.LENGTH_LONG);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }); // FirebaseAuth.getInstance().getCurrentUser().getUid() -- this will return the id of the registered user and set it to user

                } else{
                    Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_LONG);
                    progressBar.setVisibility(View.GONE);
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
