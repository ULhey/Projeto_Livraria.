package Controler;

import java.sql.SQLException;

import DAO.EdicaoDao;
import DAO.EstoqueDao;
import Entidade.Edicao;
import Entidade.Estoque;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleEstoque {
	ObservableList<Estoque> estoques = FXCollections.observableArrayList();
	ObservableList<Edicao> edicoes = FXCollections.observableArrayList();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private IntegerProperty codEdicao = new SimpleIntegerProperty(0);
	private IntegerProperty quantidade = new SimpleIntegerProperty(0);
	
	public ObservableList<Estoque> getLista() {
		return estoques;
	}

	public void daEntidade(Estoque e) {
		cod.set(e.getCod());
		codEdicao.set(e.getEdicao().getCod());
		quantidade.set(e.getQuantidade());
	}

	public ControleEstoque() throws ClassNotFoundException, SQLException {
		EstoqueDao eedao = new EstoqueDao();
		EdicaoDao eddao = new EdicaoDao();
		estoques.addAll(eedao.buscaAllEstoque());
		edicoes.addAll(eddao.buscaAllEdicao());
		eedao.desconectar();
		eddao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		if (!edicoes.isEmpty()) {
			EstoqueDao eedao = new EstoqueDao();

			Estoque ee = new Estoque();
			ee.setCod(cod.get());
			for (Edicao ed : edicoes) {
				if (ed.getCod() == codEdicao.get()) {
					ee.setEdicao(ed);
				}
			}
			ee.setQuantidade(quantidade.get());

			eedao.inserirEstoque(ee);
			estoques.add(ee);
			eedao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!edicoes.isEmpty()) {
			EstoqueDao edao = new EstoqueDao();
			for (Estoque e : estoques) {
				if (e.getCod() == cod.get()) {
					Estoque ee = edao.buscaEstoque(e);
					cod.set(ee.getCod());
					codEdicao.set(ee.getEdicao().getCod());
					quantidade.set(ee.getQuantidade());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!edicoes.isEmpty()) {
			for (Estoque e : estoques) {
				if (e.getCod() == cod.get()) {
					EstoqueDao edao = new EstoqueDao();

					for (Edicao ed : edicoes) {
						if (ed.getCod() == codEdicao.get()) {
							e.setEdicao(ed);
							break;
						}
					}

					e.setQuantidade(quantidade.get());

					edao.atualizarEstoque(e);
					edao.desconectar();
					break;
				}
			}
		}
	}

	public void remove(Estoque e) throws ClassNotFoundException, SQLException {
		if (!edicoes.isEmpty()) {
			EstoqueDao edao = new EstoqueDao();
			edao.excluirEstoque(cod.get());
			estoques.remove(e);
			edao.desconectar();
		}
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public IntegerProperty codLivroProperty() {
		return codEdicao;
	}

	public IntegerProperty quantidadeProperty() {
		return quantidade;
	}
}
