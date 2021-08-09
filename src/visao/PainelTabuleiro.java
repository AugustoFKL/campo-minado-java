package visao;

import modelo.Tabuleiro;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.awt.GridLayout;

public class PainelTabuleiro extends JPanel {

	public static final long serialVersionUID = -6673263667286139373L;

	public PainelTabuleiro(final Tabuleiro tabuleiro) {
		super();
		setLayout(new GridLayout(tabuleiro.getLines(), tabuleiro.getColumns()));

		tabuleiro.forEach(c -> add(new ButtonField(c)));
		SwingUtilities.invokeLater(() -> tabuleiro.registerObserver(e -> {
			if (e.booleanValue()) {
				JOptionPane.showMessageDialog(this, "Ganhou!");
			} else {
				JOptionPane.showMessageDialog(this, "Perdeu.");
			}

			tabuleiro.restart();
		}));

	}
}
