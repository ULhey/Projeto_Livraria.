package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Compra;

public interface iCompraDao {
	public void inserirCompra(Compra cc) throws SQLException;
	public void atualizarCompra(Compra cc) throws SQLException;
	public void excluirCompra(int cod) throws SQLException;
	public Compra buscaCompra(Compra cc) throws SQLException;
	public List<Compra> buscaAllCompra() throws SQLException;
}