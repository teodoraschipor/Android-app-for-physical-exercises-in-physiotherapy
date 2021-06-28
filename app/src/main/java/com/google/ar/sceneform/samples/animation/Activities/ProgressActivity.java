package com.google.ar.sceneform.samples.animation.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.animation.Fragments.ProgressFragment;
import com.google.ar.sceneform.samples.animation.R;

public class ProgressActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container, ProgressFragment.class, null)
                    .commit();
        }
    }
}
