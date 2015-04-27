package com.example.penpitcha.you;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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


public class VocabListActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    ArrayList<Map<String, String>> data;
    SimpleAdapter adapter;
    String playLevel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocab_list);


        Intent it = this.getIntent();

        playLevel = it.getStringExtra("playerLevel2");

        TextView tvL = (TextView)findViewById(R.id.tvLevel);
        tvL.setText(playLevel);

        data = new ArrayList<Map<String, String>>();
        adapter = new SimpleAdapter(this,
                data,
                android.R.layout.simple_list_item_1,
                new String[] {"name"},
                new int[] {android.R.id.text1});
        ListView l = (ListView)findViewById(R.id.lvName);
        l.setAdapter(adapter);

        l.setOnItemClickListener(this);

        LoadMessageTask task = new LoadMessageTask();
        task.execute();
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String word = data.get(position).get("name");

        //System.out.println(word);

        Intent intent = new Intent(this, VocabDefActivity.class);
        intent.putExtra("wordClicked", word);
        startActivity(intent);

    }

    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        String NAME;
        //String MESSAGE;
        //int TIME;
        String errorMsg = "";
        long lastUpdate = 0;

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                Log.e("LoadMessageTask", ""+ playLevel);
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522781632/androidproj/fetch.php?level="
                        + playLevel);
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
                        //MESSAGE = json.getJSONArray("msg").getJSONObject(i).getString("message");
                        //TIME = json.getJSONArray("msg").getJSONObject(i).getInt("time");

                        Map<String, String> item = new HashMap<String, String>();
                        item.put("name", NAME);
                        //item.put("message", MESSAGE);
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
                Toast t = Toast.makeText(VocabListActivity.this.getApplicationContext(),
                        "Updated the vocabulary",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vocab_list, menu);
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
