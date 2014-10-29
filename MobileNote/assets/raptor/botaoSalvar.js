Raptor.registerUi(new Raptor.Button({
    name: 'botaoSalvar',
     
    action: function() {
        
        //Recupera o conteúdo do html e passa para o código java
        var conteudo = "";            
        conteudo = this.raptor.getHtml();
		       
        //Gravar o conteúdo numa string e passar para o programa java gravar localmente.
        EditorConteudoActivity.salvarConteudo(conteudo);
    }
}));