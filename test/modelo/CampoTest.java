package modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import visao.ButtonField;

class CampoTest {

	Campo campo;
	Campo campo2;
	Campo campo3;
	Campo campo4;
	Campo campo5;
	Campo campo6;
	Campo campo7;
	Campo campo8;
	Campo campo9;
	Campo campo10;
	Campo campo11;

	@BeforeEach
	void beforeEach() {
		campo = Campo.createNewCampo(1, 2);

		campo2 = Campo.createNewCampo(0, 1);
		campo3 = Campo.createNewCampo(0, 2);
		campo4 = Campo.createNewCampo(0, 3);
		campo5 = Campo.createNewCampo(1, 1);
		campo6 = Campo.createNewCampo(1, 3);
		campo7 = Campo.createNewCampo(2, 1);
		campo8 = Campo.createNewCampo(2, 2);
		campo9 = Campo.createNewCampo(2, 3);
		campo10 = Campo.createNewCampo(3, 4);
		campo11 = Campo.createNewCampo(1, 4);

	}

	@Test
	void testGetLine() {
		Assertions.assertEquals(1, campo.getLine());
	}

	@Test
	void testGetColumn() {
		Assertions.assertEquals(2, campo.getColumn());
	}

	@Test
	void testAlterarMarcacao() {
		campo.alternarMarcacao();
		Assertions.assertEquals(true, campo.isMarkStatus());
	}

	@Test
	void testAlterarMarcacao2x() {
		campo.alternarMarcacao();
		campo.alternarMarcacao();
		Assertions.assertEquals(false, campo.isMarkStatus());
	}

	@Test
	void testAlterarMarcacaoAberto() {
		campo.setOpenStatus(true);
		campo.alternarMarcacao();
		Assertions.assertEquals(false, campo.isMarkStatus());
	}

	@Test
	void testStartMineStatus() {
		Assertions.assertEquals(false, campo.isMineStatus());
	}

	@Test
	void testMine() {
		campo.mine();
		Assertions.assertEquals(true, campo.isMineStatus());
	}

	@Test
	void testMine2x() {
		campo.mine();
		campo.mine();
		Assertions.assertEquals(true, campo.isMineStatus());
	}

	@Test
	void testDefaultOpenStatus() {
		Assertions.assertEquals(false, campo.isOpenStatus());
	}

	@Test
	void testSetOpenStatus() {
		campo.setOpenStatus(true);
		Assertions.assertEquals(true, campo.isOpenStatus());
	}

	@Test
	void testSetOpenStatusOpenClose() {
		campo.setOpenStatus(true);
		campo.setOpenStatus(false);
		Assertions.assertEquals(false, campo.isOpenStatus());
	}

	@Test
	void testRestartOpen() {
		campo.setOpenStatus(true);

		campo.restart();
		Assertions.assertEquals(false, campo.isOpenStatus());
	}

	@Test
	void testRestartMined() {
		campo.setMineStatus(true);

		campo.restart();
		Assertions.assertEquals(false, campo.isMineStatus());
	}

	@Test
	void testRestartMarked() {
		campo.setMarkStatus(true);

		campo.restart();
		Assertions.assertEquals(false, campo.isMarkStatus());
	}

	@Test
	void testNoNeighbour() {
		final int neighbours = campo.getNeighbours().size();
		Assertions.assertEquals(0, neighbours);
	}

	@Test
	void testAdd1Neighbour() {
		campo.addNeighbour(campo2);
		final int neighbours = campo.getNeighbours().size();

		Assertions.assertEquals(1, neighbours);
	}

	@Test
	void testAdd2Neighbour() {
		campo.addNeighbour(campo2);
		campo.addNeighbour(campo3);
		final int neighbours = campo.getNeighbours().size();

		Assertions.assertEquals(2, neighbours);
	}

	@Test
	void testAdd8Neighbour() {
		campo.addNeighbour(campo2);
		campo.addNeighbour(campo3);
		campo.addNeighbour(campo4);
		campo.addNeighbour(campo5);
		campo.addNeighbour(campo6);
		campo.addNeighbour(campo7);
		campo.addNeighbour(campo8);
		campo.addNeighbour(campo9);
		final int neighbours = campo.getNeighbours().size();

		Assertions.assertEquals(8, neighbours);
	}

	@Test
	void testAddInvalidNeighbour() {
		campo.addNeighbour(campo10);

		final int neighbours = campo.getNeighbours().size();
		Assertions.assertEquals(0, neighbours);
	}

	@Test
	void testAddInvalidNeighbour2up() {
		campo.addNeighbour(campo11);

		final int neighbours = campo.getNeighbours().size();
		Assertions.assertEquals(0, neighbours);
	}

	@Test
	void testAddItself() {
		campo.addNeighbour(campo);

		final int neighbours = campo.getNeighbours().size();
		Assertions.assertEquals(0, neighbours);
	}

	@Test
	void testMinesInNeighbourhood0() {
		Assertions.assertEquals(0, campo.minesInNeighbourhood());
	}

	@Test
	void testMinesInNeighbourhood1() {
		campo2.setMineStatus(true);
		campo.addNeighbour(campo2);

		final int minedNeighbours = campo.minesInNeighbourhood();
		Assertions.assertEquals(1, minedNeighbours);
	}

	@Test
	void testMinesInNeighbourhood8() {
		campo2.setMineStatus(true);
		campo.addNeighbour(campo2);
		campo3.setMineStatus(true);
		campo.addNeighbour(campo3);
		campo4.setMineStatus(true);
		campo.addNeighbour(campo4);
		campo5.setMineStatus(true);
		campo.addNeighbour(campo5);
		campo6.setMineStatus(true);
		campo.addNeighbour(campo6);
		campo7.setMineStatus(true);
		campo.addNeighbour(campo7);
		campo8.setMineStatus(true);
		campo.addNeighbour(campo8);
		campo9.setMineStatus(true);
		campo.addNeighbour(campo9);

		final int minedNeighbours = campo.minesInNeighbourhood();
		Assertions.assertEquals(8, minedNeighbours);
	}

	@Test
	void testObjectiveAchievedStart() {
		Assertions.assertEquals(false, campo.objectiveAchieved());
	}

	@Test
	void testObjectiveAchievedOpen() {
		campo.setOpenStatus(true);
		Assertions.assertEquals(true, campo.objectiveAchieved());
	}

	@Test
	void testObjectiveAchievedMarked() {
		campo.setMineStatus(true);
		campo.setMarkStatus(true);
		Assertions.assertEquals(true, campo.objectiveAchieved());
	}

	@Test
	void testObjectiveAchievedMinedOpen() {
		campo.setMineStatus(true);
		campo.setOpenStatus(true);
		Assertions.assertEquals(false, campo.objectiveAchieved());
	}
	
	@Test
	void testUnminedOpen() {
		campo.open();
		Assertions.assertEquals(true, campo.isOpenStatus());
	}
	
	@Test
	void testMinedOpen() {
		campo.setMarkStatus(true);
		campo.open();
		Assertions.assertEquals(false, campo.isOpenStatus());
	}
	
	@Test
	void testMarkedOpen() {
		campo.setMineStatus(true);
		campo.open();
		Assertions.assertEquals(true, campo.isOpenStatus());
	}
	
	@Test
	void testOpenMultipleTimes() {
		campo.open();
		campo.open();

		Assertions.assertEquals(true, campo.isOpenStatus());
	}
	
	@Test
	void testOpenWithSafeNeighbour() {
		campo.addNeighbour(campo2);
		campo.open();

		Assertions.assertEquals(true, campo2.isOpenStatus());
	}
	
	@Test
	void testOpenWithUnsafeNeighbour() {
		campo2.setMineStatus(true);
		campo.addNeighbour(campo2);
		campo.open();

		Assertions.assertEquals(false, campo2.isOpenStatus());
	}
	
	@Test
	void testGetObserver() {
		Assertions.assertEquals(0, campo.getObservers().size());
	}
	
	@Test
	void testRegisterAndGetObserver() {
		new ButtonField(campo);
		Assertions.assertEquals(1, campo.getObservers().size());
	}

}
