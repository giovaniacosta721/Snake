package domain;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class WildMushroom extends Mushroom {

	public ImageIcon image = new ImageIcon("Images/Bulbasaur.png");
	
	public WildMushroom() {
		super();
	}
	
	@Override
	public void whenConsumed(GameController gc, Snake s) {
		s.accelerate();
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) this.getX(), (int) this.getY(), null);
	}

}
