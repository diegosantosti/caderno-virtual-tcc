package com.appoena.mobilenote.screens;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Conteudo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

//Classe responsável por criar o editor de conteúdo do caderno
public class ActivityEditorConteudo extends Activity{
	
	private Bundle params;
	private String caminho;
	private boolean edicao = false;
	
	public ActivityEditorConteudo(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		
		//Recupera o caminho do conteúdo
		Intent it = getIntent();
		params = it.getExtras();
		caminho = params.getString("caminhoCadernoMateria");
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		WebView wv = (WebView) findViewById(R.id.webView1);
		
		WebSettings settings = wv.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);
						
		wv.addJavascriptInterface(this, "EditorConteudoActivity");
		
		wv.loadUrl("file:///android_asset/raptor/example/example.html");
	}
	
	@JavascriptInterface
	public void salvarConteudo(String conteudo){
		
		Conteudo cont = new Conteudo();
		cont.salvarConteudo(caminho , conteudo);
	}
	
	@JavascriptInterface
	public String lerConteudo(){
		
		Conteudo cont = new Conteudo();
		String conteudo = cont.lerConteudo(caminho);
		return conteudo;
	}

	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_conteudo, menu);
		if(edicao){
			menu.findItem(R.id.menu_salvar).setVisible(true);
			menu.findItem(R.id.menu_inserir_desenho).setVisible(true);
			menu.findItem(R.id.menu_inserir_imagem).setVisible(true);
			menu.findItem(R.id.menu_inserir_voz).setVisible(true);
			menu.findItem(R.id.menu_editar).setVisible(false);
			menu.findItem(R.id.menu_sincronizar).setVisible(false);
			menu.findItem(R.id.menu_compartilhar).setVisible(false);
			menu.findItem(R.id.menu_pesquisar).setVisible(false);
			
		}else{
			menu.findItem(R.id.menu_salvar).setVisible(false);
			menu.findItem(R.id.menu_inserir_desenho).setVisible(false);
			menu.findItem(R.id.menu_inserir_imagem).setVisible(false);
			menu.findItem(R.id.menu_inserir_voz).setVisible(false);
			menu.findItem(R.id.menu_editar).setVisible(true);
			menu.findItem(R.id.menu_sincronizar).setVisible(true);
			menu.findItem(R.id.menu_compartilhar).setVisible(true);
			menu.findItem(R.id.menu_pesquisar).setVisible(true);
		}
		
		return true;
	}
		
	
	//acoes dos menus da ActionBar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_editar:
			//codigo para editar
			edicao = true;
			invalidateOptionsMenu(); //recarrega os menus
			break;
		
		case R.id.menu_compartilhar:
			//codigo para compartilhar
			break;
			
		case R.id.menu_sincronizar:
			//codigo para sincronizar
			break;
			
		case R.id.menu_pesquisar:
			//codigo para pesquisar
			break;

		case R.id.menu_salvar:
			//codigo para salvar
			edicao = false;
			invalidateOptionsMenu(); //recarrega os menus
			break;
		
		case R.id.menu_inserir_desenho:
			//codigo para desenhar
			break;
		case R.id.menu_inserir_imagem:
			//codigo para inserir imagem
			break;
		case R.id.menu_inserir_voz:
			//codigo para inderir voz
			break;
		case android.R.id.home:
			//chamar o metodo para salvar conteudo ao sair
			finish();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
}