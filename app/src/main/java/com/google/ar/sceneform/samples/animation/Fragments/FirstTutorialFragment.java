package com.google.ar.sceneform.samples.animation.Fragments;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.ar.sceneform.samples.animation.R;

public class FirstTutorialFragment extends Fragment {

private Button btnForward;
public FirstTutorialFragment() {

        }
@Nullable
@Override
public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first_tutorial, container, false);
        btnForward = (Button) rootView.findViewById(R.id.btnForward);
        return rootView;
        }

@Override
public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnForward.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager() ;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
        fragmentTransaction.replace(R.id.fragment_container , new SecondTutorialFragment())
        .addToBackStack( null ) ;
        fragmentTransaction.commit() ;
        }
        });
        }


        void onException(int id, Throwable throwable) {
                Toast toast = Toast.makeText(getContext(), "Something went wrong :) " + id, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
        }
}
