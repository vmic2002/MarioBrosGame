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
	 * 
	 * 
	 */
	
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 600;
	private static final long serialVersionUID = 1L;


	public void run() {
		System.out.println("Hello, World!");
		
		setSize(WIDTH,HEIGHT);
		String smallMarioImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioImage.png";
		String bigMarioImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioImage.png";
		String marioMushroomImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/marioMushroomImage.png";
		BufferedImage smallMarioImage = null;
		BufferedImage bigMarioImage = null;
		BufferedImage marioMushroomImage = null;
		try {
			smallMarioImage = ImageIO.read(new File(smallMarioImagePath));
			bigMarioImage = ImageIO.read(new File(bigMarioImagePath));
			marioMushroomImage = ImageIO.read(new File(marioMushroomImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Mario mario = new Mario(smallMarioImage, bigMarioImage, this.getGCanvas());
		
		add(mario, 10, getHeight()-mario.getHeight());//FOR NOW
		
		
		addKeyListeners(new MyKeyListener(mario));
		Mushroom mushroom = new Mushroom(marioMushroomImage, this.getGCanvas());
		
		add(mushroom, 600, getHeight()-mushroom.getHeight()+40);
		System.out.println(getHeight()+"    "+mario.getY());	
	}
}
