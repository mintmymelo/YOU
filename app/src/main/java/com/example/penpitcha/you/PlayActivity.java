package com.example.penpitcha.you;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;



public class PlayActivity extends ActionBarActivity {

    GameDBHelper helper;
    SimpleCursorAdapter adapter;
    Cursor cursor;
    String playName;
    String playLevel;
    SQLiteDatabase db;
    int score = 50;
    int n = 1;

    String word;
    String question;

    TextView tvRV;
    TextView tvQ;
    TextView tvSV;

    static String newQ;
    int newScore = 50;

    static int temp = 0;

    ContentValues r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        Intent it = this.getIntent();

        playName = it.getStringExtra("playerName");
        playLevel = it.getStringExtra("playerLevel");

        tvRV = (TextView)findViewById(R.id.tvRoundValue);
        tvRV.setText(Integer.toString(n));

        helper = new GameDBHelper(this);

        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT word, question FROM vocabulary WHERE level=?;", new String[]{playLevel});

        cursor.moveToFirst(); //get the first row

        word = cursor.getString(cursor.getColumnIndex("word"));
        question = cursor.getString(cursor.getColumnIndex("question"));

        tvQ = (TextView)findViewById(R.id.tvQuestion);
        tvQ.setText(question);
        tvSV = (TextView)findViewById(R.id.tvScoreValue);
        tvSV.setText(Integer.toString(newScore));

    }

    public void setRound(){
        if(cursor.isLast()){

            db = helper.getWritableDatabase();
            ContentValues r = new ContentValues();
            r.put("name", playName);
            r.put("score", newScore);
            r.put("level", playLevel);
            long new_id = db.insert("scoreboard", null, r);

            //System.out.println("updated id ==================================== = " + new_id);


            finish();
        }else {
            cursor.moveToNext(); //get the next row


            word = cursor.getString(cursor.getColumnIndex("word"));
            question = cursor.getString(cursor.getColumnIndex("question"));

            tvQ.setText(question);
            tvSV.setText(Integer.toString(newScore));
            tvRV.setText(Integer.toString(n));
        }
    }

    public void loadActivity(String newQ, int newScore){



        if(newQ.indexOf('_') == -1){
            temp = 0;
            n++;
            setRound();
        }else{
            tvQ.setText(newQ);

            tvSV.setText(Integer.toString(newScore));
        }
    }

    public String searchAndReplace(String word, String question, Character xxx){
        int indexLetter = word.indexOf(xxx);
        int y;

        if(indexLetter == -1){
            newScore--;
        }else{

            for(y = 0; y < word.length(); y++){

                if(word.charAt(y) == xxx){

                    if(question.charAt(y) != '_'){
                        newScore--;
                    }else{
                        question = question.substring(0,y) + word.charAt(y) + question.substring(y+1);
                    }

                }else{

                }
            }
        }
        return question;
    }


    public void ButtonClicked(View v) {
        int id = v.getId();

        switch(id) {

            case R.id.btNewGame:

                finish();

                break;

            case R.id.btA:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'a');
                }else{
                    newQ = searchAndReplace(word, newQ, 'a');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btB:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'b');
                }else{
                    newQ = searchAndReplace(word, newQ, 'b');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btC:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'c');
                }else{
                    newQ = searchAndReplace(word, newQ, 'c');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btD:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'd');
                }else{
                    newQ = searchAndReplace(word, newQ, 'd');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btE:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'e');
                }else{
                    newQ = searchAndReplace(word, newQ, 'e');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btF:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'f');
                }else{
                    newQ = searchAndReplace(word, newQ, 'f');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btG:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'g');
                }else{
                    newQ = searchAndReplace(word, newQ, 'g');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btH:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'h');
                }else{
                    newQ = searchAndReplace(word, newQ, 'h');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btI:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'i');
                }else{
                    newQ = searchAndReplace(word, newQ, 'i');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btJ:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'j');
                }else{
                    newQ = searchAndReplace(word, newQ, 'j');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btK:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'k');
                }else{
                    newQ = searchAndReplace(word, newQ, 'k');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btL:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'l');
                }else{
                    newQ = searchAndReplace(word, newQ, 'l');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btM:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'm');
                }else{
                    newQ = searchAndReplace(word, newQ, 'm');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btN:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'n');
                }else{
                    newQ = searchAndReplace(word, newQ, 'n');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btO:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'o');
                }else{
                    newQ = searchAndReplace(word, newQ, 'o');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btP:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'p');
                }else{
                    newQ = searchAndReplace(word, newQ, 'p');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btQ:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'q');
                }else{
                    newQ = searchAndReplace(word, newQ, 'q');
                }

                loadActivity(newQ,newScore);

                break;


            case R.id.btR:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'r');
                }else{
                    newQ = searchAndReplace(word, newQ, 'r');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btS:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 's');
                }else{
                    newQ = searchAndReplace(word, newQ, 's');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btT:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 't');
                }else{
                    newQ = searchAndReplace(word, newQ, 't');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btU:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'u');
                }else{
                    newQ = searchAndReplace(word, newQ, 'u');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btV:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'v');
                }else{
                    newQ = searchAndReplace(word, newQ, 'v');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btW:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'w');
                }else{
                    newQ = searchAndReplace(word, newQ, 'w');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btX:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'x');
                }else{
                    newQ = searchAndReplace(word, newQ, 'x');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btY:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'y');
                }else{
                    newQ = searchAndReplace(word, newQ, 'y');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btZ:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'z');
                }else{
                    newQ = searchAndReplace(word, newQ, 'z');
                }

                loadActivity(newQ,newScore);

                break;
        }
    }

    public void finishPlay(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
