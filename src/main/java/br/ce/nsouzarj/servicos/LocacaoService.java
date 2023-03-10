package br.ce.nsouzarj.servicos;

import static br.ce.nsouzarj.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.nsouzarj.entidades.Filme;
import br.ce.nsouzarj.entidades.Locacao;
import br.ce.nsouzarj.entidades.Usuario;
import br.ce.nsouzarj.exceptions.FilmeSemEstoqueException;
import br.ce.nsouzarj.exceptions.LocadoraException;

public class LocacaoService {
	private String recebe;
	private double  valorTtotal=0.0;
	private int estoqueFilme=0;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if(usuario==null){
			throw new LocadoraException("Usuario vazio");
		}
		if(filmes==null ){
			throw new LocadoraException("Filme vazio");
		}
		if(filmes.isEmpty()){
			throw  new FilmeSemEstoqueException("Aluguel sem filmes");
		}
		for(Filme f: filmes){
			estoqueFilme=estoqueFilme+f.getEstoque();
			if(estoqueFilme==0) {
				throw  new FilmeSemEstoqueException("Filme sem estoque");
			}
			valorTtotal=valorTtotal+f.getPrecoLocacao();
		}

		for(int i=0; i < filmes.size(); i++){
			Filme filme= filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();

		}


		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());


		locacao.setValor(valorTtotal);
		locacao.setTotalAluguel(estoqueFilme);
		
		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		//TODO adicionar método para salvar
	    return locacao;
	}
}