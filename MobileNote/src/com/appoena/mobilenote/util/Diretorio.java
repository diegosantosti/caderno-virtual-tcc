package com.appoena.mobilenote.util;

import java.io.File;

import android.os.Environment;

public abstract class Diretorio {
	
	//M�todo para criar um diret�rio
		public static void criaDiretorio(String novoDiretorio){  
			File folder = new File(Environment.getExternalStorageDirectory() + novoDiretorio);
			if (!folder.exists()) {
			    folder.mkdir();
			}
		}

}
