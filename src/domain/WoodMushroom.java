package domain;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class WoodMushroom extends Mushroom {

	public ImageIcon image = new ImageIcon("Images/Charmander.png");
	
	public WoodMushroom() {
		super();
	}
	
	@Override
	public void whenConsumed(GameController gc, Snake s) {
		s.ignoreTurns();
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) this.getX(), (int) this.getY(), null);
	}

}
