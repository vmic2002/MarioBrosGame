

import java.util.ArrayList;

import acm.graphics.GObject;

public abstract class Turtle extends BadGuy {

	protected abstract MyImage getTurtleSpinning1Image();
	protected abstract MyImage getTurtleSpinning2Image();
	protected abstract MyImage getTurtleSpinning3Image();
	protected abstract MyImage getTurtleSpinning4Image();

	protected abstract MyImage getTurtleStandingLeftImage();
	protected abstract MyImage getTurtleStandingRightImage();
	protected abstract MyImage getTurtleWalkingLeftImage();
	protected abstract MyImage getTurtleWalkingRightImage();




	protected static final int WALKING_FREQUENCY = 3;//>0 num times move function is called before turtle toggles walking/standing
	protected int walkingFrequency;
	private static final int SPINNING_FREQUENCY = 2;//>0
	private int spinningFrequency;
	protected boolean rightOrLeft;//used when !shellMode 
	private boolean standingOrWalking;
	protected double dx;
	protected double dy;//used when !spinningOrFalling
	private static final double DY = MovingObject.getBaseLineSpeed()*0.9;
	
	public boolean shellMode;//true if in shell (not standing)
	public boolean spinningOrFalling;//true if spinning (shell mode and moving on platform)
	//and false if falling (value does not matter if !shellMode or stopped) if falling the turtle stills spins on itself
	private boolean stopped;//true if shell mode and stopped. (in shell mode, turtle is either stopped or not stopped (spinningOrFalling) 
	private enum SPINNING_STAGE {STAGE_1,STAGE_2,STAGE_3,STAGE_4};
	private SPINNING_STAGE spinningStage;//value doesnt matter if !shellMode
	//private boolean previousPointWorked;


	
	public Turtle(MyImage image, Lobby lobby) {
		super(image, lobby);
		rightOrLeft = true;
		standingOrWalking = true;
		walkingFrequency = 0;
		dx = MovingObject.getBaseLineSpeed()*0.5;
		shellMode = false;
	}
	//TODO have to make turtle go upside down when cat mario flicks it with tail
	private boolean nothingUnder(Point[] pointsBelow) {
		for (int i=0; i<pointsBelow.length; i++) {
			if (lobby.canvas.getElementAt(pointsBelow[i].x, pointsBelow[i].y)!=null){
				return false;
			}
		}
		return true;
	}

	public void startSpinning(boolean rightOrLeft) {
		//in this func turtle alternates between spinning on platform and falling until it dies or stopped
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{
				//keep spinning or falling until !alive or stopped
				spinningOrFalling = true;
				stopped = false;
				dx = Math.abs(dx);
				if (!rightOrLeft) dx = -dx;
				spinningFrequency = 0;
				dy = 0;
				while (alive && !stopped) {
					//System.out.println("SPINNINGORFalling(not stopped)");
					if (getY()+dy>=MarioBrosGame.HEIGHT+lobby.levelController.currLevel.yBaseLine){//!spinningOrFalling && 
						//turtle dies if reaches bottom of screen
						//System.out.println("turtle at bottom of screen ");
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
							//System.out.println("NO LONGER ON PLATFORM TIME TO FALL AGIAN");
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
					ThreadSleep.sleep(3, lobby);
				}
				if (!alive) {
					kill();
					//System.out.println("Red turtle dead2");
				}
			}	
		},"turtle spinning/falling", lobby.getLobbyId());
	}

	private void changeState() {
		if (spinningStage == SPINNING_STAGE.STAGE_1) {
			setImageAndRelocate(getTurtleSpinning2Image());
			spinningStage = SPINNING_STAGE.STAGE_2; 
		} else if (spinningStage == SPINNING_STAGE.STAGE_2) {
			setImageAndRelocate(getTurtleSpinning3Image());
			spinningStage = SPINNING_STAGE.STAGE_3; 
		} else if (spinningStage == SPINNING_STAGE.STAGE_3) {
			setImageAndRelocate(getTurtleSpinning4Image());
			spinningStage = SPINNING_STAGE.STAGE_4; 
		} else {//stage 4
			setImageAndRelocate(getTurtleSpinning1Image());
			spinningStage = SPINNING_STAGE.STAGE_1;
		}
	}



	public void setTurtleToStoppedShellMode(Mario mario) {
		stopped = true;
		setImageAndRelocate(getTurtleSpinning1Image());
		spinningStage = SPINNING_STAGE.STAGE_1;
		mario.hop();
	}

	@Override
	public void contactFromSideByMario(Mario mario) {
		//System.out.println("\n"+mario.character.name()+" ran into RED TURTLE\n");
		if (shellMode && stopped) {
			//TODO could add animation for mario to kick turtle
			lobby.soundController.playKickSound();
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
					//System.out.println("mario jumps in spinning turtle, turtle should stop");
					setTurtleToStoppedShellMode(mario);
				} else {
					//mario jumps on falling turtle, he hops/jumps off but turtle keeps moving
					mario.hop();
				}
			} else {
				//System.out.println("turtle start spinning...");
				startSpinning(mario.getX()+mario.getWidth()/2<this.getX()+this.getWidth()/2);
				mario.hop();
			}
		} else {
			//mario jumps on !shellMode turtle and sets it to shell mode
			//this happens once per turtle max because a turtle never goes back from shell mode
			shellMode = true;
			//System.out.println("mario jumps on !shellMode turtle and sets it to shell mode");
			dx *= 4.0;//shell mode (spinning or falling turtle) is 4 times as fast as standing turtle
			setTurtleToStoppedShellMode(mario);
		}
		lobby.soundController.playSquishSound();
	}

	protected void toggleStandingOrWalking() {
		if (standingOrWalking) {
			if (rightOrLeft) setImageAndRelocate(getTurtleWalkingRightImage());
			else setImageAndRelocate(getTurtleWalkingLeftImage());
		}
		else {
			if (rightOrLeft) setImageAndRelocate(getTurtleStandingRightImage());
			else setImageAndRelocate(getTurtleStandingLeftImage());
		}
		standingOrWalking = !standingOrWalking;
		walkingFrequency = 0;
	}

	protected void changeDirection() {
		if (standingOrWalking) {
			if (rightOrLeft)setImageAndRelocate(getTurtleStandingLeftImage());
			else setImageAndRelocate(getTurtleStandingRightImage());
		}
		else {
			if (rightOrLeft)  setImageAndRelocate(getTurtleWalkingLeftImage());
			else setImageAndRelocate(getTurtleWalkingRightImage());
		}
		rightOrLeft = !rightOrLeft;
		dx = -dx;
	}


}
