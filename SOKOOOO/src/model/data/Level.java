package model.data;
import java.io.Serializable;
import java.util.*;

import model.object_data.Player;
import model.object_data.Square;
import model.object_data.SquareTarget;

/* contain data of level object */

public class Level implements Serializable {

	private ArrayList<ArrayList<Square>> levelmap=new ArrayList<>();
	private Square player;
	private String levelTitle="";
	private String levelDifficulty="";
	private ArrayList<SquareTarget> st= new ArrayList<>();
	private int numoftargets=0;
	private boolean isWin=false;
	private int countSteps=0;
	
	
	
	
	
	
	
	//methods
	public Level(ArrayList<ArrayList<Square>> levelmap){
		this.levelmap = levelmap;
	}
	
	public Level(){
	}

	public String getLevelTitle() {
		return levelTitle;
	}

	public ArrayList<ArrayList<Square>> getLevelmap() {
		return levelmap;
	}

	public void setLevelmap(ArrayList<ArrayList<Square>> levelmap) {
		this.levelmap = levelmap;
	}

	public void setLevelTitle(String levelTitle) {
		this.levelTitle = levelTitle;
	}

	public String getLevelDifficulty() {
		return levelDifficulty;
	}

	public void setLevelDifficulty(String levelDifficulty) {
		this.levelDifficulty = levelDifficulty;
	}

	
	public String toString(){
		
		String result="";
		for(int i=0;i<levelmap.size();i++){
			for(int j=0;j<levelmap.get(i).size();j++){
				result +=levelmap.get(i).get(j);
			}
			 result += "\n";
		}
		return result;
	}
	
	public Square getPlayer() {
		return player;
	}

	public void setPlayer(Square player) {
		this.player = player;
	}
	
	public Square positionPlayer(){
		for(int i=0;i<levelmap.size();i++){
			for(int j=0;j<levelmap.get(i).size();j++){
				if(levelmap.get(i).get(j).getGameObject()!=null){
				if(levelmap.get(i).get(j).getGameObject().getClass()==new Player().getClass())
					player=levelmap.get(i).get(j);
		     	}
	       	}
         }
	return player;
	}
	
	public boolean checkIfWin(){
		for (SquareTarget l : this.st) {
			if(l.getGameObject() == null)
				return false;
			 if(l.toString() != "@"){
				return false;
			}
		}
		return true;
	}

	public int getNumoftargets() {
		return numoftargets;
	}
	
	
	
	

	public boolean isWin() {
		return isWin;
	}

	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}

	public void setNumoftargets(int numoftargets) {
		this.numoftargets = numoftargets;
	}

	//add square target to the list of targetSquare
	public void addTargets(){ 
		for(int i=0;i<levelmap.size();i++){
			for(int j=0;j<levelmap.get(i).size();j++){
				if(levelmap.get(i).get(j).toString()=="o")
					st.add((SquareTarget) this.levelmap.get(i).get(j));
			}
		}
	}
	
	
	public void countSteps(){
		
		 this.countSteps++;
	}

	public int getCountSteps() {
		return countSteps;
	}
	
	
}
