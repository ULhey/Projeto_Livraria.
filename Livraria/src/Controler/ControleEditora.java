package Controler;

import java.sql.SQLException;
import java.util.ArrayList;

import DAO.EditoraDao;
import Entidade.Autor;
import Entidade.Editora;
//import DAO.EditoraDao;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleEditora {
	ObservableList<Editora> editoras = FXCollections.observableArrayList();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty nome = new SimpleStringProperty("");
	private StringProperty site = new SimpleStringProperty("");

	public void daEntidade(Editora e) {
		cod.set(e.getCod());
		nome.set(e.getNome());
		site.set(e.getSite());
	}

	public ObservableList<Editora> getLista() {
		return editoras;
	}
	
	public ControleEditora() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();
		editoras.addAll(edao.buscaAllEditora());
		edao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();

		Editora e = new Editora();
		e.setCod(cod.get());
		e.setNome(nome.get());
		e.setSite(site.get());

		edao.inserirEditora(e);
		editoras.add(e);
		edao.desconectar();
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();

		for (Editora e : editoras) {
			if (e.getCod() == cod.get()) {
				Editora ee = edao.buscaEditora(e);
				nome.set(ee.getNome());
				site.set(ee.getSite());

				break;
			}
		}
		edao.desconectar();
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();
		for (Editora e : editoras) {
			if (e.getCod() == cod.get()) {
				e.setNome(nome.get());
				e.setSite(site.get());
				edao.atualizarEditora(e);
				break;
			}
		}
		edao.desconectar();
	}

	public void remove(Editora e) throws ClassNotFoundException, SQLException {
		EditoraDao edao = new EditoraDao();
		edao.excluirEditora(e.getCod());
		editoras.remove(e);
		edao.desconectar();
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty nomeProperty() {
		return nome;
	}

	public StringProperty siteProperty() {
		return site;
	}
}