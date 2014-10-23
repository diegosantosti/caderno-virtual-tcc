package com.appoena.mobilenote.screens;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;

import com.appoena.mobilenote.AdapterListMateria;
import com.appoena.mobilenote.CustomDialog.CustomDialogListener;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Materia;

public class ActivityMateria extends Activity implements CustomDialogListener{
	
	private Bundle params;
	private ListView listview;
	private AdapterListMateria adapterMateria;
	private ArrayList<Materia> arrayMaterias;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_materias);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		setBundle();
		//teste willian
		arrayMaterias = new ArrayList<Materia>();
		arrayMaterias.add(new Materia("LEGET", 0, "Tony", "tony@gmail.com", 0));
		arrayMaterias.add(new Materia("PESQOP", 1, "Mori", "mori@gmail.com", 1));
		arrayMaterias.add(new Materia("GTECINF", 2, "Andre Luiz", "andreluiz@gmail.com", 2));
		//fim teste
		adapterMateria = new AdapterListMateria(this, arrayMaterias, getResources().getStringArray(R.array.array_colors),
													getResources().getStringArray(R.array.array_semana));
		listview = (ListView) findViewById(R.id.listMaterias);
		listview.setAdapter(adapterMateria);
		registerForContextMenu(listview);
		
		//DIEGAO, aqui sera implementado o caderno. Tirei do botao config e coloquei aqui.
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent it = new Intent(ActivityMateria.this, ActivityEditorConteudo.class);
				startActivity(it);
				
			}
				
		});
	}

		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_materia, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.menu_add_materia:
			setBundle();
			showDialog(params);
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
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
		Materia m = adapterMateria.getItem(info.position);
		switch (item.getItemId()) {
		case R.id.menu_del:
			//Wesley, nao precisa instanciar outra materia, basta usar a m - WILL
			adapterMateria.removeAtPosition(info.position);
			adapterMateria.notifyDataSetChanged();
			break;

		case R.id.menu_edit:
			m = adapterMateria.getItem(info.position);
			setBundle();
			params.putString(getResources().getString(R.string.NOME_MATERIA), m.getNome());
			params.putString(getResources().getString(R.string.NOME_PROFESSOR), m.getProfessor());
			params.putString(getResources().getString(R.string.EMAIL_PROFESSOR), m.getEmailProfessor());
			params.putInt(getResources().getString(R.string.COR_MATERIA), m.getCor());
			params.putInt(getResources().getString(R.string.DIA_SEMANA), m.getDiaSemana());
			params.putInt(getResources().getString(R.string.INDEX), info.position);
			showDialog(params);
			break;
		}
		return super.onContextItemSelected(item);
	}
	
	private void showDialog(Bundle params){
		CustomDialogMateria customDialog = CustomDialogMateria.newInstance();
		customDialog.setArguments(params);
		customDialog.show(getFragmentManager(), null);
	}
	
	private void setBundle() {
		params = new Bundle();
		params.putInt(getResources().getString(R.string.VIEW), R.layout.activity_adicionar_materia);

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, Bundle params) {
		int cor = params.getInt(getResources().getString(R.string.COR_MATERIA));
		int diaSemana = params.getInt(getResources().getString(R.string.DIA_SEMANA));
		String nome = params.getString(getResources().getString(R.string.NOME_MATERIA));
		String nomeProf = params.getString(getResources().getString(R.string.NOME_PROFESSOR));
		String emailProf = params.getString(getResources().getString(R.string.EMAIL_PROFESSOR));
		Materia m = new Materia(nome, diaSemana, nomeProf, emailProf, cor);
		if (!params.getBoolean(getResources().getString(R.string.EDICAO))){
			//adapterMateria.setMaterias(arrayMaterias);
			//listview.setAdapter(adapterMateria);
			adapterMateria.addItem(m);
			
		}else{
			int position = params.getInt(getResources().getString(R.string.INDEX));
			adapterMateria.setItemAtPosition(position, m);
		}
		adapterMateria.notifyDataSetChanged();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// nao faz nada
		
	}
	
	

}
