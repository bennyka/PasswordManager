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
	
	private static final int STARTACTIVITY = 300;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// laden der Preferences Klasse
		MyPreferences MainPassword = new MyPreferences();
		
		setContentView(R.layout.activity_main);
		
		// Überprüfung ob ein Masterpasswort gesetzt ist
//		if (MainPassword.getMainPassword()){
//			setContentView(R.layout.activity_main);
//		} else {
//			Intent intent = new Intent(this, SetMasterPasswordActivity.class);
//			startActivity(intent);
//		}
	}

	
	
//	public void setMasterpassword(){
//		Preferences root =
//		  EncryptedPreferences.userNodeForPackage(
//		    EncryptedTest.class, secretKey );
//		 
//		root.put( "transparent", "encryption" );
//		 
//		Preferences subnode = root.node( "subnode" );
//		subnode.put( "also", "encrypted" );
//		 
//		root.exportSubtree( System.out );
//	}
	
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
				// Eine Instanz des AlertDialogs wird erstellt
				AlertDialog alertDialog = alertDialogBuilder.create();
				
		// Das MainPassword wird ausgelesen und in einen String umgewandelt
		EditText passwordTextField = (EditText) findViewById(R.id.textfield_mainPassword);
		String mainPassword = passwordTextField.getText().toString();
		
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
