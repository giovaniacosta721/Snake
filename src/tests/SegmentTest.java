package tests;

import static org.junit.Assert.*;

import javax.swing.ImageIcon;

import org.junit.Test;

import domain.Segment;

/**
 * This class will test the Segment class, though not things like the images 
 * contained within, typically
 * 
 * @author gacosta
 *
 */

public class SegmentTest {

	/**
	 * This method will test the constructor for its proper configurations,
	 * namely the isHead boolean
	 */
	
	@Test
	public void testConstructor() {
		Segment s = new Segment(null, 0, 0);
		assertTrue(s.isHead);
	}
	
	/**
	 * DrawOn cannot rightly be tested without actually seeing its resluts painted
	 */

}
