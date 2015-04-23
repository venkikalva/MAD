package com.example.group1a_hw05;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
/*
 * Team : Ashraf Cherukuru, Savitha Doure, Venkatesh Kalva
 * */
public class LoginActivity extends Activity {
	EditText etEmail, etPassword;
	Button bLogin, bCreateAcct;
	String email, password;
	ParseObject parseObject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		
		/*Parse.initialize(this, "o5hvWIXPl0iYxLty9meLyhzoQ4XBlE7Rwz9X85GS",
				"0CfuSfArKp0YYt3DShN9sYerQiuOENEgV11ATqO1");
		*/
		Parse.initialize(this, "6MLGqkQz8v7drYBU7wKFaKbBdn2apWjoylkiXJ26", "8HTkoAQvSj9SKjabEsxuiO3fYwViOfH7MhGd831I");
		etEmail = (EditText) findViewById(R.id.editTextEmail);
		etPassword = (EditText) findViewById(R.id.editTextPassword);

		bLogin = (Button) findViewById(R.id.buttonLogin);
		bCreateAcct = (Button) findViewById(R.id.buttonCreateNewAccount);

		checkForCurrentUser();

		bCreateAcct.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
				startActivity(intent);
				LoginActivity.this.finish();
			}
		});

		bLogin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				logginIn();
			}
		});

	}
	
	public void checkForCurrentUser() {
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
			Log.d("InClass08", currentUser.getUsername() + "Is current User");
			Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
			startActivity(intent);
			this.finish();

		} else {
			// show the signup or login screen
			Log.d("InClass08",
					"No Current User. show the signup or login screen");
		}
	}

	public void logginIn() {
		email = etEmail.getText().toString();
		password = etPassword.getText().toString();
		if (email.equals("") || password.equals("")) {
			Toast.makeText(LoginActivity.this, "Enter the required data",
					Toast.LENGTH_SHORT).show();
		} else {
			ParseUser.logInInBackground(email, password, new LogInCallback() {
				public void done(ParseUser user, ParseException e) {
					if (user != null) {
						Log.d("InClass08", "The user is logged in.");
						settingCurrentUser();
						checkForCurrentUser();
					} else {
						Log.d("InClass08",
								"Login failed: " + e.getLocalizedMessage());
						Toast.makeText(LoginActivity.this,
								e.getLocalizedMessage(), Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		}

	}

	public void settingCurrentUser() {
		ParseUser.becomeInBackground("session-token-here", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// The current user is now set to user.
					Log.d("InClass08", "The current user is now set to user.");
				} else {
					// The token could not be validated.
					Log.d("InClass08", "The token could not be validated.");
				}
			}
		});
	}
	
	
}
