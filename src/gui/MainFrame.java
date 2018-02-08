
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import domain.GameController;
import domain.Score;
import domain.ScoreList;
import exceptions.IlegalNameException;

/**
 * This class is the basis of the Snake UI. It contains an instance of the canvas class
 * in which the snake will be drawn and operate from and manages the different win/lose
 * scenarios for the game.
 * @author gacosta
 *
 */

public class MainFrame extends JFrame {
	private Canvas canvas;
	private GameController gc;
	private Timer timeUpdate;
	private ScoreList sL;
	private JLabel lblScoreLives;

	public MainFrame() {
		gc = new GameController(this);
		canvas = new Canvas(gc);
		canvas.setFocusable(true);
		canvas.setRequestFocusEnabled(true);

		JPanel pnlMain = new JPanel();	//Panel on which all other panels will rest
		pnlMain.setLayout(new BorderLayout());
		pnlMain.setBackground(Color.black);

		/*
		 * The Northern panel, which will contain a simple statistics reading of the game
		 * in progress as the snake snake eats, dies, etc. 
		 */
		JPanel pnlNorth = new JPanel();
		lblScoreLives = new JLabel("Current Score: " + gc.mySnake.getPoints() + " Lives: " + gc.mySnake.getLives());
		pnlNorth.add(lblScoreLives);

		/*
		 * The East panel consists of simple ImageIcons and labels to provide a 
		 * reference to the players should they need to find out what each character
		 * popping up on the screen do
		 */
		JPanel pnlEast = new JPanel();
		pnlEast.setLayout(new BoxLayout(pnlEast, BoxLayout.Y_AXIS));

		pnlEast.add(new JLabel("Legend", JLabel.CENTER));
		pnlEast.add(new JLabel("Health! +1 Pokemon!",new ImageIcon ("Images/PikachuLegend.png"),  JLabel.CENTER));
		pnlEast.add(new JLabel("-1 Life! KO's Trainer!",new ImageIcon ("Images/SquirtleLegend.png"),  JLabel.CENTER));
		pnlEast.add(new JLabel("+1 Stubborn! Lose 5 turns!",new ImageIcon ("Images/CharmanderLegend.png"),  JLabel.CENTER));
		pnlEast.add(new JLabel("Watch Out! Super speed!",new ImageIcon ("Images/BulbasaurLegend.png"),  JLabel.CENTER));

		JButton btnHelp = new JButton("Help!");
		btnHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onBtnHelp();
			}
		});
		pnlEast.add(btnHelp);

		/*
		 * The center panel, the most important of the gui. Simply creates an instance 
		 * of a Canvas object on which all of the graphical snake operations are 
		 * performed
		 */
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(canvas);

		//Adding secondary panels to the main
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);


		//Reading the scores file in!
		try {
			this.sL = gc.readScores();
		} catch (NumberFormatException e1) {
			System.out.println("Shouldn't ever happen!");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("File reading went wrong!");
			e1.printStackTrace();
		} catch (IlegalNameException e1) {
			System.out.println("Shouldn't Happen! Name error!");
		}

		/*
		 * Setting up the update timer. This goes off 12 times/second to ensure that
		 * the rate at which the snake moves is mostly fluid, provided it doesn't get 
		 * too fast
		 */
		timeUpdate = new Timer(1000/12, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onTimeUpdate();
			}

		});		
		timeUpdate.start();



		//Setting up the Frame for final presentation
		this.getContentPane().add(pnlMain);
		this.pack();

		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	//Methods

	/**
	 * Will update the canvas panel (12 times a second) and update the North panel's
	 * information by re-setting the text
	 */
	
	protected void onTimeUpdate() {
		canvas.repaint();
		lblScoreLives.setText("Current Score: " + gc.mySnake.getPoints() + " Lives: " + gc.mySnake.getLives());		
	}

	/**
	 * This method is called upon the user pressing the Help button below the legend.
	 * This will display a simple JOptionPane with instructions as to what keyboard
	 * buttons to press in order to get the game to pause/play or quit
	 */

	private void onBtnHelp() {
		stopTimers();
		JOptionPane.showMessageDialog(this,
				" Press \"P\" to start/stop game" + "/n" + "Press \"X\" to quit game",
				"Help Menu",
				JOptionPane.INFORMATION_MESSAGE);
		canvas.requestFocus();
		startTimers();
	}
	
	/**
	 * Will pause the current game and read in the scores. Should the players' current
	 * score qualify for the top 10 scores, will display and prompt for the users' name
	 * and current date. After receiving this information, the game will collect and 
	 * format it correctly and write it into the appropriate file before showing the
	 * list of high scores
	 */
	
	public void gameOver() {
		stopTimers();
		int currScore = this.gc.mySnake.getPoints();
		if(sL.checkScore(currScore)) {
			ScoreFrame sf = new ScoreFrame(this, currScore);
		}else {
			JOptionPane.showMessageDialog(this,
					"Game Over!" + "\n" + "No High Score this time :(",
					"Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * Stops ALL 3 timers in the game. Called when pausing is necessary, such as the end
	 * of a game
	 */
	
	public void stopTimers() {
		this.timeUpdate.stop();
		gc.getMushTimer().stop();
		gc.getSnakeTimer().stop();
	}
	
	/**
	 * Starts the timers back up again, called in cases where the game needs to be
	 * resumed after pausing, such as on the "p" keystroke 
	 */
	
	public void startTimers() {
		this.timeUpdate.start();
		gc.getMushTimer().start();
		gc.getSnakeTimer().start();
	}
	
	/**
	 * This method will perform the writing of the players' score into the appropriate
	 * scoreList and then writing that data onto the file. Will then display the score
	 * board for the player, which will close the game upon closing
	 * @param userData
	 */
	
	public void ready(String[] userData) {
		Score cur = new Score(userData[0], this.gc.mySnake.getPoints(), userData[1]);
		this.sL.addScore(cur);
		try {
			gc.writeScores(sL);
			
			JOptionPane.showMessageDialog(this,
					sL,
					"Score board",
					JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Displays a simple JDialogue prompting the user for verification in closing the 
	 * current game state
	 */

	public void exit() {
		int n = JOptionPane.showConfirmDialog(
				this,
				"Are you sure you want to quit?",
				"An Inane Question",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.NO_OPTION){
		} else {
			System.exit(0);
		}
		
	}
	
	//Main: Creates the actual frame...
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
	}



}
