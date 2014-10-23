package com.appoena.mobilenote.screens;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.R;

public class CustomDialogMateria extends CustomDialog{
	
	private Spinner spnColor;
	private Spinner spnSemana;
	private EditText edtNome;
	private EditText edtNomeProf;
	private EditText edtEmailProf;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		edtNome = (EditText)view.findViewById(R.id.edtNomeMateria);
		edtNomeProf = (EditText) view.findViewById(R.id.edtNomeProfessor);
		edtEmailProf = (EditText) view.findViewById(R.id.edtEmailProfessor);
		spnColor = (Spinner) view.findViewById(R.id.spinner_color);
		spnColor = setColorSpinner(spnColor);
		setSpinnerSemana();
		try {
			popularMateria();
		} catch (Exception e) {
			// nao faz nada
		}
		myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				Button b = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if(devolverMateria())myDialog.dismiss();
						else{
							Toast.makeText(getActivity(), R.string.informe_nome_materia, Toast.LENGTH_SHORT).show();
							edtNome.requestFocus();
						}
						
					}
				});
				
			}
		});
		
		return myDialog;
	}
	
	static CustomDialogMateria newInstance(){
		CustomDialogMateria dialog= new CustomDialogMateria();
		return dialog;
	}
	
	private void setSpinnerSemana() {
		spnSemana = (Spinner) view.findViewById(R.id.spinnerDiaSemana);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, 
																	getResources().getStringArray(R.array.array_semana));
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spnSemana.setAdapter(adapter);

	}
	
	private void popularMateria() {
		String nome = params.getString(getResources().getString(R.string.NOME_MATERIA));
		if(nome!=null){
			edicao = true;
			edtNome.setText(nome);
			edtNomeProf.setText(params.getString(getResources().getString(R.string.NOME_PROFESSOR)));
			edtEmailProf.setText(params.getString(getResources().getString(R.string.EMAIL_PROFESSOR)));
			spnColor.setSelection(params.getInt(getResources().getString(R.string.COR_MATERIA)));
			spnSemana.setSelection(params.getInt(getResources().getString(R.string.DIA_SEMANA)));
			
		}

	}
	
	private boolean devolverMateria() {
		
		int cor = spnColor.getSelectedItemPosition();
		int diaSemana = spnSemana.getSelectedItemPosition();
		String nome = edtNome.getText().toString();
		String nomeProf = edtNomeProf.getText().toString();
		String emailProf = edtNomeProf.getText().toString();		
		if (!nome.isEmpty()) {
			params.putString(getResources().getString(R.string.NOME_MATERIA), nome);
			params.putString(getResources().getString(R.string.NOME_PROFESSOR), nomeProf);
			params.putString(getResources().getString(R.string.EMAIL_PROFESSOR), emailProf);
			params.putInt(getResources().getString(R.string.COR_MATERIA), cor);
			params.putInt(getResources().getString(R.string.DIA_SEMANA), diaSemana);
			params.putBoolean(getResources().getString(R.string.EDICAO), edicao);
			mListener.onDialogPositiveClick(CustomDialogMateria.this, params);
			return true;	
		}
		else{
			return false;
		}
	}

}
