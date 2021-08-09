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
		observers.add(observer);
	}

	public void forEach(final Consumer<Campo> function) {
		campos.forEach(function);
	}

	public void restart() {
		campos.forEach(Campo::restart);
		sortearMinas();

	}

	public boolean objectiveAchieved() {
		return campos.stream().allMatch(Campo::objectiveAchieved);
	}

	private void generateField() {
		Campo campo;
		for (int i = 0; i < lines; i++) {
			for (int j = 0; j < columns; j++) {
				campo = Campo.createNewCampo(i, j);
				campo.registerObserver(this);
				campos.add(campo);
			}
		}
	}

	private void associarVizinhos() {
		for (final Campo c1 : campos) {
			for (final Campo c2 : campos) {
				c1.addNeighbour(c2);
			}
		}
	}

	private void sortearMinas() {
		long minasArmadas;
		final Random rand = new Random();
		do {
			minasArmadas = campos.stream().filter(Campo::isMineStatus).count();
			final int aleatorio = (int) (rand.nextDouble() * campos.size());
			final Campo campoAleatorio = campos.get(aleatorio);
			campoAleatorio.mine();
		} while (minasArmadas < mines);
	}

	private void notifyObservers(final boolean result) {
		observers.forEach(o -> o.accept(result));
	}

	private void showMines() {
		campos.stream().filter(Campo::isMineStatus).filter(c -> !c.isMarkStatus()).forEach(c -> c.setOpenStatus(true));
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