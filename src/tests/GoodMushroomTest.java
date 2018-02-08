package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.GameController;
import domain.GoodMushroom;
import gui.MainFrame;

/**
 * This test should, since all other mushrooms derive from the same base in
 * much the same manner, test as well the other classes of mushrooms. The 
 * draw() cannot be tested as there is no graphical aspect on which to test it
 * @author gacosta
 *
 */

public class GoodMushroomTest {

	/**
	 * Tests the number of segments from the snake before and after the 
	 * mushroom is consumed, ensuring it calls the right methods
	 */
	
	@Test
	public void testWhenConsumed() {
		GameController gc = new GameController(new MainFrame());
		GoodMushroom gm = new GoodMushroom();
		assertTrue(gc.mySnake.getNumSegments() == 2);
		gm.whenConsumed(gc, gc.mySnake);
		assertTrue(gc.mySnake.getNumSegments() == 3);
	}

}
