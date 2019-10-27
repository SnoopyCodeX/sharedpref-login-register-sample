package com.cdph.logreg.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cdph.logreg.demo.model.Constants;

public class RegisterActivity extends Activity implements View.OnClickListener
{
	private SharedPreferences prefs;
	private Button btn_login, btn_register;
	private EditText input_user, input_pass;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
		this.btn_login = (Button) findViewById(R.id.loginbtn);
		this.btn_register = (Button) findViewById(R.id.btnregister);
		this.input_user = (EditText) findViewById(R.id.username);
		this.input_pass = (EditText) findViewById(R.id.password);
		
		this.btn_login.setOnClickListener(this);
		this.btn_register.setOnClickListener(this);
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		boolean isLoggedIn = prefs.getBoolean(Constants.AccountCredentials.KEY_IS_LOGGED_IN, false);
		if(isLoggedIn)
		{
			startActivity(new Intent(this, MainActivity.class));
			finish();
		}
	}

	@Override
	public void onClick(View view)
	{
		int id = view.getId();

		switch(id)
		{
			case R.id.btnregister:
				String user = input_user.getText().toString();
				String pass = input_pass.getText().toString();
				registerAccount(user, pass);
			break;

			case R.id.loginbtn:
				startActivity(new Intent(this, LoginActivity.class));
				finish();
			break;
		}
	}

	private void registerAccount(String username, String password)
	{
		//Get the saved username from SharedPreferences
		String s_user = prefs.getString(Constants.AccountCredentials.KEY_USERNAME, "");

		//Check if username and password is blank/empty
		if(username.equals("") && password.equals(""))
		{
			input_user.setError("Username is blank");
			input_pass.setError("Password is blank");
			return;
		}
		if(username.equals(""))
		{
			input_user.setError("Username is blank");
			return;
		}
		if(password.equals(""))
		{
			input_pass.setError("Password is blank");
			return;
		}

		//Check if username already exists
		if(s_user.equals(username))
		{
			Toast.makeText(this, "Username already exists!", Toast.LENGTH_LONG).show();
			return;
		}

		//Save the username and password
		prefs.edit().putString(Constants.AccountCredentials.KEY_USERNAME, username).commit();
		prefs.edit().putString(Constants.AccountCredentials.KEY_PASSWORD, password).commit();
		prefs.edit().putBoolean(Constants.AccountCredentials.KEY_IS_LOGGED_IN, false).commit();
		Toast.makeText(this, "Successfully registered!", Toast.LENGTH_LONG).show();
		
		//Go back to Login Panel
		startActivity(new Intent(this, LoginActivity.class));
		finish();
	}
}
