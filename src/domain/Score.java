package domain;

import exceptions.IlegalNameException;

/**
 * This class is used to maintain an instance of a new playerscore upon the 
 * ending of a game. Maintains all of the information in a single line in the
 * scores.txt file and provides simple methods to format this information into
 * usable Strings
 * @author gacosta
 *
 */

public class Score {

	private String name;
	private int score;
	private String date;

	/**
	 * Will take in the given parameters and initialize them. For the name, the
	 * input String must be of size 3 without strings. If it does not, it will
	 * ignore the String and the class that calls this will pass in a default 
	 * name of DEF. The check in this method is to ensure compliance
	 * @param name
	 * @param score
	 * @param date
	 */
	
	public Score(String name, int score, String date) {
		if(name.trim().length() == 3) {this.name = name.trim();}
		this.score = score;
		this.date = date;
	}
	
	/**
	 * This method will format the current object into the appropriate String
	 * format appropriate for file reading 
	 * @return String version of a score formatted for writing
	 */

	public String toFileString() {
		return name + " " + score + " " + date;
	}
	
	//Getters
	public String getName() {return this.name;}
	public int getScore() {return this.score;}
	public String getDate() {return this.date;}
}
