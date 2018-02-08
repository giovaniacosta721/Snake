package domain;

import java.awt.Graphics;

import exceptions.GameOverException;

/**
 * This snake class will handle the chain of segment objects that will compose the 
 * snake object. It maintains a handle on the head and the tail of the snake, handles
 * on the direction in which the snake is traveling, and detects collisions/out of 
 * arena situations for the snake.
 * @author gacosta
 *
 */

public class Snake {

	public Segment head;
	public Segment tail;
	public int xDir;
	public int yDir;
	private int lives;
	private int numSegments;
	private final int MAX_LIVES = 3;
	private final int NUMSEG_START = 2;
	private int ignoreCounter = 0;
	private int snakeTimer = 5;
	private int points;
	private final int THRESHOLD = 5;

	/**
	 * Simple constructor, initializing the chain by creating two initial 
	 * segments with the second having a handle on the first. The basis for the
	 * Segment object chain on which the snake will operate on
	 */
	
	public Snake() {
		head = new Segment(null, 250, 250);
		tail = new Segment(head, 240, 250);

		this.numSegments = NUMSEG_START;		
		this.lives = MAX_LIVES;
		this.points = 0;
		
		this.xDir = 1;
		this.yDir = 0;
	}

	/**
	 * This method will be periodically checked for the head of the snake ONLY
	 * as where the other segments land will be dependent completely on where 
	 * the head been, and this will make sure the game is stopped immediately as
	 *  the snake goes off screen
	 * @return True/False whether the snake is off screen or not (True=Off|False=On)
	 */

	public boolean isOut() {
		if (this.head.x > 490 || this.head.y > 490 || this.head.x < 0 || this.head.y < 0) {return true;}
		else {return false;}
	}

	/**
	 * Check the snake and its segments for collisions with either walls or
	 * itself
	 * @throws GameOverException 
	 */

	public void checkCollisions() throws GameOverException{
		//Check collision with walls
		if(isOut()) {kill();}

		//Check collisions with self
		Segment s = tail;
		while(s.next != null) {
			if(s.intersects(this.head)) {
				kill();
			}
			s = s.next;
		}
	}


	/**
	 * Will add one segment to the end of the snake with a handle on the
	 * previous tail. Will also check on the number of segments, and if 
	 * divisible completely by a THRESHOLD, will add one extra life
	 */

	public void addSegment(){
		Segment s = new Segment(this.tail, tail.x, tail.y);
		this.tail = s;
		this.numSegments++;
		this.points++;
		
		if(this.numSegments % this.THRESHOLD == 0 && this.numSegments != 0) {
			this.lives++;
		}
	}

	/**
	 * Will remove one life from the total lives remaining for the snake and 
	 * reset the game state. If there are no lives left, will end the game 
	 * @throws GameOverException 
	 */

	public void kill() throws GameOverException{
		this.lives--;
		if(lives == 0) {throw new GameOverException();}
		restart();
	}

	/**
	 * This is activated when the snake eats a wood mushroom, causing the snake
	 * to ignore 5 keystroke inputs
	 */

	public void ignoreTurns(){
		this.ignoreCounter += 5 ;
	}

	/**
	 * This will increase the speed of the snake by increasing the rate at which
	 * the GameController Timer goes off, thereby increasing the rate at which
	 * the snake operates. This is done by increasing the snakeTimer value by
	 * one, increasing the speed at which it moves by one extra time per second
	 */

	public void accelerate(){
		this.snakeTimer += 3;
	}

	/**
	 * This method will have the end of the snake copy itself as the segment it
	 * has a handle on, and will instruct the one in front of itself to do the 
	 * same until reaching the head, at which point the head will use the given 
	 * direction vector to change its direction/move
	 * @throws GameOverException 
	 */

	public void move() throws GameOverException{
		Segment s = tail;
		while(s.next != null) {
			s.setX(s.next.x);
			s.setY(s.next.y);
			s = s.next;
		}
		/*
		 * This section, once the while loop is completed, will move only the 
		 * head of the snake
		 */
		this.head.x += (10*xDir);
		this.head.y += (10*yDir);
		checkCollisions();
	}

	/**
	 * Will use the given keystroke to change the xDir and yDir values to 
	 * determine direction in which the snake should travel
	 * @param newX
	 * @param newY
	 */

	public void changeDirections(int newX, int newY){
		if(ignoreCounter != 0) {ignoreCounter--;}
		else {this.xDir = newX; this.yDir = newY;}
	}

	/**
	 * Will reset the snake to be in its original starting position and the 
	 * mushroom list clear to give the user a new snake to work with
	 */

	public void restart() {
		head = new Segment(null, 250, 250);
		tail = new Segment(head, 240, 250);

		this.numSegments = NUMSEG_START;
	}
	
	/**
	 * This method will go through the snake's segments, starting with the tail,
	 * and draw each segment onto the canvas panel in the MainFrame
	 */
	
	public void draw(Graphics g) {
		Segment s = this.tail;
		while(s.next != null) {
			s.drawOn(g);
			s = s.next;
		}
		s.drawOn(g);
	}
	
	//Getters & Setters
	
	public int getSnakeTimer() {return this.snakeTimer;}
	public int getNumSegments() {return this.numSegments;}
	public int getPoints() {return this.points;}
	public int getLives() {return this.lives;}
}
