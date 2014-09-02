package com.appoena.mobilenote;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class AdapterGridCaderno extends BaseAdapter{
	
	private ArrayList<Caderno> cadernos;
	private LayoutInflater inflater;

	public AdapterGridCaderno(Context context, ArrayList<Caderno> cadernos) {
		

		this.cadernos = cadernos;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cadernos.size();
	}

	@Override
	public Caderno getItem(int position) {
		// TODO Auto-generated method stub
		return cadernos.get(position);
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
		Caderno c = getItem(position);
		btnCaderno.setText(c.getNome());
		btnCaderno.setBackgroundColor(Color.parseColor(c.getColor()));
		
		return convertView;
	}

}
