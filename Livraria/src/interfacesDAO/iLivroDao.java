	package interfacesDAO;

import java.sql.SQLException;
import java.util.List;

import Entidade.Livro;

public interface iLivroDao {
	public void inserirLivro(Livro l) throws SQLException;
	public void atualizarLivro(Livro l) throws SQLException;
	public void excluirLivro(int cod) throws SQLException;
	public Livro buscaLivro(Livro l) throws SQLException;
	public List<Livro> buscaAllLivro() throws SQLException;
}