package com.cdph.logreg.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.cdph.logreg.demo.model.Constants;

public class MainActivity extends Activity 
{
	private SharedPreferences prefs;
	private TextView user;
	private Button logoutBtn;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.user = (TextView) findViewById(R.id.username);
		this.logoutBtn = (Button) findViewById(R.id.logoutbtn);
		
		this.logoutBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view)
			{
				prefs.edit().putBoolean(Constants.AccountCredentials.KEY_IS_LOGGED_IN, false).commit();
				startActivity(new Intent(MainActivity.this, LoginActivity.class));
				finish();
			}
		});
    }

	@Override
	protected void onResume()
	{
		super.onResume();
		
		boolean isLoggedIn = prefs.getBoolean(Constants.AccountCredentials.KEY_IS_LOGGED_IN, false);
		if(!isLoggedIn)
		{
			this.startActivity(new Intent(this, LoginActivity.class));
			this.finish();
		}
		else
		{
			String username = prefs.getString(Constants.AccountCredentials.KEY_USERNAME, "Unknown User");
			this.user.setText("Welcome back " + username);
		}
	}
}
