package modelo;

@FunctionalInterface
public interface CampoObservador {

	void eventOccurred(final Campo campo, final CampoEvento evento);
}
