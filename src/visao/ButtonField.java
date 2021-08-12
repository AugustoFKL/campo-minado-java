package visao;

import modelo.Campo;
import modelo.CampoEvento;
import modelo.CampoObservador;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class ButtonField extends JButton implements CampoObservador, MouseListener, ComponentListener {

	public static final long serialVersionUID = 8933602135436682102L;

	private static final Color BG_DEFAULT = new Color(184, 184, 184);
	private static final Color BG_DEFAULT_OPEN = new Color(150, 150, 150);
	private static final Color BG_MARKED = new Color(8, 179, 247);
	private static final Color BG_EXPLOSION = new Color(189, 66, 68);
	private final transient Campo field;
	
	private static final String FONT_TYPE = Font.SANS_SERIF;
	private static final int FONT_FORM = Font.BOLD; 
	private static final int DEFAULT_SIZE = 25;
	private final int numberOfLines;
	
	public Campo getField() {
		return field;
	}

	public ButtonField(final Campo field, final int lines) {
		super();
		this.field = field;
		this.numberOfLines = lines;
		setBorder(BorderFactory.createBevelBorder(0));
		setFont(new Font(FONT_TYPE, FONT_FORM, DEFAULT_SIZE));
		setBackground(BG_DEFAULT);

		addMouseListener(this);
		addComponentListener(this);
		field.registerObserver(this);
	}

	@Override
	public void eventOccurred(final Campo campo, final CampoEvento event) {

		if (field.isMineStatus() && !field.isMarkStatus() && event == CampoEvento.ABRIR) {
			applyExplosionStyle();
			return;
		}
		switch (event) {
		case ABRIR:
			applyOpenStyle();
			break;
		case MARCAR:
			applyMarkedStyle();
			break;
		case EXPLODIR:
			applyExplosionStyle();
			break;
		case REINICIAR:
		case DESMARCAR:
		default:
			applyUnmarkedStyle();
			break;
		}
	}

	private void applyExplosionStyle() {
		setBackground(BG_EXPLOSION);
		setText("X");
	}

	private void applyMarkedStyle() {
		setBackground(BG_MARKED);
		setText("M");
	}

	private void applyUnmarkedStyle() {
		setBackground(BG_DEFAULT);
		setText("");
	}

	private void applyOpenStyle() {
		setBackground(BG_DEFAULT_OPEN);
		setForeground(Color.BLACK);
		if (field.minesInNeighbourhood() != 0) {
			setText(String.valueOf(field.minesInNeighbourhood()));
		}
	}

	@Override
	public void mousePressed(final MouseEvent mouseEvent) {
		if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
			field.open();
		} else {
			field.alternarMarcacao();
		}
	}

	/**
	 * Método não utilizado atualmente.
	 * 
	 * @param mouseEvent Evento recebid
	 */
	@Override
	public void mouseClicked(final MouseEvent mouseEvent) {
		// Não utilizado
	}

	@Override
	public void mouseReleased(final MouseEvent mouseEvent) {
		// Não utilizado
	}

	@Override
	public void mouseEntered(final MouseEvent mouseEvent) {
		// Não utilizado
	}

	@Override
	public void mouseExited(final MouseEvent mouseEvent) {
		// Não utilizado
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {
		//Não utilizado
		
	}

	@Override
	public void componentMoved(ComponentEvent arg0) {
		//Não utilizado
		
	}

	@Override
	public void componentResized(final ComponentEvent componentEvent) {
		final int height = this.getHeight();
		this.setFont(new Font(FONT_TYPE, FONT_FORM, DEFAULT_SIZE * height/numberOfLines));
		getRootPane().revalidate();
	}

	@Override
	public void componentShown(ComponentEvent arg0) {
		//Não utilizado
		
	}
}
