package com.appoena.mobilenote.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.R;

public class CustomDialogCaderno extends CustomDialog{
	
	private EditText 	edtCaderno;
	private Spinner		spnColor;
	
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
						if(devolverCaderno())myDialog.dismiss();
						else{
							Toast.makeText(getActivity(), R.string.informe_nome_caderno, Toast.LENGTH_SHORT).show();
							edtCaderno.requestFocus();
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

		int i = spnColor.getSelectedItemPosition();
		if(!edtCaderno.getText().toString().isEmpty()){
			params.putString(getResources().getString(R.string.NOME_CADERNO), edtCaderno.getText().toString());
			params.putInt(getResources().getString(R.string.COR_CADERNO), i);
			params.putBoolean(getResources().getString(R.string.EDICAO), edicao);
			mListener.onDialogPositiveClick(CustomDialogCaderno.this, params);
			return true;			
		}else{
			return false;
		}
	}
	
	private void popularCaderno() {
		String nome = params.getString(getResources().getString(R.string.NOME_CADERNO));
		if(nome!=null){
			edicao = true;
			edtCaderno.setText(nome);
			spnColor.setSelection(params.getInt(getResources().getString(R.string.COR_CADERNO)));
		}
	}
	

}
