package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Entidade.Autor;
import Entidade.Cliente;
import Entidade.Compra;
import Entidade.Edicao;
import Entidade.Editora;
import Entidade.Genero;
import Entidade.Livro;
import interfacesDAO.iCompraDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CompraDao implements iCompraDao {
	private Connection c;

	public CompraDao() throws ClassNotFoundException, SQLException {
		GenericDao gdao = new GenericDao();
		c = gdao.conexao();
	}

	public void desconectar() throws SQLException {
		c.close();
	}

	@Override
	public void inserirCompra(Compra cc) throws SQLException {
		String sql = "INSERT INTO compra VALUES (?, ?, ?, ?)";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cc.getCod());
		ps.setInt(2, cc.getEdicao().getCod());
		ps.setInt(3, cc.getQuantidade());
		ps.setString(4, cc.getCliente().getCPF());

		ps.execute();
		ps.close();
	}

	@Override
	public void atualizarCompra(Compra cc) throws SQLException {
		String sql = "UPDATE compra SET codEdicao = ?, qtdComprada = ?, CPFcli = ? WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cc.getEdicao().getCod());
		ps.setInt(2, cc.getQuantidade());
		ps.setString(3, cc.getCliente().getCPF());
		ps.setInt(4, cc.getCod());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluirCompra(int cod) throws SQLException {
		String sql = "DELETE compra WHERE cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cod);

		ps.execute();
		ps.close();
	}

	@Override
	public Compra buscaCompra(Compra cc) throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, g.cod as codg, g.descricao as descr, ed.cod as codEd, ed.nome as nomeEd, ed.site as siteEd, e.cod as codE, e.codEditora as codEed, e.codLivro as codElv, e.numpag as numpag, e.tipoed as tipoEd, e.valor as valor, co.cod as codco, co.codEdicao as codEco, co.CPFcli as cpfco, co.qtdComprada as qtdco, cl.CPF as cpfcl, cl.nome as nomecl, valorTotal = e.valor * co.qtdComprada "
				+ "FROM edicao e INNER JOIN livro l ON l.cod = e.codLivro INNER JOIN editora ed ON e.codEditora = ed.cod INNER JOIN genero g ON g.cod = l.codGenero INNER JOIN autor a ON a.cod = l.codAutor INNER JOIN compra co ON co.codEdicao = e.cod  INNER JOIN cliente cl ON cl.CPF = co.CPFcli "
				+ "WHERE co.cod = ?";

		PreparedStatement ps = c.prepareStatement(sql);
		ps.setInt(1, cc.getCod());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cc.setQuantidade(rs.getInt("qtdco"));
			cc.setValorTotal(rs.getDouble("valorTotal"));

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

			cc.setEdicao(ed);

			Cliente c = new Cliente();
			c.setCPF(rs.getString("cpfcl"));
			c.setNome(rs.getString("nomecl"));
			
			cc.setCliente(c);
			
		}

		rs.close();
		ps.close();
		return cc;
	}

	@Override
	public ObservableList<Compra> buscaAllCompra() throws SQLException {
		String sql = "SELECT a.cod as cod, a.nome as nome, "
				+ "	l.cod as codl, l.codAutor as codAut, l.codGenero as codGen, l.titulo as titulo, "
				+ "	g.cod as codg, g.descricao as descr, " + "	ed.cod as codEd, ed.nome as nomeEd, ed.site as siteEd, "
				+ "	e.cod as codE, e.codEditora as codEed, e.codLivro as codElv, e.numpag as numpag, e.tipoed as tipoEd, e.valor as valor, "
				+ "	co.cod as codco, co.codEdicao as codEco, co.CPFcli as cpfco, co.qtdComprada as qtdco, "
				+ "	cl.CPF as cpfcl, cl.nome as nomecl, " + "	valorTotal = e.valor * co.qtdComprada "
				+ "FROM edicao e INNER JOIN livro l ON  l.cod = e.codLivro "
				+ "	INNER JOIN editora ed ON e.codEditora = ed.cod " + "	INNER JOIN genero g ON g.cod = l.codGenero "
				+ "	INNER JOIN autor a ON a.cod = l.codAutor " + "	INNER JOIN compra co ON co.codEdicao = e.cod "
				+ "	INNER JOIN cliente cl ON cl.CPF = co.CPFcli";

		PreparedStatement ps = c.prepareStatement(sql);
		
		ResultSet rs = ps.executeQuery();

		ObservableList<Compra> compras = FXCollections.observableArrayList();
		
		while (rs.next()) {
			Compra cc = new Compra();
			cc.setCod(rs.getInt("codco"));
			cc.setQuantidade(rs.getInt("qtdco"));
			cc.setValorTotal(rs.getDouble("valorTotal"));

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

			cc.setEdicao(ed);

			Cliente c = new Cliente();
			c.setCPF(rs.getString("cpfcl"));
			c.setNome(rs.getString("nomecl"));
			
			cc.setCliente(c);
			
			compras.add(cc);
		}

		return compras;
	}
}