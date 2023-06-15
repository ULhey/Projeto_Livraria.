package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entidade.Autor;
import Entidade.Edicao;
import Entidade.Editora;
import Entidade.Genero;
import Entidade.Livro;
import interfacesDAO.iEdicaoDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EdicaoDao implements iEdicaoDao {
	private Connection c;
	
	public EdicaoDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}
	
	public void desconectar() throws SQLException {
		c.close();
	}
	
	@Override
	public void inserirEdicao(Edicao ed) throws SQLException {
		String sql = "INSERT INTO edicao VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ed.getCod());
		ps.setString(2, ed.getTipoed());
		ps.setDouble(3, ed.getValor());
		ps.setInt(4, ed.getNumpag());	
		ps.setInt(5, ed.getEditora().getCod());
		ps.setInt(6, ed.getLivro().getCod());
		
		ps.execute();
		ps.close();
		
	}

	@Override
	public void atualizarEdicao(Edicao ed) throws SQLException {
		String sql = "UPDATE edicao SET tipoEd = ?, valor = ?, numpag = ?, codEditora = ?, codLivro = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, ed.getTipoed());
		ps.setDouble(2, ed.getValor());
		ps.setInt(3, ed.getNumpag());	
		ps.setInt(4, ed.getEditora().getCod());
		ps.setInt(5, ed.getLivro().getCod());
		ps.setInt(6, ed.getCod());

		ps.execute();
		ps.close();
		
	}

	@Override
	public void excluirEdicao(int cod) throws SQLException {
		String sql = "DELETE edicao WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
		
	}

	@Override
	public Edicao buscaEdicao(Edicao ed) throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, g.cod as codg, g.descricao as descr, ed.cod as codEd, ed.nome as nomeEd, ed.site as siteEd, e.cod as codE, e.codEditora as codEed, e.codLivro as codElv, e.numpag as numpag, e.tipoed as tipoEd, e.valor as valor "
				+ "FROM edicao e INNER JOIN livro l ON  l.cod = e.codLivro INNER JOIN editora ed ON e.codEditora = ed.cod INNER JOIN genero g ON g.cod = l.codGenero INNER JOIN autor a ON a.cod = l.codAutor "
				+ "WHERE e.cod = ?";
		
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ed.getCod());
		
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			ed.setNumpag(rs.getInt("numpag"));
			ed.setTipoed(rs.getString("tipoEd"));
			ed.setValor(rs.getDouble("valor"));
			
			Editora e = new Editora();
			e.setCod(rs.getInt("codEd"));
			e.setNome(rs.getString("nomeEd"));
			e.setSite(rs.getString("siteEd"));
			
			ed.setEditora(e);
			
			Livro l = new Livro();
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
			
			ed.setLivro(l);
		}
		
		return ed;
	}

	@Override
	public ObservableList<Edicao> buscaAllEdicao() throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, "
				+ "	l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, "
				+ "	ed.cod as codEd, ed.nome as nomeEd, ed.site as siteEd, "
				+ "	e.cod as codE, e.codEditora as codEed, e.codLivro as codElv, "
				+ " g.cod as codg, g.descricao as descr, "
				+ "	e.numpag as numpag, e.tipoed as tipoEd, e.valor as valor "
				+ "FROM edicao e INNER JOIN livro l ON l.cod = e.codLivro "
				+ "	INNER JOIN editora ed ON e.codEditora = ed.cod "
				+ "	INNER JOIN genero g ON g.cod = l.codGenero "
				+ "	INNER JOIN autor a ON a.cod = l.codAutor ";
		
		ObservableList<Edicao> edicoes = FXCollections.observableArrayList();
		
		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Livro l = new Livro();
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

			Editora e = new Editora();
			e.setCod(rs.getInt("codEd"));
			e.setNome(rs.getString("nomeEd"));
			e.setSite(rs.getString("siteEd"));

			Edicao ed = new Edicao();
			ed.setCod(rs.getInt("codE"));
			ed.setNumpag(rs.getInt("numpag"));
			ed.setTipoed(rs.getString("tipoEd"));
			ed.setValor(rs.getDouble("valor"));
			ed.setEditora(e);
			ed.setLivro(l);
			
			edicoes.add(ed);
			
		}
		return edicoes;
	}

}
