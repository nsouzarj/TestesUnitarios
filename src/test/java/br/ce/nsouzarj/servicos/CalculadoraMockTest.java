package br.ce.nsouzarj.servicos;

import org.junit.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {
    @Test
    public void teste(){
        Calculadora calculadora = Mockito.mock(Calculadora.class);
        Mockito.when(calculadora.somar(Mockito.eq(1),Mockito.anyInt())).thenReturn(6);

        System.out.println(calculadora.somar(1,2000));


    }
}
