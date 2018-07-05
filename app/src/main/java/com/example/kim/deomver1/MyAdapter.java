package com.example.kim.deomver1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    String TAG = "tiger";
    List<Chat> mChat;
    String stEmail;




    public static class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView mTextView;

        public ViewHolder(View itemView){
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.mTextView);
        }
    }


    public MyAdapter(List<Chat> mChat, String email) {
        this.mChat = mChat;
        this.stEmail = email;
    }

    @Override
    public int getItemViewType(int position) {
        if(mChat.get(position).getEmail().equals(stEmail)){
            return 1;
        }else{
            return 2;
        }
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        /*v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.my_text_view, parent, false);*/
        if (viewType == 1){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_text_view, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_text_view, parent, false);
        }


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mTextView.setText(mChat.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }
}
