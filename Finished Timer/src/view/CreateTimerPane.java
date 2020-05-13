package view;

import controller.TimerController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import model.TimeStamp;
/*The following is the view for the timer page. */
public class CreateTimerPane extends GridPane{
	public Label timerLabel, addLabel, removeLabel, enterLabel;
	public TextField Timer, Addition, Removal, enterTime;
	public TimeStamp timeStamp;
	public Button Add, Remove, Start, Stop;
	public CreateTimerPane() {
		/*Init Method*/
		//Defining pane settings
		this.autosize();
		this.setVgap(2);
		this.setMinHeight(500);
		this.setScaleShape(true);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-color: linear-gradient(#F4F4F4, #CCFFFC); -fx-background-size: stretch;");
		this.setTranslateY(-70);
		this.setPadding(new Insets(5,5,5,5));
		//Setting up the actual timer
		timeStamp = new TimeStamp();
		timerLabel = new Label("Timer: ");
		Timer = new TextField();
		Timer.setText("00:00:00");
		Timer.setEditable(false);
		enterLabel = new Label("Enter Time (Seconds): ");
		enterTime = new TextField();

		//Setting up the adding/removing of time.
		addLabel = new Label("Add Time: ");
		removeLabel = new Label("Remove Time: ");
		Addition = new TextField();
		Removal = new TextField();
		
		//Setting up the Buttons for the page.
		Add = new Button("Add Time");
		Remove = new Button("Remove Time");
		Start = new Button("Start");
		Stop = new Button("Stop");
		
		
		//Adding Objects to Pane
		this.add(addLabel, 1, 10);
		this.add(Addition, 1,11);
		this.add(new HBox(), 1, 12);
		this.add(Add, 1, 12);
		
		this.add(timerLabel, 2, 0);
		this.add(Timer, 2, 1);
		
		this.add(removeLabel, 3, 10);
		this.add(Removal, 3, 11);
		this.add(new HBox(), 3,12);
		this.add(Remove, 3, 12);
		
		this.add(enterLabel, 2, 10);
		this.add(enterTime, 2, 11);
		this.add(new HBox(), 2, 12);
		this.add(new HBox(), 2,12);
		this.add(Start, 2, 12);
		this.add(Stop, 2, 12);
		
		Timer.setMinHeight(100);
		Timer.setMaxWidth(190);
		Timer.setStyle("-fx-font-size: 4em; text-align: center;");
		Start.setTranslateX(50);
		Remove.setTranslateX(30);
		Stop.setTranslateX(110);
		
	}

	/*The following 4 methods connect the buttons to "On click" do what's passed through the method. */
	public void StartListener(EventHandler<ActionEvent> handler) {
		Start.setOnAction(handler);
	}
	
	public void AddListener(EventHandler<ActionEvent> handler) {
		Add.setOnAction(handler);
	}
	
	public void RemoveListener(EventHandler<ActionEvent> handler) {
		Remove.setOnAction(handler);
	}
	
	public void StopListener(EventHandler<ActionEvent> handler) {
		Stop.setOnAction(handler);
	}

}
