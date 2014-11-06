import remixlab.dandelion.geom.Vec;
import xml.XmlBrick;

public class Roof_tile_2x2 extends Brick {
	public Roof_tile_2x2() {
		// TODO Auto-generated constructor stub
		super();
		modelName = "Roof_tile_2x2";
		calibrateVec = Util.CALIBRATE_VEC_MODEL.get(Util.MODEL_NAME_LIST
				.indexOf(modelName));
		sizeBrick = new Vec(2, 2, 1);
		if (Util.XML_BRICK_DICTIONARY.get(modelName) != null
				&& Util.XML_BRICK_DICTIONARY.get(modelName).isFinishLoading()) {
			this.xmlBrick = Util.XML_BRICK_DICTIONARY.get(modelName);
		} else {
			xmlBrick = new XmlBrick();
			xmlBrick.readXml("S:\\Lego Project\\Github project\\Lego\\https---github.com-thanhitpro-Lego.git\\Lego_02\\src\\Brick_1x4.xml", Util.MODEL_NAME_LIST.indexOf(modelName));
			Util.XML_BRICK_DICTIONARY.put(modelName, xmlBrick);
		}
	}

	
}
