import java.awt.Image;
import java.awt.event.ActionListener;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Mario extends GImage {
	private Image smallMarioImage;
	private Image bigMarioImage;
	private GCanvas canvas;
	private boolean bigOrSmall = false;//true if mario is big
	private boolean isJumping = false;//need to keep track of if mario is jumping or not
	//if he is already jumping and if the user tries to make mario jump he should not
	
	public boolean movingRight = false;
	public boolean movingLeft = false;
	//need this bools to support concurrency for jumping and moving right/left at the same time
	//movingRight, movingLeft are true if right,left key are pressed down respectively
	public Mario(Image smallMarioImage, Image bigMarioImage, GCanvas canvas) {
		super(smallMarioImage);
		this.smallMarioImage = smallMarioImage;
		this.bigMarioImage = bigMarioImage;
		this.canvas = canvas;
		// TODO Auto-generated constructor stub
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
		this.setImage(smallMarioImage);
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
		    	isJumping = true;
		    	for (int i=0; i<25; i++) {
		    		move(0, -10);
		    		try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	for (int i=0; i<25; i++) {
		    		move(0, 10);
		    		try {
						Thread.sleep(20);
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
		System.out.println("GOT HEREERE");
		final boolean c = b;
		Thread t1 = new Thread(new Runnable() {
		    @Override
		    public void run() {
		    	if (c) {
		    		while (movingRight) {
						moveHelper(rightOrLeft);
						//movingRight is set to false when right key is released
					}
		    	} else {
		    		while (movingLeft) {
						moveHelper(rightOrLeft);
						//movingLeft is set to false when left key is released
					}
		    	}
		    	
		    }
		});  
		t1.start();
		
		
		
	}
	public void moveHelper(boolean rightOrLeft) {
		//this function moves mario right or left once, is repeatedly called to move mario continuously
		double newX = 0;
		double dx = rightOrLeft?10:-10.0;//arbitrary dx to move mario not too much
		if (dx < 0) {
			if (bigOrSmall) {
				//need this because there is different amount of whitespace on
				//both sides of big/small mario images
				newX = getX()+80;
			} else {
				newX = getX()+45;
			}
			
		} else {
			if (bigOrSmall) {
				//need this because there is different amount of whitespace on
				//both sides of big/small mario images
				newX = getX()+getWidth()-70;
			} else {
				newX = getX()+getWidth()-60;
			}
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
		//System.out.println("MOVING MARIO");
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
