package br.ce.nsouzarj.builder;

import br.ce.nsouzarj.entidades.Usuario;

public class UsuarioBuilder {
    private Usuario usuario;
    private UsuarioBuilder(){

    }

    public static UsuarioBuilder usuarioBuilder(){

        UsuarioBuilder usuarioBuilder = new UsuarioBuilder();
        usuarioBuilder.usuario= new Usuario();
        usuarioBuilder.usuario.setNome("Nelson Seixas de Souza");
        return usuarioBuilder;

    }


    public Usuario getUsuario(){
        return usuario;
    }

    public UsuarioBuilder comNome(String nome){
        usuario.setNome(nome);
        return this;
    }

}
