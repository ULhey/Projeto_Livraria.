package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidade.Autor;
import Entidade.Editora;
import interfacesDAO.iEditoraDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EditoraDao implements iEditoraDao {
	private Connection c;

	public EditoraDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirEditora(Editora e) throws SQLException {
		String sql = "INSERT INTO editora VALUES (?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, e.getCod());
		ps.setString(2, e.getNome());
		ps.setString(3, e.getSite());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarEditora(Editora e) throws SQLException {
		String sql = "UPDATE editora SET nome = ?, site = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, e.getNome());
		ps.setString(2, e.getSite());
		ps.setInt(3, e.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirEditora(int cod) throws SQLException {
		String sql = "DELETE editora WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Editora buscaEditora(Editora e) throws SQLException {
		String sql = "SELECT cod, nome, site FROM editora WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, e.getCod());

		int cont = 0;
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			e.setNome(rs.getString("nome"));
			e.setSite(rs.getString("site"));
			cont++;
		}

		if (cont == 0) {
			e = new Editora();
		}

		rs.close();
		ps.close();
		return e;
	}

	@Override
	public ObservableList<Editora> buscaAllEditora() throws SQLException {
		String sql = "SELECT cod, nome, site FROM editora";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		ObservableList<Editora> autores = FXCollections.observableArrayList();

		while (rs.next()) {
			Editora e = new Editora();
			e.setCod(rs.getInt("cod"));
			e.setNome(rs.getString("nome"));
			e.setSite(rs.getString("site"));

			autores.add(e);
		}

		rs.close();
		ps.close();

		return autores;
	}
}