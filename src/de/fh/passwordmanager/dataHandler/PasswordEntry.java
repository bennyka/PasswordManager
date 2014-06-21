package de.fh.passwordmanager.dataHandler;

/*
 * Diese Funktion dient zur Verwaltung der Datenbankeinträge
 */
public class PasswordEntry 
{
	private String name;
	private String password;
	
	public PasswordEntry(String name, String password2)
	{
		this.name = name;
		this.password = password2;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public String getPassword()
	{
		return this.password;
	}
}
