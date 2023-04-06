import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import acm.graphics.GObject;

public class RedTurtle extends BadGuy {
	private static Image redTurtleSpinning1Image, redTurtleSpinning2Image, 
	redTurtleSpinning3Image, redTurtleSpinning4Image, redTurtleStandingLeftImage,
	redTurtleStandingRightImage, redTurtleWalkingLeftImage, redTurtleWalkingRightImage;
	private static int WALKING_FREQUENCY = 3;//>0 num times move function is called before red turtle toggles walking/standing
	private int walkingFrequency;
	private boolean rightOrLeft;
	private boolean standingOrWalking;
	private double dx;
	private int numMovesToReachEdge;//num moves until turtle reaches edge of platform and needs to turn around

	/***
	 * 
	 * @param width of platform red turtle stays on
	 */
	public RedTurtle(double width) {
		super(redTurtleStandingRightImage);
		rightOrLeft = true;
		standingOrWalking = true;
		walkingFrequency = 0;
		dx = MovingObject.moveDx*0.7;
		numMovesToReachEdge = ((int) ((width-this.getWidth())/dx));
	}
	//TODO have to make red turtle spin after mario jumps on it and kicks it

	private void toggleStandingOrWalking() {
		if (standingOrWalking) {
			if (rightOrLeft) setImageAndRelocate(redTurtleWalkingRightImage);
			else setImageAndRelocate(redTurtleWalkingLeftImage);
		}
		else {
			if (rightOrLeft) setImageAndRelocate(redTurtleStandingRightImage);
			else setImageAndRelocate(redTurtleStandingLeftImage);
		}
		standingOrWalking = !standingOrWalking;
		walkingFrequency = 0;
	}

	private void changeDirection() {
		if (standingOrWalking) {
			if (rightOrLeft)setImageAndRelocate(redTurtleStandingLeftImage);
			else setImageAndRelocate(redTurtleStandingRightImage);
		}
		else {
			if (rightOrLeft)  setImageAndRelocate(redTurtleWalkingLeftImage);
			else setImageAndRelocate(redTurtleWalkingRightImage);
		}
		rightOrLeft = !rightOrLeft;
		dx = -dx;
	}

	@Override
	public void move() {
		//red turtle moves right and left on the same platform until he is stepped on or dies
		//red turtle is added to same level parts as the platform he is on so this func just has to make him move right and left
		//assume red turtle is placed at leftmost part of platform and is facing right
		//TODO when mario jumps on turtle turtle needs to change to redTurtleSpinning1Image
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("In red turtle move function");
				while (alive) {
					for (int i=0; i<numMovesToReachEdge; i++) {
						if (!mario.flashing && alive) {
							double newX = rightOrLeft?getX()+getWidth()+2*dx:getX()+4*dx;
							Point p1  = new Point(newX,getY()+getHeight()*0.8);
							Point p2  = new Point(newX,getY()+getHeight()*0.5);
							Point p3  = new Point(newX,getY()+getHeight()*0.2);
							Point[] arr = new Point[]{p1,p2,p3};
							ArrayList<GObject> o = checkAtPositions(arr);
							for (GObject x : o) {
								if (mario.flashing || !alive) break;
								inContactWith(x, true);
							}
						}
						if (walkingFrequency==WALKING_FREQUENCY) toggleStandingOrWalking();
						move(dx, 0);

						//TODO also need to check if turtle runs into a power up etc (maybe change that in incontact func of badguy)
						walkingFrequency++;
						try {Thread.sleep(30);} catch (Exception e) {e.printStackTrace();}
					}
					changeDirection();
				}
				//alive is set to false by level controller when starting new level
				kill();
				System.out.println("Red turtle dead");
			}
		});  
		t1.start();		


	}

	public static void setObjects(Image redTurtleSpinning1Image1,Image redTurtleSpinning2Image1, 
			Image redTurtleSpinning3Image1, Image redTurtleSpinning4Image1, Image redTurtleStandingLeftImage1,
			Image redTurtleStandingRightImage1, Image redTurtleWalkingLeftImage1, Image redTurtleWalkingRightImage1) {
		redTurtleSpinning1Image = redTurtleSpinning1Image1;
		redTurtleSpinning2Image = redTurtleSpinning2Image1; 
		redTurtleSpinning3Image = redTurtleSpinning3Image1;
		redTurtleSpinning4Image = redTurtleSpinning4Image1;
		redTurtleStandingLeftImage = redTurtleStandingLeftImage1;
		redTurtleStandingRightImage = redTurtleStandingRightImage1;
		redTurtleWalkingLeftImage = redTurtleWalkingLeftImage1;
		redTurtleWalkingRightImage = redTurtleWalkingRightImage1;
	}
}