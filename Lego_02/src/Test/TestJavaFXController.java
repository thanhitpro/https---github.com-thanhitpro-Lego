package Test;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class TestJavaFXController {
	@FXML
	private MenuItem menuItemNew;
	
	@FXML
	private MenuItem menuItemOpen;
	
	@FXML
	private MenuItem menuItemSave;
	
	@FXML
	private MenuItem menuItemExit;
	
	@FXML
	private Button btnHome;

	@FXML
	private BorderPane mainPane;
	
	@FXML
	protected void newGame(ActionEvent event) {
		System.out.println("New");
	}
	
	@FXML
	protected void openGame(ActionEvent event) {
		System.out.println("Open");
	}
	
	@FXML
	protected void saveGame(ActionEvent event) {
		System.out.println("Save");
	}
	
	@FXML
	protected void exitGame(ActionEvent event) {
		System.out.println("Exit");
	}
	
	public void setupScene() {
		Box box = new Box();
		box.init();
	}
}
