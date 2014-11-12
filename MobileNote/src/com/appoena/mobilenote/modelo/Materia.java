package com.appoena.mobilenote.modelo;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.Environment;

import com.appoena.mobilenote.dao.DAOAgenda;
import com.appoena.mobilenote.dao.DAOMateria;

public class Materia {

	private String nome;
	private int diaSemana;
	private String professor;
	private String emailProfessor;
	private int cor;
	private long id_materia;
	private long id_caderno;
	


	public Materia(String nome, int diaSemana, String professor,String emailProfessor, int cor,long id_caderno) {
		setNome(nome);
		setDiaSemana(diaSemana);
		setProfessor(professor);
		setEmailProfessor(emailProfessor);
		setCor(cor);
		setIdCaderno(id_caderno);
	}

	public Materia(){

	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getDiaSemana() {
		return diaSemana;
	}
	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public String getEmailProfessor() {
		return emailProfessor;
	}
	public void setEmailProfessor(String emailProfessor) {
		this.emailProfessor = emailProfessor;
	}
	public int getCor() {
		return cor;
	}
	public void setCor(int cor) {
		this.cor = cor;
	}
	public long getIdMateria() {
		return id_materia;
	}
	public void setIdMateria(long id_materia) {
		this.id_materia = id_materia;
	}
	public long getIdCaderno(){
		return id_caderno;
	}
	public void setIdCaderno(long id_caderno){
		this.id_caderno = id_caderno;
	}


	public void inserirMateria(Context ctx, String nome, String professor, String email, int cor, int dia_semana,long id_caderno){
		DAOMateria dm = new DAOMateria(ctx);
		dm.incluirMateria(nome, professor, email, cor, dia_semana, id_caderno);
		
		
	}

	// metodo para alterar tarefa
	public void alterarMateria(Context ctx,String nome, String professor, String email, int cor, int dia_semana,long id_caderno, long id_materia){
		DAOMateria dm = new DAOMateria(ctx);
		dm.alterarMateria(nome, professor, email, cor, dia_semana, id_caderno, id_materia);
	
	}

	// metodo para deletar tarefa
	public void deletarTarefa(Context ctx, long id_materia){
		DAOMateria dm = new DAOMateria(ctx);
		dm.deletarMateria(id_materia);

	}
	// metodo para trazer todas as tarefas cadastradas
	public ArrayList<Materia>  consultarMateria(Context ctx, long id_caderno){
		DAOMateria dm = new DAOMateria(ctx);
		ArrayList<Materia> list = dm.consultarMateria(id_caderno);
		return list;
	}
	
	// metodo que retorna os nomes das materias de acordo com o caderno
	public ArrayList<String> nomeMaterias(Context ctx, long id_caderno){
		DAOMateria dm = new DAOMateria(ctx);
		ArrayList<String> list = dm.consultarNomes(id_caderno);
		return list;
		
	}
	
}
