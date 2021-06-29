package com.google.ar.sceneform.samples.animation.Activities;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.animation.Fragments.FirstTutorialFragment;
import com.google.ar.sceneform.samples.animation.R;

public class TutorialActivity extends AppCompatActivity {

protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        if (savedInstanceState == null) {

                getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, FirstTutorialFragment.class, null)
                .commit();
        }
        }

        void onException(int id, Throwable throwable) {
                Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
        }
}
