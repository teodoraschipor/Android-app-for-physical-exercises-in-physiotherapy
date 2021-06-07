package com.example.test.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DiagnosticAdapter extends RecyclerView.Adapter<DiagnosticAdapter.DiagnosticViewHolder> {

    private List<String> dataSet;

    public DiagnosticAdapter(List<String> dataSet){
        this.dataSet = dataSet;
    }
    @NotNull
    @Override
    public DiagnosticViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_diagnostic, parent, false);
        return new DiagnosticViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DiagnosticViewHolder holder, int position) {
        holder.bind(dataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class DiagnosticViewHolder extends RecyclerView.ViewHolder{

        public DiagnosticViewHolder(View view){
            super(view);
        }
        public void bind(String item){

        }
    }
}
