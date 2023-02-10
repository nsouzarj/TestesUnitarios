package br.ce.nsouzarj.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

public class CalculadoraMockTest {
    @Mock
    private Calculadora calMock;
    @Spy
    private Calculadora calSpy;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void devoMostrarDiferecasEntreMocks(){
        Mockito.when(calMock.somar(3,5)).thenReturn(7);
        //Mockito.when(calSpy.somar(1,5)).thenReturn(7);
        Mockito.doReturn(3).when(calSpy).somar(1,2);
        System.out.println("Mock "+calMock.somar(3,5));
        System.out.println("Spy "+calSpy.somar(3,5));
        calSpy.imprime();



        //        Mockito.when(calculadora2.somar(3,5)).thenReturn(8);
//        Mockito.doReturn(5).when(calculadora2).somar(1,2);
//        Mockito.doNothing().when(calculadora2).imprime();
//
//        System.out.println("Soma1 "+calculadora1.somar(3,4));
//        System.out.println("Soma2 "+calculadora2.somar(3,4));
//
//        System.out.println("Calculadora1");
//        calculadora1.imprime();
//        System.out.println("Calculadora2");
//        calculadora2.imprime();
    }

    @Test
    public void teste(){
        Calculadora calculadora = Mockito.mock(Calculadora.class);
        ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
        Mockito.when(calculadora.somar(captor.capture(),captor.capture())).thenReturn(6);
        Assert.assertEquals(6,calculadora.somar(1,1000));
        System.out.println(captor.getAllValues());

        System.out.println("Calculadora1");
        calMock.imprime();
        System.out.println("Calculadora2");
        calSpy.imprime();



    }
}
