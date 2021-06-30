package com.google.ar.sceneform.samples.animation.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.animation.Entities.Diagnostic;
import com.google.ar.sceneform.samples.animation.Entities.DiagnosticHasAngles;
import com.google.ar.sceneform.samples.animation.Entities.Diseases;
import com.google.ar.sceneform.samples.animation.Entities.UserIsDiagnosed;
import com.google.ar.sceneform.samples.animation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ExercisesActivity extends AppCompatActivity implements View.OnClickListener{

        private Button scoliosisBtn;
        private TextView diagnostic;
        private DatabaseReference reference;
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        private String diagnosticId;
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                //(Activity).setContentView(R.layout.activity_exercises);
                setContentView(R.layout.activity_exercises);

                scoliosisBtn = (Button) findViewById(R.id.scoliosisBtn);
                scoliosisBtn.setOnClickListener(this);

                diagnostic = findViewById(R.id.diagnostic);
                setDiagnostic(diagnostic);
        }

        @Override
        public void onClick(View v) {
                switch (v.getId()){
                case R.id.scoliosisBtn:
                startActivity(new Intent(this, ScoliosisActivity.class));
                break;
                }
        }

        public void setDiagnostic(TextView textView){

                String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                this.reference = database.getReference();

                // In "User is diagnosed". Looking for the last data that has the userId = currentUser (the last diagnostic of the current user)
                // and save it in diagnosticId
                this.reference.child("User is diagnosed").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                Iterable<DataSnapshot> children = snapshot.getChildren();
                                // verifies for all the elements
                                for (DataSnapshot child : children) {
                                        UserIsDiagnosed userIsDiagnosed = child.getValue(UserIsDiagnosed.class);
                                        child.getValue().toString();
                                        if (userIsDiagnosed.getUserId().equals(currentUser)) {
                                                // diagnosesId.add(userIsDiagnosed.getDiagnosticId());
                                                diagnosticId = userIsDiagnosed.getDiagnosticId();
                                                break;
                                        }
                                }

                                // In "Diagnostic". Looking for the diagnostic with the id diagnosticId
                                // and set the imageView to the image with the correct path and the textView with the date.
                                reference.child("Diagnostic").addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                                Iterable<DataSnapshot> children = snapshot.getChildren();
                                                for (DataSnapshot child : children) {
                                                        Diagnostic diagnostic = child.getValue(Diagnostic.class);

                                                        if (diagnosticId.equals(diagnostic.getId())) {
                                                                Diseases diseases = diagnostic.getDiseases();
                                                                diseases.getMax();
                                                                String grade = "";

                                                                if(diseases.maximum == 1){
                                                                        grade = "easy";
                                                                } else if(diseases.maximum == 2){
                                                                        grade = "medium";
                                                                } else if(diseases.maximum == 3){
                                                                        grade = "severe";
                                                                }
                                                                List<String> maxList = diseases.getMaxList();
                                                                String s = "";
                                                                for(int i = 0 ; i < maxList.size(); i++){
                                                                        s = s + maxList.get(i) + grade + "\n";
                                                                }
                                                                textView.setText(s);
                                                                break;
                                                        }

                                                        }
                                                }

                                                                        @Override
                                                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                                                                System.out.println("The read failed: " + error.getCode());
                                                                        }
                                                                });
                                                        }
                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                System.out.println("The read failed: " + error.getCode());
                        }
                });

        }
        void onException(int id, Throwable throwable) {
                Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
        }

}