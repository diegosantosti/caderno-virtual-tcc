package com.appoena.mobilenote.screens;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.appoena.mobilenote.R;
import com.appoena.mobilenote.modelo.Caderno;
import com.appoena.mobilenote.modelo.Conteudo;
import com.appoena.mobilenote.modelo.Materia;
import com.appoena.mobilenote.util.Diretorio;

public class SplashActivity extends Activity implements Runnable{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		SharedPreferences sharedPreferences = getSharedPreferences(getResources().getString(R.string.PREFS_NAME),0);
		Boolean primeiraExec = sharedPreferences.getBoolean("primeiraExec", true);
		if(primeiraExec){
			Diretorio.excluirDiretorio("");//exclui diretorio caso exista
			Diretorio.criaDiretorio("");//cria novamente
			//cria um caderno e o diretorio
			Caderno c = new Caderno("MOBILENOTE", 0);
			c.incluirCaderno(this, c.getColor(), c.getNome());
			Diretorio.criaDiretorio("/"+c.getNome());
			//cria a materia e os diretorios
			Materia m = new Materia("EXEMPLO", 1, "Professor", "professor@mobilenote.com.br", 0, 0);
			m.incluirMateria(this, m.getNome(), m.getProfessor(), m.getEmailProfessor(), m.getCor(), m.getDiaSemana(), c.consultarCaderno(this).get(0).getId());
			Diretorio.criaDiretorio("/"+c.getNome()+"/"+m.getNome()); //cria o diretorio
			Diretorio.criaDiretorio("/"+c.getNome()+"/"+m.getNome()+ "/"+ getString(R.string.AUDIOS));
			Diretorio.criaDiretorio("/"+c.getNome()+"/"+m.getNome()+"/" + getString(R.string.IMAGENS));
			String texto="<p>Esse é um conteúdo de exemplo, com o <strong class=\"cms-bold\">Mobile Note</strong>&nbsp;é &nbsp;possível :</p><p>Criar cadernos e matérias.&nbsp;</p><p>Crias agendas e vincular com o calendário do dispositivo.&nbsp;</p><p><span class=\"cms-color cms-red\">Inserir imagens.&nbsp;</span></p><p><span class=\"cms-color cms-green\">Inserir áudio</span>.&nbsp;</p><p><span class=\"cms-color cms-purple\">Inserir desenhos a mão livre.&nbsp;</span></p><p>Vincular com o dropbox e sincronizar o conteúdo completo.&nbsp;</p>";
			//cria um conteudo de exemplo
			Conteudo conteudo = new Conteudo();
			conteudo.salvarConteudo("/com.appoena.mobilenote/"+c.getNome()+"/"+m.getNome(), texto);
			//seta o sharedPreferences como falso para nao executar novamente
			SharedPreferences.Editor edit = sharedPreferences.edit();
			edit.putBoolean("primeiraExec", false);
			edit.commit();
			
		}
		
		Handler h = new Handler();
		h.postDelayed(this, 3000);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, ActivityCaderno.class));
		finish();
	}

}

