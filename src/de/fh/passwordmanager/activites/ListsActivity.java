package de.fh.passwordmanager.activites;

import java.util.ArrayList;

import de.fh.passwordmanager.R;
import de.fh.passwordmanager.dataHandler.DatabaseManager;
import de.fh.passwordmanager.dataHandler.PasswordEntry;
import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

/*
 * Diese Klasse dient der Anzeige der in der DB gespeicherten Passwörter
 */
public class ListsActivity extends ListActivity {
	
	DatabaseManager databaseManager = new DatabaseManager(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Die ActionBar wird angepasst, Titel, Navigation und Icon werden gesetzt
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(""); 
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setIcon(R.drawable.ic_launcher_unlocked);
		
		// Der ListsActivity wird ein Layout zugewiesen
		setContentView(R.layout.activity_entrylist);

		// Datenbank wird geladen
		
		
		// Eine Instanz des PasswordArrayAdapters wird erstellt
		PasswordArrayAdapter adapter = new PasswordArrayAdapter(this, databaseManager.GetPasswords());

		// Der oben erstellte Adapter wird zugewiesen
		setListAdapter(adapter); 
		
	}
	
	/*
	 * Funktion zum Export Einträge mit Name und Passwort
	 */
	public void sendMail(){
		ArrayList<PasswordEntry> array = databaseManager.GetPasswords();
		
		String message = "";
		// Die Arraylist wird in einer For Schleife durchlaufen und die Einträge in einen String gespeichert
		for (int i = 0; i < array.size(); i++)
		{
			message = message +"Name: "+array.get(i).getName() + ", Passwort: " + array.get(i).getPassword() + "\r\n";
		}
		// Erstellen der Email
		Intent sendIntent = new Intent(Intent.ACTION_SEND);
		sendIntent.setType("message/rfc822");
		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Export aus PasswordManager");
		sendIntent.putExtra(Intent.EXTRA_TEXT, message);
		sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
		startActivity(Intent.createChooser(sendIntent, "Send email..."));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		this.onCreate(null);
	}

	/*
	 * Diese Funktion dient dem auslesen der Position im Array des Eintrages welcher angeklickt wurde
	 * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
	 */
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		PasswordEntry password = (PasswordEntry)l.getItemAtPosition(position);		
		// Ein neues Intent wird erstellt, um die EntryActivity anzeigen zu können
		Intent intent = new Intent(this, EditEntryActivity.class);
		// Die aktuelle Position im Array des angegklickten Objektes wird dem Intent zugewiesen
		//intent.putExtra("listId",position);
		intent.putExtra("name", password.getName());
		intent.putExtra("password", password.getPassword());
		// Die Activity wird gestartet.
		startActivity(intent);		
		super.onListItemClick(l, v, position, id);
	}
	/*
	 * Die EntryActivity wird aufgerufen um einen neuen Eintrag zu erstellen
	 */
	public void createNewEntry(){
		Intent intent = new Intent(this, EditEntryActivity.class);
		startActivity(intent);	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Der aktuellen Activity wird ein Menu zugewiesen
		getMenuInflater().inflate(R.menu.entrylist_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	/*
	 * Hier wird der Klick auf den Add Button abgefangen und eine Funktion beim Klick aufgerufen
	 * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.action_add:
			createNewEntry();
			return true;
		case R.id.action_send:
			sendMail();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}