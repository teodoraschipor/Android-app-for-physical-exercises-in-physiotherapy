package com.example.test.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.R;

import static android.os.Build.VERSION_CODES.R;

public class ExercisesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R);

        // Initial fragment:
       // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiagnosesFragment()).commit();
    }
}
