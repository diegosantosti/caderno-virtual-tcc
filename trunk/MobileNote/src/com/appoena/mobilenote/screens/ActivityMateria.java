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
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// nao faz nada
		
	}
	
	

}
