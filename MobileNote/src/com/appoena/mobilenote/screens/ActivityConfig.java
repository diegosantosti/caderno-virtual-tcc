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
import android.widget.Toast;

public class ActivityConfig extends Activity{
	
	private Button btnDropLogout;
	private Button btnDropLogin;
	private static final String APP_KEY="tgqewuej4tssn7n";
	private static final String APP_SECRET="vci78s22idpmzbq";
	private DbxAccountManager mAccountManager;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(R.string.save);
		mAccountManager = DbxAccountManager.getInstance(getApplication(), APP_KEY, APP_SECRET);
		btnDropLogin = (Button)findViewById(R.id.btn_login);
		btnDropLogout = (Button)findViewById(R.id.btn_logout);
		clickOnLoginDrop();
		clickOnLogoutDrop();
		enableView(mAccountManager.hasLinkedAccount());
		
		
		
		
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
	
	public void clickOnLoginDrop(){
		btnDropLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAccountManager.startLink(ActivityConfig.this, 0);
				
			}
		});
		
	}
	
	public void clickOnLogoutDrop(){
		btnDropLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mAccountManager.unlink();
				enableView(mAccountManager.hasLinkedAccount());
				if(!mAccountManager.hasLinkedAccount()) Toast.makeText(getApplicationContext(), R.string.dropbox_excluido, Toast.LENGTH_SHORT).show();
				
				
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode==0){
			if(resultCode==RESULT_OK){
				enableView(mAccountManager.hasLinkedAccount());
				if(mAccountManager.hasLinkedAccount()) Toast.makeText(getApplicationContext(), R.string.dropbox_vinculado, Toast.LENGTH_SHORT).show();
			}
			
		}else{
			super.onActivityResult(requestCode, resultCode, data);
		}
		
	}
	


}
