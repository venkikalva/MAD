package com.example.group1a_hw05;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

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
public class SignUpActivity extends Activity {
	EditText etName, etEmail, etPassword, etPasswordConfirm,etlName;
	Button bSignUp, bCancel;
	String name, email, password, passwordConfirm,lName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		etName = (EditText) findViewById(R.id.editTextUserName);
		etEmail = (EditText) findViewById(R.id.editTextEmail);
		etPassword = (EditText) findViewById(R.id.editTextPassword);
		etPasswordConfirm = (EditText) findViewById(R.id.editTextPasswordConfirm);
		etlName = (EditText) findViewById(R.id.editTextLastName);
		

		bSignUp = (Button) findViewById(R.id.buttonSignup);
		bCancel = (Button) findViewById(R.id.buttonCancel);

		bSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				name = etName.getText().toString();
				lName = etlName.getText().toString();
				email = etEmail.getText().toString();
				password = etPassword.getText().toString();
				passwordConfirm = etPasswordConfirm.getText().toString();
				if (name.equals("") ||lName.equals("") || email.equals("") || password.equals("")
						|| passwordConfirm.equals("")) {
					Toast.makeText(SignUpActivity.this, "Enter required and vallid data",
							Toast.LENGTH_SHORT).show();
				} else {
					if (password.equals(passwordConfirm)) {
						signUp();
					} else
						Toast.makeText(SignUpActivity.this, "Password does not match",
								Toast.LENGTH_SHORT).show();
					etPassword.setText("");
					etPasswordConfirm.setText("");
				}
			}
		});

		bCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
				startActivity(intent);
				SignUpActivity.this.finish();
			}
		});
	}

	public void signUp() {
		ParseUser user = new ParseUser();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.put("fName", name);
		user.put("lName", lName);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					Log.d("InClass08", "Sign Up successfull");
					settingCurrentUser();
					Toast.makeText(SignUpActivity.this, name + " Successfully Logged In", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(SignUpActivity.this, TabViewActivity.class);
					startActivity(intent);
					SignUpActivity.this.finish();
				} else {
					Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

	public void settingCurrentUser() {
		ParseUser.becomeInBackground("session-token-here", new LogInCallback() {
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// The current user is now set to user.
					Log.d("DemoParse", "The current user is now set to user.");
				} else {
					// The token could not be validated.
					Log.d("DemoParse", "The token could not be validated.");
				}
			}
		});
	}

}
