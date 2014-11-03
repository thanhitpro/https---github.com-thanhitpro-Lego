import java.util.ArrayList;

import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import saito.objloader.OBJModel;
import xml.XmlBoxCollider;
import xml.XmlBrick;
import xml.XmlCenterPositionOfDot;
import xml.XmlInteractiveFrame;
import xml.XmlRotation;

public class Brick implements DrawableObject {
	protected OBJModel model;
	protected String modelName;
	protected ArrayList<InteractiveFrame> dotInteractiveFrameList;
	protected ArrayList<Vec> centerPositionOfDot;
	protected float scaleRatio;
	protected Vec rotate;
	protected XmlBrick xmlBrick;
	/**
	 * Position of the brick on Oxyz space
	 */
	protected Vec translate;
	protected BoxCollider boxCollider;
	/**
	 * When drawing a brick, according to the model, its must translate a little
	 * more
	 */
	protected Vec calibrateVec;
	/**
	 * When a brick rotate, sometime, the position will be changed like 2x1
	 * brick This info helps to re-translate to correct position
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

	public void generateInteractiveFrame(
			ArrayList<XmlInteractiveFrame> xmlInteractiveFrames) {
		for (int i = 0; i < xmlInteractiveFrames.size(); i++) {
			XmlInteractiveFrame xmlInteractiveFrame = xmlInteractiveFrames
					.get(i);

			InteractiveFrame iframe1 = new InteractiveFrame(Util.CURRENT_SCENE,
					null);
			LocalConstraint XAxis = new LocalConstraint();
			XAxis.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,
					new Vec(0.0f, 0.0f, 0.0f));
			XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS, new Vec(
					1.0f, 0.0f, 0.0f));
			iframe1.setConstraint(XAxis);
			iframe1.setScaling(Util.BRICK_SIZE);
			iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
			iframe1.setTranslation(translate.x() + Util.BRICK_SIZE
					* xmlInteractiveFrame.getPosition().x()
					+ xmlBrick.getExtraPositionVec().x(), translate.y()
					+ Util.BRICK_SIZE * xmlInteractiveFrame.getPosition().y()
					+ xmlBrick.getExtraPositionVec().y(), translate.z()
					+ Util.BRICK_SIZE * xmlInteractiveFrame.getPosition().z()
					+ xmlBrick.getExtraPositionVec().z());
			dotInteractiveFrameList.add(iframe1);
		}
	}

	public void generateBoxCollider() {
		boxCollider = new BoxCollider();
		generateBoxCollider(xmlBrick.getRotations().get(timesRotate)
				.getBoxColliders());
	}

	public void generateBoxCollider(ArrayList<XmlBoxCollider> xmlBoxColliders) {
		boxCollider = new BoxCollider();
		ArrayList<Box> boxs = new ArrayList<>();
		for (int i = 0; i < xmlBoxColliders.size(); i++) {
			XmlBoxCollider xmlBoxCollider = xmlBoxColliders.get(i);
			Box box = new Box();
			box.setPosition(Vec.add(translate,
					Vec.multiply(xmlBoxCollider.getPosition(), Util.BRICK_SIZE)));
			box.setWidth(Util.BRICK_SIZE * xmlBoxCollider.getSize().x());
			box.setHeight(Util.BRICK_SIZE * xmlBoxCollider.getSize().y());
			box.setDepth(Util.BRICK_SIZE * xmlBoxCollider.getSize().z());
			boxs.add(box);
		}
		boxCollider.setListBox(boxs);
	}

	public void generateCenterPositionOfDot() {
		centerPositionOfDot = new ArrayList<Vec>();
		for (int i = 0; i < dotInteractiveFrameList.size(); i++) {
			Vec centerPosition = new Vec(dotInteractiveFrameList.get(i)
					.position().x()
					+ Util.BRICK_SIZE / 2, dotInteractiveFrameList.get(i)
					.position().y()
					+ Util.BRICK_SIZE / 2, dotInteractiveFrameList.get(i)
					.position().z()
					- Util.BRICK_SIZE / 2);
			centerPositionOfDot.add(centerPosition);
		}
	}

	public void generateCenterPositionOfDot(
			ArrayList<XmlCenterPositionOfDot> xmlCenterPositionOfDots) {
		centerPositionOfDot = new ArrayList<Vec>();
		for (int i = 0; i < xmlCenterPositionOfDots.size(); i++) {
			XmlCenterPositionOfDot xmlCenterPositionOfDot = xmlCenterPositionOfDots
					.get(i);

			Vec centerPosition = new Vec(translate.x() + Util.BRICK_SIZE
					* xmlCenterPositionOfDot.getPosition().x()
					+ xmlBrick.getExtraPositionVec().x() + Util.BRICK_SIZE / 2,
					translate.y() + Util.BRICK_SIZE
							* xmlCenterPositionOfDot.getPosition().y()
							+ xmlBrick.getExtraPositionVec().y()
							+ Util.BRICK_SIZE / 2, translate.z()
							+ Util.BRICK_SIZE
							* xmlCenterPositionOfDot.getPosition().z()
							+ xmlBrick.getExtraPositionVec().z()
							+ Util.BRICK_SIZE / 2);
			centerPositionOfDot.add(centerPosition);
		}
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
		if (timesRotate == (xmlBrick.getNumberOfTimeRotation() - 1)) {
			timesRotate = 0;
			// sizeBrick = new Vec(1, 4, 1);
		} else {
			timesRotate++;
			// sizeBrick = new Vec(4, 1, 1);
		}

		sizeBrick = xmlBrick.getRotations().get(timesRotate).getSizeBrick();
	}

	public void decreaseTimesRotate() {
		if (timesRotate == 0) {
			timesRotate = xmlBrick.getNumberOfTimeRotation();
			// sizeBrick = new Vec(1, 4, 1);
		} else {
			timesRotate--;
			// sizeBrick = new Vec(4, 1, 1);
		}

		sizeBrick = xmlBrick.getRotations().get(timesRotate).getSizeBrick();
	}

	public void generateInteractiveFrameForSpecialCase2(Brick brickFollowMouse,
			ArrayList<InteractiveFrame> tempInteractiveFrames) {

	}

	public void generateInteractiveFrameForSpecialCase(Brick brickFollowMouse,
			ArrayList<InteractiveFrame> tempInteractiveFrames) {
		int w = (int) brickFollowMouse.getSizeBrick().x();
		int h = (int) brickFollowMouse.getSizeBrick().y();

		ArrayList<InteractiveFrame> tempIF = new ArrayList<InteractiveFrame>();

		for (int k = 0; k < xmlBrick.getRotations().get(timesRotate)
				.getInteractiveFrames().size(); k++) {
			XmlInteractiveFrame xmlInteractiveFrame = xmlBrick.getRotations()
					.get(timesRotate).getInteractiveFrames().get(k);
			Vec startGeneratePosition = new Vec(this.translate.x()
					+ xmlInteractiveFrame.getPosition().x() * Util.BRICK_SIZE,
					this.translate.y() + xmlInteractiveFrame.getPosition().y()
							* Util.BRICK_SIZE, this.translate.z()
							+ xmlInteractiveFrame.getPosition().z()
							* Util.BRICK_SIZE);

			for (int i = -(w - 1); i <= 0; i++) {
				for (int j = -(h - 1); j <= 0; j++) {
					if (i == j && j == 0)
						continue;
					Vec framePosition = new Vec(startGeneratePosition.x() + i
							* Util.BRICK_SIZE, startGeneratePosition.y() + j
							* Util.BRICK_SIZE, this
							.getDotInteractiveFrameList().get(0).position().z());

					if (Util.CheckExistInteractiveFrame(tempInteractiveFrames,
							framePosition))
						continue;

					InteractiveFrame iframe1 = new InteractiveFrame(
							Util.CURRENT_SCENE, null);
					LocalConstraint XAxis = new LocalConstraint();
					XAxis.setTranslationConstraint(
							AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f,
									0.0f, 0.0f));
					XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
							new Vec(1.0f, 0.0f, 0.0f));
					iframe1.setConstraint(XAxis);
					iframe1.setScaling(Util.BRICK_SIZE);
					iframe1.setTranslation(framePosition.x(),
							framePosition.y(), framePosition.z());
					iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
					tempInteractiveFrames.add(iframe1);
					tempIF.add(iframe1);
				}
			}
		}

		if (tempIF.size() > 0)
			Util.interactiveFrameDictionary.put(
					brickFollowMouse.getModelName(), tempIF);

	}

	/**
	 * @return the sizeBrick
	 */
	public Vec getSizeBrick() {
		return sizeBrick;
	}

	/**
	 * @param sizeBrick
	 *            the sizeBrick to set
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
		sizeBrick = xmlBrick.getRotations().get(timesRotate).getSizeBrick();
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	public void calibrateAfterRotate() {
		translateForDraw = xmlBrick.getRotations().get(timesRotate)
				.getTranslateForDraw();
	}

	public void generateInitData() {
		XmlRotation xmlRotation = xmlBrick.getRotations().get(timesRotate);
		generateInteractiveFrame(xmlRotation.getInteractiveFrames());
		generateBoxCollider(xmlRotation.getBoxColliders());
		generateCenterPositionOfDot(xmlRotation.getCenterPostionOfDots());
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

	public XmlBrick getXmlBrick() {
		return xmlBrick;
	}

	public void setXmlBrick(XmlBrick xmlBrick) {
		this.xmlBrick = xmlBrick;
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
