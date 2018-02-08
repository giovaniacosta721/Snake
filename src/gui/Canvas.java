package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import domain.GameController;
import domain.Mushroom;

/**
 * Very important component of the game. Will display the snake and the list of
 * mushrooms into a JPanel. It will listen for actions performed by the user to
 * control the snake
 * @author gacosta
 *
 */

public class Canvas extends JPanel {

	public GameController gc;
	private BufferedImage buff = null;
	private DirectionListener dListen;
	private final int SIZE = 500;
	private ImageIcon image = new ImageIcon("Images/grass.png");
	
	/**
	 * This method will set the preferences for the look of the panel and 
	 * initialize the passed in gameController object to recieve data and 
	 * instructions from the current snake.
	 * @param gc
	 */
	
	public Canvas(GameController gc) {
		this.setSize(new Dimension(SIZE, SIZE));
		this.setPreferredSize(new Dimension(SIZE, SIZE));
		this.gc = gc;
		
		dListen = new DirectionListener();
		this.addKeyListener(dListen);
		this.setBackground(Color.BLACK);
		
		
	}
	
	//Methods
	
	/**
	 * This method will paint the mushroom list and the snake object containing
	 * the chain of segments, along with the background image of the game. 
	 * Called every timer tick in the mainframe to keep updating the current 
	 * game and essential to see what is happening
	 */
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image.getImage(), 0, 0, null);
		gc.mySnake.draw(g);
		for(Mushroom mush:gc.mushList) {
			mush.draw(g);
		}
		
	}
	
	/**
	 * Will look at the pressed key and determine the steps to take accordingly.
	 * Will change direction of the snake in the event an arrow key is pressed,
	 * or will pause/start the game if p is pressed or quit entirely if x is 
	 * pressed
	 * @param e
	 */
	
	public void onKeyPress(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			gc.mySnake.changeDirections(0, -1);
		}
		else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			gc.mySnake.changeDirections(0, 1);
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			gc.mySnake.changeDirections(-1, 0);
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			gc.mySnake.changeDirections(1, 0);
		}
		else if(e.getKeyCode() == KeyEvent.VK_P) {
			gc.changeTimers();
		}
		else if(e.getKeyCode() == KeyEvent.VK_X) {
			gc.exit();
		}
	}
	
	/**
	 * This class implements the KeyListener and will utilize callback methods
	 * should a key be pressed to determine the course of action to be taken
	 * @author gacosta
	 *
	 */
	
	public class DirectionListener implements KeyListener {
		
		@Override
		public void keyPressed(KeyEvent e) {onKeyPress(e);}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
		@Override
		public void keyTyped(KeyEvent e) {}
		
	}
	
	
	
}
