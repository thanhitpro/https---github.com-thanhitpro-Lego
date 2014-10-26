import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import processing.core.PApplet;
import processing.core.PShape;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.*;

@SuppressWarnings("serial")
public class LegoGame extends PApplet {

	Scene scene;
	GameManager gameManager;

	public void setup() {
		try {
			Util.LoadModelNames();
			Util.LoadCalibrateVec();
			Util.LoadExtraPositionVec();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setupScene();
		setupGameManager();
		scene.disableKeyboardAgent();
		Util.CURRENT_SCENE = scene;
	}

	public void draw() {
		setupDisplay();
		// gameManager.drawGUI();
		drawScene();
	}

	private void drawScene() {
		for (int i = 0; i < Util.PLANE_WIDTH; i++) {
			for (int j = 0; j < Util.PLANE_HEIGHT; j++) {
				// Draw interactive frame on screen
				pushMatrix();
				// planeLego.getInteractiveFrames().get(i * width +
				// j).applyTransformation();
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
				fill(setColor(
						gameManager.getBricks().get(i)
								.getDotInteractiveFrameList().get(j)
								.grabsInput(scene.motionAgent()),
						Util.PLANE_HEIGHT * Util.PLANE_WIDTH + counter,
						gameManager.getBricks().get(i), j));
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
			gameManager.getBrickFollowMouse().draw();
			popMatrix();

		}

		for (int f = 0; f < gameManager.getTempInteractiveFrames().size(); f++) {
			// Draw interactive frame on screen
			pushMatrix();
			// planeLego.getInteractiveFrames().get(i * width +
			// j).applyTransformation();
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
		hint(ENABLE_DEPTH_TEST);
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
				} else {
					brick.setTranslate(gameManager.getTempInteractiveFrames()
							.get(positionOfInteractiveFrame).position());
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
				// TODO Auto-generated catch block
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
		gameManager.getBrickFollowMouse().calibrateAfterRotate();

		boolean flagCheckKeyCode = false;
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

		if (Util.KEY_SWITCH_BRICK.indexOf(key) != -1) {
			flagCheckKeyCode = true;
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
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (flagCheckKeyCode) {
			gameManager.setGeneratedTempIF(false);
			gameManager.setResetTempIFList(true);
			gameManager.generateInteractiveFrameForSpecialCase2(tempBrick,
					scene);
		}
	}

	public void mouseClicked() {
		if (gameManager.getCurFocusFramePos() == -1
				|| gameManager.getBrickFollowMouse() == null
				|| gameManager.isDisableBoxFollowMouse())
			return;

		// Brick brick = gameManager.getBrickFollowMouse();
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
			brick.generateInteractiveFrame();
			brick.generateBoxCollider();
			brick.generateCenterPositionOfDot();
			brick.setId(gameManager.getBricks().size());
			gameManager.updateInteractiveFrameCollection(brick);
			gameManager.getBricks().add(brick);
			gameManager.setGeneratedTempIF(false);
			gameManager.setResetTempIFList(true);
			gameManager.setSwitchBrick(false);
			gameManager.generateInteractiveFrameForSpecialCase2(null, scene);
		} catch (NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
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
				iframe.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
				gameManager.getPlaneLego().getInteractiveFrames().add(iframe);
				gameManager.getInteractiveFrameCollection().add(iframe);

				/*
				 * Roofing square for the plane Each square has size 20x20
				 */

				PShape square;
				square = createShape(RECT, 0, 0, 20, 20);
				square.setStroke(true);
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
}
