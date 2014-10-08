package com.appoena.mobilenote.dao;

import java.util.ArrayList;

import com.appoena.mobilenote.modelo.Agenda;
import com.appoena.mobilenote.modelo.Caderno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOAgenda {

	private SQLiteDatabase bd;

	public DAOAgenda(Context ctx){
		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}

	// inserir agenda
	public void inserirAgenda(String descricao, String hora, String data, int materia, int lembrar,int caderno){
		ContentValues valores =  new ContentValues();
		valores.put("descricao", descricao);
		valores.put("id_materia",materia);
		valores.put("hora", hora);
		valores.put("data", data);
		valores.put("id_caderno", caderno);
		valores.put("lembrar", lembrar);

		bd.insert("agenda", null, valores);

	}
	
	// alterar Agenda
	public void alterarAgenda(String descricao, String hora, String data, int materia, int lembrar,int caderno, long id){
		ContentValues valores =  new ContentValues();
		valores.put("descricao", descricao);
		valores.put("id_materia",materia);
		valores.put("hora", hora);
		valores.put("data", data);
		valores.put("id_caderno", caderno);
		valores.put("lembrar", lembrar);
		
		bd.update("agenda", valores, "_id_agenda = '"+id+"'", null);
	}

	// deletar caderno
	public void deletarAgenda(long id){
		bd.delete("agenda","_id_agenda = '"+id+"'", null);
	}

	// consultar caderno
	public ArrayList<Agenda> consultarAgendas(){
		ArrayList<Agenda> list = new ArrayList<Agenda>();
		String[] colunas = {"_id_agenda","descricao","data", "hora","lembrar","id_caderno","id_materia"};

		Cursor cursor = bd.query("agenda", colunas, null, null, null, null, "data");

		if(cursor.getCount() > 0){
			cursor.moveToFirst();

			do{
				Agenda a = new Agenda();
				a.setIdAgenda(cursor.getLong(0));
				a.setDescricao(cursor.getString(1));
				a.setDataAgenda(cursor.getString(2));
				a.setHoraAgenda(cursor.getString(3));
				a.setLembrar(cursor.getInt(4));
				a.setIdCaderno(cursor.getInt(5));
				a.setIdMateria(cursor.getInt(6));
				list.add(a);
			}while(cursor.moveToNext());


		}

		return (list);
	}


}
