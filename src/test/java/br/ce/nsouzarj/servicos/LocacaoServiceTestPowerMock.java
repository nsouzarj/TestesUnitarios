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
import org.junit.runner.RunWith;
import org.mockito.*;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
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

@RunWith(PowerMockRunner.class)
@PrepareForTest({LocacaoService.class})
public class LocacaoServiceTestPowerMock {
	@InjectMocks
	private LocacaoService locacaoService;
    @Rule
 	private ErrorCollector error = new ErrorCollector();
	@Rule
	public ExpectedException exception = ExpectedException.none();
	@Mock
	private ISPCService ISPCService;
	@Mock
	private LocacaoDao locacaoDao;


	private static int contador=0;
	@Before
	public void antes(){
		MockitoAnnotations.initMocks(this);
		locacaoService= PowerMockito.spy(locacaoService);


	}



	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws Exception {
		Usuario usuario = usuarioBuilder().agora();
		List<Filme> filmes = Arrays.asList(filmeBuilder().agora());

		PowerMockito.whenNew(Date.class).withNoArguments().thenReturn(DataUtils.obterData(29, 4, 2017));
		//acao
		Locacao retorno = locacaoService.alugarFilme(usuario, filmes);

		//verificacao
		assertThat(retorno.getDataRetorno(), caiNumaSegunda());
		PowerMockito.verifyNew(Date.class, Mockito.times(2)).withNoArguments();

	}



//	public static void main (String[] args) {
//		new BuilderMaster().gerarCodigoClasse(Usuario.class);
//	}


    @Test
	public void deveAlugarFilmeSemCalculo() throws Exception {

		//Cenario
		Usuario usuario = usuarioBuilder().agora();
		List<Filme> filmeList = Arrays.asList(filmeBuilder().agora());
		PowerMockito.doReturn(4.0).when(locacaoService , "CalcularFilmes",filmeList);

		//Acao
		Locacao locacao = locacaoService.alugarFilme(usuario,filmeList);

		//Verificacao
		Assert.assertThat(locacao.getValor(), is(4.0));
		PowerMockito.verifyPrivate(locacaoService,times(2)).invoke("CalcularFilmes",filmeList);

	 }

	 @Test
	 public void deveAlugarFilmeSemCalculoDireto() throws Exception {
		//Cenario
		List<Filme> filmeList = Arrays.asList(filmeBuilder().agora());

        //Acao
		Double valor =(Double) Whitebox.invokeMethod(locacaoService,"CalcularFilmes", filmeList);

		//Verificacao
		Assert.assertThat(valor, is(4.0));


	 }


}
