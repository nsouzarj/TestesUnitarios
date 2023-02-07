package br.ce.nsouzarj.dao;

import br.ce.nsouzarj.entidades.Locacao;

import java.util.List;

public interface LocacaoDao {
    public void salvar (Locacao locacao);

    public List<Locacao> obterLocacoesPendentes ();
}
