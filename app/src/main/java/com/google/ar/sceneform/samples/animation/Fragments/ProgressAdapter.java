package com.google.ar.sceneform.samples.animation.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ar.sceneform.samples.animation.Entities.Diagnostic;
import com.google.ar.sceneform.samples.animation.OnItemClickListener;
import com.google.ar.sceneform.samples.animation.R;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    private List<Diagnostic> localDataSet;

    public ProgressAdapter(List<Diagnostic> dataSet) {

        localDataSet = dataSet;
    }

    @Override
    public ProgressViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_progress, viewGroup, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgressViewHolder viewHolder, final int position) {
        viewHolder.bind(localDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;
        private final ConstraintLayout layout;
        private TextView dateView;
        private TextView angleScoliosis;
        private TextView angleKyphosis;
        private TextView angleLordosis;
        private TextView angleKneeValgus;
        private TextView angleKneeVarus;

        public ProgressViewHolder(View view) {
            super(view);
            dateView = view.findViewById(R.id.date);
            image = view.findViewById(R.id.image);
            layout = view.findViewById(R.id.container);
            angleScoliosis = view.findViewById(R.id.angleScoliosis);
            angleKyphosis = view.findViewById(R.id.angleKyphosis);
            angleLordosis = view.findViewById(R.id.angleLordosis);
            angleKneeValgus = view.findViewById(R.id.angleKneeValgus);
            angleKneeVarus = view.findViewById(R.id.angleKneeVarus);
        }

        public void bind(Diagnostic item) {

            dateView.setText(item.getDate().toString());

            Bitmap myBitmap = BitmapFactory.decodeFile(item.getImagePath());

            Matrix matrix = new Matrix();

            matrix.postRotate(270);

            Bitmap scaledBitmap = Bitmap.createScaledBitmap(myBitmap, myBitmap.getWidth(), myBitmap.getHeight(), true);

            Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

            image.setImageBitmap(rotatedBitmap);
        }
    }
}