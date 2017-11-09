package com.example.fariahuq.pocketaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static com.example.fariahuq.pocketaid.R.drawable.aid;

/**
 * Created by Faria huq on 06-Sep-17.
 */

public class MyDBHandler extends SQLiteOpenHelper {

//make some variables

    private static final int DATABASE_VERSION=1;

    private static final String DATABASE_NAME="PocketAid.db";

    public static final String COLOUMN_ID="_id"; //PK
    public static final String COLOUMN_FID="fid"; //FK
    public static final String COLOUMN_TITLE="title";
    public static final String COLOUMN_DESC="description";
    public static final String COLOUMN_IMAGE="imagesrc";
    public static final String COLOUMN_FAVOURITE="fav";

    public static final String COLOUMN_PRODUCTNAME="productname";

    public static final String TABLE_AID="aid";
    public static final String TABLE_AID_ITEM="aiditem";

    public static final String TABLE_SELFTEST="selftest";
    public static final String TABLE_SELFTEST_ITEM="selftestitem";

    public static final String TABLE_SYMPTOMS="symptoms";
    public static final String TABLE_SYMPTOMS_ITEM="symptomsitem";

    public static final String TABLE_FAVOURITE_AID="favaid";
    public static final String TABLE_FAVOURITE_TEST="favtest";
    public static final String TABLE_FAVOURITE_SYMPTOMS="favsymptom";

    public static final String TABLE_CONTACT="contacts";
    public static final String TABLE_PRODUCTS="products";



    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+TABLE_AID);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_SELFTEST);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_SYMPTOMS);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_AID_ITEM);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_SELFTEST_ITEM);
        db.execSQL("DROP TABLE IF EXISTS"+TABLE_SYMPTOMS_ITEM);
        onCreate(db);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_AID + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_TITLE + " TEXT, " + COLOUMN_IMAGE + " TEXT, " + COLOUMN_FAVOURITE + " INTEGER " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SELFTEST + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_TITLE + " TEXT, " + COLOUMN_IMAGE + " TEXT, " +COLOUMN_FAVOURITE + " INTEGER " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SYMPTOMS + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_TITLE + " TEXT, "+COLOUMN_IMAGE + " TEXT, "  +COLOUMN_FAVOURITE + " INTEGER " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_AID_ITEM + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_TITLE + " TEXT, " +COLOUMN_DESC + " TEXT, " +
                COLOUMN_FID + " INTEGER, " +COLOUMN_IMAGE + " TEXT "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SYMPTOMS_ITEM + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_TITLE + " TEXT, " +COLOUMN_DESC + " TEXT, " +
                COLOUMN_FID + " INTEGER, " +COLOUMN_IMAGE + " TEXT "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SELFTEST_ITEM + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY, " +
                COLOUMN_TITLE + " TEXT, " + COLOUMN_DESC + " TEXT, " +
                COLOUMN_FID + " INTEGER, " +COLOUMN_IMAGE + " TEXT "  +
                ");";

        db.execSQL(query);

    }

    public long addProducttoaid(Aid aid)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_IMAGE,aid.getImage());
        values.put(COLOUMN_TITLE,aid.getTitle());
        values.put(COLOUMN_FAVOURITE,0);
        SQLiteDatabase db= getWritableDatabase();
        long id = db.insert(TABLE_AID,null,values);
        db.close();
        return id;
    }

    public long addProducttosymptoms(Symptoms symp)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_IMAGE,symp.getImage());
        values.put(COLOUMN_TITLE,symp.getTitle());
        values.put(COLOUMN_FAVOURITE,0);
        SQLiteDatabase db= getWritableDatabase();
        long id = db.insert(TABLE_SYMPTOMS,null,values);
        db.close();
        return id;
    }

    public long addProducttoselftest(SelfTest self)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_IMAGE,self.getImage());
        values.put(COLOUMN_TITLE,self.getTitle());
        values.put(COLOUMN_FAVOURITE,0);
        SQLiteDatabase db= getWritableDatabase();
        long id = db.insert(TABLE_SELFTEST,null,values);
        db.close();
        return id;
    }

    public void addProducttoaiditem(AidItem aiditem,long id)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_IMAGE,aiditem.getImage());
        values.put(COLOUMN_TITLE,aiditem.getTitle());
        values.put(COLOUMN_DESC,aiditem.getDesc());
        values.put(COLOUMN_FID,id);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_AID_ITEM,null,values);
        db.close();
    }

    public void addProducttosymptomsitem(SymptomsItem sympitem,long id)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_IMAGE,sympitem.getImage());
        values.put(COLOUMN_TITLE,sympitem.getTitle());
        values.put(COLOUMN_DESC,sympitem.getDesc());
        values.put(COLOUMN_FID,id);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_SYMPTOMS_ITEM,null,values);
        db.close();
    }

    public void addProducttoselftestitem(SelfTestItem selfitem,long id)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_IMAGE,selfitem.getImage());
        values.put(COLOUMN_TITLE,selfitem.getTitle());
        values.put(COLOUMN_DESC,selfitem.getDesc());
        values.put(COLOUMN_FID,id);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_SELFTEST_ITEM,null,values);
        db.close();
    }

    public void deleteProduct(String productname){

        SQLiteDatabase db =getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_PRODUCTS + " WHERE " + COLOUMN_PRODUCTNAME + "=\"" + productname + "\";");

    }


    public ArrayList<String> databasetostring(){

        ArrayList<String> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();

        while (!c.isAfterLast())

        {

            if(c.getString(c.getColumnIndex("productname"))!=null)

            {
                listItems.add(c.getString(c.getColumnIndex("productname")));
            }

            c.moveToNext();
        }

        db.close();
        return listItems;
    }


}
