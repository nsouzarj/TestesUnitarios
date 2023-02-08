package br.ce.nsouzarj.builder;

import br.ce.nsouzarj.entidades.Usuario;

public class UsuarioBuilder {
    private Usuario usuario;
    private UsuarioBuilder(){}

    public static UsuarioBuilder usuarioBuilder() {
        UsuarioBuilder builder = new UsuarioBuilder();
        inicializarDadosPadroes(builder);
        return builder;
    }

    public static void inicializarDadosPadroes(UsuarioBuilder builder) {
        builder.usuario = new Usuario();
        Usuario elemento = builder.usuario;


        elemento.setNome("");
    }

    public UsuarioBuilder comNome(String param) {
        usuario.setNome(param);
        return this;
    }

    public Usuario agora() {
        return usuario;
    }

}
