package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Campo a ser inserido no tabuleiro.
 *
 * @author Augusto Fotino
 */
public class Campo {

	static Campo createNewCampo(final int line, final int column) {
		return new Campo(line, column);
	}
	private final int column;
	private final int line;
	private boolean markStatus;
	private boolean mineStatus;
	private final List<Campo> neighbours;
	private final List<CampoObservador> observers = new ArrayList<>();

	private boolean openStatus;

	public Campo(final int line, final int column) {
		this.line = line;
		this.column = column;
		neighbours = new ArrayList<>();
	}

	/**
	 * Adiciona um vizinho a determinado campo.
	 *
	 * @param neighbour Vizinho a ser adicionado.
	 */

	/* default */ void addNeighbour(final Campo neighbour) {
		final boolean differentLine = getLine() != neighbour.line;
		final boolean differentColumn = getColumn() != neighbour.column;
		final boolean diagonal = differentLine && differentColumn;

		final int deltaLine = Math.abs(getLine() - neighbour.line);
		final int deltaColumn = Math.abs(getColumn() - neighbour.column);
		final int deltaGeneral = deltaColumn + deltaLine;

		if (deltaGeneral == 1 || deltaGeneral == 2 && diagonal) {
			neighbours.add(neighbour);
		}
	}

	public void alternarMarcacao() {
		if (!openStatus) {
			setMarkStatus(!isMarkStatus());

			if (isMarkStatus()) {
				notifyObservers(CampoEvento.MARCAR);
			} else {
				notifyObservers(CampoEvento.DESMARCAR);
			}
		}
	}

	public int getColumn() {
		return column;
	}

	public int getLine() {
		return line;
	}

	public List<Campo> getNeighbours() {
		return neighbours;
	}

	public List<CampoObservador> getObservers() {
		return observers;
	}

	public boolean isMarkStatus() {
		return markStatus;
	}

	public boolean isMineStatus() {
		return mineStatus;
	}

	public boolean isOpenStatus() {
		return openStatus;
	}

	/* default */ void mine() {
		if (!isMineStatus()) {
			setMineStatus(true);
		}
	}

	public int minesInNeighbourhood() {
		final Stream<Campo> stream = neighbours.stream();
		final Stream<Campo> filteredStream = stream.filter(v -> v.mineStatus);
		return (int) filteredStream.count();
	}

	private void notifyObservers(final CampoEvento event) {
		observers.forEach(o -> o.eventOccurred(this, event));
	}

	public boolean objectiveAchieved() {
		final boolean openedField = !mineStatus && openStatus;
		final boolean protectedField = mineStatus && markStatus;

		return openedField || protectedField;
	}

	public void open() {
		if (!isOpenStatus() && !markStatus) {
			setOpenStatus(true);
			if (mineStatus) {
				notifyObservers(CampoEvento.EXPLODIR);
				return;
			}

			if (safeNeighbourhood()) {
				neighbours.forEach(Campo::open);
			}
		}
	}

	public void registerObserver(final CampoObservador observer) {
		observers.add(observer);
	}

	public void restart() {
		setOpenStatus(false);
		setMineStatus(false);
		setMarkStatus(false);
		notifyObservers(CampoEvento.REINICIAR);
	}

	/* default */ boolean safeNeighbourhood() {
		return neighbours.stream().noneMatch(i -> i.mineStatus);
	}

	public void setMarkStatus(final boolean markStatus) {
		this.markStatus = markStatus;
	}

	public void setMineStatus(final boolean mineStatus) {
		this.mineStatus = mineStatus;
	}

	public void setOpenStatus(final boolean openStatus) {
		this.openStatus = openStatus;

		if (openStatus) {
			notifyObservers(CampoEvento.ABRIR);
		}
	}
}
