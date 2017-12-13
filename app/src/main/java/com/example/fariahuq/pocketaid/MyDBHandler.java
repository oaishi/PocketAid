package com.example.fariahuq.pocketaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

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


    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {

        super(context, DATABASE_NAME, factory, DATABASE_VERSION);

    }

    @Override

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_AID);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SELFTEST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SYMPTOMS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_AID_ITEM);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SELFTEST_ITEM);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SYMPTOMS_ITEM);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVOURITE_AID);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVOURITE_SYMPTOMS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVOURITE_TEST);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CONTACT);
        onCreate(db);

    }

    @Override

    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_AID + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, " + COLOUMN_IMAGE + " TEXT, " + COLOUMN_FAVOURITE + " INTEGER " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SELFTEST + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, " + COLOUMN_IMAGE + " TEXT, " +COLOUMN_FAVOURITE + " INTEGER " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SYMPTOMS + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, "+COLOUMN_IMAGE + " TEXT, "  +COLOUMN_FAVOURITE + " INTEGER " +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_AID_ITEM + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, " +COLOUMN_DESC + " TEXT, " +
                COLOUMN_FID + " INTEGER, " +COLOUMN_IMAGE + " TEXT "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SYMPTOMS_ITEM + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, " +COLOUMN_DESC + " TEXT, " +
                COLOUMN_FID + " INTEGER, " +COLOUMN_IMAGE + " TEXT "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_SELFTEST_ITEM + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, " + COLOUMN_DESC + " TEXT, " +
                COLOUMN_FID + " INTEGER, " +COLOUMN_IMAGE + " TEXT "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_FAVOURITE_AID + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLOUMN_FID + " INTEGER "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_FAVOURITE_TEST + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLOUMN_FID + " INTEGER "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_FAVOURITE_SYMPTOMS + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLOUMN_FID + " INTEGER "  +
                ");";

        db.execSQL(query);

        query = "CREATE TABLE " + TABLE_CONTACT + "(" +
                COLOUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLOUMN_TITLE + " TEXT, " +COLOUMN_DESC + " TEXT " +
                ");";

        db.execSQL(query);

    }

    public long AddProductToAid(Aid aid)
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

    public long AddProductToContact(Contact contact)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_TITLE,contact.getTitle());
        values.put(COLOUMN_DESC,contact.getDesc());
        SQLiteDatabase db= getWritableDatabase();
        long id = db.insert(TABLE_CONTACT,null,values);
        Log.i("widget","adding -"+ contact.getTitle() + Long.toString(id));
        db.close();
        return id;
    }

    public long AddProductToSymptoms(Symptoms symp)
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

    public long AddProductToSelfTest(SelfTest self)
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

    public void AddProductToAidItem(AidItem aiditem, long id)
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

    public void AddProductToSymptomsItem(SymptomsItem sympitem, long id)
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

    public void AddProductToSelfTestItem(SelfTestItem selfitem, long id)
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

    public void AddProductToFavAid(long id)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_FID,id);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_FAVOURITE_AID,null,values);
        db.close();
    }

    public void AddProductToFavTest(long id)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_FID,id);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_FAVOURITE_TEST,null,values);
        db.close();
    }

    public void AddProductToFavSymptoms(long id)
    {
        ContentValues values = new ContentValues();
        values.put(COLOUMN_FID,id);
        SQLiteDatabase db= getWritableDatabase();
        db.insert(TABLE_FAVOURITE_SYMPTOMS,null,values);
        db.close();
    }

    public void DeleteProductFromFavAid(long id)
    {
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_FAVOURITE_AID + " WHERE " + COLOUMN_FID + " = " + id + ";");
        db.close();
    }

    public void DeleteProductFromFavSelfTest(long id){

        SQLiteDatabase db =getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_FAVOURITE_TEST + " WHERE " + COLOUMN_FID + " = " + id + ";");

    }

    public void DeleteProductFromFavSymptoms(long id){

        SQLiteDatabase db =getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_FAVOURITE_SYMPTOMS + " WHERE " + COLOUMN_FID + " = " + id + ";");

    }

    public ArrayList<Aid> DatabaseToStringAid(){

        ArrayList<Aid> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_AID + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Aid ad = new Aid();
            ad.setId(c.getInt(c.getColumnIndex(COLOUMN_ID)));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFavourite(c.getInt(c.getColumnIndex(COLOUMN_FAVOURITE)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<SelfTest> DatabaseToStringTest(){

        ArrayList<SelfTest> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SELFTEST + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            SelfTest ad = new SelfTest();
            ad.setId(c.getColumnIndex(COLOUMN_ID));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFavourite(c.getInt(c.getColumnIndex(COLOUMN_FAVOURITE)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<Symptoms> DatabaseToStringSymptoms(){

        ArrayList<Symptoms> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SYMPTOMS + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Symptoms ad = new Symptoms();
            ad.setId(c.getColumnIndex(COLOUMN_ID));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFavourite(c.getInt(c.getColumnIndex(COLOUMN_FAVOURITE)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<AidItem> DatabaseToStringAidItem(int id){

        ArrayList<AidItem> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        int count = 1;
        String query = "SELECT * FROM " + TABLE_AID_ITEM + " WHERE "+COLOUMN_FID + " = " +id;

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            AidItem ad = new AidItem();
            ad.setId(count++);
            ad.setDesc(c.getString(c.getColumnIndex(COLOUMN_DESC)));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFid(c.getInt(c.getColumnIndex(COLOUMN_FID)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<SelfTestItem> DatabaseToStringTestItem(int id){

        ArrayList<SelfTestItem> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        int count = 1;
        String query = "SELECT * FROM " + TABLE_SELFTEST_ITEM + " WHERE "+COLOUMN_FID + " = " +id;

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            SelfTestItem ad = new SelfTestItem();
            ad.setId(count++);
            ad.setDesc(c.getString(c.getColumnIndex(COLOUMN_DESC)));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFid(c.getInt(c.getColumnIndex(COLOUMN_FID)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<SymptomsItem> DatabaseToStringSymptomsItem(int id){

        ArrayList<SymptomsItem> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        int count = 1;
        String query = "SELECT * FROM " + TABLE_SYMPTOMS_ITEM + " WHERE "+COLOUMN_FID + " = " +id;

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            SymptomsItem ad = new SymptomsItem();
            ad.setId(count++);
            ad.setDesc(c.getString(c.getColumnIndex(COLOUMN_DESC)));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFid(c.getInt(c.getColumnIndex(COLOUMN_FID)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<Aid> DatabaseToStringFavAid(){

        ArrayList<Aid> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_AID + " INNER JOIN "+TABLE_FAVOURITE_AID + " ON " + TABLE_AID
                + "." + COLOUMN_ID + " = " + TABLE_FAVOURITE_AID + "." + COLOUMN_FID;

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Aid ad = new Aid();
            ad.setId(c.getInt(c.getColumnIndex(COLOUMN_ID)));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFavourite(c.getInt(c.getColumnIndex(COLOUMN_FAVOURITE)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<SelfTest> DatabaseToStringFavTest(){

        ArrayList<SelfTest> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVOURITE_TEST + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            SelfTest ad = new SelfTest();
            ad.setId(c.getColumnIndex(COLOUMN_ID));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFavourite(c.getInt(c.getColumnIndex(COLOUMN_FAVOURITE)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<Symptoms> DatabaseToStringFavSymptoms(){

        ArrayList<Symptoms> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_FAVOURITE_SYMPTOMS + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Symptoms ad = new Symptoms();
            ad.setId(c.getColumnIndex(COLOUMN_ID));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setImage(c.getString(c.getColumnIndex(COLOUMN_IMAGE)));
            ad.setFavourite(c.getInt(c.getColumnIndex(COLOUMN_FAVOURITE)));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public ArrayList<Contact> DatabaseToContact(){

        ArrayList<Contact> listItems = new ArrayList<>();;
        SQLiteDatabase db= getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACT + " WHERE 1";

        Cursor c =db.rawQuery(query,null);

        c.moveToFirst();
        while (!c.isAfterLast())
        {
            Contact ad = new Contact();
            ad.setId(c.getColumnIndex(COLOUMN_ID));
            ad.setTitle(c.getString(c.getColumnIndex(COLOUMN_TITLE)));
            ad.setDesc(c.getString(c.getColumnIndex(COLOUMN_DESC)));
            Log.i("widget",ad.getDesc()+ " " + ad.getTitle()+ " " + c.getColumnIndex(COLOUMN_ID));
            listItems.add(ad);
            c.moveToNext();
        }
        db.close();
        return listItems;
    }

    public void UpdateAid(long id,int i)
    {
        SQLiteDatabase db= getWritableDatabase();
        String query = "UPDATE "+ TABLE_AID + " SET " +COLOUMN_FAVOURITE + " = " + Integer.toString(i)
                + " WHERE " + COLOUMN_ID + " = " + Long.toString(id);
        db.execSQL(query);
        if(i==0)
            DeleteProductFromFavAid(id);
        else
            AddProductToFavAid(id);
    }

}
