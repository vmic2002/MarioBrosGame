import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Mario extends MovingObject {
	private Image smallMarioLeftImage, smallMarioRightImage, smallMarioLeftWalkingImage,
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
	smallMarioPipeImage, bigMarioPipeImage, fireMarioPipeImage;

	private boolean bigOrSmall = false;//true if mario is big (still true if mario is in flower mode or cat mode)
	public boolean isJumping = false;//need to keep track of if mario is jumping or not
	//if he is already jumping and if the user tries to make mario jump he should not
	public boolean wayUpOrWayDown =  false;//if isJumping is false wayUpOrDown's value is meaningless
	//if isJumping is true then if wayUpOrDown is true mario is on the way up
	//if wayUpOrDown is false then mario is on the way down of a jump

	public boolean isFire = false;//true if mario is in fire/flower mode
	public boolean isCat = false;//true if mario is in cat mode
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

	public enum SHOOT_FIRE_JUMPING {NOT_SHOOTING, STAGE1, STAGE2, STAGE3};
	public enum SHOOT_FIRE_STANDING {NOT_SHOOTING, STAGE1, STAGE2};
	SHOOT_FIRE_JUMPING shootFireJumping = SHOOT_FIRE_JUMPING .NOT_SHOOTING;
	SHOOT_FIRE_STANDING shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
	//enums above are used to determine what stage of shooting a fireball mario is in
	//if jumping, there are 4 stages, one for not shooting
	//if standing, there are 3, one for not shooting

	public enum SWING_TAIL_JUMPING {NOT_SWINGING, STAGE1, STAGE2, STAGE3};//stage 1 tail is parallel to ground, stage 2 tail is down, stage 3 back to parallel
	public enum SWING_TAIL_STANDING {NOT_SWINGING, STAGE1, STAGE2, STAGE3};//stage 1 is looking at user, stage2 right/left, stage3 back to the user
	SWING_TAIL_JUMPING swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
	SWING_TAIL_STANDING swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;
	private static final int pauseInAir = 7;//lower number means faster jump (less long-pauses in jump function)
	private int pauseGoingDown = pauseInAir;//this is changed while cat mario swings tail in the air so he is suspended in the air

	public boolean hitPlatformVertical = false;
	public boolean hitPlatformHorizontal = false;
	//need a hitPlatformVertical and hitPlatformHorizontal because if mario is falling leaning against a platform,
	//hitPlatformHorizontal will be true while hitPlatformVertical should be false so mario keeps on falling
	public boolean goingIntoPipe = false;
	public double fallDy;
	public double moveDx;
	public boolean flashing = false;//true when mario is hit by BadGuy and becomes false after mario stops flashing
	//while mario is flashing he cannot die from a bad guy, but he can still die by falling to bottom of screen
	public static int flashingTime = 3000;//total duration in ms that mario flashes when he comes into contact with BadGuy
	public static int numTimesToggleVisibility = 12;//number of times mario toggles his visibility to make it look like he is flashing (needs to be an even number)
	public static int flashingInterval = flashingTime/(numTimesToggleVisibility-1);


	public Mario(Image smallMarioLeftImage, Image smallMarioRightImage, Image smallMarioLeftWalkingImage,
			Image smallMarioRightWalkingImage,Image smallMarioLeftJumpingImage,
			Image smallMarioRightJumpingImage, Image marioDeadImage,

			Image bigMarioLeftImage, Image bigMarioRightImage, Image bigMarioLeftWalkingImage, 
			Image bigMarioRightWalkingImage, Image bigMarioLeftJumpingImage, Image bigMarioRightJumpingImage,

			Image bigMarioLeftFireImage, Image bigMarioRightFireImage, 
			Image bigMarioLeftWalkingFireImage, Image bigMarioRightWalkingFireImage,
			Image bigMarioLeftJumpingFireImage, Image bigMarioRightJumpingFireImage,
			Image bigMarioLeftJumpingDownImage, Image bigMarioRightJumpingDownImage,
			Image bigMarioLeftJumpingDownFireImage, Image bigMarioRightJumpingDownFireImage, 
			Image bigMarioLeftCrouchingImage, Image bigMarioRightCrouchingImage,
			Image bigMarioLeftCrouchingFireImage, Image bigMarioRightCrouchingFireImage,
			Image bigMarioLeftFireShooting1Image, Image bigMarioLeftFireShooting2Image,
			Image bigMarioRightFireShooting1Image, Image bigMarioRightFireShooting2Image,
			Image bigMarioLeftJumpingFireShooting1Image, Image bigMarioLeftJumpingFireShooting2Image,
			Image bigMarioLeftJumpingFireShooting3Image,Image bigMarioRightJumpingFireShooting1Image,
			Image bigMarioRightJumpingFireShooting2Image, Image bigMarioRightJumpingFireShooting3Image,

			Image bigMarioLeftCatImage, Image bigMarioRightCatImage, Image bigMarioLeftWakingCatImage, Image bigMarioRightWalkingCatImage,
			Image bigMarioLeftJumpingCatImage, Image bigMarioRightJumpingCatImage, Image bigMarioRightJumpingDownCatImage,
			Image bigMarioLeftJumpingDownCatImage, Image bigMarioLeftCrouchingCatImage, Image bigMarioRightCrouchingCatImage,
			Image bigMarioLeftJumpingCatTail1Image, Image bigMarioRightJumpingCatTail1Image, Image bigMarioLeftJumpingCatTail2Image,
			Image bigMarioRightJumpingCatTail2Image,
			Image bigMarioCatTail1Image, Image bigMarioLeftCatTail2Image,
			Image bigMarioRightCatTail2Image, Image bigMarioCatTail3Image,
			Image smallMarioPipeImage, Image bigMarioPipeImage, Image fireMarioPipeImage) {
		super(smallMarioRightImage);
		this.smallMarioRightImage = smallMarioRightImage;
		this.smallMarioLeftImage = smallMarioLeftImage;
		this.smallMarioLeftWalkingImage = smallMarioLeftWalkingImage;
		this.smallMarioRightWalkingImage = smallMarioRightWalkingImage;
		this.smallMarioLeftJumpingImage = smallMarioLeftJumpingImage;
		this.smallMarioRightJumpingImage = smallMarioRightJumpingImage;
		fallDy = smallMarioLeftImage.getHeight(canvas)/16.0;
		moveDx = 1.5*fallDy;
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

		this.smallMarioPipeImage = smallMarioPipeImage;
		this.bigMarioPipeImage = bigMarioPipeImage;
		this.fireMarioPipeImage = fireMarioPipeImage;
	}

	public void moveOnlyMario(double dx, double dy) {
		//moves only mario, not the level
		//used when mario swings his tail
		super.move(dx, dy);
	}

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
				LevelController.currLevel.moveLevel(-dx, -dy);
				//System.out.println("XXXXX");
				return;
			}
			super.move(dx, dy);
		} else {
			if (dx<0 && LevelController.currLevel.xBaseLine==0) {
				//mario can't move left if he is at the leftmost position in level
				if (getX()+dx>=0) super.move(dx, dy);
				return;
			}
			if (dx>0 && LevelController.currLevel.xBaseLine==canvas.getWidth()-LevelController.currLevel.width) {
				//if xbaseline == canvas width-level width then mario is at right most portion of level
				if (getX()+getWidth()+dx<=canvas.getWidth()) super.move(dx, dy);
				return;
			}
			if (dy > 0 && LevelController.currLevel.yBaseLine==0) {
				//mario touched bottom of screen 
				if (getY()+getHeight()<=canvas.getHeight()) {
					super.move(dx, dy);
					return;
				}
				System.out.println("MARIO DEAD he touched bottom of screen");
				marioDied();
				return;
			}
			LevelController.currLevel.moveLevel(-dx, -dy);
			//System.out.println("YYYYY");
		}
	}

	public void marioDied() {
		alive = false;
		movingRight = false;//in case user releases left/right keys right after mario dies
		movingLeft = false;
		SoundController.playMarioDeathSound();
		setImageAndRelocate(marioDeadImage);
		sendToFront();
		try {
			for (int i=0; i<60; i++) {
				super.move(0,-fallDy);
				Thread.sleep(15);
			}
			Thread.sleep(150);
			double maxTimeFallDown = 1500;
			while (maxTimeFallDown>0 && getY()+getHeight()+fallDy<=canvas.getHeight()) {
				super.move(0,fallDy);
				Thread.sleep(15);
				maxTimeFallDown -= 15;
			}
			//Thread.sleep(600);
		} catch (Exception e) {
			e.printStackTrace();
		}
		alive = true;
		goingIntoPipe = false;
		setToSmall();//need to change mario from dead sprite to small sprite
		lookingRightOrLeft = true;//mario should be looking right
		lookInCorrectDirection(true);
		LevelController.restartCurrentLevel();//when mario dies restart the level
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
		swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
		swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;
		if (isCrouching) lookInDirectionCrouching(lookingRightOrLeft);
	}

	public void setToBig() {
		//can be called if mario eats mushroom to grow
		//or if fire mario or cat mario gets hit by something and goes back 
		//to big mario
		if (bigOrSmall && !isFire && !isCat) return;
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
			} else { 
				setImageAndRelocate(bigMarioRightCrouchingImage);
			}
		} else {
			if (isFire) {
				setImageAndRelocate(bigMarioLeftCrouchingFireImage);
			} else if (isCat){
				setImageAndRelocate(bigMarioLeftCrouchingCatImage);
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
			return;
		}
		if (!isJumping && shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
			//STANDING AND SHOOTING
			//mario doesn't crouch if shooting
			return;
		}
		if (!isJumping) {
			//if mario is not jumping and crouches he must come to a stop
			if (movingRight) {
				//TODO animation for mario stopping by crouching down
				//could be cooler than just stopping at once
				movingRight = false;
			}
			if (movingLeft) {
				//TODO see todo above
				movingLeft = false;
			}
		}
		lookInDirectionCrouching(lookingRightOrLeft);
		isCrouching = true;
		checkCanGoDownIntoPipe();

	}

	public void checkCanGoDownIntoPipe() {
		//need to check if mario is on top of a PipePart
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
		} else { 
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
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//code in here runs in another thread since mario can go right or left while jumping
				//has to be done concurrently

				//int dy = 10;

				if (isJumping) {

					return;
				}
				if (shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
					return;//mario doesnt jump if he is in the middle of shooting while standing
				}
				if (swingTailStanding!=SWING_TAIL_STANDING.NOT_SWINGING) {
					return;//mario doesnt jump if he is in the middle of swinging while standing
				}
				isJumping = true;
				SoundController.playMarioJumpSound();

				wayUpOrWayDown = true;
				if (!isCrouching) {
					setToJumping(lookingRightOrLeft);
				}
				for (int i=0; i<60; i++) {
					// for 3 points over mario (left middle and right)
					Point[] arr = new Point[]{new Point(getX()+10,getY()-fallDy),
							new Point(getX()+getWidth()/2, getY()-fallDy),
							new Point(getX()+getWidth()-10, getY()-fallDy)};
					ArrayList<GObject> o = checkAtPositions(arr, canvas);
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
					try {
						Thread.sleep(pauseInAir);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(80);//PAUSE AT TOP OF JUMP
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
				if (!isCrouching) {
					lookInCorrectDirection(lookingRightOrLeft);//sets back to standing sprite looking in correct direction
				}
				isJumping = false;

			}
		});  
		t1.start();
	}

	public void fall(double dy) {
		while (!hitPlatformVertical && alive) {//mario falls down until he hits a platform
			checkUnder(dy);
			move(0, dy);
			if (hitPlatformVertical) {
				//if mario jumps onto a Platform, he needs to stop moving down
				hitPlatformVertical = false;
				break;
			}
			try {
				Thread.sleep(pauseGoingDown);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void checkUnder(double dy) {
		//checks if mario is in contact with something under him (mushroom, Platform,...)
		//check for 2 points under mario (left and right)
		//0.22 is value found to work best through testing
		Point[] arr = new Point[]{new Point(getX()+0.22*getWidth(),getY()+getHeight()+dy),
				new Point(getX()+getWidth()-0.22*getWidth(), getY()+getHeight()+dy)};
		ArrayList<GObject> o = checkAtPositions(arr, canvas);
		for (GObject x : o) {
			inContactWith(x, false);
		}
	}

	public void setToJumping(boolean rightOrLeft) {
		if (rightOrLeft) {
			if (bigOrSmall) {
				if (isFire) {
					setImageAndRelocate(bigMarioRightJumpingFireImage);
				} else if (isCat){
					setImageAndRelocate(bigMarioRightJumpingCatImage);
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
			} else {
				setImageAndRelocate(bigMarioRightJumpingDownImage);
			}
		} else {
			if (isFire) {
				setImageAndRelocate(bigMarioLeftJumpingDownFireImage);
			} else if (isCat){
				setImageAndRelocate(bigMarioLeftJumpingDownCatImage);
			} else {
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
					} else {
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
		t1.start();
	}

	public void moveHelper(boolean rightOrLeft, boolean toggleWalking) {
		//this function moves mario right or left once, is repeatedly called to move mario continuously
		if (!isJumping && isCrouching) {
			//this is if mario is moving right or left in 
			//the air while being crouched
			//once he lands (!isJumping), he should come to a stop;
			movingRight = false;
			movingLeft = false;
			return;
		}
		//arbitrary dx of 10 to move mario not too much
		double dx = rightOrLeft?moveDx:-moveDx;
		double newX = rightOrLeft?getX()+getWidth()+dx:getX()+dx;//+dx cause dx is already negative
		//TODO bug where fire mario can walk through Platform if he is rapidly shooting fireballs!
		/*TODO
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
		ArrayList<GObject> o = checkAtPositions(arr, canvas);
		for (GObject x : o) {
			inContactWith(x, true);
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
				t1.start();
			} else {
				//System.out.println("ON Platform OR at bottom of screen");
				hitPlatformVertical = false;
			}
		}
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void inContactWith(GObject o, boolean horizontolOrVertical) {
		//input o is object mario came into contact with
		if (o instanceof MovingObject) {
			if (!((MovingObject) o).alive) {
				//if o is from a previous level that has been cleared from canvas
				//but the (invisible) power ups still affect mario
				System.out.println("RAN INTO DEAD OBJECT");
				return;
			}
		}
		if (o instanceof Mario) {
			System.out.println("ITSA ME MARIO");
		} else if (o instanceof Mushroom) {
			System.out.println("ITS MUSHROOM");
			canvas.remove(o);
			if (!isFire && !isCat) setToBig();//if mario is in flower mode or cat mode, dont want mushroom to make him big
			SoundController.playPowerUpSound();
		} else if (o instanceof FireFlower) {
			canvas.remove(o);
			setToFire();
			SoundController.playPowerUpSound();
		} else if (o instanceof Leaf) {
			System.out.println("HIT LEEAFFF");
			canvas.remove(o);
			setToCat();
			SoundController.playPowerUpSound();
		} else if (o instanceof Platform) {
			//mario should halt, he cant move into a Platform
			//System.out.println("IN CONTAC WITH Platform");
			if (horizontolOrVertical) hitPlatformHorizontal = true;
			if (!horizontolOrVertical) hitPlatformVertical = true;
			//System.out.println("HIT PLatform");
			//System.out.println("MARIO BOUND: "+getX()+ " to "+(getX()+getWidth()));
			//System.out.println("Platform BOUND: "+o.getX()+ " to "+(o.getX()+o.getWidth()));

			//System.out.println("MARIO Y BOUND: "+getY()+ " to "+(getY()+getHeight()));
			//System.out.println("Platform Y BOUND: "+o.getY()+ " to "+(o.getY()+o.getHeight()));
			if (wayUpOrWayDown && !horizontolOrVertical) {
				//System.out.println("HIT Platform FROM UNDER!!!!!!");

				//if (getY()!=o.getY()+o.getHeight()) {
				//System.out.println("HIT Platform FROM SIDE"+ horizontolOrVertical);
				//} else {
				//	System.out.println("HIT Platform FROM UNDER");
				if (o instanceof MysteryBox) {
					System.out.println("MARIO HIT MYSTERYBOX FROM UNDER");
					if (Math.abs(getY()-o.getY()-o.getHeight())>20) return;
					if (!((MysteryBox) o).stateIsFinal()) {
						SoundController.playItemOutOfBoxSound();
						double x  = o.getX();
						double y = o.getY();						
						((MysteryBox) o).hitByMario();
						if (Math.random()>0.66)
							Factory.addFireFlower(x, y, o.getWidth());
						else if (Math.random()>0.33)
							Factory.addMushroom(x, y, o.getWidth());
						else
							Factory.addLeaf(x, y, o.getWidth());
					}
				} else if (o instanceof PipePart) {
					//mario jumped into a pipe part, need to make him go into pipe
					//mario cannot jumps into pipe while swinging tail/shooting fireball
					if (shootFireJumping!=SHOOT_FIRE_JUMPING.NOT_SHOOTING) {
						return;
					}
					if (swingTailJumping!=SWING_TAIL_JUMPING.NOT_SWINGING) {
						return;
					}
					if (isCrouching) {
						System.out.println("MARIO CANT JUMP INTO PIPE IF HE IS CROUCHING");
						return;
					}
					if (((PipePart) o).upOrDownPipe) {
						System.out.println("mario tries jumping into an up pipe instead of down pipe");
						return;
					}
					if (goingIntoPipe) return;

					Thread t1 = new Thread(new Runnable() {
						@Override
						public void run() {
							System.out.println("MARIO JUMP INTO PIPE "+((PipePart) o).subLevelID);
							goIntoPipe(true, (PipePart) o);
						}
					});  
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
			LevelController.currLevel.removeFireBall((FireBall) o);
			marioHit();
		} else if (o instanceof BadGuy) {
			//make mario smaller when he hits a BadGuy (turtle, flower etc)
			//also play sound
			//TODO fix bug when mario falls or jumps (vertically) on a badguy he doesnt change to dead mario sprite
			marioHit();
		}
		if (o instanceof PowerUp) {
			((PowerUp) o).alive = false;
		}
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
				if (isFire || isCat) setToBig();
				else if (bigOrSmall) setToSmall();
				flash();
			}
		});  
		t1.start();
	}

	public void flash() {
		//flashingInterval
		//flashingTime
		try {
			for (int i=0; i<numTimesToggleVisibility; i++) {
				if (!alive) {
					setVisible(true);
					flashing = false;
					return;//if mario gets hit (is flashing) and then falls to bottom of screen he shouldnt flash anymore
				}
				setVisible(!isVisible());
				Thread.sleep(flashingInterval);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		flashing = false;
		//TODO bug where mario stop flashing when he is on top of bad guy but he doesnt get hit again
	}

	public void goIntoPipe(boolean upOrDown, PipePart o) {
		if (!alive) return;
		goingIntoPipe=true;//to disable user from moving mario as he is going into pipe
		//setting movingRight/Left to false in case mario
		//is moving while going into a pipe
		movingRight = false;
		movingLeft = false;

		SoundController.playMarioGoesIntoPipeSound();
		setToPipe();

		double centerXPipe = o instanceof LeftPipePart?o.getX()+o.getWidth():o.getX(); 
		double marioNewX = centerXPipe-getWidth()/2; //to recenter mario so he goes into the center of the pipe

		double dx = getX()<marioNewX?moveDx:-moveDx;
		while (Math.abs(getX()-marioNewX)>20) {
			//to move mario to center of pipe
			moveOnlyMario(dx, 0);
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.setLocation(marioNewX, this.getY());
		try {
			double x = 15.0;
			double dy = getHeight()/x;
			if (upOrDown) dy = -dy;
			for (int i=0; i<x; i++) {
				Thread.sleep(40);
				moveOnlyMario(0, dy);
			}
		} catch(Exception e) {
			e.printStackTrace();
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
				setImageAndRelocate(bigMarioRightJumpingCatTail1Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE2) {
				setImageAndRelocate(bigMarioRightJumpingCatTail2Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE3) {
				setImageAndRelocate(bigMarioRightJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
			} else {
				System.out.println("SHOULD NEVER HAPPEN1!!!!!!!!!");
			}
		} else {
			if (swingTailJumping==SWING_TAIL_JUMPING.STAGE1) {
				setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE2) {
				setImageAndRelocate(bigMarioLeftJumpingCatTail2Image);
			} else if (swingTailJumping==SWING_TAIL_JUMPING.STAGE3) {
				setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
			} else {
				System.out.println("SHOULD NEVER HAPPEN2!!!!!!!!!");
			}
		}
	}

	public void shootFireBall() {
		if (!isFire || isCrouching) return;//only fire mario can shoot fireballs, also mario cant shoot if crouching
		if (isJumping && shootFireJumping!=SHOOT_FIRE_JUMPING.NOT_SHOOTING) {
			//System.out.println("JUMPING AND SHOOTING");
			return;//return if already shooting
		}
		if (!isJumping && shootFireStanding!=SHOOT_FIRE_STANDING.NOT_SHOOTING) {
			//System.out.println("STANDING AND SHOOTING");
			return;//return if already shooting
		}
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//code in here runs in another thread
				boolean startedJumping = isJumping;
				int pauseBetweenStages = 100;
				SoundController.playFireballSound();
				//TODO this function doesnt check if mario gets hit by turtle etc and
				//reverts to big mario or small mario in which case this function shouldreturn from function and set stage to not shooting
				double x = lookingRightOrLeft?getX()+getWidth()+moveDx*2:getX()-moveDx*6;
				double y = getY()+0.4*getHeight();//might have to change
				Factory.addFireBall(x, y, lookingRightOrLeft);
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
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//ENTERING STAGE2 				
				if (isJumping != startedJumping) {
					//need to check if isJumping is different from startedJumping after every stage
					//if they are different then mario was jumping, started shooting a fireball but
					//landed before finishing shooting the fireball
					//need to stop shooting fireball (return from function, set stage to not shooting) if that is the case
					//if here than isJumping is false but startedJumping is true
					//because mario cant jump if he is currently shooting and standing, so startedJumping must be true
					shootFireJumping = SHOOT_FIRE_JUMPING.NOT_SHOOTING;
					shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
					return;
				}
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
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isJumping != startedJumping) {
					//need to check if isJumping is different from startedJumping after every stage
					//if they are different then mario was jumping, started shooting a fireball but
					//landed before finishing shooting the fireball
					//need to stop shooting fireball (return from function, set stage to not shooting) if that is the case
					//if here than isJumping is false but startedJumping is true
					//because mario cant jump if he is currently shooting and standing, so startedJumping must be true
					shootFireJumping = SHOOT_FIRE_JUMPING.NOT_SHOOTING;
					shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
					return;
				}
				if (!isJumping) {
					//if standing there is only 2 stages so now must go back to standing sprite
					//done shooting fireball
					lookInCorrectDirection(lookingRightOrLeft);
					shootFireStanding = SHOOT_FIRE_STANDING.NOT_SHOOTING;
					return;
				}
				//ENTERING STAGE3 for jumping
				if (alive) {
					if (lookingRightOrLeft) {
						setImageAndRelocate(bigMarioRightJumpingFireShooting3Image);
					} else {
						setImageAndRelocate(bigMarioLeftJumpingFireShooting3Image);
					}
					shootFireJumping = SHOOT_FIRE_JUMPING.STAGE3;
				}
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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
		});  
		t1.start();
	}

	public void swingTail() {
		//TODO fix bug when spamming swingTail()
		if (!isCat || isCrouching) return;//cant swing tail if crouching or not cat
		if (!isJumping && (movingRight||movingLeft)) return;//cant swing if walking right or left
		if (isJumping && swingTailJumping!=SWING_TAIL_JUMPING.NOT_SWINGING) {
			//System.out.println("JUMPING AND SWINGING");
			return;//return if already swinging
		}
		if (!isJumping && swingTailStanding!=SWING_TAIL_STANDING.NOT_SWINGING) {
			//System.out.println("STANDING AND SWINGING");
			return;//return if already swinging
		}
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("SWING TAILL");
				int pauseBetweenStages = 70;
				SoundController.playTailSound();
				//TODO need to check if turtle etc or mushroom etc gets hit by tail using inContactWith() func
				//ENTERING STAGE1
				boolean startedJumping = isJumping;
				if (alive) {
					if (isJumping) {
						if (wayUpOrWayDown) {
							//on the way up swinging the tail makes mario go up a bit more
							System.out.println("SHOULD MOVE HIGHER");
							move(0, -getHeight()/4);
						} else {
							//on the way down swinging the tail makes mario slow down in the air
							pauseGoingDown = 120;
							System.out.println("SHOULD MOVE DOWN SLOWER");
						}
						if (lookingRightOrLeft) {
							setImageAndRelocate(bigMarioRightJumpingCatTail1Image);
						} else {
							setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);
						}
						swingTailJumping = SWING_TAIL_JUMPING.STAGE1;
					} else { 
						setImageAndRelocate(bigMarioCatTail1Image);
						swingTailStanding = SWING_TAIL_STANDING.STAGE1;
					}
				}
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isJumping != startedJumping) {
					//need to check if isJumping is different from startedJumping after every stage
					//if they are different then mario was jumping, started swinging his tail but
					//landed before finishing
					//need to stop swinging tail (return from function, set stage to not swinging) if that is the case
					//if here than isJumping is false but startedJumping is true
					//because mario cant jump if he is currently swinging and standing, so startedJumping must be true
					swingTailJumping =  SWING_TAIL_JUMPING.NOT_SWINGING;
					pauseGoingDown = pauseInAir;
					return;
				}
				//ENTERING STAGE2
				double dx = getWidth()/2;
				if (alive) {
					if (isJumping) {
						if (lookingRightOrLeft) {
							setImageAndRelocate(bigMarioRightJumpingCatTail2Image);
						} else {
							setImageAndRelocate(bigMarioLeftJumpingCatTail2Image);
						}
						swingTailJumping = SWING_TAIL_JUMPING.STAGE2;
					} else { 
						if (lookingRightOrLeft) {
							moveOnlyMario(dx, 0.0);
							setImageAndRelocate(bigMarioLeftCatTail2Image);
						} else {
							moveOnlyMario(-dx, 0.0);
							setImageAndRelocate(bigMarioRightCatTail2Image);
						}
						swingTailStanding = SWING_TAIL_STANDING.STAGE2;
					}
				}
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isJumping != startedJumping) {
					//need to check if isJumping is different from startedJumping after every stage
					//if they are different then mario was jumping, started swinging his tail but
					//landed before finishing
					//need to stop swinging tail (return from function, set stage to not swinging) if that is the case
					//if here than isJumping is false but startedJumping is true
					//because mario cant jump if he is currently swinging and standing, so startedJumping must be true
					swingTailJumping =  SWING_TAIL_JUMPING.NOT_SWINGING;
					pauseGoingDown = pauseInAir;
					return;
				}
				//ENTERING STAGE3
				if (alive) {
					if (isJumping) {
						if (lookingRightOrLeft) {
							setImageAndRelocate(bigMarioRightJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
						} else {
							setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
						}
						swingTailJumping = SWING_TAIL_JUMPING.STAGE3;
					} else { 
						if (lookingRightOrLeft) moveOnlyMario(-dx, 0);
						else moveOnlyMario(dx, 0);
						setImageAndRelocate(bigMarioCatTail3Image);
						swingTailStanding = SWING_TAIL_STANDING.STAGE3;
					}
				}
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (alive) {
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
		});  
		t1.start();
	}

	@Override
	public void move() {
		// this is for leaf, mushroom, etc not for mario
	}
}
