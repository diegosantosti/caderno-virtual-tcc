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
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.Spinner;

import com.appoena.mobilenote.AdapterListAgenda;
import com.appoena.mobilenote.CustomDialog.CustomDialogListener;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Agenda;
import com.appoena.mobilenote.modelo.Caderno;

public class ActivityAgenda extends Activity implements CustomDialogListener{

	private Bundle params;
	private ListView listView;
	private AdapterListAgenda adapterAgenda;
	private ArrayList<Agenda> arrayAgendas;
	private Spinner spCaderno;
	private Spinner spMateria;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setBundle();
		Agenda a = new Agenda();
		
		//--teste willian
		arrayAgendas = a.consultarAgenda(this);
		adapterAgenda = new AdapterListAgenda(this, arrayAgendas);
		/*adapterAgenda.addItem(new Agenda("Prova 1", "14/09/2014", "19:00", 0, false, 0));
		adapterAgenda.addItem(new Agenda("Prova 2", "15/09/2014", "19:00", 0, true, 0));
		adapterAgenda.addItem(new Agenda("Prova 3", "16/09/2014", "19:00", 0, false, 0));
		adapterAgenda.addItem(new Agenda("Prova 4", "17/09/2014", "19:00", 0, true, 0));*/
		//-- fim teste
		listView = (ListView) findViewById(R.id.listAgenda);
		listView.setAdapter(adapterAgenda);
		registerForContextMenu(listView);
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
		int lembrar;
		String desc 	= params.getString(getResources().getString(R.string.DESC_AGENDA));
		String data 	= params.getString(getResources().getString(R.string.DATA_AGENDA));
		String hora 	= params.getString(getResources().getString(R.string.HORA_AGENDA));
		int    caderno	= params.getInt(getResources().getString(R.id.spinnerCaderno));
				
		if(params.getBoolean(getResources().getString(R.string.LEMBRAR)))
			lembrar = 1;
		else
			lembrar = 0;
		
		Agenda a = new Agenda(desc, data, hora, caderno, lembrar, 0);
		if(!params.getBoolean(getResources().getString(R.string.EDICAO))){	
			adapterAgenda.addItem(a);
			a.inserirTarefas(this, desc, data, hora, lembrar,0, caderno);
		}else{
			
			int position	= params.getInt(getResources().getString(R.string.INDEX));
			Agenda aAntes = adapterAgenda.getItem(position);
			long id = aAntes.getIdAgenda();
			adapterAgenda.setItemAtPosition(a, position);
			a.alterarTarefa(this, desc, data, hora, lembrar, 0, caderno, id);
		}
		adapterAgenda.notifyDataSetChanged();
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// n‹o faz nada
		
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
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.actions, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.menu_del:
			Agenda ag = adapterAgenda.getItem(info.position);
			ag.deletarTarefa(this, ag.getIdAgenda());
			adapterAgenda.removeItemAtPosition(info.position);
			adapterAgenda.notifyDataSetChanged();
			break;

		case R.id.menu_edit:
			Agenda a = adapterAgenda.getItem(info.position);
			setBundle();
			params.putString(getResources().getString(R.string.DESC_AGENDA), a.getDescricao());
	        params.putString(getResources().getString(R.string.HORA_AGENDA), a.getHoraAgenda());
	        params.putString(getResources().getString(R.string.DATA_AGENDA), a.getDataAgenda());
	        params.putInt(getResources().getString(R.string.LEMBRAR), a.getLembrar());
	        params.putInt(getResources().getString(R.string.INDEX), info.position);
	        showDialog(params);
			break;
		}
		return super.onContextItemSelected(item);
	}
	

}
