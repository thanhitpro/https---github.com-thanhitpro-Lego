import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PShape;
import remixlab.dandelion.constraint.AxisPlaneConstraint;
import remixlab.dandelion.constraint.LocalConstraint;
import remixlab.dandelion.core.InteractiveFrame;
import remixlab.dandelion.geom.Vec;
import remixlab.proscene.Scene;
import saito.objloader.OBJModel;

@SuppressWarnings("serial")
public class LegoGameControl extends PApplet {

	Scene scene;
	ArrayList<InteractiveFrame> listFrame;
	ArrayList<PShape> listShape;
	int curFocusFramePos = -1;
	int prevFocusFramePos = -1;
	ArrayList<Vec> listBoxPosition;
	int width, height;
	Vec boxFollowMouse = null;
	OBJModel objModel;
	float angleRotateBox = (-3.14f / 2);
	boolean disableBoxFollowMouse = false;
	ArrayList<InteractiveFrame> tempListFrame;
	boolean firstGenerateTempFrame = true;
	
	PlaneLego planeLego;
	ArrayList<Brick> bricks;
	boolean flagPlaneInteractiveFrame = true;

	public void setup() {
		size(1024, 780, OPENGL);
		scene = new Scene(this);
		scene.setRadius(100);
		scene.showAll();
		scene.setGridVisualHint(false);
		scene.setPickingVisualHint(true);
		
		/*
		 * Setup plane
		 */
		planeLego = new PlaneLego(Util.PLANE_WIDTH, Util.PLANE_HEIGHT, Util.BRICK_SIZE);
		planeLego.setup();
		
		/*
		 * Setup list brick
		 */
		bricks = new ArrayList<Brick>();
		
		for (int i = 0; i < Util.PLANE_WIDTH; i++) {
			for (int j = 0; j < Util.PLANE_HEIGHT; j++) {
				/*
				 * Create interactive frame for plane We have an 10x10 plane
				 */
				InteractiveFrame iframe = new InteractiveFrame(scene, null);
				LocalConstraint XAxis = new LocalConstraint();
				XAxis.setTranslationConstraint(
						AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f, 0.0f,
								0.0f));
				XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
						new Vec(1.0f, 0.0f, 0.0f));
				iframe.setConstraint(XAxis);				
				iframe.setScaling(Util.BRICK_SIZE);
				iframe.setTranslation(Util.BRICK_SIZE * i + Util.BRICK_SIZE/2, 
						Util.BRICK_SIZE * j + Util.BRICK_SIZE/2, 0);
				iframe.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
				planeLego.addInteractiveFrame(iframe);

				/*
				 * Roofing square for the plane Each square has size 20x20
				 */

				PShape square;
				square = createShape(RECT, 0, 0, 20, 20);
				square.setStroke(true);
				planeLego.addShape(square);
			}
		}
		
		/*width = height = 30;
		listFrame = new ArrayList<InteractiveFrame>();
		tempListFrame = new ArrayList<InteractiveFrame>();
		listShape = new ArrayList<PShape>();
		listBoxPosition = new ArrayList<Vec>();*/
		
		
		/*for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {*/
				/*
				 * Create interactive frame for plane We have an 10x10 plane
				 */
				/*InteractiveFrame iframe = new InteractiveFrame(scene, null);
				LocalConstraint XAxis = new LocalConstraint();
				XAxis.setTranslationConstraint(
						AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f, 0.0f,
								0.0f));
				XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
						new Vec(1.0f, 0.0f, 0.0f));
				iframe.setConstraint(XAxis);
				iframe.setScaling(20);
				iframe.setTranslation(20 * i + 10, 20 * j + 10, 0);
				listFrame.add(iframe);*/

				/*
				 * Roofing square for the plane Each square has size 20x20
				 */

				/*PShape square;
				square = createShape(RECT, 0, 0, 20, 20);
				square.setStroke(true);
				listShape.add(square);*/
		/*	}
		}*/

		objModel = new OBJModel(this);
		objModel.load("new.obj");

		objModel.enableDebug();
		objModel.scale(2);

		objModel.translateToCenter();
	}

	public void draw() {
		background(0);
		lights();
		scene.setPickingVisualHint(false);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				// Draw interactive frame on screen
				pushMatrix();
				planeLego.getInteractiveFrames().get(i * width + j).applyTransformation();
				//listFrame.get(i * width + j).applyTransformation();
				popMatrix();
				pushMatrix();
				planeLego.getpShapes().get(i * width + j).setFill(
						setColor(
								planeLego.getInteractiveFrames().get(i * width + j).grabsInput(
										scene.motionAgent()), i * width + j));
				// Draw plane
				shape(planeLego.getpShapes().get(i * width + j), i * Util.BRICK_SIZE, j * Util.BRICK_SIZE);
				popMatrix();
			}
		}

		for (int i = 0; i < bricks.size(); i++) {
			/*
			 * Put interactive frame on brick
			 */
			ArrayList<InteractiveFrame> listBrickInteractive = bricks.get(i).getDotInteractiveFrameList();
			for (int k = 0; k < listBrickInteractive.size(); k++) {
				pushMatrix();
				listBrickInteractive.get(k).applyTransformation();
				popMatrix();
				pushMatrix();
				fill(setColor(
						listBrickInteractive.get(k).grabsInput(
								scene.motionAgent()), k));
				popMatrix();
			}
			
			/*
			 * Currently, we just use 2x1 brick for demonstration so we must
			 * draw two interactive frame for each dot at that time, the player
			 * can place brick like that: ____ __|___| |_____|
			 */			

			/*
			 * Set position, size, rotation for bricks
			 */
			pushMatrix();
			translate(bricks.get(i).getTranslate().x(), bricks.get(i).getTranslate().y(), bricks.get(i).getTranslate().z());			
			scale(bricks.get(i).getScaleRatio());
			rotateX(bricks.get(i).getRotate().x());
			objModel.draw();
			popMatrix();

		}

		// If the mouse over an interactive frame, draw the brick that user
		// already chosen
		if (boxFollowMouse != null && disableBoxFollowMouse == false) {
			pushMatrix();
			translate(boxFollowMouse.x() + 10, boxFollowMouse.y(),
					boxFollowMouse.z() + 14);
			scale(5);
			rotateX(-3.14f / 2);
			//rotateY(angleRotateBox);
			objModel.draw();
			popMatrix();
			
			/*
			 * Draw interactive for special case
			 */
			tempListFrame.clear();
			InteractiveFrame iframe = new InteractiveFrame(scene, null);
			LocalConstraint XAxis = new LocalConstraint();
			XAxis.setTranslationConstraint(
					AxisPlaneConstraint.Type.FORBIDDEN, new Vec(0.0f, 0.0f,
							0.0f));
			XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS,
					new Vec(1.0f, 0.0f, 0.0f));
			iframe.setConstraint(XAxis);
			iframe.setScaling(20);
			InteractiveFrame curFocusFrame = listFrame.get(curFocusFramePos);
			iframe.setTranslation(curFocusFrame.position().x() - 20, curFocusFrame
					.position().y(), curFocusFrame.position().z());
			iframe.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
			tempListFrame.add(iframe);
		}

		noStroke();

	}

	public int setColor(boolean selected, int position) {
		if (selected) {
			/*
			 * Check Collision the brick at current mouse position with all brick was placed on plane
			 */
			checkCollision(position);
			curFocusFramePos = position;
			boxFollowMouse = listFrame.get(position).position();
			return color(200, 200, 0);
		} else {
			return color(200, 200, 200);
		}
	}

	private void checkCollision(int position) {
		Vec mousePosition = listFrame.get(position).position();
		Vec centerMousePosition1 = new Vec(mousePosition.x() + 10, mousePosition.y() + 10, mousePosition.z() + 10);
		Vec centerMousePosition2 = new Vec(mousePosition.x() + 30, mousePosition.y() + 10, mousePosition.z() + 10);
		
		
		for (int i = 0; i < listBoxPosition.size(); i++) {
			/*
			 * Each brick has a box collide consist of a series of box as rectangle, cube. 
			 */
			BoxCollider boxCollider = new BoxCollider();
			ArrayList<Box> boxs = new ArrayList<>();
			Box box = new Box();
			box.setPosition(listBoxPosition.get(i));
			box.setWidth(40);
			box.setHeight(20);
			box.setDepth(20);
			boxs.add(box);
			boxCollider.setListBox(boxs);
			if (boxCollider.Containt(centerMousePosition1)) {
				 disableBoxFollowMouse = true;	
				 return;
			} else 
			if (boxCollider.Containt(centerMousePosition2)) {
				 disableBoxFollowMouse = true;		
				 return;
			} else 
				disableBoxFollowMouse = false;
		}
		
	}

	public void keyPressed() {
		if (keyCode == LEFT) {
			if (boxFollowMouse != null) {
				angleRotateBox += (-3.14f / 2);
			}
			return;
		}

		if (keyCode == RIGHT) {
			angleRotateBox -= (-3.14f / 2);
			return;
		}
	}

	public void mouseClicked() {
		if (curFocusFramePos == -1 || boxFollowMouse == null || disableBoxFollowMouse)
			return;
		
		InteractiveFrame curFocusFrame = listFrame.get(curFocusFramePos);
		listBoxPosition.add(curFocusFrame.position());

		/*
		 * Create some interactive frames according to number of dot of the
		 * brick that the player has chosen In this demonstration, I just use
		 * the 2x1 brick so I just need to create two interactive for two dot
		 */
		InteractiveFrame iframe = new InteractiveFrame(scene, null);
		LocalConstraint XAxis = new LocalConstraint();
		XAxis.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,
				new Vec(0.0f, 0.0f, 0.0f));
		XAxis.setRotationConstraint(AxisPlaneConstraint.Type.AXIS, new Vec(
				1.0f, 0.0f, 0.0f));
		iframe.setConstraint(XAxis);
		iframe.setScaling(Util.BRICK_SIZE);
		iframe.setTranslation(curFocusFrame.position().x(), curFocusFrame
				.position().y(), curFocusFrame.position().z() + Util.BRICK_SIZE);
		iframe.setGrabsInputThreshold(Util.THRESHOLD_VALUE);

		InteractiveFrame iframe1 = new InteractiveFrame(scene, null);
		LocalConstraint XAxis1 = new LocalConstraint();
		XAxis1.setTranslationConstraint(AxisPlaneConstraint.Type.FORBIDDEN,
				new Vec(0.0f, 0.0f, 0.0f));
		XAxis1.setRotationConstraint(AxisPlaneConstraint.Type.AXIS, new Vec(
				1.0f, 0.0f, 0.0f));
		iframe1.setConstraint(XAxis1);
		iframe1.setTranslation(curFocusFrame.position().x() + Util.BRICK_SIZE, curFocusFrame
				.position().y(), curFocusFrame.position().z() + Util.BRICK_SIZE);
		iframe1.setGrabsInputThreshold(Util.THRESHOLD_VALUE);
		
		listFrame.add(iframe);
		listFrame.add(iframe1);
	}
}
