package view;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
/*The following is the main window for the application and the views get applied to this window. */
public class RootPane3 extends GridPane{
	private TabPane tabs;
	private ViewMenuBar menuBar;
	private CreateTimerPane timer;
	private CreateLoginPane login;
	public RootPane3() {
		/*Init Method.
		 * -Applies views to window.
		 * -Creates tabs so choosing between views is possible. */
		this.autosize();
		this.setScaleShape(true);
		ColumnConstraints column = new ColumnConstraints();
		column.setPercentWidth(100);
		this.getColumnConstraints().add(column);
		menuBar = new ViewMenuBar();
		timer = new CreateTimerPane();
		login = new CreateLoginPane();
		
		Tab createLoginPane = new Tab("Login Page", login);
		Tab createTimerPane = new Tab("Timer Page", timer);
			
		tabs = new TabPane(createLoginPane, createTimerPane);
		tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		tabs.autosize();
		this.add(tabs, 0, 1);
		this.add(menuBar, 0, 0);
	}
	
	public CreateLoginPane getLoginPane() {
		/*Gets the Login view */
		return login;
	}
	public CreateTimerPane getTimerPane() {
		/*Gets the Timer view */
		return timer;
	}
	public ViewMenuBar getMenuBar() {
		/*Gets the menu bar. */
		return menuBar;
	}
	
	public void changeTab(int index) {
		/*Changes between tabs*/
		tabs.getSelectionModel().select(index);
	}
}


