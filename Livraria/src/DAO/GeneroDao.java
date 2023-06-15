package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidade.Genero;
import interfacesDAO.iGeneroDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GeneroDao implements iGeneroDao {
	private Connection c;

	public GeneroDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirGenero(Genero g) throws SQLException {
		String sql = "INSERT INTO genero VALUES (?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, g.getCod());
		ps.setString(2, g.getDescricao());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarGenero(Genero g) throws SQLException {
		String sql = "UPDATE genero SET descricao = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, g.getDescricao());
		ps.setInt(2, g.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirGenero(int cod) throws SQLException {
		String sql = "DELETE genero WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Genero buscaGenero(Genero g) throws SQLException {
		String sql = "SELECT cod, descricao FROM genero WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, g.getCod());

		int cont = 0;
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			g.setDescricao(rs.getString("descricao"));
			cont++;
		}

		if (cont == 0) {
			g = new Genero();
		}

		rs.close();
		ps.close();
		return g;
	}

	@Override
	public ObservableList<Genero> buscaAllGenero() throws SQLException {
		String sql = "SELECT cod, descricao FROM genero";

		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		ObservableList<Genero> generos = FXCollections.observableArrayList();

		while (rs.next()) {
			Genero g = new Genero();
			g.setCod(rs.getInt("cod"));
			g.setDescricao(rs.getString("descricao"));

			generos.add(g);
		}

		rs.close();
		ps.close();

		return generos;
	}
}