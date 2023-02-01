package br.ce.nsouzarj.exceptions;

public class FilmeSemEstoqueException extends  Exception{

    private static final long serialVersionUID = 5741915366007211234L;

    public FilmeSemEstoqueException (String message) {
        super(message);
    }
}
