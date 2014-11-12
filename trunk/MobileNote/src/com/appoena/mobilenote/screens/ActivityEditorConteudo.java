package com.appoena.mobilenote.screens;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Conteudo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

//Classe responsável por criar o editor de conteúdo do caderno
public class ActivityEditorConteudo extends Activity{
	
	private Bundle params;
	private String caminho;
	
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
	
}