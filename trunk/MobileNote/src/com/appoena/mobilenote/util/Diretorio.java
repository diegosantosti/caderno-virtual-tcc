package com.appoena.mobilenote.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.os.Environment;
import android.util.Log;

public abstract class Diretorio {
	
	public static String ROOT = "/.com.appoena.mobilenote";
	
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
			Log.v("excluirDiretorio", folder.toString());
			if (folder.exists()) {			
			    deleteDir(folder);
			}
		}
		
		private static void deleteDir(File file) {
			File[] files = file.listFiles();
	        if (files != null) {
	            for (File dirOrFile : files) {
	                if (dirOrFile.isDirectory()) {
	                    Log.v("deleteDir","Removendo diretório: " + dirOrFile.toString());
	                } else {
	                	Log.v("deleteDir","Removendo arquivo: " + dirOrFile.toString());
	                }
	                deleteDir(dirOrFile);
	            }
	        }
	        file.delete();		
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
