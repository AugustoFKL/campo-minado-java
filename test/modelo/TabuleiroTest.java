package modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TabuleiroTest {

	Tabuleiro tabuleiro;

	@BeforeEach
	void beforeEach() {
		tabuleiro = new Tabuleiro(10, 15, 20);
	}

	@Test
	void testGetMines() {
		Assertions.assertEquals(20, tabuleiro.getMines());
	}

	@Test
	void testGetCampos() {
		Assertions.assertEquals(150, tabuleiro.getCampos().size());
	}

	@Test
	void testGetLines() {
		Assertions.assertEquals(10, tabuleiro.getLines());
	}

	@Test
	void testGetColumns() {
		Assertions.assertEquals(15, tabuleiro.getColumns());
	}

	@Test
	void testRegisterObserver() {
		tabuleiro.registerObserver(e -> e.booleanValue());
		Assertions.assertEquals(1, tabuleiro.getObservers().size());
	}

	@Test
	void testForEachOpen() {
		tabuleiro.forEach(i -> i.setOpenStatus(true));
		Assertions.assertTrue(tabuleiro.getCampos().stream().allMatch(i -> i.isOpenStatus()));
		;
	}

	@Test
	void testForEachMark() {
		tabuleiro.forEach(i -> i.alternarMarcacao());
		Assertions.assertTrue(tabuleiro.getCampos().stream().allMatch(i -> i.isMarkStatus()));
		;
	}

	@Test
	void testForEachMine() {
		tabuleiro.forEach(i -> i.setMineStatus(true));
		Assertions.assertTrue(tabuleiro.getCampos().stream().allMatch(i -> i.isMineStatus()));
		;
	}

	@Test
	void testRestart() {
		tabuleiro.forEach(i -> i.setOpenStatus(true));
		tabuleiro.restart();
		Assertions.assertTrue(tabuleiro.getCampos().stream().noneMatch(i -> i.isOpenStatus()));
		;
	}

	@Test
	void testObjectiveAchieved() {
		tabuleiro.forEach(i -> {
			if (i.isMineStatus()) {
				i.setMarkStatus(true);
			} else {
				i.setOpenStatus(true);
			}
		});

		Assertions.assertTrue(tabuleiro.objectiveAchieved());
	}

	@Test
	void testEventOccurredMined() {
		tabuleiro.eventOccurred(tabuleiro.getCampos().get(0), CampoEvento.EXPLODIR);
		Assertions.assertTrue(
				tabuleiro.getCampos().stream().filter(i -> i.isMineStatus()).allMatch(i -> i.isOpenStatus()));
	}

}
