import java.awt.Image;
//import java.awt.event.ActionListener;
import java.util.ArrayList;

//import acm.graphics.GCanvas;
//import acm.graphics.GImage;
import acm.graphics.GObject;

public class Mario extends MovingObject {
	/*
	 *TODO Idea for Mario time dilating Mario the time slows down
	 * modify the global variables that govern the speed of that object, also a super fast flash mario
	 *TODO mario needs to be able to slide off and jump off platform when falling on them from the side
	 *TODO so if mario is !wayUpOrWayDown and isJumping and inContactWith(Platform, horizontalOrVertical==true) and user hits jump then he must jump off in opposite direction
	 *TODO this will let mario be able to come back up from falling between two platforms
	 */
	private MyImage smallMarioLeftImage, smallMarioRightImage, smallMarioLeftWalkingImage,
	smallMarioRightWalkingImage, smallMarioLeftJumpingImage, smallMarioRightJumpingImage, marioDeadImage,
	bigMarioLeftImage, bigMarioRightImage, bigMarioLeftWalkingImage, bigMarioRightWalkingImage,
	bigMarioLeftJumpingImage, bigMarioRightJumpingImage, bigMarioLeftJumpingDownImage,
	bigMarioRightJumpingDownImage, bigMarioLeftCrouchingImage, bigMarioRightCrouchingImage,
	bigMarioLeftFireImage, bigMarioRightFireImage, bigMarioLeftWalkingFireImage,
	bigMarioRightWalkingFireImage, bigMarioRightJumpingFireImage, bigMarioLeftJumpingFireImage,
	bigMarioLeftJumpingDownFireImage, bigMarioRightJumpingDownFireImage, bigMarioLeftCrouchingFireImage,
	bigMarioRightCrouchingFireImage, bigMarioLeftFireShooting1Image, bigMarioLeftFireShooting2Image,
	bigMarioRightFireShooting1Image, bigMarioRightFireShooting2Image, bigMarioLeftJumpingFireShooting1Image,
	bigMarioLeftJumpingFireShooting2Image, bigMarioLeftJumpingFireShooting3Image, bigMarioRightJumpingFireShooting1Image,
	bigMarioRightJumpingFireShooting2Image, bigMarioRightJumpingFireShooting3Image,
	bigMarioLeftCatImage, bigMarioRightCatImage, bigMarioLeftWakingCatImage,
	bigMarioRightWalkingCatImage, bigMarioLeftJumpingCatImage, bigMarioRightJumpingCatImage,
	bigMarioRightJumpingDownCatImage, bigMarioLeftJumpingDownCatImage, bigMarioLeftCrouchingCatImage,
	bigMarioRightCrouchingCatImage, bigMarioLeftJumpingCatTail1Image, bigMarioRightJumpingCatTail1Image,
	bigMarioLeftJumpingCatTail2Image, bigMarioRightJumpingCatTail2Image,
	bigMarioCatTail1Image, bigMarioLeftCatTail2Image, bigMarioRightCatTail2Image, bigMarioCatTail3Image,
	tanookiMarioLeftCatImage, tanookiMarioRightCatImage, tanookiMarioLeftWakingCatImage, tanookiMarioRightWalkingCatImage,
	tanookiMarioLeftJumpingCatImage, tanookiMarioRightJumpingCatImage, tanookiMarioRightJumpingDownCatImage,
	tanookiMarioLeftJumpingDownCatImage, tanookiMarioLeftCrouchingCatImage, tanookiMarioRightCrouchingCatImage,
	tanookiMarioLeftJumpingCatTail1Image, tanookiMarioRightJumpingCatTail1Image, tanookiMarioLeftJumpingCatTail2Image,
	tanookiMarioRightJumpingCatTail2Image,
	tanookiMarioCatTail1Image, tanookiMarioLeftCatTail2Image,
	tanookiMarioRightCatTail2Image, tanookiMarioCatTail3Image,
	smallMarioPipeImage, bigMarioPipeImage, fireMarioPipeImage;

	public boolean bigOrSmall = false;//true if mario is big (still true if mario is in flower mode or cat mode or tanooki mode)
	public boolean isJumping = false;//need to keep track of if mario is jumping or not
	//if he is already jumping and if the user tries to make mario jump he should not
	public boolean wayUpOrWayDown =  false;//if isJumping is false wayUpOrDown's value is meaningless
	//if isJumping is true then if wayUpOrDown is true mario is on the way up
	//if wayUpOrDown is false then mario is on the way down of a jump

	public boolean isFire = false;//true if mario is in fire/flower mode
	public boolean isCat = false;//true if mario is in cat mode
	public boolean isTanooki = false;
	//design decision: small mario eating fire flower/leaf turns directly into fire/cat mario, not just big mario

	public boolean movingRight = false;
	public boolean movingLeft = false;
	//need this bools to support concurrency for jumping and moving right/left at the same time
	//movingRight, movingLeft are true if right,left key are pressed down respectively

	//needed for switching from walking sprite to standing sprite while mario walks
	public boolean walkingRightOrLeft = false;//is true when mario is walking right or left (his leg should be out)
	//is false when mario is standing right or left

	public boolean lookingRightOrLeft = true;//true when looking right and false when looking left
	public boolean isCrouching = false; //true if mario is crouching false if not



	public boolean isSwinging = false;//true if swinging tail, false once user releases swinging tail key
	public boolean isShooting = false;//true if shooting fireball, false once user releases shooting key
	//could technically use isSwinging for isShooting or vice versa because mario cant be cat and fire mario at same time
	public enum SHOOT_FIRE_JUMPING {NOT_SHOOTING, STAGE1, STAGE2, STAGE3};
	public enum SHOOT_FIRE_STANDING {NOT_SHOOTING, STAGE1, STAGE2};
	SHOOT_FIRE_JUMPING shootFireJumping = SHOOT_FIRE_JUMPING .NOT_SHOOTING;
	SHOOT_FIRE_STANDING shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
	//enums above are used to determine what stage of shooting a fireball mario is in
	//if jumping, there are 4 stages, one for not shooting
	//if standing, there are 3, one for not shooting


	private static final int pauseBetweenShots = 10;//pause between each fireball shot, the lower the number the more fireballs mario can shoot per second
	private static final int pauseBetweenSwings = 10;//pause between each tail , the lower the number the faster mario can swing tail

	public enum SWING_TAIL_JUMPING {NOT_SWINGING, STAGE1, STAGE2, STAGE3};//stage 1 tail is parallel to ground, stage 2 tail is down, stage 3 back to parallel
	public enum SWING_TAIL_STANDING {NOT_SWINGING, STAGE1, STAGE2, STAGE3};//stage 1 is looking at user, stage2 right/left, stage3 back to the user
	SWING_TAIL_JUMPING swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;//enums are used for both cat and tanooki mario
	SWING_TAIL_STANDING swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;
	private static final double pauseInAir = 0.7;//lower number means faster jump (less long-pauses in jump function)
	private double pauseGoingDown = pauseInAir;//this is changed while cat or tanooki mario swings tail in the air so he is suspended in the air



	public boolean hitPlatformVertical = false;
	public boolean hitPlatformHorizontal = false;
	//need a hitPlatformVertical and hitPlatformHorizontal because if mario is falling leaning against a platform,
	//hitPlatformHorizontal will be true while hitPlatformVertical should be false so mario keeps on falling
	public boolean goingIntoPipe = false;
	public double fallDy;
	private double moveDx;
	public boolean flashing = false;//true when mario is hit by BadGuy and becomes false after mario stops flashing
	//while mario is flashing he cannot die from a bad guy, but he can still die by falling to bottom of screen
	public static final int flashingTime = 300;//total duration in ms that mario flashes when he comes into contact with BadGuy
	public static final int numTimesToggleVisibility = 20;//number of times mario toggles his visibility to make it look like he is flashing (needs to be an even number)
	public static final int flashingInterval = flashingTime/(numTimesToggleVisibility-1);
	private static final int maxHeightOfJump = 50;//max num times move function is called on way up of jump (move(0, -2.0*fallDy)
	//	public boolean jumpingOnTurtle = false;//so releasing a key while jumping on a turtle doesnt do anything


	private boolean jumpAgain;//set to true in hop() function if mario jumps off bad guy, other Mario, or bouncy platform



	public enum CHARACTER {MARIO, LUIGI};//for now only mario and luigi, could add peach toad, etc as along as they move like mario and have same skins (fire, cat etc)
	CHARACTER character;//to know if this (instance) is Mario, Luigi, etc
	public Mario(MyImage smallMarioLeftImage, MyImage smallMarioRightImage, MyImage smallMarioLeftWalkingImage,
			MyImage smallMarioRightWalkingImage,MyImage smallMarioLeftJumpingImage,
			MyImage smallMarioRightJumpingImage, MyImage marioDeadImage,

			MyImage bigMarioLeftImage, MyImage bigMarioRightImage, MyImage bigMarioLeftWalkingImage, 
			MyImage bigMarioRightWalkingImage, MyImage bigMarioLeftJumpingImage, MyImage bigMarioRightJumpingImage,

			MyImage bigMarioLeftFireImage, MyImage bigMarioRightFireImage, 
			MyImage bigMarioLeftWalkingFireImage, MyImage bigMarioRightWalkingFireImage,
			MyImage bigMarioLeftJumpingFireImage, MyImage bigMarioRightJumpingFireImage,
			MyImage bigMarioLeftJumpingDownImage, MyImage bigMarioRightJumpingDownImage,
			MyImage bigMarioLeftJumpingDownFireImage, MyImage bigMarioRightJumpingDownFireImage, 
			MyImage bigMarioLeftCrouchingImage, MyImage bigMarioRightCrouchingImage,
			MyImage bigMarioLeftCrouchingFireImage, MyImage bigMarioRightCrouchingFireImage,
			MyImage bigMarioLeftFireShooting1Image, MyImage bigMarioLeftFireShooting2Image,
			MyImage bigMarioRightFireShooting1Image, MyImage bigMarioRightFireShooting2Image,
			MyImage bigMarioLeftJumpingFireShooting1Image, MyImage bigMarioLeftJumpingFireShooting2Image,
			MyImage bigMarioLeftJumpingFireShooting3Image,MyImage bigMarioRightJumpingFireShooting1Image,
			MyImage bigMarioRightJumpingFireShooting2Image, MyImage bigMarioRightJumpingFireShooting3Image,

			MyImage bigMarioLeftCatImage, MyImage bigMarioRightCatImage, MyImage bigMarioLeftWakingCatImage, MyImage bigMarioRightWalkingCatImage,
			MyImage bigMarioLeftJumpingCatImage, MyImage bigMarioRightJumpingCatImage, MyImage bigMarioRightJumpingDownCatImage,
			MyImage bigMarioLeftJumpingDownCatImage, MyImage bigMarioLeftCrouchingCatImage, MyImage bigMarioRightCrouchingCatImage,
			MyImage bigMarioLeftJumpingCatTail1Image, MyImage bigMarioRightJumpingCatTail1Image, MyImage bigMarioLeftJumpingCatTail2Image,
			MyImage bigMarioRightJumpingCatTail2Image,
			MyImage bigMarioCatTail1Image, MyImage bigMarioLeftCatTail2Image,
			MyImage bigMarioRightCatTail2Image, MyImage bigMarioCatTail3Image,

			MyImage tanookiMarioLeftCatImage, MyImage tanookiMarioRightCatImage, MyImage tanookiMarioLeftWakingCatImage, MyImage tanookiMarioRightWalkingCatImage,
			MyImage tanookiMarioLeftJumpingCatImage, MyImage tanookiMarioRightJumpingCatImage, MyImage tanookiMarioRightJumpingDownCatImage,
			MyImage tanookiMarioLeftJumpingDownCatImage, MyImage tanookiMarioLeftCrouchingCatImage, MyImage tanookiMarioRightCrouchingCatImage,
			MyImage tanookiMarioLeftJumpingCatTail1Image, MyImage tanookiMarioRightJumpingCatTal1Image, MyImage tanookiMarioLeftJumpingCatTail2Image,
			MyImage tanookiMarioRightJumpingCatTail2Image,
			MyImage tanookiMarioCatTail1Image, MyImage tanookiMarioLeftCatTail2Image,
			MyImage tanookiMarioRightCatTail2Image, MyImage tanookiMarioCatTail3Image,

			MyImage smallMarioPipeImage, MyImage bigMarioPipeImage, MyImage fireMarioPipeImage, CHARACTER character) {
		super(smallMarioRightImage);
		this.character = character;
		this.smallMarioRightImage = smallMarioRightImage;
		this.smallMarioLeftImage = smallMarioLeftImage;
		this.smallMarioLeftWalkingImage = smallMarioLeftWalkingImage;
		this.smallMarioRightWalkingImage = smallMarioRightWalkingImage;
		this.smallMarioLeftJumpingImage = smallMarioLeftJumpingImage;
		this.smallMarioRightJumpingImage = smallMarioRightJumpingImage;
		//fallDy = smallMarioLeftImage.getHeight(canvas)/16.0;
		//System.out.println("FALLDY"+smallMarioLeftImage.getHeight(canvas)/16.0);
		//TODO not a todo but a note fallDy has to be an integer (currently equal to 4.0) will have to change val when changing picture size 
		//fallDy = smallMarioLeftImage.getHeight(canvas)/(20.0);
		//System.out.println("FALLDY"+fallDy);
		//scalingFactor = 1.5*fallDy*20.0/16.0;
		//moveDx = 2.0*fallDy*20.0/16.0;


		this.marioDeadImage = marioDeadImage;

		this.bigMarioRightImage = bigMarioRightImage;
		this.bigMarioLeftImage = bigMarioLeftImage;
		this.bigMarioLeftWalkingImage = bigMarioLeftWalkingImage;
		this.bigMarioRightWalkingImage = bigMarioRightWalkingImage;
		this.bigMarioLeftJumpingImage = bigMarioLeftJumpingImage;
		this.bigMarioRightJumpingImage = bigMarioRightJumpingImage;
		this.bigMarioLeftJumpingDownImage = bigMarioLeftJumpingDownImage;
		this.bigMarioRightJumpingDownImage = bigMarioRightJumpingDownImage;
		this.bigMarioLeftCrouchingImage = bigMarioLeftCrouchingImage;
		this.bigMarioRightCrouchingImage = bigMarioRightCrouchingImage;

		this.bigMarioLeftFireImage = bigMarioLeftFireImage;
		this.bigMarioRightFireImage = bigMarioRightFireImage;
		this.bigMarioLeftWalkingFireImage = bigMarioLeftWalkingFireImage;
		this.bigMarioRightWalkingFireImage = bigMarioRightWalkingFireImage;
		this.bigMarioLeftJumpingFireImage = bigMarioLeftJumpingFireImage;
		this.bigMarioRightJumpingFireImage = bigMarioRightJumpingFireImage;
		this.bigMarioLeftJumpingDownFireImage = bigMarioLeftJumpingDownFireImage;
		this.bigMarioRightJumpingDownFireImage = bigMarioRightJumpingDownFireImage;
		this.bigMarioLeftCrouchingFireImage = bigMarioLeftCrouchingFireImage;
		this.bigMarioRightCrouchingFireImage = bigMarioRightCrouchingFireImage;
		this.bigMarioLeftFireShooting1Image = bigMarioLeftFireShooting1Image;
		this.bigMarioLeftFireShooting2Image = bigMarioLeftFireShooting2Image;
		this.bigMarioRightFireShooting1Image = bigMarioRightFireShooting1Image;
		this.bigMarioRightFireShooting2Image = bigMarioRightFireShooting2Image;
		this.bigMarioLeftJumpingFireShooting1Image = bigMarioLeftJumpingFireShooting1Image;
		this.bigMarioLeftJumpingFireShooting2Image = bigMarioLeftJumpingFireShooting2Image;
		this.bigMarioLeftJumpingFireShooting3Image = bigMarioLeftJumpingFireShooting3Image;
		this.bigMarioRightJumpingFireShooting1Image = bigMarioRightJumpingFireShooting1Image;
		this.bigMarioRightJumpingFireShooting2Image = bigMarioRightJumpingFireShooting2Image;
		this.bigMarioRightJumpingFireShooting3Image = bigMarioRightJumpingFireShooting3Image;

		this.bigMarioLeftCatImage = bigMarioLeftCatImage;
		this.bigMarioRightCatImage = bigMarioRightCatImage;
		this.bigMarioLeftWakingCatImage = bigMarioLeftWakingCatImage;
		this.bigMarioRightWalkingCatImage = bigMarioRightWalkingCatImage;
		this.bigMarioLeftJumpingCatImage = bigMarioLeftJumpingCatImage;
		this.bigMarioRightJumpingCatImage = bigMarioRightJumpingCatImage;
		this.bigMarioRightJumpingDownCatImage = bigMarioRightJumpingDownCatImage;
		this.bigMarioLeftJumpingDownCatImage = bigMarioLeftJumpingDownCatImage;
		this.bigMarioLeftCrouchingCatImage = bigMarioLeftCrouchingCatImage;
		this.bigMarioRightCrouchingCatImage = bigMarioRightCrouchingCatImage;
		this.bigMarioLeftJumpingCatTail1Image = bigMarioLeftJumpingCatTail1Image;
		this.bigMarioRightJumpingCatTail1Image = bigMarioRightJumpingCatTail1Image;
		this.bigMarioLeftJumpingCatTail2Image = bigMarioLeftJumpingCatTail2Image;
		this.bigMarioRightJumpingCatTail2Image = bigMarioRightJumpingCatTail2Image;
		this.bigMarioCatTail1Image = bigMarioCatTail1Image;
		this.bigMarioLeftCatTail2Image = bigMarioLeftCatTail2Image;
		this.bigMarioRightCatTail2Image = bigMarioRightCatTail2Image;
		this.bigMarioCatTail3Image = bigMarioCatTail3Image;


		this.tanookiMarioLeftCatImage = tanookiMarioLeftCatImage;
		this.tanookiMarioRightCatImage = tanookiMarioRightCatImage;
		this.tanookiMarioLeftWakingCatImage = tanookiMarioLeftWakingCatImage;
		this.tanookiMarioRightWalkingCatImage = tanookiMarioRightWalkingCatImage;
		this.tanookiMarioLeftJumpingCatImage = tanookiMarioLeftJumpingCatImage;
		this.tanookiMarioRightJumpingCatImage = tanookiMarioRightJumpingCatImage;
		this.tanookiMarioRightJumpingDownCatImage = tanookiMarioRightJumpingDownCatImage; 
		this.tanookiMarioLeftJumpingDownCatImage = tanookiMarioLeftJumpingDownCatImage;
		this.tanookiMarioLeftCrouchingCatImage = tanookiMarioLeftCrouchingCatImage;
		this.tanookiMarioRightCrouchingCatImage = tanookiMarioRightCrouchingCatImage;
		this.tanookiMarioLeftJumpingCatTail1Image = tanookiMarioLeftJumpingCatTail1Image;
		this.tanookiMarioRightJumpingCatTail1Image = tanookiMarioRightJumpingCatTal1Image;
		this.tanookiMarioLeftJumpingCatTail2Image = tanookiMarioLeftJumpingCatTail2Image;
		this.tanookiMarioRightJumpingCatTail2Image = tanookiMarioRightJumpingCatTail2Image;
		this.tanookiMarioCatTail1Image = tanookiMarioCatTail1Image;
		this.tanookiMarioLeftCatTail2Image = tanookiMarioLeftCatTail2Image;
		this.tanookiMarioRightCatTail2Image = tanookiMarioRightCatTail2Image;
		this.tanookiMarioCatTail3Image = tanookiMarioCatTail3Image;


		this.smallMarioPipeImage = smallMarioPipeImage;
		this.bigMarioPipeImage = bigMarioPipeImage;
		this.fireMarioPipeImage = fireMarioPipeImage;
	}

	public void setFallDy(double dy) {fallDy = dy;}
	public void setMoveDx(double dx) {moveDx=dx;}

	public void moveOnlyMario(double dx, double dy) {
		//moves only mario, not the level
		//used when mario swings his tail for example
		super.move(dx, dy);
	}

	@Override
	public void move(double dx, double dy) {
		//moves mario or current level mario is playing depending on if
		//mario goes too much to the corners of the screen

		double n = 3;
		//1/n of screen on each side mario cannot walk into because level will move
		if (dx<0 && getX()>=(1/n)*canvas.getWidth() || dx>0 && getX()+getWidth()<=((n-1)/n)*canvas.getWidth()
				|| dy>0 && getY()+getHeight()<=((n-1)/n)*canvas.getHeight()
				|| dy<0 &&	getY()>=(1/n)*canvas.getHeight()) {
			if (dy>0 && LevelController.currLevel.yBaseLine>0) {
				//see LevelController baseLine documentation
				LevelController.currLevel.moveLevel(-dx, -dy, this);
				//System.out.println("XXXXX");
				return;
			}
			super.move(dx, dy);
		} else {
			//if (dx<0 && LevelController.currLevel.xBaseLine==0) {
			if (dx<0 && (LevelController.currLevel.xBaseLine==0 || getX()<0 && Math.abs(getX()-LevelController.currLevel.xBaseLine)<15)) {
				//mario can't move left if he is at the leftmost position in level
				if (getX()+dx>=0) super.move(dx, dy);
				return;
			}
			//if (dx>0 && LevelController.currLevel.xBaseLine==canvas.getWidth()-LevelController.currLevel.width) {
			if (dx>0 && (LevelController.currLevel.xBaseLine==canvas.getWidth()-LevelController.currLevel.width 
					|| getX()>0 && Math.abs(getX()+getWidth()-(LevelController.currLevel.width+LevelController.currLevel.xBaseLine))
					<15)) {
				//mario is at right mort portion of level
				//if xbaseline == canvas width-level width then mario is at right most portion of level AND not to the right of the canvas
				if (getX()+getWidth()+dx<=canvas.getWidth()) super.move(dx, dy);
				return;
			}
			if (dy > 0 && LevelController.currLevel.yBaseLine<=0) {
				if (getY()+getHeight()<=canvas.getHeight()) {
					super.move(dx, dy);
					return;
				}
				//mario touched bottom of screen 
				System.out.println("MARIO DEAD he touched bottom of screen");
				marioDied();
				return;
			}
			if (getX()<0 || getX()>canvas.getWidth()) {
				//mario is off screen but not at the complete beginning or end of the level
				super.move(dx, dy);
			} else LevelController.currLevel.moveLevel(-dx, -dy, this);
		}
	}

	public static boolean anotherMarioAlreadyDied(Mario mario) {
		//if 2 or more marios are playing at the same time and one dies
		//after another mario already died, then we dont want to restart the current level twice or more
		for (Mario m: MovingObject.characters) {
			if (m!=mario && !m.alive) return true;
		}
		return false;
	}

	public void marioDied() {
		alive = false;
		movingRight = false;//in case user releases left/right keys right after mario dies
		movingLeft = false;
		boolean anotherMarioAlreadyDied = anotherMarioAlreadyDied(this); 
		if (!anotherMarioAlreadyDied) SoundController.playMarioDeathSound();
		setImageAndRelocate(marioDeadImage);
		sendToFront();
		try {
			//alive can be set to true in LevelController.addCharactersAtStartOfLevel
			//if mario1 dies first and as he is in dead sprite mario2 dies
			//then mario2 does not have time to finish his jump in dead sprite
			//because mario1's death will end the current level
			for (int i=0; i<30; i++) {
				if (alive) return;
				super.move(0,-fallDy);
				ThreadSleep.sleep(1.5);
			}
			ThreadSleep.sleep(25);
			double maxTimeFallDown = 1500;
			while (maxTimeFallDown>0 && getY()+getHeight()+fallDy<=canvas.getHeight()) {
				if (alive) return;
				super.move(0,fallDy);
				ThreadSleep.sleep(1.5);
				maxTimeFallDown -= 15;
			}
			while (maxTimeFallDown>0) {
				if (alive) return;
				ThreadSleep.sleep(1.5);
				maxTimeFallDown -= 15;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (alive) return;
		setToAlive(true);
		if (!anotherMarioAlreadyDied) LevelController.restartCurrentLevel();//when mario dies restart the level
	}

	public void setToAlive(boolean small) {
		alive = true;
		flashing = false;
		setVisible(true);
		goingIntoPipe = false;
		if (small) setToSmall();//need to change mario from dead sprite to small sprite
		lookingRightOrLeft = true;//mario should be looking right
		lookInCorrectDirection(true);
	}


	public void setToTanooki() {
		if (isTanooki) return;
		if (lookingRightOrLeft) {
			if (isJumping) {
				//need to check if jumping because mario can jump in the air to 
				//reach for the leaf
				setImageAndRelocate(tanookiMarioRightJumpingCatImage);
			} else {
				setImageAndRelocate(tanookiMarioRightCatImage);
			}
		} else {
			if (isJumping) {
				setImageAndRelocate(tanookiMarioLeftJumpingCatImage);
			} else {
				setImageAndRelocate(tanookiMarioLeftCatImage);
			}
		}
		bigOrSmall = true;
		isFire = false;
		isCat = false;
		isTanooki = true;
		swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
		swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;
		if (isCrouching) lookInDirectionCrouching(lookingRightOrLeft);
	}

	public void setToCat() {
		if (isCat) return;
		if (lookingRightOrLeft) {
			if (isJumping) {
				//need to check if jumping because mario can jump in the air to 
				//reach for the leaf
				setImageAndRelocate(bigMarioRightJumpingCatImage);
			} else {
				setImageAndRelocate(bigMarioRightCatImage);
			}
		} else {
			if (isJumping) {
				setImageAndRelocate(bigMarioLeftJumpingCatImage);
			} else {
				setImageAndRelocate(bigMarioLeftCatImage);
			}
		}
		bigOrSmall = true;
		isFire = false;
		isCat = true;
		isTanooki = false;
		swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
		swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;
		if (isCrouching) lookInDirectionCrouching(lookingRightOrLeft);
	}

	public void setToBig() {
		//can be called if mario eats mushroom to grow
		//or if fire mario or cat mario gets hit by something and goes back 
		//to big mario
		if (bigOrSmall && !isFire && !isCat && !isTanooki) return;
		if (lookingRightOrLeft) {
			if (isJumping) {
				//need to check if jumping because mario can jump in the air to 
				//reach for the mushroom
				setImageAndRelocate(bigMarioRightJumpingImage);
			} else {
				setImageAndRelocate(bigMarioRightImage);
			}
		} else {
			if (isJumping) {
				setImageAndRelocate(bigMarioLeftJumpingImage);
			} else {
				setImageAndRelocate(bigMarioLeftImage);
			}
		}
		bigOrSmall = true;
		isFire = false;
		isCat = false;
		isTanooki = false;
		if (isCrouching) lookInDirectionCrouching(lookingRightOrLeft);
	}

	public void setToSmall() {
		//can be called if big mario gets hit by something
		if (!bigOrSmall) return;//return if mario already small
		if (lookingRightOrLeft) {
			if (isJumping) {
				//need to check if jumping because mario can jump in the air and
				//get hit by something that makes him small
				setImageAndRelocate(smallMarioRightJumpingImage);
			} else {
				setImageAndRelocate(smallMarioRightImage);
			}
		} else {
			if (isJumping) {
				setImageAndRelocate(smallMarioLeftJumpingImage);
			} else {
				setImageAndRelocate(smallMarioLeftImage);
			}
		}
		bigOrSmall = false;
		isFire = false;
		isCat = false;
		isTanooki = false;
		isCrouching = false;
	}

	public void setToFire() {
		if (isFire) return;
		if (lookingRightOrLeft) {
			if (isJumping) {
				//need to check if jumping because mario can jump in the air for flower
				setImageAndRelocate(bigMarioRightJumpingFireImage);
			} else {
				setImageAndRelocate(bigMarioRightFireImage);
			}
		} else {
			if (isJumping) {
				setImageAndRelocate(bigMarioLeftJumpingFireImage);
			} else {
				setImageAndRelocate(bigMarioLeftFireImage);
			}
		}
		bigOrSmall = true;//if small mario takes flower he becomes flower mario (flower mario is also big, cat as well)
		isFire = true;
		isCat = false;
		isTanooki = false;
		shootFireJumping = SHOOT_FIRE_JUMPING .NOT_SHOOTING;
		shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
		if (isCrouching) lookInDirectionCrouching(lookingRightOrLeft);
	}

	public void lookInDirectionCrouching(boolean rightOrLeft) {
		if (rightOrLeft) {
			if (isFire) {
				setImageAndRelocate(bigMarioRightCrouchingFireImage);
			} else if (isCat){
				setImageAndRelocate(bigMarioRightCrouchingCatImage);
			} else if (isTanooki){
				setImageAndRelocate(tanookiMarioRightCrouchingCatImage);
			} else { 
				setImageAndRelocate(bigMarioRightCrouchingImage);
			}
		} else {
			if (isFire) {
				setImageAndRelocate(bigMarioLeftCrouchingFireImage);
			} else if (isCat){
				setImageAndRelocate(bigMarioLeftCrouchingCatImage);
			} else if (isTanooki){
				setImageAndRelocate(tanookiMarioLeftCrouchingCatImage);
			} else {
				setImageAndRelocate(bigMarioLeftCrouchingImage);
			}
		}
	}

	public void setToCrouching() {
		//called when down array key pressed



		if (!bigOrSmall || isCrouching) {
			//small mario cant crouch but can still go into pipe
			checkCanGoDownIntoPipe();
			return;
		}
		if (isJumping && shootFireJumping!=SHOOT_FIRE_JUMPING.NOT_SHOOTING) {
			//JUMPING AND SHOOTING
			//mario doesn't crouch if shooting
			//return;
		}
		if (!isJumping && shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
			//STANDING AND SHOOTING
			//mario doesn't crouch if shooting
			//return;
		}
		if (!isJumping) {
			//if mario is not jumping and crouches he must come to a stop
			if (movingRight) {
				//TODO animation for mario stopping by crouching down
				//could be cooler than just stopping at once
				//movingRight = false;
			}
			if (movingLeft) {
				//TODO see todo above
				//movingLeft = false;
			}
		}
		lookInDirectionCrouching(lookingRightOrLeft);
		isCrouching = true;
		checkCanGoDownIntoPipe();

	}

	public void checkCanGoDownIntoPipe() {
		//need to check if mario is on top of a PipePart
		//TODO ALSO BUG WHERE MULTIPLE MARIO CAN GO INTO PIPE AT SAME TIME
		//TODO check to see if another mario is already going down a pipe
		if (goingIntoPipe) return;
		if (!isJumping) {
			GObject o = canvas.getElementAt(getX()+getWidth()/2, getY()+getHeight()+20);
			if (o!=null && o instanceof PipePart && ((PipePart) o).upOrDownPipe) {
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						System.out.println("MARIO GOES DOWN INTO PIPE "+((PipePart) o).subLevelID);
						goIntoPipe(false, (PipePart) o);
					}
				});
				t1.setName(this.character.name()+ " fall in pipe");
				t1.start();
			}
		}
	}

	public void setToPipe() {
		if (!bigOrSmall) {
			setImageAndRelocate(smallMarioPipeImage);
		} else if (isFire) {
			setImageAndRelocate(fireMarioPipeImage);
		} else if (isCat){
			setImageAndRelocate(bigMarioCatTail1Image);
			//CAT MARIO'S PIPE SPRITE is the same as when he swings his tail
			//and faces user
		} else if (isTanooki){
			setImageAndRelocate(tanookiMarioCatTail1Image);
			//CAT MARIO'S PIPE SPRITE is the same as when he swings his tail
			//and faces user
		}  else { 
			setImageAndRelocate(bigMarioPipeImage);
		}
	}

	public void stopCrouching() {
		//TODO need to check if there is nothing above mario preventing him from uncrouching (ceiling)
		if (!bigOrSmall) return;
		if (!isJumping)
			lookInCorrectDirection(lookingRightOrLeft);//sets back to standing sprite looking in correct direction
		else if (wayUpOrWayDown) {
			setToJumping(lookingRightOrLeft);
		} else {
			setToJumpingDown(lookingRightOrLeft);
		}
		isCrouching = false;
	}

	public void jump() {
		if (isJumping) {
			return;
		}
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//code in here runs in another thread since mario can go right or left while jumping
				//has to be done concurrently

				//int dy = 10;

				//if (isJumping) {
				//	return;
				//}
				//if (shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
				//if mario is shooting fireball while standing then he can jump
				//}
				//if (swingTailStanding!=SWING_TAIL_STANDING.NOT_SWINGING) {
				//if mario is in middle of swinging tail while standing then he can jump 
				//}


				isJumping = true;
				jumpAgain = true;

				while (jumpAgain) {
					jumpAgain = false;
					SoundController.playMarioJumpSound();

					wayUpOrWayDown = true;
					if (!isCrouching) {
						setToJumping(lookingRightOrLeft);
					}
					for (int i=0; i<maxHeightOfJump && wayUpOrWayDown; i++) {
						// for 3 points over mario (left middle and right)
						Point[] arr = new Point[]{new Point(getX()+10,getY()-fallDy),
								new Point(getX()+getWidth()/2, getY()-fallDy),
								new Point(getX()+getWidth()-10, getY()-fallDy)};
						ArrayList<GObject> o = checkAtPositions(arr);
						for (GObject x : o) {
							inContactWith(x, false);
							if (hitPlatformVertical) {
								//if mario jumps into a Platform, he needs to stop moving up
								//and start moving down, making it look like the Platform stopped him
								move(0, -fallDy);
								break;
							}
						}
						if (hitPlatformVertical) {
							hitPlatformVertical = false;
							break;
						}
						if (!alive) {isJumping = false; return;}
						move(0, -fallDy);
						//HAS to be multiple of fallDy (1.0*fallDy, 2.0*fallDy, etc)so currLevel.yBaseLine goes back to zero
						//System.out.println("GOING UP");

						ThreadSleep.sleep(pauseInAir);

					}

					ThreadSleep.sleep(8);//PAUSE AT TOP OF JUMP

					if (goingIntoPipe) {
						isJumping = false;
						return;//in case mario jumps into a pipe he shouldnt fall back down
					}
					wayUpOrWayDown = false;
					if (bigOrSmall && !isCrouching) {
						//if mario is not small then he doesn't stay in jumping sprite on the way down
						setToJumpingDown(lookingRightOrLeft);
					}
					fall(fallDy);
					if (!isCrouching && alive) {
						lookInCorrectDirection(lookingRightOrLeft);//sets back to standing sprite looking in correct direction
					}

					//hitPlatformVertical = false;
				}
				isJumping = false;
				//System.out.println("Stopping jump!!!!!!!!!!!!!!!!!!!!!!");
			}
		});
		t1.setName(this.character.name()+ " jump");
		t1.start();
	}

	public void fall(double dy) {
		while (!hitPlatformVertical && alive) {//mario falls down until he hits a platform
			checkUnder(dy);
			move(0, dy);
			//System.out.println("Going down");
			if (hitPlatformVertical) {
				//if mario jumps onto a Platform, he needs to stop moving down
				hitPlatformVertical = false;
				break;
			}

			ThreadSleep.sleep(pauseGoingDown);

		}
	}

	public void checkUnder(double dy) {
		//checks if mario is in contact with something under him (mushroom, Platform,...)
		//check for 2 points under mario (left and right)
		//0.3 is value found to work best through testing (0.22 too)
		Point[] arr = new Point[]{new Point(getX()+0.2*getWidth(),getY()+getHeight()+dy),
				new Point(getX()+0.5*getWidth(), getY()+getHeight()+dy),
				new Point(getX()+0.75*getWidth(), getY()+getHeight()+dy)};
		ArrayList<GObject> o = checkAtPositions(arr);
		for (GObject x : o) {
			//System.out.println("here");
			inContactWith(x, false);

			if (hitPlatformVertical) return;
		}
	}

	public void setToJumping(boolean rightOrLeft) {
		if (rightOrLeft) {
			if (bigOrSmall) {
				if (isFire) {
					setImageAndRelocate(bigMarioRightJumpingFireImage);
				} else if (isCat){
					setImageAndRelocate(bigMarioRightJumpingCatImage);
				} else if (isTanooki){
					setImageAndRelocate(tanookiMarioRightJumpingCatImage);
				} else {
					setImageAndRelocate(bigMarioRightJumpingImage);
				}
			} else {
				setImageAndRelocate(smallMarioRightJumpingImage);
			}
		} else {
			if (bigOrSmall) {
				if (isFire) {
					setImageAndRelocate(bigMarioLeftJumpingFireImage);
				} else if (isCat){
					setImageAndRelocate(bigMarioLeftJumpingCatImage);
				}  else if (isTanooki){
					setImageAndRelocate(tanookiMarioLeftJumpingCatImage);
				} else {
					setImageAndRelocate(bigMarioLeftJumpingImage);
				}
			} else {
				setImageAndRelocate(smallMarioLeftJumpingImage);
			}
		}
	}

	public void setToJumpingDown(boolean rightOrLeft) {
		//mario has different sprite for jumping on the way up or down (except small Mario)
		if (rightOrLeft) {
			if (isFire) {
				setImageAndRelocate(bigMarioRightJumpingDownFireImage);
			} else if (isCat){
				setImageAndRelocate(bigMarioRightJumpingDownCatImage);
			} else if (isTanooki){
				setImageAndRelocate(tanookiMarioRightJumpingDownCatImage);
			} else {
				setImageAndRelocate(bigMarioRightJumpingDownImage);
			}
		} else {
			if (isFire) {
				setImageAndRelocate(bigMarioLeftJumpingDownFireImage);
			} else if (isCat){
				setImageAndRelocate(bigMarioLeftJumpingDownCatImage);
			} else if (isTanooki){
				setImageAndRelocate(tanookiMarioLeftJumpingDownCatImage);
			}  else {
				setImageAndRelocate(bigMarioLeftJumpingDownImage);
			}
		}
	}

	public void toggleWalking(boolean rightOrLeft) {
		if (rightOrLeft) {
			if (walkingRightOrLeft) {
				if (bigOrSmall) {
					if (isFire) {
						setImage(bigMarioRightFireImage);
					} else if (isCat){
						setImage(bigMarioRightCatImage);
					} else if (isTanooki){
						setImage(tanookiMarioRightCatImage);
					}  else {
						setImage(bigMarioRightImage);
					}
				} else {
					setImage(smallMarioRightImage);
				}
				walkingRightOrLeft = false;
			} else {
				if (bigOrSmall) {
					if (isFire) {
						setImage(bigMarioRightWalkingFireImage);
					} else if (isCat){
						setImage(bigMarioRightWalkingCatImage);
					} else if (isTanooki){
						setImage(tanookiMarioRightWalkingCatImage);
					} else {
						setImage(bigMarioRightWalkingImage);
					}
				} else {
					setImage(smallMarioRightWalkingImage);
				}
				walkingRightOrLeft = true;
			}
		} else {
			if (walkingRightOrLeft) {
				if (bigOrSmall) {
					if (isFire) {
						setImage(bigMarioLeftFireImage);
					} else if (isCat){
						setImage(bigMarioLeftCatImage);
					} else if (isTanooki){
						setImage(tanookiMarioLeftCatImage);
					} else {
						setImage(bigMarioLeftImage);
					}
				} else {
					setImage(smallMarioLeftImage);
				}
				walkingRightOrLeft = false;
			} else {
				if (bigOrSmall) {
					if (isFire) {
						setImage(bigMarioLeftWalkingFireImage);
					} else if (isCat){
						setImage(bigMarioLeftWakingCatImage);
					} else if (isTanooki){
						setImage(tanookiMarioLeftWakingCatImage);
					} else {
						setImage(bigMarioLeftWalkingImage);
					}
				} else {
					setImage(smallMarioLeftWalkingImage);
				}
				walkingRightOrLeft = true;
			}
		}
	}

	public void setToStanding(boolean rightOrLeft) {
		//function called in key released when mario stops walking his sprite must be standing
		if (rightOrLeft) {
			if (bigOrSmall) {
				if (isFire) {
					setImage(bigMarioRightFireImage);
				} else if (isCat){
					setImage(bigMarioRightCatImage);
				} else if (isTanooki){
					setImage(tanookiMarioRightCatImage);
				} else {
					setImage(bigMarioRightImage);
				}
			} else {
				setImage(smallMarioRightImage);
			}
		} else {
			if (bigOrSmall) {
				if (isFire) {
					setImage(bigMarioLeftFireImage);
				} else if (isCat){
					setImage(bigMarioLeftCatImage);
				} else if (isTanooki){
					setImage(tanookiMarioLeftCatImage);
				} else {
					setImage(bigMarioLeftImage);
				}
			} else {
				setImage(smallMarioLeftImage);
			}
		}
		walkingRightOrLeft = false;
	}

	public void lookInCorrectDirection(boolean rightOrLeft) {
		//sets mario to standing position (not walking or sprinting or jumping)
		if (rightOrLeft) {
			if (bigOrSmall) {
				if (isFire) {
					setImageAndRelocate(bigMarioRightFireImage);
				} else if (isCat) {
					setImageAndRelocate(bigMarioRightCatImage);
				} else if (isTanooki) {
					setImageAndRelocate(tanookiMarioRightCatImage);
				} else {
					setImageAndRelocate(bigMarioRightImage);
				}
			} else {
				setImageAndRelocate(smallMarioRightImage);
			}
		} else {
			if (bigOrSmall) {
				if (isFire) {
					setImageAndRelocate(bigMarioLeftFireImage);
				} else if (isCat){
					setImageAndRelocate(bigMarioLeftCatImage);
				} else if (isTanooki){
					setImageAndRelocate(tanookiMarioLeftCatImage);
				} else {
					setImageAndRelocate(bigMarioLeftImage);
				}
			} else {
				setImageAndRelocate(smallMarioLeftImage);
			}
		}
		//lookingRightOrLeft = rightOrLeft;
	}

	public void move(boolean rightOrLeft) {
		//rightOrLeft is true for moving right and false for moving left
		//this function is used to move mario right and left using the right and left arrows
		//runs on different thread so mario can jump and move right/left concurrently
		if (rightOrLeft && movingRight) return;
		if (!rightOrLeft && movingLeft) return;
		if (!isJumping && swingTailStanding!=SWING_TAIL_STANDING.NOT_SWINGING) return;//cant move if swinging tail and standing
		if (isCrouching) {
			lookingRightOrLeft = rightOrLeft;
			lookInDirectionCrouching(rightOrLeft);
			if (!isJumping) return;//mario cant move if he is crouching and not jumping
		}
		boolean b = false;
		if (rightOrLeft) {
			movingRight = true;
			movingLeft = false;//prevents mario from bugging and trying to move both right and left at same time
			b = true;
		} else {
			movingLeft = true;
			movingRight = false;//prevents mario from bugging and trying to move both right and left at same time
		}
		lookingRightOrLeft = rightOrLeft;
		if (!isJumping) {
			if (shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
				//to change direction while shooting fireball
				//lookCorrectWayShootingFire(rightOrLeft);
				//line above commented because even though it is technically correct,
				//there is no point in changing direction if standing and shooting
				//because mario starts walking right after so the change is not even noticeable
			} else {
				lookInCorrectDirection(rightOrLeft);
			}
		} else if (isJumping && !bigOrSmall) {
			//!bigOrSmall because only small mario stays in jumping
			//sprite entire time he is in the air
			setToJumping(rightOrLeft);
		} else if (isJumping && bigOrSmall) {
			//to be able to change directions in the air for big mario
			//big mario (flower, cat, etc) has different sprite for up part of jump and down part
			if (isCrouching) {
				lookInDirectionCrouching(rightOrLeft);
			} else if (shootFireJumping!=SHOOT_FIRE_JUMPING.NOT_SHOOTING) {
				//MOVING IN THE AIR WHILE SHOOTING FIRE BALL
				lookCorrectWayShootingFire(rightOrLeft);
			} else if (swingTailJumping!=SWING_TAIL_JUMPING.NOT_SWINGING) {
				lookCorrectWaySwingingTail(rightOrLeft);
			} else if(wayUpOrWayDown) {
				setToJumping(rightOrLeft);
			} else {
				setToJumpingDown(rightOrLeft);
			}
		}
		final boolean c = b;
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				int counter = 3;
				int x = 3;
				//counter is used to toggle mario from walking to not walking every x number of times
				//moveHelper is called (moveHelper only moves mario a few pixels so cant alternate
				//between walking and standing sprite each time)
				if (c) {
					while (movingRight) {
						//movingRight is set to false when right key is released
						if (counter==x) {
							moveHelper(rightOrLeft, true);
							counter = 0;
						} else {
							counter++;
							moveHelper(rightOrLeft, false);
						}
					}
				} else {
					while (movingLeft) {
						//movingLeft is set to false when left key is released
						if (counter==x) {
							moveHelper(rightOrLeft, true);
							counter = 0;
						} else {
							counter++;
							moveHelper(rightOrLeft, false);
						}
					}
				}
			}
		}); 
		t1.setName(this.character.name()+ " horizontal movement");
		t1.start();
	}

	public void moveHelper(boolean rightOrLeft, boolean toggleWalking) {
		//this function moves mario right or left once, is repeatedly called to move mario continuously
		if (!isJumping && (isCrouching || isSwinging)) {
			//if mario is on ground (not jumping) and crouching or swinging, he cant move to the side
			//once he stops crouching or swinging he will start moving
			//also if mario is moving right or left in 
			//the air while being crouched once he lands (!isJumping), he should come to a stop;
			return;
		}
		//arbitrary dx of 10 to move mario not too much
		double dx = rightOrLeft?moveDx:-moveDx;
		double newX = rightOrLeft?getX()+getWidth()+dx:getX()+dx;//+dx cause dx is already negative
		//TODO bug where fire mario can walk through Platform if he is rapidly shooting fireballs!
		/*
		 * mario only checks if he walks into an object
		 * if an object were to run into him, such as turtle or mushroom,
		 * it would be in that object's move function that handling of such collision
		 * would be done (mario doesn't check if something runs into him from behind for example)
		 */
		int n = 8;//number of points to check
		Point[] arr = new Point[n-1];
		for (int i = 1; i<n; i++) {
			arr[i-1] = new Point(newX, getY()+((double) i)/n*getHeight());
		}
		ArrayList<GObject> o = checkAtPositions(arr);
		for (GObject x : o) {
			if (inContactWith(x, true)) break;
		}
		if (!hitPlatformHorizontal) {
			move(dx, 0);
		} else {
			hitPlatformHorizontal = false;
		}
		if (!isJumping && toggleWalking) {			
			//in the move function this means that mario is on the ground and moving
			//so he is walking and needs to have his sprite toggle from walking to standing repeatedly
			toggleWalking(rightOrLeft);
		}
		if (!isJumping) {
			//check if there is no Platform under mario in which case he should fall down
			//if mario jumps on Platform then walks off it he is not jumping but should fall down
			//until he hits a Platform or bottom of screen
			//hitPlatformVertical = false;
			checkUnder(10);

			if (!hitPlatformVertical && getY()+getHeight()+10<=canvas.getHeight()) { 
				//if mario is not on top of a Platform he needs to fall down 
				//(unless he is already at the bottom of the screen)
				//System.out.println("NOT ON Platform SHOULD FALL");
				Thread t1 = new Thread(new Runnable() {
					@Override
					public void run() {
						if (bigOrSmall) setToJumpingDown(rightOrLeft);
						else setToJumping(rightOrLeft);
						isJumping = true;
						fall(fallDy);
						lookInCorrectDirection(lookingRightOrLeft);//sets back to standing sprite looking in correct direction
						isJumping = false;
						//System.out.println("DONE FALLING");
					}
				});  
				t1.setName(this.character.name()+ " fall off platform");
				t1.start();
			} else {
				//System.out.println("ON Platform OR at bottom of screen");
				hitPlatformVertical = false;
			}
		}

		ThreadSleep.sleep(2);

	}

	@Override
	public boolean inContactWith(GObject o, boolean horizontalOrVertical) {
		//input o is object mario came into contact with
		if (o instanceof MovingObject) {
			if (!((MovingObject) o).alive) {
				//if o is from a previous level that has been cleared from canvas
				//but the (invisible) power ups still affect mario
				System.out.println("RAN INTO DEAD OBJECT");
				return false;
			}
		}
		if (o instanceof Mario && o.equals(this)) {
			//if points used for edge detection are in frame of this mario
			System.out.println("ITSA ME MARIO");
		} else if (o instanceof Mushroom) {
			System.out.println("ITS MUSHROOM");
			((Mushroom) o).kill();
			//if (!isFire && !isCat && !isTanooki) setToBig();//if mario is in flower mode, cat mode, or tanooki mode, dont want mushroom to make him big
			if (!bigOrSmall) setToBig();//if mario is in flower mode, cat mode, or tanooki mode, dont want mushroom to make him big
			SoundController.playPowerUpSound();
		} else if (o instanceof FireFlower) {
			((FireFlower) o).kill();
			setToFire();
			SoundController.playPowerUpSound();
		} else if (o instanceof Leaf) {
			System.out.println("HIT LEEAFFF");
			((Leaf) o).kill();
			setToCat();
			SoundController.playPowerUpSound();
		} else if (o instanceof Tanooki) {
			System.out.println("HIT TANOOKI");
			((Tanooki) o).kill();
			setToTanooki();
			SoundController.playPowerUpSound();
		} else if (o instanceof Platform) {
			//mario should halt, he cant move into a Platform
			//System.out.println("IN CONTAC WITH Platform");
			if (horizontalOrVertical) hitPlatformHorizontal = true;
			if (!horizontalOrVertical) hitPlatformVertical = true;
			//System.out.println("HIT PLatform");
			//System.out.println("MARIO BOUND: "+getX()+ " to "+(getX()+getWidth()));
			//System.out.println("Platform BOUND: "+o.getX()+ " to "+(o.getX()+o.getWidth()));

			//System.out.println("MARIO Y BOUND: "+getY()+ " to "+(getY()+getHeight()));
			//System.out.println("Platform Y BOUND: "+o.getY()+ " to "+(o.getY()+o.getHeight()));
			if (wayUpOrWayDown && !horizontalOrVertical) {
				//System.out.println("HIT Platform FROM UNDER!!!!!!");

				//if (getY()!=o.getY()+o.getHeight()) {
				//System.out.println("HIT Platform FROM SIDE"+ horizontolOrVertical);
				//} else {
				//	System.out.println("HIT Platform FROM UNDER");
				if (o instanceof MysteryBox) {
					System.out.println("MARIO HIT MYSTERYBOX FROM UNDER");
					if (Math.abs(getY()-o.getY()-o.getHeight())>20) return false;
					if (!((MysteryBox) o).stateIsFinal()) {
						SoundController.playItemOutOfBoxSound();
						double x  = o.getX();
						double y = o.getY();					
						((MysteryBox) o).hitByMario();
						if (Math.random()>0.75)
							DynamicFactory.addFireFlower(x, y, o.getWidth());
						else if (Math.random()>0.5)
							DynamicFactory.addMushroom(x, y, o.getWidth());
						else if (Math.random()>0.25)
							DynamicFactory.addLeaf(x, y, o.getWidth());
						else
							DynamicFactory.addTanooki(x, y, o.getWidth());
					}
				} else if (o instanceof PipePart) {
					//mario jumped into a pipe part, need to make him go into pipe
					//mario cannot jumps into pipe while swinging tail/shooting fireball
					if (shootFireJumping!=SHOOT_FIRE_JUMPING.NOT_SHOOTING) {
						return false;
					}
					if (swingTailJumping!=SWING_TAIL_JUMPING.NOT_SWINGING) {
						return false;
					}
					if (isCrouching) {
						System.out.println("MARIO CANT JUMP INTO PIPE IF HE IS CROUCHING");
						return false;
					}
					if (((PipePart) o).upOrDownPipe) {
						System.out.println("mario tries jumping into an up pipe instead of down pipe");
						return false;
					}
					if (goingIntoPipe) return false;

					Thread t1 = new Thread(new Runnable() {
						@Override
						public void run() {
							System.out.println("MARIO JUMP INTO PIPE "+((PipePart) o).subLevelID);
							goIntoPipe(true, (PipePart) o);
						}
					});
					t1.setName(this.character.name()+ " jumping in pipe");
					t1.start();


				} else if (o instanceof Platform) {
					System.out.println("MARIO JUMPED INTO PLATFORM");
				}
				//	}
			}
		} else if (o instanceof FireBall) {
			System.out.println("MARIO RAN/JUMPed INTO A FIREBALL");
			canvas.remove(o);
			((FireBall) o).alive = false;
			//LevelController.currLevel.removeDynamic((FireBall) o);
			marioHit();
		} else if (o instanceof BadGuy) {
			//make mario smaller when he hits a BadGuy (turtle, flower etc)
			//also play sound
			if (!horizontalOrVertical && isJumping && !wayUpOrWayDown) {
				((BadGuy) o).jumpedOnByMario(this);
			} else if (horizontalOrVertical){
				System.out.println("\n\n"+this.character.name()+" ran into BADGUY\n\n");
				((BadGuy) o).contactFromSideByMario(this);
				return true;
			} else {marioHit();}
			/*if (o instanceof RedTurtle) {
					//need to MAKE TURTLE GO TO shell mode or start spinning if already in shell mode
					//if (bigOrSmall && !wayUpOrWayDown) ((RedTurtle) o).jumpedOnByBigMario(this);
					((RedTurtle) o).jumpedOnByMario(this);//big or small mario can jump on turtle
				} else if (o instanceof BulletBill){
					//mario jumps on BulletBill
					this.hop();
					((BulletBill) o).jumpedOnByMario(this);
				} else if (o instanceof Goomba) {
					((Goomba) o).jumpedOnByMario(this);
				}
			} else {
				marioHit();
			}*/
		} else if (o instanceof Mario && !o.equals(this)) {
			//mario in contact with another mario (luigi for example)
			//want mario to treat other marios as platforms from the side
			//if mario jumps on another mario he should hop() off
			if (horizontalOrVertical) hitPlatformHorizontal = true;
			else if (wayUpOrWayDown) hitPlatformVertical = true;
			else {this.hop();}//if mario is on way down and comes in contact with other mario he hops off
		} else if (o instanceof Coin) {
			((Coin) o).collectedByMario(this);
		}
		//	if (o instanceof PowerUp) {
		//		((PowerUp) o).alive = false;
		//	LevelController.currLevel.removeDynamic((PowerUp) o);//mostly for FireFlower since every other power up would call this function after their alive field is set to false
		//}
		return false;
	}

	public void marioHit() {
		if (flashing || !alive) {
			return;
		}
		flashing = true;
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("Mario hit by a BadGuy or fireball shot from shooting flower");
				if (!bigOrSmall) {
					marioDied();
					flashing = false;
					return;
				}
				SoundController.playMarioHitSound();
				if (isFire || isCat || isTanooki) setToBig();
				else if (bigOrSmall) setToSmall();
				flash();
			}
		});  
		t1.setName(this.character.name()+ " hit by badguy");
		t1.start();
	}

	public void flash() {
		//flashingInterval
		//flashingTime
		try {
			for (int i=0; i<numTimesToggleVisibility; i++) {
				if (!alive || !flashing) {
					setVisible(true);
					flashing = false;
					return;//if mario gets hit (is flashing) and then falls to bottom of screen he shouldnt flash anymore
				}
				setVisible(!isVisible());
				ThreadSleep.sleep(flashingInterval);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flashing = false;
		//TODO bug where mario stop flashing when he is on top of bad guy but he doesnt get hit again
	}

	public void goIntoPipe(boolean upOrDown, PipePart o) {
		if (!alive) return;
		goingIntoPipe = true;//to disable user from moving mario as he is going into pipe
		//setting movingRight/Left to false in case mario
		//is moving while going into a pipe
		movingRight = false;
		movingLeft = false;

		SoundController.playMarioGoesIntoPipeSound();
		setToPipe();
		this.sendToBack();
		double centerXPipe = o instanceof LeftPipePart?o.getX()+o.getWidth():o.getX(); 
		double marioNewX = centerXPipe-getWidth()/2; //to recenter mario so he goes into the center of the pipe

		double dx = getX()<marioNewX?moveDx:-moveDx;
		while (Math.abs(getX()-marioNewX)>20) {
			//to move mario to center of pipe
			moveOnlyMario(dx, 0);

			ThreadSleep.sleep(2);

		}
		this.setLocation(marioNewX, this.getY());

		double x = 15.0;
		double dy = getHeight()/x;
		if (upOrDown) dy = -dy;
		for (int i=0; i<x; i++) {
			ThreadSleep.sleep(4);
			moveOnlyMario(0, dy);
		}

		System.out.println("MARIO CAN MOVE AGAIN");
		goingIntoPipe = false;

		if (!upOrDown) isCrouching = false;
		lookInCorrectDirection(lookingRightOrLeft);
		LevelController.playLevel(o.subLevelID);
	}

	public void lookCorrectWayShootingFire(boolean rightOrLeft) {
		//this func is called in move function to look in correct direction in
		//the middle of shooting a fireball in the air
		if (rightOrLeft) {
			if (shootFireJumping==SHOOT_FIRE_JUMPING.STAGE1) {
				setImageAndRelocate(bigMarioRightJumpingFireShooting1Image);
			} else if (shootFireJumping==SHOOT_FIRE_JUMPING.STAGE2) {
				setImageAndRelocate(bigMarioRightJumpingFireShooting2Image);
			} else if (shootFireJumping==SHOOT_FIRE_JUMPING.STAGE3) {
				setImageAndRelocate(bigMarioRightJumpingFireShooting3Image);
			} else {
				System.out.println("SHOULD NEVER HAPPEN1!!!!!!!!!");
			}
		} else {
			if (shootFireJumping==SHOOT_FIRE_JUMPING.STAGE1) {
				setImageAndRelocate(bigMarioLeftJumpingFireShooting1Image);
			} else if (shootFireJumping==SHOOT_FIRE_JUMPING.STAGE2) {
				setImageAndRelocate(bigMarioLeftJumpingFireShooting2Image);
			} else if (shootFireJumping==SHOOT_FIRE_JUMPING.STAGE3) {
				setImageAndRelocate(bigMarioLeftJumpingFireShooting3Image);
			} else {
				System.out.println("SHOULD NEVER HAPPEN2!!!!!!!!!");
			}
		}
	}

	public void lookCorrectWaySwingingTail(boolean rightOrLeft) {
		//this func is called in move function to look in correct direction in
		//the middle of swinging tail in the air
		if (rightOrLeft) {
			if (swingTailJumping==SWING_TAIL_JUMPING.STAGE1) {
				if (isCat) setImageAndRelocate(bigMarioRightJumpingCatTail1Image);
				else setImageAndRelocate(tanookiMarioRightJumpingCatTail1Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE2) {
				if (isCat) setImageAndRelocate(bigMarioRightJumpingCatTail2Image);
				else setImageAndRelocate(tanookiMarioRightJumpingCatTail2Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE3) {
				if (isCat) setImageAndRelocate(bigMarioRightJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
				else setImageAndRelocate(tanookiMarioRightJumpingCatTail1Image);
			} else {
				System.out.println("SHOULD NEVER HAPPEN1!!!!!!!!!");
			}
		} else {
			if (swingTailJumping==SWING_TAIL_JUMPING.STAGE1) {
				if (isCat) setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);
				else setImageAndRelocate(tanookiMarioLeftJumpingCatTail1Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE2) {
				if (isCat) setImageAndRelocate(bigMarioLeftJumpingCatTail2Image);
				else setImageAndRelocate(tanookiMarioLeftJumpingCatTail2Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE3) {
				if (isCat) setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
				else setImageAndRelocate(tanookiMarioLeftJumpingCatTail1Image);
			} else {
				System.out.println("SHOULD NEVER HAPPEN2!!!!!!!!!");
			}
		}
	}


	public void shootFireBall() {
		if (!isFire || isCrouching || isShooting) return;//only fire mario can shoot fireballs, also mario cant shoot if crouching
		/*if (isJumping && shootFireJumping!=SHOOT_FIRE_JUMPING.NOT_SHOOTING) {
			//System.out.println("JUMPING AND SHOOTING");
			return;//return if already shooting
		}
		if (!isJumping && shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
			//System.out.println("STANDING AND SHOOTING");
			return;//return if already shooting
		}*/
		//if (isShooting) return;
		isShooting = true;
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (alive && isShooting && isFire) {
					if (isCrouching) {System.out.println("iiiiiiiiiiiiiiiiiiiii");//WITHOUT PRINTLN IT BUGS
					} else {
						System.out.println("<<<<<<<<<<<<<<<<,SHOOTING A FIREBALL: "+isShooting);
						ThreadSleep.sleep(pauseBetweenShots);
						//isShooting is set to false when shooting key is released
						//code in here runs in another thread
						boolean startedJumping = isJumping;
						int pauseBetweenStages = 10;
						SoundController.playFireballSound();
						double x = lookingRightOrLeft?getX()+getWidth()+moveDx*2:getX()-moveDx*6;
						double y = getY()+0.4*getHeight();//might have to change
						DynamicFactory.addFireBall(x, y, lookingRightOrLeft);
						//ENTERING STAGE1
						if (alive) {
							if (isJumping) {
								if (lookingRightOrLeft) {
									setImageAndRelocate(bigMarioRightJumpingFireShooting1Image);
								} else {
									setImageAndRelocate(bigMarioLeftJumpingFireShooting1Image);
								}
								shootFireJumping = SHOOT_FIRE_JUMPING.STAGE1;
							} else {
								if (lookingRightOrLeft) {
									setImageAndRelocate(bigMarioRightFireShooting1Image);
								} else {
									setImageAndRelocate(bigMarioLeftFireShooting1Image);
								}
								shootFireStanding = SHOOT_FIRE_STANDING.STAGE1;
							}
						}

						ThreadSleep.sleep(pauseBetweenStages);

						//ENTERING STAGE2 				
						if (isJumping != startedJumping || isCrouching) {
							//need to check if isJumping is different from startedJumping after every stage
							//if they are different then mario was jumping, started shooting a fireball but
							//landed before finishing shooting the fireball
							//need to stop shooting fireball (return from function, set stage to not shooting) if that is the case
							//if here than isJumping is false but startedJumping is true
							//because mario cant jump if he is currently shooting and standing, so startedJumping must be true
							shootFireJumping = SHOOT_FIRE_JUMPING.NOT_SHOOTING;
							shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
							//isShooting = false;
							//UNCOMMENTreturn;
							//break;
						} else {
							if (alive) {
								if (isJumping) {
									if (lookingRightOrLeft) {
										setImageAndRelocate(bigMarioRightJumpingFireShooting2Image);
									} else {
										setImageAndRelocate(bigMarioLeftJumpingFireShooting2Image);
									}
									shootFireJumping = SHOOT_FIRE_JUMPING.STAGE2;
								} else {
									if (lookingRightOrLeft) {
										setImageAndRelocate(bigMarioRightFireShooting2Image);
									} else {
										setImageAndRelocate(bigMarioLeftFireShooting2Image);
									}
									shootFireStanding = SHOOT_FIRE_STANDING.STAGE2;
								}
							}

							ThreadSleep.sleep(pauseBetweenStages);

							if (isJumping != startedJumping || isCrouching) {
								//need to check if isJumping is different from startedJumping after every stage
								//if they are different then mario was jumping, started shooting a fireball but
								//landed before finishing shooting the fireball
								//need to stop shooting fireball (return from function, set stage to not shooting) if that is the case
								//if here than isJumping is false but startedJumping is true
								//because mario cant jump if he is currently shooting and standing, so startedJumping must be true
								shootFireJumping = SHOOT_FIRE_JUMPING.NOT_SHOOTING;
								shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
								//isShooting = false;
								//UNCOMMENTreturn;
								//break;
							} else {
								if (!isJumping) {
									//if standing there is only 2 stages so now must go back to standing sprite
									//done shooting fireball
									lookInCorrectDirection(lookingRightOrLeft);
									shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
									//isShooting = false;
									//UNCOMMENTreturn;
									//break;
								} else {
									//ENTERING STAGE3 for jumping
									if (alive) {
										if (lookingRightOrLeft) {
											setImageAndRelocate(bigMarioRightJumpingFireShooting3Image);
										} else {
											setImageAndRelocate(bigMarioLeftJumpingFireShooting3Image);
										}
										shootFireJumping = SHOOT_FIRE_JUMPING.STAGE3;
									}

									ThreadSleep.sleep(pauseBetweenStages);

									if (alive) {
										if (isJumping) {
											//if mario is still jumping after thread pause above
											if (wayUpOrWayDown) {
												setToJumping(lookingRightOrLeft);
											} else {
												setToJumpingDown(lookingRightOrLeft);
											}
										}
									}
									shootFireJumping= SHOOT_FIRE_JUMPING.NOT_SHOOTING;
								}
							}
						}
					}
				}

				shootFireJumping = SHOOT_FIRE_JUMPING.NOT_SHOOTING;
				shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
				isShooting = false;
				System.out.println("\n\t\t\t>>>>>>>DONE SHOOTING FIREBALL(S)\n");
			}
		});  
		t1.setName(this.character.name()+ " shoot fireball");
		t1.start();
	}

	public void swingTail() { 
		//mario isCat or isTanooki (MyKeyListener only calls this function if mario is cat or tanooki)
		//if (!isCat && !isTanooki || isCrouching || isSwinging) return;//cant swing tail if crouching or not cat
		if (isCrouching || isSwinging) return;//cant swing tail if crouching or not cat
		/*if (isJumping && swingTailJumping!=SWING_TAIL_JUMPING.NOT_SWINGING) {
			//System.out.println("JUMPING AND SWINGING");
			return;//return if already swinging
		}
		if (!isJumping && swingTailStanding!=SWING_TAIL_STANDING.NOT_SWINGING) {
			//System.out.println("STANDING AND SWINGING");
			return;//return if already swinging
		}*/
		isSwinging = true;
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (alive && isSwinging && (isCat || isTanooki)) {
					if (isCrouching) {System.out.println("yyyyyyyyy");//WITHOUT PRINTLN IT BUGS
					} else {
						//isSwinging is set to false when user releases swinging tail key
						System.out.println("\tSWING ingTAILL");
						ThreadSleep.sleep(pauseBetweenSwings);
						int pauseBetweenStages = 7;
						SoundController.playTailSound();
						if (!isJumping) {
							double newX =  lookingRightOrLeft?getX()+getWidth()+2*moveDx:getX()-2*moveDx;
							GObject a = canvas.getElementAt(newX, getY()+getHeight()*0.7);
							if (a!=null && a instanceof BadGuy) {		
								//Cat mario just swung tail at bad guy
								//kill bad guy
								System.out.println("CAT MARIO JUST KILLED BADGUY WITH TAIL");
								//TODO COULD PLAY SOUND FOR KILLING WITH TAIL
								((BadGuy) a).kill();
							}								
						}
						//ENTERING STAGE1
						boolean startedJumping = isJumping;
						if (alive && !isCrouching) {
							if (isJumping) {
								if (wayUpOrWayDown) {
									//on the way up swinging the tail makes mario go up a bit more
									System.out.println("SHOULD MOVE HIGHER");
									move(0, -getHeight()/4);
								} else {
									//on the way down swinging the tail makes mario slow down in the air
									pauseGoingDown = 12;
									System.out.println("SHOULD MOVE DOWN SLOWER");
								}
								if (lookingRightOrLeft) {
									if (isCat) setImageAndRelocate(bigMarioRightJumpingCatTail1Image);
									else setImageAndRelocate(tanookiMarioRightJumpingCatTail1Image);//isTanooki
								} else {
									if (isCat) setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);
									else setImageAndRelocate(tanookiMarioLeftJumpingCatTail1Image);//isTanooki
								}
								swingTailJumping = SWING_TAIL_JUMPING.STAGE1;
							} else { 
								if (isCat) setImageAndRelocate(bigMarioCatTail1Image);
								else setImageAndRelocate(tanookiMarioCatTail1Image);//isTanooki
								swingTailStanding = SWING_TAIL_STANDING.STAGE1;
							}
						}

						ThreadSleep.sleep(pauseBetweenStages);

						if (isJumping != startedJumping || isCrouching) {
							//need to check if isJumping is different from startedJumping after every stage
							//if they are different then mario was jumping, started swinging his tail but
							//landed before finishing
							//need to stop swinging tail (return from function, set stage to not swinging) if that is the case
							//if here than isJumping is false but startedJumping is true
							//because mario cant jump if he is currently swinging and standing, so startedJumping must be true
							swingTailJumping =  SWING_TAIL_JUMPING.NOT_SWINGING;
							pauseGoingDown = pauseInAir;
							//UNCOMEMENTreturn;
						} else {
							//ENTERING STAGE2
							double dx = getWidth()/2;
							if (alive && !isCrouching) {
								if (isJumping) {
									if (lookingRightOrLeft) {
										if (isCat) setImageAndRelocate(bigMarioRightJumpingCatTail2Image);
										else setImageAndRelocate(tanookiMarioRightJumpingCatTail2Image);//isTanooki
									} else {
										if (isCat) setImageAndRelocate(bigMarioLeftJumpingCatTail2Image);
										else setImageAndRelocate(tanookiMarioLeftJumpingCatTail2Image);//isTanooki
									}
									swingTailJumping = SWING_TAIL_JUMPING.STAGE2;
								} else { 
									if (lookingRightOrLeft) {
										moveOnlyMario(dx, 0.0);
										if (isCat) setImageAndRelocate(bigMarioLeftCatTail2Image);
										else setImageAndRelocate(tanookiMarioLeftCatTail2Image);//isTanooki
									} else {
										moveOnlyMario(-dx, 0.0);
										if (isCat) setImageAndRelocate(bigMarioRightCatTail2Image);
										else setImageAndRelocate(tanookiMarioRightCatTail2Image);//isTanooki
									}
									swingTailStanding = SWING_TAIL_STANDING.STAGE2;
								}
							}

							ThreadSleep.sleep(pauseBetweenStages);

							if (isJumping != startedJumping || isCrouching) {
								//need to check if isJumping is different from startedJumping after every stage
								//if they are different then mario was jumping, started swinging his tail but
								//landed before finishing
								//need to stop swinging tail (return from function, set stage to not swinging) if that is the case
								//if here than isJumping is false but startedJumping is true
								//because mario cant jump if he is currently swinging and standing, so startedJumping must be true
								swingTailJumping =  SWING_TAIL_JUMPING.NOT_SWINGING;
								pauseGoingDown = pauseInAir;
								//RETURNreturn;
							} else {
								//ENTERING STAGE3
								if (alive && !isCrouching) {
									if (isJumping) {
										if (lookingRightOrLeft) {
											if (isCat) setImageAndRelocate(bigMarioRightJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
											else setImageAndRelocate(tanookiMarioRightJumpingCatTail1Image);//isTanooki
										} else {
											if (isCat) setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
											else setImageAndRelocate(tanookiMarioLeftJumpingCatTail1Image);//isTanooki
										}
										swingTailJumping = SWING_TAIL_JUMPING.STAGE3;
									} else { 
										if (lookingRightOrLeft) moveOnlyMario(-dx, 0);
										else moveOnlyMario(dx, 0);
										if (isCat) setImageAndRelocate(bigMarioCatTail3Image);
										else setImageAndRelocate(tanookiMarioCatTail3Image);//isTanooki
										swingTailStanding = SWING_TAIL_STANDING.STAGE3;
									}
								}

								ThreadSleep.sleep(pauseBetweenStages);

								if (alive && !isCrouching) {
									if (isJumping) {
										if (wayUpOrWayDown) {
											setToJumping(lookingRightOrLeft);
										} else {
											setToJumpingDown(lookingRightOrLeft);
										}
									} else {
										lookInCorrectDirection(lookingRightOrLeft);
									}
								}
								pauseGoingDown = pauseInAir;
								swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
								swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;	
							}
						}
					}
				}
				swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
				swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;
				isSwinging = false;
				System.out.println("\n\n\nDONE SWINGING TAI\n\nL");
			}
		});
		t1.setName(this.character.name()+ " swing tail");
		t1.start();
	}

	public void hop() {
		//TODO bug where mario walks off platform and falls on something to hop off but doenst actually jump
		//called when mario jumps on a badguy or other Mario or bouncy platform and has to jump up
		//TODO if mario needs to hopp off a trampoline-like platform this function could be called 
		hitPlatformVertical = true;//mario should treat red turtle (or anything it hops off) like platform at first, this will make him stop falling
		jumpAgain = true;//this will make mario jump again because of while loop in jump() function 
	}

	@Override
	public void move() {
		// this is for leaf, mushroom, etc not for mario
	}


}
