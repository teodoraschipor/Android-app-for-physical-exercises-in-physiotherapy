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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.sceneform.samples.animation.Entities.Angles;
import com.google.ar.sceneform.samples.animation.Entities.Diagnostic;
import com.google.ar.sceneform.samples.animation.Entities.DiagnosticHasAngles;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    private List<Double> anglesList = new ArrayList<>();

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

        displayDiagnostic();

        btnExercises = findViewById(R.id.btnExercises);
        btnExercises.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiagnosticActivity.this, ExercisesActivity.class)); }
        });
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

                        Matrix matrix = new Matrix();

                        matrix.postRotate(270);

                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(myBitmap, myBitmap.getWidth(), myBitmap.getHeight(), true);

                        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

                        imageView.setImageBitmap(rotatedBitmap);

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
                                reference.child("Angles").child(anglesId).addListenerForSingleValueEvent(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                        Iterable<DataSnapshot> children = snapshot.getChildren();
                                        for(DataSnapshot ds : children) {
                                            //String name = ds.child("id").getValue(String.class);


                                           // double kneeValgus = ds.child("kneeValgus").getValue(Double.class);
                                            try {
                                                anglesList.add(ds.getValue(Double.class));
                                            } catch(DatabaseException e) {
                                                Log.i("A TRECUT DE STRING","!!!!");
                                            }
                                        }
                                        angleKyphosis.setText("Angle Kyphosis: " + anglesList.get(0).toString());
                                        angleScoliosis.setText("Angle Scoliosis: " + anglesList.get(1).toString());
                                        angleLordosis.setText("Angle Lordosis: " + anglesList.get(2).toString());
                                        angleKneeValgus.setText("Angle Knee Valgus: " + anglesList.get(3).toString());
                                        angleKneeVarus.setText("Angle Knee Varus: " + anglesList.get(4).toString());



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
                                Toast.makeText(DiagnosticActivity.this, "Something went wrong. Please try again :)", Toast.LENGTH_LONG).show();
                            }
                        });


                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        System.out.println("The read failed: " + error.getCode());
                        Toast.makeText(DiagnosticActivity.this, "Something went wrong. Please try again :)", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                Toast.makeText(DiagnosticActivity.this, "Something went wrong. Please try again :)", Toast.LENGTH_LONG).show();
            }
        });
    }

    void onException(int id, Throwable throwable) {
        Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
