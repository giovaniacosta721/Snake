package domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import exceptions.GameOverException;
import exceptions.IlegalNameException;
import gui.MainFrame;

/**
 * This class will provide the majority of the functionality of the game, 
 * including control of the timers that mandate the snake's velocity, the 
 * placement of mushrooms into the world, and the keystroke inputs
 * @author gacosta
 *
 */

public class GameController {

	public List<Mushroom> mushList;
	public Snake mySnake;
	private Timer timeMush;
	private Timer timeSnake;
	private MainFrame mf;
	private boolean stopped;

	private File file = new File("Data/scores.txt");

	public final int MUSH_TIMER_RATE = 2;	//# of times a second the mushroom timer goes off
	private final int PLANT_RATE_MAX= 100;
	public final int PLANT_RATE = 50;	//% chance every timer tick that a mushroom will spawn

	private Random rand = new Random();

	/**
	 * Sets up the timers, the snake, and the list of mushrooms, all of the main
	 * elements of the game. Other classes will refer to these initialized 
	 * objects in this class to check on the state of the game
	 * @param fr
	 */
	
	public GameController(MainFrame fr) {
		mySnake = new Snake();
		mushList = Collections.synchronizedList(new ArrayList<Mushroom>());
		this.mf = fr;
		stopped = false;

		timeMush = new Timer(1000/MUSH_TIMER_RATE, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onMushTimer();
			}

		});
		timeMush.start();

		timeSnake = new Timer(1000/mySnake.getSnakeTimer(), new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onSnakeTimer();
			}

		});		
		timeSnake.start();
	}




	//Methods
	
	/**
	 * This checks for collisions with mushrooms. ONLY the head of the snake is
	 * checked for collision with these, as it is the first point of contact 
	 * with points in the game, and the possibility of mushrooms spawning on 
	 * points already on the snake would make the game not only unfair, but also
	 * slower to operate with increasing lengths
	 * @throws GameOverException
	 */
	
	public void collideMush() throws GameOverException {
		Mushroom tempMush = null;
		for(Mushroom mush : this.mushList) {
			if(mySnake.head.intersects(mush)) {
				mush.whenConsumed(this, this.mySnake);
				tempMush = mush;
			}
		}
		this.mushList.remove(tempMush);
		this.timeSnake.setDelay(1000/mySnake.getSnakeTimer());


	}
	
	/**
	 * This method is called every time the mushroom timer ticks. Should a
	 * random number generated here be above the threshold for planting, then 
	 * a random mushroom will be added to the mushroom list, ready to be scanned
	 * and drawn by the next update timer tick.
	 */

	public void onMushTimer() {
		int check = rand.nextInt(PLANT_RATE_MAX);
		if(check > PLANT_RATE) {
			int mushCheck = rand.nextInt(4);	//4 types of mushrooms
			switch(mushCheck) {
			case(0): mushList.add(new GoodMushroom()); break;
			case(1): mushList.add(new BadMushroom()); break;
			case(2): mushList.add(new WoodMushroom()); break;
			case(3): mushList.add(new WildMushroom()); break;
			}
		}
	}
	
	/**
	 * This method will be called every snake timer tick, which is dependent on
	 * the state of the game and how many times the speedy mushroom was 
	 * collected. It will move the snake and check for collisions, ending the
	 * game should the user die and have no lives left at that tick
	 */

	public void onSnakeTimer() {
		try {
			mySnake.move();
			collideMush();
		} catch (GameOverException e) {
			mf.gameOver();
		}
	}
	
	/**
	 * This method will be called for once in the beginning to give the game a
	 * knowledge of the current high scores. It will maintain this knowledge 
	 * until the final death of the user snake, in which it will use this read
	 * data to compare the user score with the high scores in the list
	 * @return scoreList of top 10 high scores stored in the data.txt file
	 * @throws IOException
	 * @throws NumberFormatException
	 * @throws IlegalNameException
	 */

	public ScoreList readScores() throws IOException, NumberFormatException, IlegalNameException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		ScoreList tempScoreList = new ScoreList();

		String read = br.readLine();
		while(read != null) {
			String[] readArr = read.split(" ");
			tempScoreList.addScore(new Score(readArr[0], Integer.parseInt(readArr[1]), readArr[2]));
			read = br.readLine();
		}

		fr.close();
		br.close();

		return tempScoreList;
	}
	
	/**
	 * This method will be called upon the end of a player game. Should the user
	 * qualify for the list of top 10 scores, then after the data is entered by
	 * the user into the JOptionPane, this will take in the list of scores with 
	 * the newly added score and overwrite the current file with that list
	 * @param sL
	 * @throws IOException
	 */

	public void writeScores(ScoreList sL) throws IOException {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);

		for(Score scr : sL.getScoreList()) {
			String writeString = scr.getName() + " " + scr.getScore() + " " + scr.getDate();
			bw.write(writeString + "\n");
		}

		bw.close();
		fw.close();
	}

	/**
	 * Called by the canvas class to start/stop timers in the case the "P" key
	 * pressed by the user. Utilizes a boolean to determine whether or not the
	 * current game is actually paused. If it is, it will cause the timers to 
	 * start, and vice versa
	 */

	public void changeTimers() {
		if(stopped) {
			mf.startTimers();
			stopped = false;
		}else {
			mf.stopTimers();
			stopped = true;
		}
	}

	/**
	 * This tells the main frame to exit the game
	 */
	public void exit() {
		mf.exit();
		
	}
	
	//Getters
	public Timer getMushTimer() {return this.timeMush;}
	public Timer getSnakeTimer() {return this.timeSnake;}




}

