package com.example.gameawardnomineelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            Intent intent = new Intent(MainActivity.this, GameListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAddButton();

        GameDataSource ds = new GameDataSource(this);
        ArrayList<Game> games;
        try{
            ds.open();
            games = ds.getGames();
            ds.close();

            RecyclerView gameList = findViewById(R.id.rvGames);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            gameList.setLayoutManager(layoutManager);
            GameAdapter gameAdapter = new GameAdapter(games);
            gameAdapter.setOnClickListener(onClickListener);
            gameList.setAdapter(gameAdapter);
        }
        catch (Exception e){
            Toast.makeText(this, "Error retrieving games", Toast.LENGTH_LONG).show();
        }
    }

    private void initAddButton(){
        Button addButton = findViewById(R.id.buttonAdd);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }


}
