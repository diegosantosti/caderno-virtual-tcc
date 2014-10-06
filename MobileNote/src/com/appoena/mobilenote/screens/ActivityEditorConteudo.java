package com.appoena.mobilenote.screens;

import com.appoena.mobilenote.R;
import android.app.Activity;
import android.os.Bundle;
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
		wv.loadUrl("file:///android_asset/raptor/example/example.html");
		
	}

}