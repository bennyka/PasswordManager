package de.fh.passwordmanager.dataHandler;

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
