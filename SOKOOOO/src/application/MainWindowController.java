package application;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.data.Level;
import view.View;

public class MainWindowController extends Observable implements View ,Initializable {
	
	
	private Level level=null;
	private Stage stage;
	private String command;
	private int timerCounter = 0;
	private Timer t;

	
	@FXML
	private SokobanDisplayer sokoDisplayer;
	
	@FXML
	Text stepCount;
	
	@FXML
	Text timer;
	
	//music members
	private String mp3path = "./resorces/sia.mp3";
	private Media mp3 = new Media(new File(mp3path).toURI().toString());
	private MediaPlayer player = new MediaPlayer(mp3);
	
	
	public MainWindowController() {
		
	}
	
	
	public void setCommand(String command) {
		this.command = command;
		
		
		String[] arr = command.split(" ");
		List<String> params = new LinkedList<String>();
		
		for (String s: arr) {
			params.add(s);
		}
		
		setChanged();
		notifyObservers(params);
	}


	public void saveFile(){
		
		if(level!=null){
			
		FileChooser fc=new FileChooser();
		fc.setTitle("save sokoban file");
		fc.setInitialDirectory(new File("./resorces"));
		
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object files", "*.obj"));
		
		File chosen=fc.showSaveDialog(null);
		
		if(chosen!=null){
			
		  List<String> params=new LinkedList<String>();
		  params.add("Save");
		  params.add(chosen.getPath());
		  
		  this.setChanged();
		  this.notifyObservers(params);
		  
		}
	}
}
	

	public void openFile(){
		
		FileChooser fc=new FileChooser();
		fc.setTitle("open sokoban file");
		fc.setInitialDirectory(new File("./resorces"));
		
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files", "*.txt"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files", "*.xml"));
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Object files", "*.obj"));
		
		//fc.setSelectedExtensionFilter(filter);
		File f=fc.showOpenDialog(null);
		if(f!=null){
			
			List<String> params=new LinkedList<>();
			params.add("Load");
			params.add(f.getPath());
			startTimer();
			player.play();
			
			
			this.setChanged();
			this.notifyObservers(params);
			
			
			
		}
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {

		sokoDisplayer.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->sokoDisplayer.requestFocus());
		sokoDisplayer.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(level!=null){
					
					if(event.getCode()==KeyCode.UP)
						setCommand("Move up");
					if(event.getCode()==KeyCode.DOWN)
						setCommand("Move down");
					if(event.getCode()==KeyCode.RIGHT)
						setCommand("Move right");
					if(event.getCode()==KeyCode.LEFT)
						setCommand("Move left");
					if(event.getCode()==KeyCode.ESCAPE)
						setCommand("Exit");
									
				}
				else{
					if(event.getCode()==KeyCode.ESCAPE)
						setCommand("Exit");
				}
				
			}
		});
		
	}


	@Override
	public void displayLevel(Level lvl) {
		
		String temp = String.valueOf(lvl.getCountSteps());
		stepCount.setText(temp);
		
		this.level=lvl;
		sokoDisplayer.setLevel(level);
		sokoDisplayer.redraw();
		
		if(lvl.isWin()){
			t.cancel();
			player.stop();
			System.out.println("level completed");
			sokoDisplayer.drawLevelCompleted();
			this.level = null;
		
			//this.setCommand("Exit");
		}
		
		
		
	}
	
	public void startTimer(){
	      t= new Timer();
	      	t.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					setTimerCounter(getTimerCounter()+1);
					
				}
			}, 0, 1000);
		
	}



	public int getTimerCounter() {
		return timerCounter;
	}


	public void setTimerCounter(int timerCounter) {
		this.timerCounter = timerCounter;
		String temp = String.valueOf(this.timerCounter);
		timer.setText(temp);
		
	}


	@Override
	public void displayError(String msg) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}
	public void stopMusic(){player.stop();}
}
