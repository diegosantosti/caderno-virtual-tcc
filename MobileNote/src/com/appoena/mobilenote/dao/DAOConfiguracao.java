package com.appoena.mobilenote.dao;

import java.util.ArrayList;

import com.appoena.mobilenote.modelo.Configuracao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOConfiguracao {
	
	private SQLiteDatabase bd;

	public DAOConfiguracao(Context ctx){
		// abrir o banco para editar tabelas
		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}
	// inserir dados na tabela caderno
	public void inserirConfiguracao(String token, int sync){
		
		bd.execSQL("insert into configuracao(_id_config,token,sync) values(null,'"+token+"',"+sync+");");

	}

	// alterar caderno
	public void alterarConfiguracao(String token, int sync, long id){
		ContentValues valores =  new ContentValues();
		valores.put("token", token);
		valores.put("sync", sync);
		bd.update("configuracao", valores, "_id_config = '"+id+"'", null);
	}

	// deletar caderno
	public void deletarConfiguracao(long id){
		bd.delete("configuracao","_id_config= '"+id+"'", null);
	}

	// consultar caderno
	public ArrayList<Configuracao> consultarConfiguracao(){
		ArrayList<Configuracao> list = new ArrayList<Configuracao>();
		String[] colunas = {"_id_config","token","sync"};
		
		Cursor cursor = bd.query("configuracao", colunas, null, null, null, null, null);
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				Configuracao c = new Configuracao();
				c.setId(cursor.getLong(0));
				c.setToken(cursor.getString(1));
				c.setSync(cursor.getInt(2));
				list.add(c);
			}while(cursor.moveToNext());
				
				
		}
		
		return (list);
	}
	
}
