package com.google.ar.sceneform.samples.animation.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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

        void onException(int id, Throwable throwable) {
                Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
        }
}