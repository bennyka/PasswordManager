package de.fh.passwordmanager;

import de.fh.passwordmanager.MyPreferences.MyPreferences;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SetMasterPasswordActivity extends Activity{
	
//	// Laden der Preferences Klasse
	MyPreferences MainPassword = new MyPreferences();
	
	// Deklarieren der Textfelder
	EditText passwordTextField_1;
	EditText passwordTextField_2;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_set_mainpassword);
		
		// Laden der Textfelder
		passwordTextField_1 = (EditText) findViewById(R.id.edittext_masterpassword_1);
		passwordTextField_2 = (EditText) findViewById(R.id.edittext_masterpassword_2);
		
		super.onCreate(savedInstanceState);
	}
	
	public void saveMasterPassword(View view){
		// Laden der aktuellen Inhalte aus den textfeldern
		String password_1 = passwordTextField_1.getText().toString();
		String password_2 = passwordTextField_2.getText().toString();
		
		// Vergleichen der zwei Passwörter
		if (password_1.equals(password_2)){
			try {
				// Aufruf der setMainPassword Methode, um das Passwort in den Preferences zu speichern
				MyPreferences.setMainPassword(password_1);
				// Beenden der Activity
				finish();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Ein AlterDialog wird erstellt
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	 
			// Titel des AlertDialog wird gesetzt
			alertDialogBuilder.setTitle("Hinweis");
 
			// Der MessageBody wird mit Text gefüllt
			alertDialogBuilder
				.setMessage("Die Passwörter stimmen nicht überein. Bitte überprüfen Sie die Passwörter.")
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
	}
}
