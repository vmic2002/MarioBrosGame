import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Mario extends GImage {
	private Image smallMarioLeftImage;
	private Image smallMarioRightImage;
	private Image smallMarioLeftWalkingImage;
	private Image smallMarioRightWalkingImage;
	private Image smallMarioLeftJumpingImage;
	private Image smallMarioRightJumpingImage;

	private Image bigMarioLeftImage;
	private Image bigMarioRightImage;
	private Image bigMarioLeftWalkingImage;
	private Image bigMarioRightWalkingImage;
	private Image bigMarioLeftJumpingImage;
	private Image bigMarioRightJumpingImage;
	private Image bigMarioLeftJumpingDownImage;
	private Image bigMarioRightJumpingDownImage;
	private Image bigMarioLeftCrouchingImage;
	private Image bigMarioRightCrouchingImage;

	private Image bigMarioLeftFireImage;
	private Image bigMarioRightFireImage;
	private Image bigMarioLeftWalkingFireImage;
	private Image bigMarioRightWalkingFireImage;
	private Image bigMarioRightJumpingFireImage;
	private Image bigMarioLeftJumpingFireImage;
	private Image bigMarioLeftJumpingDownFireImage;
	private Image bigMarioRightJumpingDownFireImage;
	private Image bigMarioLeftCrouchingFireImage;
	private Image bigMarioRightCrouchingFireImage;
	private Image bigMarioLeftFireShooting1Image;
	private Image bigMarioLeftFireShooting2Image;
	private Image bigMarioRightFireShooting1Image;
	private Image bigMarioRightFireShooting2Image;
	private Image bigMarioLeftJumpingFireShooting1Image;
	private Image bigMarioLeftJumpingFireShooting2Image;
	private Image bigMarioLeftJumpingFireShooting3Image;
	private Image bigMarioRightJumpingFireShooting1Image;
	private Image bigMarioRightJumpingFireShooting2Image;
	private Image bigMarioRightJumpingFireShooting3Image;


	private Image bigMarioLeftCatImage;
	private Image bigMarioRightCatImage;
	private Image bigMarioLeftWakingCatImage;
	private Image bigMarioRightWalkingCatImage;
	private Image bigMarioLeftJumpingCatImage;
	private Image bigMarioRightJumpingCatImage;
	private Image bigMarioRightJumpingDownCatImage;
	private Image bigMarioLeftJumpingDownCatImage;
	private Image bigMarioLeftCrouchingCatImage;
	private Image bigMarioRightCrouchingCatImage;
	private Image bigMarioLeftJumpingCatTail1Image;
	private Image bigMarioRightJumpingCatTail1Image;
	private Image bigMarioLeftJumpingCatTail2Image;
	private Image bigMarioRightJumpingCatTail2Image;
	private Image bigMarioCatTail1Image;
	private Image bigMarioLeftCatTail2Image;
	private Image bigMarioRightCatTail2Image;
	private Image bigMarioCatTail3Image;



	private GCanvas canvas;
	private SoundController sound;
	private FireBallFactory fireBallFactory;
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

	public Mario(Image smallMarioLeftImage, Image smallMarioRightImage, Image smallMarioLeftWalkingImage,
			Image smallMarioRightWalkingImage,Image smallMarioLeftJumpingImage,
			Image smallMarioRightJumpingImage, Image bigMarioLeftImage,
			Image bigMarioRightImage, Image bigMarioLeftWalkingImage, Image bigMarioRightWalkingImage,
			Image bigMarioLeftJumpingImage, Image bigMarioRightJumpingImage,
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

			GCanvas canvas, SoundController sound, FireBallFactory fireBallFactory) {
		super(smallMarioRightImage);
		this.smallMarioRightImage = smallMarioRightImage;
		this.smallMarioLeftImage = smallMarioLeftImage;
		this.smallMarioLeftWalkingImage = smallMarioLeftWalkingImage;
		this.smallMarioRightWalkingImage = smallMarioRightWalkingImage;
		this.smallMarioLeftJumpingImage = smallMarioLeftJumpingImage;
		this.smallMarioRightJumpingImage = smallMarioRightJumpingImage;

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

		this.canvas = canvas;
		this.sound = sound;
		this.fireBallFactory = fireBallFactory;
	}



	public void setImageAndRelocate(Image newImage) {
		//this function is called instead of setImage when mario changes from big to small
		//or small to big since they have different heights they need to be readjusted
		//when mario changes from walking to standing technically could also relocate because
		//those sprites have slightly different widths, but it is negligible
		double relativeY = this.getY()+ this.getHeight();
		//need line above because big mario and small mario dont have the same height
		//and so need to shift vertically Mario when going from small to big or vice versa
		double previousWidth = this.getWidth();//(needed for horizontal shift)
		super.setImage(newImage);
		//X shift needed to keep big mario and small mario centered since their
		//widths can differ
		double xShift = (this.getWidth()-previousWidth)/2;
		this.setLocation(getX()-xShift, relativeY-this.getHeight());	
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
		if (isCrouching) lookInDirectionCrouching(lookingRightOrLeft);
	}

	public void setToBig() {
		//can be called if mario eats mushroom to grow
		//or if fire mario or cat mario gets hit by something and goes back 
		//to big mario
		if (bigOrSmall && !isFire) return;
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
			//small mario cant crouch
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
	}

	public void stopCrouching() {
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

				int dy = 10;
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
				sound.playMarioJumpSound();

				wayUpOrWayDown = true;
				if (!isCrouching) {
					setToJumping(lookingRightOrLeft);
				}
				for (int i=0; i<45; i++) {
					// for 3 points over mario (left middle and right)
					Point[] arr = new Point[]{new Point(getX(),getY()-dy),
							new Point(getX()+getWidth()/2, getY()-dy),
							new Point(getX()+getWidth(), getY()-dy)};
					ArrayList<GObject> o = checkAtPositions(arr);
					for (GObject x : o) {
						inContactWith(x);
					}
					move(0, -dy);
					try {
						Thread.sleep(pauseInAir);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {
					Thread.sleep(80);//PAUSE AT TOP OF JUMP
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				wayUpOrWayDown = false;
				if (bigOrSmall && !isCrouching) {
					//if mario is not small then he doesn't stay in jumping sprite on the way down
					setToJumpingDown(lookingRightOrLeft);
				}


				while (getY()+getHeight()+dy<=canvas.getHeight()) {
					//TODO once levels will be made we dont want mario to go all the way down to the bottom of the screen
					//check for 3 points under mario (left middle and right)
					Point[] arr = new Point[]{new Point(getX(),getY()+getHeight()+dy),
							new Point(getX()+getWidth()/2, getY()+getHeight()+dy),
							new Point(getX()+getWidth(), getY()+getHeight()+dy)};
					ArrayList<GObject> o = checkAtPositions(arr);
					for (GObject x : o) {
						inContactWith(x);
					}
					move(0, dy);
					try {
						Thread.sleep(pauseGoingDown);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (!isCrouching) {
					lookInCorrectDirection(lookingRightOrLeft);//sets back to standing sprite looking in correct direction
				}
				isJumping = false;
			}
		});  
		t1.start();
	}



	public ArrayList<GObject> checkAtPositions(Point[] points) {
		//TODO takes list of positions to check at
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
		double dx = rightOrLeft?10.0:-10.0;
		double newX = rightOrLeft?getX()+getWidth()+dx:getX()-dx;

		/*TODO
		 * mario only checks if he walks into an object
		 * if an object were to run into him, such as turtle or mushroom,
		 * it would be in that object's move function that handling of such collision
		 * would be done (mario doesn't check if something runs into him from behind for example)
		 */
		//TODO instead of only checking one point to the right or left should check at least 3 points to the right or left
		Point[] arr = new Point[]{new Point(newX, getY()+getHeight()-20)};
		ArrayList<GObject> o = checkAtPositions(arr);
		for (GObject x : o) {
			inContactWith(x);
		}	
		move(dx, 0);

		if (!isJumping && toggleWalking) {			
			//in the move function this means that mario is on the ground and moving
			//so he is walking and needs to have his sprite toggle from walking to standing repeatedly
			toggleWalking(rightOrLeft);
		}
		//TODO NEED TO CHECK IF MARIO OUT OF BOUNDS
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void inContactWith(GObject o) {
		//input o is object mario came into contact with
		if (o instanceof Mario) {
			//System.out.println("ITSA ME MARIO");
		} else if (o instanceof Mushroom) {
			System.out.println("ITS MUSHROOM");
			canvas.remove(o);
			if (!isFire && !isCat) setToBig();//if mario is in flower mode or cat mode, dont want mushroom to make him big
			sound.playPowerUpSound();
		} else if (o instanceof FireFlower) {
			canvas.remove(o);
			setToFire();
			sound.playPowerUpSound();
		} else if (o instanceof Leaf) {
			System.out.println("HIT LEEAFFF");
			canvas.remove(o);
			setToCat();
			sound.playPowerUpSound();
		}
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
				//TODO bug where if jumping and shooting fireball, mario doesnt come all the way back down
				//i think it is because of the concurency -> changing image and relocating it in one thread
				//plus moving the image in another thread means there could maybe be a data race!!
				//this bug should be fixed once mario's jump function doesnt only use two for loops to go up and down the same amount
				//but a while loop so mario keeps on falling until he hits the ground. data race would no longer be a problem


				int pauseBetweenStages = 100;
				sound.playFireballSound();
				//TODO this function doesnt check if mario gets hit by turtle etc and
				//reverts to big mario or small mario in which case this function shouldreturn from function and set stage to not shooting
				double x = lookingRightOrLeft?getX()+getWidth():getX()-10;
				double y = getY()+0.4*getHeight();//might have to change
				fireBallFactory.addFireBall(x, y, lookingRightOrLeft);

				//ENTERING STAGE1
				boolean startedJumping = isJumping;
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
					return;
				}
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
				if (lookingRightOrLeft) {
					setImageAndRelocate(bigMarioRightJumpingFireShooting3Image);
				} else {
					setImageAndRelocate(bigMarioLeftJumpingFireShooting3Image);
				}
				shootFireJumping = SHOOT_FIRE_JUMPING.STAGE3;
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isJumping) {
					//if mario is still jumping after thread pause above
					if (wayUpOrWayDown) {
						setToJumping(lookingRightOrLeft);
					} else {
						setToJumpingDown(lookingRightOrLeft);
					}
				}
				shootFireJumping= SHOOT_FIRE_JUMPING.NOT_SHOOTING;

			}
		});  
		t1.start();
	}

	public void swingTail() {
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
				sound.playTailSound();
				//TODO need to check if turtle etc or mushroom etc gets hit by tail using inContactWith() func
				//ENTERING STAGE1
				boolean startedJumping = isJumping;
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
					System.out.println("SAVeD PROBLEM88888888888888888888888");
					return;
				}
				//ENTERING STAGE2
				double dx = getWidth()/2;
				if (isJumping) {
					if (lookingRightOrLeft) {
						setImageAndRelocate(bigMarioRightJumpingCatTail2Image);
					} else {
						setImageAndRelocate(bigMarioLeftJumpingCatTail2Image);
					}
					swingTailJumping = SWING_TAIL_JUMPING.STAGE2;
				} else { 
					if (lookingRightOrLeft) {
						move(dx, 0);
						setImageAndRelocate(bigMarioLeftCatTail2Image);
					} else {
						move(-dx, 0);
						setImageAndRelocate(bigMarioRightCatTail2Image);
					}
					swingTailStanding = SWING_TAIL_STANDING.STAGE2;
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
					System.out.println("SAVeD PROBLEM9999999999999999999");
					return;
				}
				//ENTERING STAGE3
				if (isJumping) {
					if (lookingRightOrLeft) {
						setImageAndRelocate(bigMarioRightJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
					} else {
						setImageAndRelocate(bigMarioLeftJumpingCatTail1Image);//stage3 for jumping uses same pic as stage 1
					}
					swingTailJumping = SWING_TAIL_JUMPING.STAGE3;
				} else { 
					if (lookingRightOrLeft) move(-dx, 0);
					else move(dx, 0);
					setImageAndRelocate(bigMarioCatTail3Image);
					swingTailStanding = SWING_TAIL_STANDING.STAGE3;
				}
				try {
					Thread.sleep(pauseBetweenStages);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (isJumping) {
					if (wayUpOrWayDown) {
						setToJumping(lookingRightOrLeft);
					} else {
						setToJumpingDown(lookingRightOrLeft);
					}
				} else {
					lookInCorrectDirection(lookingRightOrLeft);
				}
				pauseGoingDown = pauseInAir;
				swingTailJumping = SWING_TAIL_JUMPING.NOT_SWINGING;
				swingTailStanding = SWING_TAIL_STANDING.NOT_SWINGING;				
			}	
		});  
		t1.start();
	}

}
