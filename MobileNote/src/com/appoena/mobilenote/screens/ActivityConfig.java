package com.appoena.mobilenote.screens;

import com.appoena.mobilenote.R;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ActivityConfig extends Activity{
	
	Button btnDropLogout;
	Button btnDropLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.save);
		
		btnDropLogin = (Button)findViewById(R.id.btn_login);
		btnDropLogout = (Button)findViewById(R.id.btn_logout);
		
		//Nao mostra botao logout, criar metodo paara automatizar
		btnDropLogout.setVisibility(View.GONE);
		
		
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			
			break;

		default:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.config, menu);
		return super.onCreateOptionsMenu(menu);
	}
	


}
