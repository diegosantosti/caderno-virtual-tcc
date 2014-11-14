package com.appoena.mobilenote.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;

public abstract class Diretorio {
	
	//M�todo para criar um diret�rio
		public static void criaDiretorio(String novoDiretorio){  
			//Substitui espa�os em brancos por underline
			novoDiretorio = Diretorio.substituirEspacosBrancosUnderline(novoDiretorio);
			
			File folder = new File(Environment.getExternalStorageDirectory() +"/com.appoena.mobilenote" + novoDiretorio);
			if (!folder.exists()) {
			    folder.mkdir();
			}
		}

		
		public static void renomearDiretorio(String diretorio){  
			File folder = new File(Environment.getExternalStorageDirectory() + diretorio);
			if (folder.exists()) {
			    folder.renameTo(folder);
			}
		}
		
		public static void excluirDiretorio(String diretorio){  
			File folder = new File(Environment.getExternalStorageDirectory() + diretorio);
			if (folder.exists()) {
			    folder.delete();
			}
		}
		
		
		//m�todo respons�vel por substitui espa�oes em brancos por underline 
		//atuazliar
		public static String substituirEspacosBrancosUnderline(String caminho){
			String padrao = "\\s";
		    Pattern regPat = Pattern.compile(padrao); 
		    Matcher matcher = regPat.matcher(caminho);
		    String res = matcher.replaceAll("_");
			return res;
		}


}
