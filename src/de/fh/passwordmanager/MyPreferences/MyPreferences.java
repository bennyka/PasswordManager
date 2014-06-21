// $Id$
 
package de.fh.passwordmanager.MyPreferences;
import java.util.prefs.*;
/*
 * Die Preferences dienen dazu das Hauptpassword zu speichern
 */
public class MyPreferences
{	
	static Preferences root = Preferences.userNodeForPackage(MyPreferences.class);
	
	static public void setMainPassword( String password ) throws Exception {
		root = Preferences.userNodeForPackage(MyPreferences.class);
	    root.put( "MainPassword", password);
	    root.exportSubtree( System.out );
	    System.out.println("password1: "+password);
	}
	
	/*
	 * Diese Funktion dient der Überprüfung, ob das Mainpassword gesetzt wurde oder nicht
	 */
	public Boolean mainPasswordExists(){
		// Das MainPassword wird aus den Preferences ausgelesen und überprüft ob es vorhanden ist
		if (root.get("MainPassword", "false").equals("false")){
			return false;
		} else {
			return true;
		}
	} 
	/*
	 * Diese Funktion liest das MainPassword aus, um die Eingabe am Gerät abzugleichen
	 */
	public String getMainPassword(){
		String returnVal = "";
		if (mainPasswordExists()){
			returnVal = root.get("MainPassword","DEFAULT");
		}
		return returnVal;
	}
}
