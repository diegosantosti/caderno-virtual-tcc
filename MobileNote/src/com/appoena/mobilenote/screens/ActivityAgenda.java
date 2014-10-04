package com.appoena.mobilenote.screens;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Toast;

import com.appoena.mobilenote.AdapterListAgenda;
import com.appoena.mobilenote.CustomDialog.CustomDialogListener;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Agenda;

public class ActivityAgenda extends Activity implements CustomDialogListener{

	private Bundle params;
	private ListView listView;
	private AdapterListAgenda adapterAgenda;
	private ArrayList<Agenda> arrayAgendas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setBundle();
		
		//--teste willian
		arrayAgendas = new ArrayList<Agenda>();
		adapterAgenda = new AdapterListAgenda(this, arrayAgendas);
		adapterAgenda.addItem(new Agenda("Prova 1", "14/09/2014", "19:00", 0, false, 0));
		adapterAgenda.addItem(new Agenda("Prova 2", "15/09/2014", "19:00", 0, true, 0));
		adapterAgenda.addItem(new Agenda("Prova 3", "16/09/2014", "19:00", 0, false, 0));
		adapterAgenda.addItem(new Agenda("Prova 4", "17/09/2014", "19:00", 0, true, 0));
		//-- fim teste
		
		//adapterAgenda.notifyDataSetChanged();
		listView = (ListView) findViewById(R.id.listAgenda);

		listView.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
		listView.setClickable(true);
		listView.setAdapter(adapterAgenda);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getApplication(), "funfa", Toast.LENGTH_SHORT).show();
				
				
			}
		});
		
		//registerForContextMenu(listView);
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
			setBundle();
			showDialog(params);
			
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
	
	private void setBundle() {
		params = new Bundle();
		params.putInt(getResources().getString(R.string.VIEW), R.layout.activity_adicionar_agenda);

	}
	
	private void showDialog(Bundle params){
		CustomDialogAgenda customDialog = CustomDialogAgenda.newInstance();
		customDialog.setArguments(params);
		customDialog.show(getFragmentManager(), null);
	}
	
//	@Override
//	public void onCreateContextMenu(ContextMenu menu, View v,
//			ContextMenuInfo menuInfo) {
//		getMenuInflater().inflate(R.menu.actions, menu);
//		super.onCreateContextMenu(menu, v, menuInfo);
//	}
//	
//	@Override
//	public boolean onContextItemSelected(MenuItem item) {
//		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
//		switch (item.getItemId()) {
//		case R.id.menu_del:
//			adapterAgenda.removeItemAtPosition(info.position);
//			adapterAgenda.notifyDataSetChanged();
//			break;
//
//		case R.id.menu_edit:
//			
//			break;
//		}
//		return super.onContextItemSelected(item);
//	}
//	

}
