package negocio;
import java.util.List;

import utilidades.Log;
import basedados.BaseDadosException;
import basedados.GerenciadorBaseDados;
import basedados.GerenciadorBaseDadosJDBC;
import basedados.GerenciadorBaseDadosRAM;
import beans.CD;
import beans.Emprestimo;
import beans.Livro;
import beans.Usuario;

public class GerenciadorRegrasNegocio {

	private GerenciadorBaseDados gerenciadorBaseDados;

	public enum BaseDados {
		RAM, JDBC;
	}

	private BaseDados bd;

	public GerenciadorRegrasNegocio(BaseDados bd) throws NegocioException {
		this.bd = bd;
		if (this.bd == BaseDados.JDBC) {
			try {
				gerenciadorBaseDados = new GerenciadorBaseDadosJDBC();
			} catch (BaseDadosException e) {
				throw new NegocioException(e);
			}
		} else {
			gerenciadorBaseDados = new GerenciadorBaseDadosRAM();
		}
	}

	public void cadastraUsuario(String nome, int codigo)
			throws NegocioException {
		Usuario usuario = new Usuario(nome, codigo);
		try {
			gerenciadorBaseDados.insereUsuario(usuario);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void cadastraLivro(String titulo, String autores, int codigo,
			int qtdExemplares) throws NegocioException {
		Livro livro = new Livro(autores, titulo, qtdExemplares, qtdExemplares,
				0, codigo);
		try {
			gerenciadorBaseDados.insereLivro(livro);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void cadastraCD(String album, String artista, int codigo,
			int qtdExemplares) throws NegocioException {
		CD cd = new CD(album, artista, qtdExemplares, codigo);
		try {
			gerenciadorBaseDados.insereCD(cd);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public void devolveLivro(int codigoEmprestimo) throws NegocioException {
		try {
			Emprestimo emprestimo = gerenciadorBaseDados
					.buscaEmprestimo(codigoEmprestimo);
			emprestimo.setFinalizado(true);
			gerenciadorBaseDados.alteraEmprestimo(emprestimo);
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Usuario> listaUsuarios() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaUsuarios();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Livro> listaLivros() throws NegocioException {
		try {
			return gerenciadorBaseDados.listaLivros();
		} catch (BaseDadosException e) {
			Log.gravaLog(e);
			throw new NegocioException("Problemas no acesso ao banco de dados.");
		}
	}

	public List<Emprestimo> listaEmprestimosEmAbertoDoUsuario(Usuario usuario)
			throws NegocioException {
		return null;
	}
}
