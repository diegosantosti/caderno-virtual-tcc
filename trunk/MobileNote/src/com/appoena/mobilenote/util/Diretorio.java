package com.appoena.mobilenote.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;

public abstract class Diretorio {
	
	private static String ROOT = "/com.appoena.mobilenote";
	
	//Método para criar um diretório
		public static void criaDiretorio(String novoDiretorio){  
			
			novoDiretorio = substituirEspacosBrancosUnderline(novoDiretorio);//Substitui espaços em brancos por underline
			
			File folder = new File(Environment.getExternalStorageDirectory() + ROOT + novoDiretorio);
			if (!folder.exists()) {
			    folder.mkdir();
			}
		}
		
		public static void renomearDiretorio(String diretorioNovo, String diretorioAntigo){ 
			diretorioAntigo = substituirEspacosBrancosUnderline(diretorioAntigo);
			diretorioNovo = substituirEspacosBrancosUnderline(diretorioNovo);
			File folderAntigo = new File(Environment.getExternalStorageDirectory() + ROOT + "/" +  diretorioAntigo);
			File folderNovo = 	new File(Environment.getExternalStorageDirectory() + ROOT + "/" +  diretorioNovo);

			if (folderAntigo.exists()) {
				folderAntigo.renameTo(folderNovo);
			}
		}
		
		public static void excluirDiretorio(String diretorio){
			diretorio = substituirEspacosBrancosUnderline(diretorio);
			File folder = new File(Environment.getExternalStorageDirectory() + ROOT + "/" + diretorio);
			if (folder.exists()) {
			    folder.delete();
			}
		}
		
		
		//método responsável por substitui espaçoes em brancos por underline 
		//atuazliar
		public static String substituirEspacosBrancosUnderline(String caminho){
			String padrao = "\\s";
		    Pattern regPat = Pattern.compile(padrao); 
		    Matcher matcher = regPat.matcher(caminho);
		    String res = matcher.replaceAll("_");
			return res;
		}


}
