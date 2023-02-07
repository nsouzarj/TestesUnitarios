package br.ce.nsouzarj.builder;

import br.ce.nsouzarj.entidades.Filme;
import br.ce.nsouzarj.entidades.Locacao;
import br.ce.nsouzarj.entidades.Usuario;
import br.ce.nsouzarj.utils.DataUtils;

import java.util.Arrays;
import java.util.Date;

import static br.ce.nsouzarj.builder.FilmeBuilder.*;
import static br.ce.nsouzarj.builder.UsuarioBuilder.*;

public class LocacaoBuilder {
    private Locacao elemento;
    private LocacaoBuilder(){}

    public static LocacaoBuilder umLocacao() {
        LocacaoBuilder builder = new LocacaoBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(LocacaoBuilder builder) {
        builder.elemento = new Locacao();
        Locacao elemento = builder.elemento;

        elemento.setUsuario(usuarioBuilder().getUsuario());
        elemento.setFilme(Arrays.asList(filmeBuilder().agora()));
        elemento.setDataLocacao(new Date());
        elemento.setDataRetorno(DataUtils.obterDataComDiferencaDias(1));
        elemento.setValor(4.0);
        elemento.setTotalAluguel(0);
    }

    public LocacaoBuilder comUsuario(Usuario param) {
        elemento.setUsuario(param);
        return this;
    }

    public LocacaoBuilder comListaFilme(Filme... params) {
        elemento.setFilme(Arrays.asList(params));
        return this;
    }

    public LocacaoBuilder comDataLocacao(Date param) {
        elemento.setDataLocacao(param);
        return this;
    }

    public LocacaoBuilder comDataRetorno(Date param) {
        elemento.setDataRetorno(param);
        return this;
    }

    public LocacaoBuilder comValor(Double param) {
        elemento.setValor(param);
        return this;
    }

    public LocacaoBuilder comTotalAluguel(int param) {
        elemento.setTotalAluguel(param);
        return this;
    }

    public Locacao agora() {
        return elemento;
    }
}