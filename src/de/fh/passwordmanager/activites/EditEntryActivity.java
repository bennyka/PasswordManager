package de.fh.passwordmanager.activites;


import de.fh.passwordmanager.R;
import de.fh.passwordmanager.dataHandler.DatabaseManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * Diese Klasse dient zur Bearbeitung eines Eintrages
 */
public class EditEntryActivity extends Activity {
	
	// Initiieren von globalen Variablen
	String entryName;
	String entryPassword;
	String newName;
	String newPassword;
	
	boolean passwordHidden = true;
	boolean newEntry = true;
	byte[] encryptPassword;
	byte[] decryptedPassword;
	
	// Initiieren von globalen Objekten (TextView und Edittext)
	TextView textview_title;
	EditText edittext_password;
	
	// Datenbank laden
	DatabaseManager databaseManager = new DatabaseManager(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		// Titel der ActionBar festlegen
		getActionBar().setTitle("Eintrag bearbeiten");
		
		// Der EntryActivity wird ein Layout zugewiesen
		setContentView(R.layout.activity_edit_entry);
		super.onCreate(savedInstanceState);
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Einbinden des ActionMenu's
		getMenuInflater().inflate(R.menu.entry_menu, menu);
		
		// Die Objekte werden Passwort und Name werden jeweils in eine Variable gespeichert
		textview_title = (TextView)findViewById(R.id.textfield_title);
		edittext_password = (EditText)findViewById(R.id.textfield_password);
	
		// Die Extras werden aus dem Intent ausgelesen
		final Bundle startActivityIntent = getIntent().getExtras();
		
		// Prüfung ob Extras in dem Intent vorhanden sind
		if (startActivityIntent != null){
			
			// Auslesen der Intent Informationen
			entryName = (String) startActivityIntent.get("name");
			entryPassword = (String) startActivityIntent.get("password");
			
			// Die Felder werden vorausgefüllt mit Passwort und Name
			textview_title.setText(entryName);
			
			
			// entschlüsseln des passwortes
			edittext_password.setText(entryPassword.toString());
			
			// Dieser Eintrag ist kein neuer Eintrag, Flag wird auf false gesetzt
			newEntry = false;
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	/*
	 *  Wird der Backbutton gedrückt, wird die saveEntry Funktion ausgeführt und die aktuelle Activity beendet
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		updateEntry();
	    finish();
	    return;
	} 
	/*
	 * Funktion zum löschen eines Eintrages
	 */
	public void deleteEntry(){
		
		// Ein AlterDialog wird erstellt
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
			// Titel des AlertDialog wird gesetzt
			alertDialogBuilder.setTitle("Hinweis");
 
			// Der MessageBody wird mit Text gefüllt
			alertDialogBuilder
			.setMessage("Möchten Sie den Eintrag wirklich löschen?")
			.setCancelable(false)
			.setNegativeButton("Nein",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// Beim onClick wird der Dialog geschlossen
					dialog.cancel();
				}
			})
			.setPositiveButton("Ja",new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// Beim onClick wird der Eintrag gelöscht
					dialog.cancel();
					String newName= textview_title.getText().toString();
					databaseManager.DeletePassword(newName);
					finish();
				}
			  });
			
			// Eine Instanz des AlertDialogs wird erstellt
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
	}

	/*
	 * Die saveEntry Funktion speichert den aktuell angezeigten Eintrag in der Datenbank
	 */
	public Boolean saveEntry(){
		Boolean success = false;
		// Auslesen der aktuellen Textfeldinhalte
		newPassword = edittext_password.getText().toString();
		newName = textview_title.getText().toString();
		
		if (newPassword != entryPassword || newName != entryName){
			if (!newEntry){
				updateEntry();
			} else {
				// Gibt zurück ob die Speicherung erfolgreich war;
				success = createNewEntry();
			};
		}
		return success;
	}
	
	/*
	 * Die updateEntry Funktion updatet einen Datenbankeintrag
	 */
	public void updateEntry(){
		boolean success = databaseManager.UpdatePassword(newName, newPassword);
		if (success){
			Toast.makeText(this,  "update entry: "+newPassword+" x "+newName, Toast.LENGTH_SHORT).show();
		};
	}
	
	/*
	 *  Die createNewEntry Funktion dient der Speicherung von neuen Einträgen
	 */
	public Boolean createNewEntry(){
		Boolean success = false;
		if (newPassword != null && newName != null && !newPassword.isEmpty() && !newName.isEmpty()){
			// Titel und Passwort in DB schreiben
			databaseManager.SavePassword(newName, newPassword);	
			System.out.println("infos: "+newName+", "+newPassword);
			Toast.makeText(this,  "create new entry", Toast.LENGTH_SHORT).show();
			
			success = true;
		} else {
			
			// Ein AlterDialog wird erstellt
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	 
			// Titel des AlertDialog wird gesetzt
			alertDialogBuilder.setTitle("Hinweis");
 
			// Der MessageBody wird mit Text gefüllt
			alertDialogBuilder
				.setMessage("Die eingegebenen Daten sind unvollständig. Bitte geben sein ein Passwort und einen Namen ein")
				.setCancelable(false)
				.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// Beim onClick wird der Dialog geschlossen
						dialog.cancel();
					}
				  });
			// Eine Instanz des AlertDialogs wird erstellt
			AlertDialog alertDialog = alertDialogBuilder.create();
			alertDialog.show();
			
		}
		return success;
	}
	
	/*
	 * Diese Funktion wird beim Klick auf die ImageView ausgeführt und dient dem Wechsel zwischen verstecken und anzeigen des Passworts
	 */	
	// click on actionmenu
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.action_save:
			Boolean success = saveEntry();
			// Nur wenn die Speicherung erfolgreich war wird die Activity geschlossen
			if (success == true){
				finish();
			};
		return true;
		
		case R.id.action_delete:
			deleteEntry();
		return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	/*
	 * Diese Funktion wird beim Klick auf die ImageView ausgeführt und dient dem Wechsel zwischen verstecken und anzeigen des Passworts
	 */
	public void triggerPassword(View view){
		// Das ImageView Objekt wird ausgelesen und in einer Variable gespeichert
		ImageView triggerPW = (ImageView) findViewById(R.id.imageView_triggerPassword);
		
		// Prüfung auf das globale Flag passwordHidden
		if (passwordHidden){
			// Wenn das Passwort versteckt ist
			// Der ImageView wird ein neues Icon zugewiesen, welches verdeutlicht, dass das Passwort wieder versteckt werden kann
			triggerPW.setImageResource(R.drawable.ic_secure);
			// Der Transformationtype des Edittext Objektes wird auf null gesetzt, somit ist das Passwort sichtbar
			edittext_password.setTransformationMethod(null);
			// Ein Toast mit dem Hinweis "sichtbar" wird ausgegeben
			Toast.makeText(this,  "Passwort ist sichtbar", Toast.LENGTH_SHORT).show();
		} else {
			// Wenn das Passwort sichtbar ist
			// Der ImageView wird ein neues Icon zugewiesen, welches verdeutlicht, dass das Passwort wieder angezeigt werden kann
			triggerPW.setImageResource(R.drawable.ic_menu_view);
			// Dem Edittext Objekt wird der Transformationtype PasswordTransformation zugewiesen, dadurch wird das Passwort versteckt
			edittext_password.setTransformationMethod(new PasswordTransformationMethod());
			// Ein Toast mit dem Hinweis "versteckt" wird ausgegeben
			Toast.makeText(this,  "Passwort ist versteckt", Toast.LENGTH_SHORT).show();
		}
		// Das globale Flag wird dem aktuellen Gegenteil zugewiesen
		passwordHidden = !passwordHidden;
	}
}
