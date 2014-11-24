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
import com.appoena.mobilenote.util.Diretorio;
import com.appoena.mobilenote.util.Dropbox;

public class ActivityMateria extends Activity implements CustomDialogListener{
	
	private Bundle params;
	private ListView listview;
	private AdapterListMateria adapterMateria;
	private ArrayList<Materia> arrayMaterias;
	private long id_caderno;
	private String nome_caderno;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_materias);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		Intent it = getIntent();
		params = it.getExtras();
		id_caderno = params.getLong("id_caderno");
		nome_caderno = params.getString("nome_caderno");
		setBundle();
		//teste willian
		Materia m = new Materia();
		arrayMaterias =  m.consultarMateria(this, id_caderno);
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
				
				
				//TESTE CRIAR DIRETÓRIO
				//EXECUTA O MÉTODO PARA CRIAR O DIRETÓRIO
//				Materia materia = new Materia();
//				materia.criaDiretorio("/TesteMatDir");
				
				Intent it = new Intent(ActivityMateria.this, ActivityEditorConteudo.class);
				
				//Recupera a matéria selecionada e recupera o nome do caminho do conteúdo no formato
				// /Caderno/Materia
				Materia m = adapterMateria.getItem(position);
				String nomeMateria = m.getNome();
				Bundle paramsMateria = new Bundle();
				
				String nomeCadernoCaminho = Diretorio.substituirEspacosBrancosUnderline(nome_caderno);
				String nomeMateriaCaminho = Diretorio.substituirEspacosBrancosUnderline(nomeMateria);
				
				paramsMateria.putString("caminhoCadernoMateria", "/com.appoena.mobilenote"+"/"+nomeCadernoCaminho+"/"+nomeMateriaCaminho);
				it.putExtras(paramsMateria);				
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
		long id_materia = m.getIdMateria();
		switch (item.getItemId()) {
		case R.id.menu_del:
			Dropbox.excluir("/"+nome_caderno+"/"+m.getNome(), getApplicationContext());
			Diretorio.excluirDiretorio("/"+nome_caderno+"/"+m.getNome()); //excluir diretorio
			m.deletarMateria(this, id_materia); 
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
			params.putString("nome_antigo", m.getNome());
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
		Materia m = new Materia();
		params.putStringArrayList("arrayMateria", m.nomeMaterias(this, id_caderno));

	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, Bundle params) {
		int cor 			= params.getInt(getResources().getString(R.string.COR_MATERIA));
		int dia_semana 		= params.getInt(getResources().getString(R.string.DIA_SEMANA));
		String nome 		= params.getString(getResources().getString(R.string.NOME_MATERIA));
		String nomeProf 	= params.getString(getResources().getString(R.string.NOME_PROFESSOR));
		String emailProf 	= params.getString(getResources().getString(R.string.EMAIL_PROFESSOR));
		Materia m = new Materia(nome, dia_semana, nomeProf, emailProf, cor, id_caderno);
		if (!params.getBoolean(getResources().getString(R.string.EDICAO))){
			
			m.incluirMateria(this, nome, nomeProf, emailProf, cor, dia_semana, id_caderno);
			arrayMaterias =  m.consultarMateria(this, id_caderno);
			adapterMateria.setMaterias(arrayMaterias);
			Diretorio.criaDiretorio("/"+nome_caderno+"/"+nome); //cria o diretorio
			Dropbox.criarPasta("/"+nome_caderno+"/"+nome, getApplicationContext());
			
		}else{
			
			int position = params.getInt(getResources().getString(R.string.INDEX));
			Materia mAntes = adapterMateria.getItem(position);
			long id_materia = mAntes.getIdMateria();
			m.alterarMateria(this, nome, nomeProf, emailProf, cor, dia_semana, id_caderno, id_materia);
			arrayMaterias =  m.consultarMateria(this, id_caderno);
			adapterMateria.setMaterias(arrayMaterias);
			Diretorio.renomearDiretorio("/"+nome_caderno+"/"+m.getNome(),"/"+nome_caderno+"/"+mAntes.getNome());
			Dropbox.renomear("/"+nome_caderno+"/"+m.getNome(), "/"+nome_caderno+"/"+mAntes.getNome(), getApplicationContext());
				
		}
		adapterMateria.notifyDataSetChanged();
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// nao faz nada
		
	}
	
	

}
