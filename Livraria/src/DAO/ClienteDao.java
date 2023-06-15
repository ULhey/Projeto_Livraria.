package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidade.Cliente;
import interfacesDAO.iClienteDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ClienteDao implements iClienteDao {
	private Connection c;

	public ClienteDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirCliente(Cliente cc) throws SQLException {
		String sql = "INSERT INTO cliente VALUES (?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cc.getCPF());
		ps.setString(2, cc.getNome());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarCliente(Cliente cc) throws SQLException {
		String sql = "UPDATE cliente SET nome = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cc.getNome());
		ps.setString(2, cc.getCPF());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirCliente(String CPF) throws SQLException {
		String sql = "DELETE cliente WHERE CPF = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, CPF);

		ps.execute();
		ps.close();
	}

	@Override
	public Cliente buscaCliente(Cliente cc) throws SQLException {
		String sql = "SELECT CPF, nome FROM cliente WHERE CPF = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cc.getCPF());

		int cont = 0;
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cc.setCPF(rs.getString("CPF"));
			cc.setNome(rs.getString("nome"));
			cont++;
		}

		if (cont == 0) {
			cc = new Cliente();
		}

		rs.close();
		ps.close();
		return cc;
	}

	@Override
	public ObservableList<Cliente> buscaAllCliente() throws SQLException {
		String sql = "SELECT CPF, nome FROM cliente";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		ObservableList<Cliente> clientes = FXCollections.observableArrayList();

		while (rs.next()) {
			Cliente cc = new Cliente();
			cc.setCPF(rs.getString("CPF"));
			cc.setNome(rs.getString("nome"));

			clientes.add(cc);
		}

		rs.close();
		ps.close();

		return clientes;
	}
}
