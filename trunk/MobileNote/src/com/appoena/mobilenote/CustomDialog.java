/**
 * @author willianvalerio
 * Classe responsável por inflar o xml da activity, exibir a janela de diálogo e devolver o resultado para classe anterior.
 */


package com.appoena.mobilenote;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Spinner;

import com.appoena.mobilenote.R;

public class CustomDialog extends DialogFragment{
	
	protected View view; //responsável por inflar o xml.
	protected Bundle params; // reponsável por enviar os parâmetros para a classe que chamou.	
	protected CustomDialogListener mListener; // Usa essa instância da interface para entregar eventos de ação
    protected Boolean edicao=false;
    protected AlertDialog myDialog;
		
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

}
