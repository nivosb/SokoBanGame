package model;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

import controller.MySokobanController;
import model.data.Level;
import model.data.LoadReceiver;
import model.data.SaveReceiver;
import model.policy.MySokobanPolicy;
import model.policy.Policy;

public class MyModel extends Observable implements Model  {
	
	
	MySokobanPolicy policy;
	LoadReceiver loadlevel;
	private SaveReceiver savelevel;
	Level level;
	
	
	
	public MyModel() {

		
		loadlevel=new LoadReceiver();
		savelevel=new SaveReceiver();
		
	}




	@Override
	public Level getLevel() {
		
		return this.level;
	}

	@Override
	public void saveLevel(String filepath) {
		
		savelevel.SaveAction(filepath, level);
		
	}

	@Override
	public void loadlLevel(String filepath) {
		
		Level level = null;
		loadlevel=new LoadReceiver();
		try {
			level= loadlevel.Action(filepath);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.level=level;
		
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("Display");
		this.notifyObservers(params);
		
		
	}

	@Override
	public void exitLevel() {
		System.out.println("exiting...");
		System.exit(1);
		
	}

	@Override
	public void move(String direction) {
		
		this.policy=new MySokobanPolicy(level);
		
		switch(direction){
		case "up":
			policy.moveUp();
			break;
		case "down":
			policy.moveDown();
			break;
		case "right":
			policy.moveRight();
			break;
		case "left":
			policy.moveLeft();
			break;
	}
		if(level.checkIfWin())
			level.setWin(true);
		
		
		this.setChanged();
		List<String> params = new LinkedList<String>();
		params.add("Display");
		this.notifyObservers(params);
		
	
	
	}
}
	
	


