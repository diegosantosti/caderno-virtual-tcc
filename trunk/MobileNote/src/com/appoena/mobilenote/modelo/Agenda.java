package com.appoena.mobilenote.modelo;

import java.util.ArrayList;

public class Agenda {
	
	private String 	descricao;
	private String 	dataAgenda;
	private String	horaAgenda;
	private boolean lembrar; //Wesley, variavel para despertar ou nao --Willian
	private int 	idMateria;
	private int 	idCaderno; //Wesley, ser‡ que precisa? -- Willian

	public Agenda(){
		
	}
	
	public Agenda(String strDesc, String dtAgenda, String hrAgenda,int materia, boolean lembrar, int caderno){
		
		setDescricao(strDesc);
		setDataAgenda(dtAgenda);
		setHoraAgenda(hrAgenda);
		setIdMateria(materia);
		setLembrar(lembrar);
		setIdCaderno(caderno);

	}
	
	
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getDataAgenda() {
		return dataAgenda;
	}

	public void setDataAgenda(String dataAgenda) {
		this.dataAgenda = dataAgenda;
	}

	public String getHoraAgenda() {
		return horaAgenda;
	}

	public void setHoraAgenda(String horaAgenda) {
		this.horaAgenda = horaAgenda;
	}

	public boolean getLembrar() {
		return lembrar;
	}

	public void setLembrar(boolean lembrar) {
		this.lembrar = lembrar;
	}

	public int getIdMateria() {
		return idMateria;
	}

	public void setIdMateria(int idMateria) {
		this.idMateria = idMateria;
	}

	public int getIdCaderno() {
		return idCaderno;
	}

	public void setIdCaderno(int idCaderno) {
		this.idCaderno = idCaderno;
	}

	// metodo para trazer todas as tarefas cadastradas
	public ArrayList  selectAgenda(){
		ArrayList agenda = new ArrayList();
		return agenda;
	}
}
