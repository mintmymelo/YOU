package com.example.penpitcha.you;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
//import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScoreboardActivity extends ActionBarActivity {
    ArrayList<Map<String, String>> data;
    GameDBHelper helper;
    //SQLiteDatabase db;
    SimpleAdapter adapter;
    String level ="easy";
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        TextView tvSBLevelName;
        //Cursor cursor;
        //ListView lv;

        tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
        tvSBLevelName.setText("------------ [ Level : Easy ] ------------");

        //helper = new GameDBHelper(this);
        //db = helper.getReadableDatabase();
        //cursor = db.rawQuery("SELECT _id, name, score FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"easy"});
        //level = "easy";
        lv = (ListView)findViewById(R.id.listView);

        data = new ArrayList<Map<String, String>>();

        LoadMessageTask taskO = new LoadMessageTask();
        taskO.execute();

        adapter = new SimpleAdapter(this,
                data, // A textview
                R.layout.mynewlistview, // cursor to a data collection
                new String[] {"name", "score"}, // column to be displayed
                new int[] {R.id.tvListName,R.id.tvListScore});


        lv.setAdapter(adapter);


    }

    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        String NAME,SCORE;
        String errorMsg = "";
        long lastUpdate = 0;

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                Log.e("LoadMessageTask", "" + level);
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522781632/androidproj/fetchscore.php?level="
                        + level);
                HttpURLConnection h = (HttpURLConnection)u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();

                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    Log.e("LoadMessageTask", buffer.toString());
                    //Parsing JSON and displaying messages

                    //To append a new message:
                    //Map<String, String> item = new HashMap<String, String>();
                    //item.put("user", u);
                    //item.put("message", m);
                    //data.add(0, item);
                    JSONObject json = new JSONObject(buffer.toString());
                    //RESPONSE = json.getString("response");
                    //TIMESTAMP = json.getString("timestamp");


                    int length = json.getJSONArray("msg").length();
                    for(int i=0;i<length;i++){
                        NAME = json.getJSONArray("msg").getJSONObject(i).getString("name");
                        SCORE = json.getJSONArray("msg").getJSONObject(i).getString("score");

                        Map<String, String> item = new HashMap<String, String>();
                        item.put("name", NAME);
                        item.put("score", SCORE);
                        data.add(0,item);

                    }
                    errorMsg = "";


                    return true;

                } else {
                    errorMsg = "HTTP Error";
                }
            } catch (MalformedURLException e) {
                Log.e("LoadMessageTask", "Invalid URL");
            } catch (IOException e) {
                Log.e("LoadMessageTask", "I/O Exception");
            } catch (JSONException e) {
                Log.e("LoadMessageTask", "Invalid JSON");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                adapter.notifyDataSetChanged();
                lastUpdate = System.currentTimeMillis();
                Toast t = Toast.makeText(ScoreboardActivity.this.getApplicationContext(),
                        "Updated the scores",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    class DelMessageTask extends AsyncTask<String, Void, Boolean> {
        String line;
        StringBuilder buffer = new StringBuilder();

        @Override
        protected Boolean doInBackground(String... params) {
            //String name = params[0];
            //String level = params[1];
            //String score = params[2];

            HttpClient h = new DefaultHttpClient();
            HttpPost p = new HttpPost("http://ict.siit.tu.ac.th/~u5522781632/androidproj/del.php");


            try {

                HttpResponse response = h.execute(p);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                while((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                return true;
            } catch (UnsupportedEncodingException e) {
                Log.e("Error", "Invalid encoding");
            } catch (ClientProtocolException e) {
                Log.e("Error", "Error in posting a message");
            } catch (IOException e) {
                Log.e("Error", "I/O Exception");
            }

            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast t = Toast.makeText(ScoreboardActivity.this.getApplicationContext(),
                        "Successfully del",
                        Toast.LENGTH_SHORT);
                t.show();
            }
            else {
                Toast t = Toast.makeText(ScoreboardActivity.this.getApplicationContext(),
                        "Unable to del",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    public void ButtonClicked(View v) {
        int id = v.getId();
        TextView tvSBLevelName;
        //Cursor cursor;
        ListView lv;


        switch(id) {
            case R.id.btSBEasy:
                data = new ArrayList<Map<String, String>>();

                lv = (ListView)findViewById(R.id.listView);

                level = "easy";
                LoadMessageTask task = new LoadMessageTask();
                task.execute();

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("------------ [ Level : Easy ] ------------");

                //helper = new GameDBHelper(this);
                //db = helper.getReadableDatabase();
                //cursor = db.rawQuery("SELECT _id, name, score FROM scoreboard WHERE level=? ORDER BY score DESC;", new String[]{"easy"});

                adapter = new SimpleAdapter(this,
                        data, // A textview
                        R.layout.mynewlistview, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore});



                lv.setAdapter(adapter);

                break;

            case R.id.btSBMedium:
                data = new ArrayList<Map<String, String>>();

                level = "medium";
                LoadMessageTask taskM = new LoadMessageTask();
                taskM.execute();

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("--------- [ Level : Medium ] ---------");

                adapter = new SimpleAdapter(this,
                        data, // A textview
                        R.layout.mynewlistview, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore});


                lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);

                break;

            case R.id.btSBHard:
                data = new ArrayList<Map<String, String>>();

                level = "hard";
                LoadMessageTask taskH = new LoadMessageTask();
                taskH.execute();

                tvSBLevelName = (TextView) findViewById(R.id.tvSBLevelName);
                tvSBLevelName.setText("------------ [ Level : Hard ] ------------");

                adapter = new SimpleAdapter(this,
                        data, // A textview
                        R.layout.mynewlistview, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore});


                lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);

                break;

            case R.id.btBack2Main:

                finish();

                break;

            case R.id.btResetList:
                data = new ArrayList<Map<String, String>>();

                DelMessageTask del = new DelMessageTask();
                del.execute();

                adapter = new SimpleAdapter(this,
                        data, // A textview
                        R.layout.mynewlistview, // cursor to a data collection
                        new String[] {"name", "score"}, // column to be displayed
                        new int[] {R.id.tvListName,R.id.tvListScore});

                lv = (ListView)findViewById(R.id.listView);
                lv.setAdapter(adapter);

                //db = helper.getWritableDatabase();
                //db.delete("scoreboard", "", null);
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
