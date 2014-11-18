package com.appoena.mobilenote.screens;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.GridView;

import com.appoena.mobilenote.AdapterGridCaderno;
import com.appoena.mobilenote.CustomDialog.CustomDialogListener;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Caderno;
import com.appoena.mobilenote.util.Diretorio;

public class ActivityCaderno extends Activity implements CustomDialogListener{


	private GridView gridView;
	private ArrayList<Caderno> arrayCaderno;
	private AdapterGridCaderno adapter;
	private Bundle params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Caderno c = new Caderno();
		arrayCaderno = c.listaCadernos(this);
		clickAddCaderno();
		clickAbout();
		clickAgenda();
		clickConfig();
		adapter = new AdapterGridCaderno(this, arrayCaderno , getResources().getStringArray(R.array.array_colors));
		gridView= (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(adapter);
		onClickItemGrid();
		registerForContextMenu(gridView);
		setBundle();
		
		//tenta criar uma pasta do aplicativo
		Diretorio.criaDiretorio("");


	}

	/*
	 * MŽtodo respons‡vel pela a�‹o no bot‹o "Adicionar caderno"
	 */
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {


			@Override
			public void onClick(View v) {
				setBundle();
				showDialog(params);


			}
		});
	}



	/*
	 * MŽtodo respons‡vel pela a�‹o no bot‹o "Sobre"
	 */
	private void clickAbout() {
		Button btnAbout = (Button) findViewById(R.id.btn_about);
		btnAbout.setOnClickListener( new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityCaderno.this, ActivitySobre.class);
				startActivity(it);
			}
		});

	}

	/*
	 * MŽtodo respons‡vel pela a�‹o no bot‹o "Agenda"
	 */
	private void clickAgenda() {

		Button btnAgenda = (Button) findViewById(R.id.btn_calendar);
		btnAgenda.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent it = new Intent(ActivityCaderno.this, ActivityAgenda.class);
				startActivity(it);
			}
		});

	}

	/*
	 * Método responsável por abrir a tela de configuração.
	 */
	private void clickConfig() {
		Button btnAbout = (Button) findViewById(R.id.btn_settings);
		btnAbout.setOnClickListener( new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent it = new Intent(ActivityCaderno.this, ActivityConfig.class);
				startActivity(it);
			}
		});

	}


	/*
	 * MŽtodo respons‡vel pelo clique no item do gridView
	 */

	private void onClickItemGrid() {
		gridView.setOnItemClickListener(new GridView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				Intent it = new Intent(ActivityCaderno.this, ActivityMateria.class);
				Caderno c = adapter.getItem(position);
				long id_caderno = c.getId();
				Bundle params = new Bundle();
				params.putLong("id_caderno",id_caderno);
				params.putString("nome_caderno", c.getNome());
				it.putExtras(params);
				startActivity(it);				
			}

		});

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
			Caderno ca = adapter.getItem(info.position);
			Diretorio.excluirDiretorio(ca.getNome());
			ca.deletarCaderno(this, ca.getId());
			adapter.removeItemAtPosition(info.position);
			adapter.notifyDataSetChanged();
			break;

		case R.id.menu_edit:
			Caderno c = adapter.getItem(info.position);
			setBundle();
			params.putString(getResources().getString(R.string.NOME_CADERNO), c.getNome());
			params.putInt(getResources().getString(R.string.COR_CADERNO), c.getColor());
			params.putInt(getResources().getString(R.string.INDEX), info.position);
			params.putString("nome_antigo", c.getNome());
			
			showDialog(params);

			break;
		}

		return super.onContextItemSelected(item);
	}

	private void showDialog(Bundle params){
		CustomDialogCaderno customDialog = CustomDialogCaderno.newInstance();
		customDialog.setArguments(params);
		customDialog.show(getFragmentManager(), null);
	}
	@Override

	public void onDialogPositiveClick(DialogFragment dialog, Bundle params) {
		String caderno 	= params.getString(getResources().getString(R.string.NOME_CADERNO));
		int cor 		= params.getInt(getResources().getString(R.string.COR_CADERNO));
		Caderno c = new Caderno(caderno, cor);
		
		// inserir caderno
		if (!params.getBoolean(getResources().getString(R.string.EDICAO))) {
				
			c.incluirCaderno(this,cor,caderno);
			arrayCaderno = c.listaCadernos(this);			
			adapter.setCadernos(arrayCaderno);
			
			//Chama função para criar diretório do caderno
			Diretorio.criaDiretorio("/"+c.getNome());
			
		}else{
			int position = params.getInt(getResources().getString(R.string.INDEX));
			Caderno cAntes = adapter.getItem(position);
			long id = cAntes.getId();
			adapter.setItemAtPosition(c, position);
			c.alterarCaderno(this, caderno, cor, id); // alterar caderno
			Diretorio.renomearDiretorio(c.getNome(), cAntes.getNome()); //renomeia diretorio
		}

		adapter.notifyDataSetChanged();

	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub

	}

	private void setBundle() {
		params = new Bundle();
		params.putInt(getResources().getString(R.string.VIEW), R.layout.activity_adicionar_caderno);
		Caderno c = new Caderno();
		params.putStringArrayList("arrayNome",c.nomesCadernos(ActivityCaderno.this));

	}

}
