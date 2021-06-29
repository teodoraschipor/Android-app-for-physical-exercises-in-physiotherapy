package com.google.ar.sceneform.samples.animation.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraMetadata;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.ar.sceneform.samples.animation.Entities.Angles;
import com.google.ar.sceneform.samples.animation.Entities.Diagnostic;
import com.google.ar.sceneform.samples.animation.Entities.DiagnosticHasAngles;
import com.google.ar.sceneform.samples.animation.Entities.Diseases;
import com.google.ar.sceneform.samples.animation.Entities.UserIsDiagnosed;
import com.google.ar.sceneform.samples.animation.MyImageAnalyzer;
import com.google.ar.sceneform.samples.animation.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.mlkit.vision.pose.PoseLandmark;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static android.view.View.GONE;

public class CameraActivity extends AppCompatActivity {

private ListenableFuture cameraProviderFuture;
private ExecutorService cameraExecutor;
private PreviewView previewView;
private MyImageAnalyzer analyzer;
private TextView textViewTimer;
final Handler handler = new Handler();
private int REQUEST_CODE_PERMISSIONS = 101;
private String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
final FirebaseDatabase database = FirebaseDatabase.getInstance();

@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);


        previewView = findViewById(R.id.previewView);
        this.getWindow().setFlags(1024, 1024);

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        textViewTimer = findViewById(R.id.timer);

        analyzer = new MyImageAnalyzer(getSupportFragmentManager());


        cameraProviderFuture.addListener(new Runnable() {
@Override
public void run() {

        if (permissions()) {
        ProcessCameraProvider processCameraProvider = null;
        try {
        processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
        } catch (ExecutionException e) {
        e.printStackTrace();
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        bindpreview(processCameraProvider);
        } else {
        ActivityCompat.requestPermissions(CameraActivity.this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

        }
        }, ContextCompat.getMainExecutor(this));
        }

public boolean permissions(){
        for (String permission : REQUIRED_PERMISSIONS) {
        if (ContextCompat.checkSelfPermission(CameraActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
        return false;
        }
        }
        return true;
        }
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ProcessCameraProvider processCameraProvider = null;
        try {
        processCameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();
        } catch (ExecutionException e) {
        e.printStackTrace();
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        bindpreview(processCameraProvider);
        }
private void bindpreview(ProcessCameraProvider processCameraProvider) {

        Preview preview = new Preview.Builder().build();
        CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_FRONT).build();
        preview.setSurfaceProvider(previewView.getSurfaceProvider());
        ImageCapture imageCapture = new ImageCapture.Builder().build();
        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1200, 700))
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        imageAnalysis.setAnalyzer(cameraExecutor, analyzer);
        processCameraProvider.unbindAll();
        processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

        long startTime = System.currentTimeMillis();
        // Timer:
        Runnable runnable = new Runnable() {
@Override
public void run() {
        long time = ((startTime - System.currentTimeMillis()) / 1000) + 5;
        textViewTimer.setText(Long.toString(time));
        handler.postDelayed(this, 1000);
        }
        };

        runnable.run();

        // After 5 seconds of timer, capture image
        handler.postDelayed(new Runnable() {
@Override
public void run() {
        handler.removeCallbacks(runnable); //stop next runnable execution
        textViewTimer.setVisibility(GONE);
        captureImage(imageCapture, processCameraProvider, preview, cameraSelector, imageAnalysis);

        }
        }, 5000);
        }

   /* public File getOutputDirectory() {

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        folder.mkdirs();
        return folder;

    }
*/
public void captureImage(ImageCapture imageCapture, ProcessCameraProvider processCameraProvider, Preview preview, CameraSelector cameraSelector, ImageAnalysis imageAnalysis){

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        folder.mkdirs();
        String photoName = "/" + System.currentTimeMillis() + ".jpg";
        String path = folder.getAbsolutePath() + photoName;
        ImageCapture.OutputFileOptions outputFileOptions =
        new ImageCapture.OutputFileOptions.Builder(new File(
        folder, photoName)).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
        new ImageCapture.OnImageSavedCallback() {
@Override
public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {

        //    Log.i("MERGEEE",":)");
        analyzeImage(imageCapture, processCameraProvider, preview, cameraSelector, imageAnalysis);
        List<PoseLandmark> allPoseLandmarks = analyzer.poseLandmarks;
        //Toast.makeText(CameraActivity.this, String.valueOf(allPoseLandmarks.get(0).getPosition3D().getX()), Toast.LENGTH_LONG).show();
        //   Uri uri = outputFileResults.getSavedUri();
        if(allPoseLandmarks.size() != 0){
                addToDatabase(path, allPoseLandmarks);
                startActivity(new Intent(CameraActivity.this, DiagnosticActivity.class));
                finish();
        } else{
                finish();
                Log.i("ACTIVITY"," DESTROYED");
                Toast.makeText(CameraActivity.this, "NO PERSON IDENTIFIED IN PHOTO. PLEASE TRY AGAIN :)", Toast.LENGTH_LONG).show();
        }
        }

@Override
public void onError(@NonNull @NotNull ImageCaptureException exception) {
        Log.i("Failed to capture image",":)");
        Toast.makeText(CameraActivity.this, "Something went wrong. Please try again :)", Toast.LENGTH_LONG).show();
        }
        }
        );

        }

public void analyzeImage(ImageCapture imageCapture, ProcessCameraProvider processCameraProvider, Preview preview, CameraSelector cameraSelector, ImageAnalysis imageAnalysis){
        imageAnalysis.setAnalyzer(cameraExecutor, new ImageAnalysis.Analyzer() {
@Override
public void analyze(@NonNull ImageProxy image) {
        analyzer.analyze(image);
        }
        });



        processCameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
        }
public void addToDatabase(String path, List<PoseLandmark> allPoseLandmarks){


        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference anglesRef = database.getReference("Angles");
        DatabaseReference pushedAnglestRef = anglesRef.push();
        String anglesId = pushedAnglestRef.getKey();
        Angles angles = new Angles();
        angles.setScoliosis(allPoseLandmarks);
        angles.setKyphosis(allPoseLandmarks);
        angles.setLordosis(allPoseLandmarks);
        angles.setKneeValgus(allPoseLandmarks);
        angles.setKneeVarus(allPoseLandmarks);
        angles.setId(anglesId);

        FirebaseDatabase.getInstance().getReference("Angles")
        .child(anglesId)
        .setValue(angles);

        DatabaseReference diagnosticRef = database.getReference("Diagnostic");
        DatabaseReference pushedDiagnosticRef = diagnosticRef.push();
        String diagnosticId = pushedDiagnosticRef.getKey();
        Diagnostic diagnostic = new Diagnostic();
        diagnostic.setImagePath(path);
        diagnostic.setDate(new Date(System.currentTimeMillis()));
        diagnostic.setId(diagnosticId);
        Diseases diseases = new Diseases();
        List<Integer> diseasesGradeList = diagnostic.diseasesGrade(angles);
        diseases.setKyphosis(diseasesGradeList.get(0));
        diseases.setScoliosis(diseasesGradeList.get(1));
        diseases.setLordosis(diseasesGradeList.get(2));
        diseases.setKneeValgus(diseasesGradeList.get(3));
        diseases.setKneeVarus(diseasesGradeList.get(4));
        diagnostic.setDiseases(diseases);
       // String diseasesId = pushedDiagnosticRef.push().getKey();
      //  FirebaseDatabase.getInstance().getReference("Diagnostic")
        //        .child(diseasesId)
         //       .setValue(diseasesGradeList);


        FirebaseDatabase.getInstance().getReference("Diagnostic")
        .child(diagnosticId)
        .setValue(diagnostic);


        DatabaseReference diagnosticAnglesRef = database.getReference("Diagnostic has Angles");
        DatabaseReference pushedDiagnosticAnglesRef = diagnosticAnglesRef.push();
        String diagnosticAnglesId = pushedDiagnosticAnglesRef.getKey();
        DiagnosticHasAngles diagnosticHasAngles = new DiagnosticHasAngles(anglesId, diagnosticId);


        FirebaseDatabase.getInstance().getReference("Diagnostic has Angles")
        .child(diagnosticAnglesId)
        .setValue(diagnosticHasAngles);


        DatabaseReference userDiagnosticRef = database.getReference("User is diagnosed");
        DatabaseReference pushedUserDiagnosticRef = userDiagnosticRef.push();
        String userDiagnosedUserId = pushedUserDiagnosticRef.getKey();
        UserIsDiagnosed userIsDiagnosed = new UserIsDiagnosed(userId, diagnosticId);



        FirebaseDatabase.getInstance().getReference("User is diagnosed")
        .child(userDiagnosedUserId)
        .setValue(userIsDiagnosed);
        }

        void onException(int id, Throwable throwable) {
                Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
        }
}

