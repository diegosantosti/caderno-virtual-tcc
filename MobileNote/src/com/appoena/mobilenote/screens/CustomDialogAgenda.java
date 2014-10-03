package com.appoena.mobilenote.screens;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.DatePickerFragment;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.TimePickerFragment;

public class CustomDialogAgenda extends CustomDialog implements DatePickerDialog.OnDateSetListener{
	
	TextView txtData;
	TextView txtHora;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		setDataHora();
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
		txtData = (TextView) view.findViewById(R.id.txtData);
		txtData.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
			    newFragment.show(getFragmentManager(), "timePicker");
				
			}
		});
	}
	
	private void setEdtHora() {
		txtHora = (TextView) view.findViewById(R.id.txtHora);
		txtHora.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragment();
			    newFragment.show(getFragmentManager(), "timePicker");
				
			}
		});
	}
	
	public void setDataHora(){
		txtData = (TextView) view.findViewById(R.id.txtData);
		txtHora = (TextView) view.findViewById(R.id.txtHora);
		
		final Calendar c = Calendar.getInstance();
		
		txtData.setText(new StringBuilder()
							.append(c.get(Calendar.DAY_OF_MONTH))
							.append("/")
							.append(c.get(Calendar.MONTH))
							.append("/")
							.append(c.get(Calendar.YEAR)));
		
		txtHora.setText(new StringBuilder()
							.append(c.get(Calendar.HOUR))
							.append(":")
							.append(c.get(Calendar.HOUR_OF_DAY)));
		
	}



	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		txtData.setText(new StringBuilder()
							.append(dayOfMonth)
							.append("/")
							.append(monthOfYear)
							.append("/")
							.append(year));
		
	}

}
