package br.ce.nsouzarj.servicos;


import br.ce.nsouzarj.dao.LocacaoDao;
import br.ce.nsouzarj.entidades.Filme;
import br.ce.nsouzarj.entidades.Locacao;
import br.ce.nsouzarj.entidades.Usuario;
import br.ce.nsouzarj.exceptions.FilmeSemEstoqueException;
import br.ce.nsouzarj.exceptions.LocadoraException;
import br.ce.nsouzarj.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.*;
import static br.ce.nsouzarj.builder.FilmeBuilder.filmeBuilder;
import static br.ce.nsouzarj.builder.LocacaoBuilder.umaLocacao;
import static br.ce.nsouzarj.builder.UsuarioBuilder.usuarioBuilder;
import static br.ce.nsouzarj.matchers.MatchersProprios.*;
import static br.ce.nsouzarj.utils.DataUtils.isMesmaData;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class LocacaoServiceTest {
    @Rule
 	public ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	private LocacaoService locacaoService;
	private ISPCService ISPCService;
	private LocacaoDao locacaoDao;
	private IEmailService IEmailService;

	private static int contador=0;
	@Before
	public void antes(){
	    locacaoService= new LocacaoService();
		locacaoDao = mock(LocacaoDao.class);
		locacaoService.setLocacaoDao(locacaoDao);
		ISPCService = mock(ISPCService.class);
		locacaoService.setSpcService(ISPCService);
		IEmailService =mock(IEmailService.class);
		locacaoService.setEmailService(IEmailService);


	}

	@Test
	public void deveAlugarFilmeComSuceso() throws Exception {

		Calendar calendar = Calendar.getInstance();
		calendar.set(2023,1,5);
		Assume.assumeTrue(DataUtils.verificarDiaSemana(calendar.getTime(),Calendar.SUNDAY));

		//cenario
		ArrayList<Filme> filmeList = new ArrayList<>();
		Usuario usuario =  usuarioBuilder().agora();
		Filme filme1 = filmeBuilder().agora();
		filmeList.add(filme1);
		Filme filme2 = filmeBuilder().agora();
		filmeList.add(filme2);
		Filme filme3 = filmeBuilder().agora();
		filmeList.add(filme3);


		//acao
		Locacao locacao = locacaoService.alugarFilme(usuario, filmeList);

		//Uso do Assert.that
		assertThat(locacao.getValor(),is(11.0));
		assertThat(locacao.getValor(),is(CoreMatchers.not(6.0)));
		assertThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
		assertThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),is(true));

		//Uso do error conector - verificacao
		error.checkThat(locacao.getValor(), is(equalTo(11.0)) );
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()),is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)),is(true));

		//verificacao
		Assert.assertEquals(11.0,locacao.getValor(),0.01);
		Assert.assertTrue(locacao.getValor() == 11.0);
		Assert.assertTrue(isMesmaData(locacao.getDataLocacao(), new Date()));Assert.assertFalse(isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(0)));

	}
  //Forma elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void  deveLancarExcecaoAoAlugarFilmeSemEstoque() throws Exception {

		//cenario
        ArrayList<Filme> filmes = new ArrayList<>();
		Usuario usuario = usuarioBuilder().agora();
		Filme filme = filmeBuilder().semEstoque().agora();
		filmes.add(filme);

		//acao
		locacaoService.alugarFilme(usuario, filmes);

	}

	@Test
	public void  testeLocacaoFilmeSemEstoque2(){

        //cenario
		Usuario usuario = usuarioBuilder().agora();
		ArrayList<Filme> filmes = new ArrayList<>();
		Filme filme = filmeBuilder().semEstoque().agora();
		filmes.add(filme);

		//acao
		try {
			locacaoService.alugarFilme(usuario, filmes);
			Assert.fail();
		} catch (Exception e) {
			Assert.assertThat(e.getMessage(),is("Filme sem estoque"));
		}
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void  testeLocacaoFilmeSemEstoque3() throws Exception{

		//cenario
		ArrayList<Filme> filmes = new ArrayList<>();
		Usuario usuario = usuarioBuilder().agora();
		Filme filme = filmeBuilder().semEstoque().agora();
        filmes.add(filme);

		//Acao
		locacaoService.alugarFilme(usuario, filmes);

	}

	@Test
	public void testeUsuarioVazio() throws FilmeSemEstoqueException {

		//cenario
		ArrayList<Filme> filmes = new ArrayList<>();
		LocacaoService locacaoService = new LocacaoService();
		Filme filme = filmeBuilder().agora();
		filmes.add(filme);

		//acao
		try {
			locacaoService.alugarFilme(null, filmes);
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio."));
		}

	}
    //Forma nova
	@Test
	public void testeFilmeVazio() throws FilmeSemEstoqueException, LocadoraException{

		//Cenario
		Usuario usuario = usuarioBuilder().agora();
		exception.equals(LocadoraException.class);
		exception.expectMessage("Filme vazio");

		//Acao
		ReflectionTestUtils.setField(locacaoService,"recebe","Ola tudo bem");
		locacaoService.alugarFilme(usuario,null);

	}

	@Test
	public void testeFilmesEmEstoqueSemDescopnto() throws FilmeSemEstoqueException, LocadoraException {

		//Cenario
		Usuario usuario = usuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme",2,11.0));

		//Acao
		Locacao locacao= locacaoService.alugarFilme(usuario,filmes);
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
//		Usuario usurio = UsuarioBuilder.usuarioBuilder().getUsuario();
//		Filme filme1= filmeBuilder().comNome("Filme1").agora();
//		Filme filme2= filmeBuilder().comNome("Filme2").agora();
//		Filme filme3= filmeBuilder().comNome("Filme4").agora();
//		Filme filme4= filmeBuilder().comNome("Filme4").agora();
//		Filme filme5= filmeBuilder().comNome("Filme5").agora();
//		Filme filme6= filmeBuilder().comNome("Filme6").agora();
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
		Calendar calendar =  Calendar.getInstance();
		//Setei a data na mao
		calendar.set(2023,1,4);

        Assume.assumeTrue(DataUtils.verificarDiaSemana(calendar.getTime(),Calendar.SATURDAY));

		//Cenario
		Usuario usuario = usuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(filmeBuilder().agora());

		//Acao
		Locacao locacao= locacaoService.alugarFilme(usuario,filmes);

		//Verificacao
		boolean ehSegunda=DataUtils.verificarDiaSemana(locacao.getDataRetorno(),Calendar.SATURDAY);
        Assert.assertFalse(ehSegunda);


	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Date date=DataUtils.obterData(6,2,2023);
		Assume.assumeTrue(DataUtils.verificarDiaSemana(date,Calendar.MONDAY));

		//Cenario
		Usuario usuario = usuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(filmeBuilder().agora());


		//Acao
		Locacao locacao= locacaoService.alugarFilme(usuario,filmes);


		//Verificacao
        boolean ehSegunda=DataUtils.verificarDiaSemana(locacao.getDataRetorno(),Calendar.WEDNESDAY);
        Assert.assertTrue(ehSegunda);
		assertThat(locacao.getDataRetorno(), caiEm(Calendar.WEDNESDAY));
		Date data=DataUtils.obterData(6,2,2023);
		locacao.setDataLocacao(data);
		assertThat(locacao.getDataLocacao(), caiNumaSegunda());

	}

	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabadoDesafio() throws FilmeSemEstoqueException, LocadoraException {

		Date date = DataUtils.obterData(5,2,2023);
		Assume.assumeTrue(DataUtils.verificarDiaSemana(date,Calendar.SUNDAY));

		//Cenario
		Usuario usuario = usuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(filmeBuilder().agora());

		//Acao
		//Locacao locacao= locacaoService.alugarFilme(usuario,filmes);

		Filme filme1 = filmeBuilder().comNome("Filme1").agora();
		Filme filme2 = filmeBuilder().comNome("Filme2").agora();
		Filme filme3 = filmeBuilder().comNome("Filme3").agora();

		umaLocacao().comListaFilme(filme1,filme2,filme3);
		umaLocacao().comUsuario(usuario);
		umaLocacao().agora().getDataLocacao();

		//Verificacao
		error.checkThat(umaLocacao().agora().getDataRetorno(), ehHojeComDiferenciaDias(1));
		error.checkThat(umaLocacao().agora().getDataLocacao(), ehHoje());

	}


//	public static void main (String[] args) {
//		new BuilderMaster().gerarCodigoClasse(Usuario.class);
//	}

	@Test
	public void naoDeveAlugarFilmeSPCO() throws FilmeSemEstoqueException {

		//Cenario
		Usuario  usuario1= usuarioBuilder().comNome("Nelson").agora();
		Usuario  usuario2= usuarioBuilder().comNome("Nelson Caloteiro").agora();

		List<Filme> filmes = Arrays.asList(filmeBuilder().agora());
        when(ISPCService.possuiNegativacao(Mockito.any(Usuario.class))).thenReturn(true);
		//exception.expectMessage("Usuario "+usuario1.getNome()+" esta negativado");

		//Acao
		try {
			locacaoService.alugarFilme(usuario1, filmes);
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(),is("Usuario "+usuario1.getNome()+" esta negativado"));
		}
		verify(ISPCService).possuiNegativacao(usuario1);

	}
	@Test
	public void notificaUsuarioComAtrasoDeEntrega(){

		//Cenario
		Usuario usuario1= usuarioBuilder().comNome("Nelson1").agora();
		Usuario usuario2= usuarioBuilder().comNome("Nelson2").agora();
		Usuario usuario3= usuarioBuilder().comNome("Nelson3").agora();

		Locacao locacao1=umaLocacao().comUsuario(usuario1).atrasado().agora();
		Locacao locacao2=umaLocacao().comUsuario(usuario2).agora();
		Locacao locacao3=umaLocacao().comUsuario(usuario3).atrasado().agora();

		List<Locacao> locacaoList =Arrays.asList(locacao1,locacao2,locacao3);

		when(locacaoDao.obterLocacoesPendentes()).thenReturn(locacaoList);

		//Acao
		locacaoService.notificarAtrasos();

		//Verificacao
		verify(IEmailService,Mockito.times(2)).notificarAtraso(Mockito.any(Usuario.class));

		verify(IEmailService).notificarAtraso(usuario1);

		verify(IEmailService,never()).notificarAtraso(usuario2);

		verify(IEmailService,Mockito.atLeastOnce()).notificarAtraso(usuario3);

		verifyNoMoreInteractions(IEmailService);

	}
}
