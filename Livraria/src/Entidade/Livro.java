package Entidade;

public class Livro {
	private int cod;
	private String titulo;
	private Genero genero;
	private Autor autor;
	private int codGenero;
	private int codAutor;
	
	public int getCod() {
		return cod;
	}
	public void setCod(int cod) {
		this.cod = cod;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
		this.codGenero = genero.getCod();
	}
	public Autor getAutor() {
		return autor;
	}
	public void setAutor(Autor autor) {
		this.autor = autor;
		this.codAutor = autor.getCod();
	}
	public int getCodAutor() {
		return codAutor;
	}
	public int getCodGenero() {
		return codGenero;
	}
}