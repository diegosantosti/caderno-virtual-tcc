package com.appoena.mobilenote.screens;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.CustomDialog.CustomDialogListener;

public class ActivityAgenda extends Activity implements CustomDialogListener{

	private int AGENDA = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_agenda, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		case R.id.menu_add_agenda:
			Bundle params = new Bundle();
			
			//showDialog(null);
			
			return true;
		default:
			break;
		}
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, Bundle params) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
//	private void showDialog(Bundle params){
//		CustomDialog  customDialog = CustomDialog.newInstance();
//		customDialog.setArguments(params);
//		customDialog.show(getFragmentManager(), null);
//	}

}
