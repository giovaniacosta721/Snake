package domain;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class GoodMushroom extends Mushroom {

	public ImageIcon image = new ImageIcon("Images/Pikachu.png");
	
	public GoodMushroom() {
		super();
	}
	
	@Override
	public void whenConsumed(GameController gc, Snake s) {
		s.addSegment();
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(image.getImage(), (int) this.getX(), (int) this.getY(), null);
	}

}
