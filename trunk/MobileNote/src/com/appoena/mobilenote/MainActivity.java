package com.appoena.mobilenote;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.appoena.mobilenote.CustomDialog.CustomDialogListener;

public class MainActivity extends Activity implements CustomDialogListener{
	


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickAddCaderno();
		clickAbout();
		clickAgenda();
	}
	
	//M�todo respons�vel pela a��o no bot�o "Adicionar caderno"
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}
	
	//M�todo respons�vel pela a��o no bot�o "Sobre"
	private void clickAbout() {
		Button btnAbout = (Button) findViewById(R.id.btn_about);
		btnAbout.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, ActivitySobre.class);
				startActivity(it);
			}
		});
		
	}
	
	private void clickAgenda() {
		
		Button btnAgenda = (Button) findViewById(R.id.btn_calendar);
		btnAgenda.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent it = new Intent(MainActivity.this, ActivityAgenda.class);
				startActivity(it);
			}
		});

	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onDialogPositiveClick(DialogFragment dialog, Bundle params) {
		String teste = params.getString("CADERNO");
		Toast.makeText(this, teste, Toast.LENGTH_SHORT).show();
		
	}
	
	void showDialog(){
		CustomDialog customDialog = CustomDialog.newInstance();
		customDialog.show(getFragmentManager(), null);
		
	}

}
