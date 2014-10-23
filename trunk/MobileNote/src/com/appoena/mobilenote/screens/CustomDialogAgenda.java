package com.appoena.mobilenote.screens;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.appoena.mobilenote.CustomDialog;
import com.appoena.mobilenote.DatePickerFragment;
import com.appoena.mobilenote.R;
import com.appoena.mobilenote.TimePickerFragment;
import com.appoena.mobilenote.modelo.Caderno;

public class CustomDialogAgenda extends CustomDialog{

	private TextView txtData;
	private TextView txtHora;
	private EditText edtDescricao;
	private CheckBox checkLembrar;
	private Spinner  spCaderno;
	//private Spinner  spMateria;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		super.onCreateDialog(savedInstanceState);
		txtData = (TextView) view.findViewById(R.id.txtData);
		txtHora = (TextView) view.findViewById(R.id.txtHora);
		checkLembrar = (CheckBox) view.findViewById(R.id.checkLembrar);
		edtDescricao = (EditText) view.findViewById(R.id.edtDescLembrete);
		
		// Preenchendo o Spinner Caderno
		spCaderno = (Spinner) view.findViewById(R.id.spinnerCaderno);
		Caderno c = new Caderno();
		ArrayList<String> listCaderno = c.nomesCadernos((getActivity()));
		ArrayAdapter<String> adpCaderno = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,listCaderno);
		spCaderno.setAdapter(adpCaderno);
		setDataHora();
		onClickDatahora();
		try {
			popularAgenda();
		} catch (Exception e) {

	}

		myDialog.setOnShowListener(new DialogInterface.OnShowListener() {	
			@Override
			public void onShow(DialogInterface dialog) {
				Button b = myDialog.getButton(AlertDialog.BUTTON_POSITIVE);
				//Sobrescreve o onClick do positive button para poder conferir os dados antes de fechar 
				b.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Dispara o evento onDialogPositiveClick para a activity que estiver escutando
						if(devolverAgenda())myDialog.dismiss();
						else{
							Toast.makeText(getActivity(), R.string.informe_descricao_agenda, Toast.LENGTH_SHORT).show();
							edtDescricao.requestFocus();
						}
					}
				});
			}
		});
		return myDialog;
	}


	/**
	 * Seta o onclick do txtData e txtHora
	 */
	private void onClickDatahora() {
		txtData.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showDatePicker();
			}
		});
		txtHora.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showTimePicker();
			}
		});

	}



	static CustomDialogAgenda newInstance(){
		CustomDialogAgenda dialog= new CustomDialogAgenda();
		return dialog;
	}

	private void popularAgenda() {
		String descricao = params.getString(getResources().getString(R.string.DESC_AGENDA));
		if(descricao!=null){
			edicao = true; //seta a variavel como true para a devolucao do caderno
			edtDescricao.setText(descricao);
			txtData.setText(params.getString(getResources().getString(R.string.DATA_AGENDA)));
			txtHora.setText(params.getString(getResources().getString(R.string.HORA_AGENDA)));
			checkLembrar.setChecked(params.getBoolean(getResources().getString(R.string.LEMBRAR)));
			
		}

	}

	private boolean devolverAgenda() {
		if(!edtDescricao.getText().toString().isEmpty()){
			params.putString(getResources().getString(R.string.DESC_AGENDA), edtDescricao.getText().toString());
			params.putString(getResources().getString(R.string.DATA_AGENDA), txtData.getText().toString());
			params.putString(getResources().getString(R.string.HORA_AGENDA), txtHora.getText().toString());
			params.putBoolean(getResources().getString(R.string.LEMBRAR), checkLembrar.isChecked());
			params.putBoolean(getResources().getString(R.string.EDICAO), edicao);
			mListener.onDialogPositiveClick(CustomDialogAgenda.this, params);
			return true;
		}else{
			return false;
		}

	}

	/**
	 * Sseta a data e a hora nos txtData
	 * e txt Hora
	 */
	public void setDataHora(){
		final Calendar c = Calendar.getInstance();
		setData(c.get(Calendar.DAY_OF_MONTH),c.get(Calendar.MONTH)+1,c.get(Calendar.YEAR));
		setHora(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
	}

	private void setData(int dia, int mes, int ano) {
		StringBuilder data = new StringBuilder();
		if(dia<10)data.append("0"); //adiciona um 0 caso dia menor que 10
		data.append(dia);
		data.append("/");
		if(mes<10)data.append("0"); //adiciona um 0 caso mes menor que 10
		data.append(mes);
		data.append("/");
		data.append(ano);
		txtData.setText(data);
	}

	public void setHora(int hora, int minuto){
		String aux="", auxHora = "";
		//se o minuto for menor que 10, coloca um zero a esquerda do valor
		if(minuto<10) aux = "0";
		if(hora<10) auxHora = "0";
		txtHora.setText(new StringBuilder()
		.append(auxHora)
		.append(hora)
		.append(":")
		.append(aux)
		.append(minuto));
	}

	/**
	 *Metodo que exibe o datePicker e implementa o retorno no txtData 
	 */
	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();
		//Seta a data do txt no picker
		Bundle args = new Bundle();
		String[] data = txtData.getText().toString().split("/");
		args.putInt("year", Integer.parseInt(data[2]));
		args.putInt("month", Integer.parseInt(data[1])-1);
		args.putInt("day", Integer.parseInt(data[0]));
		date.setArguments(args);

		//Seta o callBack para retornar a data
		date.setCallBack(ondate);
		date.show(getFragmentManager(), "Date Picker");
	}

	//implementa o retorno
	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			setData(dayOfMonth, monthOfYear+1, year);
		}
	};

	/**
	 *Metodo que exibe o timePicker e implementa o retorno no txtHora 
	 */
	private void showTimePicker() {
		TimePickerFragment time = new TimePickerFragment();
		//Seta a data do txt no picker
		Bundle args = new Bundle();
		String[] hora = txtHora.getText().toString().split(":");
		args.putInt("hour", Integer.parseInt(hora[0]));
		args.putInt("minute", Integer.parseInt(hora[1]));
		time.setArguments(args);

		//Seta o callBack para retornar a hora
		time.setCallBack(ontime);
		time.show(getFragmentManager(), "Time Picker");
	}

	//implementa o retorno
	OnTimeSetListener ontime = new OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			setHora(hourOfDay, minute);

		}	
	};


}
