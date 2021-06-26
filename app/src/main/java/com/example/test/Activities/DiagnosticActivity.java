package com.example.test.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Entities.Angles;
import com.example.test.Entities.Diagnostic;
import com.example.test.Entities.DiagnosticHasAngles;
import com.example.test.Entities.Exercises;
import com.example.test.Entities.UserIsDiagnosed;
import com.example.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiagnosticActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    //  private ImageView imageView;
 //   private TextView textView;
    private DatabaseReference reference;
 //   List<String> diagnosesId;
//    List<Diagnostic> diagnoses;
    private String diagnosticId;
    private String imagePath;
    private Date date;
    private ImageView imageView;
    private TextView dateView;
    private String anglesId;
   // List<Double> anglesList;
    private TextView angleScoliosis;
    private TextView angleKyphosis;
    private TextView angleLordosis;
    private TextView angleKneeValgus;
    private TextView angleKneeVarus;
    private Button btnExercises;
   // private List<Double> anglesList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diagnostic);
        // imageView.findViewById(R.id.image);
        imageView = (ImageView) findViewById(R.id.imageView);
        dateView = (TextView) findViewById(R.id.date);
        angleScoliosis = (TextView) findViewById(R.id.angleScoliosis);
        angleKyphosis = (TextView) findViewById(R.id.angleKyphosis);
        angleLordosis = (TextView) findViewById(R.id.angleLordosis);
        angleKneeValgus = (TextView) findViewById(R.id.angleKneeValgus);
        angleKneeVarus = (TextView) findViewById(R.id.angleKneeVarus);
        btnExercises = findViewById(R.id.btnExercises);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnosticActivity.this, ExercisesActivity.class)); }
        });
        displayDiagnostic();
        //imageView.setImageURI();
    }

    public void displayDiagnostic() {
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
                                // diagnoses.add(diagnostic);
                                imagePath = diagnostic.getImagePath();
                                date = diagnostic.getDate();
                            }
                        }
                        // imageView.findViewById(R.id.imageView);
                        // imageView.setImageURI(diagnoses.get(0).getImageUri());
                      //  File imgFile = new File(imagePath);

                        //if (imgFile.exists()) {

                            Bitmap myBitmap = BitmapFactory.decodeFile(imagePath);

                            imageView.setImageBitmap(myBitmap);

                            dateView.setText(date.toString());

                     //   }

                        // In "Diagnostic has Angles". Looking for the diagnosticId = diagnosticId found
                        // and save the correct anglesId
                        reference.child("Diagnostic has Angles").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                Iterable<DataSnapshot> children = snapshot.getChildren();
                                for (DataSnapshot child : children) {
                                    DiagnosticHasAngles diagnosticHasAngles = child.getValue(DiagnosticHasAngles.class);

                                    if (diagnosticId.equals(diagnosticHasAngles.getDiagnosticId())) {
                                        anglesId = diagnosticHasAngles.getAnglesId();
                                    }
                                }

                                // In "Angles". Looking for the angles with the id anglesId
                                // and add each angle to the anglesList
                                reference.child("Angles").addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                        Iterable<DataSnapshot> children = snapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            Angles angles = child.getValue(Angles.class);

                                        //    anglesList.add(angles);
                                         /*   if (anglesId.equals(angles.getId())) {

                                                angleScoliosis.setText("Scoliosis angle: " + angles.getScoliosis());
                                                angleKyphosis.setText("Kyphosis angle: " + angles.getKyphosis());
                                                angleLordosis.setText("Lordosis angle: " + angles.getLordosis());
                                                angleKneeValgus.setText("Knee valgus angle: " + angles.getKneeValgus());
                                                angleKneeVarus.setText("Knee varus angle: " + angles.getKneeVarus());

                                                anglesList.add(angles.getScoliosis());
                                                anglesList.add(angles.getKyphosis());
                                                anglesList.add(angles.getLordosis());
                                                anglesList.add(angles.getKneeValgus());
                                                anglesList.add(angles.getKneeVarus());
                                            }*/
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
}
