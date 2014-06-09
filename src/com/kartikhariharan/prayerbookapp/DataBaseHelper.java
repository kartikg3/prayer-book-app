package com.kartikhariharan.prayerbookapp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	//Path to the device folder with databases
    public static String DB_PATH;

    //Database file name
    public static String DB_NAME;
    public SQLiteDatabase database;
    public final Context context;

    public SQLiteDatabase getDb() {
    	return database;
    }

     public DataBaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        this.context = context;
		//Write a full path to the databases of your application
		String packageName = context.getPackageName();
		DB_PATH = String.format("//data//data//%s//databases//", packageName);
		DB_NAME = databaseName;
		openDataBase();
    }

    //This piece of code will create a database if it�s not yet created
    public void createDataBase() {
		boolean dbExist = checkDataBase();
		if (!dbExist) {
		    this.getReadableDatabase();
		    try {
		        copyDataBase();
		    } catch (IOException e) {
		        Log.e(this.getClass().toString(), "Copying error");
		        throw new Error("Error copying database!");
		    }
		} else {
		    Log.i(this.getClass().toString(), "Database already exists");
		}
    }

    //Performing a database existence check
    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try {
            String path = DB_PATH + DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(path, null,
                          SQLiteDatabase.OPEN_READONLY);
        } catch (SQLException e) {
            Log.e(this.getClass().toString(), "Error while checking db");
        }
        //Android doesn�t like resource leaks, everything should 
        // be closed
        if (checkDb != null) {
            checkDb.close();
        }
        return checkDb != null;
    }

    //Method for copying the database
    private void copyDataBase() throws IOException {
        //Open a stream for reading from our ready-made database
        //The stream source is located in the assets
        InputStream externalDbStream = context.getAssets().open(DB_NAME);

         //Path to the created empty database on your Android device
        String outFileName = DB_PATH + DB_NAME;

         //Now create a stream for writing the database byte by byte
        OutputStream localDbStream = new FileOutputStream(outFileName);

         //Copying the database
        byte[] buffer = new byte[1024];
        int bytesRead;
        
        while ((bytesRead = externalDbStream.read(buffer)) > 0) {
        	
            localDbStream.write(buffer, 0, bytesRead);
            
        }
        
        //Don�t forget to close the streams
        localDbStream.close();
        externalDbStream.close();
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        String path = DB_PATH + DB_NAME;
        if (database == null) {
            createDataBase();
            database = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);
        }
        return database;
    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
    
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	
    }

}
