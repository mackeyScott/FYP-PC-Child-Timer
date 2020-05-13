package main;

import javafx.application.Application;
import controller.TimerController;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Password;
import view.RootPane3;
//The following is the first class which instantiates on launch.
public class ApplicationLoader extends Application{
	private RootPane3 view;
	private Password model;
	private Scene scene;
	@Override
	public void init() {
		//creates the model and view then passes their references to the controller
		view = new RootPane3();
		model = new Password();
		new TimerController(view, model);
	}
		
	@Override
	public void start(Stage stage) throws Exception {
		//Creates the windows limits and title and applies the view to the window
		stage.setMinWidth(530);
		stage.setMinHeight(500);
		stage.setMaxHeight(500);
		stage.setMaxWidth(530);
		stage.setTitle("Child Timer");
		scene = new Scene(view);
		stage.setScene(scene);
		stage.show();
		
	}
	public static void main(String[] args) {
		//runs on launch
		launch(args);
	}

}
