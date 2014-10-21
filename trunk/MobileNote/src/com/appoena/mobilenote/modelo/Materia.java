package com.appoena.mobilenote.modelo;

public class Materia {
	
	private String nome;
	private int diaSemana;
	private String professor;
	private String emailProfessor;
	private int cor;
	private long id;
	
	public Materia(String nome, int diaSemana, String professor,String emailProfessor, int cor) {
		setNome(nome);
		setDiaSemana(diaSemana);
		setProfessor(professor);
		setEmailProfessor(emailProfessor);
		setCor(cor);
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

}
