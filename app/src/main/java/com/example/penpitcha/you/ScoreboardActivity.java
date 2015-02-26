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
import android.widget.Toast;


public class ScoreboardActivity extends ActionBarActivity {

    GameDBHelper helper;
    SQLiteDatabase db;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        TextView tvSBLevelName;
        Cursor cursor;
        ListView lv;

        tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
        tvSBLevelName.setText("------------ [ Level : Easy ] ------------");

        helper = new GameDBHelper(this);
        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT _id, name, score FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"easy"});

        adapter = new SimpleCursorAdapter(this,
                R.layout.mynewlistview, // A textview
                cursor, // cursor to a data collection
                new String[] {"name", "score"}, // column to be displayed
                new int[] {R.id.tvListName,R.id.tvListScore}, // ID of textview to display
                0);

        lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);

    }


    public void ButtonClicked(View v) {
        int id = v.getId();
        TextView tvSBLevelName;
        Cursor cursor;
        ListView lv;

        switch(id) {
            case R.id.btSBEasy:

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("------------ [ Level : Easy ] ------------");

                helper = new GameDBHelper(this);
                db = helper.getReadableDatabase();
                cursor = db.rawQuery("SELECT _id, name, score FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"easy"});

                adapter = new SimpleCursorAdapter(this,
                        R.layout.mynewlistview, // A textview
                        cursor, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore}, // ID of textview to display
                        0);

                lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);

                break;

            case R.id.btSBMedium:

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("--------- [ Level : Medium ] ---------");

                helper = new GameDBHelper(this);
                db = helper.getReadableDatabase();
                cursor = db.rawQuery("SELECT _id, name, score FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"medium"});

                adapter = new SimpleCursorAdapter(this,
                        R.layout.mynewlistview, // A textview
                        cursor, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore}, // ID of textview to display
                        0);

                lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);

                break;

            case R.id.btSBHard:

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("------------ [ Level : Hard ] ------------");

                helper = new GameDBHelper(this);
                db = helper.getReadableDatabase();
                cursor = db.rawQuery("SELECT _id, name, score FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"hard"});

                adapter = new SimpleCursorAdapter(this,
                        R.layout.mynewlistview, // A textview
                        cursor, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore}, // ID of textview to display
                        0);

                lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);

                break;

            case R.id.btBack2Main:

                finish();

                break;

            case R.id.btResetList:

                db = helper.getWritableDatabase();
                db.delete("scoreboard", "", null);
                Toast t = Toast.makeText(this.getApplicationContext(),
                        "Scoreboard RESET!",
                        Toast.LENGTH_LONG);
                t.show();


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
