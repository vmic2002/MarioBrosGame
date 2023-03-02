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
	 */

	private static final int WIDTH = 1400;
	private static final int HEIGHT = 1000;

	public void run() {
		System.out.println("Hello, World!");

		String smallMarioLeftImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftImage.png";
		String smallMarioRightImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightImage.png";
		String smallMarioRightWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightWalkingImage.png";
		String smallMarioLeftWalkingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftWalkingImage.png";
		String smallMarioLeftJumpingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioLeftJumpingImage.png";
		String smallMarioRightJumpingImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/smallMarioRightJumpingImage.png";
		String marioDeadImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/marioDeadImage.png";

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

		String bigMarioLeftCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftCatImage.png";
		String bigMarioRightCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightCatImage.png";
		String bigMarioLeftWakingCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftWalkingCatImage.png";
		String bigMarioRightWalkingCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightWalkingCatImage.png";
		String bigMarioLeftJumpingCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingCatImage.png";
		String bigMarioRightJumpingCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingCatImage.png";
		String bigMarioRightJumpingDownCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingDownCatImage.png";
		String bigMarioLeftJumpingDownCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingDownCatImage.png";
		String bigMarioLeftCrouchingCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftCrouchingCatImage.png";
		String bigMarioRightCrouchingCatImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightCrouchingCatImage.png";
		String bigMarioLeftJumpingCatTail1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingCatTail1Image.png";
		String bigMarioRightJumpingCatTal1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingCatTail1Image.png";
		String bigMarioLeftJumpingCatTail2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftJumpingCatTail2Image.png";
		String bigMarioRightJumpingCatTail2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightJumpingCatTail2Image.png";
		String bigMarioCatTail1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioTail1.png";
		String bigMarioLeftCatTail2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioLeftTail2.png";
		String bigMarioRightCatTail2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioRightTail2.png";
		String bigMarioCatTail3ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/bigMarioTail3.png";

		String mushroomImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mushroomImage.png";
		String fireFlowerImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/fireFlowerImage.png";
		String leftLeafImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/leftLeafImage.png";
		String rightLeafImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/rightLeafImage.png";

		String mysteryBox1ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mysteryBox1.png";
		String mysteryBox2ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mysteryBox2.png";
		String mysteryBox3ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mysteryBox3.png";
		String mysteryBox4ImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mysteryBox4.png";
		String mysteryBoxFinalImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/mysteryBoxFinal.png";

		String grassLeftTopImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/grassLeftTopImage.png";
		String grassRightTopImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/grassRightTopImage.png";
		String grassMidleTopImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/grassMiddleTopImage.png";
		String grassLeftImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/grassLeftImage.png";
		String grassRightImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/grassRightImage.png";
		String grassMiddleImagePath = "/Users/victormicha/eclipse-workspace/MarioBrosGame/Images/grassMiddleImage.png";

		BufferedImage smallMarioLeftImage = null;
		BufferedImage smallMarioRightImage = null;
		BufferedImage smallMarioRightWalkingImage = null;
		BufferedImage smallMarioLeftWalkingImage = null;
		BufferedImage smallMarioLeftJumpingImage = null;
		BufferedImage smallMarioRightJumpingImage = null;
		BufferedImage marioDeadImage = null;


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

		BufferedImage bigMarioLeftCatImage = null;
		BufferedImage bigMarioRightCatImage = null;
		BufferedImage bigMarioLeftWakingCatImage = null;
		BufferedImage bigMarioRightWalkingCatImage = null;
		BufferedImage bigMarioLeftJumpingCatImage = null;
		BufferedImage bigMarioRightJumpingCatImage = null;
		BufferedImage bigMarioRightJumpingDownCatImage = null;
		BufferedImage bigMarioLeftJumpingDownCatImage = null;
		BufferedImage bigMarioLeftCrouchingCatImage = null;
		BufferedImage bigMarioRightCrouchingCatImage = null;
		BufferedImage bigMarioLeftJumpingCatTail1Image = null;
		BufferedImage bigMarioRightJumpingCatTal1Image = null;
		BufferedImage bigMarioLeftJumpingCatTail2Image = null;
		BufferedImage bigMarioRightJumpingCatTal2Image = null;
		BufferedImage bigMarioCatTail1Image = null;
		BufferedImage bigMarioLeftCatTail2Image= null;
		BufferedImage bigMarioRightCatTail2Image= null;
		BufferedImage bigMarioCatTail3Image = null;



		BufferedImage mushroomImage = null;
		BufferedImage fireFlowerImage = null;
		BufferedImage leftLeafImage = null;
		BufferedImage rightLeafImage = null;


		BufferedImage mysteryBox1Image = null;
		BufferedImage mysteryBox2Image = null;
		BufferedImage mysteryBox3Image = null;
		BufferedImage mysteryBox4Image = null;
		BufferedImage mysteryBoxFinalImage = null;

		BufferedImage grassLeftTopImage = null;
		BufferedImage grassRightTopImage = null;
		BufferedImage grassMidleTopImage = null;
		BufferedImage grassLeftImage = null;
		BufferedImage grassRightImage = null;
		BufferedImage grassMiddleImage = null;

		try {
			smallMarioLeftImage = ImageIO.read(new File(smallMarioLeftImagePath));
			smallMarioRightImage = ImageIO.read(new File(smallMarioRightImagePath));
			smallMarioLeftWalkingImage = ImageIO.read(new File(smallMarioLeftWalkingImagePath));
			smallMarioRightWalkingImage = ImageIO.read(new File(smallMarioRightWalkingImagePath));
			smallMarioLeftJumpingImage = ImageIO.read(new File(smallMarioLeftJumpingImagePath));
			smallMarioRightJumpingImage = ImageIO.read(new File(smallMarioRightJumpingImagePath));
			marioDeadImage = ImageIO.read(new File(marioDeadImagePath));

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



			bigMarioLeftCatImage = ImageIO.read(new File(bigMarioLeftCatImagePath));
			bigMarioRightCatImage = ImageIO.read(new File(bigMarioRightCatImagePath));
			bigMarioLeftWakingCatImage = ImageIO.read(new File(bigMarioLeftWakingCatImagePath));
			bigMarioRightWalkingCatImage = ImageIO.read(new File(bigMarioRightWalkingCatImagePath));
			bigMarioLeftJumpingCatImage = ImageIO.read(new File(bigMarioLeftJumpingCatImagePath));
			bigMarioRightJumpingCatImage = ImageIO.read(new File(bigMarioRightJumpingCatImagePath));
			bigMarioRightJumpingDownCatImage = ImageIO.read(new File(bigMarioRightJumpingDownCatImagePath));
			bigMarioLeftJumpingDownCatImage = ImageIO.read(new File(bigMarioLeftJumpingDownCatImagePath));
			bigMarioLeftCrouchingCatImage = ImageIO.read(new File(bigMarioLeftCrouchingCatImagePath));
			bigMarioRightCrouchingCatImage = ImageIO.read(new File(bigMarioRightCrouchingCatImagePath));
			bigMarioLeftJumpingCatTail1Image = ImageIO.read(new File(bigMarioLeftJumpingCatTail1ImagePath));
			bigMarioRightJumpingCatTal1Image = ImageIO.read(new File(bigMarioRightJumpingCatTal1ImagePath));
			bigMarioLeftJumpingCatTail2Image = ImageIO.read(new File(bigMarioLeftJumpingCatTail2ImagePath));
			bigMarioRightJumpingCatTal2Image = ImageIO.read(new File(bigMarioRightJumpingCatTail2ImagePath));
			bigMarioCatTail1Image = ImageIO.read(new File(bigMarioCatTail1ImagePath));
			bigMarioLeftCatTail2Image = ImageIO.read(new File(bigMarioLeftCatTail2ImagePath)); 
			bigMarioRightCatTail2Image = ImageIO.read(new File(bigMarioRightCatTail2ImagePath));
			bigMarioCatTail3Image = ImageIO.read(new File(bigMarioCatTail3ImagePath));


			mushroomImage = ImageIO.read(new File(mushroomImagePath));
			fireFlowerImage = ImageIO.read(new File(fireFlowerImagePath));
			leftLeafImage = ImageIO.read(new File(leftLeafImagePath));;
			rightLeafImage = ImageIO.read(new File(rightLeafImagePath));

			mysteryBox1Image = ImageIO.read(new File(mysteryBox1ImagePath));
			mysteryBox2Image = ImageIO.read(new File(mysteryBox2ImagePath));
			mysteryBox3Image = ImageIO.read(new File(mysteryBox3ImagePath));
			mysteryBox4Image = ImageIO.read(new File(mysteryBox4ImagePath));
			mysteryBoxFinalImage = ImageIO.read(new File(mysteryBoxFinalImagePath));

			grassLeftTopImage = ImageIO.read(new File(grassLeftTopImagePath));
			grassRightTopImage = ImageIO.read(new File(grassRightTopImagePath));
			grassMidleTopImage = ImageIO.read(new File(grassMidleTopImagePath));
			grassLeftImage = ImageIO.read(new File(grassLeftImagePath));
			grassRightImage = ImageIO.read(new File(grassRightImagePath));
			grassMiddleImage = ImageIO.read(new File(grassMiddleImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		LevelController.setObjects(grassLeftTopImage,grassRightTopImage, grassMidleTopImage, grassLeftImage, 
				grassRightImage,grassMiddleImage, this.getGCanvas());
		MovingObject.setCanvas(this.getGCanvas());
		MysteryBox.setObjects(mysteryBox1Image, mysteryBox2Image, mysteryBox3Image, mysteryBox4Image, mysteryBoxFinalImage);
		Mushroom.setObjects(mushroomImage);
		FireBall.setObjects(leftFireBall1Image, rightFireBall1Image,leftFireBall2Image,
				rightFireBall2Image, leftFireBall3Image,
				rightFireBall3Image, leftFireBall4Image,
				rightFireBall4Image);
		FireFlower.setObjects(fireFlowerImage);
		Leaf.setObjects(rightLeafImage, leftLeafImage);
		Factory.setCanvas(this.getGCanvas());
		Mario mario = new Mario(smallMarioLeftImage,smallMarioRightImage,
				smallMarioLeftWalkingImage, smallMarioRightWalkingImage, smallMarioLeftJumpingImage, 
				smallMarioRightJumpingImage, marioDeadImage,

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

				bigMarioLeftCatImage, bigMarioRightCatImage, bigMarioLeftWakingCatImage, bigMarioRightWalkingCatImage,
				bigMarioLeftJumpingCatImage, bigMarioRightJumpingCatImage, bigMarioRightJumpingDownCatImage,
				bigMarioLeftJumpingDownCatImage, bigMarioLeftCrouchingCatImage, bigMarioRightCrouchingCatImage,
				bigMarioLeftJumpingCatTail1Image, bigMarioRightJumpingCatTal1Image, bigMarioLeftJumpingCatTail2Image,
				bigMarioRightJumpingCatTal2Image,
				bigMarioCatTail1Image, bigMarioLeftCatTail2Image,
				bigMarioRightCatTail2Image, bigMarioCatTail3Image);
		setSize(WIDTH,HEIGHT);
		addKeyListeners(new MyKeyListener(mario));
		LevelController.playLevel1(mario);
		//LevelController.playLevel2(mario);
	}


}
