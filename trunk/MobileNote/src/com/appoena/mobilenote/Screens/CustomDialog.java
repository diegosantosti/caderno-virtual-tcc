/**
 * @author willianvalerio
 * Classe responsável por inflar o xml da activity, exibir a janela de diálogo e devolver o resultado para classe anterior.
 */


package com.appoena.mobilenote.Screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.appoena.mobilenote.AdapterListColors;
import com.appoena.mobilenote.R;

public class CustomDialog extends DialogFragment{
	
	View view; //responsável por inflar o xml.
	Bundle params; // reponsável por enviar os parâmetros para a classe que chamou.	
    CustomDialogListener mListener; // Usa essa instância da interface para entregar eventos de ação
	
	static CustomDialog newInstance(){
		CustomDialog dialog= new CustomDialog();
		return dialog;
	}
	
	/*
	 * A activity que criar uma instância desse dialog fragment deve implementar
	 * essa interface para receber os retornos da chamada do evento.
	 */
	public interface CustomDialogListener{
		public void onDialogPositiveClick(DialogFragment dialog, Bundle params);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.activity_adicionar_caderno, null);
		setColorSpinner();
		params = new Bundle();
		
		final AlertDialog myDialog = new AlertDialog.Builder(getActivity())
			.setView(view)
			.setPositiveButton(R.string.ok, new Dialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//em branco, vai ser sobrescrito pelo onclick
					
				}
			})
			.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Dispara o evento onDialogNegativeClick para a activity que estiver escutando
					mListener.onDialogNegativeClick(CustomDialog.this);
					
				}
			}).create();
		
		myDialog.setOnShowListener(new DialogInterface.OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				
				Button b = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				//Sobrescreve o onClick do positive button para poder conferir os dados antes de fechar 
				b.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// Dispara o evento onDialogPositiveClick para a activity que estiver escutando
						if(devolverCaderno())myDialog.dismiss();
						else				 Toast.makeText(getActivity(), "Informe o nome do caderno", Toast.LENGTH_SHORT).show();
						
					}
				});
				
			}
		});
		
		return myDialog;
	}
	
	/*
	 * Sobrescreve o método onAttach para instanciar o CustomDialog 
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verifica se a activity implementa a interface de callbacks
		try {
			// Instancia o CustomDialog para que possamos enviar eventos para o host
			mListener = (CustomDialogListener) activity;
		} catch (Exception e) {
			// Essa activity não implementa a interface, levanta exceção
			throw new ClassCastException(activity.toString()+"deve implementar CustomDialogListener");

		}
	}
	
	
	public void setColorSpinner() {
		Spinner spinnerColor = (Spinner)view.findViewById(R.id.spinner_color);
		AdapterListColors adapter = new AdapterListColors(getActivity(), 
															R.layout.item_color,
															getResources().getStringArray(R.array.array_colors));
		spinnerColor.setAdapter(adapter);
	}
	
	private boolean devolverCaderno(){
		EditText edt = (EditText) view.findViewById(R.id.edtNomeCaderno);
		Spinner spn = (Spinner) view.findViewById(R.id.spinner_color);
		int i = spn.getSelectedItemPosition();
		String color[] = getResources().getStringArray(R.array.array_colors);
		if(!edt.getText().toString().isEmpty() && !color[i].isEmpty()){
			params.putString("CADERNO", edt.getText().toString());
			params.putString("COR", color[i]);
			mListener.onDialogPositiveClick(CustomDialog.this, params);
			return true;			
		}else{
			return false;
		}
	}
	

}
