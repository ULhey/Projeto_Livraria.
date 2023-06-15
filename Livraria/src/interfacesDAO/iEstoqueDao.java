package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Estoque;

public interface iEstoqueDao {
	public void inserirEstoque(Estoque ee) throws SQLException;
	public void atualizarEstoque(Estoque ee) throws SQLException;
	public void excluirEstoque(int cod) throws SQLException;
	public Estoque buscaEstoque(Estoque ee) throws SQLException;
	public List<Estoque> buscaAllEstoque() throws SQLException;
}