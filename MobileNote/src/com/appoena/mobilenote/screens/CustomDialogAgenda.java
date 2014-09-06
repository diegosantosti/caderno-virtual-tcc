package com.appoena.mobilenote.screens;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.DatePickerFragment;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.TimePickerFragment;

public class CustomDialogAgenda extends CustomDialog{
	
	TextView edtData;
	TextView edtHora;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		setEdtData();
		setEdtHora();
		return myDialog;
	}
	
	

	static CustomDialogAgenda newInstance(){
		CustomDialogAgenda dialog= new CustomDialogAgenda();
		return dialog;
	}
	
	private void popularAgenda() {

	}
	
	private void devolverAgenda() {

	}
	
	private void setEdtData() {
		edtData = (TextView) view.findViewById(R.id.edtData);
		edtData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "timePicker");
				
			}
		});
	}
	
	private void setEdtHora() {
		edtHora = (TextView) view.findViewById(R.id.edtHora);
		edtHora.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragment();
			    newFragment.show(getFragmentManager(), "timePicker");
				
			}
		});
	}

}
