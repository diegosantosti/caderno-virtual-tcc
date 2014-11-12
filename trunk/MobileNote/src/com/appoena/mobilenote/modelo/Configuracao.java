package com.appoena.mobilenote.modelo;

public class Configuracao {
	
	private int sync;
	private String token;
	
	public Configuracao(int sync, String token){
		
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

}
