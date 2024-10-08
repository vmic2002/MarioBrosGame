

import acm.graphics.GObject;
import java.util.ArrayList;

public class Mushroom extends PowerUp {
	private static MyImage mushroomImage;
	//images for Mushroom, MysteryBox, FireBall, FireFlower, Leaf etc are static
	//so we don't have to keep on providing them each time we want a new leaf, mushroom etc
	private static final double DY = MovingObject.getBaseLineSpeed();
	private static final double DX = MovingObject.getBaseLineSpeed()*0.5;
	private static final int pauseTime = 2;//1;
	private double dx;
	private double dy;
	private boolean rightOrLeft;
	//private boolean previousPointWorked;
	//previousPointWorked: for collision detection, multiple points are used, if one already worked, 
	//no need to use the other ones 
	
	public Mushroom(Lobby lobby) {
		super(mushroomImage, lobby);
		rightOrLeft = Math.random()>0.5;
		dx = rightOrLeft?DX:-DX;
		dy = 0;
	
	}

	@Override
	public void move() throws InterruptedException {
		//mushroom move left or right and fall down from mystery box (assume is on top of mysteryBox)

		ThreadSleep.sleep(50, lobby);
		//to wait for mysterybox to stop moving up/down


		System.out.println("ADDED MUSHROOM");

		boolean stillOnMysteryBox = true;
		while (alive && stillOnMysteryBox) {
			double x = rightOrLeft?this.getX()-DX:this.getX()+this.getWidth()+DX;
			//depending on if the mushroom goes right or left to glide of the mysterybox,
			//there is only one point to check to know if the mushroom is still on the mystery box
			double y = this.getY()+this.getHeight()+DY;
			GObject o = lobby.canvas.getElementAt(x, y);
			if (o!=null && o instanceof MysteryBox) {
				//mushroom is still on top of mysterybox
				//System.out.println("MUSHROOM ON TOP OF MYSTERYBOX");
				move(dx, 0);

				ThreadSleep.sleep(pauseTime, lobby);

			} else {
				//mushroom is no longer on top of mysterybox
				stillOnMysteryBox = false;
			}
		}
		//System.out.println("MUSHROOM NO LONGER ON MYSTERYBOX");
		//by now mushroom is no longer on mysterybox
		//need to make it fall until it hits mario or platform
		while (alive) {			
			if (getY()>=MarioBrosGame.HEIGHT+lobby.levelController.currLevel.yBaseLine){
				//mushroom keeps on moving until mario eats it
				//OR if it reaches bottom of screen
				alive = false;
				break;
			}
			if (getX()+dx<=0||getX()+getWidth()+dx>=MarioBrosGame.WIDTH) {
				//for mushroom to bounce off edge of screen: (for testing)
				//dx = -dx;
				//rightOrLeft = !rightOrLeft;
				//OR mushroom dies when touches edge of screen
				//alive = false;
				//break;
			}
			double newX = rightOrLeft?getX()+getWidth()+DX:getX()-DX;
			Point[] pointsSide = new Point[] {
					new Point(newX, getY()+DY),
					new Point(newX, getY()+getHeight()/2),
					new Point(newX, getY()+getHeight()-DY)
			};
			Point[] pointsBelow = new Point[] {
					new Point(getX(), getY()+getHeight()+DY),
					new Point(getX()+getWidth()/2, getY()+getHeight()+DY),
					new Point(getX()+getWidth(), getY()+getHeight()+DY)
			};
			if (dy==0 && nothingUnder(pointsBelow)) {
				//if here then mushroom was on a platform and sliding but is no longer on top of one,
				//so it should fall again
				System.out.println("NO LONGER ON PLATFORM TIME TO FALL AGIAN");
				dy = DY;
				move(dx, dy);
			} else {
				move(dx, dy);
				//previousPointWorked = false;
				ArrayList<GObject> o1 = checkAtPositions(pointsSide);
				for (GObject x : o1) {
					if (inContactWith(x, true)) break;
				}
				if (!alive) {
					//if checking points at side kills mushroom no need to check points below
					break;
				}
				//previousPointWorked = false;
				ArrayList<GObject> o2 = checkAtPositions(pointsBelow);
				for (GObject x : o2) {
					if (inContactWith(x, false)) break;
				}
			}

			ThreadSleep.sleep(pauseTime, lobby);


		}
		kill();
		System.out.println("END OF MOVE FOR MUSHROOM (DEAD)");
	}

	private boolean nothingUnder(Point[] pointsBelow) {
		for (int i=0; i<pointsBelow.length; i++) {
			if (lobby.canvas.getElementAt(pointsBelow[i].x, pointsBelow[i].y)!=null){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean inContactWith(GObject x, boolean sideOrBelow) {
		if (!alive) {
			System.out.println("DEAD MUSHROOM WAS GOING TO HIT MARIO");
			return true;
		}
		//sideOrBelow is true if in contact with something from the side
		//and false if in contact with something from below
		//if (previousPointWorked) return true;
		//previousPointWorked = true;
		if (x instanceof Platform) {
			//if sideOrBelow then mushroom is in contact with a platform from the side,
			//so it should change its horizontal direction
			//if !sideOrBelow then mushroom is in contact with a platform from below,
			//then it should glide/slide on top of platform, not go through it
			if (sideOrBelow) {
				dx = -dx;
				rightOrLeft = !rightOrLeft;
				System.out.println("CHANGE HORIZONTAL DIREWCTION");
				//System.out.println("Mushroom bounce of platform side");
			} else {
				dy = 0;
				System.out.println("SET DY TO 0");
			}
			return true;
		} else if (x instanceof Mario) {
			Mario m = (Mario) x;
			if (!m.alive) {
				return true;
			}
			lobby.canvas.remove(this);
			lobby.soundController.playPowerUpSound();
			if (!m.bigOrSmall){
				if (m.isTimeDilating)
					m.stopTimeDilationForAllCharacters(m);
				m.setToBig();
			} else if (m.isTimeDilating) {
				m.stopTimeDilationForAllCharacters(m);
				m.setToBig();
			}

			alive = false;
			System.out.println("MUSHROOM HIT MARIO");
			return true;
		}
		return false;
		//TODO could change directions etc (for example if two mushrooms run into each other they should
		//"bounce off" and change directions 
	}

	public static void setObjects(MyImage mushroomImage1) {
		mushroomImage = mushroomImage1;
	}
}
