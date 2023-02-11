package br.ce.nsouzarj.entidades;

import java.util.Objects;

public class Filme {

	private String nome;
	private Integer estoque;
	private Double precoLocacao;  
	
	public Filme() {}
	
	public Filme(String nome, Integer estoque, Double precoLocacao) {
		this.nome = nome;
		this.estoque = estoque;
		this.precoLocacao = precoLocacao;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getEstoque() {
		return estoque;
	}
	public void setEstoque(Integer estoque) {
		this.estoque = estoque;
	}
	public Double getPrecoLocacao() {
		return precoLocacao;
	}
	public void setPrecoLocacao(Double precoLocacao) {
		this.precoLocacao = precoLocacao;
	}

	@Override
	public boolean equals (Object o) {
		if (this == o) return true;
		if (!(o instanceof Filme)) return false;

		Filme filme = (Filme) o;

		if (!Objects.equals(nome, filme.nome)) return false;
		if (!Objects.equals(estoque, filme.estoque)) return false;
		return Objects.equals(precoLocacao, filme.precoLocacao);
	}

	@Override
	public int hashCode () {
		int result = nome != null ? nome.hashCode() : 0;
		result = 31 * result + (estoque != null ? estoque.hashCode() : 0);
		result = 31 * result + (precoLocacao != null ? precoLocacao.hashCode() : 0);
		return result;
	}
}