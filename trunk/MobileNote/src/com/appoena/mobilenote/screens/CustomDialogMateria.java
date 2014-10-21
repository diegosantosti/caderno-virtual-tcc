package com.appoena.mobilenote.screens;

import android.app.Dialog;
import android.os.Bundle;
import android.webkit.WebView.FindListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.R;

public class CustomDialogMateria extends CustomDialog{
	
	private Spinner spnColor;
	private Spinner spnSemana;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		spnColor = (Spinner) view.findViewById(R.id.spinner_color);
		spnColor = setColorSpinner(spnColor);
		setSpinnerSemana();
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

}
