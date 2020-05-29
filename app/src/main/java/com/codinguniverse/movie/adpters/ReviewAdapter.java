package com.codinguniverse.movie.adpters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codinguniverse.movie.R;
import com.codinguniverse.movie.models.ReviewModel;

import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<ReviewModel> mReviewList;

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.bind(mReviewList.get(position).getAuthor(), mReviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if (mReviewList == null){
            return 0;
        }
        return mReviewList.size();
    }

    public void setReviewList(List<ReviewModel> mReviewModels){
        mReviewList = mReviewModels;
        notifyDataSetChanged();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
        TextView mAuthorName;
        TextView mContent;
        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            mAuthorName = itemView.findViewById(R.id.review_author);
            mContent = itemView.findViewById(R.id.review_content);
        }

        void bind(String name, String content){
            mAuthorName.setText(name);
            mContent.setText(content);
        }

    }
}
