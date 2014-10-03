package com.appoena.mobilenote.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DAOAgenda {
	
	private SQLiteDatabase bd;
	
	public DAOAgenda(Context ctx){
		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}
	
	public void inserirAgenda(String descricao, String hora, String data, int materia){
		ContentValues valores =  new ContentValues();
		valores.put("descricao", descricao);
		valores.put("id_materia",materia);
		valores.put("hora", hora);
		valores.put("data", data);
		
		bd.insert("agenda", null, valores);
		
			}

}
