Raptor.registerUi(new Raptor.Button({
    name: 'botaoSalvar',
     
    action: function() {
        
        //Recupera o conte�do do html e passa para o c�digo java
        var conteudo = "";            
        conteudo = this.raptor.getHtml();
		       
        //Gravar o conte�do numa string e passar para o programa java gravar localmente.
        EditorConteudoActivity.salvarConteudo(conteudo);
    }
}));