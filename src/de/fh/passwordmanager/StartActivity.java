package de.fh.passwordmanager;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import de.fh.passwordmanager.MyPreferences.MyPreferences;
import de.fh.passwordmanager.activites.ListsActivity;


public class StartActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		new MyPreferences();
		
		setContentView(R.layout.activity_main);
		
		//TODO Überprüfung ob ein Masterpasswort gesetzt ist
		/*
		 * Leider ist diese Funktionalität nicht mehr fertig geworden, das schreiben in die Preferences funktioniert nicht
		 */
//		MyPreferences MainPassword = new MyPreferences();
//		if (MainPassword.mainPasswordExists()){
//			setContentView(R.layout.activity_main);
//		} else {
//			Intent intent = new Intent(this, SetMasterPasswordActivity.class);
//			startActivity(intent);
//		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    return super.onCreateOptionsMenu(menu);
	}
	
	public void startApp(View view){
		
		// Ein AlterDialog wird erstellt
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
 
			// Titel des AlertDialog wird gesetzt
			alertDialogBuilder.setTitle("@string/hint");
 
			// Der MessageBody wird mit Text gefüllt
			alertDialogBuilder
				.setMessage("@string/dialog_wrongPassword")
				.setCancelable(false)
				.setPositiveButton("@string/ok",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int id) {
						// Beim onClick wird der Dialog geschlossen
						dialog.cancel();
					}
				  });
				alertDialogBuilder.create();
				
		// Das MainPassword wird ausgelesen und in einen String umgewandelt
		EditText passwordTextField = (EditText) findViewById(R.id.textfield_mainPassword);
		passwordTextField.getText().toString();
		
		// App Login: Das MainPassword wird mit dem in der App hinterlegten Passwort verglichen
//		if (mainPassword.equals("ja")){
			Intent intent = new Intent(this, ListsActivity.class);
			startActivity(intent);
//		} else {
//			// Der oben erstellte AlertDialog wird angezeigt, wenn das falsche Passwort eingegeben wurde
//			alertDialog.show();
//		};
	}
}
