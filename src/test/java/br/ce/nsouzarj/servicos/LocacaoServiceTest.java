package br.ce.nsouzarj.servicos;



import java.lang.reflect.ReflectPermission;
import java.util.*;

import br.ce.nsouzarj.exceptions.FilmeSemEstoqueException;
import br.ce.nsouzarj.exceptions.LocadoraException;
import org.hamcrest.CoreMatchers;
import org.junit.*;

import br.ce.nsouzarj.entidades.Filme;
import br.ce.nsouzarj.entidades.Locacao;
import br.ce.nsouzarj.entidades.Usuario;
import br.ce.nsouzarj.utils.DataUtils;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.springframework.test.util.ReflectionTestUtils;


import static br.ce.nsouzarj.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LocacaoServiceTest {
    @Rule
 	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exp = ExpectedException.none();

	private  LocacaoService locacaoService;
	private static int contador=0;
	@Before
	public void antes(){
		System.out.println("Antes do teste");
		locacaoService= new LocacaoService();
		contador++;
		System.out.println("Contador "+contador);

	}

	@After
	public void depois(){
		System.out.println("Depois do teste do teste");
	}

	@BeforeClass
	public static void antesClass(){
		System.out.println("Antes do teste class");
	}

	@AfterClass
	public static void depoisClass(){
		System.out.println("Depois do teste do teste class");
	}

	@Test
	public void deveAlugarFilmeComSuceso() throws Exception {

		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

		//cenario
		ArrayList<Filme> filmeList = new ArrayList<>();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme1 = new Filme("Filme 1", 1, 4.0);
		filmeList.add(filme1);
		Filme filme2 = new Filme("Filme 2", 1, 4.0);
		filmeList.add(filme2);
		Filme filme3 = new Filme("Filme 3", 1, 4.0);
		filmeList.add(filme3);


		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmeList);
		//Uso do Assert.that
		assertThat(locacao.getValor(),is(11.0));
		assertThat(locacao.getValor(),is(CoreMatchers.not(6.0)));
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),is(false));

		//Uso do error conector - verificacao
		error.checkThat(locacao.getValor(), is(equalTo(11.0)) );
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),is(false));

		//verificacao
		Assert.assertEquals(11.0,locacao.getValor(),0.01);
		Assert.assertTrue(locacao.getValor() == 11.0);
		Assert.assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));Assert.assertFalse(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)));

	}
  //Forma elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void  deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {

		//cenario
        ArrayList<Filme> filmes = new ArrayList<>();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 5.0);
		filmes.add(filme);

		//acao
		locacaoService.alugarFilme(usuario, filmes);

	}

	@Test
	public void  testeLocacaoFilmeSemEstoque2(){

        //cenario
		Usuario usuario = new Usuario("Usuario 1");
		ArrayList<Filme> filmes = new ArrayList<>();
		Filme filme = new Filme("Filme 1", 2, 5.0);
		filmes.add(filme);

		//acao
		try {
			locacaoService.alugarFilme(usuario, filmes);
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(),is("Filme sem estoque"));
		}
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void  testeLocacaoFilmeSemEstoque3() throws Exception{

		//cenario
		ArrayList<Filme> filmes = new ArrayList<>();
		Usuario usuario = new Usuario("Usuario 1");
		Filme filme = new Filme("Filme 1", 0, 4.0);
        filmes.add(filme);

		//Acao
		locacaoService.alugarFilme(usuario, filmes);

	}

	@Test
	public void testeUsuarioVazio() throws FilmeSemEstoqueException {

		//cenario
		ArrayList<Filme> filmes = new ArrayList<>();
		LocacaoService locacaoService = new LocacaoService();
		Filme filme = new Filme("Filme doido",1,4.0);
		filmes.add(filme);

		//acao
		try {
			locacaoService.alugarFilme(null, filmes);
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}

	}
    //Forma nova
	@Test
	public void testeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException{

		//Cenario
		Usuario usuario = new Usuario("Usuario 1");
		exp.equals(LocadoraException.class);
		exp.expectMessage("Filme vazio");

		//Acao
		ReflectionTestUtils.setField(locacaoService,"recebe","Ola tudo bem");
		locacaoService.alugarFilme(usuario,null);

	}

	@Test
	public void testeFilmesEmEstoqueSemDescopnto() throws FilmeSemEstoqueException, LocadoraException {

		//Cenario
		Usuario usurio = new Usuario("Nelson 1");
		List<Filme> filmes = Arrays.asList(new Filme("Filme",2,11.0));

		//Acao
		Locacao locacao= locacaoService.alugarFilme(usurio,filmes);
		assertThat(locacao.getValor(),is(11.0));

	}

//	@Test
//	public void devePagar75pcNoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
//
//		//Cenario
//		Usuario usurio = new Usuario("Nelson 1");
//		Filme filme1= new Filme("Filme1",2,4.0);
//		Filme filme2= new Filme("Filme2",2,4.0);
//		Filme filme3= new Filme("Filme3",2,4.0);
//		List<Filme> filmes = Arrays.asList(filme1,filme2,filme3);
//
//		//Acao
//		Locacao locacao= locacaoService.alugarFilme(usurio,filmes);
//
//		//Verificacao 4+4+3
//		assertThat(locacao.getValor(),is(11.0));
//
//	}
//	@Test
//	public void devePagar50pcNoQuatroFilme() throws FilmeSemEstoqueException, LocadoraException {
//
//		//Cenario
//		Usuario usurio = new Usuario("Nelson 1");
//		Filme filme1= new Filme("Filme1",2,4.0);
//		Filme filme2= new Filme("Filme2",2,4.0);
//		Filme filme3= new Filme("Filme3",2,4.0);
//		Filme filme4= new Filme("Filme3",2,4.0);
//		List<Filme> filmes = Arrays.asList(filme1,filme2,filme3,filme4);
//
//		//Acao
//		Locacao locacao= locacaoService.alugarFilme(usurio,filmes);
//
//		//Verificacao 4+4+3+2
//		assertThat(locacao.getValor(),is(13.0));
//
//	}
//	@Test
//	public void devePagar25pcNoQuintoFilme() throws FilmeSemEstoqueException, LocadoraException {
//
//		//Cenario
//		Usuario usurio = new Usuario("Nelson 1");
//		Filme filme1= new Filme("Filme1",2,4.0);
//		Filme filme2= new Filme("Filme2",2,4.0);
//		Filme filme3= new Filme("Filme3",2,4.0);
//		Filme filme4= new Filme("Filme3",2,4.0);
//		Filme filme5= new Filme("Filme3",2,4.0);
//		List<Filme> filmes = Arrays.asList(filme1,filme2,filme3,filme4,filme5);
//
//		//Acao
//		Locacao locacao= locacaoService.alugarFilme(usurio,filmes);
//
//		//Verificacao 4+4+3+2+1
//		assertThat(locacao.getValor(),is(14.0));
//
//	}
//
//	@Test
//	public void devePagar0pcNoSextoFilme() throws FilmeSemEstoqueException, LocadoraException {
//
//		//Cenario
//		Usuario usurio = new Usuario("Nelson 1");
//		Filme filme1= new Filme("Filme1",2,4.0);
//		Filme filme2= new Filme("Filme2",2,4.0);
//		Filme filme3= new Filme("Filme3",2,4.0);
//		Filme filme4= new Filme("Filme3",2,4.0);
//		Filme filme5= new Filme("Filme3",2,4.0);
//		Filme filme6= new Filme("Filme3",2,4.0);
//		List<Filme> filmes = Arrays.asList(filme1,filme2,filme3,filme4,filme5,filme6);
//
//		//Acao
//		Locacao locacao= locacaoService.alugarFilme(usurio,filmes);
//
//		//Verificacao 4+4+3+2+1
//		assertThat(locacao.getValor(),is(14.0));
//
//	}

	@Test
	public void naoDeveAlugarFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(),Calendar.SATURDAY));

		//Cenario
		Usuario usuario = new Usuario("Nelson");
		List<Filme> filmes = Arrays.asList(new Filme("Filme1", 2, 4.0));


		//Acao
		Locacao locacao= locacaoService.alugarFilme(usuario,filmes);

		//Verificacao
		boolean ehSegunda=DataUtils.verificarDiaSemana(locacao.getDataRetorno(),Calendar.MONDAY);
        Assert.assertTrue(ehSegunda);


	}
}
