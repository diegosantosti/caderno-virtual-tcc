package com.appoena.mobilenote.screens;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

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
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agenda);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setBundle();
		Agenda a = new Agenda();
		arrayAgendas = a.consultarAgenda(this);
		adapterAgenda = new AdapterListAgenda(this, arrayAgendas);
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
		int dia = Integer.parseInt(data.substring(0,2));
		int mes = Integer.parseInt(data.substring(3,5))-1;
		int ano = Integer.parseInt(data.substring(6,10));
		int h   = Integer.parseInt(hora.substring(0,2));
		int min = Integer.parseInt(hora.substring(3,5));

		//colocando as datas 
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(ano, mes,dia,h,min);
		Calendar endTime = Calendar.getInstance();
		endTime.set(ano, mes,dia,h,min);
		endTime.add(GregorianCalendar.HOUR, 1);

		// inserindo calendário
		ContentResolver cr = getContentResolver();
		ContentValues valores =  new ContentValues();
		valores.put(Events.DTSTART, beginTime.getTimeInMillis());
		valores.put(Events.DTEND, endTime.getTimeInMillis());
		valores.put(Events.TITLE, desc);
		valores.put(Events.DESCRIPTION, desc);
		valores.put(Events.CALENDAR_ID, 3);
		valores.put(Events.EVENT_TIMEZONE, "Brasil/Brasília");


		// adicionando lembretes

		// inserindo Agenda
		if(!params.getBoolean(getResources().getString(R.string.EDICAO))){	
			// inserindo no Grid
			Agenda a = new Agenda();
			
			// inserindo no calendario android
			Uri uri = cr.insert(Events.CONTENT_URI, valores);
			long id_evento =  Long.parseLong(uri.getLastPathSegment());// pegando o id do evento android
			
			if(lembrar == 1){
				
				ContentResolver crL = getContentResolver();
				ContentValues valoresL =  new ContentValues();
				valoresL.put(Reminders.MINUTES, 15);// minutos para alerta
				valoresL.put(Reminders.EVENT_ID, id_evento);
				valoresL.put(Reminders.METHOD, Reminders.METHOD_ALERT);
				uri = crL.insert(Reminders.CONTENT_URI, valoresL);
			}
			a.inserirTarefas(this, desc, data, hora, lembrar, 0, caderno,id_evento);
			arrayAgendas = a.consultarAgenda(this);
			adapterAgenda.setAgenda(arrayAgendas);


		}else{
			
			int position	= params.getInt(getResources().getString(R.string.INDEX));
			Agenda aAntes = adapterAgenda.getItem(position);
			int lembrarAntes = aAntes.getLembrar();
			long id_agenda = aAntes.getIdAgenda();
			long id_evento = aAntes.getIdEvento();
			Agenda a = new Agenda(desc, data, hora, caderno, lembrar, 0,id_evento);

			Uri updateUri = ContentUris.withAppendedId(Events.CONTENT_URI, id_evento);
			cr.update( updateUri, valores, null, null);
			id_evento =  Long.parseLong(updateUri .getLastPathSegment());


			// alterando Lembretes lembretes
			if(lembrar == 1 || lembrarAntes == 0){
				
				ContentResolver crL = getContentResolver();
				ContentValues valoresL =  new ContentValues();
				valoresL.put(Reminders.MINUTES, 15);// minutos para alerta
				valoresL.put(Reminders.EVENT_ID, id_evento);
				valoresL.put(Reminders.METHOD, Reminders.METHOD_ALERT);
				getContentResolver().delete(Reminders.CONTENT_URI, Reminders.EVENT_ID +" = " + id_evento, null);
				Uri uri = crL.insert(Reminders.CONTENT_URI, valoresL);

			}
			else if(lembrarAntes == 1 || lembrar == 0)
			getContentResolver().delete(Reminders.CONTENT_URI, Reminders.EVENT_ID +" = " + id_evento, null);
			// alterando no BD
			a.alterarTarefa(this, desc, data, hora, lembrar, 0, caderno,id_evento, id_agenda);
			arrayAgendas = a.consultarAgenda(this);
			adapterAgenda.setAgenda(arrayAgendas);	
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
			long idEvento = ag.getIdEvento();
			ag.deletarTarefa(this, ag.getIdAgenda()); //deletando do BD
			adapterAgenda.removeItemAtPosition(info.position);
			adapterAgenda.notifyDataSetChanged();


			// deletando do calendário android
			ContentResolver cr = getContentResolver();
			Uri deleteUri = null;
			getContentResolver().delete(Reminders.CONTENT_URI, Reminders.EVENT_ID +" = " + idEvento, null);
			deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, idEvento);
			cr.delete(deleteUri, null, null);
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
