package com.appoena.mobilenote;


public class Caderno {
	
	private String nome;
	private int color;

	public Caderno(String nome, int color) {

		setNome(nome);
		setColor(color);
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
