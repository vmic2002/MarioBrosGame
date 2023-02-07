import java.awt.Image;
import java.awt.event.ActionListener;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Mario extends GImage {
	private Image smallMarioLeftImage;
	private Image smallMarioRightImage;
	private Image smallMarioLeftWalkingImage;
	private Image smallMarioRightWalkingImage;



	private Image bigMarioImage;
	private GCanvas canvas;
	private boolean bigOrSmall = false;//true if mario is big
	private boolean isJumping = false;//need to keep track of if mario is jumping or not
	//if he is already jumping and if the user tries to make mario jump he should not

	public boolean movingRight = false;
	public boolean movingLeft = false;
	//need this bools to support concurrency for jumping and moving right/left at the same time
	//movingRight, movingLeft are true if right,left key are pressed down respectively


	//needed for switching from walking sprite to standing sprite while mario walks
	public boolean walkingRightOrLeft = false;//is true when mario is walking right or left (his leg should be out)
	//is false when mario is standing right or left

	public Mario(Image smallMarioLeftImage, Image smallMarioRightImage, Image smallMarioLeftWalkingImage,
			Image smallMarioRightWalkingImage, Image bigMarioImage, GCanvas canvas) {
		super(smallMarioRightImage);
		this.smallMarioRightImage = smallMarioRightImage;
		this.smallMarioLeftImage = smallMarioLeftImage;
		this.smallMarioLeftWalkingImage = smallMarioLeftWalkingImage;
		this.smallMarioRightWalkingImage = smallMarioRightWalkingImage;

		this.bigMarioImage = bigMarioImage;
		this.canvas = canvas;
	}

	public void makeBig() {
		double relativeY = this.getY()+ this.getHeight();
		//need line above because big mario and small mario dont have the same height
		//and so need to shift vertically Mario when going from small to big or vice versa
		double smallWidth = this.getWidth();//returns width of smallMario (needed for horizontal shift)
		this.setImage(bigMarioImage);
		//now this.getHeight returns height of big mario != height of small mario

		//X shift needed to keep big mario and small mario centered since their
		//widths can differ
		double xShift = (this.getWidth()-smallWidth)/2;
		this.setLocation(getX()-xShift, relativeY-this.getHeight());	
		bigOrSmall = true;
	}

	public void makeSmall() {
		double relativeY = this.getY()+ this.getHeight();
		//need line above because big mario and small mario dont have the same height
		//and so need to shift vertically Mario when going from small to big or vice versa
		double bigWidth = this.getWidth();//returns width of bigMario (needed for horizontal shift)
		this.setImage(smallMarioLeftImage);//TODO NOT CORRECT
		//now this.getHeight returns height of small mario != height of big mario

		//X shift needed to keep big mario and small mario centered since their
		//widths can differ
		double xShift = (bigWidth-this.getWidth())/2;
		this.setLocation(getX()+xShift, relativeY-this.getHeight());
		bigOrSmall = false;
	}

	public void jump() {
		//System.out.println("JUMP not implemented");
		//MORE COMPLICATED NEED TO DO THIS IN ANOTHER THREAD
		//so mario can jump and go left/right concurrently
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//code in here runs in another thread since mario can go right or left while jumping
				//has to be done concurrently
				/*
				 * TODO
				 * NEED TO CHECK WHETHER MARIO IS JUMPING ON A MUSHROOM OR TURTLE ETC
				 */
				if (isJumping) {
					return;
				}
				//look at comments in setToWalking func
				if (movingRight) {
					setToWalking(true);
				} else if (movingLeft) {
					setToWalking(false);
				}

				isJumping = true;
				for (int i=0; i<30; i++) {
					move(0, -10);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				try {
					Thread.sleep(70);//PAUSE AT TOP OF JUMP
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				for (int i=0; i<30; i++) {
					move(0, 10);
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				isJumping = false;

			}
		});  
		t1.start();
		//System.out.println("ENd of jump method");
	}

	public void setToWalking(boolean rightOrLeft) {
		//func called in <<jump method>> if user is holding down right or left key

		//if user holds down left or right key and jumps there is possibility
		//that mario is in standing sprite since toggling is done
		//this func to make mario be in walking sprite when jumping
		if (rightOrLeft) {
			if (bigOrSmall) {
				//this.setImage(bigMarioRightWalkingImage);
			} else {
				setImage(smallMarioRightWalkingImage);
			}
		} else {
			if (bigOrSmall) {
				//this.setImage(bigMarioLeftWalkingImage);
			} else {
				setImage(smallMarioLeftWalkingImage);
			}
		}
		walkingRightOrLeft = true;
	}

	public void setToStanding(boolean rightOrLeft) {
		//function called in key released when mario stops walking his sprite must be standing
		if (rightOrLeft) {
			if (bigOrSmall) {
				//this.setImage(bigMarioRightImage);
			} else {
				setImage(smallMarioRightImage);
			}
		} else {
			if (bigOrSmall) {
				//this.setImage(bigMarioLeftImage);
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
					//this.setImage(bigMarioRightImage);
				} else {
					setImage(smallMarioRightImage);
				}
				walkingRightOrLeft = false;
			} else {
				if (bigOrSmall) {
					//this.setImage(bigMarioRightWalkingImage);
				} else {
					setImage(smallMarioRightWalkingImage);
				}
				walkingRightOrLeft = true;
			}
		} else {
			if (walkingRightOrLeft) {
				if (bigOrSmall) {
					//this.setImage(bigMarioLeftImage);
				} else {
					setImage(smallMarioLeftImage);
				}
				walkingRightOrLeft = false;
			} else {
				if (bigOrSmall) {
					//this.setImage(bigMarioLeftWalkingImage);
				} else {
					setImage(smallMarioLeftWalkingImage);
				}
				walkingRightOrLeft = true;
			}
		}
	}

	public void lookInCorrectDirection(boolean rightOrLeft) {
		if (rightOrLeft) {
			if (bigOrSmall) {
				//setImage(bigMarioRightImage);
			} else {
				setImage(smallMarioRightImage);
			}
		} else {
			if (bigOrSmall) {
				//this.setImage(bigMarioLeftImage);
			} else {
				setImage(smallMarioLeftImage);
			}
		}
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
							//System.out.println(counter);
							counter++;

							moveHelper(rightOrLeft, false);
						}
					}
				} else {
					while (movingLeft) {
						//movingLeft is set to false when left key is released
						if (counter==x) {
							//System.out.println(counter);
							moveHelper(rightOrLeft, true);
							counter = 0;
						} else {
							//System.out.println(counter);
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
		double newX = 0;
		double dx = rightOrLeft?10:-10.0;//arbitrary dx to move mario not too much
		if (dx < 0) {
			newX = getX()+10;
		} else {
			newX = getX()+getWidth()-10;
		}
		//wait until mushroom image has slightly entered
		//small mario image to make mario big (since there is whitespace on both sides of mushroom/mario)
		GObject a = canvas.getElementAt(newX, getY()+getHeight()-10); 
		if (a!=null) {
			//a is mario since we are checking if mushroom image has entered mario image enough
			//to turn small mario into big mario
			//need to remove mario and check if mushroom is there
			canvas.remove(a);
			GObject b = canvas.getElementAt(newX, getY()+getHeight()-10);
			if (b instanceof Mushroom) {
				canvas.remove(b);
				makeBig();
			}
			canvas.add(a);

		}
		move(dx, 0);
		if (!isJumping && toggleWalking) {			
			//!isJumping means that mario is on the ground and maybe moving
			//in the move function this means that mario is on the ground and moving
			//so he is walking and needs to have his sprite toggle from walking to standing repeatedly
			System.out.println("HEREREERERE");
			toggleWalking(rightOrLeft);
		}

		//TODO NEED TO CHECK IF MARIO JUMPS INTO
		//MYSTERY BOX OR OUT OF BOUNDS
		//if mario touches mushroom he becomes big
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
