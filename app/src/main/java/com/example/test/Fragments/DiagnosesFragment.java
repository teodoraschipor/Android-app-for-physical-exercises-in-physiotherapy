package com.example.test.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import org.jetbrains.annotations.NotNull;

public class DiagnosesFragment extends Fragment {

    public DiagnosesFragment(){
        super(R.layout.fragment_diagnoses);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // initializeCategoryList();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
       // CategoryAdapter categoryAdapter = new CategoryAdapter(category_list, this);
       // recyclerView.setAdapter(categoryAdapter);
    }
}
