package com.appoena.mobilenote.modelo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import android.os.Environment;
import android.util.Log;

public class Conteudo {
	
	
	public void salvarConteudo(String caminho , String conteudo){
		//escrever o conteúdo num arquivo txt
		
	     File arq;
	     byte[] dados;
	      try
	      {
	          	             
	    	  arq = new File(Environment.getExternalStorageDirectory()+caminho,"conteudo.txt");
	          FileOutputStream fos;
	             
	          dados = conteudo.getBytes();
	             
	          fos = new FileOutputStream(arq);
	          fos.write(dados);
	          fos.flush();
	          fos.close();

	      } 
	      catch (Exception e) 
	      {
	    	  Log.v("WebView", "APRESENTOU ERRO AO SALVAR ARQUIVO TXT");
	    	  Log.v("WebView", e.getMessage());
	      }
	}
	
//	Método responsável por ler um conteúdo, se não for possível encontrar, retorna um conteúdo padrão
	
	public String lerConteudo(String caminho){
		String lstrNomeArq;
	     File arq; 
	     String lstrlinha;
	     String html ="" ;
	     try
	     {
	    	 lstrNomeArq = "conteudo.txt";
	 	             
			arq = new File(Environment.getExternalStorageDirectory()+caminho, lstrNomeArq);
			BufferedReader br = new BufferedReader(new FileReader(arq));
	         
						
	          while ((lstrlinha = br.readLine()) != null) 
	          {
	        	  html = html + lstrlinha;
	          }
	          br.close();
	           	             
	      } 
	      catch (Exception e) 
	      {
	    	 Log.v("WebView", "NÃO LEU ARQUIVO");
	    	 Log.v("WebView", e.getMessage());
	    	 html = "";
	      }
	     return html;		
	}
	
}
