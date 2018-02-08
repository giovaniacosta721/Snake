package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import domain.Score;
import domain.ScoreList;

/**
 * Testing the file helper aspect class of the game.
 * @author gacosta
 *
 */

public class ScoreListTest {

	/**
	 * Testing the constructor is skipped, it simply creates a LinkedList of
	 * Score objects. This tests addScore() through comparing this list's size.
	 * Also serves to test the Score object creation, as the test will not run
	 * unless a proper length name is given to it
	 */
	
	@Test
	public void testAddScore() {
		ScoreList sl = new ScoreList();
		assertTrue(sl.scoreList.size() == 0);
		sl.addScore(new Score("DEF", 0, null));
		assertTrue(sl.scoreList.size() == 1);
	}
	
	/**
	 * Tests the checkScore() algorithm in much the same way that the prevoius
	 * test was built. It will add ten default scores with the same scores so
	 * that checkScore may be tested for both case returns
	 */
	
	@Test
	public void testCheckScore() {
		ScoreList sl = new ScoreList();
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		sl.addScore(new Score("DEF", 10, null));
		
		assertFalse(sl.checkScore(0));
		assertTrue(sl.checkScore(11));
	}

}
