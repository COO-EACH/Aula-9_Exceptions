package basedados;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import beans.CD;
import beans.Emprestimo;
import beans.Livro;
import beans.Usuario;

public class GerenciadorBaseDadosRAM implements GerenciadorBaseDados {
	LinkedList<Livro> livros = new LinkedList<Livro>();
	Vector<CD> cds = new Vector<CD>();
	ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

	@Override
	public void insereUsuario(Usuario usuario) throws BaseDadosException {
		usuarios.add(usuario);
	}

	@Override
	public void insereLivro(Livro livro) throws BaseDadosException {
		livros.add(livro);
	}

	@Override
	public void insereCD(CD cd) throws BaseDadosException {
		cds.add(cd);
	}

	@Override
	public List<Usuario> listaUsuarios() throws BaseDadosException {
		return usuarios;
	}

	@Override
	public List<Livro> listaLivros() throws BaseDadosException {
		return livros;
	}

	@Override
	public void insereEmprestimo(Emprestimo emprestimo)
			throws BaseDadosException {
		// TODO Auto-generated method stub
	}

	@Override
	public void alteraEmprestimo(Emprestimo emprestimoAlterado)
			throws BaseDadosException {
		// TODO Auto-generated method stub
	}

	@Override
	public CD buscaCD(int codigo) throws BaseDadosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Livro buscaLivro(int codigo) throws BaseDadosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario buscaUsuario(int codigoUsuario) throws BaseDadosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Emprestimo buscaEmprestimo(int codigoEmprestimo)
			throws BaseDadosException {
		// TODO Auto-generated method stub
		return null;
	}
}
