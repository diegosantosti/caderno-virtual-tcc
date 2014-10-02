package com.appoena.mobilenote.modelo;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.appoena.mobilenote.dao.DAOCaderno;


public class Caderno {

	private String nome;
	private int color;
	private long id;

	public Caderno(){

	}

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

	public long getId(){
		return this.id;
	}

	public void setId(long id){
		this.id = id;
	}

	// inserir caderno
	public void incluirCaderno(Context ctx, int cor, String nome){
		DAOCaderno dc = new DAOCaderno(ctx);
		dc.inserirCaderno(nome, cor);
	}	

	// alterar caderno
	public void alterarCaderno(Context ctx,String nome,int cor, int id){
		DAOCaderno dc = new DAOCaderno(ctx);
		dc.alterarCaderno(nome, cor, id);
	}
	// deletar caderno
	public void deletarCaderno(Context ctx, int id){
		DAOCaderno dc = new DAOCaderno(ctx);
		dc.deletarCaderno(id);

	}
	// lista de caderno
	public ArrayList<Caderno> listaCadernos(Context ctx){
		ArrayList<Caderno> list = new ArrayList<Caderno>();
		DAOCaderno dc = new DAOCaderno(ctx);
		list = dc.consultarCadernos();
		return list;
	}

}
