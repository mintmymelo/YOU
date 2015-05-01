package com.example.penpitcha.you;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
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


public class PlayActivity extends ActionBarActivity {

    ArrayList<Map<String, String>> data;
    ArrayAdapter adapter;

    //GameDBHelper helper;
    //Cursor cursor;
    String playName;
    String playLevel;
    //SQLiteDatabase db;
    int n = 1;

    String word;
    String question;

    TextView tvRV;
    TextView tvQ;
    TextView tvSV;

    ListView lvQ;

    static String newQ;
    int newScore = 25;

    int temp = 0;

    //ContentValues r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        Intent it = this.getIntent();

        playName = it.getStringExtra("playerName");
        playLevel = it.getStringExtra("playerLevel");

        tvRV = (TextView)findViewById(R.id.tvRoundValue);
        tvRV.setText(Integer.toString(n)+"/5");

        LoadMessageTask task = new LoadMessageTask();
        task.execute();
        //helper = new GameDBHelper(this);

        //db = helper.getReadableDatabase();
        //cursor = db.rawQuery("SELECT word, question FROM vocabulary WHERE level=? ORDER BY RANDOM() LIMIT 5;", new String[]{playLevel});

        //cursor.moveToFirst();   //get the first row

        word = data.get(0).get("name");//cursor.getString(cursor.getColumnIndex("word"));
        question = data.get(0).get("question");//cursor.getString(cursor.getColumnIndex("question"));

        //lvQ = (ListView)findViewById(R.id.myLV);


        //adapter = new ArrayAdapter(this, R.layout.lvq, data);

          //      new String[] {"question"},
           //     new int[] {R.id.tvQQ});


        //lvQ.setAdapter((android.widget.ListAdapter) adapter.getItem(0));

        //LoadMessageTask task = new LoadMessageTask();
        //task.execute();

/*        if(data.isEmpty()) {
            tvQ.setText("empty");
        }else{
            tvQ.setText(data.get(0).get("question"));
        }
*/
        //word = data.get(0).get("name");
        int i;
        //for(i=0; i < data.size(); i++){
         //   question = data.get(0).get("question");
          //  tvQ.setText(question);
        //}


        tvSV = (TextView)findViewById(R.id.tvScoreValue);
        tvSV.setText(Integer.toString(newScore));

        tvQ = (TextView)findViewById(R.id.tvQuestion);
        tvQ.setText(question);



    }

    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        String NAME,QUESTION;
        String errorMsg = "";
        long lastUpdate = 0;

        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                Log.e("LoadMessageTask", "" + playLevel);
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522781632/androidproj/fetchques.php?level="
                        + playLevel);
                HttpURLConnection h = (HttpURLConnection) u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();

                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    Log.e("LoadMessageTask", buffer.toString());
                    JSONObject json = new JSONObject(buffer.toString());

                    int length = json.getJSONArray("msg").length();

                    data = new ArrayList<Map<String, String>>();

                    for (int i = 0; i < length; i++) {
                        NAME = json.getJSONArray("msg").getJSONObject(i).getString("name");
                        QUESTION = json.getJSONArray("msg").getJSONObject(i).getString("question");

                        Map<String, String> item = new HashMap<String, String>();
                        item.put("name", NAME);
                        item.put("question", QUESTION);
                        data.add(0, item);
                        if(data.isEmpty()) {
                            System.out.println("empty jaaa ===================================================");
                        }else {
                            System.out.println("found !!!! -=====================================================");
                        }
                    }

 //                   data = new ArrayList<Map<String, String>>();



/*                    if(data.isEmpty()) {
                        tvQ.setText("empty");
                    }else{
                        tvQ.setText(data.get(0).get(question));
                    }
*/
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
                Toast t = Toast.makeText(PlayActivity.this.getApplicationContext(),
                        "Updated the definitions",
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }
    }

    public void setRound(){
        //cursor.isLast()
        if(false){

            if(playName==null){
                playName="player";
            }
/*
            db = helper.getWritableDatabase();
            r = new ContentValues();
            r.put("name", playName);
            r.put("score", newScore);
            r.put("level", playLevel);
            db.insert("scoreboard", null, r);
*/

            finish();

        }else {

            /*
            cursor.moveToNext(); //get the next row

            word = cursor.getString(cursor.getColumnIndex("word"));
            question = cursor.getString(cursor.getColumnIndex("question"));
*/

            tvQ.setText(question);
            tvSV.setText(Integer.toString(newScore));
            tvRV.setText(Integer.toString(n)+"/5");

        }
    }

    public void loadActivity(String newQ, int newScore){

        if(newQ.indexOf('_') == -1){

            Button btA = (Button) findViewById(R.id.btA);
            Button btB = (Button) findViewById(R.id.btB);
            Button btC = (Button) findViewById(R.id.btC);
            Button btD = (Button) findViewById(R.id.btD);
            Button btE = (Button) findViewById(R.id.btE);
            Button btF = (Button) findViewById(R.id.btF);
            Button btG = (Button) findViewById(R.id.btG);
            Button btH = (Button) findViewById(R.id.btH);
            Button btI = (Button) findViewById(R.id.btI);
            Button btJ = (Button) findViewById(R.id.btJ);
            Button btK = (Button) findViewById(R.id.btK);
            Button btL = (Button) findViewById(R.id.btL);
            Button btM = (Button) findViewById(R.id.btM);
            Button btN = (Button) findViewById(R.id.btN);
            Button btO = (Button) findViewById(R.id.btO);
            Button btP = (Button) findViewById(R.id.btP);
            Button btQ = (Button) findViewById(R.id.btQ);
            Button btR = (Button) findViewById(R.id.btR);
            Button btS = (Button) findViewById(R.id.btS);
            Button btT = (Button) findViewById(R.id.btT);
            Button btU = (Button) findViewById(R.id.btU);
            Button btV = (Button) findViewById(R.id.btV);
            Button btW = (Button) findViewById(R.id.btW);
            Button btX = (Button) findViewById(R.id.btX);
            Button btY = (Button) findViewById(R.id.btY);
            Button btZ = (Button) findViewById(R.id.btZ);

            btA.setEnabled(true);
            btB.setEnabled(true);
            btC.setEnabled(true);
            btD.setEnabled(true);
            btE.setEnabled(true);
            btF.setEnabled(true);
            btG.setEnabled(true);
            btH.setEnabled(true);
            btI.setEnabled(true);
            btJ.setEnabled(true);
            btK.setEnabled(true);
            btL.setEnabled(true);
            btM.setEnabled(true);
            btN.setEnabled(true);
            btO.setEnabled(true);
            btP.setEnabled(true);
            btQ.setEnabled(true);
            btR.setEnabled(true);
            btS.setEnabled(true);
            btT.setEnabled(true);
            btU.setEnabled(true);
            btV.setEnabled(true);
            btW.setEnabled(true);
            btX.setEnabled(true);
            btY.setEnabled(true);
            btZ.setEnabled(true);

            Toast t = Toast.makeText(this.getApplicationContext(),
                    "CORRECT ^0^ ~" + "The Answer = " + newQ,
                    Toast.LENGTH_LONG);
            t.show();


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
        boolean isWrong=false;
        boolean noWrongAnymore=false;

        if(indexLetter == -1){
            newScore--;

            if(newScore < 0){
                Toast t = Toast.makeText(this.getApplicationContext(),
                        "GAME OVER !!! BYE...",
                        Toast.LENGTH_LONG);
                t.show();

                finish();
            }

        }else{

            for(y = 0; y < word.length(); y++){

                if(word.charAt(y) == xxx){

                    if(question.charAt(y) != '_'){   // ถ้ามันเป็นตัวอักษร
                        if(noWrongAnymore != true) { //ถ้ามันไม่ได้เข้า else ข้างล่าง ถือว่า ผิด!! มันไม่ใช่คำตอบ !!!
                            isWrong = true;   // ผิดดดด
                        }

                    }else{
                        isWrong = false; /// ถูกกกก
                        noWrongAnymore = true;
                        question = question.substring(0,y) + word.charAt(y) + question.substring(y+1);
                    }

                }else{
                }
            }

            if(isWrong){
                newScore--;

                if(newScore < 0){
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "GAME OVER !!! BYE...",
                            Toast.LENGTH_LONG);
                    t.show();

                    finish();
                }
            }
        }
        return question;
    }



    public void ButtonClicked(View v) {
        int id = v.getId();
        boolean isClicked = false;

        switch(id) {

            case R.id.btNewGame:

                finish();

                break;

            case R.id.btA:
                isClicked = true;

                if(isClicked){
                    Button btA = (Button) findViewById(R.id.btA);
                    btA.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'a');
                }else{
                    newQ = searchAndReplace(word, newQ, 'a');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btB:
                isClicked = true;

                if(isClicked){
                    Button btB = (Button) findViewById(R.id.btB);
                    btB.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'b');
                }else{
                    newQ = searchAndReplace(word, newQ, 'b');
                }

                loadActivity(newQ,newScore);


                break;

            case R.id.btC:
                isClicked = true;
                if(isClicked){
                    Button btC = (Button) findViewById(R.id.btC);
                    btC.setEnabled(false);
                }
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'c');
                }else{
                    newQ = searchAndReplace(word, newQ, 'c');
                }

                loadActivity(newQ,newScore);


                break;

            case R.id.btD:
                isClicked = true;
                if(isClicked){
                    Button btD = (Button) findViewById(R.id.btD);
                    btD.setEnabled(false);
                }
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'd');
                }else{
                    newQ = searchAndReplace(word, newQ, 'd');
                }

                loadActivity(newQ,newScore);


                break;

            case R.id.btE:
                isClicked = true;
                if(isClicked){
                    Button btE = (Button) findViewById(R.id.btE);
                    btE.setEnabled(false);
                }
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'e');
                }else{
                    newQ = searchAndReplace(word, newQ, 'e');
                }

                loadActivity(newQ,newScore);


                break;

            case R.id.btF:
                isClicked = true;
                if(isClicked){
                    Button btF = (Button) findViewById(R.id.btF);
                    btF.setEnabled(false);
                }
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'f');
                }else{
                    newQ = searchAndReplace(word, newQ, 'f');
                }

                loadActivity(newQ,newScore);


                break;

            case R.id.btG:
                isClicked = true;
                if(isClicked){
                    Button btG = (Button) findViewById(R.id.btG);
                    btG.setEnabled(false);
                }
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'g');
                }else{
                    newQ = searchAndReplace(word, newQ, 'g');
                }

                loadActivity(newQ,newScore);


                break;

            case R.id.btH:
                isClicked = true;
                if(isClicked){
                    Button btH = (Button) findViewById(R.id.btH);
                    btH.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'h');
                }else{
                    newQ = searchAndReplace(word, newQ, 'h');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btI:
                isClicked = true;
                if(isClicked){
                    Button btI = (Button) findViewById(R.id.btI);
                    btI.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'i');
                }else{
                    newQ = searchAndReplace(word, newQ, 'i');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btJ:
                isClicked = true;
                if(isClicked){
                    Button btJ = (Button) findViewById(R.id.btJ);
                    btJ.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'j');
                }else{
                    newQ = searchAndReplace(word, newQ, 'j');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btK:
                isClicked = true;
                if(isClicked){
                    Button btK = (Button) findViewById(R.id.btK);
                    btK.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'k');
                }else{
                    newQ = searchAndReplace(word, newQ, 'k');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btL:
                isClicked = true;
                if(isClicked){
                    Button btL = (Button) findViewById(R.id.btL);
                    btL.setEnabled(false);
                }
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'l');
                }else{
                    newQ = searchAndReplace(word, newQ, 'l');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btM:
                isClicked = true;
                if(isClicked){
                    Button btM = (Button) findViewById(R.id.btM);
                    btM.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'm');
                }else{
                    newQ = searchAndReplace(word, newQ, 'm');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btN:
                isClicked = true;
                if(isClicked){
                    Button btN = (Button) findViewById(R.id.btN);
                    btN.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'n');
                }else{
                    newQ = searchAndReplace(word, newQ, 'n');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btO:
                isClicked = true;
                if(isClicked){
                    Button btO = (Button) findViewById(R.id.btO);
                    btO.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'o');
                }else{
                    newQ = searchAndReplace(word, newQ, 'o');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btP:
                isClicked = true;
                if(isClicked){
                    Button btP = (Button) findViewById(R.id.btP);
                    btP.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'p');
                }else{
                    newQ = searchAndReplace(word, newQ, 'p');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btQ:
                isClicked = true;
                if(isClicked){
                    Button btQ = (Button) findViewById(R.id.btQ);
                    btQ.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'q');
                }else{
                    newQ = searchAndReplace(word, newQ, 'q');
                }

                loadActivity(newQ,newScore);

                break;


            case R.id.btR:
                isClicked = true;
                if(isClicked){
                    Button btR = (Button) findViewById(R.id.btR);
                    btR.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'r');
                }else{
                    newQ = searchAndReplace(word, newQ, 'r');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btS:
                isClicked = true;
                if(isClicked){
                    Button btS = (Button) findViewById(R.id.btS);
                    btS.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 's');
                }else{
                    newQ = searchAndReplace(word, newQ, 's');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btT:
                isClicked = true;
                if(isClicked){
                    Button btT = (Button) findViewById(R.id.btT);
                    btT.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 't');
                }else{
                    newQ = searchAndReplace(word, newQ, 't');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btU:
                isClicked = true;
                if(isClicked){
                    Button btU = (Button) findViewById(R.id.btU);
                    btU.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'u');
                }else{
                    newQ = searchAndReplace(word, newQ, 'u');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btV:
                isClicked = true;
                if(isClicked){
                    Button btV = (Button) findViewById(R.id.btV);
                    btV.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'v');
                }else{
                    newQ = searchAndReplace(word, newQ, 'v');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btW:
                isClicked = true;
                if(isClicked){
                    Button btW = (Button) findViewById(R.id.btW);
                    btW.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'w');
                }else{
                    newQ = searchAndReplace(word, newQ, 'w');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btX:
                isClicked = true;
                if(isClicked){
                    Button btX = (Button) findViewById(R.id.btX);
                    btX.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'x');
                }else{
                    newQ = searchAndReplace(word, newQ, 'x');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btY:
                isClicked = true;
                if(isClicked){
                    Button btY = (Button) findViewById(R.id.btY);
                    btY.setEnabled(false);
                }

                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'y');
                }else{
                    newQ = searchAndReplace(word, newQ, 'y');
                }

                loadActivity(newQ,newScore);

                break;

            case R.id.btZ:
                isClicked = true;
                if(isClicked){
                    Button btZ = (Button) findViewById(R.id.btZ);
                    btZ.setEnabled(false);
                }

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
