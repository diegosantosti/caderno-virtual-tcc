package com.appoena.mobilenote.Screens;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.appoena.mobilenote.AdapterGridCaderno;
import com.appoena.mobilenote.Caderno;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.Screens.CustomDialog.CustomDialogListener;

public class MainActivity extends Activity implements CustomDialogListener{
	

	private GridView gridView;
	private ArrayList<Caderno> arrayCaderno;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arrayCaderno = new ArrayList<Caderno>();
		clickAddCaderno();
		clickAbout();
		clickAgenda();
		//teste
		gridView= (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new AdapterGridCaderno(this, arrayCaderno));
	}
	
	//Método responsável pela ação no botão "Adicionar caderno"
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				showDialog();
			}
		});
	}
	
	//Método responsável pela ação no botão "Sobre"
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
		String caderno = params.getString("CADERNO");
		String cor = params.getString("COR");
		Caderno c = new Caderno(caderno, cor);
		arrayCaderno.add(c);
		gridView.setAdapter(new AdapterGridCaderno(getApplicationContext(), arrayCaderno));
		
	}
	
	void showDialog(){
		CustomDialog customDialog = CustomDialog.newInstance();
		customDialog.show(getFragmentManager(), null);
		
	}


}
