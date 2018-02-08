package gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domain.Score;

/**
 * This class is called whenever the user that makes enough points to make it 
 * onto the high score list finishes their current game by losing their last
 * life. It will provide a small prompt for the name and date of the current
 * user to add into the high score list
 * @author gacosta
 *
 */

public class ScoreFrame extends JFrame {

	private final int SIZE = 500;
	private JComboBox combMonth;
	private JComboBox combDay;
	private JComboBox combYear;
	private JTextField txtName;
	private JButton btnEnter;
	private String[] userData;
	private int score;
	private MainFrame mf;
	private boolean ready;
	
	/**
	 * Adding the functionality to the dialogue. Will provide combo boxes for 
	 * accurate entry of data for the date and a simple text field for the name,
	 * whose contents will be monitored and checked for validity elsewhere
	 * @param mf
	 * @param score
	 */
	
	public ScoreFrame(MainFrame mf, int score) {
		this.score = score;
		this.mf = mf;
		this.ready = false; //denotes when the panel is ok to harvest data from
		//Added to the content pane after all components are placed on it
		JPanel pnlMain = new JPanel();
		
		//Northern decoration panel
		JPanel pnlNorth = new JPanel();
		
		//Center Prompts panel
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
		JLabel lblPrompts = new JLabel();
		lblPrompts.add(new JLabel("Initials:"));
		lblPrompts.add(new JLabel("Date:"));
		lblPrompts.add(new JLabel("Score:"));
		
		JLabel lblFields = new JLabel();

		String[] combMonthEntries = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		String[] combDayEntries = new String[31];
		for (int i =0; i <31; i++){
			combDayEntries[i] = i+1 +"";
		}
		String[] combYearEntries = new String[15];
		for (int i =0; i <15; i++){
			combYearEntries[i] = i+2004 +"";
		}
		
		txtName = new JTextField();
		combMonth = new JComboBox(combMonthEntries);
		combDay = new JComboBox(combDayEntries);
		combYear = new JComboBox(combYearEntries);
		JLabel lblScore = new JLabel(""+score);
		btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				harvestData();
				
			}
			
		});
		pnlCenter.add(txtName);
		pnlCenter.add(combMonth);
		pnlCenter.add(combDay);
		pnlCenter.add(combYear);
		pnlCenter.add(lblScore);
		
		pnlCenter.add(lblPrompts);
		pnlCenter.add(lblFields);
		pnlCenter.add(btnEnter);
		//East and West Decoration Panels
		JPanel pnlEast = new JPanel();
		pnlEast.add(new JLabel(new ImageIcon ("Images/pikachuWest.png")));
		
		JPanel pnlWest = new JPanel();
		pnlWest.add(new JLabel(new ImageIcon ("Images/pikachuEast.png")));
		
		//Setting up the main panel and adding it to the frame
		pnlMain.setLayout(new BorderLayout(0, 0));
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		JLabel lblPrompt = new JLabel("You've made a High Score!",new ImageIcon ("Images/congratulations.png"),  JLabel.CENTER);
		pnlNorth.add(lblPrompt);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		pnlMain.add(pnlWest, BorderLayout.WEST);
		
		//Setting up the frame for presentation
		this.getContentPane().add(pnlMain);
		this.pack();
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	/**
	 * Collects the data from the text fields and enters them into an array
	 * ready to be collected into a Score object. Upon closing, will dispose of
	 * itself
	 */
	
	protected void harvestData() {
		userData = new String[3];
		if (txtName.getText()!= null && txtName.getText().length() == 3){
			userData[0]=txtName.getText();
		} else {
			userData[0]="DEF";
		}
		
		userData[1] = combMonth.getSelectedItem() +"/"+ combDay.getSelectedItem() +"/"+  combYear.getSelectedItem(); 
		this.setVisible(false);
		mf.ready(userData);
		this.dispose();
	}

	/**
	 * Will return a true boolean to signify the data is ready to be collected
	 * @return Boolean true
	 */
	
	public boolean isReady() {return ready;}

	//Getters
	public String[] getUserData() {return userData;}
	

	
}
