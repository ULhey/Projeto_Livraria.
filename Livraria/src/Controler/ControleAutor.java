package Controler;

import java.sql.SQLException;

import DAO.AutorDao;
import Entidade.Autor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleAutor {
	ObservableList<Autor> autores = FXCollections.observableArrayList();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");;

	public void daEntidade(Autor a) {
		cod.set(a.getCod());
		nome.set(a.getNome());
	}

	public ObservableList<Autor> getLista() {
		return autores;
	}

	public ControleAutor() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		autores.addAll(adao.buscaAllAutor());
		adao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		Autor a = new Autor();
		a.setCod(cod.get());
		a.setNome(nome.get());

		adao.inserirAutor(a);
		autores.add(a);
		adao.desconectar();
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		for (Autor a : autores) {
			if (a.getCod() == cod.get()) {
				Autor aa = adao.buscaAutor(a);
				nome.set(aa.getNome());
				break;
			}
		}
		adao.desconectar();
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		for (Autor a : autores) {
			if (a.getCod() == cod.get()) {
				a.setNome(nome.get());
				adao.atualizarAutor(a);
				break;
			}
		}
		adao.desconectar();
	}

	public void remove(Autor a) throws ClassNotFoundException, SQLException {
		AutorDao adao = new AutorDao();
		adao.excluirAutor(a.getCod());
		autores.remove(a);
		adao.desconectar();
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty nomeProperty() {
		return nome;
	}
}