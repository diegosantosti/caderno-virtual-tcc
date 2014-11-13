package com.appoena.mobilenote.modelo;

import java.util.ArrayList;

import android.content.Context;

import com.appoena.mobilenote.dao.DAOConfiguracao;

public class Configuracao {
	
	private int sync;
	private String token;
	private long id;
	
	public Configuracao(int sync, String token,long id){
		
		setSync(sync);
		setToken(token);
	}
	
	public Configuracao(){
		
	}
	
	public int getSync() {
		return sync;
	}
	public void setSync(int sync) {
		this.sync = sync;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	// inserir Configuracao
		public void incluirConfiguracao(Context ctx, int sync, String token){
			DAOConfiguracao dc = new DAOConfiguracao(ctx);
			dc.inserirConfiguracao(token, sync);
		}	

		// alterar Configuracao
		public void alterarConfiguracao(Context ctx,String token,int sync,long id){
			DAOConfiguracao dc = new DAOConfiguracao(ctx);
			dc.alterarConfiguracao(token, sync,id);
		}
		// deletar Configuracao
		public void deletarConfiguracao(Context ctx, long id){
			DAOConfiguracao dc = new DAOConfiguracao(ctx);
			dc.deletarConfiguracao(id);

		}
		// lista de Configuracao
		public ArrayList<Configuracao> listaConfiguracao(Context ctx){
			ArrayList<Configuracao> list = new ArrayList<Configuracao>();
			DAOConfiguracao dc = new DAOConfiguracao(ctx);
			list = dc.consultarConfiguracao();
			return list;
		}

}
