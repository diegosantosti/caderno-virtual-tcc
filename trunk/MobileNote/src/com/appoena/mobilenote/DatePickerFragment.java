package com.appoena.mobilenote;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment{

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), R.style.AlertDialog_MobileNote);
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		
		//return new DatePickerDialog(getActivity() , year, month, day);
		return new DatePickerDialog(getActivity(), null, year, month, day);
	}

}