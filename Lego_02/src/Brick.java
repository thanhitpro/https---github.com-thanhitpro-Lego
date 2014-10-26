import java.util.ArrayList;

import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import saito.objloader.OBJModel;

public class Brick implements DrawableObject {
	protected OBJModel model;
	protected String modelName;
	protected ArrayList<InteractiveFrame> dotInteractiveFrameList;
	protected ArrayList<Vec> centerPositionOfDot;
	protected float scaleRatio;
	protected Vec rotate;
	/**
	 * Position of the brick on Oxyz space
	 */
	protected Vec translate;
	protected BoxCollider boxCollider;
	/**
	 * When drawing a brick, according to the model, its must translate 
	 * a little more
	 */
	protected Vec calibrateVec;
	/**
	 * When a brick rotate, sometime, the position will be changed like 2x1 brick
	 * This info helps to re-translate to correct position 
	 */
	protected Vec translateForDraw;
	/**
	 * Fist time initialize this brick
	 */
	protected boolean firstInit = false;
	protected Vec originalTranslate;
	protected int timesRotate = 0;
	protected Vec sizeBrick;
	protected int id;

	/**
	 * @return the model
	 */
	public OBJModel getModel() {
		return model;
	}

	/**
	 * @param model
	 *            the model to set
	 */
	public void setModel(OBJModel model) {
		this.model = model;
	}

	/**
	 * @return the dotInteractiveFrameList
	 */
	public ArrayList<InteractiveFrame> getDotInteractiveFrameList() {
		return dotInteractiveFrameList;
	}

	/**
	 * @param dotInteractiveFrameList
	 *            the dotInteractiveFrameList to set
	 */
	public void setDotInteractiveFrameList(
			ArrayList<InteractiveFrame> dotInteractiveFrameList) {
		this.dotInteractiveFrameList = dotInteractiveFrameList;
	}

	/**
	 * @return the scaleRatio
	 */
	public float getScaleRatio() {
		return scaleRatio;
	}

	/**
	 * @param scaleRatio
	 *            the scaleRatio to set
	 */
	public void setScaleRatio(float scaleRatio) {
		this.scaleRatio = scaleRatio;
	}

	/**
	 * @return the boxCollider
	 */
	public BoxCollider getBoxCollider() {
		return boxCollider;
	}

	/**
	 * @param boxCollider
	 *            the boxCollider to set
	 */
	public void setBoxCollider(BoxCollider boxCollider) {
		this.boxCollider = boxCollider;
	}

	/**
	 * @return the rotate
	 */
	public Vec getRotate() {
		return rotate;
	}

	/**
	 * @param rotate
	 *            the rotate to set
	 */
	public void setRotate(Vec rotate) {
		this.rotate = new Vec(rotate.x(), rotate.y(), rotate.z());
	}

	/**
	 * @return the translate
	 */
	public Vec getTranslate() {
		return translate;
	}

	/**
	 * @param translate
	 *            the translate to set
	 */
	public void setTranslate(Vec translate) {
		this.translate = new Vec(translate.x(), translate.y(), translate.z());
	}

	/**
	 * @return the modelName
	 */
	public String getModelName() {
		return modelName;
	}

	/**
	 * @param modelName
	 *            the modelName to set
	 */
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	/**
	 * @return the centerPositionOfDot
	 */
	public ArrayList<Vec> getCenterPositionOfDot() {
		return centerPositionOfDot;
	}

	/**
	 * @param centerPositionOfDot
	 *            the centerPositionOfDot to set
	 */
	public void setCenterPositionOfDot(ArrayList<Vec> centerPositionOfDot) {
		this.centerPositionOfDot = centerPositionOfDot;
	}

	/*
	 * List function for override
	 */

	/**
	 * @return the translateForDraw
	 */
	public Vec getTranslateForDraw() {
		return translateForDraw;
	}

	/**
	 * @param translateForDraw
	 *            the translateForDraw to set
	 */
	public void setTranslateForDraw(Vec translateForDraw) {
		if (translateForDraw == null)
			return;
		this.translateForDraw = new Vec(translateForDraw.x(),
				translateForDraw.y(), translateForDraw.z());
	}

	/**
	 * @return the firstInit
	 */
	public boolean isFirstInit() {
		return firstInit;
	}

	/**
	 * @param firstInit
	 *            the firstInit to set
	 */
	public void setFirstInit(boolean firstInit) {
		this.firstInit = firstInit;
	}

	public void generateInteractiveFrame() {
		dotInteractiveFrameList = new ArrayList<InteractiveFrame>();

	}

	public void generateBoxCollider() {
		boxCollider = new BoxCollider();
	}

	public void generateCenterPositionOfDot() {
		centerPositionOfDot = new ArrayList<Vec>();
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub

	}

	public void calibrate() {
		translateForDraw = Vec.add(translate, calibrateVec);
	}

	/**
	 * @return the calibrateVec
	 */
	public Vec getCalibrateVec() {
		return calibrateVec;
	}

	/**
	 * @param calibrateVec
	 *            the calibrateVec to set
	 */
	public void setCalibrateVec(Vec calibrateVec) {
		this.calibrateVec = new Vec(calibrateVec.x(), calibrateVec.y(),
				calibrateVec.z());
	}

	/**
	 * @return the originalTranslate
	 */
	public Vec getOriginalTranslate() {
		return originalTranslate;
	}

	/**
	 * @param originalTranslate
	 *            the originalTranslate to set
	 */
	public void setOriginalTranslate(Vec originalTranslate) {
		this.originalTranslate = new Vec(originalTranslate.x(),
				originalTranslate.y(), originalTranslate.z());
	}

	public void increaseTimesRotate() {
	}

	public void decreaseTimesRotate() {
	}
	
	public void generateInteractiveFrameForSpecialCase2(Brick brickFollowMouse, ArrayList<InteractiveFrame> tempInteractiveFrames) {
		
	}

	/**
	 * @return the sizeBrick
	 */
	public Vec getSizeBrick() {
		return sizeBrick;
	}

	/**
	 * @param sizeBrick the sizeBrick to set
	 */
	public void setSizeBrick(Vec sizeBrick) {
		this.sizeBrick = Util.clone(sizeBrick);
	}

	/**
	 * @return the timesRotate
	 */
	public int getTimesRotate() {
		return timesRotate;
	}

	/**
	 * @param timesRotate
	 *            the timesRotate to set
	 */
	public void setTimesRotate(int timesRotate) {
		this.timesRotate = timesRotate;
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void calibrateAfterRotate() {

	}

	public Brick() {
		super();
		dotInteractiveFrameList = new ArrayList<InteractiveFrame>();
	}
	
	

	public Brick(Brick input) {
		super();
		this.model = input.model;
		this.modelName = input.modelName;
		this.dotInteractiveFrameList = input.dotInteractiveFrameList;
		this.centerPositionOfDot = input.centerPositionOfDot;
		this.scaleRatio = input.scaleRatio;
		this.rotate = input.rotate;
		this.translate = input.translate;
		this.boxCollider = input.boxCollider;
		this.calibrateVec = input.calibrateVec;
		this.translateForDraw = input.translateForDraw;
		this.firstInit = input.firstInit;
		this.originalTranslate = input.originalTranslate;
		this.timesRotate = input.timesRotate;
		this.id = -2;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		model.draw();
	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked() {
		// TODO Auto-generated method stub

	}

}
