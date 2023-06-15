package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Cliente;

public interface iClienteDao {
	public void inserirCliente(Cliente cc) throws SQLException;
	public void atualizarCliente(Cliente cc) throws SQLException;
	public void excluirCliente(String CPF) throws SQLException;
	public Cliente buscaCliente(Cliente cc) throws SQLException;
	public List<Cliente> buscaAllCliente() throws SQLException;
}