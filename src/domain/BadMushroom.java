package domain;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import exceptions.GameOverException;

public class BadMushroom extends Mushroom {

	public ImageIcon image = new ImageIcon("Images/Squirtle.png");
	
	public BadMushroom() {
		super();
	}
	
	@Override
	public void whenConsumed(GameController gc, Snake s) throws GameOverException {
		s.kill();
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) this.getX(), (int) this.getY(), null);
	}

}
