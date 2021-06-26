package com.example.test.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.example.test.R;
//import com.google.ar.core.Anchor;
//import com.google.ar.core.ArCoreApk;
//import com.google.ar.sceneform.AnchorNode;
//import com.google.ar.sceneform.SkeletonNode;
//import com.google.ar.sceneform.animation.ModelAnimator;
//import com.google.ar.sceneform.rendering.AnimationData;
//import com.google.ar.sceneform.rendering.ModelRenderable;
//import com.google.ar.sceneform.ux.ArFragment;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.app.Fragment;

public class ScoliosisActivity extends AppCompatActivity {

/*

    private ModelAnimator modelAnimator;
    private int i = 0;
    private ArFragment arFragment;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = getSupportFragmentManager().findFragmentById(R.id.arFragment);
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
        if(modelAnimator != null && modelAnimator.isRunning()){
            modelAnimator.end();
        }

        int animationCount = modelRenderable.getAnimationDataCount();

        if(i == animationCount)
            i = 0;
        AnimationData animationData = modelRenderable.getAnimationData(i);
        modelAnimator = new ModelAnimator(animationData, modelRenderable);
        modelAnimator.start();
        i++;

    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable AR-related functionality on ARCore supported devices only.
        maybeEnableArButton();

    }

    void maybeEnableArButton() {
        ArCoreApk.Availability availability = ArCoreApk.getInstance().checkAvailability(this);
        if (availability.isTransient()) {
            // Continue to query availability at 5Hz while compatibility is checked in the background.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    maybeEnableArButton();
                }
            }, 200);
        }

    }

*/
}
