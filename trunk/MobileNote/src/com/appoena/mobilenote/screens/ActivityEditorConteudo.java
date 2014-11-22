package com.appoena.mobilenote.screens;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Conteudo;
import com.appoena.mobilenote.util.Dropbox;
import com.appoena.mobilenote.util.drawning.ActivityInserirDesenho;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFileSystem;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import org.w3c.dom.Document;  
import org.w3c.tidy.Tidy;  
//import org.xhtmlrenderer.pdf.ITextRenderer;  
import com.lowagie.text.DocumentException;  


//Classe respons�vel por criar o editor de conte�do do caderno

public class ActivityEditorConteudo extends Activity{

	private Bundle params;
	private String caminho;
	private String conteudoTemp;
	private boolean editMode = false;
	WebView wv;
	private Bundle paramsEscolherImagem;
	private static final int SELECIONAR_IMAGEM 	= 1; //codigo para retorno da activity do inserir imagem
	private static final int CONFIG_DROPBOX = 278; //codigo para retorno da activity config
	private static final int GRAVAR_AUDIO = 2; //codigo para retorno da activity do gravar Audio
	private static final int INSERIR_DESENHO = 3; //codigo para retorno da activity do Inserir desenho
	
	private DbxAccountManager accountManager;

	public ActivityEditorConteudo(){

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//recupera estado caso tenha mudado orienta��o de tela.
		if(savedInstanceState!=null){
			editMode = savedInstanceState.getBoolean("edicao");
		}
		Dropbox.execOperacoesSalva(getApplicationContext());		
		setContentView(R.layout.activity_editor);
		//Recupera o caminho do conte�do
		Intent it = getIntent();
		params = it.getExtras();
		caminho = params.getString("caminhoCadernoMateria");
		//L�GICA PARA RECUPERAR O ESTADO DO CONTE�DO QUANDO FOR ATUALIZADO NA PR�PRIA TELA.
					
		String conteudoTemp = params.getString("conteudoTemp");
		try{
			if(!conteudoTemp.isEmpty()){
				editMode = params.getBoolean("edicao");
				setConteudoTemp(params.getString("conteudoTemp"));
			}else{
				//Recupera o conte�do do arquivo e armazena na vari�vel
				setConteudoTemp(lerConteudoEditorTxt());
			}
		}catch(Exception e){
				//Vari�vel est� nula, recupera o conteudo do editor
				//Recupera o conte�do do arquivo e armazena na vari�vel
				setConteudoTemp(lerConteudoEditorTxt());
		}
		

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		setupEditorRaptor(); // CHAMA M�TODO PARA EXECUTAR O EDITOR DO RAPTOR
	}

	//M�todo para executar e efetuar o setup do Editor Raptor no modo de visualiza��o
	
	public void setupEditorRaptor(){

		wv = new WebView(getApplicationContext());		
		wv = (WebView) findViewById(R.id.webView1);
		
		WebSettings settings = wv.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSupportZoom(false);						
		wv.addJavascriptInterface(this, "EditorConteudoActivity");
		
		wv.loadUrl("file:///android_asset/raptor/example/example.html");
		
		wv.setWebViewClient(new WebViewClient() {
			@Override  
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				wv.pageDown(true);
				wv.requestFocus();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
				wv.pageDown(true);
				wv.requestFocus();
			}			
		});	
	}
	
	

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		getMenuInflater().inflate(R.menu.menu_conteudo, menu);
		if(editMode){
			menu.findItem(R.id.menu_salvar).setVisible(true);
			menu.findItem(R.id.menu_inserir_desenho).setVisible(true);
			menu.findItem(R.id.menu_inserir_imagem).setVisible(true);
			menu.findItem(R.id.menu_inserir_voz).setVisible(true);
			menu.findItem(R.id.menu_editar).setVisible(false);
			menu.findItem(R.id.menu_sincronizar).setVisible(false);
			menu.findItem(R.id.menu_compartilhar).setVisible(false);
			menu.findItem(R.id.menu_pesquisar).setVisible(false);

		}else{
			menu.findItem(R.id.menu_salvar).setVisible(false);
			menu.findItem(R.id.menu_inserir_desenho).setVisible(false);
			menu.findItem(R.id.menu_inserir_imagem).setVisible(false);
			menu.findItem(R.id.menu_inserir_voz).setVisible(false);
			menu.findItem(R.id.menu_editar).setVisible(true);
			menu.findItem(R.id.menu_sincronizar).setVisible(true);
			menu.findItem(R.id.menu_compartilhar).setVisible(true);
			menu.findItem(R.id.menu_pesquisar).setVisible(true);
			SearchView search = (SearchView) menu.findItem(R.id.menu_pesquisar).getActionView();
			int searchImg = getResources().getIdentifier("android:id/search_button", null, null);
			ImageView img = (ImageView)search.findViewById(searchImg);
			img.setImageResource(R.drawable.ic_action_pesquisar);
			search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

				@SuppressLint("NewApi")
				@Override
				public boolean onQueryTextSubmit(String query) {
					//M�todo respons�vel por pesquisar palavras dentro do conte�do
					wv.findAllAsync(query);
					return false;
				}

				@Override
				public boolean onQueryTextChange(String newText) {
					// DO NOTHING
					return false;
				}
			});

			search.setOnCloseListener(new SearchView.OnCloseListener() {

				@Override
				public boolean onClose() {
					wv.clearMatches();
					return false;
				}
			});
		}

		return true;
	}

	//acoes dos menus da ActionBar
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_editar:
			//codigo para editar
			editMode = true;
			invalidateOptionsMenu(); //recarrega os menus
			refreshEditor();//Recarrega o editor em modo de edi��o
			break;
		case R.id.menu_compartilhar:
			//codigo para compartilhar
			compartilhar();
			break;

		case R.id.menu_sincronizar:
			//codigo para sincronizar
			sincronizar(item, true);
			break;

		case R.id.menu_pesquisar:
			//	---------------------
			// 	DO NOTHING
			// 	C�DIGO DO PESQUISAR SER� REALIZADO NO M�TODO onQueryTextSubmit DA CLASSE SEARCH TYPE
			//	---------------------
			break;

		case R.id.menu_salvar:
			//codigo para salvar
			editMode = false;
			invalidateOptionsMenu(); //recarrega os menus
			salvarConteudoTxt(getConteudoTemp());
			//Recarrega o editor em modo de visualiza��o
			refreshEditor(); //Recarrega o editor em modo de visualiza��o
			sincronizar(null, false);
			break;

		case R.id.menu_inserir_desenho:
			//codigo para desenhar
			Intent itInserirDesenho = new Intent(ActivityEditorConteudo.this, ActivityInserirDesenho.class);
			Bundle paramsDesenho = new Bundle();
			paramsDesenho.putString("caminhoCadernoMateria", caminho);
			itInserirDesenho.putExtras(paramsDesenho);
			startActivityForResult(itInserirDesenho, INSERIR_DESENHO);			
			break;
		case R.id.menu_inserir_imagem:
			//codigo para inserir imagem
			Intent it = new Intent(ActivityEditorConteudo.this, ActivityEscolherImagem.class);
			startActivityForResult(it, SELECIONAR_IMAGEM);
			break;
		case R.id.menu_inserir_voz:
			//codigo para inderir voz
			
			Intent itAudio = new Intent(ActivityEditorConteudo.this, ActivityGravarAudio.class);
			Bundle paramsAudio = new Bundle();
			paramsAudio.putString("caminhoCadernoMateria", caminho);
			itAudio.putExtras(paramsAudio);
			startActivityForResult(itAudio, GRAVAR_AUDIO);
						
			break;
		case android.R.id.home:
			//chamar o metodo para salvar conteudo ao sair
			salvarConteudoTxt(getConteudoTemp());
			finish();
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("edicao", editMode);
		//inserir aqui metodo para salvar quando mudar orientacao da tela.

		//Diego - Anota��o
		//Salvar conte�do na vari�vel para o arquivo txt
		salvarConteudoTxt(getConteudoTemp());
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		//Salvar o conte�do quando a activity for finalizada
		salvarConteudoTxt(getConteudoTemp());
		super.onDestroy();
	}

	//M�todos para gravar o conte�do tempor�rio do Editor raptor para uma vari�vel
	private void setConteudoTemp(String conteudoTemp){
		this.conteudoTemp = conteudoTemp;
	}

	private String getConteudoTemp(){
		return this.conteudoTemp;
	}

	@JavascriptInterface
	public void salvarConteudoEditor(String conteudo){
		setConteudoTemp(conteudo);
	}

	@JavascriptInterface
	public String lerConteudoEditor(){
		return getConteudoTemp();
	}
	
	@JavascriptInterface
	public boolean isEditar(){
		return editMode;
	}

	@JavascriptInterface
	public void goLastPage(){
		wv.pageDown(true);
		wv.requestFocus();
	}

	//M�todos para recuperar o conte�do do arquivo txt
	public void salvarConteudoTxt(String conteudo){
		Conteudo cont = new Conteudo();
		cont.salvarConteudo(caminho , conteudo);
	}

	public String lerConteudoEditorTxt(){
		Conteudo cont = new Conteudo();
		String conteudo = cont.lerConteudo(caminho);
		return conteudo;
	}


	//recupera as informa��es de outras activities
	/**
	 * Receive the result from the startActivity
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == Activity.RESULT_OK) {

			switch (requestCode) {
			case SELECIONAR_IMAGEM:	
				Bundle params = data.getExtras();
				String caminhoImagem;
				caminhoImagem = params.getString("filePathImage");

				//Verifica se o caminho est� preenchido, se sim ent�o insere a imagem no editor
				if(!caminhoImagem.isEmpty()){
					inserirImagemEditor(caminhoImagem);
				}

				break;
			
			case GRAVAR_AUDIO:	
				Bundle paramsAudio = data.getExtras();
				String caminhoAudio;
				caminhoAudio = paramsAudio.getString("caminhoAudio");
				
				//Verifica se o caminho est� preenchido, se sim ent�o insere a imagem no editor
				if(!caminhoAudio.isEmpty()){
					inserirAudioEditor(caminhoAudio);
				}

				break;
				
			case INSERIR_DESENHO:	
				Bundle paramsDesenho = data.getExtras();
				String caminhoDesenho;
				caminhoDesenho = paramsDesenho.getString("caminhoDesenho");
				
				//Verifica se o caminho est� preenchido, se sim ent�o insere a imagem no editor
				if(!caminhoDesenho.isEmpty()){
					inserirImagemEditor(caminhoDesenho);
				}
				break;
				
			case CONFIG_DROPBOX:
				//Menu menu = getResources().
				sincronizar(null, true);
				break;
			default:
				break;
			}
		}
	}

	private void inserirImagemEditor(String caminhoImagem) {

		String tagHtml = "<p><img src=\"" + caminhoImagem + "\" width='400' /></p>";
		String novoConteudo = getConteudoTemp() + tagHtml;
		setConteudoTemp(novoConteudo);
		
		refreshEditor();

	}
	
	private void inserirAudioEditor(String caminhoAudio) {
		
		//Recupera a data/hora atual do sistema
		Locale locale = new Locale("pt","BR"); 
		GregorianCalendar calendar = new GregorianCalendar(); 
		SimpleDateFormat formatador = new SimpleDateFormat("dd' de 'MMMM' de 'yyyy' - 'HH':'mm'h'",locale); 
		String dataHoraAtual = formatador.format(calendar.getTime());
		
		String tagHtml = 
			"<p><audio controls>" +
				"<source src=\"" + caminhoAudio + "\" type=\"audio/wav\">" +
				"Your browser does not support the audio element."+
			"</audio></p><p>"+dataHoraAtual+"</p>";
		
//		String tagHtml = "<p><img src=\"" + caminhoAudio + "\" width='500' /></p>";
		String novoConteudo = getConteudoTemp() + tagHtml;
		setConteudoTemp(novoConteudo);
		
		refreshEditor();

	}
	

	// compartilhar
	public void compartilhar(){

		//converter em pdf
		/*try{
			convert("<h1 style='color:red'>Hello PDF</h1>", new FileOutputStream("/sdcard"+caminho+"/conteudo.pdf"));
		}catch(Exception e){
			
		}*/
		Resources res = getResources();  
		final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		Log.i("Caminho", caminho);
		Uri uri = Uri.parse("file:///sdcard"+caminho+"/conteudo.txt");
		this.grantUriPermission("com.appoena.mobilenote", uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
		emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard"+caminho+"/conteudo.txt"));  
		emailIntent.setType("text/plain");  
		this.startActivity( Intent.createChooser(emailIntent,res.getString(R.string.menu_compartilhar)));   
	}
	
	public void refreshEditor(){
				
		Intent it = new Intent(ActivityEditorConteudo.this, ActivityEditorConteudo.class);
		
		Bundle paramsReload = new Bundle();
		paramsReload.putBoolean("edicao", editMode);
		paramsReload.putString("conteudoTemp", getConteudoTemp());
		paramsReload.putString("caminhoCadernoMateria",caminho);
		it.putExtras(paramsReload);
		finish();
		startActivity(it);
		
	}

	/*public static void convert(String input, OutputStream out) throws DocumentException{  
        convert(new ByteArrayInputStream(input.getBytes()), out);
        }
     

    public static void convert(InputStream input, OutputStream out) throws DocumentException{  
        Tidy tidy = new Tidy();           
        Document doc = tidy.parseDOM(input, null);  
        ITextRenderer renderer = new ITextRenderer();  
        renderer.setDocument(doc, null);  
        renderer.layout();         
        renderer.createPDF(out);          
    }        */  

	public void sincronizar(final MenuItem item, final boolean forced){
		accountManager = DbxAccountManager.getInstance(getApplication(), getString(R.string.APP_KEY), getString(R.string.APP_SECRET));
		if(item!=null)item.setActionView(R.layout.progress_bar);
		if(accountManager.hasLinkedAccount()){
			//sincronizar
			//Thread para rodar em background
			new Thread(){
				public void run(){
					Dropbox.criarArquivo(caminho+"/conteudo.txt", forced, getApplicationContext());
					//metodo que execuda na Thread principal para mostrar mensagem de sincroniza��o concluida.
					runOnUiThread(new Runnable() {
						public void run() {
							if(item!=null){
								item.setActionView(null);
								item.setIcon(R.drawable.ic_cloud_upload_white_48dp);
							}
							if(forced)Toast.makeText(getApplication(), getString(R.string.sync_ok), Toast.LENGTH_SHORT).show();
						}
					});
				}	
			}.start();			
		}
		else{
			Intent  it = new Intent(ActivityEditorConteudo.this, ActivityConfig.class);
			startActivityForResult(it, CONFIG_DROPBOX);	
		}
	}
}