package com.example.test.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Fragments.DiagnosesFragment;
import com.example.test.R;


public class ExercisesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        // Initial fragment:
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DiagnosesFragment()).commit();
    }
}
