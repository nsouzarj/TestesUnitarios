package br.ce.nsouzarj.servicos;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//Ordem dos teste
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdermTest {
    public static  int contador=0;
    @Test
    public  void inicia(){
        contador=1;

    }

    @Test
    public void verifica(){
        Assert.assertEquals(1,contador);

    }

}
