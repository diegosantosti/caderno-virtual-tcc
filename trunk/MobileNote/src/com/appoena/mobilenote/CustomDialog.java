/**
 * @author willianvalerio
 * Classe respons‡vel por inflar o xml da activity, exibir a janela de di‡logo e devolver o resultado para classe anterior.
 */


package com.appoena.mobilenote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

public class CustomDialog extends DialogFragment{
	
	protected View view; //respons‡vel por inflar o xml.
	protected Bundle params; // repons‡vel por enviar os par‰metros para a classe que chamou.	
	protected CustomDialogListener mListener; // Usa essa inst‰ncia da interface para entregar eventos de a�‹o
    protected Boolean edicao=false;
    protected AlertDialog myDialog;
		
	/*
	 * A activity que criar uma inst‰ncia desse dialog fragment deve implementar
	 * essa interface para receber os retornos da chamada do evento.
	 */
	public interface CustomDialogListener{
		public void onDialogPositiveClick(DialogFragment dialog, Bundle params);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	

	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		//carregar tema do dialog
		//ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), R.style.AlertDialog_MobileNote);
		params = getArguments();
		if(params==null){
			params = new Bundle();
		}else{
			view = View.inflate(getActivity(), params.getInt(getResources().getString(R.string.VIEW)), null);			
		}
		myDialog = new AlertDialog.Builder(getActivity())
			.setView(view)
			.setPositiveButton(R.string.ok, new Dialog.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					//em branco, vai ser sobrescrito pelo onclick que estara em cada classe
					
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

	public Spinner setColorSpinner(Spinner spinner) {
		AdapterListColors adapter = new AdapterListColors(getActivity(), 
															R.layout.item_color,
															getResources().getStringArray(R.array.array_colors));
		spinner.setAdapter(adapter);
		return spinner;
	}

	/*
	 * Sobrescreve o mŽtodo onAttach para instanciar o CustomDialog 
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// Verifica se a activity implementa a interface de callbacks
		try {
			// Instancia o CustomDialog para que possamos enviar eventos para o host
			mListener = (CustomDialogListener) activity;
		} catch (Exception e) {
			// Essa activity n‹o implementa a interface, levanta exce�‹o
			throw new ClassCastException(activity.toString()+"deve implementar CustomDialogListener");

		}
	}

}
