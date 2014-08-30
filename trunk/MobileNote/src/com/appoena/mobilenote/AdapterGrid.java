package com.appoena.mobilenote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class AdapterGrid extends BaseAdapter{
	
	private String[] lista;
	private LayoutInflater inflater;

	public AdapterGrid(Context context, String[] lista) {
		

		this.lista = lista;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return lista.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return lista[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = inflater.inflate(R.layout.item_caderno, null);
		
		Button btnCaderno = (Button) convertView.findViewById(R.id.btnCaderno);
		btnCaderno.setText(lista[position]);
		
		return convertView;
	}

}
