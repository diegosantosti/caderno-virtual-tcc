package com.appoena.mobilenote.screens;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Caderno;

public class CustomDialogCaderno extends CustomDialog{
	
	private EditText 	edtCaderno;
	private Spinner		spnColor;
	private String 		mensagem;
	private Bundle 		params;
	private ArrayList<String> list;


	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		
		edtCaderno 	= (EditText) view.findViewById(R.id.edtNomeCaderno);
		spnColor	= (Spinner) view.findViewById(R.id.spinner_color);
		spnColor 	= setColorSpinner(spnColor);
		

	
		
		
		try {
			popularCaderno();
		} catch (Exception e) {
			
		}
		myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				
				Button b = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				//Sobrescreve o onClick do positive button para poder conferir os dados antes de fechar 
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// Dispara o evento onDialogPositiveClick para a activity que estiver escutando
						
							if(devolverCaderno()){
								myDialog.dismiss();
							}else{
								Toast.makeText(getActivity(), mensagem, Toast.LENGTH_SHORT).show();
							}
					}
				});
				
			}
		});
		
		return myDialog;
		
	}
	
	static CustomDialogCaderno newInstance(){
		CustomDialogCaderno dialog= new CustomDialogCaderno();
		return dialog;
	}
	
	private boolean devolverCaderno(){
		String nome = edtCaderno.getText().toString();
		int i = spnColor.getSelectedItemPosition();
		if(getCadernoDuplicado(nome)){
			mensagem = getResources().getString(R.string.caderno_duplicado);
			return false;
		}	
		if(!edtCaderno.getText().toString().isEmpty()){
				params.putString(getResources().getString(R.string.NOME_CADERNO), nome);
				params.putInt(getResources().getString(R.string.COR_CADERNO), i);
				params.putBoolean(getResources().getString(R.string.EDICAO), edicao);
				mListener.onDialogPositiveClick(CustomDialogCaderno.this, params);
				return true;			
		}else{
			mensagem = getResources().getString(R.string.informe_nome_caderno);
			return false;
		}
	}
	
	private void popularCaderno() {
		/* Will, essa � a linha q est� dando erro
		list = params.getStringArrayList("arrayNome");
		Log.i("Nome", list.get(0));*/

		String nome = params.getString(getResources().getString(R.string.NOME_CADERNO));
		if(nome!=null){
			edicao = true;
			edtCaderno.setText(nome);
			spnColor.setSelection(params.getInt(getResources().getString(R.string.COR_CADERNO)));
		}
	}
	
	public boolean getCadernoDuplicado(String nome){
		
	
		boolean d = false;
		for(int i = 0; i < list.size(); i++){
			if(list.get(i).equals(nome)){
				d = true;
				return d;
			}	
		}
		return d;
	}
	

}
