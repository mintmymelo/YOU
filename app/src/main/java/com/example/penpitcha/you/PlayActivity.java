package com.example.penpitcha.you;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class PlayActivity extends ActionBarActivity {

    GameDBHelper helper;
    Cursor cursor;
    String playName;
    String playLevel;
    SQLiteDatabase db;
    int n = 1;

    String word;
    String question;

    TextView tvRV;
    TextView tvQ;
    TextView tvSV;

    static String newQ;
    int newScore = 50;

    int temp = 0;

    ContentValues r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        Intent it = this.getIntent();

        playName = it.getStringExtra("playerName");
        playLevel = it.getStringExtra("playerLevel");

        tvRV = (TextView)findViewById(R.id.tvRoundValue);
        tvRV.setText(Integer.toString(n)+"/5");

        helper = new GameDBHelper(this);

        db = helper.getReadableDatabase();
        cursor = db.rawQuery("SELECT word, question FROM vocabulary WHERE level=?;", new String[]{playLevel});

        cursor.moveToFirst(); //get the first row

        word = cursor.getString(cursor.getColumnIndex("word"));
        question = cursor.getString(cursor.getColumnIndex("question"));

        tvQ = (TextView)findViewById(R.id.tvQuestion);
        tvQ.setText(question);
        tvSV = (TextView)findViewById(R.id.tvScoreValue);
        tvSV.setText(Integer.toString(newScore));

    }

    public void setRound(){

        if(cursor.isLast()){

            db = helper.getWritableDatabase();
            r = new ContentValues();
            r.put("name", playName);
            r.put("score", newScore);
            r.put("level", playLevel);
            long new_id = db.insert("scoreboard", null, r);

            finish();

        }else {

            cursor.moveToNext(); //get the next row

            word = cursor.getString(cursor.getColumnIndex("word"));
            question = cursor.getString(cursor.getColumnIndex("question"));

            tvQ.setText(question);
            tvSV.setText(Integer.toString(newScore));
            tvRV.setText(Integer.toString(n)+"/5");

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
        }
    }

    public void loadActivity(String newQ, int newScore){

        if(newQ.indexOf('_') == -1){

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
        int noWrongAnymore = 0;

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

                    if(question.charAt(y) != '_'){
                        if(noWrongAnymore != 777) {
                            isWrong = true;
                        }
                    }else{
                        isWrong = false;
                        noWrongAnymore = 777;
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
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'a');
                }else{
                    newQ = searchAndReplace(word, newQ, 'a');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btA = (Button) findViewById(R.id.btA);
                    btA.setEnabled(false);
                }

                break;

            case R.id.btB:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'b');
                }else{
                    newQ = searchAndReplace(word, newQ, 'b');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btB = (Button) findViewById(R.id.btB);
                    btB.setEnabled(false);
                }

                break;

            case R.id.btC:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'c');
                }else{
                    newQ = searchAndReplace(word, newQ, 'c');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btC = (Button) findViewById(R.id.btC);
                    btC.setEnabled(false);
                }

                break;

            case R.id.btD:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'd');
                }else{
                    newQ = searchAndReplace(word, newQ, 'd');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btD = (Button) findViewById(R.id.btD);
                    btD.setEnabled(false);
                }

                break;

            case R.id.btE:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'e');
                }else{
                    newQ = searchAndReplace(word, newQ, 'e');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btE = (Button) findViewById(R.id.btE);
                    btE.setEnabled(false);
                }

                break;

            case R.id.btF:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'f');
                }else{
                    newQ = searchAndReplace(word, newQ, 'f');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btF = (Button) findViewById(R.id.btF);
                    btF.setEnabled(false);
                }

                break;

            case R.id.btG:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'g');
                }else{
                    newQ = searchAndReplace(word, newQ, 'g');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btG = (Button) findViewById(R.id.btG);
                    btG.setEnabled(false);
                }

                break;

            case R.id.btH:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'h');
                }else{
                    newQ = searchAndReplace(word, newQ, 'h');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btH = (Button) findViewById(R.id.btH);
                    btH.setEnabled(false);
                }

                break;

            case R.id.btI:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'i');
                }else{
                    newQ = searchAndReplace(word, newQ, 'i');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btI = (Button) findViewById(R.id.btI);
                    btI.setEnabled(false);
                }

                break;

            case R.id.btJ:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'j');
                }else{
                    newQ = searchAndReplace(word, newQ, 'j');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btJ = (Button) findViewById(R.id.btJ);
                    btJ.setEnabled(false);
                }

                break;

            case R.id.btK:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'k');
                }else{
                    newQ = searchAndReplace(word, newQ, 'k');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btK = (Button) findViewById(R.id.btK);
                    btK.setEnabled(false);
                }

                break;

            case R.id.btL:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'l');
                }else{
                    newQ = searchAndReplace(word, newQ, 'l');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btL = (Button) findViewById(R.id.btL);
                    btL.setEnabled(false);
                }

                break;

            case R.id.btM:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'm');
                }else{
                    newQ = searchAndReplace(word, newQ, 'm');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btM = (Button) findViewById(R.id.btM);
                    btM.setEnabled(false);
                }

                break;

            case R.id.btN:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'n');
                }else{
                    newQ = searchAndReplace(word, newQ, 'n');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btN = (Button) findViewById(R.id.btN);
                    btN.setEnabled(false);
                }

                break;

            case R.id.btO:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'o');
                }else{
                    newQ = searchAndReplace(word, newQ, 'o');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btO = (Button) findViewById(R.id.btO);
                    btO.setEnabled(false);
                }

                break;

            case R.id.btP:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'p');
                }else{
                    newQ = searchAndReplace(word, newQ, 'p');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btP = (Button) findViewById(R.id.btP);
                    btP.setEnabled(false);
                }

                break;

            case R.id.btQ:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'q');
                }else{
                    newQ = searchAndReplace(word, newQ, 'q');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btQ = (Button) findViewById(R.id.btQ);
                    btQ.setEnabled(false);
                }

                break;


            case R.id.btR:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'r');
                }else{
                    newQ = searchAndReplace(word, newQ, 'r');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btR = (Button) findViewById(R.id.btR);
                    btR.setEnabled(false);
                }

                break;

            case R.id.btS:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 's');
                }else{
                    newQ = searchAndReplace(word, newQ, 's');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btS = (Button) findViewById(R.id.btS);
                    btS.setEnabled(false);
                }

                break;

            case R.id.btT:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 't');
                }else{
                    newQ = searchAndReplace(word, newQ, 't');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btT = (Button) findViewById(R.id.btT);
                    btT.setEnabled(false);
                }

                break;

            case R.id.btU:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'u');
                }else{
                    newQ = searchAndReplace(word, newQ, 'u');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btU = (Button) findViewById(R.id.btU);
                    btU.setEnabled(false);
                }

                break;

            case R.id.btV:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'v');
                }else{
                    newQ = searchAndReplace(word, newQ, 'v');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btV = (Button) findViewById(R.id.btV);
                    btV.setEnabled(false);
                }

                break;

            case R.id.btW:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'w');
                }else{
                    newQ = searchAndReplace(word, newQ, 'w');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btW = (Button) findViewById(R.id.btW);
                    btW.setEnabled(false);
                }

                break;

            case R.id.btX:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'x');
                }else{
                    newQ = searchAndReplace(word, newQ, 'x');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btX = (Button) findViewById(R.id.btX);
                    btX.setEnabled(false);
                }

                break;

            case R.id.btY:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'y');
                }else{
                    newQ = searchAndReplace(word, newQ, 'y');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btY = (Button) findViewById(R.id.btY);
                    btY.setEnabled(false);
                }

                break;

            case R.id.btZ:
                isClicked = true;
                temp++;
                if(temp == 1) {
                    newQ = searchAndReplace(word, question, 'z');
                }else{
                    newQ = searchAndReplace(word, newQ, 'z');
                }

                loadActivity(newQ,newScore);

                if(isClicked){
                    Button btZ = (Button) findViewById(R.id.btZ);
                    btZ.setEnabled(false);
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
