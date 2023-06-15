package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidade.Autor;
import Entidade.Genero;
import Entidade.Livro;
import interfacesDAO.iLivroDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LivroDao implements iLivroDao {
	private Connection c;

	public LivroDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirLivro(Livro l) throws SQLException {
		String sql = "INSERT INTO livro VALUES (?, ?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, l.getCod());
		ps.setString(2, l.getTitulo());
		ps.setInt(3, l.getGenero().getCod());
		ps.setInt(4, l.getAutor().getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarLivro(Livro l) throws SQLException {
		String sql = "UPDATE livro SET titulo = ?, codGenero = ?, codAutor = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, l.getTitulo());
		ps.setInt(2, l.getGenero().getCod());
		ps.setInt(3, l.getAutor().getCod());
		ps.setInt(4, l.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirLivro(int cod) throws SQLException {
		String sql = "DELETE livro WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Livro buscaLivro(Livro l) throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, g.cod as codg, g.descricao as descr "
				+ "FROM autor a INNER JOIN livro l ON l.codAutor = a.cod INNER JOIN genero g ON g.cod = l.codGenero "
				+ "WHERE l.cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, l.getCod());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			l.setCod(rs.getInt("codl"));
			l.setTitulo(rs.getString("titulo"));
			
			Autor a = new Autor();
			a.setCod(rs.getInt("cod"));
			a.setNome(rs.getString("nome"));
			l.setAutor(a);
			
			Genero g = new Genero();
			g.setCod(rs.getInt("codg"));
			g.setDescricao(rs.getString("descr"));
			l.setGenero(g);
		}
		
		return l;
	}

	@Override
	public ObservableList<Livro> buscaAllLivro() throws SQLException {
		ObservableList<Livro> livros = FXCollections.observableArrayList();
		
		String sql = "SELECT a.cod as cod, a.nome as nome,  l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, g.cod as codg, g.descricao as descr "
				+ "FROM autor a INNER JOIN livro l ON l.codAutor = a.cod INNER JOIN genero g ON g.cod = l.codGenero ";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Livro l = new Livro();
			l.setCod(rs.getInt("codL"));
			l.setTitulo(rs.getString("titulo"));
			
			Autor a = new Autor();
			a.setCod(rs.getInt("cod"));
			a.setNome(rs.getString("nome"));
			l.setAutor(a);
			
			Genero g = new Genero();
			g.setCod(rs.getInt("codg"));
			g.setDescricao(rs.getString("descr"));
			l.setGenero(g);
			
			livros.add(l);
		}
		
		return livros;
	}
}