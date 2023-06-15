package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Editora;

public interface iEditoraDao {
	public void inserirEditora(Editora e) throws SQLException;
	public void atualizarEditora(Editora e) throws SQLException;
	public void excluirEditora(int cod) throws SQLException;
	public Editora buscaEditora(Editora e) throws SQLException;
	public List<Editora> buscaAllEditora() throws SQLException;
}