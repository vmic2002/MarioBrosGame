import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GObject;

public class Tanooki extends PowerUp {
	private static Image tanookiImage;
	private static final double DY = MovingObject.scalingFactor*1.2;
	private static final double DX = MovingObject.scalingFactor*0.2;
	private static final int pauseTime = 5;
	private double dx;
	private boolean previousPointWorked;
	public double yBaseline;
	public double xBaseline;
	private static final double maxHeightOfJump = MovingObject.scalingFactor*20.0;
	private static final double lengthOfJumpFactor = 0.01;//>0, closer to 0 means quadratic will be wider (wider hops)
	private static final double xOffset = Math.sqrt(maxHeightOfJump/lengthOfJumpFactor);
	//Tanooki bounces in quadratic motion, x/yBaseline to keep track of where he started hopping
	public Tanooki() {
		super(tanookiImage);
		dx =  Math.random()>0.5?DX:-DX;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public long getID() {
		return id;
	}

	private void hop() {
		double newX = getX()+dx;
		double oldY = getY();
		//imagine x,y coordinate with quadratic equation y=-a(x-(xBaseline-W))^2+maxHeightOfJump+(canvas.getHeight()-yBaseline)
		//find W s.t. y(xBaseline)=yBaseLine -> W = +or- sqrt(H/a) depending on if going right or left

		double W = (dx>0)?-xOffset:xOffset;

		double newY = canvas.getHeight()-(-lengthOfJumpFactor*Math.pow(newX-(xBaseline-W), 2)+maxHeightOfJump+(canvas.getHeight()-yBaseline));
		//oldY-newY is how much tanooki needs to move in the y to stay on quadratic path
		//however needs to be negated since positive oldY-newY would mean moving up, which is negative y displacement
		//for GCanvas
		move(dx, newY-oldY);
		//System.out.println("IN HOP TANOOKI"+ maxHeightOfJump);
	}

	@Override
	public void move() {
		try {
			Thread.sleep(300);
			//to wait for mysterybox to stop moving up/down
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Tanooki ADDED");

		setBaselines();

		while (alive) {
			if (getY()>=canvas.getHeight()+LevelController.currLevel.yBaseLine){
				//tanooki keeps on moving until mario eats it
				//OR if it reaches bottom of screen
				alive = false;
				break;
			}
			double newX = dx>0?getX()+getWidth()+10:getX()-10;
			Point[] pointsSide = new Point[] {
					new Point(newX, getY()+10),
					new Point(newX, getY()+getHeight()/2),
					new Point(newX, getY()+getHeight()-10)
			};
			Point[] pointsBelow = new Point[] {
					new Point(getX(), getY()+getHeight()+DY),
					new Point(getX()+getWidth()/2, getY()+getHeight()+DY),
					new Point(getX()+getWidth(), getY()+getHeight()+DY),

			};
			Point[] pointsAbove = new Point[] {
					new Point(getX(), getY()-10),
					new Point(getX()+getWidth()/2, getY()-10),
					new Point(getX()+getWidth(), getY()-10)
			};		

			//System.out.println("TANOOOKI GOING UP/DOWN: "+goingUpOrDown());
			previousPointWorked = false;
			ArrayList<GObject> o1 = checkAtPositions(pointsSide);
			for (GObject x : o1) {
				inContactWith(x, true);
			}
			if (!alive) {
				//if checking points at side kills tanooki no need to check points below
				break;
			}
			previousPointWorked = false;
			ArrayList<GObject> o2 = goingUpOrDown()?checkAtPositions(pointsAbove):checkAtPositions(pointsBelow);
			//if goingUp check points above else (going down) check points below
			for (GObject x : o2) {
				inContactWith(x, false);
			}
			hop();
			try {
				Thread.sleep(pauseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		canvas.remove(this);
		alive = false;
		LevelController.currLevel.removeDynamic(this);
		System.out.println("END OF MOVE FOR TANOOKI (DEAD)");
	}

	private void setBaselines(){
		//resets xBaseline and yBaseline so tanooki starts at beginning of hop on parabolic path
		xBaseline = this.getX();
		yBaseline = this.getY();
	}

	private boolean goingUpOrDown() {
		double middleOfParabolaX = dx>0?xBaseline+xOffset:xBaseline-xOffset;
		return !(dx>0 && getX()>middleOfParabolaX || dx<0 && getX()<middleOfParabolaX);
	}

	@Override
	public void inContactWith(GObject x, boolean horizontalOrVertical) {
		if (!alive) {
			System.out.println("DEAD TANOOKI WAS GOING TO HIT MARIO");
			return;
		}
		//horizontalOrVertical is true if in contact with something from the side
		//and false if in contact with something from below/above
		if (previousPointWorked) return;
		previousPointWorked = true;
		if (x instanceof Platform) {
			//if horizontalOrVertical then tanooki is in contact with a platform from the side,
			//so it should change its horizontal direction
			//if !horizontalOrVertical then tanooki is in contact with a platform from below/above,
			//then it should reset baselines accordingly
			if (horizontalOrVertical) {
				dx = -dx;
				setBaselines();
				//System.out.println("CHANGE HORIZONTAL DIREWCTION");
			} else {
				if (goingUpOrDown()) {
					//tanooki is going up and in contact with platform
					xBaseline = dx>0?this.getX()-xOffset:this.getX()+xOffset;
					yBaseline = this.getY()+maxHeightOfJump;
					//System.out.println("\n\nHIT PLATFORM GOING UPPPPPP\n\n");
				} else {
					//tanooki is going down and in contact with platform
					setBaselines();
					//System.out.println("\n\nHIT PLATFORM GOING DOOWWWWWN\n\n");
				}
			}
		} else if (x instanceof Mario) {
			if (!((Mario) x).alive) {
				return;
			}
			canvas.remove(this);
			SoundController.playPowerUpSound();
			((Mario) x).setToTanooki();
			alive = false;
			System.out.println("Tanooki HIT MARIO");
		}
	}

	public static void setObjects(Image tanookiImage1) {
		tanookiImage = tanookiImage1;
	}
}