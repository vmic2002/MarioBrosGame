import java.awt.image.BufferedImage;
import java.awt.Image;
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
	 * 
	 * small mario has height 160 pixels
	 * big mario has height 320 pixels
	 * 
	 * 
	 * 
	 * maybe a Level object needs an additional attribute: a background that moves along when
	 * mario walks
	 */

	private static final int WIDTH = 1200;
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
		String bigMarioLeftJumpingDownImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingDownImage.png";
		String bigMarioRightJumpingDownImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingDownImage.png";
		String bigMarioLeftCrouchingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftCrouchingImage.png";
		String bigMarioRightCrouchingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightCrouchingImage.png";

		String bigMarioLeftFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftFireImage.png";
		String bigMarioRightFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightFireImage.png";
		String bigMarioLeftWakingFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftWalkingFireImage.png";
		String bigMarioRightWalkingFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightWalkingFireImage.png";
		String bigMarioLeftJumpingFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingFireImage.png";
		String bigMarioRightJumpingFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingFireImage.png";
		String bigMarioLeftJumpingDownFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingDownFireImage.png";
		String bigMarioRightJumpingDownFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingDownFireImage.png";
		String bigMarioLeftCrouchingFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftCrouchingFireImage.png";
		String bigMarioRightCrouchingFireImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightCrouchingFireImage.png";
		String bigMarioLeftFireShooting1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftFireShooting1Image.png";
		String bigMarioLeftFireShooting2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftFireShooting2Image.png";
		String bigMarioRightFireShooting1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightFireShooting1Image.png";
		String bigMarioRightFireShooting2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightFireShooting2Image.png";
		String bigMarioLeftJumpingFireShooting1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingFireShooting1Image.png";
		String bigMarioLeftJumpingFireShooting2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingFireShooting2Image.png";
		String bigMarioLeftJumpingFireShooting3ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingFireShooting3Image.png";
		String bigMarioRightJumpingFireShooting1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingFireShooting1Image.png";
		String bigMarioRightJumpingFireShooting2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingFireShooting2Image.png";
		String bigMarioRightJumpingFireShooting3ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingFireShooting3Image.png";
		String leftFireBall1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/leftFireBall1.png";
		String rightFireBall1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/rightFireBall1.png";
		String leftFireBall2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/leftFireBall2.png";
		String rightFireBall2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/rightFireBall2.png";
		String leftFireBall3ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/leftFireBall3.png";
		String rightFireBall3ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/rightFireBall3.png";
		String leftFireBall4ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/leftFireBall4.png";
		String rightFireBall4ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/rightFireBall4.png";

		String mushroomImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mushroomImage.png";
		String fireFlowerImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/fireFlowerImage.png";

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
		BufferedImage bigMarioLeftJumpingDownImage = null;
		BufferedImage bigMarioRightJumpingDownImage = null;
		BufferedImage bigMarioLeftCrouchingImage = null;
		BufferedImage bigMarioRightCrouchingImage = null;

		BufferedImage bigMarioLeftFireImage = null;
		BufferedImage bigMarioRightFireImage = null;
		BufferedImage bigMarioRightWalkingFireImage = null;
		BufferedImage bigMarioLeftWalkingFireImage = null;
		BufferedImage bigMarioRightJumpingFireImage = null;
		BufferedImage bigMarioLeftJumpingFireImage = null;
		BufferedImage bigMarioLeftJumpingDownFireImage = null;
		BufferedImage bigMarioRightJumpingDownFireImage = null;
		BufferedImage bigMarioLeftCrouchingFireImage = null;
		BufferedImage bigMarioRightCrouchingFireImage = null;
		BufferedImage bigMarioLeftFireShooting1Image = null;
		BufferedImage bigMarioLeftFireShooting2Image = null;
		BufferedImage bigMarioRightFireShooting1Image = null;
		BufferedImage bigMarioRightFireShooting2Image  = null;
		BufferedImage bigMarioLeftJumpingFireShooting1Image = null;
		BufferedImage bigMarioLeftJumpingFireShooting2Image = null;
		BufferedImage bigMarioLeftJumpingFireShooting3Image = null;
		BufferedImage bigMarioRightJumpingFireShooting1Image = null;
		BufferedImage bigMarioRightJumpingFireShooting2Image = null;
		BufferedImage bigMarioRightJumpingFireShooting3Image = null;
		BufferedImage leftFireBall1Image = null;
		BufferedImage rightFireBall1Image = null;
		BufferedImage leftFireBall2Image = null;
		BufferedImage rightFireBall2Image = null;
		BufferedImage leftFireBall3Image = null;
		BufferedImage rightFireBall3Image = null;
		BufferedImage leftFireBall4Image = null;
		BufferedImage rightFireBall4Image = null;

		BufferedImage mushroomImage = null;
		BufferedImage fireFlowerImage = null;
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
			bigMarioLeftJumpingDownImage = ImageIO.read(new File(bigMarioLeftJumpingDownImagePath));
			bigMarioRightJumpingDownImage = ImageIO.read(new File(bigMarioRightJumpingDownImagePath));
			bigMarioLeftCrouchingImage = ImageIO.read(new File(bigMarioLeftCrouchingImagePath));
			bigMarioRightCrouchingImage = ImageIO.read(new File(bigMarioRightCrouchingImagePath));

			bigMarioLeftFireImage = ImageIO.read(new File(bigMarioLeftFireImagePath));
			bigMarioRightFireImage = ImageIO.read(new File(bigMarioRightFireImagePath));
			bigMarioLeftWalkingFireImage = ImageIO.read(new File(bigMarioLeftWakingFireImagePath));
			bigMarioRightWalkingFireImage = ImageIO.read(new File(bigMarioRightWalkingFireImagePath));
			bigMarioLeftJumpingFireImage = ImageIO.read(new File(bigMarioLeftJumpingFireImagePath));
			bigMarioRightJumpingFireImage = ImageIO.read(new File(bigMarioRightJumpingFireImagePath));
			bigMarioLeftJumpingDownFireImage = ImageIO.read(new File(bigMarioLeftJumpingDownFireImagePath));
			bigMarioRightJumpingDownFireImage = ImageIO.read(new File(bigMarioRightJumpingDownFireImagePath));
			bigMarioLeftCrouchingFireImage = ImageIO.read(new File(bigMarioLeftCrouchingFireImagePath));
			bigMarioRightCrouchingFireImage= ImageIO.read(new File(bigMarioRightCrouchingFireImagePath));
			bigMarioLeftFireShooting1Image = ImageIO.read(new File(bigMarioLeftFireShooting1ImagePath));
			bigMarioLeftFireShooting2Image = ImageIO.read(new File(bigMarioLeftFireShooting2ImagePath));
			bigMarioRightFireShooting1Image = ImageIO.read(new File(bigMarioRightFireShooting1ImagePath));
			bigMarioRightFireShooting2Image  = ImageIO.read(new File(bigMarioRightFireShooting2ImagePath));
			bigMarioLeftJumpingFireShooting1Image = ImageIO.read(new File(bigMarioLeftJumpingFireShooting1ImagePath));
			bigMarioLeftJumpingFireShooting2Image = ImageIO.read(new File(bigMarioLeftJumpingFireShooting2ImagePath));
			bigMarioLeftJumpingFireShooting3Image = ImageIO.read(new File(bigMarioLeftJumpingFireShooting3ImagePath));
			bigMarioRightJumpingFireShooting1Image = ImageIO.read(new File(bigMarioRightJumpingFireShooting1ImagePath));
			bigMarioRightJumpingFireShooting2Image = ImageIO.read(new File(bigMarioRightJumpingFireShooting2ImagePath));
			bigMarioRightJumpingFireShooting3Image = ImageIO.read(new File(bigMarioRightJumpingFireShooting3ImagePath));;
			leftFireBall1Image = ImageIO.read(new File(leftFireBall1ImagePath));
			rightFireBall1Image = ImageIO.read(new File(rightFireBall1ImagePath));
			leftFireBall2Image = ImageIO.read(new File(leftFireBall2ImagePath));
			rightFireBall2Image = ImageIO.read(new File(rightFireBall2ImagePath));
			leftFireBall3Image = ImageIO.read(new File(leftFireBall3ImagePath));
			rightFireBall3Image = ImageIO.read(new File(rightFireBall3ImagePath));
			leftFireBall4Image = ImageIO.read(new File(leftFireBall4ImagePath));
			rightFireBall4Image = ImageIO.read(new File(rightFireBall4ImagePath));


					mushroomImage = ImageIO.read(new File(mushroomImagePath));
			fireFlowerImage = ImageIO.read(new File(fireFlowerImagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SoundController sound = new SoundController();
		FireBall.setObjects(this.getGCanvas(), leftFireBall1Image, rightFireBall1Image,leftFireBall2Image,
				 rightFireBall2Image, leftFireBall3Image,
				 rightFireBall3Image, leftFireBall4Image,
				 rightFireBall4Image);
		FireBallFactory fireBallFactory = new FireBallFactory(leftFireBall1Image, rightFireBall1Image, this.getGCanvas());
		Mario mario = new Mario(smallMarioLeftImage,smallMarioRightImage,
				smallMarioLeftWalkingImage, smallMarioRightWalkingImage, smallMarioLeftJumpingImage, 
				smallMarioRightJumpingImage, 
				bigMarioLeftImage, bigMarioRightImage,
				bigMarioLeftWalkingImage, bigMarioRightWalkingImage, bigMarioLeftJumpingImage,
				bigMarioRightJumpingImage, bigMarioLeftFireImage, bigMarioRightFireImage, bigMarioLeftWalkingFireImage
				, bigMarioRightWalkingFireImage,bigMarioLeftJumpingFireImage, bigMarioRightJumpingFireImage,
				bigMarioLeftJumpingDownImage, bigMarioRightJumpingDownImage,
				bigMarioLeftJumpingDownFireImage, bigMarioRightJumpingDownFireImage,
				bigMarioLeftCrouchingImage, bigMarioRightCrouchingImage,
				bigMarioLeftCrouchingFireImage, bigMarioRightCrouchingFireImage,
				bigMarioLeftFireShooting1Image, bigMarioLeftFireShooting2Image,
				bigMarioRightFireShooting1Image, bigMarioRightFireShooting2Image,
				bigMarioLeftJumpingFireShooting1Image, bigMarioLeftJumpingFireShooting2Image,
				bigMarioLeftJumpingFireShooting3Image, bigMarioRightJumpingFireShooting1Image,
				bigMarioRightJumpingFireShooting2Image, bigMarioRightJumpingFireShooting3Image,
				this.getGCanvas(), sound, fireBallFactory);

		add(mario, getWidth(), getHeight()-mario.getHeight());//FOR NOW

		addKeyListeners(new MyKeyListener(mario));
		Mushroom mushroom = new Mushroom(mushroomImage, this.getGCanvas());
		Mushroom mushroom2 = new Mushroom(mushroomImage, this.getGCanvas());
		Mushroom mushroom3 = new Mushroom(mushroomImage, this.getGCanvas());
		FireFlower flower = new FireFlower(fireFlowerImage);
		add(mushroom, 600, getHeight()-mushroom.getHeight());
		add(mushroom2, 800, getHeight()-mushroom.getHeight());
		add(mushroom3, 200, getHeight()-mushroom.getHeight());
		add(flower, 400, getHeight()-flower.getHeight());



		/*while (true) {
			add(mushroom, 600, getHeight()-mushroom.getHeight());
			add(mushroom2, 800, getHeight()-mushroom.getHeight());
			add(mushroom3, 0, getHeight()-mushroom.getHeight());
			add(flower, 400, getHeight()-flower.getHeight());
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
	}
}
