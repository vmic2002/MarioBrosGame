import acm.graphics.GObject;

public class GreenTurtle extends Turtle {
	private static MyImage greenTurtleSpinning1, greenTurtleSpinning2, greenTurtleSpinning3, greenTurtleSpinning4,
	greenTurtleStandingLeft, greenTurtleStandingRight, greenTurtleWalkingLeft, greenTurtleWalkingRight;
	
	public GreenTurtle(Lobby lobby) {
		super(greenTurtleStandingRight, lobby);
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
			//System.out.println("setting dy to 0\n\n\n");'
			
			
			//TODO THIS IS CORRECT IF TURTLE IS ALREADY SPINNING
			//TODO BUT A GREEN TURTLE CAN WALK ON A PLATROM SO SHOULD KEEP WALKING
			//TODO SET dy=0 in this case 
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
		//TODO green turtle moves like red turtle except it is not restricted to one platoform
		// it walks off platforms
		//can base off red turtle
	}
	
	@Override
	protected MyImage getTurtleSpinning1Image() {
		return greenTurtleSpinning1;
	}

	@Override
	protected MyImage getTurtleSpinning2Image() {
		return greenTurtleSpinning2;
	}

	@Override
	protected MyImage getTurtleSpinning3Image() {
		return greenTurtleSpinning3;
	}

	@Override
	protected MyImage getTurtleSpinning4Image() {
		return greenTurtleSpinning4;
	}

	@Override
	protected MyImage getTurtleStandingLeftImage() {
		return greenTurtleStandingLeft;
	}

	@Override
	protected MyImage getTurtleStandingRightImage() {
		return greenTurtleStandingRight;	
	}

	@Override
	protected MyImage getTurtleWalkingLeftImage() {
		return greenTurtleWalkingLeft;
	}

	@Override
	protected MyImage getTurtleWalkingRightImage() {
		return greenTurtleWalkingRight;
	}
	
	public static void setObjects(MyImage greenTurtleSpinning1Image1,MyImage greenTurtleSpinning2Image1, 
			MyImage greenTurtleSpinning3Image1, MyImage greenTurtleSpinning4Image1, MyImage greenTurtleStandingLeftImage1,
			MyImage greenTurtleStandingRightImage1, MyImage greenTurtleWalkingLeftImage1, MyImage greenTurtleWalkingRightImage1) {
		greenTurtleSpinning1 = greenTurtleSpinning1Image1;
		greenTurtleSpinning2 = greenTurtleSpinning2Image1; 
		greenTurtleSpinning3 = greenTurtleSpinning3Image1;
		greenTurtleSpinning4 = greenTurtleSpinning4Image1;
		greenTurtleStandingLeft = greenTurtleStandingLeftImage1;
		greenTurtleStandingRight = greenTurtleStandingRightImage1;
		greenTurtleWalkingLeft = greenTurtleWalkingLeftImage1;
		greenTurtleWalkingRight = greenTurtleWalkingRightImage1;
	}


}
