package br.ce.nsouzarj.servicos;

import br.ce.nsouzarj.exceptions.NaoPodeDividirPorZeroException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {
    private Calculadora calculadora;
    @Before
    public void init(){
        calculadora=new Calculadora();
    }

    @Test
    public void deveSomarDoisValores(){
        //Cenario
        int a=5;
        int b=3;


        //Acao
        int resultado=calculadora.somar(a,b);

        //Verificacao
        Assert.assertEquals(8,resultado);
        Assert.assertNotEquals(7,resultado);
    }

    @Test
    public void deveSubtrairDoisValores(){

        //Cenario
        int a=5;
        int b=3;


        //acao
        int resultado=calculadora.subtrair(a,b);

        //Verificacao
        Assert.assertEquals(2,resultado);
        Assert.assertNotEquals(3,resultado);

    }

    @Test
    public void deveDividirDoisValores(){
        //Cenario
        Calculadora calc= new Calculadora(5,3);

        //acao
        int resultado=calculadora.getResultDividir();

        //Verificacao
        Assert.assertEquals(0,resultado);
        Assert.assertNotEquals(3,resultado);

    }

    @Test(expected = NaoPodeDividirPorZeroException.class)
    public void deveLancarExecaoDividirPorZero() throws NaoPodeDividirPorZeroException {
        Calculadora calculadora = new Calculadora();
        calculadora.dividr(10,0);
    }
}
