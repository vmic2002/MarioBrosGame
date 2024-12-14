

//import java.awt.Image;
//import java.awt.image.BufferedImage;
import java.util.ArrayList;

import acm.graphics.GObject;

public class RedTurtle extends Turtle {
	private static MyImage redTurtleSpinning1, redTurtleSpinning2, 
	redTurtleSpinning3, redTurtleSpinning4, redTurtleStandingLeft,
	redTurtleStandingRight, redTurtleWalkingLeft, redTurtleWalkingRight;
	private int numMovesToReachEdge;//num moves until turtle reaches edge of platform and needs to turn around


	/***
	 * 
	 * @param width of platform red turtle stays on
	 */
	public RedTurtle(double width, Lobby lobby) {
		super(redTurtleStandingRight, lobby);
		numMovesToReachEdge = ((int) ((width-this.getWidth())/dx));
	}





	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//called to check if hitting platform from the side to bounce off
		if ((x instanceof Platform || x instanceof BadGuy) && horizontalOrVertical) {
			//red turtles bounce off BadGuys and platforms
			dx = -dx;
			//System.out.println("CHANGE DIRECTIONS\n\n\n\n");
			this.sendToFront();//FOR TESTING
			//previousPointWorked = true;
			lobby.soundController.playBumpSound();
			return true;
		} else if (x instanceof Platform && !horizontalOrVertical) {
			//turtle fell on platform
			//System.out.println("setting dy to 0\n\n\n");
			spinningOrFalling = true;
			dy = 0.0;
			//previousPointWorked = true;
			return true;
		} else {return super.inContactWith(x, true);}
		/*else if (x instanceof BadGuy) {
			((BadGuy) x).kill();
			previousPointWorked=true;
		}
		else {super.inContactWith(x, true);}*/
		//super.inContactWith checks to see if turtle runs into mario
	}

	






	@Override
	public void move() throws InterruptedException {
		//red turtle moves right and left on the same platform until he is stepped on or dies
		//red turtle is added to same level parts as the platform he is on so this func just has to make him move right and left
		//assume red turtle is placed at leftmost part of platform and is facing right
		System.out.println("In red turtle move function");
		while (alive) {
			for (int i=0; i<numMovesToReachEdge && !shellMode; i++) {
				//if (!mario.flashing && alive) {
				if (alive) {
					double newX = rightOrLeft?getX()+getWidth()+2*dx:getX()+4*dx;
					Point p1  = new Point(newX,getY()+getHeight()*0.8);
					Point p2  = new Point(newX,getY()+getHeight()*0.5);
					Point p3  = new Point(newX,getY()+getHeight()*0.2);
					Point[] arr = new Point[]{p1,p2,p3};
					ArrayList<GObject> o = checkAtPositions(arr);
					for (GObject x : o) {

						//super.inContactWith
						if (x instanceof Mario) {
							if (((Mario) x).flashing || !alive) break;
							((Mario) x).marioHit();
						}
					}
				} else break;
				if (walkingFrequency==WALKING_FREQUENCY) toggleStandingOrWalking();
				move(dx, 0);

				//TODO also need to check if turtle runs into a power up etc (maybe change that in incontact func of badguy)
				walkingFrequency++;
				ThreadSleep.sleep(3, lobby);
			}
			if (shellMode) break;
			changeDirection();
		}
		if (!shellMode) {
			kill();
			System.out.println("Red turtle dead1");
		}
	}

	@Override
	protected MyImage getTurtleSpinning1Image() {
		return redTurtleSpinning1;
	}

	@Override
	protected MyImage getTurtleSpinning2Image() {
		return redTurtleSpinning2;
	}

	@Override
	protected MyImage getTurtleSpinning3Image() {
		return redTurtleSpinning3;
	}

	@Override
	protected MyImage getTurtleSpinning4Image() {
		return redTurtleSpinning4;
	}

	@Override
	protected MyImage getTurtleStandingLeftImage() {
		return redTurtleStandingLeft;
	}

	@Override
	protected MyImage getTurtleStandingRightImage() {
		return redTurtleStandingRight;	
	}

	@Override
	protected MyImage getTurtleWalkingLeftImage() {
		return redTurtleWalkingLeft;
	}

	@Override
	protected MyImage getTurtleWalkingRightImage() {
		return redTurtleWalkingRight;
	}



	public static void setObjects(MyImage redTurtleSpinning1Image1,MyImage redTurtleSpinning2Image1, 
			MyImage redTurtleSpinning3Image1, MyImage redTurtleSpinning4Image1, MyImage redTurtleStandingLeftImage1,
			MyImage redTurtleStandingRightImage1, MyImage redTurtleWalkingLeftImage1, MyImage redTurtleWalkingRightImage1) {
		redTurtleSpinning1 = redTurtleSpinning1Image1;
		redTurtleSpinning2 = redTurtleSpinning2Image1; 
		redTurtleSpinning3 = redTurtleSpinning3Image1;
		redTurtleSpinning4 = redTurtleSpinning4Image1;
		redTurtleStandingLeft = redTurtleStandingLeftImage1;
		redTurtleStandingRight = redTurtleStandingRightImage1;
		redTurtleWalkingLeft = redTurtleWalkingLeftImage1;
		redTurtleWalkingRight = redTurtleWalkingRightImage1;
	}
}
