package com.appoena.mobilenote;

import java.util.ArrayList;

public class Caderno {
	
	private String nome;
	private String color;
	private ArrayList<Caderno> arrayCadernos;

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

	public ArrayList<Caderno> getArrayCadernos() {
		return arrayCadernos;
	}

	public void setArrayCadernos(ArrayList<Caderno> arrayCadernos) {
		this.arrayCadernos = arrayCadernos;
	}


}
