package com.example.gameawardnomineelist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class GameDataSource {
    private SQLiteDatabase database;
    private GameDBHelper dbHelper;

    public GameDataSource(Context context){

        dbHelper = new GameDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){

        dbHelper.close();
    }

    public boolean insertGame(Game c){
        boolean didSucceed = false;
        try{
            ContentValues values = new ContentValues();
            values.put("gamename", c.getGameName());
            values.put("releasedate", c.getReleaseDate());
            values.put("genre", c.getGenre());

            didSucceed = database.insert("game", null, values)>0;
        } catch(Exception e){

        }
        return didSucceed;
    }

    public boolean updateGame(Game c){
        boolean didSucceed = false;
        try{
            ContentValues values = new ContentValues();
            values.put("gamename", c.getGameName());
            values.put("releasedate", c.getReleaseDate());
            values.put("genre", c.getGenre());

            Long id = (long) c.getGameID();
            didSucceed = database.update("game", values, "_id="+id, null)>0;
        } catch(Exception e){

        }
        return didSucceed;
    }

    public int getLastGameID(){
        int lastID = -1;
        try{
            String query = "Select MAX(_id) from game";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();

        }
        catch (Exception e){


        }
        return lastID;
    }

    public ArrayList<String> getGameNames(){
        ArrayList<String> names = new ArrayList<>();
        try{
            String query = "Select gamename from game";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                names.add(cursor.getString(0));
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){

        }
        return names;
    }

    public ArrayList<Game> getGames(){
        ArrayList<Game> games = new ArrayList<>();
        try{
            String query = "Select * from game";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Game g = new Game();
                g.setGameName(cursor.getString(1));
                g.setReleaseDate(cursor.getString(2));
                g.setGenre(cursor.getString(3));
                games.add(g);
                cursor.moveToNext();
            }
            cursor.close();
        }
        catch(Exception e){

        }
        return games;
    }


    public Game getGame(int id){
        Game g = new Game();
        try{
            String query = "Select * from game where _id="+id;
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            g.setGameID(id);
            g.setGameName(cursor.getString(1));
            g.setReleaseDate(cursor.getString(2));
            g.setGenre(cursor.getString(3));
            cursor.close();
        }
        catch(Exception e){

        }
        return g;
    }
}
