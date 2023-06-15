package Controler;

import java.sql.SQLException;

import DAO.AutorDao;
import DAO.GeneroDao;
import DAO.LivroDao;
import Entidade.Autor;
import Entidade.Genero;
import Entidade.Livro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleLivro {
	ObservableList<Livro> livros = FXCollections.observableArrayList();
	ObservableList<Autor> autores = FXCollections.observableArrayList();
	ObservableList<Genero> generos = FXCollections.observableArrayList();
	
	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty titulo = new SimpleStringProperty("");
	private IntegerProperty codGenero = new SimpleIntegerProperty(0);
	private IntegerProperty codAutor = new SimpleIntegerProperty(0);
	
	public void daEntidade(Livro l) {
		cod.set(l.getCod());
		titulo.set(l.getTitulo());
		codGenero.set(l.getGenero().getCod());
		codAutor.set(l.getAutor().getCod());
	}

	public ObservableList<Livro> getLista() {
		return livros;
	}
	
	public ControleLivro() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		GeneroDao gdao = new GeneroDao();
		LivroDao ldao = new LivroDao();
		autores.addAll(adao.buscaAllAutor());
		generos.addAll(gdao.buscaAllGenero());
		livros.addAll(ldao.buscaAllLivro());
		adao.desconectar();
		gdao.desconectar();
		ldao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {		
		if (!generos.isEmpty() && !autores.isEmpty()) {
			LivroDao ldao = new LivroDao();

			Livro l = new Livro();
			l.setCod(cod.get());
			l.setTitulo(titulo.get());
			
			for (Genero g : generos) {
				if (codGenero.get() == g.getCod()) {
					l.setGenero(g);
					break;
				}
			}

			for (Autor a : autores) {
				if (codAutor.get() == a.getCod()) {
					l.setAutor(a);
					break;
				}
			}
			
			ldao.inserirLivro(l);
			livros.add(l);
			ldao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!generos.isEmpty() && !autores.isEmpty()) {
			for (Livro l : livros) {
				if (l.getCod() == cod.get()) {
					LivroDao ldao = new LivroDao();
					titulo.set(ldao.buscaLivro(l).getTitulo());
					codGenero.set(ldao.buscaLivro(l).getGenero().getCod());
					codAutor.set(ldao.buscaLivro(l).getAutor().getCod());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!generos.isEmpty() && !autores.isEmpty()) {
			for (Livro l : livros) {
				if (l.getCod() == cod.get()) {
					LivroDao ldao = new LivroDao();
					
					l.setTitulo(titulo.get());
					
					for (Genero g : generos) {
						if (codGenero.get() == g.getCod()) {
							l.setGenero(g);
							break;
						}
					}

					for (Autor a : autores) {
						if (codAutor.get() == a.getCod()) {
							l.setAutor(a);
							break;
						}
					}
					
					ldao.atualizarLivro(l);
					ldao.desconectar();
					break;
				}
			}
		}
	}

	public void remove(Livro l) throws ClassNotFoundException, SQLException {
		if (!generos.isEmpty() && !autores.isEmpty()) {
			LivroDao ldao = new LivroDao();
			ldao.excluirLivro(cod.get());
			livros.remove(l);
			ldao.desconectar();
		}
	}
	
	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty tituloProperty() {
		return titulo;
	}
	
	public IntegerProperty codGeneroProperty() {
		return codGenero;
	}
	
	public IntegerProperty codAutorProperty() {
		return codAutor;
	}
}