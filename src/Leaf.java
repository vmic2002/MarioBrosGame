import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.awt.Image;
import java.util.ArrayList;
public class Leaf extends MovingObject {
	private static GCanvas canvas;
	private static Image leftLeafImage, rightLeafImage;
	private boolean rightOrLeft;
	private static double DX = 8;
	private static double DY = 6;
	private static int pauseTime = 35;
	private static int toggleCounter = 15;//every <toggleCounter> times leaf moves it toggles direction
	private double dx, dy;
	private int toggle;
	private boolean alive;
	public Leaf(boolean rightOrLeft) {
		super(rightOrLeft?rightLeafImage:leftLeafImage);
		this.rightOrLeft = rightOrLeft;
		dx = rightOrLeft?DX:-DX;
		dy = DY;
		alive = true;
		toggle = 0;
	}
	
	private void toggleState() {
		//changes leaf from right to left or from left to right	
		Image newImage = rightOrLeft?leftLeafImage:rightLeafImage;
		setImageAndRelocate(newImage);
		rightOrLeft = !rightOrLeft;
		dx = rightOrLeft?DX:-DX;
		try {
			Thread.sleep(5*pauseTime);
			//leaf pauses while changing direction
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 
	public void move() {
		//TODO leaf needs to fall down left and right
		this.sendToFront();
		for (int i=0; i<15; i++) {
			move(0, -10);
			try {
				Thread.sleep(15);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("ADDED LEAF");
		while (alive && getY()<=canvas.getHeight()+LevelController.currLevel.yBaseLine) {
			if (toggle==toggleCounter) {
				toggle = 0;
				toggleState();
			}
			//dx+=2 and dx-=2 ensure that the leaf moves in an arc like pattern
			//since its dx is increasing in the direction it is moving
			//dx is set back to its normal value in toggleState()
			if (dx>0) dx+=2;
			else dx-=2;
			toggle++;
			move(dx, dy);
			try {
				Thread.sleep(pauseTime);
			} catch (Exception e) {
				e.printStackTrace();
			}
			double newX = rightOrLeft?getX()+getWidth()+dx:getX()+dx;//+dx because dx == -10
			Point[] points = new Point[] {
					new Point(newX, getY()),
					new Point(newX, getY()+getWidth()/2),
					new Point(newX, getY()+getWidth()+dy),
					new Point(getX()+getWidth()/2, getY()+getHeight()+dy)
					};
			ArrayList<GObject> oList = checkAtPositions(points);
			for (GObject o : oList) {
				inContactWith(o);
			}
		}
		alive = false;
		canvas.remove(this);
		System.out.println("LEAF DEAD");
	}
	
	private void inContactWith(GObject x) {
		if (x instanceof Mario) {
			canvas.remove(this);
			alive = false;
			((Mario) x).setToCat();
			SoundController.playPowerUpSound();
		} else {
			System.out.println("LEAF ONLY CHANGES WHEN in contact with mario");
		}
	}
	
	public ArrayList<GObject> checkAtPositions(Point[] points) {
		//THIS FUNCTION IS ALREADY IN MARIO.java
		//TODO consider making a class for edge detection so dont have to copy checkAtPositions in multiple classes
		ArrayList<GObject> result = new ArrayList<GObject>();
		for (Point p : points) {
			GObject a = canvas.getElementAt(p.x, p.y);
			if (a!=null) {
				result.add(a);
			}
		}
		return result;
	}
	

	public void setImageAndRelocate(Image newImage) {
		//THIS FUNC IS ALREADY IN MARIO.java
		double relativeY = this.getY()+ this.getHeight();
		double previousWidth = this.getWidth();
		super.setImage(newImage);
		double xShift = (this.getWidth()-previousWidth)/2;
		this.setLocation(getX()-xShift, relativeY-this.getHeight());	
	}
	
	public static void setObjects(Image rightLeafImage1, Image leftLeafImage1, GCanvas canvas1) {
		rightLeafImage = rightLeafImage1;
		leftLeafImage = leftLeafImage1;
		canvas = canvas1;
	}
}
