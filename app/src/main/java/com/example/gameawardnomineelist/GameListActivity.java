package com.example.gameawardnomineelist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class GameListActivity extends AppCompatActivity {

    private Game currentGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        currentGame = new Game();

        initSaveButton();
        initTextChangedEvents();
    }

    public void onResume(){
        super.onResume();
        Intent intent = getIntent();
        int position = intent.getIntExtra("position", -1);
        if(position!=-1){
            GameDataSource ds = new GameDataSource(this);
            try{
                ds.open();
                currentGame = ds.getGame(position+1);
                ds.close();
                EditText nameEdit = findViewById(R.id.editName);
                nameEdit.setText(currentGame.getGameName());
                EditText dateEdit = findViewById(R.id.editDate);
                dateEdit.setText(currentGame.getReleaseDate());
                EditText genreEdit = findViewById(R.id.editGenre);
                genreEdit.setText(currentGame.getGenre());
            } catch(Exception e){
                Toast.makeText(this, "error accessing game", Toast.LENGTH_LONG).show();
            }

        }
    }


    private void initTextChangedEvents(){
        EditText nameEdit = findViewById(R.id.editName);
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentGame.setGameName(nameEdit.getText().toString());
                currentGame.setGameID(-1);
            }
        });

        EditText dateEdit = findViewById(R.id.editDate);
        dateEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentGame.setReleaseDate(dateEdit.getText().toString());
            }
        });

        EditText genreEdit = findViewById(R.id.editGenre);
        genreEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                currentGame.setGenre(genreEdit.getText().toString());
            }
        });


    }

    private void initSaveButton(){
        Button saveButton = findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean wasSuccessful = false;
                GameDataSource ds = new GameDataSource(GameListActivity.this);
                try{
                    ds.open();
                    if(currentGame.getGameID()==-1){
                        wasSuccessful = ds.insertGame(currentGame);
                        if(wasSuccessful){
                            int newId = ds.getLastGameID();
                            currentGame.setGameID(newId);
                        }
                    }
                    else{
                        wasSuccessful = ds.updateGame(currentGame);
                    }
                    ds.close();
                }
                catch (Exception e){
                    wasSuccessful = false;
                }

                Intent intent = new Intent(GameListActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });
    }

}