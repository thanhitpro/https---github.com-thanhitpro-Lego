import remixlab.dandelion.geom.Vec;

public class Ray {
	private Vec origin;
	private Vec direction;

	public Vec getOrigin() {
		return origin;
	}

	public void setOrigin(Vec origin) {
		this.origin = origin;
	}

	public Vec getDirection() {
		return direction;
	}

	public void setDirection(Vec direction) {
		this.direction = direction;
	}

	public Ray(Vec origin, Vec direction) {
		super();
		this.origin = origin;
		this.direction = direction;
	}

	
	
}
