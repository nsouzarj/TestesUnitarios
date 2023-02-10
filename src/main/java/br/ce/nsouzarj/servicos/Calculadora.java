package br.ce.nsouzarj.servicos;

import br.ce.nsouzarj.exceptions.NaoPodeDividirPorZeroException;

import java.util.concurrent.CancellationException;

public class Calculadora {
    private int resultSoma;
    private int resultSubtrair;

    private int resultDividir;

    private int resultMultiplicar;

    public Calculadora () {
    }

    public int getResultSoma () {
        return resultSoma;
    }

    public void setResultSoma (int resultSoma) {
        this.resultSoma = resultSoma;
    }

    public int getResultSubtrair () {
        return resultSubtrair;
    }

    public void setResultSubtrair (int resultSubtrair) {
        this.resultSubtrair = resultSubtrair;
    }

    public int getResultDividir () {
        return resultDividir;
    }

    public void setResultDividir (int resultDividir) {
        this.resultDividir = resultDividir;
    }

    public int getResultMultiplicar () {
        return resultMultiplicar;
    }

    public void setResultMultiplicar (int resultMultiplicar) {
        this.resultMultiplicar = resultMultiplicar;
    }

    public Calculadora (int a, int b) {
       setResultSoma(a+b);
       setResultSubtrair(a-b);
       try {
           setResultDividir(a / b);
       }catch (CancellationException e){
           e.getMessage();
       }

       setResultMultiplicar(a*b);

    }



    public  int somar(int a, int b){
        System.out.println("Estou executando metodo somar");
        return a+b;
    }

    public int subtrair (int a, int b) {
        return a - b;
    }

    public int dividr (int a, int b) throws NaoPodeDividirPorZeroException {
        if(b==0){
            throw new NaoPodeDividirPorZeroException();
        }
        return a/b;
    }

    public void imprime(){
          System.out.println("PAssei aqui");
    }
}
