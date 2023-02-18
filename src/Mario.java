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

	private Image bigMarioLeftFireImage;
	private Image bigMarioRightFireImage;
	private Image bigMarioLeftWalkingFireImage;
	private Image bigMarioRightWalkingFireImage;
	private Image bigMarioRightJumpingFireImage;
	private Image bigMarioLeftJumpingFireImage;

	private GCanvas canvas;
	private boolean bigOrSmall = false;//true if mario is big (still true if mario is in flower mode)
	public boolean isJumping = false;//need to keep track of if mario is jumping or not
	//if he is already jumping and if the user tries to make mario jump he should not

	public boolean isFire = false;//true if mario is in fire/flower mode
	//design decision: small mario eating fire flower turns directly into fire mario, not just big mario


	public boolean movingRight = false;
	public boolean movingLeft = false;
	//need this bools to support concurrency for jumping and moving right/left at the same time
	//movingRight, movingLeft are true if right,left key are pressed down respectively


	//needed for switching from walking sprite to standing sprite while mario walks
	public boolean walkingRightOrLeft = false;//is true when mario is walking right or left (his leg should be out)
	//is false when mario is standing right or left

	public boolean lookingRightOrLeft = true;//true when looking right and false when looking left

	public Mario(Image smallMarioLeftImage, Image smallMarioRightImage, Image smallMarioLeftWalkingImage,
			Image smallMarioRightWalkingImage,Image smallMarioLeftJumpingImage,
			Image smallMarioRightJumpingImage, Image bigMarioLeftImage,
			Image bigMarioRightImage, Image bigMarioLeftWalkingImage, Image bigMarioRightWalkingImage,
			Image bigMarioLeftJumpingImage, Image bigMarioRightJumpingImage,
			Image bigMarioLeftFireImage, Image bigMarioRightFireImage, 
			Image bigMarioLeftWalkingFireImage, Image bigMarioRightWalkingFireImage,
			Image bigMarioLeftJumpingFireImage, Image bigMarioRightJumpingFireImage,
			GCanvas canvas) {
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

		this.bigMarioLeftFireImage = bigMarioLeftFireImage;
		this.bigMarioRightFireImage = bigMarioRightFireImage;
		this.bigMarioLeftWalkingFireImage = bigMarioLeftWalkingFireImage;
		this.bigMarioRightWalkingFireImage = bigMarioRightWalkingFireImage;
		this.bigMarioLeftJumpingFireImage = bigMarioLeftJumpingFireImage;
		this.bigMarioRightJumpingFireImage = bigMarioRightJumpingFireImage;

		this.canvas = canvas;
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
		setImage(newImage);
		//X shift needed to keep big mario and small mario centered since their
		//widths can differ
		double xShift = (this.getWidth()-previousWidth)/2;
		this.setLocation(getX()-xShift, relativeY-this.getHeight());	
	}


	public void setToBig() {
		//can be called if mario eats mushroom to grow
		//or if fire mario gets hit by something and goes back 
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
		bigOrSmall = true;//if small mario takes flower he becomes flower mario (flower mario is also big)
		isFire = true;
		//walkingRightOrLeft = false;//maybe comment
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
				isJumping = true;

				setToJumping(lookingRightOrLeft);

				for (int i=0; i<45; i++) {
					/*
					 * TODO
					 * need to check above mario if he jumps into box or mushroom falls on him (fire flower would be impossible since they dont move)
					 */
					move(0, -dy);
					try {
						Thread.sleep(7);
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
				if (bigOrSmall) {
					//if mario is not small then he doesn't stay in jumping sprite on the way down
					//TODO need to add correct sprite for on the way down of a jump
					lookInCorrectDirection(lookingRightOrLeft);
				}
				
				for (int i=0; i<45; i++) {
					//check for 3 points under mario (left middle and right)
					Point[] arr = new Point[]{new Point(getX(),getY()+getHeight()+dy),
							new Point(getX()+getWidth()/2, getY()+getHeight()+dy),
							new Point(getX()+getWidth(), getY()+getHeight()+dy)};
					ArrayList<GObject> o = checkAtPositions(arr);
					System.out.println("JUMPING ON SOMETHING "+o.size());
					for (GObject x : o) {
						inContactWith(x);
					}
					move(0, dy);
					try {
						Thread.sleep(7);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				lookInCorrectDirection(lookingRightOrLeft);//sets back to standing sprite looking in correct direction
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
					setImage(bigMarioRightJumpingFireImage);
				} else {
					setImage(bigMarioRightJumpingImage);
				}
			} else {
				setImage(smallMarioRightJumpingImage);
			}
		} else {
			if (bigOrSmall) {
				if (isFire) {
					setImage(bigMarioLeftJumpingFireImage);
				} else {
					setImage(bigMarioLeftJumpingImage);
				}
			} else {
				setImage(smallMarioLeftJumpingImage);
			}
		}
	}

	public void setToStanding(boolean rightOrLeft) {
		//function called in key released when mario stops walking his sprite must be standing
		if (rightOrLeft) {
			if (bigOrSmall) {
				if (isFire) {
					setImage(bigMarioRightFireImage);
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
				} else {
					setImage(bigMarioLeftImage);
				}
			} else {
				setImage(smallMarioLeftImage);
			}
		}
		walkingRightOrLeft = false;
	}

	public void toggleWalking(boolean rightOrLeft) {
		if (rightOrLeft) {
			if (walkingRightOrLeft) {
				if (bigOrSmall) {
					if (isFire) {
						setImage(bigMarioRightFireImage);
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

	public void lookInCorrectDirection(boolean rightOrLeft) {
		//sets mario to standing position (not walking or sprinting or jumping)
		if (rightOrLeft) {
			if (bigOrSmall) {
				if (isFire) {
					setImage(bigMarioRightFireImage);
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
				} else {
					setImage(bigMarioLeftImage);
				}
			} else {
				setImage(smallMarioLeftImage);
			}
		}
		lookingRightOrLeft = rightOrLeft;
	}


	public void move(boolean rightOrLeft) {
		//rightOrLeft is true for moving right and false for moving left
		//this function is used to move mario right and left using the right and left arrows
		//runs on different thread so mario can jump and move right/left concurrently

		boolean b = false;
		if (rightOrLeft) {
			movingRight = true;
			b = true;
		} else {
			movingLeft = true;
		}
		lookInCorrectDirection(rightOrLeft);
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

		//arbitrary dx of 10 to move mario not too much
		double dx = rightOrLeft?10.0:-10.0;
		double newX = rightOrLeft?getX()+getWidth()+dx:getX()-dx;

		/*TODO
		 * mario only checks if he walks into an object
		 * if an object were to run into him, such as turtle or mushroom,
		 * it would be in that object's move function that handling of such collision
		 * would be done (mario doesn't check if something runs into him from behind for example)
		 */
		Point[] arr = new Point[]{new Point(newX, getY()+getHeight()-20)};
		ArrayList<GObject> o = checkAtPositions(arr);
		for (GObject x : o) {
			inContactWith(x);
		}
		move(dx, 0);
		if (!isJumping && toggleWalking) {			
			//!isJumping means that mario is on the ground and maybe moving
			//in the move function this means that mario is on the ground and moving
			//so he is walking and needs to have his sprite toggle from walking to standing repeatedly
			toggleWalking(rightOrLeft);
		} else if (isJumping && !bigOrSmall) {
			//!bigOrSmall because only small mario stays in jumping
			//sprite entire time he is in the air
			setToJumping(rightOrLeft);
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
			System.out.println("ITSA ME MARIO");
		} else if (o instanceof Mushroom) {
			System.out.println("ITS MUSHROOM");
			canvas.remove(o);
			if (!isFire) setToBig();//if mario is in flower mode, dont want mushroom to make him big
		} else if (o instanceof FireFlower) {
			canvas.remove(o);
			setToFire();

		}
	}

}
