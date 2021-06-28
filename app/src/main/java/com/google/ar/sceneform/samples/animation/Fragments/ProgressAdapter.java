package com.google.ar.sceneform.samples.animation.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ar.sceneform.samples.animation.Entities.Diagnostic;
import com.google.ar.sceneform.samples.animation.OnItemClickListener;
import com.google.ar.sceneform.samples.animation.R;

import java.util.List;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder> {

    private List<Diagnostic> localDataSet;
    public static OnItemClickListener itemClickListener;

    public ProgressAdapter(
            List<Diagnostic> dataSet

    ) {

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
        //   private final TextView duration;
        private final ImageView movieImage;

        private final ConstraintLayout layout;
        private TextView dateView;

        public ProgressViewHolder(View view) {
            super(view);
            dateView = view.findViewById(R.id.movie_title);
            movieImage = view.findViewById(R.id.iv_picture);
            layout = view.findViewById(R.id.container);
        }

        public void bind(Diagnostic item) {
            dateView.setText(item.getDate().toString());

            Bitmap myBitmap = BitmapFactory.decodeFile(item.getImagePath());

            movieImage.setImageBitmap(myBitmap);


            // duration.setText(item.getDuration());
            // movieImage.setImageDrawable(ContextCompat.getDrawable(movieImage.getContext(), item.getImageId()));


        }
    }
}