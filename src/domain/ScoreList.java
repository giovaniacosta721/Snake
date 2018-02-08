package domain;

import java.util.LinkedList;

/**
 * The purpose of this class is to provide an easy-to-reference list of Score
 * objects read in from the scores.txt file. It is a LinkedList to provide 
 * insertion/deletion and replacement capabilities in the case that the current 
 * list is/is not of size 10
 * @author gacosta
 *
 */

public class ScoreList {

	public LinkedList<Score> scoreList;

	public ScoreList() {
		this.scoreList = new LinkedList<Score>();
	}

	/**
	 * This method will go through the list of scores read from the file and, if
	 * the current score is high enough, place that score in between the 
	 * appropriate elements. Should it not be high enough, however, it will place
	 * the score at the end of the list. If the list is then larger than 10, it
	 * will remove the last element to make it of size 10
	 * @param scr
	 */
	
	public void addScore(Score scr) {
		boolean inserted = false;
		
		for(int i = 0; i < scoreList.size(); i++) {
			if(scoreList.get(i).getScore() <= scr.getScore()) {
				scoreList.add(i, scr);
				inserted = true;
				break;
			}
		}
		if (!inserted){scoreList.add(scr);}
		
		if (scoreList.size() > 10) {scoreList.removeLast();}
	}
	
	/**
	 * This method checks the int passed in to see if it qualifies as a high
	 * score. If so returns true, else returns false.
	 * @param score
	 * @return
	 */
	
	public boolean checkScore(int score){
		boolean qualifies = false;
		for(Score tempScore : scoreList) {
			if(score > tempScore.getScore()) {qualifies = true;}
		}
		return qualifies;
	}
	
	//Getters
	public LinkedList<Score> getScoreList() {
		return this.scoreList;
	}
	
	public String toString(){
		String str="";
		for (Score ele: scoreList){
			str += ele.toFileString() + "\n";
		}
		return str;
	}
	
}
