import java.util.ArrayList;

import processing.core.PShape;
import remixlab.dandelion.core.InteractiveFrame;

public class PlaneLego implements DrawableObject{
	private int width;
	private int height;
	private int brickSize;
	private ArrayList<InteractiveFrame> interactiveFrames;
	private ArrayList<PShape> pShapes;

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the brickSize
	 */
	public int getBrickSize() {
		return brickSize;
	}

	/**
	 * @param brickSize
	 *            the brickSize to set
	 */
	public void setBrickSize(int brickSize) {
		this.brickSize = brickSize;
	}

	/**
	 * @return the interactiveFrames
	 */
	public ArrayList<InteractiveFrame> getInteractiveFrames() {
		return interactiveFrames;
	}

	/**
	 * @param interactiveFrames
	 *            the interactiveFrames to set
	 */
	public void setInteractiveFrames(
			ArrayList<InteractiveFrame> interactiveFrames) {
		this.interactiveFrames = interactiveFrames;
	}

	/**
	 * @return the pShapes
	 */
	public ArrayList<PShape> getpShapes() {
		return pShapes;
	}

	/**
	 * @param pShapes
	 *            the pShapes to set
	 */
	public void setpShapes(ArrayList<PShape> pShapes) {
		this.pShapes = pShapes;
	}

	public PlaneLego() {
		super();
	}

	public PlaneLego(int width, int height, int brickSize) {
		super();
		this.width = width;
		this.height = height;
		this.brickSize = brickSize;
	}

	@Override
	public void setup() {
		interactiveFrames = new ArrayList<InteractiveFrame>();
		pShapes = new ArrayList<PShape>();	
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked() {
		// TODO Auto-generated method stub

	}

	public void addInteractiveFrame(InteractiveFrame frame) {
		interactiveFrames.add(frame);
	}

	public void addShape(PShape square) {
		pShapes.add(square);
	}
}
