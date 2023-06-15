package Controler;

import java.sql.SQLException;

import DAO.ClienteDao;
import DAO.CompraDao;
import DAO.EdicaoDao;
import Entidade.Cliente;
import Entidade.Compra;
import Entidade.Edicao;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleCompra {
	ObservableList<Compra> compras = FXCollections.observableArrayList();
	ObservableList<Edicao> edicaos = FXCollections.observableArrayList();
	ObservableList<Cliente> clientes = FXCollections.observableArrayList();

	private IntegerProperty cod = new SimpleIntegerProperty(0);
	private IntegerProperty codEdicao = new SimpleIntegerProperty(0);
	private IntegerProperty quantidade = new SimpleIntegerProperty(0);
	private DoubleProperty valorTotal = new SimpleDoubleProperty(0);
	private StringProperty cpfCliente = new SimpleStringProperty("");

	public void daEntidade(Compra c) {
		cod.set(c.getCod());
		codEdicao.set(c.getEdicao().getCod());
		quantidade.set(c.getQuantidade());
		valorTotal.set(c.getValorTotal());
		cpfCliente.set(c.getCliente().getCPF());
	}

	public ObservableList<Compra> getLista() {
		return compras;
	}

	public ControleCompra() throws ClassNotFoundException, SQLException {
		CompraDao cdao = new CompraDao();
		EdicaoDao eddao = new EdicaoDao();
		ClienteDao ccdao = new ClienteDao();
		edicaos.addAll(eddao.buscaAllEdicao());
		clientes.addAll(ccdao.buscaAllCliente());
		compras.addAll(cdao.buscaAllCompra());
		cdao.desconectar();
		eddao.desconectar();
		ccdao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		if (!edicaos.isEmpty() && !clientes.isEmpty()) {
			CompraDao cdao = new CompraDao();

			Compra c = new Compra();

			c.setCod(cod.get());
			for (Edicao ed : edicaos) {
				if (ed.getCod() == codEdicao.get()) {
					c.setEdicao(ed);
					break;
				}
			}
			c.setQuantidade(quantidade.get());
			for (Cliente cc : clientes) {
				if (cc.getCPF().equals(cpfCliente.get())) {
					c.setCliente(cc);
				}
			}

			cdao.inserirCompra(c);
			compras.add(c);
			cdao.desconectar();
		}
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		if (!edicaos.isEmpty() && !clientes.isEmpty()) {
			CompraDao cdao = new CompraDao();
			for (Compra c : compras) {
				if (c.getCod() == cod.get()) {
					Compra cc = cdao.buscaCompra(c);
					codEdicao.set(cc.getEdicao().getCod());
					quantidade.set(cc.getQuantidade());
					valorTotal.set(cc.getValorTotal());
					cpfCliente.set(cc.getCliente().getCPF());
					break;
				}
			}
		}
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		if (!edicaos.isEmpty() && !clientes.isEmpty()) {
			for (Compra c : compras) {
				if (cod.get() == c.getCod()) {
					CompraDao cdao = new CompraDao();

					c.setCod(cod.get());
					for (Edicao ed : edicaos) {
						if (ed.getCod() == codEdicao.get()) {
							c.setEdicao(ed);
							break;
						}
					}
					c.setQuantidade(quantidade.get());
					for (Cliente cc : clientes) {
						if (cc.getCPF().equals(cpfCliente.get())) {
							c.setCliente(cc);
						}
					}

					compras.add(c);
					cdao.atualizarCompra(c);
					cdao.desconectar();
				}
			}
		}
	}

	public void remove(Compra c) throws ClassNotFoundException, SQLException {
		if (!edicaos.isEmpty() && !edicaos.isEmpty()) {
			CompraDao cdao = new CompraDao();
			cdao.excluirCompra(cod.get());
			compras.remove(c);
			cdao.desconectar();
		}
	}

	public IntegerProperty codProperty() {
		return cod;
	}

	public IntegerProperty codEdicaoProperty() {
		return codEdicao;
	}

	public IntegerProperty quantidadeProperty() {
		return quantidade;
	}

	public DoubleProperty valorTotalProperty() {
		return valorTotal;
	}

	public StringProperty cpfClienteProperty() {
		return cpfCliente;
	}

}