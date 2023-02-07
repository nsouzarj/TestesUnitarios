package br.ce.nsouzarj.servicos;

import br.ce.nsouzarj.builder.FilmeBuilder;
import br.ce.nsouzarj.dao.LocacaoDao;
import br.ce.nsouzarj.entidades.Filme;
import br.ce.nsouzarj.entidades.Locacao;
import br.ce.nsouzarj.entidades.Usuario;
import br.ce.nsouzarj.exceptions.FilmeSemEstoqueException;
import br.ce.nsouzarj.exceptions.LocadoraException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static br.ce.nsouzarj.builder.FilmeBuilder.*;
import static br.ce.nsouzarj.builder.UsuarioBuilder.usuarioBuilder;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class CalculoValorLocacaoTest {

    private LocacaoService locacaoService;
    @Parameterized.Parameter
    public List<Filme> filmes;
    @Parameterized.Parameter(value = 1)
    public Double valorLocacao;

    @Parameterized.Parameter(value = 2)
    public String cenario;


    @Before
    public void inicio(){
        locacaoService=new LocacaoService();
        LocacaoDao locacaoDao = Mockito.mock(LocacaoDao.class);
        locacaoService.setLocacaoDao(locacaoDao);
        SPCService spcService = Mockito.mock(SPCService.class);
        locacaoService.setSpcService(spcService);
    }
    //Cenario
    private Usuario usuario = usuarioBuilder().getUsuario();
    private static Filme filme1= filmeBuilder().agora();
    private static Filme filme2= filmeBuilder().agora();
    private static Filme filme3= filmeBuilder().agora();
    private static Filme filme4= filmeBuilder().agora();
    private static Filme filme5= filmeBuilder().agora();
    private static Filme filme6= filmeBuilder().agora();
    private static Filme filme7= filmeBuilder().agora();
    @Parameterized.Parameters(name =  "{2}")
    public static Collection<Object[]> getParametros(){
       return Arrays.asList(new Object[][]{
               {Arrays.asList(filme1,filme2),8.0, "2 Filmes Sem Desconto"},
               {Arrays.asList(filme1,filme2,filme3),11.0, "3 Filmes: 25%"},
               {Arrays.asList(filme1,filme2,filme3,filme4), 13.0,"4 Filens: 50%"},
               {Arrays.asList(filme1,filme2,filme3,filme4,filme5), 14.0,"5 Filens: 70%"},
               {Arrays.asList(filme1,filme2,filme3,filme4,filme5, filme6),14.0,"6 Filens: 100%"},
               {Arrays.asList(filme1,filme2,filme3,filme4,filme5, filme6,filme7),18.0,"7 Filmes sem desconto"}

       });
    }

    @Test
    public void deveCalcularValorGenerico() throws FilmeSemEstoqueException, LocadoraException {

        //Cenario
        Usuario usuario = usuarioBuilder().getUsuario();

        //Acao
        Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

        //Verificacao 4+4+3+2+1
        assertThat(locacao.getValor(), is(valorLocacao));

    }

    @Test
    public void imprimaValor(){
        System.out.println(valorLocacao);
    }


}
