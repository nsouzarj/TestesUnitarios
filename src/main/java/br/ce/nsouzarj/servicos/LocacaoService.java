package br.ce.nsouzarj.servicos;

import static br.ce.nsouzarj.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.nsouzarj.dao.LocacaoDao;
import br.ce.nsouzarj.entidades.Filme;
import br.ce.nsouzarj.entidades.Locacao;
import br.ce.nsouzarj.entidades.Usuario;
import br.ce.nsouzarj.exceptions.FilmeSemEstoqueException;
import br.ce.nsouzarj.exceptions.LocadoraException;
import br.ce.nsouzarj.utils.DataUtils;

public class LocacaoService {
	private LocacaoDao locacaoDao;
	private ISPCService ISPCService;
	private IEmailService IEmailService;
	private String recebe;

	private int estoqueFilme=0;


	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if(usuario==null){
			throw new LocadoraException("Usuario vazio.");
		}
		if(filmes==null ){
			throw new LocadoraException("Filme vazio.");
		}
		if(filmes.isEmpty()){
			throw  new FilmeSemEstoqueException("Aluguel sem filmes.");
		}


		CalcularFilmes(filmes);
		boolean nega;
		try {
			 nega=ISPCService.possuiNegativacao(usuario);
		} catch (Exception e) {
			throw new LocadoraException("Problemas com SPC, tente novamente");
		}
		if(nega){
			throw new LocadoraException("Usuario negativado");
		}

		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(CalcularFilmes(filmes));
		locacao.setTotalAluguel(estoqueFilme);
		
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)){
			dataEntrega = adicionarDias(dataEntrega, 1);
		}
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
		locacaoDao.salvar(locacao);

	    return locacao;
	}

	private Double CalcularFilmes (List<Filme> filmes) throws FilmeSemEstoqueException {
		System.out.println("Entrou no calculo.");
		Double  valorTtotal=0.0;
		for(int i = 0; i < filmes.size(); i++){
			Filme filme= filmes.get(i);
			if(filme.getEstoque()==0){
				throw  new FilmeSemEstoqueException("Filme sem estoque");
			}
			Double valorFilme = filme.getPrecoLocacao();

			switch (i){
				case 2: valorFilme=valorFilme*0.75;
				break;
				case 3: valorFilme=valorFilme*0.5;
				break;
				case 4: valorFilme=valorFilme*0.25;
				break;
				case 5: valorFilme=0.0;
				break;
			}
			valorTtotal+=valorFilme;
		}
		return  valorTtotal;
	}

	public void notificarAtrasos(){
		List<Locacao> locacaos = locacaoDao.obterLocacoesPendentes();
		for(Locacao locacao: locacaos){
			if(locacao.getDataRetorno().before(new Date())) {
				IEmailService.notificarAtraso(locacao.getUsuario());
			}
		}
	}

	public void prorrogaLocacao(Locacao locacao, int dias){
		Locacao novaLocacao = new Locacao();
		novaLocacao.setUsuario(locacao.getUsuario());
        novaLocacao.setFilme(locacao.getFilme());
		novaLocacao.setDataLocacao(new Date());
		novaLocacao.setDataRetorno(DataUtils.obterDataComDiferencaDias(dias));
		novaLocacao.setValor(locacao.getValor()*dias);
		locacaoDao.salvar(novaLocacao);
	}


//
//	public void setLocacaoDao(LocacaoDao locacaoDao){
//		this.locacaoDao=locacaoDao;
//	}
//
//	public void setSpcService (ISPCService spc) {
//		ISPCService = spc;
//	}
//
//	public void setEmailService (IEmailService IEmailService) {
//		this.IEmailService = IEmailService;
//	}



}