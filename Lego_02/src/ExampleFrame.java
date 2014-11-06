import java.awt.BorderLayout;
import java.awt.Frame;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import processing.core.PApplet;

public class ExampleFrame extends Frame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFXPanel javafxPanel;
	private static JFXPanel javafxMenu;
	private static JFXPanel javafxToolbar;
	

	public ExampleFrame() {
		super("Embedded PApplet");
		javafxPanel = new JFXPanel();
		javafxMenu = new JFXPanel();
		javafxToolbar = new JFXPanel();
		setLayout(new BorderLayout());
		setSize(1224, 768);
		PApplet embed = new LegoGame();
		add(embed, BorderLayout.EAST);
		add(javafxPanel, BorderLayout.WEST);
		add(javafxMenu, BorderLayout.NORTH);
		add(javafxToolbar, BorderLayout.NORTH);
		embed.init();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX();
			}
		});
	}

	private static void initFX() {
		// This method is invoked on the JavaFX thread
		Scene scene = createScene();
		Scene menuScene = createMenuScene();
		Scene toolbarScene = createToolbarScene();
		javafxPanel.setScene(scene);
		javafxMenu.setScene(menuScene);
		javafxToolbar.setScene(toolbarScene);
	}

	private static Scene createToolbarScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);

		Button homeBtn = new Button();
		Button saveBtn = new Button();
		Button undoBtn = new Button();
		Button redoBtn = new Button();
		 
		//Set the icon/graphic for the ToolBar Buttons.
		homeBtn.setGraphic(new ImageView("home.png"));
		saveBtn.setGraphic(new ImageView("save.png"));
		undoBtn.setGraphic(new ImageView("undo.png"));
		redoBtn.setGraphic(new ImageView("redo.png"));
				
		 
		//Add the Buttons to the ToolBar.
		ToolBar toolBar = new ToolBar();
		toolBar.getItems().addAll(homeBtn, saveBtn, undoBtn, redoBtn);
		root.getChildren().add(toolBar);
		return (scene);
	}
	
	private static Scene createMenuScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);

		final Menu menuFile = new Menu("File");		
		MenuItem menuNew = new MenuItem("New");
		MenuItem menuOpen = new MenuItem("Open");
		MenuItem menuSave = new MenuItem("Save");
		MenuItem menuExit = new MenuItem("Exit");
		menuFile.getItems().add(menuNew);
		menuFile.getItems().add(menuOpen);
		menuFile.getItems().add(menuSave);
		menuFile.getItems().add(menuExit);
		
		final Menu menuEdit = new Menu("Edit");
		MenuItem menuUndo = new MenuItem("Undo");
		MenuItem menuRedo = new MenuItem("Redo");
		menuEdit.getItems().add(menuUndo);
		menuEdit.getItems().add(menuRedo);
		
		final Menu menuHelp = new Menu("Help");
		MenuItem menuHelpItem = new MenuItem("Help");
		MenuItem menuAbout = new MenuItem("About");
		MenuItem menuPrivacyPolicy = new MenuItem("Privacy Policy");
		menuHelp.getItems().add(menuHelpItem);
		menuHelp.getItems().add(menuAbout);
		menuHelp.getItems().add(menuPrivacyPolicy);
		
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuEdit);
		menuBar.getMenus().add(menuHelp);

		root.getChildren().add(menuBar);

		return (scene);
	}

	private static Scene createScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);

		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		Tab bricksTab = new Tab();
		bricksTab.setText("Bricks");
		HBox hbox = new HBox();
		Label label = new Label("Magic Lab");
		hbox.getChildren().add(label);

		bricksTab.setContent(hbox);

		Tab templatesTab = new Tab();
		templatesTab.setText("Templates");
		HBox hboxTemplates = new HBox();
		templatesTab.setContent(hboxTemplates);

		Tab groupsTab = new Tab();
		groupsTab.setText("Groups");
		HBox hboxGroups = new HBox();
		groupsTab.setContent(hboxGroups);

		tabPane.getTabs().add(bricksTab);
		tabPane.getTabs().add(templatesTab);
		tabPane.getTabs().add(groupsTab);

		root.getChildren().add(tabPane);

		return (scene);
	}

	public static void main(String args[]) {
		ExampleFrame t = new ExampleFrame();
		t.show();
	}
}
