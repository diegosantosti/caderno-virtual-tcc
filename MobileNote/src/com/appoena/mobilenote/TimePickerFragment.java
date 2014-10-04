package com.appoena.mobilenote;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;

public class TimePickerFragment extends DialogFragment {
	
	OnTimeSetListener onTimeSet;

	public TimePickerFragment() {
	}

	public void setCallBack(OnTimeSetListener onTime) {
		onTimeSet = onTime;
	}

	private int hourOfDay, minute;

	@Override
	public void setArguments(Bundle args) {
		super.setArguments(args);
		hourOfDay = args.getInt("hour");
		minute = args.getInt("minute");
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return new TimePickerDialog(getActivity(), onTimeSet, hourOfDay, minute, true);
	}
} 