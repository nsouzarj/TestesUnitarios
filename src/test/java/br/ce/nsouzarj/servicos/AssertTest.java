package br.ce.nsouzarj.servicos;
import br.ce.nsouzarj.entidades.Usuario;
import org.junit.Assert;
import org.junit.Test;
import java.lang.*;

import static br.ce.nsouzarj.builder.UsuarioBuilder.usuarioBuilder;


public class AssertTest {
    @Test
    public void teste(){
        Assert.assertTrue(true);
        Assert.assertFalse(false);
        Assert.assertEquals(1,1);
        Assert.assertEquals(0.51,0.51,0.01);
        Assert.assertEquals(Math.PI,3.14, 0.01 );



        int i = 5;
        Integer x = 5;
        Assert.assertEquals(Integer.valueOf(i),x);
        Assert.assertEquals(i,x.intValue());        Assert.assertEquals("bola","bola");
        Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
        Assert.assertNotEquals("bola","casa");
        Usuario usuario1 = usuarioBuilder().getUsuario();
        Usuario usuario2 = usuarioBuilder().getUsuario();
        Usuario u1 = usuario1;
        Usuario u2 = usuario2;
        Usuario u3 = u2;
        Usuario u4 = null;

        Assert.assertNotNull(u1);
        Assert.assertEquals(u1,u2);
        Assert.assertSame(u3,u2);
        Assert.assertNotSame(u1,u2);
        Assert.assertNull(u4);
        Assert.assertNotNull("Me ferrei",u3);






    }
}
