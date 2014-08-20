/**
 * @author willianvalerio
 * Classe respons�vel por inflar o xml da activity, exibir a janela de di�logo e devolver o resultado para classe anterior.
 */


package com.appoena.mobilenote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialog extends DialogFragment{
	
	View view; //respons�vel por inflar o xml.
	Bundle params; // repons�vel por enviar os par�metros para a classe que chamou.	
    CustomDialogListener mListener; // Usa essa inst�ncia da interface para entregar eventos de a��o
	
	static CustomDialog newInstance(){
		CustomDialog dialog= new CustomDialog();
		return dialog;
	}
	
	/*
	 * A activity que criar uma inst�ncia desse dialog fragment deve implementar
	 * essa interface para receber os retornos da chamada do evento.
	 */
	public interface CustomDialogListener{
		public void onDialogPositiveClick(DialogFragment dialog, Bundle params);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		view = View.inflate(getActivity(), R.layout.activity_adicionar_caderno, null);
		
		params = new Bundle();
		
		Dialog myDialog = new AlertDialog.Builder(getActivity())
			.setView(view)
			.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Dispara o evento onDialogPositiveClick para a activity que estiver escutando
					EditText teste = (EditText) view.findViewById(R.id.edtNomeCaderno);
					params.putString("CADERNO", teste.getText().toString());
					mListener.onDialogPositiveClick(CustomDialog.this, params);
					
					dismiss();
					
					
				}
			})
			.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// Dispara o evento onDialogNegativeClick para a activity que estiver escutando
					mListener.onDialogNegativeClick(CustomDialog.this);
					
				}
			}).create();
		
		
		return myDialog;
	}
	
	// Sobrescreve o m�todo onAttach para instanciar o CustomDialog
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verifica se a activity implementa a interface de callbacks
		try {
			// Instancia o CustomDialog para que possamos enviar eventos para o host
			mListener = (CustomDialogListener) activity;
		} catch (Exception e) {
			// Essa activity n�o implementa a interface, levanta exce��o
			throw new ClassCastException(activity.toString()+"deve implementar CustomDialogListener");

		}
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		//super.dismiss();
		Toast.makeText(getActivity(), "teste", 2).show();
	}
	
	

}
