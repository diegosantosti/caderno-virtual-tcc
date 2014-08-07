package com.appoena.mobilenote;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickAddCaderno();
		clickAbout();
	}
	
	//teste willian

	
	
	//M�todo respons�vel pela a��o no bot�o "Adicionar caderno"
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_adicionar_caderno);
				
			}
		});


	}
	
	//M�todo respons�vel pela a��o no bot�o "Sobre"
	private void clickAbout() {
		Button btnAbout = (Button) findViewById(R.id.btn_about);
		btnAbout.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_sobre);
				
			}
		});
		
	}

}
