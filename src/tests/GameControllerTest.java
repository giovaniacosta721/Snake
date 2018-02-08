package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.GameController;
import domain.GoodMushroom;
import exceptions.GameOverException;
import gui.MainFrame;

/**
 * In this class I test the GameController class for proper functionality. In 
 * the methods I don't test, I test by running the program instead, and I choose
 * to skip those methods such as changeTimer() because they are so simple, or 
 * exit() because it is impractical to test
 * @author gacosta
 *
 */

public class GameControllerTest {

	private GameController gc;

	/**
	 * I test the constructor of the class by ensuring the snake is created 
	 * properly
	 */
	
	@Test
	public void testConstructor() {
		gc = new GameController(new MainFrame());
		assertTrue(gc.mySnake.getNumSegments() == 2);
	}

	/**
	 * This test manually places a mushroom in front of the snake to collide into
	 * and tests for their effect of adding another life to the snake
	 * @throws GameOverException
	 */

	@Test
	public void testCollideMush() throws GameOverException {
		gc = new GameController(new MainFrame());
		GoodMushroom testMush = new GoodMushroom();
		testMush.x = gc.mySnake.head.x + 10;
		testMush.x = gc.mySnake.head.y;
		gc.mySnake.changeDirections(1, 0);
		gc.mushList.add(testMush);
		gc.mySnake.move();
		gc.collideMush();
		assertTrue(gc.mySnake.getLives() == 3);
	}

	/**
	 * This runs the mushTimer 100 times, at which point there will certainly be 
	 * more than one mushroom added. This tests to see if mushrooms really are 
	 * added after it is called
	 */

	@Test
	public void testOnMushTimer() {
		gc = new GameController(new MainFrame());
		assertTrue(gc.mushList.size() == 0);
		for(int i = 0; i < 100; i++) {
			gc.onMushTimer();
		}
		assertTrue(gc.mushList.size() != 0);
	}

	/**
	 * This tests the positions of the snake before and after it has gone 
	 * through the onSnakeTimer() method, to ensure they are different
	 */
	
	@Test
	public void testOnSnakeTimer() {
		gc = new GameController(new MainFrame());
		int tempPos = gc.mySnake.head.x;
		assertTrue(gc.mySnake.head.x == tempPos);
		gc.onSnakeTimer();
		assertTrue(gc.mySnake.head.x != tempPos);
	}
}
