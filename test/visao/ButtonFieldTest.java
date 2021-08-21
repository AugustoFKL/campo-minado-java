package visao;

import static org.junit.Assert.assertEquals;

import java.awt.event.ComponentEvent;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import modelo.Campo;
import modelo.CampoEvento;

class ButtonFieldTest {

	Campo campo;
	Campo campo2;
	Campo campo3;
	ButtonField field;

	@BeforeEach
	void beforeEach() {
		campo = new Campo(1, 1);
		campo = new Campo(1, 2);
		campo = new Campo(2, 2);

		field = new ButtonField(campo);
	}

	@Test
	void openEventText() {
		field.eventOccurred(campo, CampoEvento.ABRIR);
		assertEquals(ButtonField.getDefaultText(), field.getText());
	}

	@Test
	void openEventColor() {
		field.eventOccurred(campo, CampoEvento.ABRIR);
		assertEquals(ButtonField.getBgDefaultOpen(), field.getBackground());
	}

	@Test
	void markEventText() {
		field.eventOccurred(campo, CampoEvento.MARCAR);
		assertEquals(ButtonField.getMarkedText(), field.getText());
	}

	@Test
	void markEventColor() {
		field.eventOccurred(campo, CampoEvento.MARCAR);
		assertEquals(ButtonField.getBgMarked(), field.getBackground());
	}

	@Test
	void explosionEventText() {
		field.eventOccurred(campo, CampoEvento.EXPLODIR);
		assertEquals(ButtonField.getExplosionText(), field.getText());
	}

	@Test
	void explosionEventColor() {
		field.eventOccurred(campo, CampoEvento.EXPLODIR);
		assertEquals(ButtonField.getBgExplosion(), field.getBackground());
	}

	@Test
	void restartEventText() {
		field.eventOccurred(campo, CampoEvento.REINICIAR);
		assertEquals(ButtonField.getDefaultText(), field.getText());
	}

	@Test
	void restartEventColor() {
		final ButtonField field = new ButtonField(campo);
		field.eventOccurred(campo, CampoEvento.REINICIAR);
		assertEquals(ButtonField.getBgDefault(), field.getBackground());
	}

	@Test
	void markAndUnmarkEventText() {
		field.eventOccurred(campo, CampoEvento.MARCAR);
		field.eventOccurred(campo, CampoEvento.DESMARCAR);
		assertEquals(ButtonField.getDefaultText(), field.getText());
	}

	@Test
	void markAndUnmarkEventColor() {
		field.eventOccurred(campo, CampoEvento.MARCAR);
		field.eventOccurred(campo, CampoEvento.DESMARCAR);
		assertEquals(ButtonField.getBgDefault(), field.getBackground());
	}
	
	@Test
	void openUnmarkedWithMineEventText() {
		final ButtonField field = new ButtonField(campo);
		campo.setMineStatus(true);
		field.eventOccurred(campo, CampoEvento.ABRIR);
		assertEquals(ButtonField.getExplosionText(), field.getText());
	}

	@Test
	void openUnmarkedWithMineEventColor() {
		campo.setMineStatus(true);
		field.eventOccurred(campo, CampoEvento.ABRIR);
		assertEquals(ButtonField.getBgExplosion(), field.getBackground());
	}
	
	@Test
	void testInitialSize(){
		final int expectedSize = (int) (field.getHeight() * 0.9);
		final ComponentEvent componentEvent = new ComponentEvent(field, ComponentEvent.COMPONENT_RESIZED);
		field.componentResized(componentEvent);
		Assertions.assertEquals(expectedSize, field.getFont().getSize());
	}
	
	@Test
	void testResize(){
		final int nextHeight = field.getHeight() * 2;
		
		field.setSize(field.getWidth(), nextHeight);
		final int expectedSize = (int) (nextHeight * 0.9);
		
		final ComponentEvent componentEvent = new ComponentEvent(field, ComponentEvent.COMPONENT_RESIZED);
		field.componentResized(componentEvent);
		
		Assertions.assertEquals(expectedSize, field.getFont().getSize());
	}
}
