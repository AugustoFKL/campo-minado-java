package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tabuleiro implements CampoObservador {

	private final int lines;
	private final int columns;
	private final int mines;
	private final List<Campo> campos = new ArrayList<>();
	private final List<Consumer<Boolean>> observers = new ArrayList<>();
	final Random rand = new Random();

	public Tabuleiro(final int lines, final int columns, final int mines) {
		this.lines = lines;
		this.columns = columns;
		this.mines = mines;

		generateField();
		associarVizinhos();
		sortearMinas();
	}

	public int getMines() {
		return mines;
	}

	public List<Campo> getCampos() {
		return campos;
	}

	public List<Consumer<Boolean>> getObservers() {
		return observers;
	}

	public int getLines() {
		return lines;
	}

	public int getColumns() {
		return columns;
	}

	public void registerObserver(final Consumer<Boolean> observer) {
		getObservers().add(observer);
	}

	public void forEach(final Consumer<Campo> function) {
		getCampos().forEach(function);
	}

	public void restart() {
		getCampos().forEach(Campo::restart);
		sortearMinas();

	}

	public boolean objectiveAchieved() {
		return getCampos().stream().allMatch(Campo::objectiveAchieved);
	}

	private void generateField() {
		Campo campo;
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				campo = Campo.createNewCampo(i, j);
				campo.registerObserver(this);
				getCampos().add(campo);
			}
		}
	}

	private void associarVizinhos() {
		for (final Campo c1 : getCampos()) {
			for (final Campo c2 : getCampos()) {
				c1.addNeighbour(c2);
			}
		}
	}

	private void sortearMinas() {
		long minasArmadas;
		do {
			minasArmadas = getCampos().stream().filter(Campo::isMineStatus).count();
			final int aleatorio = (int) (rand.nextDouble() * getCampos().size());
			final Campo campoAleatorio = getCampos().get(aleatorio);
			campoAleatorio.mine();
		} while (minasArmadas < getMines() - 1);
	}

	private void notifyObservers(final boolean result) {
		getObservers().forEach(o -> o.accept(result));
	}

	private void showMines() {
		getCampos().stream().filter(Campo::isMineStatus).filter(c -> !c.isMarkStatus()).forEach(c -> c.setOpenStatus(true));
	}

	@Override
	public void eventOccurred(final Campo campo, final CampoEvento event) {
		final Logger log = Logger.getLogger(Tabuleiro.class.getName());
		if (event == CampoEvento.EXPLODIR) {
			log.log(Level.INFO, "You lose...");
			showMines();
			notifyObservers(false);
		} else if (objectiveAchieved()) {
			log.log(Level.INFO, "You win...");
			notifyObservers(true);
		}
	}
}