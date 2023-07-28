import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import acm.graphics.GObject;

public class RedTurtle extends BadGuy {
	private static Image redTurtleSpinning1Image, redTurtleSpinning2Image, 
	redTurtleSpinning3Image, redTurtleSpinning4Image, redTurtleStandingLeftImage,
	redTurtleStandingRightImage, redTurtleWalkingLeftImage, redTurtleWalkingRightImage;
	private static final int WALKING_FREQUENCY = 3;//>0 num times move function is called before red turtle toggles walking/standing
	private int walkingFrequency;
	private static final int SPINNING_FREQUENCY = 2;//>0
	private int spinningFrequency;
	private boolean rightOrLeft;//used when !shellMode 
	private boolean standingOrWalking;
	private double dx;
	private double dy;//used when !spinningOrFalling
	private static final double DY = MovingObject.scalingFactor*0.9;
	private int numMovesToReachEdge;//num moves until turtle reaches edge of platform and needs to turn around
	public boolean shellMode;//true if in shell (not standing)
	public boolean spinningOrFalling;//true if spinning (shell mode and moving on platform)
	//and false if falling (value does not matter if !shellMode or stopped) if falling the turtle stills spins on itself
	private boolean stopped;//true if shell mode and stopped. (in shell mode, turtle is either stopped or not stopped (spinningOrFalling) 
	private enum SPINNING_STAGE {STAGE_1,STAGE_2,STAGE_3,STAGE_4};
	private SPINNING_STAGE spinningStage;//value doesnt matter if !shellMode
	//private boolean previousPointWorked;


	/***
	 * 
	 * @param width of platform red turtle stays on
	 */
	public RedTurtle(double width) {
		super(redTurtleStandingRightImage);
		rightOrLeft = true;
		standingOrWalking = true;
		walkingFrequency = 0;
		dx = MovingObject.scalingFactor*0.7;
		numMovesToReachEdge = ((int) ((width-this.getWidth())/dx));
		shellMode = false;
	}
	//TODO have to make turtle go upside down when cat mario flicks it with tail
	private boolean nothingUnder(Point[] pointsBelow) {
		for (int i=0; i<pointsBelow.length; i++) {
			if (canvas.getElementAt(pointsBelow[i].x, pointsBelow[i].y)!=null){
				return false;
			}
		}
		return true;
	}

	public void startSpinning(boolean rightOrLeft) {
		//in this func turtle alternates between spinning on platform and falling until it dies or stopped
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//keep spinning or falling until !alive or stopped
				spinningOrFalling = true;
				stopped = false;
				dx = Math.abs(dx);
				if (!rightOrLeft) dx = -dx;
				spinningFrequency = 0;
				dy = 0;
				while (alive && !stopped) {
					System.out.println("SPINNINGORFalling(not stopped)");
					if (getY()+dy>=canvas.getHeight()+LevelController.currLevel.yBaseLine){//!spinningOrFalling && 
						//turtle dies if reaches bottom of screen
						System.out.println("turtle at bottom of screen ");
						alive = false;
						break;
					}
					double newX = dx>0?getX()+getWidth()+2.0*dx:getX()+2.0*dx;
					Point[] pointsSide = new Point[] {
							new Point(newX, getY()+getHeight()*0.2),
							new Point(newX, getY()+getHeight()*0.5),
							new Point(newX, getY()+getHeight()*0.9)//,
							//new Point(newX, getY()+getHeight())
					};
					double newY = getY()+getHeight()+1.4*DY;
					Point[] pointsBelow = new Point[] {
							new Point(getX()+getWidth()*0.3 ,newY),
							new Point(getX()+getWidth()*0.7,newY)
					};
					if (spinningOrFalling) {
						if (nothingUnder(pointsBelow)) {
							//if here then turtle was on a platform and spinning but is no longer on top of one,
							//so it should fall again
							System.out.println("NO LONGER ON PLATFORM TIME TO FALL AGIAN");
							spinningOrFalling = false;
							dy = DY;
						}
					}
					move(dx, dy);
					//if (!alive || stopped) break;

					//if (!alive || stopped) break;
				//	previousPointWorked = false;
					ArrayList<GObject> o1 = checkAtPositions(pointsBelow);
					for (GObject x : o1) {
						if (inContactWith(x, false)) break;
						//if (previousPointWorked) break;
					}
				//	previousPointWorked = false;
					ArrayList<GObject> o2 = checkAtPositions(pointsSide);
					for (GObject x : o2) {
						if (inContactWith(x, true)) break;
						//if (previousPointWorked) break;
					}
					//move(dx, dy);
					//if (!alive || stopped) break;
					spinningFrequency++;
					if (spinningFrequency==SPINNING_FREQUENCY) {
						changeState();
						spinningFrequency = 0;
					}
					try {Thread.sleep(30);} catch (Exception e) {e.printStackTrace();}
				}
				if (!alive) {
					kill();
					System.out.println("Red turtle dead2");
				}
			}	
		});
		t1.setName("red turtle spinning/falling");
		t1.start();
	}

	private void changeState() {
		if (spinningStage == SPINNING_STAGE.STAGE_1) {
			setImageAndRelocate(redTurtleSpinning2Image);
			spinningStage = SPINNING_STAGE.STAGE_2; 
		} else if (spinningStage == SPINNING_STAGE.STAGE_2) {
			setImageAndRelocate(redTurtleSpinning3Image);
			spinningStage = SPINNING_STAGE.STAGE_3; 
		} else if (spinningStage == SPINNING_STAGE.STAGE_3) {
			setImageAndRelocate(redTurtleSpinning4Image);
			spinningStage = SPINNING_STAGE.STAGE_4; 
		} else {//stage 4
			setImageAndRelocate(redTurtleSpinning1Image);
			spinningStage = SPINNING_STAGE.STAGE_1;
		}
	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//called to check if hitting platform from the side to bounce off
		if ((x instanceof Platform || x instanceof BadGuy) && horizontalOrVertical) {
			//red turtles bounce off BadGuys and platforms
			dx = -dx;
			System.out.println("CHANGE DIRECTIONS\n\n\n\n");
			this.sendToFront();//FOR TESTING
			//previousPointWorked = true;
			SoundController.playBumpSound();
			return true;
		} else if (x instanceof Platform && !horizontalOrVertical) {
			//turtle fell on platform
			System.out.println("setting dy to 0\n\n\n");
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

	public void setTurtleToStoppedShellMode(Mario mario) {
		stopped = true;
		setImageAndRelocate(redTurtleSpinning1Image);
		spinningStage = SPINNING_STAGE.STAGE_1;
		mario.hop();
	}

	@Override
	public void contactFromSideByMario(Mario mario) {
		//System.out.println("\n"+mario.character.name()+" ran into RED TURTLE\n");
		if (shellMode && stopped) {
			//TODO could add animation for mario to kick turtle
			SoundController.playKickSound();
			startSpinning(mario.getX()<this.getX());//mario walking into a stopped turtle in shell mode will make the shell start spinning (like mario kicked the turtle)
		} else {
			mario.marioHit();
		}
	}

	@Override
	public void jumpedOnByMario(Mario mario) {
		if (shellMode) {
			if (!stopped) {
				//mario jumps in spinning turtle, turtle should stop if not falling
				if (spinningOrFalling) {
					System.out.println("mario jumps in spinning turtle, turtle should stop");
					setTurtleToStoppedShellMode(mario);
				} else {
					//mario jumps on falling turtle, he hops/jumps off but turtle keeps moving
					mario.hop();
				}
			} else {
				System.out.println("turtle start spinning...");
				startSpinning(mario.getX()+mario.getWidth()/2<this.getX()+this.getWidth()/2);
				mario.hop();
			}
		} else {
			//mario jumps on !shellMode turtle and sets it to shell mode
			//this happens once per turtle max because a turtle never goes back from shell mode
			shellMode = true;
			System.out.println("big mario jumps on !shellMode turtle and sets it to shell mode");
			dx *= 4.0;//shell mode (spinning or falling turtle) is 4 times as fast as standing turtle
			setTurtleToStoppedShellMode(mario);
		}
		SoundController.playSquishSound();
	}

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
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
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
						try {Thread.sleep(30);} catch (Exception e) {e.printStackTrace();}
					}
					if (shellMode) break;
					changeDirection();
				}
				//alive is set to false by level controller when starting new level
				if (!alive) {
					kill();
					System.out.println("Red turtle dead1");
				}
			}
		});
		t1.setName("red turtle moving NOT shell mode");
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