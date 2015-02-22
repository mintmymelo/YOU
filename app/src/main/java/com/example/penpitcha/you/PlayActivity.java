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

    String newQ;
    int newScore;


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
        tvSV.setText(Integer.toString(score));

    }

    private void loadActivity(String newQ, int newScore){
        // Get the intent used to create this activity
        //Intent i = this.getIntent();
        // Get a string value named "value1"
        //playID = it.getStringExtra("playerID");
        //playLevel = it.getStringExtra("playerLevel");



        tvQ.setText(newQ);

        tvSV.setText(Integer.toString(newScore));
    }

    public String searchAndReplace(String word, String question, Character xxx){
        int indexLetter = word.indexOf(xxx);
        int indexSpace = question.indexOf('_');

        if(indexLetter == -1){
            newScore = score--;
        }else{
            if(indexLetter != indexSpace){
                newScore = score--;
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

                newQ = searchAndReplace(word,question,'A');
                loadActivity(newQ,newScore);

                break;

            case R.id.btR:

                newQ = searchAndReplace(word,question,'R');
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
