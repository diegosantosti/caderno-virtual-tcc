package com.appoena.mobilenote.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.appoena.mobilenote.R;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

public abstract class OperacoesDrop {
	
	private static final int ADICIONAR_PASTA=0;
	private static final int ADICIONAR_ARQUIVO=1;
	private static final int RENOMEAR=2;
	private static final int EXCLUIR=3;
	private static DbxAccountManager accountManager;
	private static ArrayList<Dropbox> arrayDrop;
	
	
	public static void efetuarOperacao(Context context, int operacao, String newPath, String oldPath){
		accountManager = DbxAccountManager.getInstance(context, context.getString(R.string.APP_KEY), context.getString(R.string.APP_SECRET));
		if(!accountManager.hasLinkedAccount())return; //nao esta logado, sai do metodo sem fazer nada
		int conexDispositivo = getConexaoDispositivo(context); //conexao do dispositivo no momento.
		int conexSalva = getConfigConexao(context); //opcao de sincronizacao salva pelo usuario.
		if(conexSalva!=R.id.radio_naosinc && conexDispositivo!=0){
			//efetuar operacao
			
			try {
				DbxFileSystem dbxFileSystem = DbxFileSystem.forAccount(accountManager.getLinkedAccount());
				commitDropbox(operacao, newPath, oldPath, dbxFileSystem);
			} catch (Unauthorized e) {
				//nao esta logado, nao faz nada.
				e.printStackTrace();
			}
		}
		else{
			// adiciona no array para ser feito mais tarde.
			Dropbox d = new Dropbox();
			d.lerOperacoes(context);
			arrayDrop = new ArrayList<Dropbox>();
			arrayDrop = d.getArrayOperacoes();
			arrayDrop.add(new Dropbox(operacao, newPath, oldPath));
			d.gravarOperacoes(context, d);			
		}
	}
	
	
	
	/**
	 * Efetua a operacao no dropbox
	 * @param operacao
	 * 		Tipo de operacao:
	 * 			ADICIONAR_PASTA=0;
	 *			ADICIONAR_ARQUIVO=1;
	 *			RENOMEAR=2;
	 *			EXCLUIR=3;
	 * @param newPath
	 * 		Nome diretorio ou arquivo a ser adicionado ou renomeado
	 * @param oldPath
	 * 		Nome do diretorio ou arquivo antigo que sera renomeado
	 * @param dbxFileSystem
	 * 		Classe da API do Dropbox responsavel por efetuar as operacoes.
	 */
	public static void commitDropbox(int operacao, String newPath, String oldPath, DbxFileSystem dbxFileSystem){
		newPath = preparaCaminho(newPath);
		Log.v("commitDropbox", newPath);
		DbxPath path = new DbxPath(DbxPath.ROOT, newPath);
		switch (operacao) {
		
		case ADICIONAR_PASTA:
			 try {
				dbxFileSystem.createFolder(path);
			} catch (DbxException e) {
				// erro ao criar pasta
				e.printStackTrace();
			}
			break;
			
		case ADICIONAR_ARQUIVO:
			try {
				dbxFileSystem.create(path);
			} catch (DbxException e) {
				// erro ao adicionar arquivo
				e.printStackTrace();
			}
			break;
			
		case RENOMEAR:
			oldPath = preparaCaminho(oldPath);
			DbxPath old = new DbxPath(DbxPath.ROOT, oldPath);
			try {
				dbxFileSystem.move(old, path);
			} catch (DbxException e) {
				// erro ao renomear
				e.printStackTrace();
			}
			break;
		
		case EXCLUIR:
			try {
				dbxFileSystem.delete(path);
			} catch (DbxException e) {
				// erro ao excluir
				e.printStackTrace();
			}
			break;
		}
	}
	
	/**
	 * 
	 * @param context
	 * 		Contexto da aplicacao.
	 * @return
	 * 		Retorna a conexao do usuario no momento, 0 se nao houver conexao.
	 */
	
	public static int getConexaoDispositivo(Context context){
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(connectivityManager!=null){
			NetworkInfo info = connectivityManager.getActiveNetworkInfo();
			if(info== null) return 0; //sem conexao
			if(info.isConnected()){
				switch (info.getType()) {
				case ConnectivityManager.TYPE_WIFI:
					return ConnectivityManager.TYPE_WIFI; //conexao wifi
				case ConnectivityManager.TYPE_MOBILE:
					return ConnectivityManager.TYPE_MOBILE; //conexao 3g
				}
			}
			
		}else{
			return 0; //sem conexao
		}
		return 0; //sem conexao
	}
	
	/**
	 * 
	 * @param context 
	 * 		Contexto da aplicacao
	 * @return
	 * 		Retorna o tipo de conexao salva
	 */
	public static int getConfigConexao(Context context){
		SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.PREFS_NAME), 0);
		int conex = sp.getInt(context.getString(R.string.SYNC), 0);
		switch (conex) {
		case R.id.radio_sinc_wifi:
			return ConnectivityManager.TYPE_WIFI; //configuracao sincronizar wi-fi
		case R.id.radio_sinc_3g:
			return ConnectivityManager.TYPE_MOBILE; //configuracao sincronizar wi-fi e 3g
		case R.id.radio_naosinc: //nao sincronizar
			return 0;
		default:
			return 0; //nao sincronizar
		}
	}
	
	public static String preparaCaminho(String caminho){
		if(caminho.startsWith("/")){
			caminho = caminho.substring(1);
		}
		if(caminho.contains("com.appoena.mobilenote")){
			caminho = caminho.replace("com.appoena.mobilenote/", "");
		}
		return caminho;
	}
		

}
