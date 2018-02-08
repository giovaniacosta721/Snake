package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Snake;
import exceptions.GameOverException;

/**
 * I test the snake class here and ensure it responds properly to its methods 
 * and that it is configured properly when called
 * @author gacosta
 *
 */

public class SnakeTest {

	
	private Snake s;
	
	/**
	 * Testing the constructor is a matter of checking its initialized 
	 * variables, very simple
	 */
	
	@Test
	public void testConstructor() {
		s = new Snake();
		assertTrue(s.head.x == 250 && s.head.y == 250);
		assertTrue(s.tail.x == 240 && s.tail.y == 250);
		assertTrue(s.getLives() == 3);
		assertTrue(s.getPoints() == 0);
	}
	
	/**
	 * Checking the isOut algorithm is fairly simple as well, only checking to see
	 * that a snake head manually placed out of bounds of the world will cause the
	 * method to return true 
	 */

	@Test
	public void testIsOut() {
		s = new Snake();
		s.head.x = 500;
		assertTrue(s.isOut());
	}
	
	/**
	 * This method checks for the functionality of the addSegment() method by 
	 * comparing the length of the snake prior to the method being called and 
	 * after the fact
	 */
	
	@Test
	public void testaddSegment() {
		s = new Snake();
		int tempSize = s.getNumSegments();
		s.addSegment();
		assertFalse(s.getNumSegments() == tempSize);
	}
	
	/**
	 * Testing the kill() algorithm by checking the number of lives before and
	 * after it is called
	 * @throws GameOverException 
	 */
	
	@Test
	public void testKill() throws GameOverException {
		s = new Snake();
		int tempLives = s.getLives();
		s.kill();
		assertFalse(s.getLives() == tempLives);
	}
	
	/**IGNORING ACCELERATE() & IGNORETURNS(): they are very simple and 
	 * shouldn't have any problems passing
	 */
	
	/**
	 * Testing the move will be done by comparing the initial positions 
	 * of the head with the final
	 * @throws GameOverException 
	 */
	
	@Test
	public void testMove() throws GameOverException {
		s = new Snake();
		int[] tempPos = {s.head.x, s.head.y};
		s.move();
		assertFalse(tempPos[0] == s.head.x && tempPos[1] == s.head.y);
	}
	
	/**CHANGEDIRECTIONS IGNORED: it is basically  getter method, unecessary to test*/
	
	/**
	 * This will test the restart() test in resetting the position of 
	 * the snake
	 * @throws GameOverException 
	 */
	
	@Test
	public void testRestart() throws GameOverException {
		s = new Snake();
		int[] tempPos = {s.head.x, s.head.y};
		s.move();
		assertFalse(tempPos[0] == s.head.x && tempPos[1] == s.head.y);
		s.restart();
		assertTrue(tempPos[0] == s.head.x && tempPos[1] == s.head.y);
	}
}
