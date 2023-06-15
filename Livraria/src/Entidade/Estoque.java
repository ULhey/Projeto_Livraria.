package Entidade;

public class Estoque {
	private int cod;
	private Edicao edicao;
	private int quantidade;
	private int codEdicao;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public Edicao getEdicao() {
		return edicao;
	}
	public void setEdicao(Edicao edicao) {
		this.edicao = edicao;
		this.codEdicao = edicao.getCod();
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public int getCodEdicao() {
		return codEdicao;
	}
}