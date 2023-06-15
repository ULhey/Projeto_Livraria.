package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidade.Autor;
import Entidade.Edicao;
import Entidade.Editora;
import Entidade.Estoque;
import Entidade.Genero;
import Entidade.Livro;
import interfacesDAO.iEstoqueDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EstoqueDao implements iEstoqueDao {
	private Connection c;

	public EstoqueDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirEstoque(Estoque ee) throws SQLException {
		String sql = "INSERT INTO estoque VALUES (?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ee.getCod());
		ps.setInt(2, ee.getEdicao().getCod());
		ps.setInt(3, ee.getQuantidade());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarEstoque(Estoque ee) throws SQLException {
		String sql = "UPDATE estoque SET codEdicao = ?, quantidade = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ee.getEdicao().getCod());
		ps.setInt(2, ee.getQuantidade());
		ps.setInt(3, ee.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirEstoque(int cod) throws SQLException {
		String sql = "DELETE estoque WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Estoque buscaEstoque(Estoque ee) throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, "
				+ "	l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, "
				+ "	g.cod as codg, g.descricao as descr, " + "	ed.cod as codEd, ed.nome as nomeEd, ed.site as siteEd, "
				+ "	e.cod as codE, e.codEditora as codEed, e.codLivro as codElv, e.numpag as numpag, e.tipoed as tipoEd, e.valor as valor, "
				+ "	et.cod as codEt, et.codEdicao as codEet, et.quantidade as qtd "
				+ "FROM edicao e INNER JOIN livro l ON  l.cod = e.codLivro "
				+ "	INNER JOIN editora ed ON e.codEditora = ed.cod " + "	INNER JOIN genero g ON g.cod = l.codGenero "
				+ "	INNER JOIN autor a ON a.cod = l.codAutor " + "	INNER JOIN estoque et ON et.codEdicao = e.cod "
				+ "WHERE et.cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, ee.getCod());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			ee.setCod(rs.getInt("codEt"));

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
			ed.setLivro(l);

			ee.setEdicao(ed);
			ee.setQuantidade(rs.getInt("qtd"));
		}

		return ee;
	}

	@Override
	public ObservableList<Estoque> buscaAllEstoque() throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, "
				+ "	l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, "
				+ "	g.cod as codg, g.descricao as descr, " + "	ed.cod as codEd, ed.nome as nomeEd, ed.site as siteEd, "
				+ "	e.cod as codE, e.codEditora as codEed, e.codLivro as codElv, e.numpag as numpag, e.tipoed as tipoEd, e.valor as valor, "
				+ "	et.cod as codEt, et.codEdicao as codEet, et.quantidade as qtd "
				+ "FROM edicao e INNER JOIN livro l ON  l.cod = e.codLivro "
				+ "	INNER JOIN editora ed ON e.codEditora = ed.cod " + "	INNER JOIN genero g ON g.cod = l.codGenero "
				+ "	INNER JOIN autor a ON a.cod = l.codAutor " + "	INNER JOIN estoque et ON et.codEdicao = e.cod ";

		PreparedStatement ps = c.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		ObservableList<Estoque> estoques = FXCollections.observableArrayList();

		while (rs.next()) {
			Estoque ee = new Estoque();
			ee.setCod(rs.getInt("codEt"));

			Editora e = new Editora();
			e.setCod(rs.getInt("codEd"));
			e.setNome(rs.getString("nomeEd"));
			e.setSite(rs.getString("siteEd"));

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

			Edicao ed = new Edicao();
			ed.setCod(rs.getInt("codE"));
			ed.setNumpag(rs.getInt("numpag"));
			ed.setTipoed(rs.getString("tipoEd"));
			ed.setValor(rs.getDouble("valor"));
			ed.setLivro(l);

			ee.setEdicao(ed);
			ee.setQuantidade(rs.getInt("qtd"));

			estoques.add(ee);
		}

		return estoques;
	}
}
