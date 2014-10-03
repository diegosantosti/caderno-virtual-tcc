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
					"nome text not null," +
					"cor_da_capa int not null);");
		bd.execSQL("insert into caderno(_id_caderno,nome,cor_da_capa) values(1,'USJT',1);");

		// tabela matéria
		/*bd.execSQL(	"create table materia(" +
					"_id_materia integer primary key autoincrement," +
					"id_caderno integer foreign key, " +
					"nome_materia text unique key, " +
					"cor_da_capa text not null," +
					"dia_semana text, " +
					"horario text, " +
					"email_professor text," +
					"sala text);");
		// tabela agenda
		bd.execSQL(	"create table agenda(" +
					"_id_agenda integer primary key autoincrement" +
					"id_materia integer foreign key" +
					"descricao text not null" +
					"hora text not null," +
					"data text not null);");*/
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
