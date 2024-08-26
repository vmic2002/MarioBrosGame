import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GObject;

public abstract class ShootingFlower extends BadGuy {
	//UpShootingFlower and DownShootingFlower extend ShootingFlower
	//UpShootingFlower comes in and out of Up Pipe, down pipe for downShootingFlower
	//shootingflower is added to level parts when creating/spawning an up/down pipe
	//shootingflower is part of the same level part as an up/down pipe
	private static final double DY = MovingObject.getBaseLineSpeed()*1.33;
	private int numMoves;
	public double dy;
	public int timeOffset;
	//timeOffset is so that not all flowers in a level come in/out of pipe at the same time
	//can use timeOffset to coordinate flowers to come out at same time
	public ShootingFlower(MyImage arg0, int timeOffset, Lobby lobby) {
		super(arg0, lobby);
		if (this instanceof UpShootingFlower) dy = -DY;
		else dy = DY;//(this instanceof DownShootingFlower)
		numMoves = (int) (getHeight()/DY);
		this.timeOffset = timeOffset;
	}
	@Override
	public void jumpedOnByMario(Mario mario) {mario.marioHit();}
	@Override
	public void contactFromSideByMario(Mario mario) {mario.marioHit();}

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

	public Mario getClosestMario() {
		int index = 0;
		double smallestDistance = MarioBrosGame.WIDTH*2;
		//System.out.println("smallestDistance: "+smallestDistance);
		for (int i=0; i<lobby.characters.length; i++) {
			Mario m = lobby.characters[i];
			double d = Math.sqrt(Math.pow(m.getX()+m.getWidth()/2-this.getX()-this.getWidth()/2, 2)+Math.pow(m.getY()+m.getHeight()/2-this.getY()-this.getHeight()/2, 2));
			//System.out.println("!!!!!!!!LOOp AT: "+m.character+"   "+d);
			if (d<smallestDistance) {
				index = i;
				smallestDistance = d;
			}
		}
		//System.out.println("shooting at:" +MovingObject.characters[index].character.name()+ " smallest distance; "+smallestDistance);
		return lobby.characters[index];
	}

	public void shootMario() throws InterruptedException {
		//flower needs to locate mario and shoot fireball at him (in a straight line)
		//TODO could add sound when shooting flower shoot fireball at mario
		Mario mario = getClosestMario();

		//System.out.println("!!!!!!!!SHOOTING AT: "+mario.character);
		boolean rightOrLeft;
		boolean upOrDown = mario.getY()<getY()+MovingObject.getBaseLineSpeed();
		if (mario.getX()<getX()) {
			rightOrLeft = false;
		} else {
			rightOrLeft = true;
		}
		//need to make shooting flower look towards mario with a closed mouth
		closeMouth(upOrDown, rightOrLeft);
		ThreadSleep.sleep(50, lobby);
		//need to open the mouth of the shooting flower
		openMouth(upOrDown, rightOrLeft);
		double fireBallX = rightOrLeft?getX()+getWidth()+MovingObject.getBaseLineSpeed()*1.0:getX()-MovingObject.getBaseLineSpeed()*5.0;
		double fireBallY = (this instanceof DownShootingFlower)?getY()+getHeight()-(upOrDown?9.0:1.0)*MovingObject.getBaseLineSpeed():
			getY()+(upOrDown?0.0:5.0)*MovingObject.getBaseLineSpeed();
		if (!alive) {
			closeMouth(upOrDown, rightOrLeft);
			return;
		};
		if  (getX()<=MarioBrosGame.WIDTH && getX()+getWidth()>=0
				&& getY()<=MarioBrosGame.HEIGHT && getY()+getHeight()>=0) {	
			lobby.dFactory.addFlowerFireBall(fireBallX, fireBallY, rightOrLeft, mario);
			//only flowers on screen shoot at mario
			//flower still comes out of pipe when off screen to preserve timeOffset
		}
		//factory calls the fireball function to move the fireball towards mario (in a straight line)
		ThreadSleep.sleep(50, lobby);
		closeMouth(upOrDown, rightOrLeft);		
	}

	@Override
	public void move() throws InterruptedException {
		//this func makes the flower move out of the pipe, shoot a fireball at mario,
		//and come back into the pipe depending on if is a up/down shootingflower		
		sendToBack();//to fix bugs on client side
		ThreadSleep.sleep(timeOffset, lobby);
		while (alive) {
			sendToBack();
			for (int i=0; i<numMoves; i++) {
				ArrayList<GObject> o1 = checkAtPositions(getPoints());
				for (GObject x : o1) {
					inContactWith(x, false);
				}
				move(0, dy);
				//flower comes out of pipe
				ThreadSleep.sleep(4, lobby);
			}
			ThreadSleep.sleep(50, lobby);
			if (!alive) break;
			shootMario();
			if (!alive) break;
			sendToBack();//to fix bugs on client side
			for (int i=0; i<numMoves; i++) {
				ArrayList<GObject> o1 = checkAtPositions(getPoints());
				for (GObject x : o1) {
					inContactWith(x, false);
				}
				move(0, -dy);
				//flower goes back into pipe
				ThreadSleep.sleep(4, lobby);
			}
			ThreadSleep.sleep(400, lobby);
		}
		//alive is set to false by level controller when starting new level
		kill();
		System.out.println("SHOOTING FLOWER DEAD");
	}
}