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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        helper = new GameDBHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT question FROM vocabulary WHERE level='easy' and id=0;", null);


        adapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2, // A textview
                cursor, // cursor to a data collection
                new String[] {"question"}, // column to be displayed
                new int[] {android.R.id.text1}, // ID of textview to display

                0);

        TextView tvQ = (TextView)findViewById(R.id.tvQuestion);
        //tvQ.setAdapter(adapter);
        tvQ.setText(adapter);
    }


    public void ButtonClicked(View v) {
        int id = v.getId();
        Intent i;

        switch(id) {
            case R.id.btOK:

                for(int n = 1 ; n<=5 ; n++) {


                }

                break;

        }
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
