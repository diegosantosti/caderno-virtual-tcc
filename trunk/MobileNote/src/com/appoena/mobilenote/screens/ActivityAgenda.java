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
import android.util.Log;
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
import com.appoena.mobilenote.modelo.Materia;

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
		long caderno	= params.getLong("id_caderno");
		long materia	= params.getLong("id_materia");
		Materia m		= new Materia();
		String nomeMateria		= m.nomeMateria(this,materia);
		Log.i("ID", "Caderno = "+caderno+"\nMateria = "+materia);
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
		valores.put(Events.TITLE, "Mobile Note - " +nomeMateria + " - " + desc);
		valores.put(Events.DESCRIPTION, desc);
		valores.put(Events.CALENDAR_ID, 3);
		valores.put(Events.EVENT_TIMEZONE, "Brasil/Brasília");
		// inserindo no Grid
		Agenda a = new Agenda();
		// inserindo Agenda
		if(!params.getBoolean(getResources().getString(R.string.EDICAO))){
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
			a.incluirAgenda(this, desc, data, hora, lembrar, materia, caderno,id_evento);
		}else{	
			int position	= params.getInt(getResources().getString(R.string.INDEX));
			Agenda aAntes = adapterAgenda.getItem(position);
			int lembrarAntes = aAntes.getLembrar();
			long id_agenda = aAntes.getIdAgenda();
			long id_evento = aAntes.getIdEvento();
			a = new Agenda(desc, data, hora, caderno, lembrar, 0,id_evento);
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
				crL.insert(Reminders.CONTENT_URI, valoresL);
			}
			else if(lembrarAntes == 1 || lembrar == 0)
				getContentResolver().delete(Reminders.CONTENT_URI, Reminders.EVENT_ID +" = " + id_evento, null);
			// alterando no BD
			a.alterarTarefa(this, desc, data, hora, lembrar, materia, caderno,id_evento, id_agenda);
			
				
		}
		arrayAgendas = a.consultarAgenda(this);
		adapterAgenda.setAgenda(arrayAgendas);
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
			deletarAgenda(info.position);
			break;

		case R.id.menu_edit:
			Agenda a = adapterAgenda.getItem(info.position);
			boolean lembrar = false;
			setBundle();
			params.putString(getResources().getString(R.string.DESC_AGENDA), a.getDescricao());
			params.putString(getResources().getString(R.string.HORA_AGENDA), a.getHoraAgenda());
			params.putString(getResources().getString(R.string.DATA_AGENDA), a.getDataAgenda());
			if(a.getLembrar() == 1)
				lembrar = true;
			params.putBoolean(getResources().getString(R.string.LEMBRAR),lembrar);
			params.putLong("id_caderno_ed", a.getIdCaderno());
			params.putLong("id_materia_ed", a.getIdMateria());
			Log.i("Qual id Materia",""+a.getIdMateria());
			params.putBoolean("edicao", true);
			showDialog(params);
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	public void deletarAgenda(int position){
		Agenda ag = adapterAgenda.getItem(position);
		long idEvento = ag.getIdEvento();
		// deletando do calendário android
		ContentResolver cr = getContentResolver();
		Uri deleteUri = null;
		getContentResolver().delete(Reminders.CONTENT_URI, Reminders.EVENT_ID +" = " + idEvento, null);
		deleteUri = ContentUris.withAppendedId(Events.CONTENT_URI, idEvento);
		cr.delete(deleteUri, null, null);
		
		ag.deletarAgenda(this, ag.getIdAgenda()); //deletando do BD
		adapterAgenda.removeItemAtPosition(position);
		adapterAgenda.notifyDataSetChanged();
	}

}
