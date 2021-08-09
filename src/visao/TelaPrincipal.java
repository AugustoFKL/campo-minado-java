package visao;

import javax.swing.JFrame;

import modelo.Tabuleiro;

public class TelaPrincipal extends JFrame {

	private static final long serialVersionUID = 3683065875741257042L;
	public static final int WIDTH1 = 690;
	public static final int HEIGHT1 = 438;

	public TelaPrincipal() {
		super();
		final Tabuleiro tabuleiro = new Tabuleiro(35, 35, 150);
		final PainelTabuleiro painelTabuleiro = new PainelTabuleiro(tabuleiro);
		add(painelTabuleiro);

		setTitle("Campo Minado");
		setSize(WIDTH1, HEIGHT1);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}

	public static void main(final String[] args) {
		new TelaPrincipal();
	}
}
