package com.appoena.mobilenote.util;

import java.io.File;

import android.os.Environment;

public class StorageUtils {
	private static final String AUDIO_FILE_NAME = "audio.wav";
	private static final String VIDEO_FILE_NAME = "videorecordtest.3gp";
	
	public static boolean checkExternalStorageAvailable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
	    }
		else {
			return false;
		}
	}
	
	public static String getFileName(boolean isAudio) {
		String storageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
		return String.format("%s/%s", storageDir, (isAudio) ? AUDIO_FILE_NAME : VIDEO_FILE_NAME);
	}
	
	//Método para recuperar o nome do arquivo passando um caminho
	public static String getFileName(boolean isAudio , String caminho) {
		String storageDir = Environment.getExternalStorageDirectory()+caminho;
		String nomeAudio = getNomeAudio(caminho);
		return String.format("%s/%s", storageDir, (isAudio) ? nomeAudio : VIDEO_FILE_NAME);
	}
	
	//Método para recuperar o nome do próximo audio
	public static String getNomeAudio(String caminho){
		File file=new File(Environment.getExternalStorageDirectory()+caminho);
        File[] list = file.listFiles();
        int count = 0;
        for (File f: list){
            String name = f.getName();
            if (name.endsWith(".wav") || name.endsWith(".mp3") || name.endsWith(".some media extention"))
               count++;
        }
        count++;
        
		return "mnaudio" + count + ".wav";
	}
	
	//Método para recuperar o nome do próximo audio
	public static String getNomeImagem(String caminho){
		File file=new File(Environment.getExternalStorageDirectory()+caminho);
        File[] list = file.listFiles();
        int count = 0;
        for (File f: list){
            String name = f.getName();
            if (name.endsWith(".jpg") || name.endsWith(".png"))
               count++;
        }
        count++;
        
		return "mnimagem" + count + ".jpg";
	}
}
