package com.example.penpitcha.you;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ScoreboardActivity extends ActionBarActivity {

    GameDBHelper helper;
    SQLiteDatabase db;
    int rank = 0;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);
    }


    public void ButtonClicked(View v) {
        int id = v.getId();
        Intent i;
        TextView tvSBLevelName;
        String name;
        int score;

        switch(id) {
            case R.id.btSBEasy:

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("----------- [ Level : Easy ] -----------");

                helper = new GameDBHelper(this);
                db = helper.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT name FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"easy"});

                //cursor.moveToFirst(); // get the first row

                //name = cursor.getString(cursor.getColumnIndex("name"));
                //score = cursor.getInt(cursor.getColumnIndex("score"));

                adapter = new SimpleCursorAdapter(this,
                        android.R.layout.simple_list_item_1, // A textview
                        cursor, // cursor to a data collection
                        new String[] {"name"}, // column to be displayed
                        new int[] {android.R.id.text1}, // ID of textview to display
                        0);

                ListView lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);


                break;

            case R.id.btSBMedium:

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("----------- [ Level : Medium ] -----------");


                break;

            case R.id.btSBHard:

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("----------- [ Level : Hard ] -----------");


                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scoreboard, menu);
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
