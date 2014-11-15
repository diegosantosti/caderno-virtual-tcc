package com.appoena.mobilenote;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.appoena.mobilenote.modelo.Agenda;
import com.appoena.mobilenote.modelo.Materia;
import com.appoena.mobilenote.screens.ActivityAgenda;

public class AdapterListAgenda extends BaseAdapter {
	
	private ArrayList<Agenda> agendas;
	private LayoutInflater inflater;
	
	public AdapterListAgenda(Context context, ArrayList<Agenda> agendas) {
		
		this.agendas = agendas;
		inflater = LayoutInflater.from(context);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return agendas.size();
	}

	@Override
	public Agenda getItem(int position) {
		// TODO Auto-generated method stub
		return agendas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void removeItemAtPosition(int position){
		agendas.remove(position);
	}
	
	public void addItem(Agenda a){
		agendas.add(a);
	}
	
	public void setItemAtPosition(Agenda a, int position){
		agendas.set(position, a);
	}
	
	public void setAgenda(ArrayList<Agenda> agendas){
		this.agendas = agendas;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = inflater.inflate(R.layout.row_agenda, null);
		Agenda a = getItem(position);
		boolean lembrar;
		((TextView) convertView.findViewById(R.id.titAgenda)).setText(a.getDescricao());
		((TextView) convertView.findViewById(R.id.horaAgenda)).setText(a.getHoraAgenda());
		((TextView) convertView.findViewById(R.id.dataAgenda)).setText(a.getDataAgenda());
		//((TextView) convertView.findViewById(R.id.materiaAgenda)).setText(a.nomeMateria(getActivity(), a.getIdAgenda()));
		
		//if para converter o campo lembrar =  1,  não lembrar = 0
		if(a.getLembrar() == 1){
			lembrar = true;
		}
		else{
			lembrar = false;
		}
			
			
		((CheckBox) convertView.findViewById(R.id.checkLembrar)).setChecked(lembrar);
		
		return convertView;
	}

}
