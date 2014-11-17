package com.appoena.mobilenote.screens;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SearchView;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Conteudo;

//Classe respons�vel por criar o editor de conte�do do caderno
public class ActivityEditorConteudo extends Activity{
	
	private Bundle params;
	private String caminho;
	private String conteudoTemp;
	private boolean editMode = false;
	WebView wv;
	private Bundle paramsEscolherImagem;
	
	private static final int SELECIONAR_IMAGEM 	= 1; 
	
	public ActivityEditorConteudo(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//recupera estado caso tenha mudado orienta��o de tela.
		if(savedInstanceState!=null){
			editMode = savedInstanceState.getBoolean("edicao");
		}
		setContentView(R.layout.activity_editor);
		//Recupera o caminho do conte�do
		Intent it = getIntent();
		params = it.getExtras();
		caminho = params.getString("caminhoCadernoMateria");
		
		//Recupera o conte�do do arquivo e armazena na vari�vel
		setConteudoTemp(lerConteudoEditorTxt());
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		wv = (WebView) findViewById(R.id.webView1);
		executarEditorRaptor();		
		
	}
	
	//M�todo para executar o Editor Raptor no modo de visualiza��o
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
		wv.setWebViewClient(new WebViewClient() {
		    @Override  
		    public void onPageFinished(WebView view, String url) {
		        super.onPageFinished(view, url);
		        wv.pageDown(true);
		    }
		    

		    @Override
		    public void onPageStarted(WebView view, String url, Bitmap favicon) {
		    	// TODO Auto-generated method stub
		    	super.onPageStarted(view, url, favicon);
		    	wv.pageDown(true);
		    }
		    	    
		});
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
			SearchView search = (SearchView) menu.findItem(R.id.menu_pesquisar).getActionView();
			int searchImg = getResources().getIdentifier("android:id/search_button", null, null);
			ImageView img = (ImageView)search.findViewById(searchImg);
			img.setImageResource(R.drawable.ic_action_pesquisar);
	        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
				
	        	@SuppressLint("NewApi")
				@Override
	        	public boolean onQueryTextSubmit(String query) {
	        		// TODO Auto-generated method stub
	        		
	        		//M�todo respons�vel por pesquisar palavras dentro do conte�do
	        		
	        		wv.findAllAsync(query);
	        		return false;
	        	}

	        	@Override
	        	public boolean onQueryTextChange(String newText) {
	        		// TODO Auto-generated method stub
	        		// DO NOTHING
	        		return false;
	        	}
			});
	        
	        search.setOnCloseListener(new SearchView.OnCloseListener() {
				
				@Override
				public boolean onClose() {
					// TODO Auto-generated method stub
					wv.clearMatches();
					return false;
				}
			});
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
			//Recarrega o editor em modo de edi��o
			executarEditorRaptor();
			break;
		
		case R.id.menu_compartilhar:
			//codigo para compartilhar
			break;
			
		case R.id.menu_sincronizar:
			//codigo para sincronizar
			break;
			
		case R.id.menu_pesquisar:
			//	---------------------
			// 	DO NOTHING
			// 	C�DIGO DO PESQUISAR SER� REALIZADO NO M�TODO onQueryTextSubmit DA CLASSE SEARCH TYPE
			//	---------------------
			break;

		case R.id.menu_salvar:
			//codigo para salvar
			editMode = false;
			invalidateOptionsMenu(); //recarrega os menus
			salvarConteudoTxt(getConteudoTemp());
			//Recarrega o editor em modo de visualiza��o
			executarEditorRaptor();
			break;
		
		case R.id.menu_inserir_desenho:
			//codigo para desenhar
			break;
		case R.id.menu_inserir_imagem:
			//codigo para inserir imagem
			Intent it = new Intent(ActivityEditorConteudo.this, ActivityEscolherImagem.class);
			startActivityForResult(it, SELECIONAR_IMAGEM);
						
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
		
		//Diego - Anota��o
		//Salvar conte�do na vari�vel para o arquivo txt
		salvarConteudoTxt(getConteudoTemp());
		
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onDestroy() {
		//Salvar o conte�do quando a activity for finalizadaas
		salvarConteudoTxt(getConteudoTemp());
		super.onDestroy();
	}
	
	//M�todos para gravar o conte�do tempor�rio do Editor raptor para uma vari�vel
	private void setConteudoTemp(String conteudoTemp){
		this.conteudoTemp = conteudoTemp;
	}
	
	private String getConteudoTemp(){
		return this.conteudoTemp;
	}
	
	@JavascriptInterface
	public void salvarConteudoEditor(String conteudo){
		setConteudoTemp(conteudo);
	}
	
	@JavascriptInterface
	public String lerConteudoEditor(){
		return getConteudoTemp();
	}
	
	@JavascriptInterface
	public void goLastPage(){
		wv.pageDown(true);
		wv.requestFocus();
	}
	
	//M�todos para recuperar o conte�do do arquivo txt
	public void salvarConteudoTxt(String conteudo){
		Conteudo cont = new Conteudo();
		cont.salvarConteudo(caminho , conteudo);
	}
	
	public String lerConteudoEditorTxt(){
		Conteudo cont = new Conteudo();
		String conteudo = cont.lerConteudo(caminho);
		return conteudo;
	}
	
	
	//recupera as informa��es de outras activities
	/**
     * Receive the result from the startActivity
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	   	
    	if (resultCode == Activity.RESULT_OK) {
    		
	    	switch (requestCode) {
			case SELECIONAR_IMAGEM:	
				Bundle params = data.getExtras();
				String caminhoImagem;
				caminhoImagem = params.getString("filePathImage");	
				
				//Verifica se o caminho est� preenchido, se sim ent�o insere a imagem no editor
				if(!caminhoImagem.isEmpty()){
					inserirImagemEditor(caminhoImagem);
				}
				
				break;
			
			default:
				break;
			}
    	}
    }

	private void inserirImagemEditor(String caminhoImagem) {
		
		String tagHtml = "<img src='" + caminhoImagem + "'/>";
		String novoConteudo = getConteudoTemp() + tagHtml;
		setConteudoTemp(novoConteudo);
		
		wv.reload();
		
	}
	
	
}