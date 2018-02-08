package domain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * An instance of this class will serve to populate the Segment list contained within 
 * the Snake class. Each instance of this object will serve as a portion of the 
 * Snake's body, and as the number of lives left. Each Segment will maintain a handle
 * on the segment in front of it.
 * 
 * @author gacosta13
 * 
 */

public class Segment extends Rectangle{
	
	//Member Variables
	public ImageIcon image;
	public ImageIcon body = new ImageIcon("Images/body.png");
	public ImageIcon head = new ImageIcon("Images/head.png");
	public Segment next;
	public boolean isHead;
	public final int SIZE = 10;
	
	//Constructor
	/**
	 * This constructor will serve to connect and determine the end points for 
	 * the snake object. It will maintain a handle on the snake segment directly 
	 * in front of it to link together. The next two parameters are the x and y
	 * of the segment, respectively
	 * @param handle
	 */
	
	public Segment(Segment handle, int x, int y) {
		if(handle == null) {
			this.image = head;
			this.isHead = true;
		} else {
			this.image = body;
			this.isHead = false;
		}
		this.next = handle;
		
		this.width = SIZE;
		this.height = SIZE;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * This method is used to draw the snake's body once created by the snake. 
	 * Uses given images to differentiate the head from the body. 
	 * @param g
	 */
	
	public void drawOn(Graphics g) {
		if (isHead){
			g.drawImage(head.getImage(), (int) this.getX(), (int) this.getY(), null);
		} else {
			g.drawImage(body.getImage(), (int) this.getX(), (int) this.getY(), null);
		}
	}	
	
	//Setters & Getters
	public void setX(int x) {this.x = x;}
	public double getX() {return this.x;}
	public void setY(int y) {this.y = y;}
	public double getY() {return this.y;}
}
