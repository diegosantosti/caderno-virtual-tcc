package com.appoena.mobilenote.util;

import java.io.File;

import android.os.Environment;

public abstract class Diretorio {
	
	//Método para criar um diretório
		public static void criaDiretorio(String novoDiretorio){  
			File folder = new File(Environment.getExternalStorageDirectory() + novoDiretorio);
			if (!folder.exists()) {
			    folder.mkdir();
			}
		}

}
