package com.google.ar.sceneform.samples.animation.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Camera;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.Sun;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.samples.animation.R;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.ArrayList;
import java.util.List;

public class ScoliosisActivity extends AppCompatActivity {

    private ModelAnimator modelAnimator;
    private int i;
    private ArFragment arFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoliosis);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            createModel(hitResult, arFragment);
        });
    }


    private void createModel(HitResult hitResult, ArFragment arFragment) {

        ModelRenderable.builder().setSource(this, Uri.parse("firstEx_scoliosis_EnableAllFromRainSettings_000001.sfb"))
                .build().thenAccept(modelRenderable -> {

                    Anchor anchor = hitResult.createAnchor();
            AnchorNode anchorNode = new AnchorNode(anchor);

            SkeletonNode skeletonNode = new SkeletonNode();
            skeletonNode.setParent(anchorNode);
            skeletonNode.setRenderable(modelRenderable);

            arFragment.getArSceneView().getScene().addChild(anchorNode);

            Button button = findViewById(R.id.button);

            button.setOnClickListener(view -> animateModel(anchorNode, skeletonNode));
        });
    }

    private void animateModel( AnchorNode anchorNode, SkeletonNode skeletonNodeInitial) {
        SkeletonNode skeletonNode2 = new SkeletonNode();

       // for(i = 2; i < 10; i ++){

                ModelRenderable.builder().setSource(this, Uri.parse("firstEx_scoliosis_EnableAllFromRainSettings_000010.sfb"))
                .build().thenAccept(modelR -> {

                  //  if(i == 2){
                     //   anchorNode.removeChild(skeletonNodeInitial);
                  //  } else{
                        anchorNode.removeChild(skeletonNodeInitial);
                   // }
                    skeletonNode2.setParent(anchorNode);
                    skeletonNode2.setRenderable(modelR);

                    arFragment.getArSceneView().getScene().addChild(anchorNode);
                });
                animateModel(anchorNode, skeletonNode2);
   // }
}

        /*for(i = 2; i < 10; i++){

            ModelRenderable.builder().setSource(this, Uri.parse("firstEx_scoliosis_EnableAllFromRainSettings_00000" + i + "1.sfb"))
                    .build().thenAccept(modelR -> {
                arFragment.getArSceneView().getScene().removeChild(skeletonNode);
                SkeletonNode skeletonNode2 = new SkeletonNode();
                skeletonNode2.setParent(anchorNode);
                skeletonNode2.setRenderable(modelR);

                arFragment.getArSceneView().getScene().addChild(anchorNode);

            });
        }

        /*
        if (modelAnimator != null && modelAnimator.isRunning()) {
            modelAnimator.end();
        }

        int animationCount = modelRenderable.getAnimationDataCount();

        if (i == animationCount)
            i = 0;
        AnimationData animationData = modelRenderable.getAnimationData(i);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
        i++;*/
        void onException(int id, Throwable throwable) {
            Toast toast = Toast.makeText(this, "Something went wrong :) " + id, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }


    //PENTRU FISIERE .FBX:
/*
public class ScoliosisActivity extends AppCompatActivity {

    private ModelAnimator modelAnimator;
    private int i = 0;
    private ArFragment arFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoliosis);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {

            createModel(hitResult.createAnchor(), arFragment);
        });
    }


    private void createModel(Anchor anchor, ArFragment arFragment) {

        ModelRenderable.builder().setSource(this, Uri.parse("58_Movement_LODa_r01.sfb"))
                .build().thenAccept(modelRenderable -> {

            AnchorNode anchorNode = new AnchorNode(anchor);

            SkeletonNode skeletonNode = new SkeletonNode();
            skeletonNode.setParent(anchorNode);
            skeletonNode.setRenderable(modelRenderable);

            arFragment.getArSceneView().getScene().addChild(anchorNode);

            Button button = findViewById(R.id.button);

            button.setOnClickListener(view -> animateModel(modelRenderable));
        });
    }

    private void animateModel(ModelRenderable modelRenderable) {
        if (modelAnimator != null && modelAnimator.isRunning()) {
            modelAnimator.end();
        }

        int animationCount = modelRenderable.getAnimationDataCount();

        if (i == animationCount)
            i = 0;
        AnimationData animationData = modelRenderable.getAnimationData(i);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
        i++;

    }
}
*/