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
					"nome text," +
					"cor_da_capa integer not null" +
					");");

		// tabela matéria
		bd.execSQL("create table materia(" +
					"_id_materia integer primary key autoincrement," +
					"id_caderno integer, " +
					"nome text, " +
					"cor integer not null," +
					"email_professor text," +
					"dia_semana integer," +
					"professor text," +
					"FOREIGN KEY(id_caderno) REFERENCES caderno(_id_caderno));");
		bd.execSQL("insert into materia(_id_materia, id_caderno,nome,cor,email_professor, dia_semana, professor) values (1,1,'LEGET',1,'tony@gmail.com',1,'Tony');");
		// tabela agenda
		bd.execSQL(	"create table agenda(" +
					"_id_agenda integer primary key autoincrement," +
					"id_caderno integer," +
					"id_materia integer ," +
					"descricao text not null," +
					"hora text not null," +
					"data text not null," +
					"lembrar integer not null,"+
					"id_evento integer not null" +
					");");
		// tabela configuracao
		bd.execSQL(	"create table configuracao(" +
					"_id_config integer primary key autoincrement, " +
					"token text," +
					"sync integer not null" +
					");");
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
