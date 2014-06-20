// $Id$
 
package de.fh.passwordmanager.MyPreferences;
import java.util.prefs.*;

public class MyPreferences
{	
	static Preferences root = Preferences.userNodeForPackage(MyPreferences.class);
	
	static public void setMainPassword( String password ) throws Exception {
		System.out.println("password1: "+password);
		root = Preferences.userNodeForPackage(MyPreferences.class);
		System.out.println("password2: "+password);
	    root.put( "MainPassword", password);
	    System.out.println("password3: "+password);
	    root.exportSubtree( System.out );
	}
  
	public Boolean getMainPassword(){
		if (root.get("MainPassword", "false").equals("false")){
			return false;
		} else {
			return true;
		}
	} 
}
