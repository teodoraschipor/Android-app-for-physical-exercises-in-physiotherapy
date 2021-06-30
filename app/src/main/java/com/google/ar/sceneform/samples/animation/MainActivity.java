/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.ar.sceneform.samples.animation;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.samples.animation.Activities.LogInActivity;
import com.google.ar.sceneform.samples.animation.Activities.MenuActivity;
import com.google.ar.sceneform.samples.animation.Activities.TutorialActivity;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

/** Demonstrates playing animated FBX models. */
public class MainActivity extends AppCompatActivity {
  Timer timer;
  FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


       /* FileInputStream serviceAccount =
                null;
        try {
            serviceAccount = new FileInputStream("path/to/serviceAccountKey.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);*/

    timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if(firebaseAuth.getCurrentUser() != null){
          Intent intent = new Intent(MainActivity.this, MenuActivity.class);
          startActivity(intent);
          finish();
        }
        else{
          Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
          startActivity(intent);
          finish();
        }

      }
    }, 3000);
  }
}
