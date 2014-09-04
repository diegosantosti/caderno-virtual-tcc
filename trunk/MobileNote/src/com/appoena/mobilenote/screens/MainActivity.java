package com.appoena.mobilenote.screens;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.appoena.mobilenote.AdapterGridCaderno;
import com.appoena.mobilenote.Caderno;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.screens.CustomDialog.CustomDialogListener;

public class MainActivity extends Activity implements CustomDialogListener{
	

	private GridView gridView;
	private ArrayList<Caderno> arrayCaderno;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		arrayCaderno = new ArrayList<Caderno>();
		arrayCaderno.add(new Caderno("USJT","#FF9326"));
		arrayCaderno.add(new Caderno("FISK","#A3D900"));
		arrayCaderno.add(new Caderno("ULM","#00B2B2"));
		arrayCaderno.add(new Caderno("ABCD","#FF7373"));
		clickAddCaderno();
		clickAbout();
		clickAgenda();
		//teste
		gridView= (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(new AdapterGridCaderno(this, arrayCaderno));
		onClickItemGrid();
		
	}
	
	/*
	 * Método responsável pela ação no botão "Adicionar caderno"
	 */
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				showDialog("ADD_CADERNO");
			}
		});
	}
	
	/*
	 * Método responsável pela ação no botão "Sobre"
	 */
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
	
	/*
	 * Método responsável pela ação no botão "Agenda"
	 */
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
	
	/*
	 * Método responsável pelo clique no item do gridView
	 */
	
	private void onClickItemGrid() {
		gridView.setOnItemClickListener(new GridView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				Caderno c = arrayCaderno.get(position);
				Toast.makeText(getBaseContext(), c.getNome(), Toast.LENGTH_SHORT).show();
				
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
	
	void showDialog(String tag){
		CustomDialog customDialog = CustomDialog.newInstance();
		customDialog.show(getFragmentManager(), tag);
		
	}


}
