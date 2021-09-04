package visao;

import modelo.Tabuleiro;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jdbc.FabricaConexao;
import jdbc.DBGame;
import util.Log;

import java.awt.GridLayout;
import java.sql.SQLException;

public class PainelTabuleiro extends JPanel {

	public static final long serialVersionUID = -6673263667286139373L;

	public PainelTabuleiro(final Tabuleiro tabuleiro) {
		super();
		setLayout(new GridLayout(tabuleiro.getLines(), tabuleiro.getColumns()));

		tabuleiro.forEach(c -> add(new ButtonField(c)));
		tabuleiro.registerObserver(e -> SwingUtilities.invokeLater(() -> {
			final String resultado = e ? "Ganhou" : "Perdeu";
			JOptionPane.showMessageDialog(this, resultado);

			try{
				DBGame.saveGame(tabuleiro, "resultado");
			}catch(final Exception exception){
				Log.logAnon().logSevere("Não foi possível salvar o jogo");
			}
			tabuleiro.restart();
		}));
	}
}
