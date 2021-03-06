import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import processing.core.PApplet;
import processing.core.PConstants;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.Scene;
import saito.objloader.OBJModel;
import controlP5.ControlP5;

public class GameManager {
	private PlaneLego planeLego;
	private ArrayList<Brick> bricks;
	public static ArrayList<InteractiveFrame> interactiveFrameCollection;

	private int curFocusFramePos = -1;
	private int prevFocusFramePos = -1;

	private Brick brickFollowMouse = null;
	private boolean disableBoxFollowMouse = false;
	private Dictionary<String, OBJModel> brickModels;
	private BrickFactory brickFactory;
	private PApplet pApplet;
	private Brick curBrick = null;
	private ArrayList<InteractiveFrame> tempInteractiveFrames;
	private boolean isGeneratedTempIF = false;
	private Brick prevBrick;
	private int prevIF;
	private ArrayList<GuiText> guiTexts;
	private ControlP5 controlP5;
	private Brick prevFollowMouseBrick;
	private boolean resetTempIFList = false;
	private boolean switchBrick = false;
	private ArrayList<Brick> undoListBricks;
	private int maxLayerIndex;
	private Vec currentColor;

	public GameManager(PApplet p) {
		super();
		this.pApplet = p;
	}

	/**
	 * @return the controlP5
	 */
	public ControlP5 getControlP5() {
		return controlP5;
	}

	/**
	 * @param controlP5
	 *            the controlP5 to set
	 */
	public void setControlP5(ControlP5 controlP5) {
		this.controlP5 = controlP5;
	}

	public Vec getCurrentColor() {
		return currentColor;
	}

	public void setCurrentColor(Vec currentColor) {
		this.currentColor = currentColor;
	}

	/**
	 * @return the planeLego
	 */
	public PlaneLego getPlaneLego() {
		return planeLego;
	}

	/**
	 * @param planeLego
	 *            the planeLego to set
	 */
	public void setPlaneLego(PlaneLego planeLego) {
		this.planeLego = planeLego;
	}

	/**
	 * @return the bricks
	 */
	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	/**
	 * @param bricks
	 *            the bricks to set
	 */
	public void setBricks(ArrayList<Brick> bricks) {
		this.bricks = bricks;
	}

	/**
	 * @return the interactiveFrameCollection
	 */
	public ArrayList<InteractiveFrame> getInteractiveFrameCollection() {
		return GameManager.interactiveFrameCollection;
	}

	/**
	 * @param interactiveFrameCollection
	 *            the interactiveFrameCollection to set
	 */
	public void setInteractiveFrameCollection(
			ArrayList<InteractiveFrame> interactiveFrameCollection) {
		GameManager.interactiveFrameCollection = interactiveFrameCollection;
	}

	/**
	 * @return the prevFollowMouseBrick
	 */
	public Brick getPrevFollowMouseBrick() {
		return prevFollowMouseBrick;
	}

	/**
	 * @param prevFollowMouseBrick
	 *            the prevFollowMouseBrick to set
	 */
	public void setPrevFollowMouseBrick(Brick prevFollowMouseBrick) {
		this.prevFollowMouseBrick = prevFollowMouseBrick;
	}

	/**
	 * @return the curFocusFramePos
	 */
	public int getCurFocusFramePos() {
		return curFocusFramePos;
	}

	/**
	 * @param curFocusFramePos
	 *            the curFocusFramePos to set
	 */
	public void setCurFocusFramePos(int curFocusFramePos) {
		this.curFocusFramePos = curFocusFramePos;
	}

	/**
	 * @return the prevFocusFramePos
	 */
	public int getPrevFocusFramePos() {
		return prevFocusFramePos;
	}

	/**
	 * @param prevFocusFramePos
	 *            the prevFocusFramePos to set
	 */
	public void setPrevFocusFramePos(int prevFocusFramePos) {
		this.prevFocusFramePos = prevFocusFramePos;
	}

	/**
	 * @return the brickFollowMouse
	 */
	public Brick getBrickFollowMouse() {
		return brickFollowMouse;
	}

	/**
	 * @param brickFollowMouse
	 *            the brickFollowMouse to set
	 */
	public void setBrickFollowMouse(Brick brickFollowMouse) {
		this.brickFollowMouse = brickFollowMouse;
	}

	/**
	 * @return the brickModels
	 */
	public Dictionary<String, OBJModel> getBrickModels() {
		return brickModels;
	}

	/**
	 * @param brickModels
	 *            the brickModels to set
	 */
	public void setBrickModels(Dictionary<String, OBJModel> brickModels) {
		this.brickModels = brickModels;
	}

	/**
	 * @return the disableBoxFollowMouse
	 */
	public boolean isDisableBoxFollowMouse() {
		return disableBoxFollowMouse;
	}

	/**
	 * @param disableBoxFollowMouse
	 *            the disableBoxFollowMouse to set
	 */
	public void setDisableBoxFollowMouse(boolean disableBoxFollowMouse) {
		this.disableBoxFollowMouse = disableBoxFollowMouse;
	}

	/**
	 * @return the curBrick
	 */
	public Brick getCurBrick() {
		return curBrick;
	}

	/**
	 * @param curBrick
	 *            the curBrick to set
	 */
	public void setCurBrick(Brick curBrick) {
		this.curBrick = curBrick;
	}

	/**
	 * @return the brickFactory
	 */
	public BrickFactory getBrickFactory() {
		return brickFactory;
	}

	/**
	 * @param brickFactory
	 *            the brickFactory to set
	 */
	public void setBrickFactory(BrickFactory brickFactory) {
		this.brickFactory = brickFactory;
	}

	/**
	 * @return the tempInteractiveFrames
	 */
	public ArrayList<InteractiveFrame> getTempInteractiveFrames() {
		return tempInteractiveFrames;
	}

	/**
	 * @param tempInteractiveFrames
	 *            the tempInteractiveFrames to set
	 */
	public void setTempInteractiveFrames(
			ArrayList<InteractiveFrame> tempInteractiveFrames) {
		this.tempInteractiveFrames = tempInteractiveFrames;
	}

	/**
	 * @return the isGeneratedTempIF
	 */
	public boolean isGeneratedTempIF() {
		return isGeneratedTempIF;
	}

	/**
	 * @param isGeneratedTempIF
	 *            the isGeneratedTempIF to set
	 */
	public void setGeneratedTempIF(boolean isGeneratedTempIF) {
		this.isGeneratedTempIF = isGeneratedTempIF;
	}

	/**
	 * @return the prevBrick
	 */
	public Brick getPrevBrick() {
		return prevBrick;
	}

	/**
	 * @param prevBrick
	 *            the prevBrick to set
	 */
	public void setPrevBrick(Brick prevBrick) {
		if (prevBrick == null)
			return;
		this.prevBrick = new Brick();
		this.prevBrick.setId(prevBrick.getId());
		this.prevBrick.setModelName(prevBrick.getModelName());
	}

	/**
	 * @return the guiTexts
	 */
	public ArrayList<GuiText> getGuiTexts() {
		return guiTexts;
	}

	/**
	 * @param guiTexts
	 *            the guiTexts to set
	 */
	public void setGuiTexts(ArrayList<GuiText> guiTexts) {
		this.guiTexts = guiTexts;
	}

	public ArrayList<Brick> getUndoListBricks() {
		return undoListBricks;
	}

	public void setUndoListBricks(ArrayList<Brick> undoListBricks) {
		this.undoListBricks = undoListBricks;
	}

	public int getMaxLayerIndex() {
		return maxLayerIndex;
	}

	public void setMaxLayerIndex(int maxLayerIndex) {
		this.maxLayerIndex = maxLayerIndex;
	}

	/**
	 * @return the prevIF
	 */
	public int getPrevIF() {
		return prevIF;
	}

	/**
	 * @param prevIF
	 *            the prevIF to set
	 */
	public void setPrevIF(int prevIF) {
		this.prevIF = prevIF;
	}

	public void setup() {
		brickFactory = new BrickFactory();
		planeLego = new PlaneLego();
		planeLego.setup();
		bricks = new ArrayList<Brick>();
		undoListBricks = new ArrayList<Brick>();
		GameManager.interactiveFrameCollection = new ArrayList<InteractiveFrame>();
		setupBrickModel();
		setupBrickFactory();
		// curBrick = new Brick_2x1();
		// curBrick.setModelName(Util.DEFAULT_BRICK_NAME);
		// curBrick.setModel(brickModels.get(Util.DEFAULT_BRICK_NAME));
		curBrick = new Roof_tile_2x2();
		curBrick.setModelName(Util.MODEL_NAME_LIST.get(1));
		curBrick.setModel(brickModels.get(Util.MODEL_NAME_LIST.get(1)));
		tempInteractiveFrames = new ArrayList<InteractiveFrame>();
		maxLayerIndex = 0;
		currentColor = new Vec(255, 0, 0);
	}

	/*
	 * public void drawGUI() { controlP5 = new ControlP5(pApplet);
	 * 
	 * // description : a bang controller triggers an event when pressed. //
	 * parameters : name, x, y, width, height controlP5.addBang("bang1", 10, 10,
	 * 20, 20);
	 * 
	 * // description : a button executes after release // parameters : name,
	 * value (float), x, y, width, height controlP5.addButton("button1", 1, 70,
	 * 10, 60, 20);
	 * 
	 * // description : a toggle can have two states, true and false // where
	 * true has the value 1 and false is 0. // parameters : name, default value
	 * (boolean), x, y, width, height controlP5.addToggle("toggle1", false, 170,
	 * 10, 20, 20);
	 * 
	 * // description : a slider is either used horizontally or vertically. //
	 * width is bigger, you get a horizontal slider // height is bigger, you get
	 * a vertical slider. // parameters : name, minimum, maximum, default value
	 * (float), x, y, // width, height controlP5.addSlider("slider1", 0, 255,
	 * 128, 10, 80, 10, 100); controlP5.addSlider("slider2", 0, 255, 128, 70,
	 * 80, 100, 10);
	 * 
	 * // description : round turning dial knob // parameters : name, minimum,
	 * maximum, default value (float, x, y, // diameter
	 * controlP5.addKnob("knob1", 0, 360, 0, 70, 120, 50);
	 * 
	 * // parameters : name, default value (float), x, y, width, height
	 * controlP5.addNumberbox("numberbox1", 50, 170, 120, 60, 14); }
	 */

	private void setupBrickModel() {
		brickModels = new Hashtable<String, OBJModel>();
		// TODO Auto-generated method stub
		for (int i = 0; i < Util.MODEL_NAME_LIST.size(); i++) {
			OBJModel objModel = new OBJModel(pApplet);
			objModel.setDrawMode(PConstants.POLYGON);
			objModel.load(Util.MODEL_NAME_LIST.get(i) + ".obj");
			objModel.enableDebug();
			objModel.scale(Util.MODEL_SCALE);

			objModel.translateToCenter();
			brickModels.put(Util.MODEL_NAME_LIST.get(i), objModel);
		}
	}

	private void setupBrickFactory() {
		for (int i = 0; i < Util.MODEL_NAME_LIST.size(); i++) {
			try {
				brickFactory.registerBrick(Util.MODEL_NAME_LIST.get(i),
						Class.forName(Util.MODEL_NAME_LIST.get(i)));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void updateInteractiveFrameCollection(Brick brick) {
		// TODO Auto-generated method stub
		for (int i = 0; i < brick.getDotInteractiveFrameList().size(); i++) {
			GameManager.interactiveFrameCollection.add(brick
					.getDotInteractiveFrameList().get(i));
		}
	}

	public void generateInteractiveFrameForSpecialCase2(Brick brick, Scene scene) {
		if (isGeneratedTempIF) {
			return;
		}

		if (resetTempIFList)
			resetTempInteractiveFrame(scene);

		// tempInteractiveFrames = new ArrayList<InteractiveFrame>();
		if (!switchBrick)
			for (int i = 0; i < bricks.size(); i++) {
				bricks.get(i).generateInteractiveFrameForSpecialCase(
						brickFollowMouse, tempInteractiveFrames);
			}
		else {
			for (int i = 0; i < bricks.size(); i++) {
				bricks.get(i).generateInteractiveFrameForSpecialCase(brick,
						tempInteractiveFrames);
			}
		}

		resetTempIFList = false;

	}

	public boolean isSwitchBrick() {
		return switchBrick;
	}

	public void setSwitchBrick(boolean switchBrick) {
		this.switchBrick = switchBrick;
	}

	public boolean isResetTempIFList() {
		return resetTempIFList;
	}

	public void setResetTempIFList(boolean resetTempIFList) {
		this.resetTempIFList = resetTempIFList;
	}

	private void resetTempInteractiveFrame(Scene scene) {
		for (int i = 0; i < tempInteractiveFrames.size(); i++) {
			tempInteractiveFrames.get(i).removeFromAgentPool(
					scene.motionAgent());
			tempInteractiveFrames.remove(i);
		}

		tempInteractiveFrames = new ArrayList<InteractiveFrame>();
	}

	public void generateInteractiveFrameForSpecialCase(Brick baseBrick,
			int positionOfIF) {
		if (isGeneratedTempIF) {
			return;
		}

		for (int i = 0; i < tempInteractiveFrames.size(); i++) {
		}

		tempInteractiveFrames = new ArrayList<InteractiveFrame>();
		if (baseBrick == null)
			return;
		int posY = (int) (positionOfIF / baseBrick.getSizeBrick().x());
		int posX = (int) (positionOfIF % baseBrick.getSizeBrick().x());

		if (posX == 0) {

			if (posY == 0) {
				// Generate base Brick
				for (int i = (int) (brickFollowMouse.getSizeBrick().x()); i > 0; i--) {
					for (int j = (int) (brickFollowMouse.getSizeBrick().y()); j > 0; j--) {
						if (i == (int) (brickFollowMouse.getSizeBrick().x())
								&& j == (int) (brickFollowMouse.getSizeBrick()
										.y()))
							continue;
						else {
							InteractiveFrame iframe1 = new InteractiveFrame(
									Util.CURRENT_SCENE, null);
							LocalConstraint XAxis = new LocalConstraint();
							XAxis.setTranslationConstraint(
									AxisPlaneConstraint.Type.FORBIDDEN,
									new Vec(0.0f, 0.0f, 0.0f));
							XAxis.setRotationConstraint(
									AxisPlaneConstraint.Type.AXIS, new Vec(
											1.0f, 0.0f, 0.0f));
							iframe1.setConstraint(XAxis);
							iframe1.setScaling(Util.BRICK_SIZE);
							iframe1.setTranslation(baseBrick.translate.x()
									- (brickFollowMouse.getSizeBrick().x() - i)
									* Util.BRICK_SIZE, baseBrick.translate.y()
									- (brickFollowMouse.getSizeBrick().y() - j)
									* Util.BRICK_SIZE, baseBrick
									.getDotInteractiveFrameList().get(0)
									.position().z());
							iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
							tempInteractiveFrames.add(iframe1);

						}
					}
				}
			} else {
				// Generate left
				for (int i = (int) (brickFollowMouse.getSizeBrick().x()); i > 0; i--) {
					if (i == (int) (brickFollowMouse.getSizeBrick().x()))
						continue;
					else {
						InteractiveFrame iframe1 = new InteractiveFrame(
								Util.CURRENT_SCENE, null);
						LocalConstraint XAxis = new LocalConstraint();
						XAxis.setTranslationConstraint(
								AxisPlaneConstraint.Type.FORBIDDEN, new Vec(
										0.0f, 0.0f, 0.0f));
						XAxis.setRotationConstraint(
								AxisPlaneConstraint.Type.AXIS, new Vec(1.0f,
										0.0f, 0.0f));
						iframe1.setConstraint(XAxis);
						iframe1.setScaling(Util.BRICK_SIZE);
						iframe1.setTranslation(baseBrick.translate.x()
								- (brickFollowMouse.getSizeBrick().x() - i)
								* Util.BRICK_SIZE, baseBrick.translate.y(),
								baseBrick.getDotInteractiveFrameList().get(0)
										.position().z());
						iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
						tempInteractiveFrames.add(iframe1);

					}
				}
			}
		} else {
			if (posY == 0) {
				// Generate top
				for (int i = (int) (brickFollowMouse.getSizeBrick().y()); i > 0; i--) {
					if (i == (int) (brickFollowMouse.getSizeBrick().y()))
						continue;
					else {
						InteractiveFrame iframe1 = new InteractiveFrame(
								Util.CURRENT_SCENE, null);
						LocalConstraint XAxis = new LocalConstraint();
						XAxis.setTranslationConstraint(
								AxisPlaneConstraint.Type.FORBIDDEN, new Vec(
										0.0f, 0.0f, 0.0f));
						XAxis.setRotationConstraint(
								AxisPlaneConstraint.Type.AXIS, new Vec(1.0f,
										0.0f, 0.0f));
						iframe1.setConstraint(XAxis);
						iframe1.setScaling(Util.BRICK_SIZE);
						iframe1.setTranslation(baseBrick.translate.x() + posX
								* Util.BRICK_SIZE, baseBrick.translate.y()
								- (brickFollowMouse.getSizeBrick().y() - i)
								* Util.BRICK_SIZE, baseBrick
								.getDotInteractiveFrameList().get(0).position()
								.z());
						iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
						tempInteractiveFrames.add(iframe1);
					}
				}
			} else {
				// Don't do anything else
				// tempInteractiveFrames = new ArrayList<InteractiveFrame>();
			}
		}

		// isGeneratedTempIF = true;

	}

	public void undo() {
		if (bricks.size() <= 0)
			return;

		if (undoListBricks.size() > 0
				&& undoListBricks.get(undoListBricks.size() - 1).isDeleteFlag()) {
			Brick brick = undoListBricks.get(undoListBricks.size() - 1);
			brick.setDeleteFlag(true);
			undoListBricks.remove(brick);
			brick.generateInteractiveFrame();
			bricks.add(brick);
			return;
		}

		Brick lastBrick = bricks.get(bricks.size() - 1);
		lastBrick.setDeleteFlag(false);
		removeBrickFromGame(lastBrick);
		bricks.remove(bricks.size() - 1);
	}

	private void removeBrickFromGame(Brick lastBrick) {
		for (int i = 0; i < lastBrick.getDotInteractiveFrameList().size(); i++) {
			lastBrick.getDotInteractiveFrameList().get(i)
					.removeFromAgentPool(Util.CURRENT_SCENE.motionAgent());
			lastBrick.getDotInteractiveFrameList().remove(i);
		}

		lastBrick.setDotInteractiveFrameList(new ArrayList<InteractiveFrame>());

		undoListBricks.add(lastBrick);

	}

	public void delete(int brickSelected) {
		Brick selectedBrick = bricks.get(brickSelected);
		selectedBrick.setDeleteFlag(true);
		removeBrickFromGame(selectedBrick);
		bricks.remove(brickSelected);
	}

	public void redo() {
		if (bricks.get(bricks.size() - 1).isDeleteFlag()) {
			delete(bricks.size() - 1);
			return;
		}
		if (undoListBricks.size() > 0) {
			Brick brick = undoListBricks.get(undoListBricks.size() - 1);
			undoListBricks.remove(brick);
			brick.generateInteractiveFrame();
			bricks.add(brick);

		}
	}
}
