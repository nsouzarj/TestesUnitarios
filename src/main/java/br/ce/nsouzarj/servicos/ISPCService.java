package br.ce.nsouzarj.servicos;

import br.ce.nsouzarj.entidades.Usuario;

public interface ISPCService {
  boolean possuiNegativacao(Usuario usuario) throws Exception;
}
