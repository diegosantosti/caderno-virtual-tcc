package com.appoena.mobilenote.modelo;

import java.util.ArrayList;

import com.appoena.mobilenote.dao.DAOAgenda;

import android.content.Context;

public class Agenda {
	
	private String 	descricao;
	private String 	dataAgenda;
	private String	horaAgenda;
	private int 	lembrar; //Wesley, variavel para despertar ou nao --Willian
	private int 	id_materia;
	private int 	id_caderno; //Wesley, ser‡ que precisa? -- Willian
	private long 	id_agenda;
	private long 	id_evento;

	public Agenda(){
		
	}
	
	public Agenda(String strDesc, String dtAgenda, String hrAgenda,int materia, int lembrar, int caderno,long id_evento){
		
		setDescricao(strDesc);
		setDataAgenda(dtAgenda);
		setHoraAgenda(hrAgenda);
		setIdMateria(materia);
		setLembrar(lembrar);
		setIdCaderno(caderno);
		setIdEvento(id_evento);

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

	public int getLembrar() {
		return lembrar;
	}

	public void setLembrar(int lembrar) {
		this.lembrar = lembrar;
	}

	public int getIdMateria() {
		return id_materia;
	}

	public void setIdMateria(int idMateria) {
		this.id_materia = idMateria;
	}

	public int getIdCaderno() {
		return id_caderno;
	}

	public void setIdCaderno(int idCaderno) {
		this.id_caderno = idCaderno;
	}
	
	public long getIdAgenda() {
		return id_agenda;
	}

	public void setIdAgenda(long idAgenda) {
		this.id_agenda = idAgenda;
	}
	
	public long getIdEvento(){
		return this.id_evento;
	}
	
	public void setIdEvento(long id){
		this.id_evento = id;
	}
	// metodo para incluir tarefas
	public void inserirTarefas(Context ctx, String descricao, String data, String hora, int lembrar, int idMateria, int id_caderno, long id_evento){
		DAOAgenda dg = new DAOAgenda(ctx);
		dg.inserirAgenda(descricao, hora, data, idMateria, lembrar, id_caderno,id_evento);
	}
	
	// metodo para alterar tarefa
	public void alterarTarefa(Context ctx, String descricao, String data, String hora, int lembrar, int idMateria, int idCaderno, long idAgenda){
		DAOAgenda dg = new DAOAgenda(ctx);
		dg.alterarAgenda(descricao, hora, data, idMateria, lembrar, idCaderno, idAgenda);
	}
	
	// metodo para deletar tarefa
	public void deletarTarefa(Context ctx, long idAgenda){
		DAOAgenda dg = new DAOAgenda(ctx);
		dg.deletarAgenda(idAgenda);
		
	}
	// metodo para trazer todas as tarefas cadastradas
	public ArrayList<Agenda>  consultarAgenda(Context ctx){
		ArrayList<Agenda> list = new ArrayList<Agenda>();
		DAOAgenda da = new DAOAgenda(ctx);
		list = da.consultarAgendas();
		return list;
	}


}
