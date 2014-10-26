import java.util.ArrayList;

import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;

public class Roof_tile_2x2 extends Brick {
	@Override
	public void generateBoxCollider() {
		// TODO Auto-generated method stub
		super.generateBoxCollider();
		ArrayList<Box> boxs = new ArrayList<>();
		Box box = new Box();
		box.setPosition(translate);
		box.setWidth(Util.BRICK_SIZE * 2);
		box.setHeight(Util.BRICK_SIZE * 2);
		box.setDepth(Util.BRICK_SIZE);
		boxs.add(box);
		boxCollider.setListBox(boxs);
	}

	@Override
	public void generateCenterPositionOfDot() {
		// TODO Auto-generated method stub
		super.generateCenterPositionOfDot();

		for (int i = 0; i < sizeBrick.x(); i++) {
			for (int j = 0; j < sizeBrick.y(); j++) {
				for (int k = 0; k < sizeBrick.z(); k++) {
					Vec centerPosition = new Vec(translate.x()
							+ Util.BRICK_SIZE / 2 + i * Util.BRICK_SIZE,
							translate.y() + Util.BRICK_SIZE / 2 + j
									* Util.BRICK_SIZE, translate.z()
									+ Util.BRICK_SIZE / 2 + k * Util.BRICK_SIZE);
					centerPositionOfDot.add(centerPosition);
				}
			}
		}
	}

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
		iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
		switch (timesRotate) {
		case 0:
			iframe1.setTranslation(
					translate.x(),
					translate.y(),
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		case 1:
			iframe1.setTranslation(
					translate.x() + Util.BRICK_SIZE,
					translate.y(),
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		case 2:
			iframe1.setTranslation(
					translate.x() + Util.BRICK_SIZE,
					translate.y() + Util.BRICK_SIZE,
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		case 3:
			iframe1.setTranslation(
					translate.x(),
					translate.y() + Util.BRICK_SIZE,
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		}

		InteractiveFrame iframe2 = new InteractiveFrame(Util.CURRENT_SCENE,
				null);
		LocalConstraint XAxis1 = new LocalConstraint();
		XAxis1.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,
				new Vec(0.0f, 0.0f, 0.0f));
		XAxis1.setRotationConstraint(AxisPlaneConstraint.Type.AXIS, new Vec(
				1.0f, 0.0f, 0.0f));
		iframe2.setConstraint(XAxis1);
		iframe2.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
		switch (timesRotate) {
		case 0:
			iframe2.setTranslation(
					translate.x() + Util.BRICK_SIZE,
					translate.y(),
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		case 1:
			iframe2.setTranslation(
					translate.x() + Util.BRICK_SIZE,
					translate.y() + Util.BRICK_SIZE,
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		case 2:
			iframe2.setTranslation(
					translate.x(),
					translate.y() + Util.BRICK_SIZE,
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		case 3:
			iframe2.setTranslation(
					translate.x(),
					translate.y(),
					translate.z()
							+ Util.BRICK_SIZE
							+ Util.EXTRA_POSITION_VEC.get(
									Util.MODEL_NAME_LIST.indexOf(modelName))
									.z());
			break;
		}

		dotInteractiveFrameList.add(iframe1);
		dotInteractiveFrameList.add(iframe2);

	}

	public Roof_tile_2x2() {
		// TODO Auto-generated constructor stub
		super();
		modelName = "Roof_tile_2x2";
		calibrateVec = Util.CALIBRATE_VEC_MODEL.get(Util.MODEL_NAME_LIST
				.indexOf(modelName));
		sizeBrick = new Vec(2, 2, 1);
	}

	@Override
	public void calibrateAfterRotate() {
		// TODO Auto-generated method stub
		super.calibrateAfterRotate();
	}

	@Override
	public void increaseTimesRotate() {
		// TODO Auto-generated method stub
		super.increaseTimesRotate();

		if (timesRotate == 3) {
			timesRotate = 0;
		} else {
			timesRotate++;
		}
	}

	@Override
	public void decreaseTimesRotate() {
		// TODO Auto-generated method stub
		super.decreaseTimesRotate();

		if (timesRotate == 0) {
			timesRotate = 3;
		} else {
			timesRotate--;
		}
	}

	@Override
	public void generateInteractiveFrameForSpecialCase2(Brick brickFollowMouse,
			ArrayList<InteractiveFrame> tempInteractiveFrames) {
		// TODO Auto-generated method stub
		super.generateInteractiveFrameForSpecialCase2(brickFollowMouse,
				tempInteractiveFrames);
		int w = (int) (this.getDotInteractiveFrameList().size() % this
				.getSizeBrick().x());
		int h = (int) (this.getDotInteractiveFrameList().size() / this
				.getSizeBrick().x());
		if (h > 0) {
			w = (int) this.getSizeBrick().x();
		}

		Vec startGeneratePosition = new Vec(this.translate.x(),
				this.translate.y(), this.translate.z());
		ArrayList<InteractiveFrame> tempIF = new ArrayList<InteractiveFrame>();

		switch (timesRotate) {
		case 0:
			break;
		case 1:
			int temp = w;
			w = h;
			h = temp;
			startGeneratePosition.setX(startGeneratePosition.x()
					+ Util.BRICK_SIZE);
			break;
		case 2:
			startGeneratePosition.setY(startGeneratePosition.y()
					+ Util.BRICK_SIZE);
			break;
		case 3:
			temp = w;
			w = h;
			h = temp;
			break;
		}

		for (int i = (int) -(brickFollowMouse.getSizeBrick().x() - 1); i < (w); i++) {
			for (int j = (int) -(brickFollowMouse.getSizeBrick().y() - 1); j < 0; j++) {
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

		for (int i = (int) -(brickFollowMouse.getSizeBrick().x() - 1); i < 0; i++) {
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
