package com.appoena.mobilenote.screens;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import com.appoena.mobilenote.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

//Classe responsável por criar o editor de conteúdo do caderno
public class ActivityEditorConteudo extends Activity{
	
	public ActivityEditorConteudo(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_editor);
		
		WebView wv = (WebView) findViewById(R.id.webView1);
		
		WebSettings settings = wv.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);
						
		wv.addJavascriptInterface(this, "EditorConteudoActivity");
		
		wv.loadUrl("file:///android_asset/raptor/example/example.html");
	}
	
	@JavascriptInterface
	public void salvarConteudo(String conteudo){
		
		Log.v("WebView", "passou por Salvar Conteudo");
		Log.v("WebView", conteudo);
		
		//escrever o conteúdo num arquivo txt
		
	     File arq;
	     byte[] dados;
	      try
	      {
	          	             
	    	  arq = new File(Environment.getExternalStorageDirectory(),"conteudo.txt");
	          FileOutputStream fos;
	             
	          dados = conteudo.getBytes();
	             
	          fos = new FileOutputStream(arq);
	          fos.write(dados);
	          fos.flush();
	          fos.close();
	          Log.v("WebView", "GRAVOU ARQUIVO TXT");
	      } 
	      catch (Exception e) 
	      {
	    	  Log.v("WebView", "APRESENTOU ERRO AO SALVAR ARQUIVO TXT");
	    	  Log.v("WebView", e.getMessage());
	      }
	}
	
	@JavascriptInterface
	public String lerConteudo(){
		
		String conteudo = lerArquivo2();
		
		Log.v("WebView", "passou por Ler Conteúdo");
		Log.v("WebView", conteudo);
		
		return conteudo;
	}
	
	
	public String lerArquivo2(){
		
		String lstrNomeArq;
	     File arq; 
	     String lstrlinha;
	     String html ="" ;
	     try
	     {
	    	 lstrNomeArq = "conteudo.txt";
	 	             
			arq = new File(Environment.getExternalStorageDirectory(), lstrNomeArq);
			BufferedReader br = new BufferedReader(new FileReader(arq));
	         
						
	          while ((lstrlinha = br.readLine()) != null) 
	          {
	        	  html = html + lstrlinha;
	          }
	           	             
	      } 
	      catch (Exception e) 
	      {
	    	 Log.v("WebView", "NÃO LEU ARQUIVO");
	    	 Log.v("WebView", e.getMessage());
	      }
	     return html;
	}

}