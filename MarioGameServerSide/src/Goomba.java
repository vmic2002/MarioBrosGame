import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GObject;

public class Goomba extends BadGuy {
	//TODO could make goomba implement Dynamic and have a pipe that pumps out goomba periodically
	private static MyImage goombaRight, goombaLeft, goombaSquished;
	private static final int LEFT_OR_RIGHT_FREQUENCY = 5;//>0 num times move function is called before goomba toggles left to right image
	private static final double DY = MovingObject.scalingFactor*1.4;
	private static final double DX = MovingObject.scalingFactor*0.9;
	private int leftOrRightFrequency;
	private boolean leftOrRightImage;//true if goombaLeft image false if goombaRight image
	private double dx;
	private double dy;
	//private boolean previousPointWorked;

	public Goomba() {
		super(goombaRight);
		leftOrRightImage = false;
		leftOrRightFrequency = 0;
		dx = DX;
		dy = 0.0;
	}

	private void toggleRightOrLeft() {
		if (leftOrRightImage) setImageAndRelocate(goombaRight);
		else setImageAndRelocate(goombaLeft);
		leftOrRightImage = !leftOrRightImage;
		leftOrRightFrequency = 0;
	}
	
	
	@Override
	public void contactFromSideByMario(Mario mario) {mario.marioHit();}

	@Override
	public void jumpedOnByMario(Mario mario) {
		if (!this.alive) return;
		StatsController.killGoombaByJumpingOnIt(mario);
		this.alive = false;
		setImageAndRelocate(goombaSquished);
		SoundController.playSquishSound();
		mario.hop();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
				//thread sleeps to let goomba stay squished for a little bit before being removed from canvas
				kill();
			}
		});
		t1.setName("goomba kill");
		t1.start();	
	}

	@Override
	public void move() {
		System.out.println("ADDED GOOMBA");
		//Goomba is added to same level parts as the platform he is on so this func just has to make him move right/left and fall if no platform are under him (he walks off platform)
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("In red turtle move function");
				while (alive) {

					if (getY()+dy>=canvas.getHeight()+LevelController.currLevel.yBaseLine){//!spinningOrFalling && 
						//turtle dies if reaches bottom of screen
						System.out.println("turtle at bottom of screen ");
						kill();
						break;
					}

					double newX = dx>0?getX()+getWidth()+2.0*dx:getX()+2.0*dx;
					Point[] pointsSide = new Point[] {
							new Point(newX, getY()+getHeight()*0.2),
							new Point(newX, getY()+getHeight()*0.5),
							new Point(newX, getY()+getHeight()*0.9)//,
							//new Point(newX, getY()+getHeight())
					};
					double newY = getY()+getHeight()+2.0*dy;
					Point[] pointsBelow = new Point[] {
							new Point(getX()+getWidth()*0.3 ,newY),
							new Point(getX()+getWidth()*0.7,newY)
					};


					move(dx, dy);



					//previousPointWorked = false;
					ArrayList<GObject> o1 = checkAtPositions(pointsBelow);
					for (GObject x : o1) {
						if (inContactWith(x, false)) break;
						//if (previousPointWorked) break;
					}
					//previousPointWorked = false;
					ArrayList<GObject> o2 = checkAtPositions(pointsSide);
					for (GObject x : o2) {
						if (inContactWith(x, true)) break;
						//if (previousPointWorked) break;
					}

					if (nothingUnder(pointsBelow)) {
						//if here then goomba was on a platform but is no longer on top of one,
						//so it should fall
						dy = DY;
					}

					if (dy==0 && nothingOnSides(pointsSide) && Math.random()<0.05) {
						dx = -dx;//goomba randomly changes directions if nothing prevents him from doing so (nothingOnSides makes sure he can change directions)
					}

					if (leftOrRightFrequency==LEFT_OR_RIGHT_FREQUENCY) toggleRightOrLeft();
					leftOrRightFrequency++;
					try {Thread.sleep(30);} catch (Exception e) {e.printStackTrace();}
				}
				kill();
			}
		});
		t1.setName("goomba move");
		t1.start();	
	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		if ((x instanceof Platform || x instanceof BadGuy) && horizontalOrVertical) {
			//goombas bounce off BadGuys and platforms
			dx = -dx;
			//this.sendToFront();//FOR TESTING
			//previousPointWorked = true;
			return true;
			//SoundController.playBumpSound();
		} else if (x instanceof Platform && !horizontalOrVertical) {
			//goomba fell on platform
			dy = 0;
			//previousPointWorked = true;
			return true;
		} else {return super.inContactWith(x, true);}
		//super.inContactWith checks to see if turtle runs into mario
	}

	private boolean nothingOnSides(Point[] pointsSide) {
		return nothingThere(pointsSide);
	}

	private boolean nothingUnder(Point[] pointsBelow) {
		return nothingThere(pointsBelow);
	}

	private boolean nothingThere(Point[] p) {
		for (int i=0; i<p.length; i++) {
			if (canvas.getElementAt(p[i].x, p[i].y)!=null){
				return false;
			}
		}
		return true;
	}

	public static void setObjects(MyImage goombaRight1, MyImage goombaLeft1, MyImage goombaSquished1) {
		goombaRight = goombaRight1; 
		goombaLeft = goombaLeft1;
		goombaSquished = goombaSquished1;
	}
}