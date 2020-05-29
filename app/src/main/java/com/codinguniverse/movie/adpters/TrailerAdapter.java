package com.codinguniverse.movie.adpters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codinguniverse.movie.R;
import com.codinguniverse.movie.models.TrailersModel;
import com.codinguniverse.movie.utils.YouTubeThumbnailPath;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>{

    private List<TrailersModel> mTrailersList;

    private TrailerClickHandler mClickHandler;


    /* String to check if the video is from youtube site
        so that only you trailer will be stored
        in mTrailersList
     */
    private static final String YOUTUBE_TYPE = "YouTube";

    // Constructor
    public TrailerAdapter(TrailerClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /*
        Interface to handle click on trailer view
     */
    public interface TrailerClickHandler{
        void onTrailerClickHandle(String videoId);
    }


    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.bind(mTrailersList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mTrailersList == null){
            return 0;
        }
        return mTrailersList.size();
    }

    public void setTrailersList(List<TrailersModel> trailersModels){
        if (trailersModels == null){
            mTrailersList = trailersModels;
        }else {
            mTrailersList = new ArrayList<>();
            for (TrailersModel trailer : trailersModels){
                if (trailer.getSite().equals(YOUTUBE_TYPE)){
                    mTrailersList.add(trailer);
                }
            }
        }
        notifyDataSetChanged();
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView trailerThumbnail;
        TextView trailerName;
        TrailerViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerThumbnail = itemView.findViewById(R.id.trailer_image);
            trailerName = itemView.findViewById(R.id.trailer_name);

            itemView.setOnClickListener(this);
        }

        void bind(TrailersModel trailersModel){
            String key = trailersModel.getKey();
            String name = trailersModel.getName();
            trailerName.setText(name);
            String url = YouTubeThumbnailPath.buildThumbNailPath(key);
            Picasso.get().load(url).into(trailerThumbnail);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onTrailerClickHandle(mTrailersList.get(getAdapterPosition()).getKey());
        }
    }
}
