import java.util.ArrayList;

import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;

public class Brick_1x4 extends Brick {
	@Override
	public void generateInteractiveFrame() {
		// TODO Auto-generated method stub
		super.generateInteractiveFrame();

		InteractiveFrame iframe1 = new InteractiveFrame(Util.CURRENT_SCENE,
				null);
		LocalConstraint XAxis = new LocalConstraint();
		XAxis.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,
				new Vec(0.0f, 0.0f, 0.0f));
		XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS, new Vec(
				1.0f, 0.0f, 0.0f));
		iframe1.setConstraint(XAxis);
		iframe1.setScaling(Util.BRICK_SIZE);
		iframe1.setTranslation(
				translate.x(),
				translate.y(),
				translate.z()
						+ Util.BRICK_SIZE
						+ Util.EXTRA_POSITION_VEC.get(
								Util.MODEL_NAME_LIST.indexOf(modelName)).z());
		iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
		dotInteractiveFrameList.add(iframe1);
		int tempLength = (int) sizeBrick.x();
		if (timesRotate % 2 != 0) {
			tempLength = (int) sizeBrick.y();
		}

		for (int i = 1; i < tempLength; i++) {
			InteractiveFrame iframe2 = new InteractiveFrame(Util.CURRENT_SCENE,
					null);
			LocalConstraint XAxis1 = new LocalConstraint();
			XAxis1.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,
					new Vec(0.0f, 0.0f, 0.0f));
			XAxis1.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
					new Vec(1.0f, 0.0f, 0.0f));

			iframe2.setConstraint(XAxis1);
			iframe2.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
			if (timesRotate % 2 == 0) {
				iframe2.setTranslation(
						translate.x() + Util.BRICK_SIZE * i,
						translate.y(),
						translate.z()
								+ Util.BRICK_SIZE
								+ Util.EXTRA_POSITION_VEC
										.get(Util.MODEL_NAME_LIST
												.indexOf(modelName)).z());
			} else {
				iframe2.setTranslation(
						translate.x(),
						translate.y() + Util.BRICK_SIZE * i,
						translate.z()
								+ Util.BRICK_SIZE
								+ Util.EXTRA_POSITION_VEC
										.get(Util.MODEL_NAME_LIST
												.indexOf(modelName)).z());
			}
			dotInteractiveFrameList.add(iframe2);
		}
	}

	@Override
	public void generateBoxCollider() {
		// TODO Auto-generated method stub
		super.generateBoxCollider();
		ArrayList<Box> boxs = new ArrayList<>();
		Box box = new Box();
		box.setPosition(translate);
		if (timesRotate % 2 == 0) {
			box.setWidth(Util.BRICK_SIZE * 4);
			box.setHeight(Util.BRICK_SIZE);
		} else {
			box.setWidth(Util.BRICK_SIZE);
			box.setHeight(Util.BRICK_SIZE * 4);
		}
		box.setDepth(Util.BRICK_SIZE);
		boxs.add(box);
		boxCollider.setListBox(boxs);
	}

	@Override
	public void generateCenterPositionOfDot() {
		// TODO Auto-generated method stub
		super.generateCenterPositionOfDot();
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

	public Brick_1x4() {
		// TODO Auto-generated constructor stub
		super();
		modelName = "Brick_1x4";
		calibrateVec = Util.CALIBRATE_VEC_MODEL.get(Util.MODEL_NAME_LIST
				.indexOf(modelName));
		sizeBrick = new Vec(4, 1, 1);
	}

	@Override
	public void setTimesRotate(int timesRotate) {
		// TODO Auto-generated method stub
		super.setTimesRotate(timesRotate);
		if (timesRotate == 1) {
			sizeBrick = new Vec(1, 4, 1);
		} else {
			sizeBrick = new Vec(4, 1, 1);
		}
	}

	@Override
	public void calibrateAfterRotate() {
		// TODO Auto-generated method stub
		super.calibrateAfterRotate();
		if (timesRotate % 2 == 0) {
			translateForDraw = new Vec(-30, 30, 0);
		} else {
			translateForDraw = new Vec(0, 0, 0);
		}
	}

	@Override
	public void increaseTimesRotate() {
		// TODO Auto-generated method stub
		super.increaseTimesRotate();
		if (timesRotate == 0) {
			timesRotate = 1;
			sizeBrick = new Vec(1, 4, 1);
		} else {
			timesRotate = 0;
			sizeBrick = new Vec(4, 1, 1);
		}
	}

	@Override
	public void decreaseTimesRotate() {
		// TODO Auto-generated method stub
		super.decreaseTimesRotate();
		if (timesRotate == 0) {
			timesRotate = 1;
			sizeBrick = new Vec(1, 4, 1);
		} else {
			timesRotate = 0;
			sizeBrick = new Vec(4, 1, 1);
		}
	}

	@Override
	public void generateInteractiveFrameForSpecialCase2(Brick brickFollowMouse,
			ArrayList<InteractiveFrame> tempInteractiveFrames) {
		// TODO Auto-generated method stub
		super.generateInteractiveFrameForSpecialCase2(brickFollowMouse,
				tempInteractiveFrames);
		int w, h, wf, hf;
		Vec startGeneratePosition = new Vec(this.translate.x(),
				this.translate.y(), this.translate.z());
		ArrayList<InteractiveFrame> tempIF = new ArrayList<InteractiveFrame>();
		if (timesRotate == 0) {
			w = 4;
			h = 1;
		} else {
			w = 1;
			h = 4;
		}
		wf = (int) brickFollowMouse.getSizeBrick().x();
		hf = (int) brickFollowMouse.getSizeBrick().y();

		for (int i = (int) -(wf - 1); i < (w); i++) {
			for (int j = (int) -(hf - 1); j < 0; j++) {
				Vec framePosition = new Vec(startGeneratePosition.x() + i
						* Util.BRICK_SIZE, startGeneratePosition.y() + j
						* Util.BRICK_SIZE, this.getDotInteractiveFrameList()
						.get(0).position().z());
				if (Util.CheckExistInteractiveFrame(tempInteractiveFrames,
						framePosition))
					continue;
				InteractiveFrame iframe1 = new InteractiveFrame(
						Util.CURRENT_SCENE, null);
				LocalConstraint XAxis = new LocalConstraint();
				XAxis.setTranslationConstraint(
						AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f, 0.0f,
								0.0f));
				XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
						new Vec(1.0f, 0.0f, 0.0f));
				iframe1.setConstraint(XAxis);
				iframe1.setScaling(Util.BRICK_SIZE);
				iframe1.setTranslation(startGeneratePosition.x() + i
						* Util.BRICK_SIZE, startGeneratePosition.y() + j
						* Util.BRICK_SIZE, this.getDotInteractiveFrameList()
						.get(0).position().z());
				iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
				tempInteractiveFrames.add(iframe1);
				tempIF.add(iframe1);
			}
		}

		for (int i = (int) -(wf - 1); i < 0; i++) {
			for (int j = 0; j < (h); j++) {
				Vec framePosition = new Vec(startGeneratePosition.x() + i
						* Util.BRICK_SIZE, startGeneratePosition.y() + j
						* Util.BRICK_SIZE, this.getDotInteractiveFrameList()
						.get(0).position().z());
				if (Util.CheckExistInteractiveFrame(tempInteractiveFrames,
						framePosition))
					continue;
				InteractiveFrame iframe1 = new InteractiveFrame(
						Util.CURRENT_SCENE, null);
				LocalConstraint XAxis = new LocalConstraint();
				XAxis.setTranslationConstraint(
						AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f, 0.0f,
								0.0f));
				XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
						new Vec(1.0f, 0.0f, 0.0f));
				iframe1.setConstraint(XAxis);
				iframe1.setScaling(Util.BRICK_SIZE);
				iframe1.setTranslation(startGeneratePosition.x() + i
						* Util.BRICK_SIZE, startGeneratePosition.y() + j
						* Util.BRICK_SIZE, this.getDotInteractiveFrameList()
						.get(0).position().z());
				iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
				tempInteractiveFrames.add(iframe1);
				tempIF.add(iframe1);
			}
		}

		if (tempIF.size() > 0)
			Util.interactiveFrameDictionary.put(
					brickFollowMouse.getModelName(), tempIF);
	}
}
