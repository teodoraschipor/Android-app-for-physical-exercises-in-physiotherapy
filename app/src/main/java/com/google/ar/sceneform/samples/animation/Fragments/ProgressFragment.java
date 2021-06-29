package com.google.ar.sceneform.samples.animation.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ar.sceneform.samples.animation.Entities.Diagnostic;
import com.google.ar.sceneform.samples.animation.Entities.UserIsDiagnosed;
import com.google.ar.sceneform.samples.animation.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment {
    public static List<Diagnostic> finalDiagnoses = new ArrayList<>();
    private List<Diagnostic> diagnosesList = new ArrayList<>();
    private List<String> diagnosesIdList = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference reference;
    //   private String diagnosticId;
    private String imagePath;
    private Date date;

    private String anglesId;

    public ProgressFragment() {
        super(R.layout.fragment_progress);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeDiagnosesList(view);
    }

    private void initializeDiagnosesList(View view) {

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
                        diagnosesIdList.add(userIsDiagnosed.getDiagnosticId());
                        //  diagnosticId = userIsDiagnosed.getDiagnosticId();
                    }
                }

                // In "Diagnostic". Looking for the diagnostic with the id diagnosticId
                // and set the imageView to the image with the correct path and the textView with the date.
                reference.child("Diagnostic").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        //for (String diagnosticId : diagnosesIdList) {

                        for (DataSnapshot child : children) {
                            Diagnostic diagnostic = child.getValue(Diagnostic.class);
                            diagnosesList.add(diagnostic);
                            // if (diagnosticId.equals(diagnostic.getId())) {
                            // diagnoses.add(diagnostic);
                            //imagePath = diagnostic.getImagePath();
                            //date = diagnostic.getDate();
                            //    diagnosesList.add(diagnostic);
                            //  }
                        }
                        // }
                        for(String diagnosticId : diagnosesIdList){
                            for(Diagnostic diagnostic : diagnosesList){
                                if(diagnosticId.equals(diagnostic.getId())){
                                    finalDiagnoses.add(diagnostic);
                                }
                            }
                        }
                        ProgressAdapter progressAdapter = new ProgressAdapter(finalDiagnoses);
                        RecyclerView rv = view.findViewById(R.id.recycler_view);
                        rv.setAdapter(progressAdapter);
                        // In "Diagnostic has Angles". Looking for the diagnosticId = diagnosticId found
                        // and save the correct anglesId
                       /* reference.child("Diagnostic has Angles").addListenerForSingleValueEvent(new ValueEventListener() {
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
                             /*   reference.child("Angles").addListenerForSingleValueEvent(new ValueEventListener() {
                                    List<Double> anglesList = new ArrayList<>();
                                    @Override
                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                        Iterable<DataSnapshot> children = snapshot.getChildren();
                                        for (DataSnapshot child : children) {
                                            Angles angles = child.getValue(Angles.class);

                                        //    anglesList.add(angles);
                                            if (anglesId.equals(angles.getId())) {

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
                                            }
                                        }



                                    }


                                    @Override
                                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                                        System.out.println("The read failed: " + error.getCode());
                                    }
                                });*/
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        System.out.println("The read failed: " + error.getCode());
                        Toast toast = Toast.makeText(getContext(), "Something went wrong :) ", Toast.LENGTH_LONG);
                    }
                });


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                Toast toast = Toast.makeText(getContext(), "Something went wrong :) ", Toast.LENGTH_LONG);
            }
        });

    }
    void onException(int id, Throwable throwable) {
        Toast toast = Toast.makeText(getContext(), "Something went wrong :) " + id, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

}



/*
    @Override
    public void onItemClick(MovieModel item) {
//        Toast.makeText(getContext(), item.getTitle(), Toast.LENGTH_LONG).show();

        Bundle bundle = new Bundle();
        bundle.putString(MOVIE_TITLE, item.getTitle());
        bundle.putParcelable(MOVIE,  item);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        SecondFragment fragment = new SecondFragment();
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragment_container, fragment)
                .addToBackStack(null);

        fragmentTransaction.commit();
    }*/

