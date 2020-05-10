package com.missouristate.hudson.bankapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bankAppDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_ACCOUNT = "accountTable";
    private static final String KEY = "id";
    private static final String COLUMN2_ACCOUNT_NUMBER = "accountNumber";
    private static final String COLUMN3_NAME = "name";
    private static final String COLUMN4_BALANCE = "balance";



    public DatabaseManager(Context context) {
        super (context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // build SQL create database statement that will create the database for us
        // this uses SQL statements
        String sqlCreate = "create table " + TABLE_ACCOUNT + "( "+ KEY; sqlCreate += " integer primary key autoincrement, " + COLUMN2_ACCOUNT_NUMBER;
        sqlCreate += " real, " + COLUMN3_NAME;
        sqlCreate += " text, " + COLUMN4_BALANCE + " real )" ;

        db.execSQL( sqlCreate );

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TABLE_ACCOUNT );
        // Re-create tables
        onCreate( db );

    }


    public void prePopulateAccounts(int accountNumber, String name, double balance) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN2_ACCOUNT_NUMBER, accountNumber);
        contentValues.put(COLUMN3_NAME, name);
        contentValues.put(COLUMN4_BALANCE, balance);
        db.insert(TABLE_ACCOUNT, null, contentValues);

        db.close();
    }


    public void updateBalance( int accountNumber, double balance ) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlUpdate = "update " + TABLE_ACCOUNT + " set " + COLUMN4_BALANCE + " = " + balance + " where " + COLUMN2_ACCOUNT_NUMBER + " = " + accountNumber;

        db.execSQL( sqlUpdate );
        db.close( );

    }

    public BankAccount selectByAcctNo( int accountNumber ) {
        String sqlQuery = "select * from " + TABLE_ACCOUNT;
        sqlQuery += " where " + COLUMN2_ACCOUNT_NUMBER + " = " + accountNumber;

        SQLiteDatabase db = this.getWritableDatabase( );
        Cursor cursor = db.rawQuery( sqlQuery, null );

        BankAccount account = null;
        if( cursor.moveToFirst( ) )
            account = new BankAccount( cursor.getInt( 0 ),
                    cursor.getString( 1 ), cursor.getDouble( 2 ) );
        return account;
    }

}