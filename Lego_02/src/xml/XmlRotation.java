package xml;

import java.util.ArrayList;

import remixlab.dandelion.geom.Vec;

public class XmlRotation {
	Vec sizeBrick;
	Vec translateForDraw;
	int numberOfDot;
	int numberOfBoxColider;
	int numberOfCenterOfDot;
	int numberOfInteractiveFrame;
	int scale;
	int threshold;

	ArrayList<XmlDot> dots;
	ArrayList<XmlBoxCollider> boxColliders;
	ArrayList<XmlCenterPositionOfDot> centerPostionOfDots;
	ArrayList<XmlInteractiveFrame> interactiveFrames;

	public XmlRotation() {
		super();
		dots = new ArrayList<XmlDot>();
		boxColliders = new ArrayList<XmlBoxCollider>();
		centerPostionOfDots = new ArrayList<XmlCenterPositionOfDot>();
		interactiveFrames = new ArrayList<XmlInteractiveFrame>();
	}

	public Vec getSizeBrick() {
		return sizeBrick;
	}

	public void setSizeBrick(Vec sizeBrick) {
		this.sizeBrick = sizeBrick;
	}

	public Vec getTranslateForDraw() {
		return translateForDraw;
	}

	public void setTranslateForDraw(Vec translateForDraw) {
		this.translateForDraw = translateForDraw;
	}

	public ArrayList<XmlDot> getDots() {
		return dots;
	}

	public void setDots(ArrayList<XmlDot> dots) {
		this.dots = dots;
	}

	public ArrayList<XmlBoxCollider> getBoxColliders() {
		return boxColliders;
	}

	public void setBoxColliders(ArrayList<XmlBoxCollider> boxColliders) {
		this.boxColliders = boxColliders;
	}

	public ArrayList<XmlCenterPositionOfDot> getCenterPostionOfDots() {
		return centerPostionOfDots;
	}

	public void setCenterPostionOfDots(
			ArrayList<XmlCenterPositionOfDot> centerPostionOfDots) {
		this.centerPostionOfDots = centerPostionOfDots;
	}

	public ArrayList<XmlInteractiveFrame> getInteractiveFrames() {
		return interactiveFrames;
	}

	public void setInteractiveFrames(
			ArrayList<XmlInteractiveFrame> interactiveFrames) {
		this.interactiveFrames = interactiveFrames;
	}

	public int getNumberOfDot() {
		return numberOfDot;
	}

	public void setNumberOfDot(int numberOfDot) {
		this.numberOfDot = numberOfDot;
	}

	public int getNumberOfBoxColider() {
		return numberOfBoxColider;
	}

	public void setNumberOfBoxColider(int numberOfBoxColider) {
		this.numberOfBoxColider = numberOfBoxColider;
	}

	public int getNumberOfCenterOfDot() {
		return numberOfCenterOfDot;
	}

	public void setNumberOfCenterOfDot(int numberOfCenterOfDot) {
		this.numberOfCenterOfDot = numberOfCenterOfDot;
	}

	public int getNumberOfInteractiveFrame() {
		return numberOfInteractiveFrame;
	}

	public void setNumberOfInteractiveFrame(int numberOfInteractiveFrame) {
		this.numberOfInteractiveFrame = numberOfInteractiveFrame;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getThreshold() {
		return threshold;
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

}
