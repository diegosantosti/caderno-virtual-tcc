package com.appoena.mobilenote;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;

import com.appoena.mobilenote.modelo.Caderno;

public class AdapterGridCaderno extends BaseAdapter{
	
	private ArrayList<Caderno> cadernos;
	private LayoutInflater inflater;
	private String[] color;

	public AdapterGridCaderno(Context context, ArrayList<Caderno> cadernos,String color[]) {
		

		this.cadernos = cadernos;
		inflater = LayoutInflater.from(context);
		this.color = color;
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
	
	public void removeItemAtPosition(int position){
		cadernos.remove(position);
	}
	
	public void addItem(Caderno c){
		cadernos.add(c);
	}
	
	public void setItemAtPosition(Caderno c, int position){
		cadernos.set(position, c);
	}
	

	public void setCadernos(ArrayList<Caderno> cadernos) {
		this.cadernos = cadernos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		convertView = inflater.inflate(R.layout.item_caderno, null);
		
		Button btnCaderno = (Button) convertView.findViewById(R.id.btnCaderno);
		Caderno c = getItem(position);
		btnCaderno.setText(c.getNome());
		btnCaderno.setClickable(false);
		btnCaderno.setFocusable(false);
		btnCaderno.setBackgroundResource(R.drawable.button_caderno_grid);
		LinearLayout ln = (LinearLayout) convertView.findViewById(R.id.linearBtnGrid);
		ln.setBackgroundColor(Color.parseColor(color[c.getColor()]));
		return convertView;
	}

}
