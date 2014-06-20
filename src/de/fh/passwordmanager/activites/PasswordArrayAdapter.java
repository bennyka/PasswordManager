package de.fh.passwordmanager.activites;

import java.util.ArrayList;

import de.fh.passwordmanager.dataHandler.PasswordEntry;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;



public class PasswordArrayAdapter extends ArrayAdapter<PasswordEntry>
{
	private ArrayList<PasswordEntry> _passwords = null;

	public PasswordArrayAdapter(Context context, ArrayList<PasswordEntry> passwords) 
	{
		super(context, de.fh.passwordmanager.R.layout.passwordentry_layout, passwords);
		
		this._passwords = passwords;
	}
	
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		if(null == convertView)
			convertView = ((LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(de.fh.passwordmanager.R.layout.passwordentry_layout, parent, false);
	
		((TextView)convertView.findViewById(de.fh.passwordmanager.R.id.layoutPassword_PasswordName)).setText(this._passwords.get(position).getName());
		
		return convertView;
	}

}
