package br.ce.nsouzarj.servicos;

import br.ce.nsouzarj.entidades.Usuario;

public interface EmailService {
    void notificarAtraso(Usuario usuario);
}
