package xml;

import remixlab.dandelion.geom.Vec;

public class XmlBoxCollider extends XmlObject {
	Vec size;

	public Vec getSize() {
		return size;
	}

	public void setSize(Vec size) {
		this.size = size;
	}

}
