package visualizacao;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import negocio.GerenciadorRegrasNegocio;
import negocio.NegocioException;
import beans.Livro;
import beans.Usuario;

public class GerenciadorVisualizacaoSwing extends JFrame {

	private static final long serialVersionUID = 1L;
	private List<JPanel> paineis = new LinkedList<JPanel>();
	private GerenciadorRegrasNegocio gerenciadorRegrasNegocio;
	private Validador validador = new Validador();
	
	// painel fundo
	private JLabel lblBibliotecaCOO2018 = new JLabel();
	private Container painelFundo;

	// painel cadastrar usuário
	private JPanel painelCadastrarUsuario = new JPanel();
	private JTextField fieldNomeUsuario = new JTextField();
	private JTextField fieldCodigoUsuario = new JTextField();

	// painel cadastrar livro
	private JPanel painelCadastrarLivro = new JPanel();
	private JTextField fieldTituloLivro = new JTextField();
	private JTextField fieldCodigoLivro = new JTextField();
	private JTextField fieldAutoresLivro = new JTextField();

	// painel lista livros
	private JPanel painelListaLivros = new JPanel();
	private JTable tabelaLivros = new JTable(new DefaultTableModel(
			new Object[] { "Código", "Autores", "Título", }, 0));

	// painel lista usuários
	private JPanel painelListaUsuarios = new JPanel();
	private JTable tabelaUsuarios = new JTable(new DefaultTableModel(
			new Object[] { "Código", "Nome" }, 0));

	public GerenciadorVisualizacaoSwing() {
		try {
			gerenciadorRegrasNegocio = new GerenciadorRegrasNegocio(
					GerenciadorRegrasNegocio.BaseDados.JDBC);
		} catch (NegocioException e) {
			JOptionPane.showMessageDialog(this, e.getMessage() + "\n"
					+ "A aplicação será encerrada.");
			System.exit(0);
		}

		inicializaComponentes();
	}

	private void inicializaComponentes() {

		// frame
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 500, 500);
		setResizable(false);
		setTitle("Biblioteca COO 2018");

		// --- painel de fundo ---
		painelFundo = getContentPane();
		painelFundo.setBounds(0, 0, 500, 500);
		painelFundo.setLayout(null);
		painelFundo.setBackground(new Color(234, 255, 255));

		lblBibliotecaCOO2018.setFont(new Font("Luminari", 3, 36));
		lblBibliotecaCOO2018.setForeground(new Color(0, 153, 153));
		lblBibliotecaCOO2018.setText(" Biblioteca COO 2018 ");
		adicionaNoCentroDaLinha(painelFundo, lblBibliotecaCOO2018, 20);

		criaMenu();
		// ------------------------

		// --- painel cadastrar usuário ---
		adicionaPainel(painelCadastrarUsuario, "Cadastrar Usuário", new Color(
				154, 255, 255));
		adicionaCamposComRotulos(painelCadastrarUsuario, new String[] {
				"Nome: ", "Código: " }, new JTextField[] { fieldNomeUsuario,
				fieldCodigoUsuario });
		adicionaBotao(painelCadastrarUsuario, "Cadastrar", 3,
				"cadastrarUsuarioActionPerformed");
		// ----------------------------------

		// --- painel cadastrar livro ---
		adicionaPainel(painelCadastrarLivro, "Cadastrar Livro", new Color(255,
				154, 255));
		adicionaCamposComRotulos(painelCadastrarLivro, new String[] {
				"Título: ", "Autores: ", "Código: " }, new JTextField[] {
				fieldTituloLivro, fieldAutoresLivro, fieldCodigoLivro });
		adicionaBotao(painelCadastrarLivro, "Cadastrar", 4,
				"cadastrarLivroActionPerformed");
		// ------------------------------

		// --- painel lista livros ---
		adicionaPainel(painelListaLivros, "Livros Cadastrados", new Color(255,
				255, 154));
		adicionaTabela(painelListaLivros, tabelaLivros);
		// ---------------------------

		// --- painel lista usuarios ---
		adicionaPainel(painelListaUsuarios, "Usuários Cadastrados", new Color(
				255, 154, 154));
		adicionaTabela(painelListaUsuarios, tabelaUsuarios);
		// ---------------------------

		mostraPainel(null);
		setVisible(true);
	}

	// ---------------- métodos auxiliares ---------------------
	private void adicionaNoCentroDaLinha(Container painel,
			Component componente, int descolamentoTopo) {
		Insets insetsPainel = painel.getInsets();
		painel.add(componente);
		Dimension d = componente.getPreferredSize();
		int deslocamento = (painel.getWidth() - d.width) / 2;
		componente.setBounds(insetsPainel.left + deslocamento,
				insetsPainel.right + descolamentoTopo, d.width, d.height);
	}

	private int calcRowPosition(int row) {
		return 50 + 30 * row;
	}

	private void adicionaCampoComRotulo(JPanel painel, String lblText,
			Component field, int row) {
		JLabel lbl = new JLabel();
		lbl.setFont(new Font("Microsoft Sans Serif", 0, 13));
		lbl.setText(lblText);
		Insets insetsPainel = painel.getInsets();
		painel.add(lbl);
		painel.add(field);
		int fimLabel = 150;
		Dimension dimLbl = lbl.getPreferredSize();
		int deslocamentoEsq = fimLabel - dimLbl.width;
		int descolamentoTopo = calcRowPosition(row);
		int tamanhoField = 230;
		lbl.setBounds(insetsPainel.left + deslocamentoEsq, insetsPainel.top
				+ descolamentoTopo, dimLbl.width, dimLbl.height);

		field.setBounds(insetsPainel.left + fimLabel + 5, insetsPainel.top
				+ descolamentoTopo - 4, tamanhoField, dimLbl.height + 10);
	}

	private void adicionaPainel(JPanel painel, String titulo, Color cor) {
		painel.setLayout(null);
		painel.setBounds(0, 90, 500, 410);
		painel.setBackground(cor);
		painelFundo.add(painel);
		paineis.add(painel);
		JLabel lbl = new JLabel();
		lbl.setFont(new Font("Microsoft Sans Serif", 2, 18));
		lbl.setText(titulo);
		adicionaNoCentroDaLinha(painel, lbl, 30);
	}

	private void adicionaCamposComRotulos(JPanel painel, String[] lblText,
			Component[] field) {
		for (int row = 0; row < lblText.length; row++) {
			adicionaCampoComRotulo(painel, lblText[row], field[row], row + 1);
		}
	}

	void adicionaBotao(JPanel painel, String btnText, int row,
			String actionListenerName) {
		JButton btn = new JButton();
		painel.add(btn);
		btn.setText(btnText);
		adicionaNoCentroDaLinha(painel, btn, calcRowPosition(row));
		addActionListener(btn, actionListenerName);
	}

	private void addActionListener(AbstractButton btn, String actionListenerName) {
		Object autoRef = this;
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					Method method = autoRef.getClass().getMethod(
							actionListenerName, ActionEvent.class);
					method.invoke(autoRef, evt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void adicionaTabela(JPanel painel, JTable tabela) {
		JScrollPane scrollPane = new JScrollPane(tabela);
		tabela.setBounds(0, 90, 400, 0);
		scrollPane.setBounds(50, 90, 400, 200);
		painel.add(scrollPane);
	}

	private void montaTabelaLivros(List<Livro> livros) {
		DefaultTableModel model = (DefaultTableModel) tabelaLivros.getModel();

		for (Livro livro : livros) {
			model.addRow(new Object[] { livro.getCodigo(), livro.getAutores(),
					livro.getTitulo() });
		}
	}

	private void montaTabelaUsuarios(List<Usuario> usuarios) {
		DefaultTableModel model = (DefaultTableModel) tabelaUsuarios.getModel();

		for (Usuario usuario : usuarios) {
			model.addRow(new Object[] { usuario.getCodigo(), usuario.getNome() });
		}
	}

	private void mostraPainel(JPanel painel) {
		for (JPanel p : paineis) {
			if (p == painel) {
				p.setVisible(true);
			} else {
				p.setVisible(false);
			}
		}
	}

	private void insereItemNoMenu(JMenuItem item, JMenu menu, String texto,
			String actionListenerName) {
		menu.add(item);
		item.setText(texto);
		addActionListener(item, actionListenerName);
	}

	private void criaMenu() {
		// menu principal
		JMenuBar menuRaiz = new JMenuBar();
		setJMenuBar(menuRaiz);

		// submenu cadastrar
		JMenu menuInserir = new JMenu();
		menuInserir.setText("Cadastrar");
		insereItemNoMenu(new JMenuItem(), menuInserir, "Livro",
				"inserirLivroActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuInserir, "CD",
				"inserirCDActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuInserir, "Usuário",
				"inserirUsuarioActionPerformed");
		menuRaiz.add(menuInserir);

		// submenu listar
		JMenu menuListar = new JMenu();
		menuListar.setText("Listar");
		insereItemNoMenu(new JMenuItem(), menuListar, "Livro",
				"listarLivroActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuListar, "CD",
				"listarCDActionPerformed");
		insereItemNoMenu(new JMenuItem(), menuListar, "Usuário",
				"listarUsuarioActionPerformed");
		menuRaiz.add(menuListar);
	}

	// ---------------------------------------------------------

	// ------------- tratamento dos eventos --------------------
	public void inserirLivroActionPerformed(ActionEvent evt) {
		mostraPainel(painelCadastrarLivro);
	}

	public void inserirUsuarioActionPerformed(ActionEvent evt) {
		mostraPainel(painelCadastrarUsuario);
	}

	public void inserirCDActionPerformed(ActionEvent evt) {
		System.out.println("inserir CD");
	}

	public void listarUsuarioActionPerformed(ActionEvent evt) {
		mostraPainel(painelListaUsuarios);
		List<Usuario> usuarios;
		
		try {
			usuarios = gerenciadorRegrasNegocio.listaUsuarios();
			Collections.sort(usuarios, new Comparator<Usuario>() {

				@Override
				public int compare(Usuario u1, Usuario u2) {
					if(u1.getCodigo() < u2.getCodigo()) {
						return -1;
					}
					
					if(u1.getCodigo() > u2.getCodigo()) {
						return 1;
					}

					return 0;
				}
			});
			montaTabelaUsuarios(usuarios);
		} catch (NegocioException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Não foi possível listar os usuários. \n"
									+ e.getMessage());

		}
	}

	public void listarLivroActionPerformed(ActionEvent evt) {
		mostraPainel(painelListaLivros);
		List<Livro> livros = new LinkedList<Livro>();

		for (int i = 0; i < 16; i++) {
			livros.add(new Livro("Autor " + i, "Título " + i, 0, 0, 0, i));
		}

		montaTabelaLivros(livros);
	}

	public void listarCDActionPerformed(ActionEvent evt) {
		System.out.println("listar CD");
	}

	public void cadastrarUsuarioActionPerformed(ActionEvent evt) {
		int codigo;
		String nome;

		try {
			codigo = validador.validaFormataInteiro(
					fieldCodigoUsuario.getText(), "Código");
			nome = validador.validaFormataCaixaAlta(fieldNomeUsuario.getText(),
					10, "Nome");
			gerenciadorRegrasNegocio.cadastraUsuario(nome, codigo);
			JOptionPane.showMessageDialog(this,
					"Usuário cadastrado com sucesso.");
			fieldCodigoUsuario.setText("");
			fieldNomeUsuario.setText("");
		} catch (ValidacaoException | NegocioException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Não foi possível cadastrar o usuário. \n"
									+ e.getMessage());
		}
	}

	public void cadastrarLivroActionPerformed(ActionEvent evt) {
		System.out.println("cadastrar livro");
		System.out.println(fieldTituloLivro.getText());
		System.out.println(fieldAutoresLivro.getText());
		System.out.println(fieldCodigoLivro.getText());
	}

	// ----------------------------------------------------------

	public static void main(String args[]) throws Exception {
		// configura look-and-feel
		for (UIManager.LookAndFeelInfo info : UIManager
				.getInstalledLookAndFeels()) {
			if ("Nimbus".equals(info.getName())) {
				UIManager.setLookAndFeel(info.getClassName());
				break;
			}
		}

		// mostra a tela em uma outra thread
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GerenciadorVisualizacaoSwing().setVisible(true);
			}
		});
	}
}
