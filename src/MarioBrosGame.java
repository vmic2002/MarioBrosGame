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
	 * small mario has height 160 pixels
	 * big mario has height 320 pixels
	 * 
	 * maybe need to make set Image function for mairo that will do like the makeBig
	 * and makeSmall functions that recenter the images once they are changed 
	 * 
	 * 
	 * maybe a Level object needs an additional attribute: a background that moves along when
	 * mario walksq\
	 */
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 800;
	private static final long serialVersionUID = 1L;


	public void run() {
		System.out.println("Hello, World!");
		
		setSize(WIDTH,HEIGHT);
		String smallMarioLeftImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftImage.png";
		String smallMarioRightImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightImage.png";
		String smallMarioRightWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightWalkingImage.png";
		String smallMarioLeftWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftWalkingImage.png";
		String smallMarioLeftJumpingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftJumpingImage.png";
		String smallMarioRightJumpingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightJumpingImage.png";
		
		String bigMarioLeftImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftImage.png";
		String bigMarioRightImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightImage.png";
		String bigMarioRightWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightWalkingImage.png";
		String bigMarioLeftWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftWalkingImage.png";
		String bigMarioLeftJumpingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingImage.png";
		String bigMarioRightJumpingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingImage.png";
		
		String marioMushroomImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mushroomImage.png";
		
		BufferedImage smallMarioLeftImage = null;
		BufferedImage smallMarioRightImage = null;
		BufferedImage smallMarioRightWalkingImage = null;
		BufferedImage smallMarioLeftWalkingImage = null;
		BufferedImage smallMarioLeftJumpingImage = null;
		BufferedImage smallMarioRightJumpingImage = null;
		
		
		BufferedImage bigMarioLeftImage = null;
		BufferedImage bigMarioRightImage = null;
		BufferedImage bigMarioRightWalkingImage = null;
		BufferedImage bigMarioLeftWalkingImage = null;
		BufferedImage bigMarioLeftJumpingImage = null;
		BufferedImage bigMarioRightJumpingImage = null;
		
		
		BufferedImage bigMarioImage = null;
		
		BufferedImage marioMushroomImage = null;
		try {
			smallMarioLeftImage = ImageIO.read(new File(smallMarioLeftImagePath));
			smallMarioRightImage = ImageIO.read(new File(smallMarioRightImagePath));
			smallMarioLeftWalkingImage = ImageIO.read(new File(smallMarioLeftWalkingImagePath));
			smallMarioRightWalkingImage = ImageIO.read(new File(smallMarioRightWalkingImagePath));
			smallMarioLeftJumpingImage = ImageIO.read(new File(smallMarioLeftJumpingImagePath));
			smallMarioRightJumpingImage = ImageIO.read(new File(smallMarioRightJumpingImagePath));
			
			bigMarioLeftImage = ImageIO.read(new File(bigMarioLeftImagePath));
			bigMarioRightImage = ImageIO.read(new File(bigMarioRightImagePath));
			bigMarioLeftWalkingImage = ImageIO.read(new File(bigMarioLeftWalkingImagePath));
			bigMarioRightWalkingImage = ImageIO.read(new File(bigMarioRightWalkingImagePath));
			bigMarioLeftJumpingImage = ImageIO.read(new File(bigMarioLeftJumpingImagePath));
			bigMarioRightJumpingImage = ImageIO.read(new File(bigMarioRightJumpingImagePath));
			
			
			marioMushroomImage = ImageIO.read(new File(marioMushroomImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mario mario = new Mario(smallMarioLeftImage,smallMarioRightImage,
				smallMarioLeftWalkingImage, smallMarioRightWalkingImage, smallMarioLeftJumpingImage, 
				smallMarioRightJumpingImage, 
				bigMarioLeftImage, bigMarioRightImage,
				bigMarioLeftWalkingImage, bigMarioRightWalkingImage, bigMarioLeftJumpingImage,
				bigMarioRightJumpingImage,
				this.getGCanvas());
		
		add(mario, 120, getHeight()-mario.getHeight());//FOR NOW
				
		addKeyListeners(new MyKeyListener(mario));
		Mushroom mushroom = new Mushroom(marioMushroomImage, this.getGCanvas());
		Mushroom mushroom2 = new Mushroom(marioMushroomImage, this.getGCanvas());
		Mushroom mushroom3 = new Mushroom(marioMushroomImage, this.getGCanvas());
		add(mushroom, 600, getHeight()-mushroom.getHeight());
		add(mushroom2, 800, getHeight()-mushroom.getHeight());
		add(mushroom3, 0, getHeight()-mushroom.getHeight());
		System.out.println(getHeight()+"    "+mario.getY());	
	}
}
