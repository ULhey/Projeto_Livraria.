package Controler;

import java.sql.SQLException;

import DAO.EdicaoDao;
import DAO.EditoraDao;
import DAO.LivroDao;
import Entidade.Edicao;
import Entidade.Editora;
import Entidade.Livro;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleEdicao {
	ObservableList<Edicao> edicoes = FXCollections.observableArrayList();
	ObservableList<Editora> editoras = FXCollections.observableArrayList();
	ObservableList<Livro> livros = FXCollections.observableArrayList();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private StringProperty tipoEd = new SimpleStringProperty("");
	private DoubleProperty valor = new SimpleDoubleProperty(0);
	private IntegerProperty numpag = new SimpleIntegerProperty(0);
	private IntegerProperty codEditora = new SimpleIntegerProperty(0);
	private IntegerProperty codLivro = new SimpleIntegerProperty(0);
	
	public void daEntidade(Edicao e) {
		cod.set(e.getCod());
		tipoEd.set(e.getTipoed());
		valor.set(e.getValor());
		numpag.set(e.getNumpag());
		codEditora.set(e.getEditora().getCod());
		codLivro.set(e.getLivro().getCod());
	}

	public ObservableList<Edicao> getLista() {
		return edicoes;
	}

	public ControleEdicao() throws ClassNotFoundException, SQLException {
		EdicaoDao edDao = new EdicaoDao();
		EditoraDao edao = new EditoraDao();
		LivroDao ldao = new LivroDao();
		editoras.addAll(edao.buscaAllEditora());
		edicoes.addAll(edDao.buscaAllEdicao());
		livros.addAll(ldao.buscaAllLivro());
		edDao.desconectar();
		edao.desconectar();
		ldao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty() && !editoras.isEmpty()) {
			EdicaoDao edDao = new EdicaoDao();
			
			Edicao e = new Edicao();
			
			e.setCod(cod.get());
			e.setTipoed(tipoEd.get());
			e.setValor(valor.get());
			e.setNumpag(numpag.get());
			
			for (Editora ed : editoras) {
				if (codEditora.get() == ed.getCod()) {
					e.setEditora(ed);
				}
			}
			
			for (Livro l : livros) {
				if (codEditora.get() == l.getCod()) {
					e.setLivro(l);
				}
			}
			
			edDao.inserirEdicao(e);
			edicoes.add(e);
			edDao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty() && !editoras.isEmpty()) {
			for (Edicao e : edicoes) {
				if (cod.get() == e.getCod()) {
					EdicaoDao edDao = new EdicaoDao();
					tipoEd.set(edDao.buscaEdicao(e).getTipoed());
					valor.set(edDao.buscaEdicao(e).getValor());
					numpag.set(edDao.buscaEdicao(e).getNumpag());
					codEditora.set(edDao.buscaEdicao(e).getEditora().getCod());
					codLivro.set(edDao.buscaEdicao(e).getLivro().getCod());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty() && !editoras.isEmpty()) {
			for (Edicao e : edicoes) {
				if (cod.get() == e.getCod()) {
					EdicaoDao edDao = new EdicaoDao();
					
					e.setTipoed(tipoEd.get());
					e.setValor(valor.get());
					e.setNumpag(numpag.get());
					
					for (Editora ed : editoras) {
						if (codEditora.get() == ed.getCod()) {
							e.setEditora(ed);
						}
					}
					
					for (Livro l : livros) {
						if (codLivro.get() == l.getCod()) {
							e.setLivro(l);
						}
					}
					
					edDao.atualizarEdicao(e);
					edDao.desconectar();
				}
			}
		}
	}

	public void remove(Edicao e) throws ClassNotFoundException, SQLException {
		if (!livros.isEmpty() && !editoras.isEmpty()) {
			EdicaoDao edao = new EdicaoDao();
			edao.excluirEdicao(e.getCod());
			edicoes.remove(e);
			edao.desconectar();
		}
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public StringProperty tipoEdProperty() {
		return tipoEd;
	}

	public DoubleProperty valorProperty() {
		return valor;
	}

	public IntegerProperty numPagsProperty() {
		return numpag;
	}

	public IntegerProperty codEditoraProperty() {
		return codEditora;
	}

	public IntegerProperty codLivroProperty() {
		return codLivro;
	}
}
