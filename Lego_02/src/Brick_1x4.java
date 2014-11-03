import remixlab.dandelion.geom.Vec;
import xml.XmlBrick;

public class Brick_1x4 extends Brick {

	public Brick_1x4() {
		// TODO Auto-generated constructor stub
		super();
		modelName = "Brick_1x4";
		calibrateVec = Util.CALIBRATE_VEC_MODEL.get(Util.MODEL_NAME_LIST
				.indexOf(modelName));
		sizeBrick = new Vec(4, 1, 1);
		if (Util.XML_BRICK_DICTIONARY.get(modelName) != null
				&& Util.XML_BRICK_DICTIONARY.get(modelName).isFinishLoading()) {
			this.xmlBrick = Util.XML_BRICK_DICTIONARY.get(modelName);
		} else {
			xmlBrick = new XmlBrick();
			xmlBrick.readXml("Brick_1x4.xml", Util.MODEL_NAME_LIST.indexOf(modelName));
			Util.XML_BRICK_DICTIONARY.put(modelName, xmlBrick);

		}
	}
}
