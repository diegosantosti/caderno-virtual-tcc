package com.appoena.mobilenote.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appoena.mobilenote.modelo.Agenda;

public class DAOAgenda {

	private SQLiteDatabase bd;

	public DAOAgenda(Context ctx){
		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}

	// inserir agenda
	public void incluirAgenda(String descricao, String hora, String data, long materia, int lembrar,long caderno, long id_evento){
		ContentValues valores =  new ContentValues();
		valores.put("descricao", descricao);
		valores.put("id_materia",materia);
		valores.put("hora", hora);
		valores.put("data", data);
		valores.put("id_caderno", caderno);
		valores.put("lembrar", lembrar);
		valores.put("id_evento", id_evento);

		bd.insert("agenda", null, valores);

	}
	
	// alterar Agenda
	public void alterarAgenda(String descricao, String hora, String data, long materia, int lembrar,long caderno,long id_evento, long id){
		ContentValues valores =  new ContentValues();
		valores.put("descricao", descricao);
		valores.put("id_materia",materia);
		valores.put("hora", hora);
		valores.put("data", data);
		valores.put("id_caderno", caderno);
		valores.put("lembrar", lembrar);
		valores.put("id_evento", id_evento);
		
		bd.update("agenda", valores, "_id_agenda = '"+id+"'", null);
	}

	// deletar caderno
	public void deletarAgenda(long id){
		bd.delete("agenda","_id_agenda = '"+id+"'", null);
	}

	// consultar caderno
	public ArrayList<Agenda> consultarAgenda(){
		ArrayList<Agenda> list = new ArrayList<Agenda>();
		String[] colunas = {"_id_agenda","descricao","data", "hora","lembrar","id_caderno","id_materia", "id_evento"};

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
				a.setIdEvento(cursor.getLong(7));
				
				list.add(a);
			}while(cursor.moveToNext());


		}

		return (list);
	}
	
	public String nomeCaderno(long id){

		String[] colunas = {"nome"};
		String nome ="";
		Cursor cursor = bd.query("caderno", colunas, "_id_caderno = "+id, null, null, null, null);

		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			do{
				nome = cursor.getString(0);

			}while(cursor.moveToNext());


		}
		return nome;
	}
	
	public String nomeMateria(long id){

		String[] colunas = {"nome"};
		String nome ="";
		Cursor cursor = bd.query("materia", colunas, "_id_materia = "+id, null, null, null, null);

		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			do{
				nome = cursor.getString(0);

			}while(cursor.moveToNext());


		}
		return nome;
	}

}
