import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import processing.core.PApplet;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import saito.objloader.OBJModel;
import controlP5.ControlP5;

public class GameManager {
	private PlaneLego planeLego;
	private ArrayList<Brick> bricks;
	private ArrayList<InteractiveFrame> interactiveFrameCollection;

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
		return interactiveFrameCollection;
	}

	/**
	 * @param interactiveFrameCollection
	 *            the interactiveFrameCollection to set
	 */
	public void setInteractiveFrameCollection(
			ArrayList<InteractiveFrame> interactiveFrameCollection) {
		this.interactiveFrameCollection = interactiveFrameCollection;
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
		interactiveFrameCollection = new ArrayList<InteractiveFrame>();
		setupBrickModel();
		setupBrickFactory();
		// curBrick = new Brick_2x1();
		// curBrick.setModelName(Util.DEFAULT_BRICK_NAME);
		// curBrick.setModel(brickModels.get(Util.DEFAULT_BRICK_NAME));
		curBrick = new Roof_tile_2x2();
		curBrick.setModelName(Util.MODEL_NAME_LIST.get(1));
		curBrick.setModel(brickModels.get(Util.MODEL_NAME_LIST.get(1)));
		tempInteractiveFrames = new ArrayList<InteractiveFrame>();
		guiTexts = new ArrayList<GuiText>();
	}

	public void drawGUI() {
		controlP5 = new ControlP5(pApplet);

		// description : a bang controller triggers an event when pressed.
		// parameters : name, x, y, width, height
		controlP5.addBang("bang1", 10, 10, 20, 20);

		// description : a button executes after release
		// parameters : name, value (float), x, y, width, height
		controlP5.addButton("button1", 1, 70, 10, 60, 20);

		// description : a toggle can have two states, true and false
		// where true has the value 1 and false is 0.
		// parameters : name, default value (boolean), x, y, width, height
		controlP5.addToggle("toggle1", false, 170, 10, 20, 20);

		// description : a slider is either used horizontally or vertically.
		// width is bigger, you get a horizontal slider
		// height is bigger, you get a vertical slider.
		// parameters : name, minimum, maximum, default value (float), x, y,
		// width, height
		controlP5.addSlider("slider1", 0, 255, 128, 10, 80, 10, 100);
		controlP5.addSlider("slider2", 0, 255, 128, 70, 80, 100, 10);

		// description : round turning dial knob
		// parameters : name, minimum, maximum, default value (float, x, y,
		// diameter
		controlP5.addKnob("knob1", 0, 360, 0, 70, 120, 50);

		// parameters : name, default value (float), x, y, width, height
		controlP5.addNumberbox("numberbox1", 50, 170, 120, 60, 14);
	}

	private void setupBrickModel() {
		brickModels = new Hashtable<String, OBJModel>();
		// TODO Auto-generated method stub
		for (int i = 0; i < Util.MODEL_NAME_LIST.size(); i++) {
			OBJModel objModel = new OBJModel(pApplet);
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
			interactiveFrameCollection.add(brick.getDotInteractiveFrameList()
					.get(i));
		}
	}

	public void generateInteractiveFrameForSpecialCase2(Brick baseBrick) {
		if (isGeneratedTempIF) {
			return;
		}

		// tempInteractiveFrames = new ArrayList<InteractiveFrame>();
		baseBrick.generateInteractiveFrameForSpecialCase2(brickFollowMouse,
				tempInteractiveFrames);

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
}
