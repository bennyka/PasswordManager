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
	
	/*
	 * Diese Funktion übernimmt die Verschlüsselung
	 */
	public byte[] encryptString(String password){
		byte[] encryptedPassword = encryption.encryptString(password);
		return encryptedPassword;
	}
	
	/*
	 * Diese Funktion übernimmt die Entschlüsselung
	 */
	public String decryptString(byte[] password){
		String decryptedPassword = encryption.decryptString(password);
		return decryptedPassword;
	}

	// Name der Datenbank
	private static final String DB_NAME = "database2.db";
	// Version der Datenbank
	private static final int DB_VERSION = 3;
	// Erstellen der Datenbank
	private static final String KLASSEN_CREATE = 
			"CREATE TABLE PASSWORD (" +
			"NAME TEXT NOT NULL, "+
			"PASSWORD TEXT NOT NULL" +
			")";
	
	public DatabaseManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	// Die Datenbank wird erstellt
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(KLASSEN_CREATE);
	}
	
	// Die Datenbank wird überschrieben
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE PASSWORD");
	}
	/*
	 * Diese Funktion dient zum Löschen von Einträgen aus einer Datenbank
	 */
	public boolean DeletePassword(String name)
	{
		boolean success = false;
		
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getWritableDatabase();
			// Delete Methode der Datenbank wird aufgerufen und der Query welcher den übergebenen Namen Sucht wird der Methode übergeben
			success = 1 <= db.delete("PASSWORD", "NAME = ?", new String[] { name });
			db.close();
			
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_DeletePassword", e.toString());
		}
		
		return success;
	}
	
	public boolean SavePassword(String name, String newPassword){
		
		boolean success = false;
		
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getWritableDatabase();
			
			// Neue Instanz von values erstellen
			ContentValues values = new ContentValues();
			// Name wird in values hinzugefügt
			values.put("NAME", name);
			
			// verschlüsseltes Passwort wird in values hinzugefügt
			values.put("PASSWORD", encryptString(newPassword));
			
			// Name und Passwort werden der Datenbank hinzugefügt
			db.insert("PASSWORD", null, values);
			db.close();
			
			success = true;
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_SavePassword", e.toString());
		}
		
		return success;
	}
	/*
	 * Diese Funktion trägt das MainPassword in die DB ein
	 */
	public boolean SetMainPassword(String newPassword){
		
		boolean success = false;
		
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getWritableDatabase();
			
			// Neue Instanz von values erstellen
			ContentValues values = new ContentValues();
			// Name wird in values hinzugefügt
			values.put("NAME", "MAINPASSWORD");
			
			// verschlüsseltes Passwort wird in values hinzugefügt
			values.put("PASSWORD", encryptString(newPassword));
			
			// Name und Passwort werden der Datenbank hinzugefügt
			db.insert("PASSWORD", null, values);
			db.close();
			
			success = true;
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_SetMainPassword", e.toString());
		}
		return success;
	}
	
	/*
	 * Diese Funktion prüft auf das vorhandensein des MainPasswords in der DB
	 */
	public Boolean ExistsMainPassword(){
		boolean success = false;
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getWritableDatabase();
			
			// SQL Abfrage nach dem Eintrag mit dem Namen MAINPASSWORD
			Cursor c = db.rawQuery("SELECT NAME, PASSWORD FROM PASSWORD WHERE NAME LIKE 'MAINPASSWORD'", new String[0]);
			c.moveToFirst(); 
			
			success = true;
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_ExistsMainPassword", e.toString());
		}
		return success;
	}
	
	/*
	 * Diese Funktion liest das MainPassword aus
	 */
	public String GetMainPassword(){
		String mainPassword = "";
		
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getWritableDatabase();
			
			// SQL Abfrage nach dem Eintrag mit dem Namen MAINPASSWORD
			Cursor c = db.rawQuery("SELECT NAME, PASSWORD FROM PASSWORD WHERE NAME LIKE 'MAINPASSWORD'", new String[0]);
			c.moveToFirst();
			mainPassword = decryptString(c.getBlob(1)); 
			
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_GetMainPassword", e.toString());
		}
		
		return mainPassword;
	}
	
	/*
	 * Diese Funktion dient zum Updaten eines Eintrages.
	 * Lediglich das Passwort kann geupdatet werden.
	 */
	public boolean UpdatePassword(String name, String newPassword){
		
		boolean success = false;
		
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			
			// Passwort wird in values hinzugefügt
			values.put("PASSWORD", encryptString(newPassword));
			
			// values wird dem Eintrag welcher mit dem übergebenen Namen übereinstimmt hinzugefügt
			db.update("PASSWORD", values, "NAME = ?", new String[] { name });	
			db.close();
			
			success = true;
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_UpdatePassword", e.toString());
		}
		
		return success;
	}
	
	/*
	 * Diese Funktion liest alle Einträge aus der Datenbank und übergibt diese an die ListActivity
	 */
	public ArrayList<PasswordEntry> GetPasswords()
	{
		ArrayList<PasswordEntry> passwords = new ArrayList<PasswordEntry>();
		
		try
		{
			// Die Datenbank wird in schreibbaren Zustand geladen
			SQLiteDatabase db = this.getReadableDatabase();
			
			// Alle Einträge aus der Datenbank werden geladen und einem Cursor übergeben
			Cursor c = db.rawQuery("SELECT NAME, PASSWORD FROM PASSWORD", new String[0]);
			
			// Prüfung ob weitere Einträge in der DB vorhanden sind
			if(c.moveToFirst())
			{
				// Schleife: Gehe solange durch bis kein nächster EIntrag vorhanden ist
				do
				{	
					// auslesen des Namen eines Eintrages
					String name = c.getString(0);
					// auslesen des Passwortes eines Eintrages
					String password = decryptString(c.getBlob(1)); 
					
					//Name und Passwort werden einer Instanz eines Passwordentrys übergeben
					passwords.add(new PasswordEntry(name, password));
				}
				while(c.moveToNext());
			}
			
		}
		catch(Exception e)
		{
			Log.d("DatabaseManager_GetPasswords", e.toString());
		}
		
		return passwords;
	}
	
	
	
}
