import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ExampleFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFXPanel javafxPanel;
	private static JFXPanel javafxMenu;
	private static JFXPanel javafxToolbar;
	private static VBox vboxContainter;
	private static JFXPanel javafxTop;
	private static MenuBar menuBar; // Creates our main menu to hold our
									// Sub-Menus.
	private static ToolBar toolBar; // Creates our tool-bar to hold the buttons.
	private static LegoGame legoGame;

	public ExampleFrame() {
		super("Lego");
		javafxPanel = new JFXPanel();
		javafxMenu = new JFXPanel();
		javafxToolbar = new JFXPanel();
		vboxContainter = new VBox();
		javafxTop = new JFXPanel();

		setLayout(new BorderLayout());
		setSize(1224, 768);
		legoGame = new LegoGame();
		add(legoGame, BorderLayout.EAST);
		add(javafxPanel, BorderLayout.WEST);

		add(javafxTop, BorderLayout.NORTH);
		// add(javafxToolbar, BorderLayout.SOUTH);

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX();
			}
		});

		setResizable(false);
		legoGame.init();
	}

	private static void initFX() {
		// This method is invoked on the JavaFX thread
		Scene menuScene = createMenuScene();
		Scene toolbarScene = createToolbarScene();
		javafxMenu.setScene(menuScene);
		javafxToolbar.setScene(toolbarScene);
		vboxContainter.getChildren().add(menuBar);
		vboxContainter.getChildren().add(toolBar);
		javafxTop.setScene(new Scene(vboxContainter, 200, 90));
		Scene scene = createScene();
		javafxPanel.setScene(scene);

	}

	private static Scene createToolbarScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.ALICEBLUE);

		Button homeBtn = new Button();
		Button saveBtn = new Button();
		Button undoBtn = new Button();
		Button redoBtn = new Button();

		// Set the icon/graphic for the ToolBar Buttons.
		homeBtn.setGraphic(new ImageView("home.png"));
		saveBtn.setGraphic(new ImageView("save.png"));
		undoBtn.setGraphic(new ImageView("undo.png"));
		undoBtn.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				legoGame.undo();
			}
		});
		redoBtn.setGraphic(new ImageView("redo.png"));

		// Add the Buttons to the ToolBar.
		toolBar = new ToolBar();
		toolBar.getItems().addAll(homeBtn, saveBtn, undoBtn, redoBtn);
		root.getChildren().add(toolBar);
		return (scene);
	}

	public void setsize() {
		this.setSize(1224, 768);
	}

	private static Scene createMenuScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.valueOf("f5f5f5"));

		final Menu menuFile = new Menu("File");
		MenuItem menuNew = new MenuItem("New");
		menuNew.setAccelerator(new KeyCodeCombination(KeyCode.N,
				KeyCombination.CONTROL_DOWN));
		menuNew.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				legoGame.newGame();
			}
		});
		MenuItem menuOpen = new MenuItem("Open");
		menuOpen.setAccelerator(new KeyCodeCombination(KeyCode.O,
				KeyCombination.CONTROL_DOWN));
		menuOpen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load game");
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("All File", "*.*"),
						new FileChooser.ExtensionFilter("xml", "*.xml"));
				File file = fileChooser.showOpenDialog(null);
				if (file != null) {
					legoGame.loadGame(file.getPath());
				}
			}
		});
		MenuItem menuSave = new MenuItem("Save");
		menuSave.setAccelerator(new KeyCodeCombination(KeyCode.S,
				KeyCombination.CONTROL_DOWN));
		menuSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save game");
				fileChooser.getExtensionFilters().addAll(
						new FileChooser.ExtensionFilter("All File", "*.*"),
						new FileChooser.ExtensionFilter("xml", "*.xml"));
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					legoGame.saveGame(file.getPath());
				}
			}
		});
		MenuItem menuExit = new MenuItem("Exit");
		menuExit.setAccelerator(new KeyCodeCombination(KeyCode.Q,
				KeyCombination.CONTROL_DOWN));
		menuExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				showDialog(null);
				System.exit(0);
			}
		});
		menuFile.getItems().add(menuNew);
		menuFile.getItems().add(menuOpen);
		menuFile.getItems().add(menuSave);
		menuFile.getItems().add(menuExit);

		final Menu menuEdit = new Menu("Edit");
		menuEdit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
			}
		});
		MenuItem menuUndo = new MenuItem("Undo");
		menuUndo.setAccelerator(new KeyCodeCombination(KeyCode.Z,
				KeyCombination.CONTROL_DOWN));
		menuUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				legoGame.undo();
			}
		});
		MenuItem menuRedo = new MenuItem("Redo");
		menuRedo.setAccelerator(new KeyCodeCombination(KeyCode.Y,
				KeyCombination.CONTROL_DOWN));
		menuEdit.getItems().add(menuUndo);
		menuEdit.getItems().add(menuRedo);

		final Menu menuHelp = new Menu("Help");
		MenuItem menuHelpItem = new MenuItem("Help");
		MenuItem menuAbout = new MenuItem("About");
		MenuItem menuPrivacyPolicy = new MenuItem("Privacy Policy");
		menuHelp.getItems().add(menuHelpItem);
		menuHelp.getItems().add(menuAbout);
		menuHelp.getItems().add(menuPrivacyPolicy);

		menuBar = new MenuBar();
		menuBar.getMenus().add(menuFile);
		menuBar.getMenus().add(menuEdit);
		menuBar.getMenus().add(menuHelp);

		root.getChildren().add(menuBar);

		return (scene);
	}

	private static Scene createScene() {
		Group root = new Group();
		Scene scene = new Scene(root, Color.valueOf("f5f5f5"));

		BorderPane borderPane = new BorderPane();

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
		borderPane.setCenter(tabPane);

		ToolBar toolBar = new ToolBar();
		Button btnColor = new Button();
		btnColor.setGraphic(new ImageView("color_picker.png"));
		btnColor.setPrefSize(48, 48);
		btnColor.setPadding(new Insets(-10));
		toolBar.getItems().add(btnColor);

		borderPane.setBottom(toolBar);

		borderPane.prefHeightProperty().bind(scene.heightProperty());
		borderPane.prefWidthProperty().add(200);

		root.getChildren().add(borderPane);
		return (scene);
	}

	public static void main(String args[]) throws InterruptedException {
		ExampleFrame exampleFrame = new ExampleFrame();
		exampleFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		exampleFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				showDialog(exampleFrame);
				System.exit(0);
			}
		});
		exampleFrame.setVisible(true);
		exampleFrame.setState(JFrame.ICONIFIED);

		Thread.sleep(1000);

		exampleFrame.setState(JFrame.NORMAL);
	}

	public static void showDialog(Component c) {
		JOptionPane.showMessageDialog(c, "Bye Bye!");
	}
}
