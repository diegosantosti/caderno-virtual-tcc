package com.appoena.mobilenote.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appoena.mobilenote.modelo.Caderno;

public class DAOCaderno {

	private SQLiteDatabase bd;

	public DAOCaderno(Context ctx){
		// abrir o banco para editar tabelas
		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}
	// inserir dados na tabela caderno
	public void incluirCaderno(String nome, int cor){
		
		bd.execSQL("insert into caderno(_id_caderno,nome,cor_da_capa) values(null,'"+nome+"',"+cor+");");

	}

	// alterar caderno
	public void alterarCaderno(String nome, int cor, long id){
		ContentValues valores =  new ContentValues();
		valores.put("nome", nome);
		valores.put("cor_da_capa", cor);
		bd.update("caderno", valores, "_id_caderno = '"+id+"'", null);
	}

	// deletar caderno
	public void deletarCaderno(long id){
		bd.delete("materia","id_caderno = '"+id+"'", null);
		bd.delete("agenda","id_caderno = '"+id+"'", null);
		bd.delete("caderno","_id_caderno = '"+id+"'", null);
	}

	// consultar caderno
	public ArrayList<Caderno> consultarCaderno(){
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
	public ArrayList<String> consultarNomes() {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		String[] colunas = {"nome"};
		
		Cursor cursor = bd.query("caderno", colunas, null, null, null, null, "nome");
		//list.add("Caderno");
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				String nome = cursor.getString(0);
				list.add(nome);
			}while(cursor.moveToNext());
				
				
		}
		
		return (list);
	}
	// Retorna o nome a partir do caderno
	public String nomeCaderno(long id){
		
		String[] colunas = {"nome"};
		String nome = "";
		Cursor cursor = bd.query("caderno", colunas, "_id_caderno = "+id, null, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				nome = cursor.getString(0);
				
			}while(cursor.moveToNext());
				
				
		}
		
		return nome;
		
	}
	
}
