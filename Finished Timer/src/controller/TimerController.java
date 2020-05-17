package controller;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Password;
import view.CreateLoginPane;
import view.CreateTimerPane;
import view.RootPane3;
import view.ViewMenuBar;
/*The following class controls the entire program meaning it has all the methods which actively change the programs state.
 * Controlling how the program should behave based upon user input. */
public class TimerController {
	private RootPane3 view;
	private Password model;
	private ViewMenuBar menuBar;
	private CreateTimerPane ctp;
	private CreateLoginPane clp;
	public Boolean timerStarted, loggedIn,update;
	public Integer time = 0;
	public int i = 0;
	public Integer targetTime = 0;
	public long initTime = 0;
	public long checkerSave = 0;
	public long currentTime = 0;
	public Integer counter = 0;
	public Thread timeRefresh = new Thread();
	public TimerController(RootPane3 view, Password model) {
		/*The following is the init method for the TimerController class.*/
		this.view = view;
		this.model = model;
		this.loggedIn = false;
		timerStarted = false;
		ctp = view.getTimerPane();
		clp = view.getLoginPane();
		menuBar = view.getMenuBar();
		this.attachEventHandlers();
		ctp.enterTime.setEditable(false);
		ctp.Addition.setEditable(false);
		ctp.Removal.setEditable(false);
		ctp.Add.setDisable(true);
		ctp.Remove.setDisable(true);
		ctp.Start.setDisable(true);
		ctp.Stop.setDisable(true);
		ctp.Add.setOpacity(100);
		ctp.Remove.setOpacity(100);
		ctp.Start.setOpacity(100);
		ctp.Stop.setOpacity(100);
		
	}
	
	
	
	private void attachEventHandlers() {
		/*The following connects the buttons on the View with their respective methods.*/
		menuBar.AboutListener(new AboutHandler());
		menuBar.ExitListener(new ExitHandler());
		menuBar.LogoutListener(new LogOutHandler());
		menuBar.SettingListener(new SettingsHandler());
		clp.loginListener(new SubmitHandler());
		ctp.StartListener(new StartHandler());
		ctp.AddListener(new AddHandler());
		ctp.RemoveListener(new RemoveHandler());
		ctp.StopListener(new StopHandler());
	}
	
	private class AddHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following code checks if the input box isn't empty and checks if the input is an integer and also if the timer is active.
			 * once those pass we add the specified amount from the currently active timer.*/
			System.out.println("Addition");
			if(ctp.Addition.getText().isEmpty() == false & ctp.Addition.getText().matches("[0-9]+")) {
				initTime = ctp.timeStamp.getTimeLong();
				Integer time = 0;
				if(timerStarted == true) {
					time = time.valueOf(ctp.Addition.getText());
					if(((targetTime-initTime)+time) <= 21600) {
						targetTime = targetTime + time;
						System.out.println("Time Added");
					}
					else {
						System.out.println((targetTime+time));
						Alert error = new Alert(AlertType.WARNING, "Number will exceed timer's limit of 6 hours. \nPlease enter a smaller number.");
						error.show();
					}
				}
				else {
					Alert error = new Alert(AlertType.ERROR, "You cannot add time onto the timer, whilst the timer is inactive. \nPlease try again.");
					error.show();
				}
			}
			else {
				Alert error = new Alert(AlertType.ERROR, "Please enter an integer value.");
				error.show();
			}
		}
	}
	
	private class RemoveHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following code checks if the input box isn't empty and checks if the input is an integer and also if the timer is active.
			 * once those pass we take the specified amount from the currently active timer.*/
			if(ctp.Removal.getText().isEmpty() == false & ctp.Removal.getText().matches("[0-9]+")) {
				initTime = ctp.timeStamp.getTimeLong();
				Integer time = 0;
				if(timerStarted == true) {
					time= time.valueOf(ctp.Removal.getText());
					if(((targetTime-initTime)-time) >= 0) {
						targetTime = targetTime - time;
						counter = (int) (targetTime - currentTime);
						checkerSave = counter;
					}
					else {
						Alert error = new Alert(AlertType.WARNING, "Number will exceed timer's lower limit of 0 seconds. \nPlease enter a smaller number.");
						error.show();
					}
					
				}
				else {
					Alert error = new Alert(AlertType.ERROR, "You cannot remove time from the timer, whilst the timer is inactive. \nPlease try again.");
					error.show();
				}
			}
			else {
				Alert error = new Alert(AlertType.ERROR, "Please enter an integer value.");
				error.show();
			}
		}
	}
	
	private class StopHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following checks if the timer is already active if not it will throw a pop-up then it checks if the thread is active and does the same.
			 * once those 2 conditions pass it will tell the thread to stop. Ceasing the threads execution cycle. */
			if(timerStarted == true) {
				if(timeRefresh.isAlive()) {
					timerStarted = false;
				}
			}
			else {
				Alert error = new Alert(AlertType.ERROR, "The timer must be currently active to stop it.");
				error.show();
			}
		}
	}
	
	private class StartHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following code controls the main timer.
			 * By checking if the input for the timer isn't empty and isn't in the alphabet (so only integers are allowed)
			 * we open a new thread and having it sleep every second, we run code to calculate the difference between the time initiated
			 * and the time expected to finish to use as a timer clock which will count down every second, in the beginning of the timer it will wait 5 seconds before starting and runs checks to make sure the calculations are
			 * run correctly at this given point in time the timer skips 1 second on the first run of every instance of the program after that it has no problems running the timer accurately.
			 * 
			 *  Once the timer finishes it will run a batch script which locks the workstation, so that the user knows that their timer has finished.
			 *  We format the string for the timer based on HH:MM:SS to get this we took the long number and divided by 60 for minutes and for seconds we took the long number and modulo'ed it
			 *  by 60.*/
			if(ctp.enterTime.getText().isEmpty() == false & ctp.enterTime.getText().matches("[0-9]+")) {
				initTime = ctp.timeStamp.getTimeLong();
				timerStarted = true;
				String timeFormat = "";
				update = false;
				String input = ctp.enterTime.getText();
				time = time.valueOf(input);
				if(timerStarted == true & time <= 21600 & time >= 60) {
					targetTime = (int) (initTime + time);
					timeRefresh = new Thread(new Runnable(){
						@Override public void run() {
							if(time >= 21600) {
								time = 21600;
							}
							while(timerStarted == true){
								if(i >= 5) {
									update = true;
								}
								currentTime = ctp.timeStamp.getTimeLong();
								counter = (int) (targetTime - currentTime);		
								timer();
								ctp.timeStamp.updateTime();
							}
						}
						public void timer() {
							if(update == true) {
								if(counter != (checkerSave-=1) && (counter > checkerSave) == false) {
									counter = (int) (checkerSave);
								}
								ctp.Timer.setText(timeFormat.format("%02d:%02d:%02d",counter/3600, counter%3600/60, counter%60));
								ctp.enterTime.setText("" + counter);
							}
							else {
								initTime = ctp.timeStamp.getTimeLong();
								targetTime = (int) (initTime + time);
								i+=1;
							}
							if(currentTime >= targetTime) {
								timerStarted = false;
								try {
									Runtime.getRuntime().exec("../Prototype Timer/Lock.bat");
								} catch (IOException e) {
									return;
								}
							}		
							try {
								TimeUnit.SECONDS.sleep(1);
								checkerSave = counter;
							}
							catch (Exception e) {
								return;
							}
							
						}
						});
						timeRefresh.setPriority(10);
						timeRefresh.start();
				}
			}
			else {
				Alert error = new Alert(AlertType.ERROR, "Please enter an integer value, or a larger time.");
				error.show();
			}
		}
			
	}
	
	private class ExitHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			//Literally just exits the program.
			if(timerStarted) {
				try {
					Runtime.getRuntime().exec("../Prototype Timer/Lock.bat");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			Platform.exit();
		}
	}
	
	private class SubmitHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following code controls the login process of the application.
			 * By checking the stored hashed password against the hashed version of what the user has entered.
			 * if they match we set the important fields to editable again.
			 * This is all provided they aren't already logged in.*/
			if(loggedIn == false) {
				Integer hashCheck = Integer.parseInt(model.getPassword());
				if(clp.getPass().hashCode() == hashCheck) {
					loggedIn = true;
					ctp.enterTime.setEditable(true);
					ctp.Addition.setEditable(true);
					ctp.Removal.setEditable(true);
					ctp.Add.setDisable(false);
					ctp.Remove.setDisable(false);
					ctp.Start.setDisable(false);
					ctp.Stop.setDisable(false);
					ctp.Add.setOpacity(100);
					ctp.Remove.setOpacity(100);
					ctp.Start.setOpacity(100);
					ctp.Stop.setOpacity(100);
					view.changeTab(1);
				}
				else {
					Alert error = new Alert(AlertType.ERROR, "Password is incorrect.");
					error.show();
				}
			}
			else {
				Alert error = new Alert(AlertType.ERROR, "You are already logged in.");
				error.show();
			}
		}

	}
	
	private class LogOutHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following controls logging out which will only function if the user is already logged into the application.
			 * By setting the various important fields to uneditable so when the user is logged out the application can no be tampered with. */
			if(loggedIn == true) {
				loggedIn = false;
				ctp.enterTime.setEditable(false);
				ctp.Addition.setEditable(false);
				ctp.Removal.setEditable(false);
				ctp.Add.setDisable(true);
				ctp.Remove.setDisable(true);
				ctp.Start.setDisable(true);
				ctp.Stop.setDisable(true);
				Alert error = new Alert(AlertType.INFORMATION, "Logged out successfully.");
				error.show();
			}
			else {
				Alert error = new Alert(AlertType.ERROR, "You are not logged in.");
				error.show();
			}
		}
	}
	
	private class SettingsHandler implements EventHandler<ActionEvent>{

		public void handle(ActionEvent event) { 
			/*The following function creates a pop-up window which allows the user to access various settings for the application */
			Stage popupwindow = new Stage();
			GridPane layout = new GridPane();
			GridPane layout2 = new GridPane();
			Scene scene1 = new Scene(layout, 300, 250);
			Scene Pass = new Scene(layout2, 300, 250);
			layout.setStyle("-fx-background-color: linear-gradient(#F4F4F4, #CCFFFC); -fx-background-size: stretch;");
			layout2.setStyle("-fx-background-color: linear-gradient(#F4F4F4, #CCFFFC); -fx-background-size: stretch;");
			popupwindow.initModality(Modality.APPLICATION_MODAL);
			popupwindow.setTitle("Settings");  
			popupwindow.setScene(scene1);   
			Integer hashCheck = Integer.parseInt(model.getPassword());
			
			Button exit= new Button("Close Settings");
			exit.setOnAction(e -> popupwindow.close());
			Button changePass = new Button ("Change Password");
			changePass.setOnAction(e -> popupwindow.setScene(Pass));
			Button settings = new Button ("Return to Settings");
			settings.setOnAction(e -> popupwindow.setScene(scene1));

			Label oldPass = new Label("Enter Old Password: ");
			Label newPass = new Label("Enter New Password: ");
			Label newPass2 = new Label("Enter New Password Again: ");
			
			TextField oldElPasso = new TextField();
			TextField newElPasso = new TextField();
			TextField newElPasso2 = new TextField();
			
			Button subPass= new Button("Submit New Password");
			subPass.setOnAction(e -> {
				if(newElPasso.getText().equals(newElPasso2.getText()) && oldElPasso.getText().hashCode() == hashCheck) {
					model.setPassword(newElPasso.getText());
					model.Decrypt();
					Alert error = new Alert(AlertType.INFORMATION, "Password Changed!");
					error.show();
				}
				else {
					Alert error = new Alert(AlertType.ERROR, "Either the old password doesn't match our record OR the new passwords don't match");
					error.show();
				}
			});
			
			layout.add(exit, 0, 1);  
			layout.add(changePass, 0, 0);
			layout.setAlignment(Pos.CENTER);
			
			changePass.setTranslateY(-5);
			
			layout2.add(oldPass, 0, 0);
			layout2.add(oldElPasso, 1, 0);
			layout2.add(newPass, 0, 1);
			layout2.add(newElPasso, 1, 1);
			layout2.add(newPass2, 0, 2);
			layout2.add(newElPasso2, 1, 2);
			layout2.add(settings, 0, 4);
			layout2.add(subPass, 1, 4);
			layout2.setAlignment(Pos.CENTER);
			popupwindow.showAndWait();
			
			
			 
		}

	}
	
	private class AboutHandler implements EventHandler<ActionEvent>{
		public void handle(ActionEvent event) {
			/*The following creates a pop-up which informs the user about the application. */ 
			Alert about = new Alert(AlertType.INFORMATION);
			about.setTitle("About The PC Child Timer");
			about.setHeaderText(null);
			about.setContentText("The PC Child Timer. \n"+
			"The default password for this application is Default1. \n"
			+ "The timer will always skip 1 second for the first launch, I apologise for any inconviences this causes. \n"
			+ "The following application is designed for Parents whom want to keep their children's time on electronic device kept to a minimum. \n"
			+ "However this does not mean it cannot be used for other purposes. Please note that upon changing your password if you forget it you will need a clean install of the application to gain access again. \n"
			+ "I hope you enjoy your time on this application - Scott Mackey");
			about.showAndWait();
		}
	}
	
}