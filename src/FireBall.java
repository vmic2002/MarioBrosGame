import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

class FireBall extends GImage {
	private static GCanvas canvas;
	private static Image leftFireBall1;
	private static Image rightFireBall1;
	private static Image leftFireBall2;
	private static Image rightFireBall2;
	private static Image leftFireBall3;
	private static Image rightFireBall3;
	private static Image leftFireBall4;
	private static Image rightFireBall4;

	private enum FIREBALL_STAGE {STAGE_1, STAGE_2, STAGE_3, STAGE_4};
	FIREBALL_STAGE fireBallStage;

	private static final int maxDistance = 1000;//max distance until it disappears
	private static final int frequencyChangeToNextStage = 10;//number of times move function is called in between
	//changing fireball sprite image to next stage (1->2, ..., 4->1), low number -> high frequency
	private static final int sizeOfHops = 300;//fireball hops once it moves on the ground
	private static final int hopRadius = sizeOfHops/2;//width of semi circle (hop) is 2*R
	private boolean rightOrLeft;
	private int frequencyChangeStage = frequencyChangeToNextStage;
	private int gasLeft = maxDistance;//fireball moves until it has no more gas left (gasLeft == 0)
	private int dx; //x speed
	private int dy;//y speed
	private int pauseTime = 10;//milliseconds pause in between each move function call
	public FireBall(boolean rightOrLeft) {
		super((rightOrLeft?rightFireBall1:leftFireBall1));
		this.rightOrLeft = rightOrLeft;
		dx = rightOrLeft?10:-10;
		dy = 10;
		fireBallStage = FIREBALL_STAGE.STAGE_1;
		//rightOrLeft parameter determines if fireball is moving right or left
	}

	public void changeToNextStage() {
		//changes fireball image from stage 1 to stage 2, 2->3, 3->4, 4->1
		Image newImage;
		if (fireBallStage == FIREBALL_STAGE.STAGE_1) {
			newImage = rightOrLeft?rightFireBall2:leftFireBall2;
			fireBallStage = FIREBALL_STAGE.STAGE_2;
		} else if (fireBallStage == FIREBALL_STAGE.STAGE_2) {
			newImage = rightOrLeft?rightFireBall3:leftFireBall3;
			fireBallStage = FIREBALL_STAGE.STAGE_3;
		} else if (fireBallStage == FIREBALL_STAGE.STAGE_3) {
			newImage = rightOrLeft?rightFireBall4:leftFireBall4;
			fireBallStage = FIREBALL_STAGE.STAGE_4;
		}else {//if (fireBallStage == FIREBALL_STAGE.STAGE_4) {
			newImage = rightOrLeft?rightFireBall1:leftFireBall1;
			fireBallStage = FIREBALL_STAGE.STAGE_1;
		}
		setImageAndRelocate(newImage);
	}

	public void move() {
		//this function moves a fireball its maximum distance or until
		//it hits a flower or turtle while changing its images
		while (gasLeft >0 && getY()+getHeight()+dy<=canvas.getHeight()) {
			//if in this while loop then fireball is in the air,
			//need to bring it down
			//need to check the points to the left/right diagonal (p3), middle down (p2), and left/right in middle (p1)
			Point p1  = rightOrLeft?new Point(getX()+getWidth()+dx,getY()+getHeight()/2):new Point(getX()-dx,getY()+getHeight()/2);
			Point p2 = new Point(getX()+getWidth()/2, getY()+getHeight()+dy);//point needs to be checked for both going right or left
			Point p3 = rightOrLeft?new Point(getX()+getWidth()+dx, getY()+getHeight()+dy):new Point(getX()-dx, getY()+getHeight()+dy);
			Point[] arr = new Point[]{p1,p2,p3};
			ArrayList<GObject> o = checkAtPositions(arr);
			for (GObject x : o) {
				inContactWith(x);
			}
			if (frequencyChangeStage==0) {
				changeToNextStage();
				frequencyChangeStage = frequencyChangeToNextStage;
			}
			move(dx, dy);
			gasLeft -= Math.sqrt(dx*dx+dy*dy);
			frequencyChangeStage--;
			try {
				Thread.sleep(pauseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//now fireball is as low as it can go
		
		while (gasLeft>0) {
			if (frequencyChangeStage==0) {
				changeToNextStage();
				frequencyChangeStage = frequencyChangeToNextStage;
			}
			hop();
			frequencyChangeStage--;
			try {
				Thread.sleep(pauseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("NO MORE GAS FOR FIREBALL");
		canvas.remove(this);
	}
	
	private void hop() {
		//need to make fireball do semi circles as it is moving left or right on the ground (like hopping)
		double x = getX()%sizeOfHops;
		//getX() is compressed into a window of size of hops
		//imagine x,y coordinate with semi circle with equation (x-R)^2+y^2=R^2, y>0
		//for GCanvas however y coordinate starts at the top, 
		//so need to use canvas.getHeight()-y
		double y = canvas.getHeight()-Math.sqrt(hopRadius*hopRadius-(x-hopRadius)*(x-hopRadius));
		//getY()-y is how much fireball needs to move in the y to stay on semi circle path
		//however needs to be negated since positive getY()-y would mean moving up, which is negative y displacement
		//for GCanvas
		//TODO before moving need to make sure fireball doesnt come inContactWith() anything
		move(dx,-(getY()-y));
		if (getX()<5) canvas.remove(this); //bug when fireball goes to the left border of screen probably because
		//of using % (mod) at beginning of function
		gasLeft -= Math.abs(dx);
	}

	private void inContactWith(GObject x) {
		//TODO fireball needs to check if it runs into a flower, turtle, etc and kills it
		//if (x instanceof Turtle)
		//if (x!=null) System.out.println("FIREBALL RAN INTO SOMETHING");
	}

	public ArrayList<GObject> checkAtPositions(Point[] points) {
		//THIS FUNCTION IS ALREADY IN MARIO.java
		//expects positions to not be in mario's frame, but right next to it
		//returns list of object mario comes in contact with, returns empty list if none
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

	public static void setObjects(Image leftFireBall1X,
			Image rightFireBall1X,Image leftFireBall2X,
			Image rightFireBall2X,Image leftFireBall3X,
			Image rightFireBall3X,Image leftFireBall4X,
			Image rightFireBall4X, GCanvas canvas1) {
		leftFireBall1 = leftFireBall1X;
		rightFireBall1 = rightFireBall1X;
		leftFireBall2 = leftFireBall2X;
		rightFireBall2 = rightFireBall2X;
		leftFireBall3 = leftFireBall3X;
		rightFireBall3 = rightFireBall3X;
		leftFireBall4 = leftFireBall4X;
		rightFireBall4 = rightFireBall4X;
		canvas = canvas1;
	}
}