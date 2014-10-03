package com.appoena.mobilenote.screens;

import java.util.ArrayList;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.GridView;

import com.appoena.mobilenote.AdapterGridCaderno;
import com.appoena.mobilenote.CustomDialog.CustomDialogListener;
import com.appoena.mobilenote.modelo.Caderno;
import com.appoena.mobilenote.R;

public class MainActivity extends Activity implements CustomDialogListener{
	

	private GridView gridView;
	private ArrayList<Caderno> arrayCaderno;
	private AdapterGridCaderno adapter;
	private Bundle params;
	private Caderno c;
	//private int INDEX_CADERNO = -1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		c = new Caderno();
		arrayCaderno = c.listaCadernos(this);
		clickAddCaderno();
		clickAbout();
		clickAgenda();
		
		adapter = new AdapterGridCaderno(this, arrayCaderno , getResources().getStringArray(R.array.array_colors));
		/*
		adapter.addItem(new Caderno("USJT",0));
		adapter.addItem(new Caderno("FISK",1));
		adapter.addItem(new Caderno("ULM",2));
		adapter.addItem(new Caderno("ABCD",3));*/
		adapter.notifyDataSetChanged();
		gridView= (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(adapter);
		onClickItemGrid();
		registerForContextMenu(gridView);
		setBundle();
		
		
	}
	
	/*
	 * MŽtodo respons‡vel pela a�‹o no bot‹o "Adicionar caderno"
	 */
	private void clickAddCaderno() {
		Button btnAddCaderno = (Button) findViewById(R.id.btn_add_caderno);
		btnAddCaderno.setOnClickListener( new View.OnClickListener() {
			

			@Override
			public void onClick(View v) {
				setBundle();
				showDialog(params);

			}
		});
	}
	
	

	/*
	 * MŽtodo respons‡vel pela a�‹o no bot‹o "Sobre"
	 */
	private void clickAbout() {
		Button btnAbout = (Button) findViewById(R.id.btn_about);
		btnAbout.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, ActivitySobre.class);
				startActivity(it);
			}
		});
		
	}
	
	/*
	 * MŽtodo respons‡vel pela a�‹o no bot‹o "Agenda"
	 */
	private void clickAgenda() {
		
		Button btnAgenda = (Button) findViewById(R.id.btn_calendar);
		btnAgenda.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent it = new Intent(MainActivity.this, ActivityAgenda.class);
				startActivity(it);
			}
		});

	}
	
	/*
	 * MŽtodo respons‡vel pelo clique no item do gridView
	 */
	
	private void onClickItemGrid() {
		gridView.setOnItemClickListener(new GridView.OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,long id) {
				Intent it = new Intent(MainActivity.this, ActivityMateria.class);
				startActivity(it);				
			}
			
		});

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		getMenuInflater().inflate(R.menu.actions, menu);
		super.onCreateContextMenu(menu, v, menuInfo);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId()) {
		case R.id.menu_del:
			GridView g = (GridView) findViewById(R.id.gridView1);
			g.setAdapter(adapter);
			Caderno ca = adapter.getItem(info.position);
			String nome = ca.getNome();
			adapter.removeItemAtPosition(info.position);
			ca.deletarCaderno(this, nome);
			adapter.notifyDataSetChanged();
			break;

		case R.id.menu_edit:
            Caderno c = adapter.getItem(info.position);
            setBundle();
            params.putString(getResources().getString(R.string.NOME_CADERNO), c.getNome());
            params.putInt(getResources().getString(R.string.COR_CADERNO), c.getColor());
            params.putInt(getResources().getString(R.string.INDEX_CADERNO), info.position);
            showDialog(params);
            
			break;
		}

		return super.onContextItemSelected(item);
	}
	
	private void showDialog(Bundle params){
		CustomDialogCaderno customDialog = CustomDialogCaderno.newInstance();
		customDialog.setArguments(params);
		customDialog.show(getFragmentManager(), null);
	}

	@Override
	public void onDialogPositiveClick(DialogFragment dialog, Bundle params) {
		String caderno = params.getString(getResources().getString(R.string.NOME_CADERNO));
        int cor = params.getInt(getResources().getString(R.string.COR_CADERNO));
        Caderno c = new Caderno(caderno, cor);
        
        if (!params.getBoolean(getResources().getString(R.string.EDICAO))) {
                adapter.addItem(c);
                c.incluirCaderno(this,cor,caderno);
        }else{
                int position = params.getInt(getResources().getString(R.string.INDEX_CADERNO));
                Caderno cAntes = adapter.getItem(position);
                String nomeAntigo = cAntes.getNome();
                adapter.setItemAtPosition(c, position);
                c.alterarCaderno(this, caderno, cor, nomeAntigo);
        }
        
        adapter.notifyDataSetChanged();
		
	}

	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}
	
	private void setBundle() {
		params = new Bundle();
		params.putInt(getResources().getString(R.string.VIEW), R.layout.activity_adicionar_caderno);

	}


}
