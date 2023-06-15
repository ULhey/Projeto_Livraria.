package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Autor;


public interface iAutorDao {
	public void inserirAutor(Autor a) throws SQLException;
	public void atualizarAutor(Autor a) throws SQLException;
	public void excluirAutor(int cod) throws SQLException;
	public Autor buscaAutor(Autor a) throws SQLException;
	public List<Autor> buscaAllAutor() throws SQLException;
}