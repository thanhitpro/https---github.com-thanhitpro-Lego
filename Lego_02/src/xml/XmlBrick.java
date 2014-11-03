package xml;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;

import remixlab.dandelion.geom.Vec;

public class XmlBrick {
	String name;
	Vec defaultSizeBrick;
	Vec calibrateVec;
	Vec extraPositionVec;
	int numberOfTimeRotation;
	boolean finishLoading = false;
	int numberOfInteractiveFrame;

	ArrayList<XmlRotation> rotations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vec getDefaultSizeBrick() {
		return defaultSizeBrick;
	}

	public void setDefaultSizeBrick(Vec defaultSizeBrick) {
		this.defaultSizeBrick = defaultSizeBrick;
	}

	public Vec getCalibrateVec() {
		return calibrateVec;
	}

	public void setCalibrateVec(Vec calibrateVec) {
		this.calibrateVec = calibrateVec;
	}

	public Vec getExtraPositionVec() {
		return extraPositionVec;
	}

	public void setExtraPositionVec(Vec extraPositionVec) {
		this.extraPositionVec = extraPositionVec;
	}

	public int getNumberOfTimeRotation() {
		return numberOfTimeRotation;
	}

	public void setNumberOfTimeRotation(int numberOfTimeRotation) {
		this.numberOfTimeRotation = numberOfTimeRotation;
	}

	public ArrayList<XmlRotation> getRotations() {
		return rotations;
	}

	public void setRotations(ArrayList<XmlRotation> rotations) {
		this.rotations = rotations;
	}

	public void readXml(String xmlFileName, int id) {
		try {
			File xmlFile = new File(xmlFileName);
			XPath xPath = XPathFactory.newInstance().newXPath();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			String expression = "/brick/models/model[@id='" + id + "']/name";
			name = xPath.compile(expression).evaluate(doc);
			defaultSizeBrick = new Vec(
					getIntValue(xPath, doc, "/brick/models/model[@id='" + id
							+ "']/defaultsizebrick/@x"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/defaultsizebrick/@y"), getIntValue(
							xPath, doc, "/brick/models/model[@id='" + id
									+ "']/defaultsizebrick/@z"));
			calibrateVec = new Vec(getIntValue(xPath, doc,
					"/brick/models/model[@id='" + id + "']/calibratevec/@x"),
					getIntValue(xPath, doc, "/brick/models/model[@id='" + id
							+ "']/calibratevec/@y"), getIntValue(xPath, doc,
							"/brick/models/model[@id='" + id
									+ "']/calibratevec/@z"));
			extraPositionVec = new Vec(
					getIntValue(xPath, doc, "/brick/models/model[@id='" + id
							+ "']/extrapositionvec/@x"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/extrapositionvec/@y"), getIntValue(
							xPath, doc, "/brick/models/model[@id='" + id
									+ "']/extrapositionvec/@z"));
			rotations = new ArrayList<XmlRotation>();
			numberOfTimeRotation = getIntValue(xPath, doc,
					"/brick/models/model[@id='" + id
							+ "']/rotations/@numberofrotation");

			for (int i = 0; i < numberOfTimeRotation; i++) {
				XmlRotation xmlRotation = new XmlRotation();

				xmlRotation.setSizeBrick(new Vec(getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/sizebrick/@x"), getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/sizebrick/@y"), getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/sizebrick/@z")));

				xmlRotation.setTranslateForDraw(new Vec(getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/translatefordraw/@x"), getIntValue(xPath,
						doc, "/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/translatefordraw/@y"), getIntValue(xPath,
						doc, "/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/translatefordraw/@z")));

				int numberOfDot = getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/dots/@numberofdot");
				xmlRotation.setNumberOfDot(numberOfDot);

				for (int j = 0; j < numberOfDot; j++) {
					XmlDot dot = new XmlDot();
					dot.setPosition(new Vec(getIntValue(xPath, doc,
							"/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/dots/dot[@id='" + j
									+ "']/position/@x"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/dots/dot[@id='" + j
									+ "']/position/@y"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/dots/dot[@id='" + j
									+ "']/position/@z")));
					xmlRotation.getDots().add(dot);
				}

				int numberOfBoxColider = getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/boxcolliders/@numberofboxcolider");
				xmlRotation.setNumberOfBoxColider(numberOfBoxColider);
				for (int j = 0; j < numberOfBoxColider; j++) {
					XmlBoxCollider boxCollider = new XmlBoxCollider();
					boxCollider.setPosition(new Vec(getIntValue(xPath, doc,
							"/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/boxcolliders/box[@id='" + j
									+ "']/position/@x"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/boxcolliders/box[@id='" + j
									+ "']/position/@y"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/boxcolliders/box[@id='" + j
									+ "']/position/@z")));
					boxCollider.setSize(new Vec(getIntValue(xPath, doc,
							"/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/boxcolliders/box[@id='" + j
									+ "']/size/@width"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/boxcolliders/box[@id='" + j
									+ "']/size/@height"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/boxcolliders/box[@id='" + j
									+ "']/size/@depth")));
					xmlRotation.getBoxColliders().add(boxCollider);
				}

				int numberOfCenterPostionOfDot = getIntValue(
						xPath,
						doc,
						"/brick/models/model[@id='"
								+ id
								+ "']/rotations/rotation[@id = '"
								+ i
								+ "']/centerpositionofdots/@numberofcenterofdot");
				xmlRotation.setNumberOfCenterOfDot(numberOfCenterPostionOfDot);
				for (int j = 0; j < numberOfCenterPostionOfDot; j++) {
					XmlCenterPositionOfDot centerPositionOfDot = new XmlCenterPositionOfDot();
					centerPositionOfDot
							.setPosition(new Vec(
									getIntValue(
											xPath,
											doc,
											"/brick/models/model[@id='"
													+ id
													+ "']/rotations/rotation[@id = '"
													+ i
													+ "']/centerpositionofdots/centerpositionofdot[@id='"
													+ j + "']/position/@x"),
									getIntValue(
											xPath,
											doc,
											"/brick/models/model[@id='"
													+ id
													+ "']/rotations/rotation[@id = '"
													+ i
													+ "']/centerpositionofdots/centerpositionofdot[@id='"
													+ j + "']/position/@y"),
									getIntValue(
											xPath,
											doc,
											"/brick/models/model[@id='"
													+ id
													+ "']/rotations/rotation[@id = '"
													+ i
													+ "']/centerpositionofdots/centerpositionofdot[@id='"
													+ j + "']/position/@z")));
					xmlRotation.getCenterPostionOfDots().add(
							centerPositionOfDot);
				}

				xmlRotation.setScale(getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/interactiveframes/@scale"));
				xmlRotation.setThreshold(getIntValue(xPath, doc,
						"/brick/models/model[@id='" + id
								+ "']/rotations/rotation[@id = '" + i
								+ "']/interactiveframes/@threshold"));
				int numberOfInteractiveFrame = getIntValue(
						xPath,
						doc,
						"/brick/models/model[@id='"
								+ id
								+ "']/rotations/rotation[@id = '"
								+ i
								+ "']/interactiveframes/@numberofinteractiveframe");
				xmlRotation
						.setNumberOfInteractiveFrame(numberOfInteractiveFrame);
				this.setNumberOfInteractiveFrame(numberOfInteractiveFrame);
				for (int j = 0; j < numberOfInteractiveFrame; j++) {
					XmlInteractiveFrame interactiveFrame = new XmlInteractiveFrame();
					interactiveFrame.setPosition(new Vec(getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/interactiveframes/if[@id='" + j
									+ "']/position/@x"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/interactiveframes/if[@id='" + j
									+ "']/position/@y"), getIntValue(xPath,
							doc, "/brick/models/model[@id='" + id
									+ "']/rotations/rotation[@id = '" + i
									+ "']/interactiveframes/if[@id='" + j
									+ "']/position/@z")));
					xmlRotation.getInteractiveFrames().add(interactiveFrame);
				}

				rotations.add(xmlRotation);
			}

			finishLoading = true;
		} catch (Exception ex) {
			System.out.println("Error");
			finishLoading = false;
		}
		System.out.println("Finish");

	}

	public int getNumberOfInteractiveFrame() {
		return numberOfInteractiveFrame;
	}

	public void setNumberOfInteractiveFrame(int numberOfInteractiveFrame) {
		this.numberOfInteractiveFrame = numberOfInteractiveFrame;
	}

	public boolean isFinishLoading() {
		return finishLoading;
	}

	public void setFinishLoading(boolean finishLoading) {
		this.finishLoading = finishLoading;
	}

	private Integer getIntValue(XPath xPath, Document doc, String xPathString)
			throws XPathExpressionException {
		return Integer.valueOf(xPath.compile(xPathString).evaluate(doc));
	}
}
