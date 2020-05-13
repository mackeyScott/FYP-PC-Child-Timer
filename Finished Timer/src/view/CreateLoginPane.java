package view;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
/*The following is the visuals for the login page. */
public class CreateLoginPane extends GridPane{
	public PasswordField password;
	private Button subPass;
	public CreateLoginPane() {
		/*Init method, which adds the visuals to the page.*/
		this.autosize();
		this.setVgap(2);
		this.setScaleShape(true);
		this.setMinHeight(500);
		this.setStyle("-fx-background-color: linear-gradient(#F4F4F4, #CCFFFC); -fx-background-size: stretch;");
		password = new PasswordField();
		subPass = new Button("Submit Password");
		
		Label lblPass = new Label("Enter your Password: ");
		
		this.add(lblPass, 0, 0);
		this.add(password, 0, 1);
		this.add(new HBox(), 0, 2);
		this.add(subPass, 0, 3);
		this.setAlignment(Pos.CENTER);
		this.setTranslateY(-70);
	}
	
	public String getPass() {
		/*Returns the value of the textfield password.*/
		return password.getText();
	}
	public void loginListener(EventHandler<ActionEvent> handler) {
		/*On click do this*/
		subPass.setOnAction(handler);
	}
}
