package com.example.junggar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.SearchHolder> {
    private ArrayList<CommentModel> arrayList;


    public CommentAdapter(ArrayList<CommentModel> arrayList){
        this.arrayList=arrayList;
    }


    public class SearchHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView search_text1, search_text2;

        public SearchHolder(View view){
            super(view);
            this.imageView=view.findViewById(R.id.iv_image);
            this.search_text1=view.findViewById(R.id.tv_name);
            this.search_text2=view.findViewById(R.id.tv_comment);
        }
    }


    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_comment_recycler,parent,false);
        SearchHolder searchHolder=new SearchHolder(holderView);

        return searchHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SearchHolder searchHolder, final int i) {
        CommentModel currentItem = arrayList.get(i);
        searchHolder.imageView.setImageResource(currentItem.getImageResource());
        searchHolder.search_text1.setText(currentItem.getText1());
        searchHolder.search_text2.setText(currentItem.getText2());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
