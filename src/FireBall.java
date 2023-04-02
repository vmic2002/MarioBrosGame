import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

class FireBall extends MovingObject {
	//TODO fix bug where fireball moves weirdly when the level is moving (sometimes it moves too much when mario jumps/walks close to edges)
	//TODO bug could be due to fireball being added/removed from levelParts while level is being moved (levelParts being looped through)
	private static Image leftFireBall1, rightFireBall1, leftFireBall2, rightFireBall2, leftFireBall3, rightFireBall3, leftFireBall4, rightFireBall4;
	private static final int maxDistance = canvas.getWidth()*3;//max distance until it disappears
	private static final int frequencyChangeToNextStage = 10;//number of times move function is called in between
	//changing fireball sprite image to next stage (1->2, ..., 4->1), low number -> high frequency
	private static final double sizeOfHops = mario.moveDx*20;//fireball hops once it moves on the ground
	private static final double hopRadius = sizeOfHops/2;//width of semi circle (hop) is 2*R
	private static int pauseTime = 10;//milliseconds pause in between each move function call
	private enum FIREBALL_STAGE {STAGE_1, STAGE_2, STAGE_3, STAGE_4};
	FIREBALL_STAGE fireBallStage;
	private boolean rightOrLeft;
	private int frequencyChangeStage = frequencyChangeToNextStage;
	private int gasLeft = maxDistance;//fireball moves until it has no more gas left (gasLeft == 0)
	private double dx; //x speed
	private double dy;//y speed
	private boolean fallingOrHopping;//fireball is either falling or "hopping" once it falls on a platform
	public double hoppingY; //y coordinate height of line on which fireball hopps on
	//hoppingY needs to be moved up/down when the level moves. this is done in LevelPart.java
	public double hoppingX;//x coordinate to keep track of where fireball started bouncing
	//helpful for using  Math.abs(getX()-hoppingX)%sizeOfHops; for hopping

	public double speedFactor = 2.0;//(needs to be >0) the higher the number the faster the fire ball will go towards mario (when shooting flower shoots fireball)
	public FireBall(boolean rightOrLeft) {
		super((rightOrLeft?rightFireBall1:leftFireBall1));
		this.rightOrLeft = rightOrLeft;
		dx = rightOrLeft?MovingObject.moveDx*1.1:-MovingObject.moveDx*1.1;
		dy = Math.abs(dx);
		fireBallStage = FIREBALL_STAGE.STAGE_1;
		fallingOrHopping = true;
		//rightOrLeft parameter determines if fireball is moving right or left
	}

	public void shootAtMario() {
		//called when a ShootingFlower shoot a fireball at mario
		//fireball needs to go to in a straight path to mario or until it hits a platform and dies
		double finalX = mario.getX()+mario.getWidth()/2;
		double finalY = mario.getY()+mario.getHeight()/4;//aim for the head so that ducking will save mario
		//divde dx and dy by lenght of (dx,dy) vector to obtain the unit vector
		double dx = finalX-getX();
		double dy = finalY-getY();
		double vectorLength = Math.sqrt(dx*dx+dy*dy);
		dx /= vectorLength;
		dy /= vectorLength;
		//now (dx, dy) is unit vector. multiply by speedFactor to change the speed
		dx *= speedFactor;
		dy *= speedFactor;
		double gasUsagePerMove = Math.sqrt(dx*dx+dy*dy);
		while (gasLeft >0 && alive) {
			try {Thread.sleep(pauseTime);} catch (Exception e) {e.printStackTrace();}
			Point p1  = rightOrLeft?new Point(getX()+getWidth()+dx,getY()+getHeight()):new Point(getX()+dx,getY()+getHeight());
			Point p2  = rightOrLeft?new Point(getX()+getWidth()+dx,getY()+getHeight()/2):new Point(getX()+dx,getY()+getHeight()/2);
			Point p3  = rightOrLeft?new Point(getX()+getWidth()+dx,getY()):new Point(getX()+dx,getY());
			Point[] arr = new Point[]{p1, p2};
			ArrayList<GObject> o = checkAtPositions(arr, canvas);
			
			for (GObject x : o) {
				if (x instanceof Mario) {
					if (!alive) break;
					System.out.println("Shooting Flower hit mario with fireball!");
					((Mario) x).marioHit();
					alive = false;
					break;
				} else if (x instanceof FireBall) {
					if (((FireBall) x).alive) {
						((FireBall) x).alive = false;
						alive = false;
						break;
					}
				} else if (x instanceof Platform) {
					alive = false;
					break;
				}
			}
			if (!alive) break;
			move(dx, dy);

			gasLeft -= gasUsagePerMove;
			if (frequencyChangeStage==0) {
				changeToNextStage();
				frequencyChangeStage = frequencyChangeToNextStage;
			}
			frequencyChangeStage--;
		}
		canvas.remove(this);
		alive = false;
		LevelController.currLevel.removeFireBall(this);
		System.out.println("FIREBALL DEAD     GASLEFT: "+gasLeft);
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

	private void checkIfShouldFall() {
		double x  = getX()+getWidth()/2;
		GObject o = canvas.getElementAt(x, hoppingY+getHeight()+dy);
		if (Math.abs(getY()-hoppingY)<5*dy) {
			if (o==null) {
				fallingOrHopping = true;
				System.out.println("FIREBALL SHOULD FALL11111111");

			} else if (!(o instanceof Platform)) {
				fallingOrHopping = true;
				System.out.println("FIREBALL SHOULD FALL222222222");
			}	
		}
	}

	@Override
	public void move() {
		//this function moves a fireball its maximum distance or until
		//it hits a flower, platform, turtle while changing its images
		while (gasLeft >0 && alive) {
			//if in this while loop then fireball is falling or hopping

			Point p1  = rightOrLeft?new Point(getX()+getWidth()+dx,getY()+getHeight()):new Point(getX()+dx,getY()+getHeight());
			//Point p3 = rightOrLeft?new Point(getX()+getWidth()+dx, getY()+getHeight()+dy):new Point(getX()-dx, getY()+getHeight()+dy);

			Point[] arr = new Point[]{p1};
			ArrayList<GObject> o = checkAtPositions(arr, canvas);
			for (GObject x : o) {
				inContactWith(x, true);
			}
			if (!alive) break;

			if (!fallingOrHopping)
				checkIfShouldFall();//to check if hopping fireball should start falling

			Point below1 = new Point(getX()+getWidth()/2, getY()+getHeight()+dy);
			Point below2 = new Point(getX()+getWidth(), getY()+getHeight()+dy);
			Point below3 = new Point(getX(), getY()+getHeight()+dy);

			Point[] arr1 = new Point[]{below1, below2, below3};
			ArrayList<GObject> o1 = checkAtPositions(arr1, canvas);
			for (GObject x : o1) {
				inContactWith(x, false);
			}
			if (!alive) break;

			if (frequencyChangeStage==0) {
				changeToNextStage();
				frequencyChangeStage = frequencyChangeToNextStage;
			}
			if (fallingOrHopping) {
				move(dx, dy);
			} else {
				hop();
			}
			gasLeft -= Math.sqrt(dx*dx+dy*dy);
			frequencyChangeStage--;
			try {
				Thread.sleep(pauseTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("END MOVE FUNCTION FIREBALL GAS: "+gasLeft);
		canvas.remove(this);
		alive = false;
		LevelController.currLevel.removeFireBall(this);
	}

	private void hop() {
		//need to make fireball do semi circles as it is moving left or right on the ground (like hopping)
		double x = Math.abs(getX()-hoppingX)%sizeOfHops;
		//getX()-hoppingX is compressed into a window of size of hops
		//imagine x,Y coordinate with semi circle with equation (x-R)^2+Y^2=R^2, Y>0
		//for GCanvas however Y coordinate starts at the top, 
		//so need to use hoppingY-Y
		double y = hoppingY-Math.sqrt(hopRadius*hopRadius-(x-hopRadius)*(x-hopRadius));
		//getY()-y is how much fireball needs to move in the y to stay on semi circle path
		//however needs to be negated since positive getY()-y would mean moving up, which is negative y displacement
		//for GCanvas
		move(dx,-(getY()-y));
	}

	@Override
	public void inContactWith(GObject x, boolean horizontalOrVertical) {
		//if (x instanceof Turtle)
		//if (x!=null) System.out.println("FIREBALL RAN INTO SOMETHING");
		//will need to set alive = false when fire ball runs into flower, turtle, side of platform
		if (horizontalOrVertical && x instanceof Mario) {
			alive = false;
			//fireball dies if runs into mario
			//shouldnt happen since fireball is faster, but
			//if mario and luigi shoot fireballs at each other, the fireballs need to die
			//Luigi should extend Mario
			return;
		}

		if (horizontalOrVertical && x instanceof Platform) {
			//fireball dies if it runs into a platform from the side
			alive = false;
			System.out.println("fireball run into platform from side"); 
		} else if (!horizontalOrVertical && x instanceof Platform){
			if (fallingOrHopping) {
				//if fireball falls on platform then it starts hopping 
				fallingOrHopping = false;
				hoppingY = getY();
				hoppingX = getX();
				System.out.println("fireball fall on platform starts hopping");
			}
		} else if (x instanceof BadGuy) {
			//fireball could have been shot by mario or shooting flower
			//a shooting flower can kill another if mario stands behind the one getting shot at
			if (!((BadGuy) x).alive) return;
			//TODO could add sound of bad guy dying
			((BadGuy) x).alive = false;
			x.setVisible(false);
			alive = false;
		} else if (x instanceof FireBall) {
			//if fireball shot from mario runs into another fireball then they both die
			if (!((FireBall) x).alive) return;
			((FireBall) x).alive = false;
			alive = false;
		}
	}

	public static void setObjects(Image leftFireBall1X,
			Image rightFireBall1X,Image leftFireBall2X,
			Image rightFireBall2X,Image leftFireBall3X,
			Image rightFireBall3X,Image leftFireBall4X,
			Image rightFireBall4X) {
		leftFireBall1 = leftFireBall1X;
		rightFireBall1 = rightFireBall1X;
		leftFireBall2 = leftFireBall2X;
		rightFireBall2 = rightFireBall2X;
		leftFireBall3 = leftFireBall3X;
		rightFireBall3 = rightFireBall3X;
		leftFireBall4 = leftFireBall4X;
		rightFireBall4 = rightFireBall4X;
	}
}
