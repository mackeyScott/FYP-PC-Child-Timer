package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;
/*Creates the Menu bar at the top of page */
public class ViewMenuBar extends MenuBar {
	private MenuItem exit, about, settings, logOut;
	private Menu menuBar;
	public ViewMenuBar(){
		/*Init Method - 
		 * Creates the layout for the page and places items in their place.
		 * Attaches shortcuts to these items.*/
		menuBar = new Menu("File");
		logOut = new MenuItem("LogOut");
		logOut.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		exit = new MenuItem("Exit");
		exit.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));
		about = new MenuItem("About");
		about.setAccelerator(KeyCombination.keyCombination("SHORTCUT+H"));
		settings = new MenuItem("Settings");
		settings.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		
		menuBar.getItems().add(new SeparatorMenuItem());
		menuBar.getItems().add(settings);
		menuBar.getItems().add(logOut);
		menuBar.getItems().add(exit);
		this.getMenus().add(menuBar);
		
		menuBar = new Menu("Help");

		menuBar.getItems().add(about);
		
		this.getMenus().add(menuBar);
		
		
	}
	
	/*the following 4 methods connect the buttons to an "On Click" method. */
	public void LogoutListener(EventHandler<ActionEvent> handler) {
		logOut.setOnAction(handler);
	}
	public void ExitListener(EventHandler<ActionEvent> handler) {
		exit.setOnAction(handler);
	}
	public void AboutListener(EventHandler<ActionEvent> handler) {
		about.setOnAction(handler);
	}
	public void SettingListener(EventHandler<ActionEvent> handler) {
		settings.setOnAction(handler);
	}
}