import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GObject;

public abstract class ShootingFlower extends BadGuy {
	//UpShootingFlower and DownShootingFlower extend ShootingFlower
	//UpShootingFlower comes in and out of Up Pipe, down pipe for downShootingFlower
	//shootingflower is added to level parts when creating/spawning an up/down pipe
	//shootingflower is part of the same level part as an up/down pipe
	private static final double DY = 10.0;
	private int numMoves;
	public double dy;
	public int timeOffset;
	//timeOffset is so that not all flowers in a level come in/out of pipe at the same time
	public ShootingFlower(Image arg0, int timeOffset) {
		super(arg0);
		if (this instanceof UpShootingFlower) dy = -DY;
		else dy = DY;//(this instanceof DownShootingFlower)
		numMoves = (int) (getHeight()/DY);
		this.timeOffset = timeOffset;
	}

	public abstract void lookDownClosedMouth(boolean rightOrLeft);
	public abstract void lookDownOpenMouth(boolean rightOrLeft);
	public abstract void lookUpClosedMouth(boolean rightOrLeft);
	public abstract void lookUpOpenMouth(boolean rightOrLeft);

	public void openMouth(boolean upOrDown, boolean rightOrLeft) {
		if (upOrDown) lookUpOpenMouth(rightOrLeft);
		else lookDownOpenMouth(rightOrLeft);
	}
	public void closeMouth(boolean upOrDown, boolean rightOrLeft) {
		if (upOrDown) lookUpClosedMouth(rightOrLeft);
		else lookDownClosedMouth(rightOrLeft);
	}
	public abstract Point[] getPoints();

	public void shootMario() {
		//flower needs to locate mario and shoot fireball at him (in a straight line)
		//TODO could add sound when shooting flower shoot fireball at mario
		boolean rightOrLeft;
		boolean upOrDown = mario.getY()<getY()+MovingObject.moveDx;
		if (mario.getX()<getX()) {
			rightOrLeft = false;
		} else {
			rightOrLeft = true;
		}
		//need to make shooting flower look towards mario with a closed mouth
		closeMouth(upOrDown, rightOrLeft);
		try{Thread.sleep(500);} catch(Exception e) {e.printStackTrace();}
		//need to open the mouth of the shooting flower
		openMouth(upOrDown, rightOrLeft);
		double fireBallX = rightOrLeft?getX()+getWidth()+MovingObject.moveDx*1.0:getX()-MovingObject.moveDx*5.0;
		double fireBallY = (this instanceof DownShootingFlower)?getY()+getHeight()-(upOrDown?9.0:1.0)*MovingObject.moveDx:
			getY()+(upOrDown?0.0:5.0)*MovingObject.moveDx;
		if (!alive) {
			closeMouth(upOrDown, rightOrLeft);
			return;
		};
		Factory.addFlowerFireBall(fireBallX, fireBallY, rightOrLeft);
		//factory calls the fireball function to move the fireball towards mario (in a straight line)
		try{Thread.sleep(500);} catch(Exception e) {e.printStackTrace();}
		closeMouth(upOrDown, rightOrLeft);		
	}

	public void move() {
		//shootingflower.move() is called when shootingflower is added to levelParts
		//this func makes the flower move out of the pipe, shoot a fireball at mario,
		//and come back into the pipe depending on if is a up/down shootingflower		
		System.out.println("In move function for shooting flower");
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(timeOffset);}catch (Exception e) {e.printStackTrace();}
				while (alive) {
					try {
						for (int i=0; i<numMoves; i++) {
							ArrayList<GObject> o1 = checkAtPositions(getPoints(), canvas);
							for (GObject x : o1) {
								inContactWith(x, false);
							}
							move(0, dy);
							//flower comes out of pipe
							Thread.sleep(40);
						}
						Thread.sleep(500);
						if (!alive) break;
						shootMario();
						if (!alive) break;
						for (int i=0; i<numMoves; i++) {
							ArrayList<GObject> o1 = checkAtPositions(getPoints(), canvas);
							for (GObject x : o1) {
								inContactWith(x, false);
							}
							move(0, -dy);
							//flower goes back into pipe
							Thread.sleep(40);
						}
						Thread.sleep(4000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("SHOOTING FLOWER DEAD");
			}
		});  
		t1.start();		
	}
}