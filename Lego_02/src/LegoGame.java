import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PShape;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Mat;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.Scene;

@SuppressWarnings("serial")
public class LegoGame extends PApplet {

	Scene scene;
	GameManager gameManager;
	MenuController menuController;
	boolean finishLoading = true;
	// ArrayList<Vec> tempBox = new ArrayList<Vec>();
	// private Ray currentRay;
	boolean disableBox = false;
	int brickCollisionIndexPrev = -1;
	int brickCollisionIndex = -1;
	int brickSelected = -1;
	boolean debugFlag = false;
	boolean selectedBrick = false;
	boolean flagCheckKeyCode = false;

	public void saveGame(String fileName) {
		menuController.setFileName(fileName);
		menuController.saveGame();
	}

	public void newGame() {
		finishLoading = false;
		Brick brickFollowMouse = gameManager.getBrickFollowMouse();
		setup();
		gameManager.setBrickFollowMouse(brickFollowMouse);
		finishLoading = true;
	}

	public void undo() {
		gameManager.undo();
	}

	public void redo() {
		gameManager.redo();
	}

	public void setColor(Vec color) {
		gameManager.setCurrentColor(color);
	}

	public void selectBrick(int brick) {
		Brick tempBrick = null;
		flagCheckKeyCode = true;
		selectedBrick = false;
		// Process switching brick
		gameManager.getCurBrick().setModelName(
				Util.MODEL_NAME_LIST.get(brick - 1));
		gameManager.setSwitchBrick(true);
		// gameManager.getTempInteractiveFrames().clear();

		try {
			tempBrick = gameManager.getBrickFactory().createBrick(
					gameManager.getCurBrick().getModelName());
			tempBrick.setModelName(gameManager.getCurBrick().getModelName());
			gameManager.setDisableBoxFollowMouse(false);
			disableBox = false;
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {

			e.printStackTrace();
		}

		if (flagCheckKeyCode) {
			gameManager.setGeneratedTempIF(false);
			gameManager.setResetTempIFList(true);
			gameManager.generateInteractiveFrameForSpecialCase2(tempBrick,
					scene);
		}
	}

	public void setup() {
		try {
			Util.LoadModelNames();
			Util.LoadCalibrateVec();
			Util.LoadExtraPositionVec();

		} catch (IOException e) {
			e.printStackTrace();
		}
		setupScene();
		setupGameManager();
		scene.disableKeyboardAgent();
		Util.CURRENT_SCENE = scene;
		scene.camera().setPosition(new Vec(0, 0, 280));
		// scene.camera().setUpVector(new Vec(1, 1, -1));
		menuController = new MenuController(gameManager);
		// currentRay = null;
	}

	public void delete() {
		gameManager.delete(brickSelected);
		brickSelected = -1;
		brickCollisionIndex = -1;
		brickCollisionIndexPrev = -1;
	}

	public void loadGame(String fileName) {
		newGame();
		menuController.setFileName(fileName);
		menuController.loadGame();
	}

	public void draw() {
		noStroke();
		setupDisplay();
		// gameManager.drawGUI();
		if (!finishLoading)
			return;
		drawScene();
		// drawBox();
		drawBoxCoverBrickSelected();
	}

	private void drawBoxCoverBrickSelected() {
		int index = brickCollisionIndex;

		if (brickSelected != -1 && selectedBrick) {
			strokeWeight(2);
			index = brickSelected;
			stroke(39, 255, 228);
			noFill();
			pushMatrix();
			translate(gameManager.getBricks().get(index).getTranslate().x()
					+ gameManager.getBricks().get(index).getCalibrateVec().x(),
					gameManager.getBricks().get(index).getTranslate().y()
							+ gameManager.getBricks().get(index)
									.getCalibrateVec().y(), gameManager
							.getBricks().get(index).getTranslate().z()
							+ gameManager.getBricks().get(index)
									.getCalibrateVec().z());
			if (gameManager.getBricks().get(index).getTranslateForDraw() != null) {
				translate(gameManager.getBricks().get(index)
						.getTranslateForDraw().x(), gameManager.getBricks()
						.get(index).getTranslateForDraw().y(), gameManager
						.getBricks().get(index).getTranslateForDraw().z());
			}
			for (int i = 0; i < gameManager.getBricks().get(index)
					.getBoxCollider().getListBox().size(); i++) {
				box(gameManager.getBricks().get(index).getBoxCollider()
						.getListBox().get(i).getWidth(), gameManager
						.getBricks().get(index).getBoxCollider().getListBox()
						.get(i).getHeight(), gameManager.getBricks().get(index)
						.getBoxCollider().getListBox().get(i).getDepth()
						+ Util.DOT_HEIGH + Util.FOOT_HEIGHT);

			}
			popMatrix();
		}
		if (brickCollisionIndex != -1) {
			strokeWeight(2);
			index = brickCollisionIndex;
			stroke(255, 120, 189);
			noFill();
			pushMatrix();
			translate(gameManager.getBricks().get(index).getTranslate().x()
					+ gameManager.getBricks().get(index).getCalibrateVec().x(),
					gameManager.getBricks().get(index).getTranslate().y()
							+ gameManager.getBricks().get(index)
									.getCalibrateVec().y(), gameManager
							.getBricks().get(index).getTranslate().z()
							+ gameManager.getBricks().get(index)
									.getCalibrateVec().z());
			if (gameManager.getBricks().get(index).getTranslateForDraw() != null) {
				translate(gameManager.getBricks().get(index)
						.getTranslateForDraw().x(), gameManager.getBricks()
						.get(index).getTranslateForDraw().y(), gameManager
						.getBricks().get(index).getTranslateForDraw().z());
			}
			for (int i = 0; i < gameManager.getBricks().get(index)
					.getBoxCollider().getListBox().size(); i++) {
				box(gameManager.getBricks().get(index).getBoxCollider()
						.getListBox().get(i).getWidth()
						+ Util.EXTEND_BOX_SELECTED, gameManager.getBricks()
						.get(index).getBoxCollider().getListBox().get(i)
						.getHeight()
						+ Util.EXTEND_BOX_SELECTED, gameManager.getBricks()
						.get(index).getBoxCollider().getListBox().get(i)
						.getDepth()
						+ Util.DOT_HEIGH
						+ Util.FOOT_HEIGHT
						+ Util.EXTEND_BOX_SELECTED);

			}
			popMatrix();
		}
	}

	/*
	 * private void drawBox() { for (int i = 0; i < tempBox.size(); i++) {
	 * pushMatrix(); translate(tempBox.get(i).x(), tempBox.get(i).y(),
	 * tempBox.get(i) .z()); box(1); popMatrix(); } }
	 */

	private void drawScene() {
		for (int i = 0; i < Util.PLANE_WIDTH; i++) {
			for (int j = 0; j < Util.PLANE_HEIGHT; j++) {
				// Draw interactive frame on screen
				pushMatrix();
				gameManager.getPlaneLego().getInteractiveFrames()
						.get(i * Util.PLANE_WIDTH + j).applyTransformation();
				drawAxes();
				popMatrix();
				pushMatrix();
				gameManager
						.getPlaneLego()
						.getpShapes()
						.get(i * Util.PLANE_WIDTH + j)
						.setFill(
								setColor(
										gameManager
												.getPlaneLego()
												.getInteractiveFrames()
												.get(i * Util.PLANE_WIDTH + j)
												.grabsInput(scene.motionAgent()),
										i * Util.PLANE_WIDTH + j, null, 0));
				// Draw plane
				shape(gameManager.getPlaneLego().getpShapes()
						.get(i * Util.PLANE_WIDTH + j),
						(i - Util.PLANE_WIDTH / 2) * Util.BRICK_SIZE,
						(j - Util.PLANE_HEIGHT / 2) * Util.BRICK_SIZE);
				popMatrix();
			}
		}

		int counter = 0;
		for (int i = 0; i < gameManager.getBricks().size(); i++) {

			pushMatrix();
			for (int j = 0; j < gameManager.getBricks().get(i)
					.getDotInteractiveFrameList().size(); j++) {
				gameManager.getBricks().get(i).getDotInteractiveFrameList()
						.get(j).applyTransformation();
				drawAxes();
			}
			popMatrix();

			pushMatrix();

			for (int j = 0; j < gameManager.getBricks().get(i)
					.getDotInteractiveFrameList().size(); j++) {
				setColor(
						gameManager.getBricks().get(i)
								.getDotInteractiveFrameList().get(j)
								.grabsInput(scene.motionAgent()),
						Util.PLANE_HEIGHT * Util.PLANE_WIDTH + counter,
						gameManager.getBricks().get(i), j);
				counter++;
			}

			popMatrix();

			pushMatrix();
			translate(gameManager.getBricks().get(i).getTranslate().x()
					+ gameManager.getBricks().get(i).getCalibrateVec().x(),
					gameManager.getBricks().get(i).getTranslate().y()
							+ gameManager.getBricks().get(i).getCalibrateVec()
									.y(), gameManager.getBricks().get(i)
							.getTranslate().z()
							+ gameManager.getBricks().get(i).getCalibrateVec()
									.z());
			if (gameManager.getBricks().get(i).getTranslateForDraw() != null) {
				translate(gameManager.getBricks().get(i).getTranslateForDraw()
						.x(), gameManager.getBricks().get(i)
						.getTranslateForDraw().y(), gameManager.getBricks()
						.get(i).getTranslateForDraw().z());
			}
			scale(gameManager.getBricks().get(i).getScaleRatio());
			rotateX(gameManager.getBricks().get(i).getRotate().x());
			rotateY(gameManager.getBricks().get(i).getRotate().y());
			fill(gameManager.getBricks().get(i).getColor().x(), gameManager
					.getBricks().get(i).getColor().y(), gameManager.getBricks()
					.get(i).getColor().z());
			gameManager.getBricks().get(i).getModel().disableMaterial();
			gameManager.getBricks().get(i).getModel().draw();
			popMatrix();
		}

		if (gameManager.getBrickFollowMouse() != null
				&& gameManager.isDisableBoxFollowMouse() == false) {
			pushMatrix();
			translate(gameManager.getBrickFollowMouse().getTranslate().x()
					+ gameManager.getBrickFollowMouse().getCalibrateVec().x(),
					gameManager.getBrickFollowMouse().getTranslate().y()
							+ gameManager.getBrickFollowMouse()
									.getCalibrateVec().y(), gameManager
							.getBrickFollowMouse().getTranslate().z()
							+ gameManager.getBrickFollowMouse()
									.getCalibrateVec().z());
			if (gameManager.getBrickFollowMouse().getTranslateForDraw() != null) {
				translate(gameManager.getBrickFollowMouse()
						.getTranslateForDraw().x(), gameManager
						.getBrickFollowMouse().getTranslateForDraw().y(),
						gameManager.getBrickFollowMouse().getTranslateForDraw()
								.z());
			}

			scale(gameManager.getBrickFollowMouse().getScaleRatio());
			rotateX(gameManager.getBrickFollowMouse().getRotate().x());
			rotateY(gameManager.getBrickFollowMouse().getRotate().y());
			fill(gameManager.getBrickFollowMouse().getColor().x(), gameManager
					.getBrickFollowMouse().getColor().y(), gameManager
					.getBrickFollowMouse().getColor().z());
			gameManager.getBrickFollowMouse().getModel().disableMaterial();
			gameManager.getBrickFollowMouse().draw();
			popMatrix();

		}

		for (int f = 0; f < gameManager.getTempInteractiveFrames().size(); f++) {
			pushMatrix();
			gameManager.getTempInteractiveFrames().get(f).applyTransformation();
			drawAxes();
			popMatrix();

			pushMatrix();
			setColor(
					gameManager.getTempInteractiveFrames().get(f)
							.grabsInput(scene.motionAgent()), -2, null, f);
			popMatrix();

		}

		noStroke();
		// saveState();
		// gameManager.drawGUI();
		// restoreState();
	}

	private void drawAxes() {
		if (Util.DRAW_AXES)
			scene.drawAxes(1);
	}

	void saveState() {
		// Set processing projection and modelview matrices to draw in 2D:
		// 1. projection matrix:
		float cameraZ = ((height / 2.0f) / tan(PI * 60.0f / 360.0f));
		scene.pg().perspective(PI / 3.0f, scene.camera().aspectRatio(),
				cameraZ / 10.0f, cameraZ * 10.0f);
		// 2 model view matrix
		scene.pg().camera();
		hint(DISABLE_DEPTH_TEST);
	}

	void restoreState() {
		// 1. Restore processing projection matrix
		switch (scene.camera().type()) {
		case PERSPECTIVE:
			scene.pg().perspective(scene.camera().fieldOfView(),
					scene.camera().aspectRatio(), scene.camera().zNear(),
					scene.camera().zFar());
			break;
		case ORTHOGRAPHIC:
			float[] wh = scene.camera().getBoundaryWidthHeight();
			scene.pg().ortho(-wh[0], wh[0], -wh[1], wh[1],
					scene.camera().zNear(), scene.camera().zFar());
			break;
		}
		// 2. Restore processing modelview matrix
		scene.pg().camera(scene.camera().position().x(),
				scene.camera().position().y(), scene.camera().position().z(),
				scene.camera().at().x(), scene.camera().at().y(),
				scene.camera().at().z(), scene.camera().upVector().x(),
				scene.camera().upVector().y(), scene.camera().upVector().z());
		// hint(ENABLE_DEPTH_TEST);
	}

	/**
	 * @param selected
	 *            : flag will be set true if the interactive frame is selected
	 * @param position
	 *            : position of the selected interactive frame in list
	 *            interactive frame
	 * @param brickConsistIFrame
	 *            : brick consist the interactive frame
	 * @param positionOfInteractiveFrame
	 *            : position of the selected interactive frame on brick
	 * @return
	 */
	public int setColor(boolean selected, int position,
			Brick brickConsistIFrame, int positionOfInteractiveFrame) {
		if (disableBox)
			return color(200, 200, 200);
		if (selected) {
			/*
			 * Check Collision the brick at current mouse position with all
			 * brick was placed on plane
			 */

			checkCollision(position);
			gameManager.setCurFocusFramePos(position);
			Brick brick;
			try {
				brick = gameManager.getBrickFactory().createBrick(
						gameManager.getCurBrick().getModelName());
				brick.setModelName(gameManager.getCurBrick().getModelName());
				if (position != -2) {
					brick.setTranslate(gameManager
							.getInteractiveFrameCollection().get(position)
							.position());
					brick.setLayerIndex((int) (gameManager
							.getInteractiveFrameCollection().get(position)
							.position().z() / Util.BRICK_SIZE));
				} else {
					brick.setTranslate(gameManager.getTempInteractiveFrames()
							.get(positionOfInteractiveFrame).position());
					brick.setLayerIndex((int) (gameManager
							.getTempInteractiveFrames()
							.get(positionOfInteractiveFrame).position().z() / Util.BRICK_SIZE));
				}
				brick.setOriginalTranslate(brick.getTranslate());
				if (gameManager.getBrickFollowMouse() != null
						&& brick.getModelName() == gameManager
								.getBrickFollowMouse().getModelName()
						&& gameManager.getBrickFollowMouse()
								.getTranslateForDraw() != null) {
					brick.setTranslateForDraw(gameManager.getBrickFollowMouse()
							.getTranslateForDraw());
					brick.setTimesRotate(gameManager.getBrickFollowMouse()
							.getTimesRotate());
					brick.setRotate(gameManager.getBrickFollowMouse()
							.getRotate());
				} else {
					brick.setTranslateForDraw(new Vec(0, 0, 0));
					brick.setRotate(Util.DEFAULT_ROTATE);
				}
				// brick.calibrate();
				brick.setScaleRatio(Util.OBJECT_SCALE);

				brick.setModel(gameManager.getBrickModels().get(
						brick.getModelName()));
				brick.generateBoxCollider();
				brick.setColor(gameManager.getCurrentColor());

				if (brickConsistIFrame == null) {
					gameManager.setGeneratedTempIF(true);
				} else {
					gameManager.setGeneratedTempIF(false);
				}

				gameManager.setPrevBrick(brickConsistIFrame);
				// gameManager.setPrevIF(positionOfInteractiveFrame);

				gameManager.setBrickFollowMouse(brick);
				// gameManager.generateInteractiveFrameForSpecialCase(brickConsistIFrame,
				// positionOfInteractiveFrame);
				// gameManager.generateInteractiveFrameForSpecialCase2(brickConsistIFrame);
				return color(200, 200, 0);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
				return color(200, 200, 200);
			}

		} else {
			return color(200, 200, 200);
		}
	}

	private void checkCollision(int position) {

		for (int i = 0; i < gameManager.getBricks().size(); i++) {
			/*
			 * Each brick has a box collide consist of a series of box as
			 * rectangle, cube.
			 */

			for (int k = 0; k < gameManager.getBricks().get(i)
					.getCenterPositionOfDot().size(); k++) {
				if (gameManager
						.getBrickFollowMouse()
						.getBoxCollider()
						.Containt(
								gameManager.getBricks().get(i)
										.getCenterPositionOfDot().get(k))) {
					gameManager.setDisableBoxFollowMouse(true);
					return;
				}

			}
			gameManager.setDisableBoxFollowMouse(false);
		}
	}

	public void keyPressed() {
		/*
		 * Increase the angle
		 */

		Brick tempBrick = null;
		if (gameManager.getBrickFollowMouse() == null)
			return;
		// gameManager.getBrickFollowMouse().calibrateAfterRotate();

		if (key == 't' || key == 'T' || keyCode == 37) {
			gameManager.getBrickFollowMouse().getRotate()
					.add(0, Util.ROTATE_ANGLE_ADDED, 0);
			gameManager.getBrickFollowMouse().decreaseTimesRotate();
			gameManager.setSwitchBrick(false);
			flagCheckKeyCode = true;
		}

		/*
		 * Decrease the angle
		 */
		if (key == 'r' || key == 'R' || keyCode == 39) {
			gameManager.getBrickFollowMouse().getRotate()
					.subtract(0, Util.ROTATE_ANGLE_ADDED, 0);
			gameManager.getBrickFollowMouse().increaseTimesRotate();
			gameManager.setSwitchBrick(false);
			flagCheckKeyCode = true;
		}

		gameManager.getBrickFollowMouse().calibrateAfterRotate();

		if (Util.KEY_SWITCH_BRICK.indexOf(key) != -1) {
			flagCheckKeyCode = true;
			selectedBrick = false;
			// Process switching brick
			gameManager.getCurBrick().setModelName(
					Util.MODEL_NAME_LIST.get((Integer.parseInt(String
							.valueOf(key)) - 1)));
			gameManager.setSwitchBrick(true);
			// gameManager.getTempInteractiveFrames().clear();

			try {
				tempBrick = gameManager.getBrickFactory().createBrick(
						gameManager.getCurBrick().getModelName());
				tempBrick
						.setModelName(gameManager.getCurBrick().getModelName());
				gameManager.setDisableBoxFollowMouse(false);
				disableBox = false;
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {

				e.printStackTrace();
			}
		}

		if (flagCheckKeyCode) {
			gameManager.setGeneratedTempIF(false);
			gameManager.setResetTempIFList(true);
			gameManager.generateInteractiveFrameForSpecialCase2(tempBrick,
					scene);
		}

		if (key == 'n' || key == 'N') {
			newGame();
		}

		if (key == 'd') {
			debugFlag = true;
		}

		if (key == PConstants.DELETE) {
			if (brickSelected != -1) {
				System.out.println("Delete");
				delete();
				brickSelected = -1;

			}
		}
	}

	public void mouseClicked() {
		if (disableBox && brickCollisionIndex != -1) {
			selectedBrick = true;
			brickSelected = brickCollisionIndex;
			System.out.println(brickSelected);
		} else {
			brickSelected = -1;
		}

		if (gameManager.getCurFocusFramePos() == -1
				|| gameManager.getBrickFollowMouse() == null
				|| gameManager.isDisableBoxFollowMouse())
			return;

		Brick brick;
		try {
			brick = gameManager.getBrickFactory().createBrick(
					gameManager.getBrickFollowMouse().modelName);
			brick.setModel(gameManager.getBrickFollowMouse().model);
			brick.setModelName(gameManager.getBrickFollowMouse().modelName);
			brick.setRotate(new Vec(gameManager.getBrickFollowMouse().rotate
					.x(), gameManager.getBrickFollowMouse().rotate.y(),
					gameManager.getBrickFollowMouse().rotate.z()));
			brick.setScaleRatio(gameManager.getBrickFollowMouse().scaleRatio);
			brick.setTranslate(gameManager.getBrickFollowMouse().translate);
			brick.setTimesRotate(gameManager.getBrickFollowMouse()
					.getTimesRotate());
			brick.setTranslateForDraw(gameManager.getBrickFollowMouse()
					.getTranslateForDraw());
			brick.setLayerIndex(gameManager.getBrickFollowMouse()
					.getLayerIndex());
			brick.setColor(gameManager.getBrickFollowMouse().getColor());
			if (brick.getLayerIndex() > gameManager.getMaxLayerIndex()) {
				gameManager.setMaxLayerIndex(brick.getLayerIndex());
			}
			// System.out.println(brick.getLayerIndex());
			brick.generateInitData();
			brick.setId(gameManager.getBricks().size());
			gameManager.updateInteractiveFrameCollection(brick);
			gameManager.getBricks().add(brick);
			gameManager.setGeneratedTempIF(false);
			gameManager.setResetTempIFList(true);
			gameManager.setSwitchBrick(false);
			gameManager.generateInteractiveFrameForSpecialCase2(null, scene);
			gameManager.setDisableBoxFollowMouse(true);
			disableBox = true;
			// gameManager.setBrickFollowMouse(null);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		/*
		 * Create so
		 * 
		 * 
		 * me interactive frames according to number of dot of the brick that
		 * the player has chosen In this demonstration, I just use the 2x1 brick
		 * so I just need to create two interactive for two dot
		 */
	}

	private void setupDisplay() {
		background(85, 173, 255);
		lights();

		scene.setPickingVisualHint(false);
	}

	private void setupGameManager() {
		gameManager = new GameManager(this);
		gameManager.setup();
		setupPlane();

	}

	private void setupPlane() {
		for (int i = -(Util.PLANE_WIDTH / 2); i < Util.PLANE_WIDTH / 2; i++) {
			for (int j = -(Util.PLANE_HEIGHT / 2); j < Util.PLANE_HEIGHT / 2; j++) {
				/*
				 * Create interactive frame for plane We have an 10x10 plane
				 */
				InteractiveFrame iframe = new InteractiveFrame(scene, null);
				LocalConstraint XAxis = new LocalConstraint();
				XAxis.setTranslationConstraint(
						AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f, 0.0f,
								0.0f));
				XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
						new Vec(1.0f, 0.0f, 0.0f));
				iframe.setConstraint(XAxis);
				iframe.setScaling(Util.BRICK_SIZE);
				iframe.setTranslation(
						Util.BRICK_SIZE * i + Util.BRICK_SIZE / 2,
						Util.BRICK_SIZE * j + Util.BRICK_SIZE / 2, 0);
				iframe.setGrabsInputThreshold(Util.THRESHOLD_VALUE / 2);
				gameManager.getPlaneLego().getInteractiveFrames().add(iframe);
				gameManager.getInteractiveFrameCollection().add(iframe);

				/*
				 * Roofing square for the plane Each square has size 20x20
				 */

				PShape square;
				square = loadShape("circle95.svg");
				// square = createShape(RECT, 0, 0, 20, 20);
				// square.setStroke(true);
				gameManager.getPlaneLego().getpShapes().add(square);
			}
		}
	}

	private void setupScene() {
		size(1024, 780, OPENGL);
		scene = new Scene(this);
		scene.setRadius(100);
		scene.showAll();
		scene.setGridVisualHint(false);
		scene.setPickingVisualHint(true);

	}

	/*
	 * private boolean checkCollision(Ray currentRay, BoundingBox
	 * brickBoundingBox, float t0, float t1) { float tmin, tmax, tymin, tymax,
	 * tzmin, tzmax;
	 * 
	 * if (currentRay.getDirection().x() >= 0) { tmin =
	 * (brickBoundingBox.getMin().x - currentRay.getOrigin().x()) /
	 * currentRay.getDirection().x(); tmax = (brickBoundingBox.getMax().x -
	 * currentRay.getOrigin().x()) / currentRay.getDirection().x(); } else {
	 * tmin = (brickBoundingBox.getMax().x - currentRay.getOrigin().x()) /
	 * currentRay.getDirection().x(); tmax = (brickBoundingBox.getMin().x -
	 * currentRay.getOrigin().x()) / currentRay.getDirection().x(); }
	 * 
	 * if (currentRay.getDirection().y() >= 0) { tymin =
	 * (brickBoundingBox.getMin().y - currentRay.getOrigin().y()) /
	 * currentRay.getDirection().y(); tymax = (brickBoundingBox.getMax().y -
	 * currentRay.getOrigin().y()) / currentRay.getDirection().y(); } else {
	 * tymin = (brickBoundingBox.getMax().y - currentRay.getOrigin().y()) /
	 * currentRay.getDirection().y(); tymax = (brickBoundingBox.getMin().y -
	 * currentRay.getOrigin().y()) / currentRay.getDirection().y(); }
	 * 
	 * if ((tmin > tymax) || (tymin > tmax)) return false; if (tymax > tmin)
	 * tmin = tymin; if (tymax < tmax) tmax = tymax; if
	 * (currentRay.getDirection().z() >= 0) { tzmin =
	 * (brickBoundingBox.getMin().z - currentRay.getOrigin().z()) /
	 * currentRay.getDirection().z(); tzmax = (brickBoundingBox.getMax().z -
	 * currentRay.getOrigin().z()) / currentRay.getDirection().z(); } else {
	 * tzmin = (brickBoundingBox.getMax().z - currentRay.getOrigin().z()) /
	 * currentRay.getDirection().z(); tzmax = (brickBoundingBox.getMin().z -
	 * currentRay.getOrigin().z()) / currentRay.getDirection().z(); }
	 * 
	 * if ((tmin > tzmax) || (tzmin > tmax)) { return false; } if (tzmin > tmin)
	 * { tmin = tzmin;
	 * 
	 * } if (tzmax < tmax) { tmax = tzmax; } return ((tmin < t1) && (tmax >
	 * t0)); }
	 */

	@Override
	public void mouseMoved() {
		super.mouseMoved();
		// tempBox.clear();
		objectPicking();

	}

	public void objectPicking() {
		Vec CS_Direc = new Vec();
		CS_Direc.setX(((((2.0f * mouseX) / width) - 1) / scene.projection()
				.m00()));
		CS_Direc.setY(-1.0f
				* ((((2.0f * mouseY) / height) - 1) / scene.projection().m11()));
		CS_Direc.setZ(-1.0f);

		Vec WS_Start = new Vec();
		Mat M_view_Inv = scene.camera().getView();
		M_view_Inv.invert();
		WS_Start.setX(M_view_Inv.m03());
		WS_Start.setY(M_view_Inv.m13());
		WS_Start.setZ(M_view_Inv.m23());

		Vec WS_Direc = new Vec();
		WS_Direc.setX(CS_Direc.x() * M_view_Inv.m00() + CS_Direc.y()
				* M_view_Inv.m01() + CS_Direc.z() * M_view_Inv.m02());
		WS_Direc.setY(CS_Direc.x() * M_view_Inv.m10() + CS_Direc.y()
				* M_view_Inv.m11() + CS_Direc.z() * M_view_Inv.m12());
		WS_Direc.setZ(CS_Direc.x() * M_view_Inv.m20() + CS_Direc.y()
				* M_view_Inv.m21() + CS_Direc.z() * M_view_Inv.m22());

		int i = 0;
		while (true) {
			Vec pos = new Vec();
			pos = Vec.add(WS_Start,
					Vec.multiply(WS_Direc, i + scene.camera().zNear()));
			// tempBox.add(pos);
			brickCollisionIndex = checkCollisionPointAndBox(pos);
			if (pos.z() < 0 || brickCollisionIndex != -1) {
				if (brickCollisionIndex != -1) {
					if (brickCollisionIndexPrev == -1
							|| brickCollisionIndex != -1) {
						brickCollisionIndexPrev = brickCollisionIndex;
					}
				}
				break;
			}

			i++;
			if (i > 1000)
				break;
		}

		// currentRay = new Ray(WS_Start, WS_Direc);

		// System.out.println("Start " + WS_Start);
		// System.out.println("Direct " + WS_Direc);
	}

	private int checkCollisionPointAndBox(Vec pos) {
		for (int i = 0; i < gameManager.getBricks().size(); i++) {
			for (int j = 0; j < gameManager.getBricks().get(i).getBoxCollider()
					.getListBox().size(); j++) {
				Vec tempVec = Vec.subtract(gameManager.getBricks().get(i)
						.getBoxCollider().getListBox().get(j).getPosition(),
						new Vec(Util.BRICK_SIZE / 2, Util.BRICK_SIZE / 2, 0));
				Box box = new Box();
				box.setPosition(tempVec);
				box.setWidth(gameManager.getBricks().get(i).getBoxCollider()
						.getListBox().get(j).getWidth());
				box.setHeight(gameManager.getBricks().get(i).getBoxCollider()
						.getListBox().get(j).getHeight());
				box.setDepth(gameManager.getBricks().get(i).getBoxCollider()
						.getListBox().get(j).getDepth());
				if (box.Container(pos)) {
					return i;
				}
			}
		}
		return -1;
	}
}
