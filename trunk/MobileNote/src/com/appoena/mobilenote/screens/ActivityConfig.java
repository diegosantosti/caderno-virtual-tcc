package com.appoena.mobilenote.screens;

import com.appoena.mobilenote.R;
import com.dropbox.sync.android.DbxAccountManager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ActivityConfig extends Activity{
	
	private Button btnDropLogout;
	private Button btnDropLogin;
	private final String APP_KEY="tgqewuej4tssn7n";
	private final String APP_SECRET="vci78s22idpmzbq";
	private DbxAccountManager mAccountManager;
	
	
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
	
	/**
	 * Recebe o status de login com o drop
	 * e mostra/esconde os botoes de login/logout
	 */
	public void enableView(Boolean status){
		if(status){
			btnDropLogout.setVisibility(View.VISIBLE);
			btnDropLogin.setVisibility(View.GONE);
		}else{
			btnDropLogout.setVisibility(View.GONE);
			btnDropLogin.setVisibility(View.VISIBLE);
		}
		
		
	}
	
	public void loginDrop(View view){
		btnDropLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAccountManager.startLink(ActivityConfig.this, 0);
				
			}
		});
		
	}
	
	public void logoutDrop(View view){
		btnDropLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAccountManager.unlink();
				enableView(mAccountManager.hasLinkedAccount());
				
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0){
			if(resultCode==RESULT_OK){
				enableView(mAccountManager.hasLinkedAccount());
			}
			
		}else{
			super.onActivityResult(requestCode, resultCode, data);
		}
		
	}
	


}
