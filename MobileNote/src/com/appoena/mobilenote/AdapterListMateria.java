package com.appoena.mobilenote;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appoena.mobilenote.modelo.Materia;

public class AdapterListMateria extends BaseAdapter{
	
	private ArrayList<Materia> materias;
	private LayoutInflater inflater;
	private String[] color;
	private String[] diaSemana;
	
	public AdapterListMateria(Context context, ArrayList<Materia> materias, String[] color, String[] diaSemana) {
		this.materias = materias;
		this.color = color;
		this.diaSemana = diaSemana;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return materias.size();
	}

	@Override
	public Materia getItem(int position) {
		return materias.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = inflater.inflate(R.layout.row_materia, null);
		Materia m = getItem(position);
		((TextView) convertView.findViewById(R.id.nomeMateria)).setText(m.getNome());
		((TextView)convertView.findViewById(R.id.diaMateria)).setText(diaSemana[m.getDiaSemana()]);
		((TextView)convertView.findViewById(R.id.profMateria)).setText(m.getProfessor());
		((TextView)convertView.findViewById(R.id.emailMateria)).setText(m.getEmailProfessor());
		LinearLayout ln = (LinearLayout) convertView.findViewById(R.id.lnListMateria);
		ln.setBackgroundColor(Color.parseColor(color[m.getCor()]));
		
		return convertView;
	}

}
