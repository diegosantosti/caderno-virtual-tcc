package com.appoena.mobilenote.screens;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.SearchView;
import android.widget.Toast;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Conteudo;

//Classe responsável por criar o editor de conteúdo do caderno
public class ActivityEditorConteudo extends Activity{
	
	private Bundle params;
	private String caminho;
	private String pesquisarConteudo;
	private String conteudoTemp;
	private boolean editMode = false;
	WebView wv;
	
	public ActivityEditorConteudo(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//recupera estado caso tenha mudado orientação de tela.
		if(savedInstanceState!=null){
			editMode = savedInstanceState.getBoolean("edicao");
		}
		setContentView(R.layout.activity_editor);
		//Recupera o caminho do conteúdo
		Intent it = getIntent();
		params = it.getExtras();
		caminho = params.getString("caminhoCadernoMateria");
		
		//Recupera o conteúdo do arquivo e armazena na variável
		setConteudoTemp(lerConteudoEditorTxt());
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		wv = (WebView) findViewById(R.id.webView1);
		executarEditorRaptor();		
		
	}
	
	//Método para executar o Editor Raptor no modo de visualização
	public void executarEditorRaptor(){
		
		//Limpa o estado anterior da WebView para ser chamada novamente
		wv.loadUrl("about:blank");
		
		WebSettings settings = wv.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);						
		wv.addJavascriptInterface(this, "EditorConteudoActivity");
		if(editMode){
			wv.loadUrl("file:///android_asset/raptor/example/exampleEdicao.html");
		}else{
			wv.loadUrl("file:///android_asset/raptor/example/example.html");
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
				
		getMenuInflater().inflate(R.menu.menu_conteudo, menu);
		SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.menu_pesquisar).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				// TODO Auto-generated method stub
				setPesquisarConteudo(query);
				query = query + "chamou metodo onQueryTextSubmit";
				Toast.makeText(getApplicationContext(), query, Toast.LENGTH_LONG).show();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				// TODO Auto-generated method stub
				setPesquisarConteudo(newText);
				newText = newText + "chamou metodo onQueryTextChange";
				Toast.makeText(getApplicationContext(), newText, Toast.LENGTH_LONG).show();
				return true;
			}
		});
        
        
        return true;
	    
	}
	
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.menu_conteudo, menu);
		if(editMode){
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
			editMode = true;
			invalidateOptionsMenu(); //recarrega os menus
			//Recarrega o editor em modo de edição
			executarEditorRaptor();
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
			editMode = false;
			invalidateOptionsMenu(); //recarrega os menus
			salvarConteudoTxt(getConteudoTemp());
			//Recarrega o editor em modo de visualização
			executarEditorRaptor();
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
			salvarConteudoTxt(getConteudoTemp());
			finish();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("edicao", editMode);
		//inserir aqui metodo para salvar quando mudar orientacao da tela.
		
		//Diego - Anotação
		//Salvar conteúdo na variável para o arquivo txt
		salvarConteudoTxt(getConteudoTemp());
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy() {
		//Salvar o conteúdo quando a activity for finalizadaas
		salvarConteudoTxt(getConteudoTemp());
		super.onDestroy();
	}	
	
	//Métodos para gravar o conteúdo temporário do Editor raptor para uma variável
	private void setConteudoTemp(String conteudoTemp){
		this.conteudoTemp = conteudoTemp;
	}
	
	private String getConteudoTemp(){
		return this.conteudoTemp;
	}
	
	@JavascriptInterface
	public void salvarConteudoEditor(String conteudo){
		
		setConteudoTemp(conteudo);
		
		//Conteudo cont = new Conteudo();
		//cont.salvarConteudo(caminho , conteudo);
	}
	
	@JavascriptInterface
	public String lerConteudoEditor(){
		
		return getConteudoTemp();
		
		//Conteudo cont = new Conteudo();
		//String conteudo = cont.lerConteudo(caminho);
		//return conteudo;
	}
	
	//Métodos para recuperar o conteúdo do arquivo txt
	public void salvarConteudoTxt(String conteudo){
		Conteudo cont = new Conteudo();
		cont.salvarConteudo(caminho , conteudo);
	}
	
	public String lerConteudoEditorTxt(){
		Conteudo cont = new Conteudo();
		String conteudo = cont.lerConteudo(caminho);
		return conteudo;
	}

	public String getPesquisarConteudo() {
		return pesquisarConteudo;
	}

	public void setPesquisarConteudo(String pesquisarConteudo) {
		this.pesquisarConteudo = pesquisarConteudo;
	}
	
}