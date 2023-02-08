package br.ce.nsouzarj.servicos;

import br.ce.nsouzarj.entidades.Usuario;

public interface IEmailService {
    void notificarAtraso(Usuario usuario);
}
