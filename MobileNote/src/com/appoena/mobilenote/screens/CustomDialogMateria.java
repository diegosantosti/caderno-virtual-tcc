package com.appoena.mobilenote.screens;

import android.app.Dialog;
import android.os.Bundle;

import com.appoena.mobilenote.CustomDialog;

public class CustomDialogMateria extends CustomDialog{
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		
		return myDialog;
	}

}
