package basedados;
import java.sql.SQLException;
import java.util.LinkedList;

import utilidades.Log;
import beans.CD;
import beans.Emprestimo;
import beans.Item;
import beans.Livro;
import beans.Usuario;

public class GerenciadorBaseDadosJDBC extends ConectorJDBC implements
		GerenciadorBaseDados {

	private static final String PASSWORD = "";
	private static final String USER = "root";
	private static final String HOST = "localhost";
	private static final String DB_NAME = "coo2018";

	public GerenciadorBaseDadosJDBC() throws BaseDadosException {
		super(DB.MYSQL);
	}

	protected String getUser() {
		return USER;
	}

	@Override
	protected String getPassword() {
		return PASSWORD;
	}

	@Override
	protected String getDbHost() {
		return HOST;
	}

	@Override
	protected String getDbName() {
		return DB_NAME;
	}

	private void insereItem(Item item) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Item (qtdTotalExemplares, qtdExemplaresDisponiveis, qtdExemplaresEmprestados, codigo) values (?, ?, ?, ?)");

		try {
			pstmt.setInt(1, item.getQtdTotalExemplares());
			pstmt.setInt(2, item.getQtdExemplaresDisponiveis());
			pstmt.setInt(3, item.getQtdExemplaresEmprestados());
			pstmt.setInt(4, item.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public void insereUsuario(Usuario usuario) throws BaseDadosException {
		abreConexao();
		preparaComandoSQL("insert into Usuario (nome, codigo) values (?, ?)");

		try {
			pstmt.setString(1, usuario.getNome());
			pstmt.setInt(2, usuario.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public void insereLivro(Livro livro) throws BaseDadosException {
		insereItem(livro);
		abreConexao();
		preparaComandoSQL("insert into Livro (autores, titulo, codigo) values (?, ?, ?)");

		try {
			pstmt.setString(1, livro.getAutores());
			pstmt.setString(2, livro.getTitulo());
			pstmt.setInt(3, livro.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public void insereCD(CD cd) throws BaseDadosException {
		insereItem(cd);
		abreConexao();
		preparaComandoSQL("insert into CD (artista, album, codigo) values (?, ?, ?)");

		try {
			pstmt.setString(1, cd.getArtista());
			pstmt.setString(2, cd.getAlbum());
			pstmt.setInt(3, cd.getCodigo());
			pstmt.execute();
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Erro ao setar os parâmetros da consulta.");
		}

		fechaConexao();
	}

	public void insereEmprestimo(Emprestimo emprestimo)
			throws BaseDadosException {
	}

	public void alteraEmprestimo(Emprestimo emprestimoAlterado)
			throws BaseDadosException {
	}

	public CD buscaCD(int codigo) throws BaseDadosException {
		return null; // não encontrou
	}

	public Livro buscaLivro(int codigo) throws BaseDadosException {
		return null; // não encontrou
	}

	public Usuario buscaUsuario(int codigoUsuario) throws BaseDadosException {
		return null; // não encontrou
	}

	public Emprestimo buscaEmprestimo(int codigoEmprestimo)
			throws BaseDadosException {
		return null; // não encontrou
	}
	
	//spekead
	public LinkedList<Usuario> listaUsuarios() throws BaseDadosException {
		LinkedList<Usuario> usuarios = new LinkedList<Usuario>();
		abreConexao();
		preparaComandoSQL("select codigo, nome from Usuario");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				String nome = rs.getString(2);
				Usuario usuario = new Usuario(nome, codigo);
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return usuarios;
	}

	private LinkedList<Item> listaItens() throws BaseDadosException {
		LinkedList<Item> itens = new LinkedList<Item>();
		abreConexao();
		preparaComandoSQL("select codigo, " + "qtdExemplaresDisponiveis, "
				+ "qtdExemplaresEmprestados, " + "qtdTotalExemplares "
				+ "from Item");

		try {
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int codigo = rs.getInt(1);
				int qtdExemplaresDisponiveis = rs.getInt(2);
				int qtdExemplaresEmprestados = rs.getInt(3);
				int qtdTotalExemplares = rs.getInt(4);
				Item item = new Item(qtdTotalExemplares,
						qtdExemplaresDisponiveis, qtdExemplaresEmprestados,
						codigo);
				itens.add(item);
			}
		} catch (SQLException e) {
			Log.gravaLog(e);
			throw new BaseDadosException(
					"Problemas ao ler o resultado da consulta.");
		}

		fechaConexao();
		return itens;
	}

	public LinkedList<Livro> listaLivros() throws BaseDadosException {
		LinkedList<Livro> livros = new LinkedList<Livro>();
		LinkedList<Item> itens = listaItens();
		abreConexao();
		preparaComandoSQL("select autores, titulo from Livro where codigo = ?");

		for (Item item : itens) {
			try {
				pstmt.setInt(1, item.getCodigo());
				rs = pstmt.executeQuery();

				if (rs.next()) {
					String autores = rs.getString(1);
					String titulo = rs.getString(2);
					Livro livro = new Livro(autores, titulo, item);
					livros.add(livro);
				}
			} catch (SQLException e) {
				Log.gravaLog(e);
				throw new BaseDadosException(
						"Problemas ao ler o resultado da consulta.");
			}

		}

		fechaConexao();
		return livros;
	}

	public LinkedList<Emprestimo> listaEmprestimosEmAbertoDoUsuario(
			Usuario usuario) throws BaseDadosException {
		return null;
	}
}
