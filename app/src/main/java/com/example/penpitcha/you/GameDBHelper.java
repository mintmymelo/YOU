package com.example.penpitcha.you;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GameDBHelper extends SQLiteOpenHelper {

    private static final String name = "word.sqlite3";
    private static final int version = 2;


    public GameDBHelper(Context ctx) {
        super(ctx, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE vocabulary ( " +
                "_id integer primary key autoincrement," +
                "word text not null," +             // e.g. CAT
                "question text not null ," +           // e.g.C_T
                "level text not null);";         // easy,medium,hard
        db.execSQL(sql);

        sql = "CREATE TABLE scoreboard ( " +
                "_id integer primary key autoincrement," +
                "name text not null," +             // e.g. Bgcomedian
                "score integer not null ," +           // e.g.15
                "level text not null);";         // easy,medium,hard
        db.execSQL(sql);

        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('trouble','t_oub_e','easy');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('nothing','n_thi_g','easy');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('force','fo_c_','easy');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('suddenly','sud_e_ly','easy');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('decide','d_ci_e','easy');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('love','l_v_','easy');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('apple','a_p_e','easy');";
        db.execSQL(sql);

        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('peculiar','p_cul_ar','medium');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('acquiesce','ac_uie_ce','medium');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('spasm','__asm','medium');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('venture','_ent_re','medium');";
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('omnivore','o_ni_o_e','medium');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('independent','in_ep_nd__t','medium');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('oblivious','ob_i_io_s','medium');" ;
        db.execSQL(sql);

        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('nubivagant','nu_i_aga_t','hard');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('alexithymia','ale_i_h_m_a','hard');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('kakistocracy','kak_sto_ra_y','hard');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('galeanthropy','gal__nth__py','hard');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('lethologica','_eth_lo_ica','hard');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('bibliophilia','b_bl_oph_li_','hard');" ;
        db.execSQL(sql);
        sql = "INSERT INTO vocabulary (word,question,level) VALUES ('scripturient','_c_ipt_ri_nt','hard');" ;
        db.execSQL(sql);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS vocabulary;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}