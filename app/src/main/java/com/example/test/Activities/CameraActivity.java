package com.example.test.Activities;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.util.Size;
import android.view.Menu;
import android.view.View;
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

import com.example.test.Entities.Diagnostic;
import com.example.test.MyImageAnalyzer;
import com.example.test.R;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static android.view.View.GONE;

public class CameraActivity extends AppCompatActivity {

    private ListenableFuture cameraProviderFuture;
    private ExecutorService cameraExecutor;
    private PreviewView previewView;
    private MyImageAnalyzer analyzer;
    private ImageCapture imageCapture;
    private TextView textViewTimer;
    final Handler handler = new Handler();

    private int REQUEST_CODE_PERMISSIONS = 101;
    private String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};

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

     //   ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
      //          EXTERNAL_STORAGE_PERMISSION_CODE);
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

    public File getOutputDirectory() {

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
      //  File file = new File(folder, getResources().getString(R.string.directory));
      //  File folder = new File(String.valueOf(Arrays.stream(getExternalMediaDirs()).findFirst()), getResources().getString(R.string.directory));
        folder.mkdirs();
        return folder;

    }

    public void captureImage(ImageCapture imageCapture, ProcessCameraProvider processCameraProvider, Preview preview, CameraSelector cameraSelector, ImageAnalysis imageAnalysis){

        File outputDirectory = getOutputDirectory();
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(new File(
                        outputDirectory, System.currentTimeMillis() + ".jpg")).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {

                       // Diagnostic diagnostic = new Diagnostic();
                       // diagnostic.setImagePath(outputDirectory.getAbsolutePath());
               //         ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setTargetResolution(new Size(1200, 700))
                 //               .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
                  //      imageAnalysis.setAnalyzer(imageCapture, analyzer);

                       // processCameraProvider.unbindAll();
                       // processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture, imageAnalysis);

                      //  analyzer.poseDetection((ImageProxy) imageCapture);
                    //    analyzer.poseDetection(imageCapture);
                     //   List<PoseLandmark> all = analyzer.poseLandmarks;
                      //  Integer s = all.size();
                    //    s.toString();
                       // Log.i("SIZE!!! ", String.valueOf(s));
                       // addToDatabase();
                        //  startActivity(new Intent(CameraActivity.this, DiagnosticActivity.class));

                        Log.i("MERGEEE",":)");
               //         analyzeImage(imageCapture, processCameraProvider, preview, cameraSelector, imageAnalysis);
                     //   addToDatabase();
                        startActivity(new Intent(CameraActivity.this, DiagnosticActivity.class));

                       // Log.i("MERGEEE",":)");
                    }

                    @Override
                    public void onError(@NonNull @NotNull ImageCaptureException exception) {
                        Log.i("Failed to capture image",":)");
                    }
                }
        );

    }

    public void analyzeImage(ImageCapture imageCapture, ProcessCameraProvider processCameraProvider, Preview preview, CameraSelector cameraSelector, ImageAnalysis imageAnalysis){
        imageAnalysis.setAnalyzer(cameraExecutor, new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy image) {
               // int rotationDegrees = image.getImageInfo().getRotationDegrees();
                // insert your code here.


            }
        });



        processCameraProvider.bindToLifecycle((LifecycleOwner) this, cameraSelector, imageAnalysis, preview);
    }
    public void addToDatabase(){

    }
}

