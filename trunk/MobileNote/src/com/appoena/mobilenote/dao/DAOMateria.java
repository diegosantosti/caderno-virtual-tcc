package com.appoena.mobilenote.dao;

import java.util.ArrayList;

import com.appoena.mobilenote.modelo.Materia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOMateria {

	private SQLiteDatabase bd;

	public DAOMateria(Context ctx){

		BDMobileNote aux = new BDMobileNote(ctx);
		bd = aux.getWritableDatabase();
	}

	public void incluirMateria(String nome, String professor, String email, int cor, int dia_semana,long id_caderno){
		ContentValues valores =  new ContentValues();
		valores.put("nome", nome);
		valores.put("id_caderno",id_caderno);
		valores.put("professor", professor);
		valores.put("email_professor", email);
		valores.put("cor", cor);
		valores.put("dia_semana", dia_semana);

		bd.insert("materia", null, valores);

	}

	public void alterarMateria(String nome, String professor, String email, int cor, int dia_semana,long id_caderno, long id_materia){
		ContentValues valores =  new ContentValues();
		valores.put("nome", nome);
		valores.put("id_caderno",id_caderno);
		valores.put("professor", professor);
		valores.put("email_professor", email);
		valores.put("cor", cor);
		valores.put("dia_semana", dia_semana);

		bd.update("materia", valores, "_id_materia = '"+id_materia+"'", null);
	}

	// deletar caderno
	public void deletarMateria(long id){
		bd.delete("materia","_id_materia = '"+id+"'", null);
	}

	// consultar materia
	public ArrayList<Materia> consultarMateria(long id_caderno){
		ArrayList<Materia> list = new ArrayList<Materia>();
		String[] colunas = {"_id_materia","nome","professor", "email_professor","cor","dia_semana","id_caderno"};

		Cursor cursor = bd.query("materia", colunas, "id_caderno = "+  id_caderno, null, null, null, "nome");

		if(cursor.getCount() > 0){
			cursor.moveToFirst();

			do{
				Materia m = new Materia();
				m.setIdMateria(cursor.getLong(0));
				m.setNome(cursor.getString(1));
				m.setProfessor(cursor.getString(2));
				m.setEmailProfessor(cursor.getString(3));
				m.setCor(cursor.getInt(4));
				m.setDiaSemana(cursor.getInt(5));
				m.setIdCaderno(cursor.getLong(6));
				

				list.add(m);
			}while(cursor.moveToNext());


		}

		return (list);

	}
	
	public ArrayList<String> consultarNomes(long id_caderno) {
		// TODO Auto-generated method stub
		ArrayList<String> list = new ArrayList<String>();
		String[] colunas = {"nome"};
		
		Cursor cursor = bd.query("materia", colunas, "id_caderno = "+id_caderno, null, null, null, "nome");
		//list.add("");
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			
			do{
				String nome = cursor.getString(0);
				list.add(nome);
			}while(cursor.moveToNext());
				
				
		}
		
		return (list);
	}
}
