package domain;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import exceptions.GameOverException;

/**
 * This abstract class will serve as the basis for which all real mushrooms will base
 * their functionality off of. An instance of a "Mushroom" object is never actually
 * called, hence the abstraction.
 * @author gacosta
 *
 */

public abstract class Mushroom extends Rectangle{

	private Random rand = new Random();
	private final int SIZE = 10;
	
	public Mushroom() {
		this.width = SIZE;
		this.height = SIZE;
		this.x = rand.nextInt(49)*10;
		this.y = rand.nextInt(49)*10;
	}
	
	public abstract void whenConsumed(GameController gc, Snake s) throws GameOverException;

	public abstract void draw(Graphics g);
	
}
