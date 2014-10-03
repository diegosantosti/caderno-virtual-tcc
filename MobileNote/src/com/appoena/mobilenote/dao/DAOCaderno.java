package com.appoena.mobilenote.dao;

import java.util.ArrayList;
import java.util.List;

import com.appoena.mobilenote.modelo.Caderno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOCaderno {

	private SQLiteDatabase bd;

	public DAOCaderno(Context ctx){
		// abrir o banco para editar tabelas
		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}
	// inserir dados na tabela caderno
	public void inserirCaderno(String nome, int cor){
		ContentValues valores =  new ContentValues();
		valores.put("nome", nome);
		valores.put("cor_da_capa",cor);

		bd.insert("caderno", null, valores);

	}

	// alterar caderno
	public void alterarCaderno(String nome, int cor, int caderno){
		ContentValues valores =  new ContentValues();
		valores.put("nome", nome);
		valores.put("cor_da_capa", cor);
		bd.update("caderno", valores, "_id_caderno = "+caderno, null);
	}

	// deletar caderno
	public void deletarCaderno(int caderno){
		bd.delete("caderno","_id_caderno = "+caderno, null);
	}

	// consultar caderno
	public ArrayList<Caderno> consultarCadernos(){
		ArrayList<Caderno> list = new ArrayList<Caderno>();
		String[] colunas = {"_id_caderno","nome","cor_da_capa"};
		
		Cursor cursor = bd.query("caderno", colunas, null, null, null, null, "nome");
		
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				Caderno c = new Caderno();
				c.setId(cursor.getLong(0));
				c.setNome(cursor.getString(1));
				c.setColor(cursor.getInt(2));
				list.add(c);
			}while(cursor.moveToNext());
				
				
		}
		
		return (list);
	}
	
}
