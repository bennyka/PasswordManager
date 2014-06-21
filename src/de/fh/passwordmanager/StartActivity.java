package de.fh.passwordmanager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import de.fh.passwordmanager.activites.ListsActivity;
import de.fh.passwordmanager.dataHandler.DatabaseManager;

/*
 * Diese Klasse dient zur Eingabe und Überprüfung des Masterpasswortes
 * Es ist die Startactivity und bildet den "Eingang" in die App
 */
public class StartActivity extends Activity {
	
	// Datenbank laden
	DatabaseManager databaseManager = new DatabaseManager(this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Überprüfung ob ein Masterpasswort gesetzt ist
		if (databaseManager.ExistsMainPassword() == true){
			setContentView(R.layout.activity_main);
			
		} else {
			Intent intent = new Intent(this, SetMasterPasswordActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    return super.onCreateOptionsMenu(menu);
	}
	/*
	 * Diese Funktion prüft das eingegebene Passwort und gleicht es mit dem gespeicherten Passwort ab
	 */
	public void startApp(View view){
		
		// Ein AlterDialog wird erstellt
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
			// Titel des AlertDialog wird gesetzt
			alertDialogBuilder.setTitle("Hinweis");
 
			// Der MessageBody wird mit Text gefüllt
			alertDialogBuilder
				.setMessage("Sie haben ein falsches Passwort eingegeben. Bitte versuchen Sie es erneut.")
				.setCancelable(false)
				.setPositiveButton("Okay",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// Beim onClick wird der Dialog geschlossen
						dialog.cancel();
					}
				  });
				alertDialogBuilder.create();
				
		// Das MainPassword wird ausgelesen und in einen String umgewandelt
		TextView masterPassword = (TextView)findViewById(R.id.textfield_mainPassword);
		// App Login: Das MainPassword wird mit dem in der App hinterlegten Passwort verglichen
		if (masterPassword.getText().toString().equals(databaseManager.GetMainPassword())){
			Intent intent = new Intent(this, ListsActivity.class);
			startActivity(intent);
		} else {
			// Der oben erstellte AlertDialog wird angezeigt, wenn das falsche Passwort eingegeben wurde
			alertDialogBuilder.show();
		};
	}
}
