package Controler;

import java.sql.SQLException;

import DAO.GeneroDao;
import Entidade.Genero;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleGenero {
	ObservableList<Genero> generos = FXCollections.observableArrayList();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty descricao = new SimpleStringProperty("");

	public void daEntidade(Genero g) {
		cod.set(g.getCod());
		descricao.set(g.getDescricao());
	}

	public ObservableList<Genero> getLista() {
		return generos;
	}

	public ControleGenero() throws ClassNotFoundException, SQLException {
		GeneroDao gdao = new GeneroDao();
		generos.addAll(gdao.buscaAllGenero());
		gdao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		GeneroDao gdao = new GeneroDao();

		Genero g = new Genero();
		g.setCod(cod.get());
		g.setDescricao(descricao.get());

		gdao.inserirGenero(g);
		generos.add(g);
		gdao.desconectar();
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		GeneroDao gdao = new GeneroDao();
		for (Genero g : generos) {
			if (g.getCod() == cod.get()) {
				Genero gg = gdao.buscaGenero(g);
				descricao.set(gg.getDescricao());
				break;
			}
		}
		gdao.desconectar();
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		GeneroDao gdao = new GeneroDao();
		for (Genero g : generos) {
			if (g.getCod() == cod.get()) {
				g.setDescricao(descricao.get());
				gdao.atualizarGenero(g);
				break;
			}
		}
		gdao.desconectar();
	}

	public void remove(Genero g) throws ClassNotFoundException, SQLException {
		GeneroDao gdao = new GeneroDao();
		gdao.excluirGenero(g.getCod());
		generos.remove(g.getCod());
		gdao.desconectar();
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty descricaoProperty() {
		return descricao;
	}
}
