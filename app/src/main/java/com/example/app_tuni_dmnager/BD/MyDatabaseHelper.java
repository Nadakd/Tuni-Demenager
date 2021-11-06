package com.example.app_tuni_dmnager.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.app_tuni_dmnager.Model.DEMANDE_DEVIS;
import com.example.app_tuni_dmnager.Model.Demande_Demenagement;
import com.example.app_tuni_dmnager.Model.Demenageur;
import com.example.app_tuni_dmnager.Model.Devis;


public class MyDatabaseHelper extends SQLiteOpenHelper  {

    private SQLiteDatabase db;
    Context context;
    private static final String DATABASE_NAME = "Tuni_demenager.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE1 = "MESSAGE";
    private static final String TABLE2 = "DEMENAGEUR";
    private static final String TABLE3 = "DEMANDE_DEVIS";
    private static final String TABLE4 = "CLIENT";
    private static final String TABLE5 = "DEVIS";
    private static final String TABLE6 = "DEMANDE_DEMENAGEMENT";
    public static final String KEY_ROWID = "_id";
    private static MyDatabaseHelper myDatabaseHelper;

    public MyDatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql1 = "CREATE TABLE MESSAGE (_id INTEGER PRIMARY KEY AUTOINCREMENT,Sujet TEXT,Contenu TEXT,clientid Integer,FOREIGN KEY (clientid) REFERENCES CLIENT ("+KEY_ROWID+"))";
        String sql2 = "CREATE TABLE DEMENAGEUR (_id INTEGER PRIMARY KEY AUTOINCREMENT,nom_prenom TEXT,email TEXT,cin INTEGER,tlf INTEGER,ville TEXT)";
        String sql3 = "CREATE TABLE DEMANDE_DEVIS (_id INTEGER PRIMARY KEY AUTOINCREMENT,adresse_depart TEXT,code_postal_dep INTEGER,ville_depart TEXT,etage_dep TEXT,Ascenseur_dep TEXT,adresse_arrive TEXT,code_postal_arv INTEGER,ville_arv TEXT,etage_arv TEXT,Ascenseur_arv TEXT,distance TEXT,demenageur_id Integer,clientid Integer,FOREIGN KEY (demenageur_id) REFERENCES DEMENAGEUR (\"+KEY_ROWID+\"),FOREIGN KEY (clientid) REFERENCES CLIENT (\"+KEY_ROWID+\"))";
        String sql4 = "CREATE TABLE CLIENT (_id INTEGER PRIMARY KEY AUTOINCREMENT,nom_prenom TEXT,civilité TEXT,ville TEXT,cin INTEGER,age INTEGER,tlf INTEGER,email TEXT,Password TEXT,ConfirmPassword TEXT)";
        String sql5 = "CREATE TABLE DEVIS (_id INTEGER PRIMARY KEY AUTOINCREMENT,Prix INTEGER,nom_prenom TEXT,tlfdem INTEGER,villedepart TEXT,ville_arv TEXT,clientid Integer,FOREIGN KEY (clientid) REFERENCES CLIENT (\"+KEY_ROWID+\"))";
        String sql6 = "CREATE TABLE DEMANDE_DEMENAGEMENT (_id INTEGER PRIMARY KEY AUTOINCREMENT,Date TEXT,clientid Integer,devisid Integer,FOREIGN KEY (clientid) REFERENCES CLIENT (\"+KEY_ROWID+\"),FOREIGN KEY (devisid) REFERENCES DEVIS (\"+KEY_ROWID+\"))";

        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
        db.execSQL(sql5);
        db.execSQL(sql6);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE5);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE6);
        onCreate(db);
    }



    public int deletedevis(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE5,KEY_ROWID +" =?",new String[]{String.valueOf(id)});

    }
    public int deletemsg(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE1,KEY_ROWID +" =?",new String[]{String.valueOf(id)});

    }



    public static MyDatabaseHelper instanceOfDatabase(Context context) {
        if (myDatabaseHelper == null)
            myDatabaseHelper = new MyDatabaseHelper(context);

        return myDatabaseHelper;
    }

    public void demandedemenagementListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("select dem.Date ,dev.nom_prenom From DEMANDE_DEMENAGEMENT dem JOIN DEVIS dev ON dem.devisid = dev." + KEY_ROWID  , null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                     int id = result.getInt(0);
                    String Date = result.getString(1);
                    String nomdem = result.getString(2);

                    Demande_Demenagement dem = new Demande_Demenagement(Date,nomdem);
                    Demande_Demenagement.DemandeDemenagementArrayList.add(dem);
                }
            }
        }


    }

    //-----------------Display

    public void populateDemListArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE2, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String nom_prenom = result.getString(1);
                    String email = result.getString(2);
                    int tlf = result.getInt(4);
                    String ville = result.getString(5);
                    Demenageur dem = new Demenageur(id, nom_prenom, email, tlf, ville);
                    Demenageur.demArrayList.add(dem);
                }
            }
        }


    }

    public void DemListArraybyid(Demenageur dem) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(TABLE2, new String[]{"_id", "nom_prenom", "email", "tlf", "ville"}, "_id=?",
                new String[]{String.valueOf(dem.getId())}, null, null, null);
        c.moveToFirst();
        dem = new Demenageur(c.getString(1), c.getString(2), c.getInt(3), c.getString(4));

        Demenageur.demArrayList.add(dem);

    }


//-----------------------Liste demande devis
DEMANDE_DEVIS dv = new DEMANDE_DEVIS();
//"SELECT id,adresse_depart,code_postal_dep,ville_depart,etage_dep,ascenseur_dep,adresse_arrive,code_postal_arv,ville_arv,etage_arv,ascenseur_arv,distance,dm.nom_prenom,clientid FROM demande_devis dv ,demenageur dm WHERE dv.demenageur_id=dm.id
    public void ListeDemandeDevis() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM DEMANDE_DEVIS  ", null)) {

            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String adresse_depart = result.getString(1);
                    int code_postal_dep = result.getInt(2);
                    String ville_depart = result.getString(3);
                    String etage_dep = result.getString(4);
                    String ascenseur_dep = result.getString(5);
                    String adresse_arrive = result.getString(6);
                    int code_postal_arv = result.getInt(7);
                    String ville_arv = result.getString(8);
                    String etage_arv = result.getString(9);
                    String ascenseur_arv = result.getString(10);
                    String distance = result.getString(11);

                    int clientid = result.getInt(12);
                    DEMANDE_DEVIS dem = new DEMANDE_DEVIS(id,adresse_depart,code_postal_dep, ville_depart, etage_dep, ascenseur_dep, adresse_arrive, code_postal_arv, ville_arv, etage_arv, ascenseur_arv,distance,clientid);
                    DEMANDE_DEVIS.demande_devisArrayList.add(dem);
                }
            }
        }
    }







    public void ListDevis() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE5, null)) {
            if (result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    int prix = result.getInt(1);
                    String nom_prenom = result.getString(2);
                    int tlfdem = result.getInt(3);
                    String villedep = result.getString(4);
                    String villearr = result.getString(5);
                    int iclient = result.getInt(6);

                    Devis dem = new Devis(id,prix, nom_prenom, tlfdem, villedep, villearr,iclient);
                    Devis.devisArrayList.add(dem);
                }
            }
        }
    }
    public void envoyerdemendedem(String Date,int clientid,int devisid) {

        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("Date", Date);
        cv.put("clientid", clientid);
        cv.put("devisid", devisid);
        long result = db.insert("DEMANDE_DEMENAGEMENT", null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public int deletedemandedevis(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE3,KEY_ROWID +" =?",new String[]{String.valueOf(id)});
    }

    public int deletedemandedemanagement(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE6,KEY_ROWID +" =?",new String[]{String.valueOf(id)});
    }

}


