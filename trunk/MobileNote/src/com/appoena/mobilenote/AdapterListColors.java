package com.appoena.mobilenote;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

public class AdapterListColors extends ArrayAdapter<String>{
	
	private String[] colors;
	private Activity context;

	
	public AdapterListColors(Activity context, int textViewResourceId,   String[] objects) {
        super(context, textViewResourceId, objects);
        colors = objects;
		this.context = context;
        
    }
	
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public int getCount() {

		return colors.length;
	}

	@Override
	public String getItem(int position) {

		return colors[position];
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//chama a view personalizada
		return getCustomView(position, convertView, parent);
	}
	
	/*
	 * Retorna a view personalizada, atribuindo a cor a cada botao do listview.
	 */
	private View getCustomView(int position, View convertView, ViewGroup parent) {
		
		View row;
		LayoutInflater inflater = context.getLayoutInflater(); 
		row = inflater.inflate(R.layout.item_color, parent, false); //infla o xml
		
		Button btnColor = (Button)row.findViewById(R.id.btnColor);
		btnColor.setBackgroundColor(Color.parseColor(colors[position])); //seta as cores dos botoes

		btnColor.setClickable(false); //botao nao vai ser clicavel
		btnColor.setFocusable(false); //retirar foco do botao para deixar no listview
		
		return row;

	}

}
