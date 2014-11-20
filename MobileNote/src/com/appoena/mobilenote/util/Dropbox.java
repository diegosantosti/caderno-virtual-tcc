package com.appoena.mobilenote.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;

public class Dropbox implements Serializable {

	
	private static final long serialVersionUID = 89L;
	
	private int operacao;
	private String newPath;
	private String oldPath;
	private ArrayList<Dropbox> arrayOperacoes;
	private String FILENAME = "operacoes";
	
	public Dropbox(ArrayList<Dropbox> list){
		setArrayOperacoes(list);
	}
	
	public Dropbox(){
		
	}
	
	public Dropbox(int operacao, String newPath, String oldPath){
		setOperacao(operacao);
		setNewPath(newPath);
		setOldPath(oldPath);
	}

	public int getOperacao() {
		return operacao;
	}

	public void setOperacao(int operacao) {
		this.operacao = operacao;
	}

	public String getNewPath() {
		return newPath;
	}

	public void setNewPath(String newPath) {
		this.newPath = newPath;
	}

	public String getOldPath() {
		return oldPath;
	}

	public void setOldPath(String oldPath) {
		this.oldPath = oldPath;
	}

	public ArrayList<Dropbox> getArrayOperacoes() {
		return arrayOperacoes;
	}

	public void setArrayOperacoes(ArrayList<Dropbox> arrayOperacoes) {
		this.arrayOperacoes = arrayOperacoes;
	}
	
	public void gravarOperacoes(Context context, Dropbox d){
		File file =context.getFileStreamPath(FILENAME);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			ObjectOutputStream oos;
			oos = new ObjectOutputStream(fos);
			oos.writeObject(d);
			oos.close();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void lerOperacoes(Context context){
		try {

			File file =context.getFileStreamPath(FILENAME);
			FileInputStream fis;
			ObjectInputStream ois;
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Dropbox d = (Dropbox) ois.readObject();
			fis.close();
			ois.close();
			setArrayOperacoes(d.getArrayOperacoes());

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (StreamCorruptedException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	
}
