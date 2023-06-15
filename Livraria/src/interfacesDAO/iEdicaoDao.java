package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Edicao;

public interface iEdicaoDao {
	public void inserirEdicao(Edicao ed) throws SQLException;
	public void atualizarEdicao(Edicao ed) throws SQLException;
	public void excluirEdicao(int cod) throws SQLException;
	public Edicao buscaEdicao(Edicao ed) throws SQLException;
	public List<Edicao> buscaAllEdicao() throws SQLException;
}
