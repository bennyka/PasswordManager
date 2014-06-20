package de.fh.passwordmanager.dataHandler;

import java.util.ArrayList;

import de.fh.passwordmanager.encryption.Encryption;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseManager extends SQLiteOpenHelper {
	
	// Verschlüsselung wird geladen
	Encryption encryption = new Encryption();
	
	public byte[] encryptString(String password){
		byte[] encryptedPassword = encryption.encryptString(password);
		return encryptedPassword;
	}
	
	public String decryptString(byte[] password){
		String decryptedPassword = encryption.decryptString(password);
		return decryptedPassword;
	}

	
	private static final String DB_NAME = "database.db";
	private static final int DB_VERSION = 1;
	private static final String KLASSEN_CREATE = 
			"CREATE TABLE PASSWORD (" +
			"NAME TEXT NOT NULL, "+
			"PASSWORD BLOB" +
			")";
	
	public DatabaseManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(KLASSEN_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE PASSWORD");
	}
	
	public boolean DeletePassword(String name)
	{
		boolean success = false;
		
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			
			success = 1 <= db.delete("PASSWORD", "NAME = ?", new String[] { name });
			db.close();
			
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager", "delete unsuccessfully");
		}
		
		return success;
	}
	
	public boolean SavePassword(String name, String newPassword){
		
		boolean success = false;
		
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put("NAME", name);
			values.put("PASSWORD", newPassword);
//			values.put("PASSWORD", encryptString(newPassword)); TODO Verschlüsselung wird nicht angewandt, da Entschlüsselung nicht funktioniert
			
			db.insert("PASSWORD", null, values);
			db.close();
			
			success = true;
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager", "save unsuccessfully");
		}
		
		return success;
	}

	public boolean UpdatePassword(String name, String newPassword){
		
		boolean success = false;
		
		try
		{
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put("PASSWORD", newPassword);
//			values.put("PASSWORD", encryptString(newPassword)); TODO Verschlüsselung wird nicht angewandt, da Entschlüsselung nicht funktioniert
			
			db.update("PASSWORD", values, "NAME = ?", new String[] { name });	
			db.close();
			
			success = true;
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager", "update unsuccessfully");
		}
		
		return success;
	}
	
	public ArrayList<PasswordEntry> GetPasswords()
	{
		ArrayList<PasswordEntry> passwords = new ArrayList<PasswordEntry>();
		
		try
		{
			SQLiteDatabase db = this.getReadableDatabase();
			
			Cursor c = db.rawQuery("SELECT NAME, PASSWORD FROM PASSWORD", new String[0]);
			
			if(c.moveToFirst())
			{
				do
				{	
					String name = c.getString(0);
//					String password = decryptString(c.getBlob(1)); TODO Problem "last block incomplete in decription"
					String password = String.valueOf(c.getString(1));
					System.out.println("Entschlüsseltes Passwort: "+password);

					passwords.add(new PasswordEntry(name, password));
				}
				while(c.moveToNext());
			}
			
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager", "read databse unsuccessfully");
			Log.d("Databasemanager", e.toString());
		}
		
		return passwords;
	}
	
	
	
}
