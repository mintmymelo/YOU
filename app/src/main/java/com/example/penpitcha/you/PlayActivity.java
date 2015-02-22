package com.example.penpitcha.you;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class PlayActivity extends ActionBarActivity {

    GameDBHelper helper;
    SimpleCursorAdapter adapter;
    Cursor cursor;
    String playID;
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
    static int newScore = 50;

    int temp = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        tvRV = (TextView)findViewById(R.id.tvRoundValue);
        tvRV.setText(Integer.toString(n));

        helper = new GameDBHelper(this);

        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT word, question FROM vocabulary WHERE level=?;", new String[] {"easy"});

        cursor.moveToFirst(); //get the first row

        word = cursor.getString(cursor.getColumnIndex("word"));
        question = cursor.getString(cursor.getColumnIndex("question"));

        tvQ = (TextView)findViewById(R.id.tvQuestion);
        tvQ.setText(question);
        tvSV = (TextView)findViewById(R.id.tvScoreValue);
        tvSV.setText(Integer.toString(newScore));

    }

    public void setRound(){
        cursor.moveToNext(); //get the next row

        word = cursor.getString(cursor.getColumnIndex("word"));
        question = cursor.getString(cursor.getColumnIndex("question"));

        tvQ.setText(question);
        tvSV.setText(Integer.toString(newScore));
        tvRV.setText(Integer.toString(n));
    }

    private void loadActivity(String newQ, int newScore){
        // Get the intent used to create this activity
        //Intent i = this.getIntent();
        // Get a string value named "value1"
        //playID = it.getStringExtra("playerID");
        //playLevel = it.getStringExtra("playerLevel");


        if(newQ.indexOf('_') == -1){
            temp--;
            n++;
            setRound();
        }else{
            tvQ.setText(newQ);

            tvSV.setText(Integer.toString(newScore));
        }
    }

    public String searchAndReplace(String word, String question, Character xxx){
        int indexLetter = word.indexOf(xxx);
        int indexSpace = question.indexOf('_');

        if(indexLetter == -1){
            newScore = newScore--;
        }else{
            if(indexLetter != indexSpace){
                newScore = newScore--;
            }else{
                question = question.substring(0,indexLetter) + word.charAt(indexLetter) + question.substring(indexLetter+1);
            }
        }

        return question;
    }


    public void ButtonClicked(View v) {
        int id = v.getId();
        Intent i;

        switch(id) {
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

            case R.id.btL:

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'l');
                }else{
                    newQ = searchAndReplace(word, newQ, 'l');
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
