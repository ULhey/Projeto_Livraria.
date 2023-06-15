package Entidade;

public class Edicao {
	private int cod;
	private String tipoed;
	private double valor;
	private int numpag;
	private Editora editora;
	private Livro livro;
	private int codEditora;
	private int codLivro;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getTipoed() {
		return tipoed;
	}
	public void setTipoed(String tipoed) {
		this.tipoed = tipoed;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public int getNumpag() {
		return numpag;
	}
	public void setNumpag(int numpag) {
		this.numpag = numpag;
	}
	public Editora getEditora() {
		return editora;
	}
	public void setEditora(Editora editora) {
		this.editora = editora;
		this.codEditora = editora.getCod();
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
		this.codLivro = livro.getCod();
	}
	public int getCodEditora() {
		return codEditora;
	}
	public int getCodLivro() {
		return codLivro;
	}
}