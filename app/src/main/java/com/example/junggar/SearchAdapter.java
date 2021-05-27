package com.example.junggar;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private ArrayList<itemModel> arrayList;

    public SearchAdapter(ArrayList<itemModel> arrayList){
        this.arrayList=arrayList;
    }


    public class SearchHolder extends RecyclerView.ViewHolder {
        //public ImageView imageView;
        public TextView search_text1, search_text2, search_text3;

        public SearchHolder(View view){
            super(view);
            //this.imageView=view.findViewById(R.id.iv_image_search);
            this.search_text1=view.findViewById(R.id.tv_title);
            this.search_text2=view.findViewById(R.id.tv_content);
            this.search_text3=view.findViewById(R.id.tv_time);

        }
    }


    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.soo_yeon_activity_recyclertmp,parent,false);
        SearchHolder searchHolder=new SearchHolder(holderView);

        return searchHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder searchHolder, int i) {
        itemModel currentItem = arrayList.get(i);
        /*
        Glide.with(searchHolder.itemView)
                .load(arrayList.get(i).getImageResource())
                .into(searchHolder.imageView);

         */
        searchHolder.search_text1.setText(currentItem.getText1());
        searchHolder.search_text2.setText(currentItem.getText2());
        searchHolder.search_text3.setText(currentItem.getText3());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
