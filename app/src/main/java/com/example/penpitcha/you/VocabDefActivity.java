package com.example.penpitcha.you;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class VocabDefActivity extends ActionBarActivity {

    ArrayList<Map<String, String>> data;
    SimpleAdapter adapter;
    String wordName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_def);


        Intent it = this.getIntent();

        wordName = it.getStringExtra("wordClicked");

        TextView tvN = (TextView)findViewById(R.id.tvName);
        tvN.setText(wordName);

        /*data = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(this,
                data,
                R.layout.deflistview,
                new String[] {"type","definition"},
                new int[] {R.id.tvType,R.id.tvDef});
        ListView l = (ListView)findViewById(R.id.listView);
        l.setAdapter(adapter);

        LoadMessageTask task = new LoadMessageTask();
        task.execute();*/
    }

    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        String TYPE,DEF;
        String errorMsg = "";
        long lastUpdate = 0;

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                Log.e("LoadMessageTask", "" + wordName);
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522781632/androidproj/fetchdef.php?name="
                        + wordName);
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
                        TYPE = json.getJSONArray("msg").getJSONObject(i).getString("type");
                        DEF = json.getJSONArray("msg").getJSONObject(i).getString("definition");

                        Map<String, String> item = new HashMap<String, String>();
                        item.put("type", TYPE);
                        item.put("definition", DEF);
                        data.add(0, item);

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
                Toast t = Toast.makeText(VocabDefActivity.this.getApplicationContext(),
                        "Updated the definitions",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vocab_def, menu);
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
