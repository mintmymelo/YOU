package com.example.penpitcha.you;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {


    SQLiteDatabase db;
    GameDBHelper helper = new GameDBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ButtonClicked(View v) {
        int id = v.getId();
        Intent i;


        switch(id) {
            case R.id.btPlay:

                EditText etName = (EditText) findViewById(R.id.etName);
                String name = etName.getText().toString();

                RadioGroup rLevel = (RadioGroup)findViewById(R.id.rbtLevel);
                int level = rLevel.getCheckedRadioButtonId();

                String levelName;

                if(level == R.id.btEasy) {
                    levelName = "easy";
                }else if(level == R.id.btMedium){
                    levelName = "medium";
                }else{
                    levelName = "hard";
                }

                if(name.trim().length()==0) {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Please input your name ^_^",
                            Toast.LENGTH_SHORT);
                    t.show();

                }else{
                    i = new Intent(this, PlayActivity.class);
                    i.putExtra("playerName", name);
                    i.putExtra("playerLevel", levelName);
                    startActivity(i);
                }


                break;

            case R.id.btScoreboard:
                i = new Intent(this, ScoreboardActivity.class);
                startActivity(i);
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
