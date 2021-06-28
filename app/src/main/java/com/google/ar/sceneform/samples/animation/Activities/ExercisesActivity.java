package com.google.ar.sceneform.samples.animation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.animation.R;

public class ExercisesActivity extends AppCompatActivity implements View.OnClickListener{

private Button scoliosisBtn;
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //(Activity).setContentView(R.layout.activity_exercises);
        setContentView(R.layout.activity_exercises);

        scoliosisBtn = (Button) findViewById(R.id.scoliosisBtn);
        scoliosisBtn.setOnClickListener(this);
        }

@Override
public void onClick(View v) {
        switch (v.getId()){
        case R.id.scoliosisBtn:
        startActivity(new Intent(this, ScoliosisActivity.class));
        break;
        }
        }
        }