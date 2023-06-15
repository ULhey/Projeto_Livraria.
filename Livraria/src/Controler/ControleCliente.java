package Controler;

import java.sql.SQLException;

import DAO.ClienteDao;
import Entidade.Cliente;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ControleCliente {
	ObservableList<Cliente> clientes = FXCollections.observableArrayList();

	private StringProperty CPF = new SimpleStringProperty("");
	private StringProperty nome = new SimpleStringProperty("");

	public ObservableList<Cliente> getLista() {
		return clientes;
	}

	public void daEntidade(Cliente a) {
		CPF.set(a.getCPF());
		nome.set(a.getNome());
	}

	public ControleCliente() throws ClassNotFoundException, SQLException {
		ClienteDao cdao = new ClienteDao();
		clientes.addAll(cdao.buscaAllCliente());
		cdao.desconectar();
	}

	public void criar() throws ClassNotFoundException, SQLException {
		ClienteDao cdao = new ClienteDao();

		Cliente c = new Cliente();
		c.setCPF(CPF.get());
		c.setNome(nome.get());

		cdao.inserirCliente(c);
		clientes.add(c);
		cdao.desconectar();
	}

	public void leitura() throws ClassNotFoundException, SQLException {
		ClienteDao cdao = new ClienteDao();
		for (Cliente c : clientes) {
			if (c.getCPF().contains(CPF.get())) {
				Cliente cc = cdao.buscaCliente(c);
				nome.set(cc.getNome());
				break;
			}
		}
		cdao.desconectar();
	}

	public void atualizar() throws ClassNotFoundException, SQLException {
		ClienteDao cdao = new ClienteDao();
		for (Cliente c : clientes) {
			if (c.getCPF().contains(CPF.get())) {
				c.setNome(nome.get());
				cdao.atualizarCliente(c);
				break;
			}
		}
		cdao.desconectar();
	}

	public void remove(Cliente c) throws ClassNotFoundException, SQLException {
		ClienteDao cdao = new ClienteDao();
		cdao.excluirCliente(c.getCPF());
		clientes.remove(c);
		cdao.desconectar();
	}

	public StringProperty CPFProperty() {
		return CPF;
	}

	public StringProperty nomeProperty() {
		return nome;
	}
}
