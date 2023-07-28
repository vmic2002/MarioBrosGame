import java.awt.image.BufferedImage;
import java.awt.Color;
//import java.awt.Image;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;

import javax.imageio.ImageIO;

import acm.graphics.*;
//import acm.program.GraphicsProgram;

import java.awt.BorderLayout;

import javax.swing.JFrame;


import java.awt.GraphicsEnvironment;//to verify that headless mode is enabled (if run on server without displaying a window)
import java.awt.HeadlessException;

public class MarioBrosGame {//extends GraphicsProgram {

	/*General comments for Mario Game:
	 * 
	 * to use new images for GImages take a screenshot
	 * and select size 320x320 in Preview
	 * 
	 * 
	 * small mario has height 80 pixels
	 * big mario has height 160 pixels
	 * 
	 * TODO possible idea mario could have laser eyes (mario or firemario
	 * pictures edited to a different color.) mario would be able to move while shooting his laser eyes
	 * since the arrows will be needed to control where the laser beam goes
	 * 
	 * TODO could have campaign mode (worlds) AND mode with randomly generated levels!
	 */

	private static final int WIDTH = 800;//1200;
	private static final int HEIGHT = 750;//800;

	//public void run() {
	//@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		System.out.println("RUNNING MAIN FUNCTION");
		boolean runningOnTomcatServer = false;
		if (args.length>0) {
			System.out.println("Session id: "+args[0]);

			ServerToClientMessenger.setSessionId(args[0]);
			runningOnTomcatServer = true;
		}

		// If running game on java web server and connecting to client site (website) via javascript using websockets,
		// need to set headless mode to true
		// because no window will be displayed on the server side
		

		boolean setToHeadlessMode = runningOnTomcatServer;
		// Enable headless mode
		if (setToHeadlessMode) System.setProperty("java.awt.headless", "true");

		// Check if headless mode is enabled
		System.out.println("Headless mode: " + GraphicsEnvironment.isHeadless());


		GCanvas canvas = new GCanvas();
		canvas.setSize(WIDTH, HEIGHT);
		JFrame frame = null;
		try {
			//new JFrame() will throw HeadlessException if headless mode is set to true -> GraphicsEnvironment.isHeadless()==true
			frame = new JFrame();
			frame.getContentPane().add(BorderLayout.CENTER, canvas);
			frame.setSize(WIDTH,HEIGHT);
			frame.show();

			float[] hsb = new float[3];
			Color.RGBtoHSB(0, 120, 255, hsb);
			Color c = new Color(hsb[0], hsb[1], hsb[2]);
			canvas.setBackground(c);
		} catch (HeadlessException e) {
			System.out.println("\"new JFrame()\" threw HeadlessException!");
			System.out.println(e.getMessage());
		}

		System.out.println("MarioBrosGame loading...");

		//when running from command line:
		//image path is ../Images/*.png since program is run from bin directory
		//when running from command line, prefix = "../"
		//when running from eclipse, prefix = ""
		//before pushing to github make sure prefix = "../"

		boolean runningFromCommandLine = false;//set to false to run from eclipse, true from command line
		//TO RUN FROM TOMCAT SERVER, runningFromCommandLine NEEDS TO BE SET TO FALSE (still need to test this not sure)

		String prefix;
		if (runningOnTomcatServer) {
			//if running $CATALINA_HOME/bin/startup.sh from 
			// /Users/victormicha/Desktop/apache-tomcat-10.1.11
			//this successfully loads images!
			prefix = "webapps/MarioGameServerSide/";
		} else {
			prefix = runningFromCommandLine?"../":"";
		}
		//String prefix = "";

		String imageDirectory = "Images";
		//String imageDirectory = "ScaledImages";




		SoundController.setPrefix(prefix);
		SoundController.setRunningOnTomcatServer(runningOnTomcatServer);

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



		String tanookiMarioLeftCatImagePath = prefix+imageDirectory+"/leftTanookiMario.png";
		String tanookiMarioRightCatImagePath = prefix+imageDirectory+"/rightTanookiMario.png";
		String tanookiMarioLeftWakingCatImagePath = prefix+imageDirectory+"/leftTanookiMarioWalking.png";
		String tanookiMarioRightWalkingCatImagePath = prefix+imageDirectory+"/rightTanookiMarioWalking.png";
		String tanookiMarioLeftJumpingCatImagePath = prefix+imageDirectory+"/leftTanookiMarioJumpingUp.png";
		String tanookiMarioRightJumpingCatImagePath = prefix+imageDirectory+"/rightTanookiMarioJumpingUp.png";
		String tanookiMarioRightJumpingDownCatImagePath = prefix+imageDirectory+"/rightTanookiMarioJumpingDown.png";
		String tanookiMarioLeftJumpingDownCatImagePath = prefix+imageDirectory+"/leftTanookiMarioJumpingDown.png";
		String tanookiMarioLeftCrouchingCatImagePath = prefix+imageDirectory+"/leftTanookiMarioCrouching.png";
		String tanookiMarioRightCrouchingCatImagePath = prefix+imageDirectory+"/rightTanookiMarioCrouching.png";
		String tanookiMarioLeftJumpingCatTail1ImagePath = prefix+imageDirectory+"/leftTanookiMarioJumpTail1.png";
		String tanookiMarioRightJumpingCatTal1ImagePath = prefix+imageDirectory+"/rightTanookiMarioJumpTail1.png";
		String tanookiMarioLeftJumpingCatTail2ImagePath = prefix+imageDirectory+"/leftTanookiMarioJumpTail2.png";
		String tanookiMarioRightJumpingCatTail2ImagePath = prefix+imageDirectory+"/rightTanookiMarioJumpTail1.png";
		String tanookiMarioCatTail1ImagePath = prefix+imageDirectory+"/tanookiMarioTail1.png";
		String tanookiMarioLeftCatTail2ImagePath = prefix+imageDirectory+"/leftTanookiMarioTail2.png";
		String tanookiMarioRightCatTail2ImagePath = prefix+imageDirectory+"/rightTanookiMarioTail2.png";
		String tanookiMarioCatTail3ImagePath = prefix+imageDirectory+"/tanookiTail3.png";


		String smallMarioPipeImagePath = prefix+imageDirectory+"/smallMarioPipe.png";
		String bigMarioPipeImagePath = prefix+imageDirectory+"/bigMarioPipe.png";
		String fireMarioPipeImagePath = prefix+imageDirectory+"/fireMarioPipe.png";

		String smallLuigiLeftImagePath = prefix+imageDirectory+"/smallLuigiLeftImage.png";
		String smallLuigiRightImagePath = prefix+imageDirectory+"/smallLuigiRightImage.png";
		String smallLuigiRightWalkingImagePath = prefix+imageDirectory+"/smallLuigiRightWalkingImage.png";
		String smallLuigiLeftWalkingImagePath = prefix+imageDirectory+"/smallLuigiLeftWalkingImage.png";
		String smallLuigiLeftJumpingImagePath = prefix+imageDirectory+"/smallLuigiLeftJumpingImage.png";
		String smallLuigiRightJumpingImagePath = prefix+imageDirectory+"/smallLuigiLRightJumpingImage.png";
		String luigiDeadImagePath = prefix+imageDirectory+"/luigiDeadImage.png";

		String bigLuigiLeftImagePath = prefix+imageDirectory+"/bigLuigiLeftImage.png";
		String bigLuigiRightImagePath = prefix+imageDirectory+"/bigLuigiRightImage.png";
		String bigLuigiRightWalkingImagePath = prefix+imageDirectory+"/bigLuigiRightWalkingImage.png";
		String bigLuigiLeftWalkingImagePath = prefix+imageDirectory+"/bigLuigiLeftWalkingImage.png";
		String bigLuigiLeftJumpingImagePath = prefix+imageDirectory+"/bigLuigiLeftJumpingImage.png";
		String bigLuigiRightJumpingImagePath = prefix+imageDirectory+"/bigLuigiRightJumpingImage.png";
		String bigLuigiLeftJumpingDownImagePath = prefix+imageDirectory+"/bigLuigiLeftJumpingDownImage.png";
		String bigLuigiRightJumpingDownImagePath = prefix+imageDirectory+"/bigLuigiRightJumpingDownImage.png";
		String bigLuigiLeftCrouchingImagePath = prefix+imageDirectory+"/bigLuigiLeftCrouchingImage.png";
		String bigLuigiRightCrouchingImagePath = prefix+imageDirectory+"/bigLuigiRightCrouchingImage.png";

		String bigLuigiLeftCatImagePath = prefix+imageDirectory+"/catLuigiLeftImage.png";
		String bigLuigiRightCatImagePath = prefix+imageDirectory+"/catLuigiRightImage.png";
		String bigLuigiLeftWakingCatImagePath = prefix+imageDirectory+"/catLuigiLeftWalkingImage.png";
		String bigLuigiRightWalkingCatImagePath = prefix+imageDirectory+"/catLuigiRightWalkingImage.png";
		String bigLuigiLeftJumpingCatImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingImage.png";
		String bigLuigiRightJumpingCatImagePath = prefix+imageDirectory+"/catLuigiRightJumpingImage.png";
		String bigLuigiRightJumpingDownCatImagePath = prefix+imageDirectory+"/catLuigiRightJumpingDownImage.png";
		String bigLuigiLeftJumpingDownCatImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingDownImage.png";
		String bigLuigiLeftCrouchingCatImagePath = prefix+imageDirectory+"/catLuigiLeftCrouchingImage.png";
		String bigLuigiRightCrouchingCatImagePath = prefix+imageDirectory+"/catLuigiRightCrouchingImage.png";
		String bigLuigiLeftJumpingCatTail1ImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingTail1Image.png";
		String bigLuigiRightJumpingCatTal1ImagePath = prefix+imageDirectory+"/catLuigiRightJumpingTail1Image.png";
		String bigLuigiLeftJumpingCatTail2ImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingTail2Image.png";
		String bigLuigiRightJumpingCatTail2ImagePath = prefix+imageDirectory+"/catLuigiRightJumpingTail2Image.png";
		String bigLuigiCatTail1ImagePath = prefix+imageDirectory+"/catLuigiPipeImage.png";
		String bigLuigiLeftCatTail2ImagePath = prefix+imageDirectory+"/catLuigiLeftTail2Image.png";
		String bigLuigiRightCatTail2ImagePath = prefix+imageDirectory+"/catLuigiRightTail2Image.png";
		String bigLuigiCatTail3ImagePath = prefix+imageDirectory+"/catLuigiTail3.png";

		String smallLuigiPipeImagePath = prefix+imageDirectory+"/smallLuigiPipe.png";
		String bigLuigiPipeImagePath = prefix+imageDirectory+"/bigLuigiPipe.png";		

		String leftFireBall1ImagePath = prefix+imageDirectory+"/leftFireBall1.png";
		String rightFireBall1ImagePath = prefix+imageDirectory+"/rightFireBall1.png";
		String leftFireBall2ImagePath = prefix+imageDirectory+"/leftFireBall2.png";
		String rightFireBall2ImagePath = prefix+imageDirectory+"/rightFireBall2.png";
		String leftFireBall3ImagePath = prefix+imageDirectory+"/leftFireBall3.png";
		String rightFireBall3ImagePath = prefix+imageDirectory+"/rightFireBall3.png";
		String leftFireBall4ImagePath = prefix+imageDirectory+"/leftFireBall4.png";
		String rightFireBall4ImagePath = prefix+imageDirectory+"/rightFireBall4.png";

		String mushroomImagePath = prefix+imageDirectory+"/mushroomImage.png";
		String fireFlowerImagePath = prefix+imageDirectory+"/fireFlowerImage.png";
		String leftLeafImagePath = prefix+imageDirectory+"/leftLeafImage.png";
		String rightLeafImagePath = prefix+imageDirectory+"/rightLeafImage.png";
		String tanookiPowerUpImagePath = prefix+imageDirectory+"/tanookiPowerUp.png";


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



		String leftBulletBillImagePath = prefix+imageDirectory+"/leftBulletBill.png";
		String rightBulletBillImagePath = prefix+imageDirectory+"/rightBulletBill.png";
		String billBlasterTopImagePath = prefix+imageDirectory+"/billBlasterTop.png";
		String billBlasterMiddleImagePath = prefix+imageDirectory+"/billBlasterMiddle.png";
		String billBlasterBottomImagePath = prefix+imageDirectory+"/billBlasterBottom.png";

		String goombaLeftImagePath = prefix+imageDirectory+"/goombaLeft.png";
		String goombaRightImagePath = prefix+imageDirectory+"/goombaRight.png";
		String goombaSquishedImagePath = prefix+imageDirectory+"/goombaSquished.png";

		String coin1ImagePath = prefix+imageDirectory+"/coin1.png";
		String coin2ImagePath = prefix+imageDirectory+"/coin2.png";
		String coin3ImagePath = prefix+imageDirectory+"/coin3.png";



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


		BufferedImage tanookiMarioLeftCatImage = null;
		BufferedImage tanookiMarioRightCatImage = null;
		BufferedImage tanookiMarioLeftWakingCatImage = null;
		BufferedImage tanookiMarioRightWalkingCatImage = null;
		BufferedImage tanookiMarioLeftJumpingCatImage = null;
		BufferedImage tanookiMarioRightJumpingCatImage = null;
		BufferedImage tanookiMarioRightJumpingDownCatImage = null;
		BufferedImage tanookiMarioLeftJumpingDownCatImage = null;
		BufferedImage tanookiMarioLeftCrouchingCatImage = null;
		BufferedImage tanookiMarioRightCrouchingCatImage = null;
		BufferedImage tanookiMarioLeftJumpingCatTail1Image = null;
		BufferedImage tanookiMarioRightJumpingCatTal1Image = null;
		BufferedImage tanookiMarioLeftJumpingCatTail2Image = null;
		BufferedImage tanookiMarioRightJumpingCatTail2Image = null;
		BufferedImage tanookiMarioCatTail1Image = null;
		BufferedImage tanookiMarioLeftCatTail2Image = null;
		BufferedImage tanookiMarioRightCatTail2Image = null;
		BufferedImage tanookiMarioCatTail3Image = null;


		BufferedImage smallMarioPipeImage = null;
		BufferedImage bigMarioPipeImage = null;
		BufferedImage fireMarioPipeImage = null;

		BufferedImage smallLuigiLeftImage = null;
		BufferedImage smallLuigiRightImage = null;
		BufferedImage smallLuigiRightWalkingImage = null;
		BufferedImage smallLuigiLeftWalkingImage = null;
		BufferedImage smallLuigiLeftJumpingImage = null;
		BufferedImage smallLuigiRightJumpingImage = null;
		BufferedImage luigiDeadImage = null;

		BufferedImage bigLuigiLeftImage = null;
		BufferedImage bigLuigiRightImage = null;
		BufferedImage bigLuigiRightWalkingImage = null;
		BufferedImage bigLuigiLeftWalkingImage = null;
		BufferedImage bigLuigiLeftJumpingImage = null;
		BufferedImage bigLuigiRightJumpingImage = null;
		BufferedImage bigLuigiLeftJumpingDownImage = null;
		BufferedImage bigLuigiRightJumpingDownImage = null;
		BufferedImage bigLuigiLeftCrouchingImage = null;
		BufferedImage bigLuigiRightCrouchingImage = null;

		BufferedImage bigLuigiLeftCatImage = null;
		BufferedImage bigLuigiRightCatImage = null;
		BufferedImage bigLuigiLeftWakingCatImage = null;
		BufferedImage bigLuigiRightWalkingCatImage = null;
		BufferedImage bigLuigiLeftJumpingCatImage = null;
		BufferedImage bigLuigiRightJumpingCatImage = null;
		BufferedImage bigLuigiRightJumpingDownCatImage = null;
		BufferedImage bigLuigiLeftJumpingDownCatImage = null;
		BufferedImage bigLuigiLeftCrouchingCatImage = null;
		BufferedImage bigLuigiRightCrouchingCatImage = null;
		BufferedImage bigLuigiLeftJumpingCatTail1Image = null;
		BufferedImage bigLuigiRightJumpingCatTal1Image = null;
		BufferedImage bigLuigiLeftJumpingCatTail2Image = null;
		BufferedImage bigLuigiRightJumpingCatTail2Image = null;
		BufferedImage bigLuigiCatTail1Image = null;
		BufferedImage bigLuigiLeftCatTail2Image = null;
		BufferedImage bigLuigiRightCatTail2Image = null;
		BufferedImage bigLuigiCatTail3Image = null;

		BufferedImage smallLuigiPipeImage = null;
		BufferedImage bigLuigiPipeImage = null;

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
		BufferedImage leftLeafImage = null;
		BufferedImage rightLeafImage = null;
		BufferedImage tanookiPowerUpImage = null;

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

		BufferedImage leftBulletBillImage = null;
		BufferedImage rightBulletBillImage = null;
		BufferedImage billBlasterTopImage = null;
		BufferedImage billBlasterMiddleImage = null;
		BufferedImage billBlasterBottomImage = null;


		BufferedImage goombaLeftImage = null;
		BufferedImage goombaRightImage = null;
		BufferedImage goombaSquishedImage = null;

		BufferedImage coin1Image = null;
		BufferedImage coin2Image = null;
		BufferedImage coin3Image = null;

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


			tanookiMarioLeftCatImage = ImageIO.read(new File(tanookiMarioLeftCatImagePath));
			tanookiMarioRightCatImage = ImageIO.read(new File(tanookiMarioRightCatImagePath));
			tanookiMarioLeftWakingCatImage = ImageIO.read(new File(tanookiMarioLeftWakingCatImagePath));
			tanookiMarioRightWalkingCatImage = ImageIO.read(new File(tanookiMarioRightWalkingCatImagePath));
			tanookiMarioLeftJumpingCatImage = ImageIO.read(new File(tanookiMarioLeftJumpingCatImagePath));
			tanookiMarioRightJumpingCatImage = ImageIO.read(new File(tanookiMarioRightJumpingCatImagePath));
			tanookiMarioRightJumpingDownCatImage = ImageIO.read(new File(tanookiMarioRightJumpingDownCatImagePath));
			tanookiMarioLeftJumpingDownCatImage = ImageIO.read(new File(tanookiMarioLeftJumpingDownCatImagePath));
			tanookiMarioLeftCrouchingCatImage = ImageIO.read(new File(tanookiMarioLeftCrouchingCatImagePath));
			tanookiMarioRightCrouchingCatImage = ImageIO.read(new File(tanookiMarioRightCrouchingCatImagePath));
			tanookiMarioLeftJumpingCatTail1Image = ImageIO.read(new File(tanookiMarioLeftJumpingCatTail1ImagePath));
			tanookiMarioRightJumpingCatTal1Image = ImageIO.read(new File(tanookiMarioRightJumpingCatTal1ImagePath));
			tanookiMarioLeftJumpingCatTail2Image = ImageIO.read(new File(tanookiMarioLeftJumpingCatTail2ImagePath));
			tanookiMarioRightJumpingCatTail2Image = ImageIO.read(new File(tanookiMarioRightJumpingCatTail2ImagePath));
			tanookiMarioCatTail1Image = ImageIO.read(new File(tanookiMarioCatTail1ImagePath));
			tanookiMarioLeftCatTail2Image = ImageIO.read(new File(tanookiMarioLeftCatTail2ImagePath));
			tanookiMarioRightCatTail2Image = ImageIO.read(new File(tanookiMarioRightCatTail2ImagePath));
			tanookiMarioCatTail3Image = ImageIO.read(new File(tanookiMarioCatTail3ImagePath));


			smallMarioPipeImage = ImageIO.read(new File(smallMarioPipeImagePath));
			bigMarioPipeImage = ImageIO.read(new File(bigMarioPipeImagePath));
			fireMarioPipeImage = ImageIO.read(new File(fireMarioPipeImagePath));			

			smallLuigiLeftImage = ImageIO.read(new File(smallLuigiLeftImagePath));
			smallLuigiRightImage = ImageIO.read(new File(smallLuigiRightImagePath));
			smallLuigiRightWalkingImage = ImageIO.read(new File(smallLuigiRightWalkingImagePath));
			smallLuigiLeftWalkingImage = ImageIO.read(new File(smallLuigiLeftWalkingImagePath));
			smallLuigiLeftJumpingImage = ImageIO.read(new File(smallLuigiLeftJumpingImagePath));
			smallLuigiRightJumpingImage = ImageIO.read(new File(smallLuigiRightJumpingImagePath));
			luigiDeadImage = ImageIO.read(new File(luigiDeadImagePath));

			bigLuigiLeftImage = ImageIO.read(new File(bigLuigiLeftImagePath));
			bigLuigiRightImage = ImageIO.read(new File(bigLuigiRightImagePath));
			bigLuigiRightWalkingImage = ImageIO.read(new File(bigLuigiRightWalkingImagePath));
			bigLuigiLeftWalkingImage = ImageIO.read(new File(bigLuigiLeftWalkingImagePath));
			bigLuigiLeftJumpingImage = ImageIO.read(new File(bigLuigiLeftJumpingImagePath));
			bigLuigiRightJumpingImage = ImageIO.read(new File(bigLuigiRightJumpingImagePath));
			bigLuigiLeftJumpingDownImage = ImageIO.read(new File(bigLuigiLeftJumpingDownImagePath));
			bigLuigiRightJumpingDownImage = ImageIO.read(new File(bigLuigiRightJumpingDownImagePath));
			bigLuigiLeftCrouchingImage = ImageIO.read(new File(bigLuigiLeftCrouchingImagePath));
			bigLuigiRightCrouchingImage = ImageIO.read(new File(bigLuigiRightCrouchingImagePath));

			bigLuigiLeftCatImage = ImageIO.read(new File(bigLuigiLeftCatImagePath));
			bigLuigiRightCatImage = ImageIO.read(new File(bigLuigiRightCatImagePath));
			bigLuigiLeftWakingCatImage = ImageIO.read(new File(bigLuigiLeftWakingCatImagePath));
			bigLuigiRightWalkingCatImage = ImageIO.read(new File(bigLuigiRightWalkingCatImagePath));
			bigLuigiLeftJumpingCatImage = ImageIO.read(new File(bigLuigiLeftJumpingCatImagePath));
			bigLuigiRightJumpingCatImage = ImageIO.read(new File(bigLuigiRightJumpingCatImagePath));
			bigLuigiRightJumpingDownCatImage = ImageIO.read(new File(bigLuigiRightJumpingDownCatImagePath));
			bigLuigiLeftJumpingDownCatImage = ImageIO.read(new File(bigLuigiLeftJumpingDownCatImagePath));
			bigLuigiLeftCrouchingCatImage = ImageIO.read(new File(bigLuigiLeftCrouchingCatImagePath));
			bigLuigiRightCrouchingCatImage = ImageIO.read(new File(bigLuigiRightCrouchingCatImagePath));
			bigLuigiLeftJumpingCatTail1Image = ImageIO.read(new File(bigLuigiLeftJumpingCatTail1ImagePath));
			bigLuigiRightJumpingCatTal1Image = ImageIO.read(new File(bigLuigiRightJumpingCatTal1ImagePath));
			bigLuigiLeftJumpingCatTail2Image = ImageIO.read(new File(bigLuigiLeftJumpingCatTail2ImagePath));
			bigLuigiRightJumpingCatTail2Image = ImageIO.read(new File(bigLuigiRightJumpingCatTail2ImagePath));
			bigLuigiCatTail1Image = ImageIO.read(new File(bigLuigiCatTail1ImagePath));
			bigLuigiLeftCatTail2Image = ImageIO.read(new File(bigLuigiLeftCatTail2ImagePath));
			bigLuigiRightCatTail2Image = ImageIO.read(new File(bigLuigiRightCatTail2ImagePath));
			bigLuigiCatTail3Image = ImageIO.read(new File(bigLuigiCatTail3ImagePath));

			smallLuigiPipeImage = ImageIO.read(new File(smallLuigiPipeImagePath));
			bigLuigiPipeImage = ImageIO.read(new File(bigLuigiPipeImagePath));

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
			leftLeafImage = ImageIO.read(new File(leftLeafImagePath));;
			rightLeafImage = ImageIO.read(new File(rightLeafImagePath));
			tanookiPowerUpImage = ImageIO.read(new File(tanookiPowerUpImagePath));

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

			leftBulletBillImage = ImageIO.read(new File(leftBulletBillImagePath));
			rightBulletBillImage = ImageIO.read(new File(rightBulletBillImagePath));
			billBlasterTopImage = ImageIO.read(new File(billBlasterTopImagePath));
			billBlasterMiddleImage = ImageIO.read(new File(billBlasterMiddleImagePath));
			billBlasterBottomImage = ImageIO.read(new File(billBlasterBottomImagePath));

			goombaLeftImage = ImageIO.read(new File(goombaLeftImagePath));
			goombaRightImage = ImageIO.read(new File(goombaRightImagePath));
			goombaSquishedImage = ImageIO.read(new File(goombaSquishedImagePath));


			coin1Image = ImageIO.read(new File(coin1ImagePath));
			coin2Image = ImageIO.read(new File(coin2ImagePath));
			coin3Image = ImageIO.read(new File(coin3ImagePath));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("IMAGES SUCCESSFULLY LOADED!");
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


				tanookiMarioLeftCatImage, tanookiMarioRightCatImage, tanookiMarioLeftWakingCatImage, tanookiMarioRightWalkingCatImage,
				tanookiMarioLeftJumpingCatImage, tanookiMarioRightJumpingCatImage, tanookiMarioRightJumpingDownCatImage,
				tanookiMarioLeftJumpingDownCatImage, tanookiMarioLeftCrouchingCatImage, tanookiMarioRightCrouchingCatImage,
				tanookiMarioLeftJumpingCatTail1Image, tanookiMarioRightJumpingCatTal1Image, tanookiMarioLeftJumpingCatTail2Image,
				tanookiMarioRightJumpingCatTail2Image,
				tanookiMarioCatTail1Image, tanookiMarioLeftCatTail2Image,
				tanookiMarioRightCatTail2Image, tanookiMarioCatTail3Image,


				smallMarioPipeImage, bigMarioPipeImage, fireMarioPipeImage, Mario.CHARACTER.MARIO
				);
		Luigi luigi = new Luigi(smallLuigiLeftImage,smallLuigiRightImage,
				smallLuigiLeftWalkingImage, smallLuigiRightWalkingImage, smallLuigiLeftJumpingImage, 
				smallLuigiRightJumpingImage, luigiDeadImage,

				bigLuigiLeftImage, bigLuigiRightImage,
				bigLuigiLeftWalkingImage, bigLuigiRightWalkingImage, bigLuigiLeftJumpingImage,
				bigLuigiRightJumpingImage, bigMarioLeftFireImage, bigMarioRightFireImage, bigMarioLeftWalkingFireImage
				, bigMarioRightWalkingFireImage,bigMarioLeftJumpingFireImage, bigMarioRightJumpingFireImage,
				bigLuigiLeftJumpingDownImage, bigLuigiRightJumpingDownImage,

				bigMarioLeftJumpingDownFireImage, bigMarioRightJumpingDownFireImage,
				bigLuigiLeftCrouchingImage, bigLuigiRightCrouchingImage,
				bigMarioLeftCrouchingFireImage, bigMarioRightCrouchingFireImage,
				bigMarioLeftFireShooting1Image, bigMarioLeftFireShooting2Image,
				bigMarioRightFireShooting1Image, bigMarioRightFireShooting2Image,
				bigMarioLeftJumpingFireShooting1Image, bigMarioLeftJumpingFireShooting2Image,
				bigMarioLeftJumpingFireShooting3Image, bigMarioRightJumpingFireShooting1Image,
				bigMarioRightJumpingFireShooting2Image, bigMarioRightJumpingFireShooting3Image,

				bigLuigiLeftCatImage, bigLuigiRightCatImage, bigLuigiLeftWakingCatImage, bigLuigiRightWalkingCatImage,
				bigLuigiLeftJumpingCatImage, bigLuigiRightJumpingCatImage, bigLuigiRightJumpingDownCatImage,
				bigLuigiLeftJumpingDownCatImage, bigLuigiLeftCrouchingCatImage, bigLuigiRightCrouchingCatImage,
				bigLuigiLeftJumpingCatTail1Image, bigLuigiRightJumpingCatTal1Image, bigLuigiLeftJumpingCatTail2Image,
				bigLuigiRightJumpingCatTail2Image,
				bigLuigiCatTail1Image, bigLuigiLeftCatTail2Image,
				bigLuigiRightCatTail2Image, bigLuigiCatTail3Image,


				tanookiMarioLeftCatImage, tanookiMarioRightCatImage, tanookiMarioLeftWakingCatImage, tanookiMarioRightWalkingCatImage,
				tanookiMarioLeftJumpingCatImage, tanookiMarioRightJumpingCatImage, tanookiMarioRightJumpingDownCatImage,
				tanookiMarioLeftJumpingDownCatImage, tanookiMarioLeftCrouchingCatImage, tanookiMarioRightCrouchingCatImage,
				tanookiMarioLeftJumpingCatTail1Image, tanookiMarioRightJumpingCatTal1Image, tanookiMarioLeftJumpingCatTail2Image,
				tanookiMarioRightJumpingCatTail2Image,
				tanookiMarioCatTail1Image, tanookiMarioLeftCatTail2Image,
				tanookiMarioRightCatTail2Image, tanookiMarioCatTail3Image,

				smallLuigiPipeImage, bigLuigiPipeImage, fireMarioPipeImage, Mario.CHARACTER.LUIGI);
		int numCharacters = 2;//number of players in game. could add toad peach etc for more characters (all playing at the same time in same level!)
		Mario[] characters = new Mario[numCharacters];
		characters[0] = luigi;
		characters[1] = mario;
		StatsController.initializeStats(characters);
		MovingObject.setObjects(canvas, mario.scalingFactor, characters);
		Coin.setObjects(coin1Image, coin2Image, coin3Image, canvas);
		Goomba.setObjects(goombaRightImage, goombaLeftImage, goombaSquishedImage);
		BulletBill.setObjects(leftBulletBillImage, rightBulletBillImage);
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
		Tanooki.setObjects(tanookiPowerUpImage);
		Mushroom.setObjects(mushroomImage);
		FireBall.setObjects(leftFireBall1Image, rightFireBall1Image,leftFireBall2Image,
				rightFireBall2Image, leftFireBall3Image,
				rightFireBall3Image, leftFireBall4Image,
				rightFireBall4Image);
		FireFlower.setObjects(fireFlowerImage);
		Leaf.setObjects(rightLeafImage, leftLeafImage);
		DynamicFactory.setCanvas(canvas);
		StaticFactory.setObjects(grassLeftTopImage,grassRightTopImage, grassMidleTopImage, grassLeftImage, 
				grassRightImage,grassMiddleImage, 
				pipeUpTopLeftImage, pipeUpTopRightImage, pipeDownMiddleLeftImage, pipeDownMiddleRightImage,
				pipeDownTopLeftImage, pipeDownTopRightImage, pipeUpMiddleLeftImage, pipeUpMiddleRightImage,
				billBlasterTopImage, billBlasterMiddleImage, billBlasterBottomImage,
				canvas);
		LevelController.setObjects(canvas, mario.scalingFactor);
		BillBlasterController.setCanvas(canvas);
		if (!runningOnTomcatServer) canvas.addKeyListener(new MyKeyListener(characters));
		else VirtualClientKeyboard.setCharacters(characters);
		LevelController.playLevel("5");
		//LevelController.playLevel2();
	}
}
