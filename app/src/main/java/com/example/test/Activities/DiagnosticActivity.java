package com.example.test.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.Entities.Diagnostic;
import com.example.test.Entities.UserIsDiagnosed;
import com.example.test.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

public class DiagnosticActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private ImageView imageView;
    private TextView textView;
    private DatabaseReference reference;
    List<String> diagnosesId;
    List<Diagnostic> diagnoses;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diagnostic);
       // imageView.findViewById(R.id.image);
        displayDiagnostic();
        //imageView.setImageURI();
    }

    public void displayDiagnostic() {
        String currentUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        this.reference = database.getReference();
        this.reference.child("User is diagnosed").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                Iterable<DataSnapshot> children = snapshot.getChildren();
                // verifies for all the elements
                for (DataSnapshot child : children) {
                    UserIsDiagnosed userIsDiagnosed = child.getValue(UserIsDiagnosed.class);
                    if (userIsDiagnosed.getUserId().equals(currentUser)) {
                        diagnosesId.add(userIsDiagnosed.getDiagnosticId());
                    }
                }

                reference.child("Diagnostic").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        Iterable<DataSnapshot> children = snapshot.getChildren();
                        for (DataSnapshot child : children) {
                            Diagnostic diagnostic = child.getValue(Diagnostic.class);

                            if (diagnosesId.contains(diagnostic.getId())) {
                                diagnoses.add(diagnostic);
                            }
                        }
                       // imageView.findViewById(R.id.imageView);
                       // imageView.setImageURI(diagnoses.get(0).getImageUri());
                        File imgFile = new  File(diagnoses.get(diagnoses.size() - 1).getImagePath());

                        if(imgFile.exists()){

                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                            ImageView myImage = (ImageView) findViewById(R.id.imageView);

                            myImage.setImageBitmap(myBitmap);

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
            }
                @Override
                public void onCancelled (@NonNull @NotNull DatabaseError error){

                }
            });


    }
}
