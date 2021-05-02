package com.example.junggar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchHolder> {
    private String[] search_text1,search_text2;
    SearchHolder searchHolder;

    public SearchAdapter(String[] search_text1,String[] search_text2){
        this.search_text1=search_text1;
        this.search_text2=search_text2;
    }


    public class SearchHolder extends RecyclerView.ViewHolder {

        public TextView search_text1, search_text2;
        public SearchHolder(View view){
            super(view);
            this.search_text1=view.findViewById(R.id.text1_ex);
            this.search_text2=view.findViewById(R.id.text2_ex);
        }
    }


    @NonNull
    @Override
    public SearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View holderView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_holder_example,parent,false);
        searchHolder=new SearchHolder(holderView);

        return searchHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHolder searchHolder, int i) {
        searchHolder.search_text1.setText(this.search_text1[i]);
        searchHolder.search_text2.setText(this.search_text2[i]);

    }

    @Override
    public int getItemCount() {
        return search_text1.length;
    }


}
