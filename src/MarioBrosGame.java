import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import acm.graphics.*;
import acm.program.GraphicsProgram;

public class MarioBrosGame extends GraphicsProgram {

	/*General comments for Mario Game:
	 * 
	 * to use new images for GImages take a screenshot
	 * and select size 320x320 in Preview
	 * 
	 * NEED TO CHANGE MUHSROOM PICTURE AND GET CORRECT BOUNDS
	 * small mario has width, height of (128, 160) in pixels
	 * big mario has width, height of (250 × 320) in pixels
	 * 
	 */
	
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 1000;
	private static final long serialVersionUID = 1L;


	public void run() {
		System.out.println("Hello, World!");
		
		setSize(WIDTH,HEIGHT);
		String smallMarioLeftImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftImage.png";
		String smallMarioRightImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightImage.png";
		String smallMarioRightWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightWalkingImage.png";
		String smallMarioLeftWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftWalkingImage.png";
		
		String bigMarioImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioImage.png";
		
		String marioMushroomImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/marioMushroomImage.png";
		
		BufferedImage smallMarioLeftImage = null;
		BufferedImage smallMarioRightImage = null;
		BufferedImage smallMarioRightWalkingImage = null;
		BufferedImage smallMarioLeftWalkingImage = null;
		
		
		BufferedImage bigMarioImage = null;
		
		BufferedImage marioMushroomImage = null;
		try {
			smallMarioLeftImage = ImageIO.read(new File(smallMarioLeftImagePath));
			smallMarioRightImage = ImageIO.read(new File(smallMarioRightImagePath));
			smallMarioLeftWalkingImage = ImageIO.read(new File(smallMarioLeftWalkingImagePath));
			smallMarioRightWalkingImage = ImageIO.read(new File(smallMarioRightWalkingImagePath));
			
			
			bigMarioImage = ImageIO.read(new File(bigMarioImagePath));
			marioMushroomImage = ImageIO.read(new File(marioMushroomImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mario mario = new Mario(smallMarioLeftImage,smallMarioRightImage, smallMarioLeftWalkingImage, smallMarioRightWalkingImage, bigMarioImage, this.getGCanvas());
		
		add(mario, 120, getHeight()-mario.getHeight());//FOR NOW
				
		addKeyListeners(new MyKeyListener(mario));
		Mushroom mushroom = new Mushroom(marioMushroomImage, this.getGCanvas());
		Mushroom mushroom2 = new Mushroom(marioMushroomImage, this.getGCanvas());
		Mushroom mushroom3 = new Mushroom(marioMushroomImage, this.getGCanvas());
		////add(mushroom, 600, getHeight()-mushroom.getHeight());
		//add(mushroom2, 800, getHeight()-mushroom.getHeight());
		//add(mushroom3, 0, getHeight()-mushroom.getHeight());
		System.out.println(getHeight()+"    "+mario.getY());	
	}
}
