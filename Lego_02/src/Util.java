import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.Scene;

public class Util {
	public static String BRICK_2x1 = "brick_2x1";
	public static int PLANE_WIDTH = 10;
	public static int PLANE_HEIGHT = 10;
	public static int BRICK_SIZE = 20;
	public static int MODEL_SCALE = 2;
	public static int OBJECT_SCALE = 5;
	public static String MODEL_NAME_FILE = "ModelNameList.txt";
	public static String CALIBRATE_VEC_FILE = "CalibrateVecList.txt";
	public static String EXTRA_POSITION_VEC_FILE = "ExtraPostionVec.txt";
	public static ArrayList<String> MODEL_NAME_LIST;
	public static Vec DEFAULT_ROTATE = new Vec(-3.14159265359f / 2, 0, 0);
	public static float ROTATE_ANGLE_ADDED = 3.14159265359f / 2;
	public static String DEFAULT_BRICK_NAME = "Brick_2x1";
	public static Scene CURRENT_SCENE = null;
	public static ArrayList<Vec> CALIBRATE_VEC_MODEL;
	public static ArrayList<Vec> EXTRA_POSITION_VEC;
	public static double PI = Math.PI;
	public static int THRESHOLD_VALUE = 30;
	public static Dictionary<String, ArrayList<InteractiveFrame>> interactiveFrameDictionary = new Hashtable<String, ArrayList<InteractiveFrame>>();

	public static String FONT_FILE_NAME_DEFAULT = "SegoeUI-Light-48.vlw";
	public static int FONT_SIZE_DEFAULT = 48;

	public static void LoadModelNames() throws IOException {
		MODEL_NAME_LIST = new ArrayList<String>();
		BufferedReader in = new BufferedReader(new FileReader(new File(
				MODEL_NAME_FILE)));
		String line = null;
		while ((line = in.readLine()) != null)
			MODEL_NAME_LIST.add(line);
		in.close();
	}

	public static boolean CheckExistInteractiveFrame(
			ArrayList<InteractiveFrame> tempInteractiveFrames, Vec frameVec) {
		for (int i = 0; i < tempInteractiveFrames.size(); i++) {
			if (tempInteractiveFrames.get(i) == null)
				continue;
			if (tempInteractiveFrames.get(i).position().equals(frameVec))
				return true;
		}
		return false;
	}

	public static void LoadCalibrateVec() throws IOException {
		CALIBRATE_VEC_MODEL = new ArrayList<Vec>();
		BufferedReader in = new BufferedReader(new FileReader(new File(
				CALIBRATE_VEC_FILE)));
		String line = null;
		while ((line = in.readLine()) != null) {
			String[] vec = line.split(" ");
			Vec temp = new Vec(Float.parseFloat(vec[0]),
					Float.parseFloat(vec[1]), Float.parseFloat(vec[2]));
			CALIBRATE_VEC_MODEL.add(temp);

		}
		in.close();
	}

	public static void LoadExtraPositionVec() throws IOException {
		EXTRA_POSITION_VEC = new ArrayList<Vec>();
		BufferedReader in = new BufferedReader(new FileReader(new File(
				EXTRA_POSITION_VEC_FILE)));
		String line = null;
		while ((line = in.readLine()) != null) {
			String[] vec = line.split(" ");
			Vec temp = new Vec(Float.parseFloat(vec[0]),
					Float.parseFloat(vec[1]), Float.parseFloat(vec[2]));
			EXTRA_POSITION_VEC.add(temp);

		}
		in.close();
	}

	public static Vec clone(Vec input) {
		return new Vec(input.x(), input.y(), input.z());
	}

	public static InteractiveFrame generateInteractiveFrame(Vec translate,
			String modelName) {
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
		return iframe1;
	}

}
