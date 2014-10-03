package com.appoena.mobilenote.modelo;

import java.util.ArrayList;

public class Agenda {
	
	private String 	descricao;
	private String 	dataAgenda;
	private String	horaAgenda;
	private int 	idMateria;
	
	
	public Agenda(){
		
	}
	
	public Agenda(String strDesc, String dtAgenda, String hrAgenda,int materia){
		
		setDescricao(strDesc);
		setDataAgenda(dtAgenda);
		setHoraAgenda(hrAgenda);
		setIdMateria(materia);

	}
	
	// metodos get
	public String getDescricao(){
		return this.descricao;
	}
	
	public String getHoraAgenda(){
		return this.horaAgenda;
	}
	
	public String getDataAgenda(){
		return dataAgenda;
	}
	
	
	public int getIdMateria(){
		return idMateria;
	}
	
	// metodos set
	public void setDescricao(String strDesc){
		descricao = strDesc;
	}
	
	public void setHoraAgenda(String strHora){
		horaAgenda = strHora;
	}
	
	public void setDataAgenda(String strData){
		dataAgenda = strData;
	}
	
	public void setIdMateria(int materia){
		idMateria = materia;
	}
	
	
	
	// metodo para trazer todas as tarefas cadastradas
	public ArrayList  selectAgenda(){
		ArrayList agenda = new ArrayList();
		return agenda;
	}
}
