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
import java.util.List;

import android.content.Context;

public class Operacoes implements Serializable {

	
	private static final long serialVersionUID = 23L;
	private int operacao;
	private String newPath;
	private String oldPath;
	private List<Operacoes> arrayOperacoes;
	String FILENAME = "operacoes";
	
	public Operacoes(ArrayList<Operacoes> list){
		setArrayOperacoes(list);
	}
	
	public Operacoes(){
		
	}
	
	public Operacoes(int operacao, String newPath, String oldPath){
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

	public List<Operacoes> getArrayOperacoes() {
		return arrayOperacoes;
	}

	public void setArrayOperacoes(List<Operacoes> arrayOperacoes) {
		this.arrayOperacoes = arrayOperacoes;
	}
	
	public void gravarOperacoes(Context context, Operacoes d){
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
	
	public List<Operacoes> lerOperacoes(Context context){
		try {

			File file =context.getFileStreamPath(FILENAME);
			FileInputStream fis;
			ObjectInputStream ois;
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			Operacoes d = (Operacoes) ois.readObject();
			fis.close();
			ois.close();
			return d.getArrayOperacoes();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (StreamCorruptedException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
		return null;
	}

	
}
