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
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		setColorSpinner();
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
						else				 Toast.makeText(getActivity(), "Informe o nome do caderno", Toast.LENGTH_SHORT).show();
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
		EditText edt = (EditText) view.findViewById(R.id.edtNomeCaderno);
		Spinner spn = (Spinner) view.findViewById(R.id.spinner_color);
		int i = spn.getSelectedItemPosition();
		if(!edt.getText().toString().isEmpty()){
			params.putString("NOME_CADERNO", edt.getText().toString());
			params.putInt("COR_CADERNO", i);
			params.putBoolean("EDICAO", edicao);
			mListener.onDialogPositiveClick(CustomDialogCaderno.this, params);
			return true;			
		}else{
			return false;
		}
	}
	
	private void popularCaderno() {
		EditText edt = (EditText) view.findViewById(R.id.edtNomeCaderno);
		Spinner spn = (Spinner) view.findViewById(R.id.spinner_color);
		edt.setText(params.getString("NOME_CADERNO"));
		spn.setSelection(params.getInt("COR_CADERNO"));
		edicao = true;
		
	}
	

}
