package br.ce.nsouzarj.builder;

import br.ce.nsouzarj.entidades.Filme;

public class FilmeBuilder {
    private Filme filme;
    private FilmeBuilder(){

    }

    public static FilmeBuilder filmeBuilder(){
        FilmeBuilder filmeBuilder = new FilmeBuilder();
        filmeBuilder.filme= new Filme();
        filmeBuilder.filme.setNome("Filme Builder");
        filmeBuilder.filme.setEstoque(2);
        filmeBuilder.filme.setPrecoLocacao(4.0);
        return filmeBuilder;
    }

    public Filme agora(){
        return filme;
    }

    public FilmeBuilder semEstoque(){
        filme.setEstoque(0);
        return  this;
    }

    public FilmeBuilder comValor(Double comValor){
        filme.setPrecoLocacao(comValor);
        return  this;
    }

    public FilmeBuilder comNome(String comNome){
        filme.setNome(comNome);
        return  this;
    }
}
