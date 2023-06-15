package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Genero;

public interface iGeneroDao {
	public void inserirGenero(Genero g) throws SQLException;
	public void atualizarGenero(Genero g) throws SQLException;
	public void excluirGenero(int cod) throws SQLException;
	public Genero buscaGenero(Genero g) throws SQLException;
	public List<Genero> buscaAllGenero() throws SQLException;
}