package com.example.gameawardnomineelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameAdapter extends RecyclerView.Adapter {
    private ArrayList<Game> gameData;
    private View.OnClickListener onClickListener;

    public class GameViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewDate;
        public TextView textViewGenre;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewGenre = itemView.findViewById(R.id.textViewGenre);

            itemView.setTag(this);
            itemView.setOnClickListener(onClickListener);
        }

        public TextView getNameTextView(){
            return textViewName;
        }

        public TextView getDateTextView() { return textViewDate; }

        public TextView getGenreTextView() { return textViewGenre; }

    }

    public void setOnClickListener(View.OnClickListener listener){
        onClickListener = listener;
    }

    public GameAdapter(ArrayList<Game> arrayList){
        gameData = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_item_view, parent, false);

        return new GameViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GameViewHolder cvh = (GameViewHolder) holder;
        cvh.getNameTextView().setText(gameData.get(position).getGameName());
        cvh.getDateTextView().setText(gameData.get(position).getReleaseDate());
        cvh.getGenreTextView().setText(gameData.get(position).getGenre());
    }

    @Override
    public int getItemCount() {
        return gameData.size();
    }

}
