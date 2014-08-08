package com.appoena.mobilenote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private String testeRetorno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		clickAddCaderno();
		clickAbout();
	}
	
	//Método responsável pela ação no botão "Adicionar caderno"
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				showDialog(1, null);
				
				
				
			}
		});
	}
	
	//Método responsável pela ação no botão "Sobre"
	private void clickAbout() {
		Button btnAbout = (Button) findViewById(R.id.btn_about);
		btnAbout.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setContentView(R.layout.activity_sobre);
				
			}
		});
		
	}
	
	//Método responsåvel por inflar a view adicionar caderno
	private View getViewCaderno(){
		
		//instanciando inflate
		LayoutInflater inflate = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		//instanciando view a partir do inflate e do xml
		View view = inflate.inflate(R.layout.activity_adicionar_caderno, null);
		return view;
	}
	
	@Override
	protected Dialog onCreateDialog(int id, Bundle bundle) {
		AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
		alerta.setView(getViewCaderno());

		alerta.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				EditText edt = (EditText) findViewById(R.id.edtNomeCaderno);
				testeRetorno = edt.getText().toString();
				testeRetorno(testeRetorno);
				
				
			}
		});
		
		alerta.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				
			}
		});
		
		return alerta.create();

	}
	
	private void testeRetorno(String teste) {
		Toast.makeText(MainActivity.this, teste, Toast.LENGTH_SHORT).show();

	}
	
	//teste willian
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	

}
