package com.appoena.mobilenote.screens;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.appoena.mobilenote.R;

public class SplashActivity extends Activity implements Runnable{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		Handler h = new Handler();
		h.postDelayed(this, 3000);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

}
