import java.awt.image.BufferedImage;
import java.awt.Color;
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
	 * TODO possible idea mario could have laser eyes (mario or firemario
	 * pictures edited to a different color.) mario would be able to move while shooting his laser eyes
	 * since the arrows will be needed to control where the laser beam goes
	 */

	private static final int WIDTH = 1200;
	private static final int HEIGHT = 1000;

	public void run() {
		float[] hsb = new float[3];
		Color.RGBtoHSB(0, 120, 255, hsb);
		Color c = new Color(hsb[0], hsb[1], hsb[2]);
		this.getGCanvas().setBackground(c);
		System.out.println("Hello, World!");
		//when running from command line:
		//image path is ../Images/*.png since program is run from bin directory
		//when running from command line, prefix = "../"
		//when running from eclipse, prefix = ""
		//before pushing to github make sure prefix = "../"
		String prefix = "../";
		//String prefix = "";

		String imageDirectory = "Images";
		//String imageDirectory = "ScaledImages";


		SoundController.setPrefix(prefix);

		String smallMarioLeftImagePath = prefix+imageDirectory+"/smallMarioLeftImage.png";
		String smallMarioRightImagePath = prefix+imageDirectory+"/smallMarioRightImage.png";
		String smallMarioRightWalkingImagePath = prefix+imageDirectory+"/smallMarioRightWalkingImage.png";
		String smallMarioLeftWalkingImagePath = prefix+imageDirectory+"/smallMarioLeftWalkingImage.png";
		String smallMarioLeftJumpingImagePath = prefix+imageDirectory+"/smallMarioLeftJumpingImage.png";
		String smallMarioRightJumpingImagePath = prefix+imageDirectory+"/smallMarioRightJumpingImage.png";
		String marioDeadImagePath = prefix+imageDirectory+"/marioDeadImage.png";

		String bigMarioLeftImagePath = prefix+imageDirectory+"/bigMarioLeftImage.png";
		String bigMarioRightImagePath = prefix+imageDirectory+"/bigMarioRightImage.png";
		String bigMarioRightWalkingImagePath = prefix+imageDirectory+"/bigMarioRightWalkingImage.png";
		String bigMarioLeftWalkingImagePath = prefix+imageDirectory+"/bigMarioLeftWalkingImage.png";
		String bigMarioLeftJumpingImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingImage.png";
		String bigMarioRightJumpingImagePath = prefix+imageDirectory+"/bigMarioRightJumpingImage.png";
		String bigMarioLeftJumpingDownImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingDownImage.png";
		String bigMarioRightJumpingDownImagePath = prefix+imageDirectory+"/bigMarioRightJumpingDownImage.png";
		String bigMarioLeftCrouchingImagePath = prefix+imageDirectory+"/bigMarioLeftCrouchingImage.png";
		String bigMarioRightCrouchingImagePath = prefix+imageDirectory+"/bigMarioRightCrouchingImage.png";

		String bigMarioLeftFireImagePath = prefix+imageDirectory+"/bigMarioLeftFireImage.png";
		String bigMarioRightFireImagePath = prefix+imageDirectory+"/bigMarioRightFireImage.png";
		String bigMarioLeftWakingFireImagePath = prefix+imageDirectory+"/bigMarioLeftWalkingFireImage.png";
		String bigMarioRightWalkingFireImagePath = prefix+imageDirectory+"/bigMarioRightWalkingFireImage.png";
		String bigMarioLeftJumpingFireImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingFireImage.png";
		String bigMarioRightJumpingFireImagePath = prefix+imageDirectory+"/bigMarioRightJumpingFireImage.png";
		String bigMarioLeftJumpingDownFireImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingDownFireImage.png";
		String bigMarioRightJumpingDownFireImagePath = prefix+imageDirectory+"/bigMarioRightJumpingDownFireImage.png";
		String bigMarioLeftCrouchingFireImagePath = prefix+imageDirectory+"/bigMarioLeftCrouchingFireImage.png";
		String bigMarioRightCrouchingFireImagePath = prefix+imageDirectory+"/bigMarioRightCrouchingFireImage.png";
		String bigMarioLeftFireShooting1ImagePath = prefix+imageDirectory+"/bigMarioLeftFireShooting1Image.png";
		String bigMarioLeftFireShooting2ImagePath = prefix+imageDirectory+"/bigMarioLeftFireShooting2Image.png";
		String bigMarioRightFireShooting1ImagePath = prefix+imageDirectory+"/bigMarioRightFireShooting1Image.png";
		String bigMarioRightFireShooting2ImagePath = prefix+imageDirectory+"/bigMarioRightFireShooting2Image.png";
		String bigMarioLeftJumpingFireShooting1ImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingFireShooting1Image.png";
		String bigMarioLeftJumpingFireShooting2ImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingFireShooting2Image.png";
		String bigMarioLeftJumpingFireShooting3ImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingFireShooting3Image.png";
		String bigMarioRightJumpingFireShooting1ImagePath = prefix+imageDirectory+"/bigMarioRightJumpingFireShooting1Image.png";
		String bigMarioRightJumpingFireShooting2ImagePath = prefix+imageDirectory+"/bigMarioRightJumpingFireShooting2Image.png";
		String bigMarioRightJumpingFireShooting3ImagePath = prefix+imageDirectory+"/bigMarioRightJumpingFireShooting3Image.png";
		String leftFireBall1ImagePath = prefix+imageDirectory+"/leftFireBall1.png";
		String rightFireBall1ImagePath = prefix+imageDirectory+"/rightFireBall1.png";
		String leftFireBall2ImagePath = prefix+imageDirectory+"/leftFireBall2.png";
		String rightFireBall2ImagePath = prefix+imageDirectory+"/rightFireBall2.png";
		String leftFireBall3ImagePath = prefix+imageDirectory+"/leftFireBall3.png";
		String rightFireBall3ImagePath = prefix+imageDirectory+"/rightFireBall3.png";
		String leftFireBall4ImagePath = prefix+imageDirectory+"/leftFireBall4.png";
		String rightFireBall4ImagePath = prefix+imageDirectory+"/rightFireBall4.png";

		String bigMarioLeftCatImagePath = prefix+imageDirectory+"/bigMarioLeftCatImage.png";
		String bigMarioRightCatImagePath = prefix+imageDirectory+"/bigMarioRightCatImage.png";
		String bigMarioLeftWakingCatImagePath = prefix+imageDirectory+"/bigMarioLeftWalkingCatImage.png";
		String bigMarioRightWalkingCatImagePath = prefix+imageDirectory+"/bigMarioRightWalkingCatImage.png";
		String bigMarioLeftJumpingCatImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingCatImage.png";
		String bigMarioRightJumpingCatImagePath = prefix+imageDirectory+"/bigMarioRightJumpingCatImage.png";
		String bigMarioRightJumpingDownCatImagePath = prefix+imageDirectory+"/bigMarioRightJumpingDownCatImage.png";
		String bigMarioLeftJumpingDownCatImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingDownCatImage.png";
		String bigMarioLeftCrouchingCatImagePath = prefix+imageDirectory+"/bigMarioLeftCrouchingCatImage.png";
		String bigMarioRightCrouchingCatImagePath = prefix+imageDirectory+"/bigMarioRightCrouchingCatImage.png";
		String bigMarioLeftJumpingCatTail1ImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingCatTail1Image.png";
		String bigMarioRightJumpingCatTal1ImagePath = prefix+imageDirectory+"/bigMarioRightJumpingCatTail1Image.png";
		String bigMarioLeftJumpingCatTail2ImagePath = prefix+imageDirectory+"/bigMarioLeftJumpingCatTail2Image.png";
		String bigMarioRightJumpingCatTail2ImagePath = prefix+imageDirectory+"/bigMarioRightJumpingCatTail2Image.png";
		String bigMarioCatTail1ImagePath = prefix+imageDirectory+"/bigMarioTail1.png";
		String bigMarioLeftCatTail2ImagePath = prefix+imageDirectory+"/bigMarioLeftTail2.png";
		String bigMarioRightCatTail2ImagePath = prefix+imageDirectory+"/bigMarioRightTail2.png";
		String bigMarioCatTail3ImagePath = prefix+imageDirectory+"/bigMarioTail3.png";

		String mushroomImagePath = prefix+imageDirectory+"/mushroomImage.png";
		String fireFlowerImagePath = prefix+imageDirectory+"/fireFlowerImage.png";
		String leftLeafImagePath = prefix+imageDirectory+"/leftLeafImage.png";
		String rightLeafImagePath = prefix+imageDirectory+"/rightLeafImage.png";

		String mysteryBox1ImagePath = prefix+imageDirectory+"/mysteryBox1.png";
		String mysteryBox2ImagePath = prefix+imageDirectory+"/mysteryBox2.png";
		String mysteryBox3ImagePath = prefix+imageDirectory+"/mysteryBox3.png";
		String mysteryBox4ImagePath = prefix+imageDirectory+"/mysteryBox4.png";
		String mysteryBoxFinalImagePath = prefix+imageDirectory+"/mysteryBoxFinal.png";

		String grassLeftTopImagePath = prefix+imageDirectory+"/grassLeftTopImage.png";
		String grassRightTopImagePath = prefix+imageDirectory+"/grassRightTopImage.png";
		String grassMidleTopImagePath = prefix+imageDirectory+"/grassMiddleTopImage.png";
		String grassLeftImagePath = prefix+imageDirectory+"/grassLeftImage.png";
		String grassRightImagePath = prefix+imageDirectory+"/grassRightImage.png";
		String grassMiddleImagePath = prefix+imageDirectory+"/grassMiddleImage.png";


		String pipeUpTopLeftImagePath = prefix+imageDirectory+"/pipeUpTopLeft.png";
		String pipeUpTopRightImagePath = prefix+imageDirectory+"/pipeUpTopRight.png";
		String pipeDownMiddleLeftImagePath = prefix+imageDirectory+"/pipeDownMiddleLeft.png";
		String pipeDownMiddleRightImagePath = prefix+imageDirectory+"/pipeDownMiddleRight.png";
		String pipeDownTopLeftImagePath = prefix+imageDirectory+"/pipeDownTopLeft.png";
		String pipeDownTopRightImagePath = prefix+imageDirectory+"/pipeDownTopRight.png";
		String pipeUpMiddleLeftImagePath = prefix+imageDirectory+"/pipeUpMiddleLeft.png";
		String pipeUpMiddleRightImagePath = prefix+imageDirectory+"/pipeUpMiddleRight.png";


		String smallMarioPipeImagePath = prefix+imageDirectory+"/smallMarioPipe.png";
		String bigMarioPipeImagePath = prefix+imageDirectory+"/bigMarioPipe.png";
		String fireMarioPipeImagePath = prefix+imageDirectory+"/fireMarioPipe.png";

		String shootingFlowerLeftDownClosedImagePath = prefix+imageDirectory+"/shootingFlowerLeftDownClosed.png";
		String shootingFlowerLeftDownOpenImagePath = prefix+imageDirectory+"/shootingFlowerLeftDownOpen.png";
		String shootingFlowerLeftUpClosedImagePath = prefix+imageDirectory+"/shootingFlowerLeftUpClosed.png";
		String shootingFlowerLeftUpOpenImagePath = prefix+imageDirectory+"/shootingFlowerLeftUpOpen.png";
		String shootingFlowerRightDownClosedImagePath = prefix+imageDirectory+"/shootingFlowerRightDownClosed.png";
		String shootingFlowerRightDownOpenImagePath = prefix+imageDirectory+"/shootingFlowerRightDownOpen.png";
		String shootingFlowerRightUpClosedImagePath = prefix+imageDirectory+"/shootingFlowerRightUpClosed.png";
		String shootingFlowerRightUpOpenImagePath = prefix+imageDirectory+"/shootingFlowerRightUpOpen.png";

		String downShootingFlowerLeftDownClosedImagePath = prefix+imageDirectory+"/DOWNshootingFlowerLeftDownClosed.png";
		String downShootingFlowerLeftDownOpenImagePath = prefix+imageDirectory+"/DOWNshootingFlowerLeftDownOpen.png";
		String downShootingFlowerLeftUpClosedImagePath = prefix+imageDirectory+"/DOWNshootingFlowerLeftUpClosed.png";
		String downShootingFlowerLeftUpOpenImagePath = prefix+imageDirectory+"/DOWNshootingFlowerLeftUpOpen.png";
		String downShootingFlowerRightDownClosedImagePath = prefix+imageDirectory+"/DOWNshootingFlowerRightDownClosed.png";
		String downShootingFlowerRightDownOpenImagePath = prefix+imageDirectory+"/DOWNshootingFlowerRightDownOpen.png";
		String downShootingFlowerRightUpClosedImagePath = prefix+imageDirectory+"/DOWNshootingFlowerRightUpClosed.png";
		String downShootingFlowerRightUpOpenImagePath = prefix+imageDirectory+"/DOWNshootingFlowerRightUpOpen.png";

		String redTurtleSpinning1ImagePath = prefix+imageDirectory+"/redTurtleSpinning1.png";
		String redTurtleSpinning2ImagePath = prefix+imageDirectory+"/redTurtleSpinning2.png";
		String redTurtleSpinning3ImagePath = prefix+imageDirectory+"/redTurtleSpinning3.png";
		String redTurtleSpinning4ImagePath = prefix+imageDirectory+"/redTurtleSpinning4.png";
		String redTurtleStandingLeftImagePath = prefix+imageDirectory+"/redTurtleStandingLeft.png";
		String redTurtleStandingRightImagePath = prefix+imageDirectory+"/redTurtleStandingRight.png";
		String redTurtleWalkingLeftImagePath = prefix+imageDirectory+"/redTurtleWalkingLeft.png";
		String redTurtleWalkingRightImagePath = prefix+imageDirectory+"/redTurtleWalkingRight.png";


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

		BufferedImage pipeUpTopLeftImage = null;
		BufferedImage pipeUpTopRightImage = null;
		BufferedImage pipeDownMiddleLeftImage = null;
		BufferedImage pipeDownMiddleRightImage = null;
		BufferedImage pipeDownTopLeftImage = null;
		BufferedImage pipeDownTopRightImage = null;
		BufferedImage pipeUpMiddleLeftImage = null;
		BufferedImage pipeUpMiddleRightImage = null;

		BufferedImage smallMarioPipeImage = null;
		BufferedImage bigMarioPipeImage = null;
		BufferedImage fireMarioPipeImage = null;

		BufferedImage shootingFlowerLeftDownClosedImage = null; 
		BufferedImage shootingFlowerLeftDownOpenImage = null;
		BufferedImage shootingFlowerLeftUpClosedImage = null;
		BufferedImage shootingFlowerLeftUpOpenImage = null;
		BufferedImage shootingFlowerRightDownClosedImage = null; 
		BufferedImage shootingFlowerRightDownOpenImage = null;
		BufferedImage shootingFlowerRightUpClosedImage = null;
		BufferedImage shootingFlowerRightUpOpenImage = null;

		BufferedImage downShootingFlowerLeftDownClosedImage = null; 
		BufferedImage downShootingFlowerLeftDownOpenImage = null;
		BufferedImage downShootingFlowerLeftUpClosedImage = null;
		BufferedImage downShootingFlowerLeftUpOpenImage = null;
		BufferedImage downShootingFlowerRightDownClosedImage = null; 
		BufferedImage downShootingFlowerRightDownOpenImage = null;
		BufferedImage downShootingFlowerRightUpClosedImage = null;
		BufferedImage downShootingFlowerRightUpOpenImage = null;

		BufferedImage redTurtleSpinning1Image = null;
		BufferedImage redTurtleSpinning2Image = null;
		BufferedImage redTurtleSpinning3Image = null;
		BufferedImage redTurtleSpinning4Image = null;
		BufferedImage redTurtleStandingLeftImage = null;
		BufferedImage redTurtleStandingRightImage = null;
		BufferedImage redTurtleWalkingLeftImage = null;
		BufferedImage redTurtleWalkingRightImage = null;



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

			pipeUpTopLeftImage = ImageIO.read(new File(pipeUpTopLeftImagePath));
			pipeUpTopRightImage = ImageIO.read(new File(pipeUpTopRightImagePath));
			pipeDownMiddleLeftImage = ImageIO.read(new File(pipeDownMiddleLeftImagePath));
			pipeDownMiddleRightImage = ImageIO.read(new File(pipeDownMiddleRightImagePath));
			pipeDownTopLeftImage = ImageIO.read(new File(pipeDownTopLeftImagePath));
			pipeDownTopRightImage = ImageIO.read(new File(pipeDownTopRightImagePath));
			pipeUpMiddleLeftImage = ImageIO.read(new File(pipeUpMiddleLeftImagePath));
			pipeUpMiddleRightImage = ImageIO.read(new File(pipeUpMiddleRightImagePath));

			smallMarioPipeImage = ImageIO.read(new File(smallMarioPipeImagePath));
			bigMarioPipeImage = ImageIO.read(new File(bigMarioPipeImagePath));
			fireMarioPipeImage = ImageIO.read(new File(fireMarioPipeImagePath));

			shootingFlowerLeftDownClosedImage = ImageIO.read(new File(shootingFlowerLeftDownClosedImagePath)); 
			shootingFlowerLeftDownOpenImage = ImageIO.read(new File(shootingFlowerLeftDownOpenImagePath));
			shootingFlowerLeftUpClosedImage = ImageIO.read(new File(shootingFlowerLeftUpClosedImagePath));
			shootingFlowerLeftUpOpenImage = ImageIO.read(new File(shootingFlowerLeftUpOpenImagePath));
			shootingFlowerRightDownClosedImage = ImageIO.read(new File(shootingFlowerRightDownClosedImagePath));
			shootingFlowerRightDownOpenImage = ImageIO.read(new File(shootingFlowerRightDownOpenImagePath));
			shootingFlowerRightUpClosedImage = ImageIO.read(new File(shootingFlowerRightUpClosedImagePath));
			shootingFlowerRightUpOpenImage = ImageIO.read(new File(shootingFlowerRightUpOpenImagePath));

			downShootingFlowerLeftDownClosedImage = ImageIO.read(new File(downShootingFlowerLeftDownClosedImagePath)); 
			downShootingFlowerLeftDownOpenImage = ImageIO.read(new File(downShootingFlowerLeftDownOpenImagePath));
			downShootingFlowerLeftUpClosedImage = ImageIO.read(new File(downShootingFlowerLeftUpClosedImagePath));
			downShootingFlowerLeftUpOpenImage = ImageIO.read(new File(downShootingFlowerLeftUpOpenImagePath));
			downShootingFlowerRightDownClosedImage = ImageIO.read(new File(downShootingFlowerRightDownClosedImagePath));
			downShootingFlowerRightDownOpenImage = ImageIO.read(new File(downShootingFlowerRightDownOpenImagePath));
			downShootingFlowerRightUpClosedImage = ImageIO.read(new File(downShootingFlowerRightUpClosedImagePath));
			downShootingFlowerRightUpOpenImage = ImageIO.read(new File(downShootingFlowerRightUpOpenImagePath));


			redTurtleSpinning1Image = ImageIO.read(new File(redTurtleSpinning1ImagePath));
			redTurtleSpinning2Image = ImageIO.read(new File(redTurtleSpinning2ImagePath));
			redTurtleSpinning3Image = ImageIO.read(new File(redTurtleSpinning3ImagePath));
			redTurtleSpinning4Image = ImageIO.read(new File(redTurtleSpinning4ImagePath));
			redTurtleStandingLeftImage = ImageIO.read(new File(redTurtleStandingLeftImagePath));
			redTurtleStandingRightImage = ImageIO.read(new File(redTurtleStandingRightImagePath));
			redTurtleWalkingLeftImage = ImageIO.read(new File(redTurtleWalkingLeftImagePath));
			redTurtleWalkingRightImage = ImageIO.read(new File(redTurtleWalkingRightImagePath));


		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
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
				bigMarioRightCatTail2Image, bigMarioCatTail3Image,
				smallMarioPipeImage, bigMarioPipeImage, fireMarioPipeImage
				);
		MovingObject.setCanvas(this.getGCanvas(), mario);
		RedTurtle.setObjects(redTurtleSpinning1Image, redTurtleSpinning2Image, 
				redTurtleSpinning3Image, redTurtleSpinning4Image, redTurtleStandingLeftImage,
				redTurtleStandingRightImage, redTurtleWalkingLeftImage, redTurtleWalkingRightImage);
		UpShootingFlower.setObjects(shootingFlowerLeftDownClosedImage, shootingFlowerLeftDownOpenImage,
				shootingFlowerLeftUpClosedImage, shootingFlowerLeftUpOpenImage,
				shootingFlowerRightDownClosedImage, shootingFlowerRightDownOpenImage,
				shootingFlowerRightUpClosedImage, shootingFlowerRightUpOpenImage);
		DownShootingFlower.setObjects(downShootingFlowerLeftDownClosedImage, downShootingFlowerLeftDownOpenImage,
				downShootingFlowerLeftUpClosedImage, downShootingFlowerLeftUpOpenImage,
				downShootingFlowerRightDownClosedImage, downShootingFlowerRightDownOpenImage,
				downShootingFlowerRightUpClosedImage, downShootingFlowerRightUpOpenImage);
		MysteryBox.setObjects(mysteryBox1Image, mysteryBox2Image, mysteryBox3Image, mysteryBox4Image, mysteryBoxFinalImage);
		Mushroom.setObjects(mushroomImage);
		FireBall.setObjects(leftFireBall1Image, rightFireBall1Image,leftFireBall2Image,
				rightFireBall2Image, leftFireBall3Image,
				rightFireBall3Image, leftFireBall4Image,
				rightFireBall4Image);
		FireFlower.setObjects(fireFlowerImage);
		Leaf.setObjects(rightLeafImage, leftLeafImage);
		Factory.setCanvas(this.getGCanvas());

		LevelController.setObjects(grassLeftTopImage,grassRightTopImage, grassMidleTopImage, grassLeftImage, 
				grassRightImage,grassMiddleImage, 
				pipeUpTopLeftImage, pipeUpTopRightImage, pipeDownMiddleLeftImage, pipeDownMiddleRightImage,
				pipeDownTopLeftImage, pipeDownTopRightImage, pipeUpMiddleLeftImage, pipeUpMiddleRightImage,
				mario,
				this.getGCanvas());
		setSize(WIDTH,HEIGHT);
		addKeyListeners(new MyKeyListener(mario));
		LevelController.playLevel1();
		//LevelController.playLevel2();
	}


}
