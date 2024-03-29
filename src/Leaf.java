
import acm.graphics.GObject;


import java.util.ArrayList;
public class Leaf extends PowerUp {
	private static MyImage leftLeafImage, rightLeafImage;
	private static double DX = MovingObject.getBaseLineSpeed()*0.8;
	private static double DY = MovingObject.getBaseLineSpeed()*0.6;
	private static double pauseTime = 3.5;
	private static int toggleCounter = 14;//every <toggleCounter> times leaf moves it toggles direction
	private boolean rightOrLeft;
	private double dx, dy;
	private int toggle;
	public Leaf(boolean rightOrLeft) {
		super(rightOrLeft?rightLeafImage:leftLeafImage);
		this.rightOrLeft = rightOrLeft;
		dx = rightOrLeft?DX:-DX;
		dy = DY;
		toggle = 0;
	}

	private void toggleState() throws InterruptedException {
		//changes leaf from right to left or from left to right	
		MyImage newImage = rightOrLeft?leftLeafImage:rightLeafImage;
		setImageAndRelocate(newImage);
		rightOrLeft = !rightOrLeft;
		dx = rightOrLeft?DX:-DX;

		ThreadSleep.sleep(5*pauseTime);
		//leaf pauses while changing direction

	}

	@Override
	public void move() throws InterruptedException {
		this.sendToFront();
		for (int i=0; i<15 && alive; i++) {
			move(0, -MovingObject.getBaseLineSpeed());

			ThreadSleep.sleep(1.5);

		}
		System.out.println("ADDED LEAF");
		while (alive && getY()<=canvas.getHeight()+LevelController.currLevel.yBaseLine) {
			if (toggle==toggleCounter) {
				toggle = 0;
				toggleState();
			}
			//dx+=2 and dx-=2 ensure that the leaf moves in an arc like pattern
			//since its dx is increasing in the direction it is moving
			//dx is set back to its normal value in toggleState()
			if (dx>0) dx+=2;
			else dx-=2;
			toggle++;
			move(dx, dy);

			ThreadSleep.sleep(pauseTime);

			double newX = rightOrLeft?getX()+getWidth()+dx:getX()+dx;//+dx because dx == -10
			Point[] points = new Point[] {
					new Point(newX, getY()),
					new Point(newX, getY()+getWidth()/2),
					new Point(newX, getY()+getWidth()+dy),
					new Point(getX()+getWidth()/2, getY()+getHeight()+dy)
			};
			ArrayList<GObject> oList = checkAtPositions(points);
			for (GObject o : oList) {
				inContactWith(o, false);
				//for leaf it doesnt matter if it comes into contact with something
				//from the sides or vertically
			}
		}
		kill();
		System.out.println("end of move function for LEAF (DEAD)");
	}

	@Override
	public boolean inContactWith(GObject x, boolean b) {
		if (!alive) {
			System.out.println("DEAD LEAF WAS GOING TO HIT MARIO");
		}
		if (x instanceof Mario) {
			Mario m = (Mario) x;
			if (!m.alive) {
				return true;
			}
			canvas.remove(this);
			alive = false;

			if (m.isTimeDilating)
				m.stopTimeDilationForAllCharacters(m);


			m.setToCat();




			SoundController.playPowerUpSound();
		} else {
			//System.out.println("LEAF ONLY CHANGES WHEN in contact with mario");
		}
		return true;
	}

	public static void setObjects(MyImage rightLeafImage1, MyImage leftLeafImage1) {
		rightLeafImage = rightLeafImage1;
		leftLeafImage = leftLeafImage1;
	}
}
