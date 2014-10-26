import remixlab.dandelion.geom.Vec;


public class Box {
	private Vec position;
	private Vec centerPosition;
	private float width;
	private float height;
	private float depth;
	/**
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vec position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public Vec getPosition() {
		return position;
	}	
	
	/**
	 * @return the depth
	 */
	public float getDepth() {
		return depth;
	}

	/**
	 * @param depth the depth to set
	 */
	public void setDepth(float depth) {
		this.depth = depth;
	}
	
	

	/**
	 * @return the centerPosition
	 */
	public Vec getCenterPosition() {
		return centerPosition;
	}

	/**
	 * @param centerPosition the centerPosition to set
	 */
	public void setCenterPosition(Vec centerPosition) {
		this.centerPosition = centerPosition;
	}

	// fix
	public Boolean Container(Vec point) {
		if (point.x() > position.x() && point.x() < (position.x() + width)
				&& point.y() > position.y() && point.y() < (position.y() + height)
				&& point.z() > position.z() && point.z() < (position.z() + depth)) {
			return true;
			
		}
		return false;
	}
	
	
}
