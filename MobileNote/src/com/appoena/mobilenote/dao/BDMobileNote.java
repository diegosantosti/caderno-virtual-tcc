package com.appoena.mobilenote.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BDMobileNote extends SQLiteOpenHelper {
	
	private static final String nomeBD = "BDMobileNote";
	private static int versaoBD = 7;
	
	public BDMobileNote(Context ctx){
		super(ctx,nomeBD,null,versaoBD);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase bd) {
		// TODO Auto-generated method sbdb
		// criando base de dados tabela caderno
		bd.execSQL(	"create table caderno(" +
					"_id_caderno integer primary key autoincrement, " +
					"nome text unique on CONFLICT REPLACE," +
					"cor_da_capa integer not null" +
					");");
		bd.execSQL("insert into caderno(_id_caderno,nome,cor_da_capa) values(1,'USJT',1);");

		// tabela matéria
		/*bd.execSQL("create table materia(" +
					"_id_materia integer primary key autoincrement," +
					"id_caderno integer foreign key, " +
					"nome_materia text unique key, " +
					"cor_da_capa text not null," +
					"dia_semana text, " +
					"horario text, " +
					"email_professor text," +
					"sala text);");*/
		// tabela agenda
		bd.execSQL(	"create table agenda(" +
					"_id_agenda integer primary key autoincrement," +
					"id_caderno integer," +
					"id_materia integer ," +
					"descricao text not null," +
					"hora text not null," +
					"data text not null," +
					"lembrar integer not null,"+
					"id_evento integer not null);");
		//bd.execSQL("insert into agenda(_id_agenda,descricao,id_materia,id_caderno,hora,data,lembrar) values (1,'Teste',null,null,'19:00','14/12/2014',1);");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		bd.execSQL("drop table caderno;");
		bd.execSQL("drop table agenda;");
		bd.execSQL("drop table materia;");
		onCreate(bd);
		
	}

}
