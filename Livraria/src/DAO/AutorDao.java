package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidade.Autor;
import interfacesDAO.iAutorDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AutorDao implements iAutorDao {
	private Connection c;

	public AutorDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirAutor(Autor a) throws SQLException {
		String sql = "INSERT INTO autor VALUES (?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getCod());
		ps.setString(2, a.getNome());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarAutor(Autor a) throws SQLException {
		String sql = "UPDATE autor SET nome = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, a.getNome());
		ps.setInt(2, a.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirAutor(int cod) throws SQLException {
		String sql = "DELETE autor WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Autor buscaAutor(Autor a) throws SQLException {
		String sql = "SELECT cod, nome FROM autor WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, a.getCod());

		int cont = 0;
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			a.setNome(rs.getString("nome"));
			cont++;
		}

		if (cont == 0) {
			a = new Autor();
		}

		rs.close();
		ps.close();
		return a;
	}

	@Override
	public ObservableList<Autor> buscaAllAutor() throws SQLException {
		String sql = "SELECT cod, nome FROM autor";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		ObservableList<Autor> autores = FXCollections.observableArrayList();

		while (rs.next()) {
			Autor a = new Autor();
			a.setCod(rs.getInt("cod"));
			a.setNome(rs.getString("nome"));

			autores.add(a);
		}

		rs.close();
		ps.close();

		return autores;
	}
}