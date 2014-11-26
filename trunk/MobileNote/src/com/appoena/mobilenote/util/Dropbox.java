package com.appoena.mobilenote.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;

import com.appoena.mobilenote.R;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;

public abstract class Dropbox {
	
	private static final int ADICIONAR_PASTA=0;
	private static final int ADICIONAR_ARQUIVO=1;
	private static final int RENOMEAR=2;
	private static final int EXCLUIR=3;
	private static DbxAccountManager accountManager;
	private static ArrayList<Operacoes> arrayDrop;
	private static DbxFileSystem dbxFileSystem;
	
	
	private static void efetuarOperacao(Context context, int operacao, String newPath, String oldPath){
		if(getDbxFileSystem(context)==null)return; //nao autorizado
		int conexDispositivo = getConexaoDispositivo(context); //conexao do dispositivo no momento.
		int conexSalva = getConfigConexao(context); //opcao de sincronizacao salva pelo usuario.
		if(isSincronizar(conexSalva, conexDispositivo)){
			commitDropbox(operacao, newPath, oldPath, dbxFileSystem);
		}
		else{
			salvarOperacao(context, operacao, newPath, oldPath);
		}
	}
	

	
	private static void salvarOperacao(Context context, int operacao, String newPath, String oldPath){
		Operacoes operacoes = new Operacoes(operacao, newPath, oldPath);
		arrayDrop = (ArrayList<Operacoes>) operacoes.lerOperacoes(context);
		if(arrayDrop==null)arrayDrop = new ArrayList<Operacoes>();
		Log.v("salvarOperacao", "add operacao");
		arrayDrop.add(operacoes);
		operacoes.setArrayOperacoes(arrayDrop);
		operacoes.gravarOperacoes(context, operacoes);
	}
	
	public static void execOperacoesSalva(final Context context){
		//executar em segundo plano para nao comprometer a interacao com o usuario.
		new Thread(){
			public void run(){
				Log.v("excutar operacoes", "iniciou");
				if(!isSincronizar(getConfigConexao(context), getConexaoDispositivo(context))) return;
				Log.v("excutar operacoes", "Conexao ok");
				accountManager = DbxAccountManager.getInstance(context, context.getString(R.string.APP_KEY), context.getString(R.string.APP_SECRET));
				if(!accountManager.hasLinkedAccount())return; //nao esta logado, sai do metodo sem fazer nada
				Log.v("excutar operacoes", "Logado com drop");
				if(getDbxFileSystem(context)==null)return;
				Operacoes operacoes = new Operacoes();
				arrayDrop = (ArrayList<Operacoes>) operacoes.lerOperacoes(context);
				if(arrayDrop==null)return;
				Log.v("excutar operacoes", "Leu operacoes");
				int index = arrayDrop.size()-1;
				Log.v("excutar operacoes", "Drop antes do for: "+ arrayDrop.size());
				for (int i = 0; i<=index; i++) {
					Log.v("excutar operacoes", "Size: "+ arrayDrop.size());
					Log.v("excutar operacoes", "Vou editar: "+ i);
					operacoes = arrayDrop.get(0);
					commitDropbox(operacoes.getOperacao(), operacoes.getNewPath(), operacoes.getOldPath(), dbxFileSystem);
					arrayDrop.remove(0);
				}
				Log.v("excutar operacoes", "Tamanho do array no fim:" +arrayDrop.size());
				operacoes.setArrayOperacoes(arrayDrop);
				operacoes.gravarOperacoes(context, operacoes);
			}
		}.start();	
	}
	
	
	
	public static void criarArquivo(String newPath, boolean forced, Context context){
		//se clicou compartilhar, da o commit direto
		if(forced)commitDropbox(ADICIONAR_ARQUIVO, newPath, null, getDbxFileSystem(context));
		//se clicou no salvar, vai tentar efetuar a operacao.
		else efetuarOperacao(context, ADICIONAR_ARQUIVO, newPath, null);


	}
	
	public static void excluir(String path, Context context){
		salvarOperacao(context, EXCLUIR, path, null);
	}
	
	public static void renomear(String newPath, String oldPath, Context context){
		salvarOperacao(context, RENOMEAR, newPath, oldPath);
	}
	
	public static void criarPasta(String newPath, Context context){
		salvarOperacao(context, ADICIONAR_PASTA, newPath, null);
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
	private static void commitDropbox(int operacao, String newPath, String oldPath, DbxFileSystem dbxFileSystem){
		File newFile = new File(new File(Environment.getExternalStorageDirectory(), newPath).getPath());
		newPath = preparaCaminho(newPath);
		Log.v("commitDropbox", "Caminho preparado: " + newPath);
		Log.v("commitDropbox caminho bruto: ", newFile.toString());
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
			sincronizarDiretorios(newPath, dbxFileSystem, newFile);
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
	
	private static void sincronizarDiretorios(String newPath,DbxFileSystem dbxFileSystem, File newFile){
		DbxPath path = new DbxPath(DbxPath.ROOT, newPath);
		DbxFile dbxFile = null;
		try {
			File files[] = newFile.listFiles();
			if(files!=null){
				Log.v("sincronizarPasta", "Arquivos: "+files.length);
				for(File dirOrFile : files){
					Log.v("sincronizarPasta", "toString arquivos : "+dirOrFile.toString());
					Log.v("sincronizarPasta", "getName arquivos: "+dirOrFile.getName());
					path = new DbxPath(new DbxPath("/"+preparaCaminho(dirOrFile.getParent())), dirOrFile.getName());
					if(dirOrFile.isDirectory()){
						//path = new DbxPath(new DbxPath(dirOrFile.getParent()), dirOrFile.getName());
						Log.v("sincronizarPasta", "eh pasta");
						if(!dbxFileSystem.exists(path)){
							dbxFileSystem.createFolder(path);
							Log.v("sincronizarPasta", "Pasta criada");
						}
						Log.v("sincronizarPasta", "Recursividade:" + preparaCaminho(dirOrFile.toString()));
						sincronizarDiretorios(preparaCaminho(dirOrFile.toString()), dbxFileSystem, dirOrFile);
					}else{
						Log.v("sincronizarPasta", "eh arquivo");
						Log.v("sincronizarPasta", "caminho do arquivo: "+ preparaCaminho(dirOrFile.toString()));
						if(dbxFileSystem.exists(path)){
							dbxFile = dbxFileSystem.open(path); //se existe, abre o arquivo para leitura
						}else{
							dbxFile = dbxFileSystem.create(path);
						}
						try {
							dbxFile.writeFromExistingFile(dirOrFile, false);
							Log.v("sincronizarPasta", "Arquivo upado");
						}catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							dbxFile.close();
						}
						
					}
				}
			}
			
		} catch (DbxException e) {
			// erro ao adicionar arquivo
			e.printStackTrace();
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
	private static int getConfigConexao(Context context){
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
	
	/**
	 * 
	 * @param conexSalva
	 * 		Conexao salva pelo usuario
	 * @param conexDispositivo
	 * 		Conexao atual do dispositivo no momento
	 * @return
	 * 		Se deve sincronizar ou nao
	 */
	private static boolean isSincronizar(int conexSalva, int conexDispositivo){
		if(conexSalva==0){
			Log.v("isSincronizar", "Nunca sincronizar");
			return false;
		}		
		// se conexao do dispositivo for nula, salva operacao e sai do metodo.
		if(conexDispositivo==0){
			Log.v("isSincronizar", "Conexao do dispositovo nula");
			return false;
		}
		
		//se conexao salva for igual a 3g e wifi, chegando aqui ja garanti que existe conexao entao efetuo a operacao e sai do metodo.
		if(conexSalva==ConnectivityManager.TYPE_MOBILE){
			Log.v("isSincronizar", "Conexao salva eh wifi e 3g");
			return true;
		}
		if(conexSalva==conexDispositivo){
			Log.v("isSincronizar", "Conexao salva eh wifi");
			return true;
		}
		
		Log.v("isSincronizar", "Nenhuma condicao foi satisfeita");
		return false;
	}
	
	private static String preparaCaminho(String caminho){
		if(caminho.startsWith("/")){
			caminho = caminho.substring(1);
		}
		if(caminho.contains("com.appoena.mobilenote")){
			caminho = caminho.replace("com.appoena.mobilenote/", "");
		}
		if(caminho.contains("storage/emulated/0/")){
			caminho = caminho.replace("storage/emulated/0/", "");
		}
		return caminho;
	}
	
	public static DbxFileSystem getDbxFileSystem(Context context){
		accountManager = DbxAccountManager.getInstance(context, context.getString(R.string.APP_KEY), context.getString(R.string.APP_SECRET));
		if(dbxFileSystem==null){
			if(!accountManager.hasLinkedAccount()) {
				Log.v("getDbxFileSystem", "has linkek account false");
				return null;
			}
			try {
				dbxFileSystem = DbxFileSystem.forAccount(accountManager.getLinkedAccount());
				Log.v("getDbxFileSystem", "Retornei ok");
				return dbxFileSystem;
			} catch (Unauthorized e) {
				e.printStackTrace();
				Log.v("getDbxFileSystem", "Unauthorized");
				return null;
			}
		}
		else{
			if(!dbxFileSystem.getAccount().isLinked()){
				try {
					Log.v("getDbxFileSystem", "Nao estava nulo mas nao estava linkado");
					return dbxFileSystem = DbxFileSystem.forAccount(accountManager.getLinkedAccount());
				} catch (Unauthorized e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		Log.v("getDbxFileSystem", "nao estava nulo");
		return dbxFileSystem;
	}
		

}
