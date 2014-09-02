package com.appoena.mobilenote;


public class Caderno {
	
	private String nome;
	private String color;

	public Caderno(String nome, String color) {

		setNome(nome);
		setColor(color);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
