//import java.awt.image.BufferedImage;
import java.awt.Color;
//import java.awt.Image;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
//import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;

//import acm.graphics.*;
//import acm.program.GraphicsProgram;

import java.awt.BorderLayout;

import javax.swing.JFrame;


import java.awt.GraphicsEnvironment;//to verify that headless mode is enabled (if run on server without displaying a window)
import java.awt.HeadlessException;

public class MarioBrosGame {
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
	 * pictures edited to a different color.) mario would not be able to move while shooting his laser eyes
	 * since the arrows will be needed to control where the laser beam goes
	 * 
	 * TODO could have campaign mode (worlds) AND mode with randomly generated levels!
	 */

	private static final int WIDTH = 1000;//1200;
	private static final int HEIGHT = 800;//800;

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


		MyGCanvas canvas = new MyGCanvas();
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
		String bigMarioTail1Path = prefix+imageDirectory+"/bigMarioTail1.png";
		String bigMarioLeftTail2Path = prefix+imageDirectory+"/bigMarioLeftTail2.png";
		String bigMarioRightTail2Path = prefix+imageDirectory+"/bigMarioRightTail2.png";
		String bigMarioTail3Path = prefix+imageDirectory+"/bigMarioTail3.png";



		String leftTanookiMarioPath = prefix+imageDirectory+"/leftTanookiMario.png";
		String rightTanookiMarioPath = prefix+imageDirectory+"/rightTanookiMario.png";
		String leftTanookiMarioWalkingPath = prefix+imageDirectory+"/leftTanookiMarioWalking.png";
		String rightTanookiMarioWalkingPath = prefix+imageDirectory+"/rightTanookiMarioWalking.png";
		String leftTanookiMarioJumpingUpPath = prefix+imageDirectory+"/leftTanookiMarioJumpingUp.png";
		String rightTanookiMarioJumpingUpPath = prefix+imageDirectory+"/rightTanookiMarioJumpingUp.png";
		String rightTanookiMarioJumpingDownPath = prefix+imageDirectory+"/rightTanookiMarioJumpingDown.png";
		String leftTanookiMarioJumpingDownPath = prefix+imageDirectory+"/leftTanookiMarioJumpingDown.png";
		String leftTanookiMarioCrouchingPath = prefix+imageDirectory+"/leftTanookiMarioCrouching.png";
		String rightTanookiMarioCrouchingPath = prefix+imageDirectory+"/rightTanookiMarioCrouching.png";
		String leftTanookiMarioJumpTail1Path = prefix+imageDirectory+"/leftTanookiMarioJumpTail1.png";
		String rightTanookiMarioJumpTail1Path = prefix+imageDirectory+"/rightTanookiMarioJumpTail1.png";
		String leftTanookiMarioJumpTail2Path = prefix+imageDirectory+"/leftTanookiMarioJumpTail2.png";
		String rightTanookiMarioJumpTail1ImagePath = prefix+imageDirectory+"/rightTanookiMarioJumpTail1.png";
		String tanookiMarioTail1Path = prefix+imageDirectory+"/tanookiMarioTail1.png";
		String leftTanookiMarioTail2Path = prefix+imageDirectory+"/leftTanookiMarioTail2.png";
		String rightTanookiMarioTail2Path = prefix+imageDirectory+"/rightTanookiMarioTail2.png";
		String tanookiTail3Path = prefix+imageDirectory+"/tanookiTail3.png";


		String smallMarioPipePath = prefix+imageDirectory+"/smallMarioPipe.png";
		String bigMarioPipePath = prefix+imageDirectory+"/bigMarioPipe.png";
		String fireMarioPipePath = prefix+imageDirectory+"/fireMarioPipe.png";

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



		String catLuigiLeftImagePath = prefix+imageDirectory+"/catLuigiLeftImage.png";
		String catLuigiRightImagePath = prefix+imageDirectory+"/catLuigiRightImage.png";
		String catLuigiLeftWalkingImagePath = prefix+imageDirectory+"/catLuigiLeftWalkingImage.png";
		String catLuigiRightWalkingImagePath = prefix+imageDirectory+"/catLuigiRightWalkingImage.png";
		String catLuigiLeftJumpingImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingImage.png";
		String catLuigiRightJumpingImagePath = prefix+imageDirectory+"/catLuigiRightJumpingImage.png";
		String catLuigiRightJumpingDownImagePath = prefix+imageDirectory+"/catLuigiRightJumpingDownImage.png";
		String catLuigiLeftJumpingDownImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingDownImage.png";
		String catLuigiLeftCrouchingImagePath = prefix+imageDirectory+"/catLuigiLeftCrouchingImage.png";
		String catLuigiRightCrouchingImagePath = prefix+imageDirectory+"/catLuigiRightCrouchingImage.png";
		String catLuigiLeftJumpingTail1ImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingTail1Image.png";
		String catLuigiRightJumpingTail1ImagePath = prefix+imageDirectory+"/catLuigiRightJumpingTail1Image.png";
		String catLuigiLeftJumpingTail2ImagePath = prefix+imageDirectory+"/catLuigiLeftJumpingTail2Image.png";
		String catLuigiRightJumpingTail2ImagePath = prefix+imageDirectory+"/catLuigiRightJumpingTail2Image.png";
		String catLuigiPipeImagePath = prefix+imageDirectory+"/catLuigiPipeImage.png";
		String catLuigiLeftTail2ImagePath = prefix+imageDirectory+"/catLuigiLeftTail2Image.png";
		String catLuigiRightTail2ImagePath = prefix+imageDirectory+"/catLuigiRightTail2Image.png";
		String catLuigiTail3Path = prefix+imageDirectory+"/catLuigiTail3.png";

		String smallLuigiPipePath = prefix+imageDirectory+"/smallLuigiPipe.png";
		String bigLuigiPipePath = prefix+imageDirectory+"/bigLuigiPipe.png";		


		String leftFireBall1Path = prefix+imageDirectory+"/leftFireBall1.png";
		String rightFireBall1Path = prefix+imageDirectory+"/rightFireBall1.png";
		String leftFireBall2Path = prefix+imageDirectory+"/leftFireBall2.png";
		String rightFireBall2Path = prefix+imageDirectory+"/rightFireBall2.png";
		String leftFireBall3Path = prefix+imageDirectory+"/leftFireBall3.png";
		String rightFireBall3Path = prefix+imageDirectory+"/rightFireBall3.png";
		String leftFireBall4Path = prefix+imageDirectory+"/leftFireBall4.png";
		String rightFireBall4Path = prefix+imageDirectory+"/rightFireBall4.png";


		String mushroomImagePath = prefix+imageDirectory+"/mushroomImage.png";
		String fireFlowerImagePath = prefix+imageDirectory+"/fireFlowerImage.png";
		String leftLeafImagePath = prefix+imageDirectory+"/leftLeafImage.png";
		String rightLeafImagePath = prefix+imageDirectory+"/rightLeafImage.png";
		String tanookiPowerUpPath = prefix+imageDirectory+"/tanookiPowerUp.png";


		String mysteryBox1Path = prefix+imageDirectory+"/mysteryBox1.png";
		String mysteryBox2Path = prefix+imageDirectory+"/mysteryBox2.png";
		String mysteryBox3Path = prefix+imageDirectory+"/mysteryBox3.png";
		String mysteryBox4Path = prefix+imageDirectory+"/mysteryBox4.png";
		String mysteryBoxFinalPath = prefix+imageDirectory+"/mysteryBoxFinal.png";

		String grassLeftTopImagePath = prefix+imageDirectory+"/grassLeftTopImage.png";
		String grassRightTopImagePath = prefix+imageDirectory+"/grassRightTopImage.png";
		String grassMiddleTopImagePath = prefix+imageDirectory+"/grassMiddleTopImage.png";
		String grassLeftImagePath = prefix+imageDirectory+"/grassLeftImage.png";
		String grassRightImagePath = prefix+imageDirectory+"/grassRightImage.png";
		String grassMiddleImagePath = prefix+imageDirectory+"/grassMiddleImage.png";

		String pipeUpTopLeftPath = prefix+imageDirectory+"/pipeUpTopLeft.png";
		String pipeUpTopRightPath = prefix+imageDirectory+"/pipeUpTopRight.png";
		String pipeDownMiddleLeftPath = prefix+imageDirectory+"/pipeDownMiddleLeft.png";
		String pipeDownMiddleRightPath = prefix+imageDirectory+"/pipeDownMiddleRight.png";
		String pipeDownTopLeftPath = prefix+imageDirectory+"/pipeDownTopLeft.png";
		String pipeDownTopRightPath = prefix+imageDirectory+"/pipeDownTopRight.png";
		String pipeUpMiddleLeftPath = prefix+imageDirectory+"/pipeUpMiddleLeft.png";
		String pipeUpMiddleRightPath = prefix+imageDirectory+"/pipeUpMiddleRight.png";

		String shootingFlowerLeftDownClosedPath = prefix+imageDirectory+"/shootingFlowerLeftDownClosed.png";
		String shootingFlowerLeftDownOpenPath = prefix+imageDirectory+"/shootingFlowerLeftDownOpen.png";
		String shootingFlowerLeftUpClosedPath = prefix+imageDirectory+"/shootingFlowerLeftUpClosed.png";
		String shootingFlowerLeftUpOpenPath = prefix+imageDirectory+"/shootingFlowerLeftUpOpen.png";
		String shootingFlowerRightDownClosedPath = prefix+imageDirectory+"/shootingFlowerRightDownClosed.png";
		String shootingFlowerRightDownOpenPath = prefix+imageDirectory+"/shootingFlowerRightDownOpen.png";
		String shootingFlowerRightUpClosedPath = prefix+imageDirectory+"/shootingFlowerRightUpClosed.png";
		String shootingFlowerRightUpOpenPath = prefix+imageDirectory+"/shootingFlowerRightUpOpen.png";

		String DOWNshootingFlowerLeftDownClosedPath = prefix+imageDirectory+"/DOWNshootingFlowerLeftDownClosed.png";
		String DOWNshootingFlowerLeftDownOpenPath = prefix+imageDirectory+"/DOWNshootingFlowerLeftDownOpen.png";
		String DOWNshootingFlowerLeftUpClosedPath = prefix+imageDirectory+"/DOWNshootingFlowerLeftUpClosed.png";
		String DOWNshootingFlowerLeftUpOpenPath = prefix+imageDirectory+"/DOWNshootingFlowerLeftUpOpen.png";
		String DOWNshootingFlowerRightDownClosedPath = prefix+imageDirectory+"/DOWNshootingFlowerRightDownClosed.png";
		String DOWNshootingFlowerRightDownOpenPath = prefix+imageDirectory+"/DOWNshootingFlowerRightDownOpen.png";
		String DOWNshootingFlowerRightUpClosedPath = prefix+imageDirectory+"/DOWNshootingFlowerRightUpClosed.png";
		String DOWNshootingFlowerRightUpOpenPath = prefix+imageDirectory+"/DOWNshootingFlowerRightUpOpen.png";

		String redTurtleSpinning1Path = prefix+imageDirectory+"/redTurtleSpinning1.png";
		String redTurtleSpinning2Path = prefix+imageDirectory+"/redTurtleSpinning2.png";
		String redTurtleSpinning3Path = prefix+imageDirectory+"/redTurtleSpinning3.png";
		String redTurtleSpinning4Path = prefix+imageDirectory+"/redTurtleSpinning4.png";
		String redTurtleStandingLeftPath = prefix+imageDirectory+"/redTurtleStandingLeft.png";
		String redTurtleStandingRightPath = prefix+imageDirectory+"/redTurtleStandingRight.png";
		String redTurtleWalkingLeftPath = prefix+imageDirectory+"/redTurtleWalkingLeft.png";
		String redTurtleWalkingRightPath = prefix+imageDirectory+"/redTurtleWalkingRight.png";



		String leftBulletBillPath = prefix+imageDirectory+"/leftBulletBill.png";
		String rightBulletBillPath = prefix+imageDirectory+"/rightBulletBill.png";
		String billBlasterTopPath = prefix+imageDirectory+"/billBlasterTop.png";
		String billBlasterMiddlePath = prefix+imageDirectory+"/billBlasterMiddle.png";
		String billBlasterBottomPath = prefix+imageDirectory+"/billBlasterBottom.png";

		String goombaLeftPath = prefix+imageDirectory+"/goombaLeft.png";
		String goombaRightPath = prefix+imageDirectory+"/goombaRight.png";
		String goombaSquishedPath = prefix+imageDirectory+"/goombaSquished.png";

		String coin1Path = prefix+imageDirectory+"/coin1.png";
		String coin2Path = prefix+imageDirectory+"/coin2.png";
		String coin3Path = prefix+imageDirectory+"/coin3.png";




		String timedilatingsmallMarioLeftImagePath = prefix+imageDirectory+"/timedilatingsmallMarioLeftImage.png";
		String timedilatingbigLuigiLeftJumpingDownImagePath = prefix+imageDirectory+"/timedilatingbigLuigiLeftJumpingDownImage.png";
		String timedilatingbigMarioLeftJumpingImagePath = prefix+imageDirectory+"/timedilatingbigMarioLeftJumpingImage.png";
		String timedilatingsmallMarioLeftWalkingImagePath = prefix+imageDirectory+"/timedilatingsmallMarioLeftWalkingImage.png";
		String timedilatingbigLuigiLeftImagePath = prefix+imageDirectory+"/timedilatingbigLuigiLeftImage.png";
		String timedilatingsmallLuigiLRightJumpingImagePath = prefix+imageDirectory+"/timedilatingsmallLuigiLRightJumpingImage.png";
		String timedilatingbigLuigiRightJumpingImagePath = prefix+imageDirectory+"/timedilatingbigLuigiRightJumpingImage.png";
		String timedilatingbigMarioRightWalkingImagePath = prefix+imageDirectory+"/timedilatingbigMarioRightWalkingImage.png";
		String timedilatingsmallLuigiRightWalkingImagePath = prefix+imageDirectory+"/timedilatingsmallLuigiRightWalkingImage.png";
		String timedilatingbigLuigiLeftJumpingImagePath = prefix+imageDirectory+"/timedilatingbigLuigiLeftJumpingImage.png";
		String timedilatingsmallMarioRightJumpingImagePath = prefix+imageDirectory+"/timedilatingsmallMarioRightJumpingImage.png";
		String timedilatingsmallMarioRightImagePath = prefix+imageDirectory+"/timedilatingsmallMarioRightImage.png";
		String timedilatingbigMarioRightCrouchingImagePath = prefix+imageDirectory+"/timedilatingbigMarioRightCrouchingImage.png";
		String timedilatingsmallLuigiLeftWalkingImagePath = prefix+imageDirectory+"/timedilatingsmallLuigiLeftWalkingImage.png";
		String timedilatingbigLuigiRightCrouchingImagePath = prefix+imageDirectory+"/timedilatingbigLuigiRightCrouchingImage.png";
		String timedilatingbigLuigiRightImagePath = prefix+imageDirectory+"/timedilatingbigLuigiRightImage.png";
		String timedilatingbigMarioLeftWalkingImagePath = prefix+imageDirectory+"/timedilatingbigMarioLeftWalkingImage.png";
		String timedilatingsmallLuigiLeftImagePath = prefix+imageDirectory+"/timedilatingsmallLuigiLeftImage.png";
		String timedilatingsmallLuigiPipePath = prefix+imageDirectory+"/timedilatingsmallLuigiPipe.png";
		String timedilatingbigMarioPipePath = prefix+imageDirectory+"/timedilatingbigMarioPipe.png";
		String timedilatingbigMarioLeftImagePath = prefix+imageDirectory+"/timedilatingbigMarioLeftImage.png";
		String timedilatingsmallMarioLeftJumpingImagePath = prefix+imageDirectory+"/timedilatingsmallMarioLeftJumpingImage.png";
		String timedilatingbigMarioLeftJumpingDownImagePath = prefix+imageDirectory+"/timedilatingbigMarioLeftJumpingDownImage.png";
		String timedilatingsmallMarioRightWalkingImagePath = prefix+imageDirectory+"/timedilatingsmallMarioRightWalkingImage.png";
		String timedilatingbigLuigiLeftWalkingImagePath = prefix+imageDirectory+"/timedilatingbigLuigiLeftWalkingImage.png";
		String timedilatingbigMarioRightJumpingImagePath = prefix+imageDirectory+"/timedilatingbigMarioRightJumpingImage.png";
		String timedilatingbigMarioRightImagePath = prefix+imageDirectory+"/timedilatingbigMarioRightImage.png";
		String timedilatingbigLuigiRightWalkingImagePath = prefix+imageDirectory+"/timedilatingbigLuigiRightWalkingImage.png";
		String timedilatingsmallMarioPipePath = prefix+imageDirectory+"/timedilatingsmallMarioPipe.png";
		String timedilatingbigLuigiLeftCrouchingImagePath = prefix+imageDirectory+"/timedilatingbigLuigiLeftCrouchingImage.png";
		String timedilatingbigMarioLeftCrouchingImagePath = prefix+imageDirectory+"/timedilatingbigMarioLeftCrouchingImage.png";
		String timedilatingbigLuigiPipePath = prefix+imageDirectory+"/timedilatingbigLuigiPipe.png";
		String timedilatingsmallLuigiRightImagePath = prefix+imageDirectory+"/timedilatingsmallLuigiRightImage.png";
		String timedilatingbigMarioRightJumpingDownImagePath = prefix+imageDirectory+"/timedilatingbigMarioRightJumpingDownImage.png";
		String timedilatingsmallLuigiLeftJumpingImagePath = prefix+imageDirectory+"/timedilatingsmallLuigiLeftJumpingImage.png";
		String timedilatingbigLuigiRightJumpingDownImagePath = prefix+imageDirectory+"/timedilatingbigLuigiRightJumpingDownImage.png";

		String timeDilationPowerupPath = prefix+imageDirectory+"/timeDilationPowerup.png";

		
		
		String greenMushroomPlatformLeftPath = prefix+imageDirectory+"/greenMushroomPlatformLeft.png";
		String redMushroomPlatformLeftPath = prefix+imageDirectory+"/redMushroomPlatformLeft.png";
		String greenMushroomPlatformMiddlePath = prefix+imageDirectory+"/greenMushroomPlatformMiddle.png";
		String yellowMushroomPlatformMiddlePath = prefix+imageDirectory+"/yellowMushroomPlatformMiddle.png";
		String redMushroomPlatformRightPath = prefix+imageDirectory+"/redMushroomPlatformRight.png";
		String mushroomPlatformBottomPath = prefix+imageDirectory+"/mushroomPlatformBottom.png";
		String yellowMushroomPlatformLeftPath = prefix+imageDirectory+"/yellowMushroomPlatformLeft.png";
		String yellowMushroomPlatformRightPath = prefix+imageDirectory+"/yellowMushroomPlatformRight.png";
		String greenMushroomPlatformRightPath = prefix+imageDirectory+"/greenMushroomPlatformRight.png";
		String redMushroomPlatformMiddlePath = prefix+imageDirectory+"/redMushroomPlatformMiddle.png";
		


		MyImage smallMarioLeftImage = null;
		MyImage smallMarioRightImage = null;
		MyImage smallMarioRightWalkingImage = null;
		MyImage smallMarioLeftWalkingImage = null;
		MyImage smallMarioLeftJumpingImage = null;
		MyImage smallMarioRightJumpingImage = null;
		MyImage marioDeadImage = null;

		MyImage bigMarioLeftImage = null;
		MyImage bigMarioRightImage = null;
		MyImage bigMarioRightWalkingImage = null;
		MyImage bigMarioLeftWalkingImage = null;
		MyImage bigMarioLeftJumpingImage = null;
		MyImage bigMarioRightJumpingImage = null;
		MyImage bigMarioLeftJumpingDownImage = null;
		MyImage bigMarioRightJumpingDownImage = null;
		MyImage bigMarioLeftCrouchingImage = null;
		MyImage bigMarioRightCrouchingImage = null;

		MyImage bigMarioLeftFireImage = null;
		MyImage bigMarioRightFireImage = null;
		MyImage bigMarioRightWalkingFireImage = null;
		MyImage bigMarioLeftWalkingFireImage = null;
		MyImage bigMarioRightJumpingFireImage = null;
		MyImage bigMarioLeftJumpingFireImage = null;
		MyImage bigMarioLeftJumpingDownFireImage = null;
		MyImage bigMarioRightJumpingDownFireImage = null;
		MyImage bigMarioLeftCrouchingFireImage = null;
		MyImage bigMarioRightCrouchingFireImage = null;
		MyImage bigMarioLeftFireShooting1Image = null;
		MyImage bigMarioLeftFireShooting2Image = null;
		MyImage bigMarioRightFireShooting1Image = null;
		MyImage bigMarioRightFireShooting2Image  = null;
		MyImage bigMarioLeftJumpingFireShooting1Image = null;
		MyImage bigMarioLeftJumpingFireShooting2Image = null;
		MyImage bigMarioLeftJumpingFireShooting3Image = null;
		MyImage bigMarioRightJumpingFireShooting1Image = null;
		MyImage bigMarioRightJumpingFireShooting2Image = null;
		MyImage bigMarioRightJumpingFireShooting3Image = null;

		MyImage bigMarioLeftCatImage = null;
		MyImage bigMarioRightCatImage = null;
		MyImage bigMarioLeftWakingCatImage = null;
		MyImage bigMarioRightWalkingCatImage = null;
		MyImage bigMarioLeftJumpingCatImage = null;
		MyImage bigMarioRightJumpingCatImage = null;
		MyImage bigMarioRightJumpingDownCatImage = null;
		MyImage bigMarioLeftJumpingDownCatImage = null;
		MyImage bigMarioLeftCrouchingCatImage = null;
		MyImage bigMarioRightCrouchingCatImage = null;
		MyImage bigMarioLeftJumpingCatTail1Image = null;
		MyImage bigMarioRightJumpingCatTal1Image = null;
		MyImage bigMarioLeftJumpingCatTail2Image = null;
		MyImage bigMarioRightJumpingCatTal2Image = null;
		MyImage bigMarioTail1 = null;
		MyImage bigMarioLeftTail2 = null;
		MyImage bigMarioRightTail2 = null;
		MyImage bigMarioTail3 = null;





		MyImage leftTanookiMario = null;
		MyImage rightTanookiMario = null;
		MyImage leftTanookiMarioWalking = null;
		MyImage rightTanookiMarioWalking = null;
		MyImage leftTanookiMarioJumpingUp = null;
		MyImage rightTanookiMarioJumpingUp = null;
		MyImage rightTanookiMarioJumpingDown = null;
		MyImage leftTanookiMarioJumpingDown = null;
		MyImage leftTanookiMarioCrouching = null;
		MyImage rightTanookiMarioCrouching = null;
		MyImage leftTanookiMarioJumpTail1 = null;
		MyImage rightTanookiMarioJumpTail1 = null;
		MyImage leftTanookiMarioJumpTail2 = null;
		MyImage rightTanookiMarioJumpTail1Image = null;
		MyImage tanookiMarioTail1 = null;
		MyImage leftTanookiMarioTail2 = null;
		MyImage rightTanookiMarioTail2 = null;
		MyImage tanookiTail3 = null;


		MyImage smallMarioPipe = null;
		MyImage bigMarioPipe = null;
		MyImage fireMarioPipe = null;



		MyImage smallLuigiLeftImage = null;
		MyImage smallLuigiRightImage = null;
		MyImage smallLuigiRightWalkingImage = null;
		MyImage smallLuigiLeftWalkingImage = null;
		MyImage smallLuigiLeftJumpingImage = null;
		MyImage smallLuigiRightJumpingImage = null;
		MyImage luigiDeadImage = null;

		MyImage bigLuigiLeftImage = null;
		MyImage bigLuigiRightImage = null;
		MyImage bigLuigiRightWalkingImage = null;
		MyImage bigLuigiLeftWalkingImage = null;
		MyImage bigLuigiLeftJumpingImage = null;
		MyImage bigLuigiRightJumpingImage = null;
		MyImage bigLuigiLeftJumpingDownImage = null;
		MyImage bigLuigiRightJumpingDownImage = null;
		MyImage bigLuigiLeftCrouchingImage = null;
		MyImage bigLuigiRightCrouchingImage = null;



		MyImage catLuigiLeftImage = null;
		MyImage catLuigiRightImage = null;
		MyImage catLuigiLeftWalkingImage = null;
		MyImage catLuigiRightWalkingImage = null;
		MyImage catLuigiLeftJumpingImage = null;
		MyImage catLuigiRightJumpingImage = null;
		MyImage catLuigiRightJumpingDownImage = null;
		MyImage catLuigiLeftJumpingDownImage = null;
		MyImage catLuigiLeftCrouchingImage = null;
		MyImage catLuigiRightCrouchingImage = null;
		MyImage catLuigiLeftJumpingTail1Image = null;
		MyImage catLuigiRightJumpingTail1Image = null;
		MyImage catLuigiLeftJumpingTail2Image = null;
		MyImage catLuigiRightJumpingTail2Image = null;
		MyImage catLuigiPipeImage = null;
		MyImage catLuigiLeftTail2Image = null;
		MyImage catLuigiRightTail2Image = null;
		MyImage catLuigiTail3 = null;

		MyImage smallLuigiPipe = null;
		MyImage bigLuigiPipe = null;

		MyImage leftFireBall1 = null;
		MyImage rightFireBall1 = null;
		MyImage leftFireBall2 = null;
		MyImage rightFireBall2 = null;
		MyImage leftFireBall3 = null;
		MyImage rightFireBall3 = null;
		MyImage leftFireBall4 = null;
		MyImage rightFireBall4 = null;




		MyImage mushroomImage = null;
		MyImage fireFlowerImage = null;
		MyImage leftLeafImage = null;
		MyImage rightLeafImage = null;
		MyImage tanookiPowerUp = null;

		MyImage mysteryBox1 = null;
		MyImage mysteryBox2 = null;
		MyImage mysteryBox3 = null;
		MyImage mysteryBox4 = null;
		MyImage mysteryBoxFinal = null;



		MyImage grassLeftTopImage = null;
		MyImage grassRightTopImage = null;
		MyImage grassMidleTopImage = null;
		MyImage grassLeftImage = null;
		MyImage grassRightImage = null;
		MyImage grassMiddleImage = null;



		MyImage pipeUpTopLeft = null;
		MyImage pipeUpTopRight = null;
		MyImage pipeDownMiddleLeft = null;
		MyImage pipeDownMiddleRight = null;
		MyImage pipeDownTopLeft = null;
		MyImage pipeDownTopRight = null;
		MyImage pipeUpMiddleLeft = null;
		MyImage pipeUpMiddleRight = null;

		MyImage shootingFlowerLeftDownClosed = null; 
		MyImage shootingFlowerLeftDownOpen = null;
		MyImage shootingFlowerLeftUpClosed = null;
		MyImage shootingFlowerLeftUpOpen = null;
		MyImage shootingFlowerRightDownClosed = null; 
		MyImage shootingFlowerRightDownOpen = null;
		MyImage shootingFlowerRightUpClosed = null;
		MyImage shootingFlowerRightUpOpen = null;

		MyImage DOWNshootingFlowerLeftDownClosed = null; 
		MyImage DOWNshootingFlowerLeftDownOpen = null;
		MyImage DOWNshootingFlowerLeftUpClosed = null;
		MyImage DOWNshootingFlowerLeftUpOpen = null;
		MyImage DOWNshootingFlowerRightDownClosed = null; 
		MyImage DOWNshootingFlowerRightDownOpen = null;
		MyImage DOWNshootingFlowerRightUpClosed = null;
		MyImage DOWNshootingFlowerRightUpOpen = null;

		MyImage redTurtleSpinning1 = null;
		MyImage redTurtleSpinning2 = null;
		MyImage redTurtleSpinning3 = null;
		MyImage redTurtleSpinning4 = null;
		MyImage redTurtleStandingLeft = null;
		MyImage redTurtleStandingRight = null;
		MyImage redTurtleWalkingLeft = null;
		MyImage redTurtleWalkingRight = null;

		MyImage leftBulletBill = null;
		MyImage rightBulletBill = null;
		MyImage billBlasterTop = null;
		MyImage billBlasterMiddle = null;
		MyImage billBlasterBottom = null;


		MyImage goombaLeft = null;
		MyImage goombaRight = null;
		MyImage goombaSquished = null;

		MyImage coin1 = null;
		MyImage coin2 = null;
		MyImage coin3 = null;



		MyImage timedilatingsmallMarioLeftImage = null;
		MyImage timedilatingbigMarioLeftJumpingImage = null;
		MyImage timedilatingsmallMarioLeftWalkingImage = null;
		MyImage timedilatingbigMarioRightWalkingImage = null;
		MyImage timedilatingsmallMarioRightJumpingImage = null;
		MyImage timedilatingsmallMarioRightImage = null;
		MyImage timedilatingbigMarioRightCrouchingImage = null;
		MyImage timedilatingbigMarioLeftWalkingImage = null;
		MyImage timedilatingbigMarioPipe = null;
		MyImage timedilatingbigMarioLeftImage = null;
		MyImage timedilatingsmallMarioLeftJumpingImage = null;
		MyImage timedilatingbigMarioLeftJumpingDownImage = null;
		MyImage timedilatingsmallMarioRightWalkingImage = null;
		MyImage timedilatingbigMarioRightJumpingImage = null;
		MyImage timedilatingbigMarioRightImage = null;
		MyImage timedilatingsmallMarioPipe = null;
		MyImage timedilatingbigMarioLeftCrouchingImage = null;
		MyImage timedilatingbigMarioRightJumpingDownImage = null;


		MyImage timedilatingbigLuigiLeftJumpingDownImage = null;
		MyImage timedilatingbigLuigiLeftImage = null;
		MyImage timedilatingsmallLuigiLRightJumpingImage = null;
		MyImage timedilatingbigLuigiRightJumpingImage = null;
		MyImage timedilatingsmallLuigiRightWalkingImage = null;
		MyImage timedilatingbigLuigiLeftJumpingImage = null;
		MyImage timedilatingsmallLuigiLeftWalkingImage = null;
		MyImage timedilatingbigLuigiRightCrouchingImage = null;
		MyImage timedilatingbigLuigiRightImage = null;
		MyImage timedilatingsmallLuigiLeftImage = null;
		MyImage timedilatingsmallLuigiPipe = null;
		MyImage timedilatingbigLuigiLeftWalkingImage = null;
		MyImage timedilatingbigLuigiRightWalkingImage = null;
		MyImage timedilatingbigLuigiLeftCrouchingImage = null;
		MyImage timedilatingbigLuigiPipe = null;
		MyImage timedilatingsmallLuigiRightImage = null;
		MyImage timedilatingsmallLuigiLeftJumpingImage = null;
		MyImage timedilatingbigLuigiRightJumpingDownImage = null;

		MyImage timeDilationPowerup = null;
		
		
		MyImage greenMushroomPlatformLeft = null;
		MyImage redMushroomPlatformLeft = null;
		MyImage greenMushroomPlatformMiddle = null;
		MyImage yellowMushroomPlatformMiddle = null;
		MyImage redMushroomPlatformRight = null;
		MyImage mushroomPlatformBottom = null;
		MyImage yellowMushroomPlatformLeft = null;
		MyImage yellowMushroomPlatformRight = null;
		MyImage greenMushroomPlatformRight = null;
		MyImage redMushroomPlatformMiddle = null;

		try {
			smallMarioLeftImage = new MyImage(ImageIO.read(new File(smallMarioLeftImagePath)), "smallMarioLeftImage");
			smallMarioRightImage = new MyImage(ImageIO.read(new File(smallMarioRightImagePath)), "smallMarioRightImage");
			smallMarioLeftWalkingImage = new MyImage(ImageIO.read(new File(smallMarioLeftWalkingImagePath)), "smallMarioLeftWalkingImage");
			smallMarioRightWalkingImage = new MyImage(ImageIO.read(new File(smallMarioRightWalkingImagePath)), "smallMarioRightWalkingImage");
			smallMarioLeftJumpingImage = new MyImage(ImageIO.read(new File(smallMarioLeftJumpingImagePath)), "smallMarioLeftJumpingImage");
			smallMarioRightJumpingImage = new MyImage(ImageIO.read(new File(smallMarioRightJumpingImagePath)), "smallMarioRightJumpingImage");
			marioDeadImage = new MyImage(ImageIO.read(new File(marioDeadImagePath)), "marioDeadImage");

			bigMarioLeftImage = new MyImage(ImageIO.read(new File(bigMarioLeftImagePath)), "bigMarioLeftImage");
			bigMarioRightImage = new MyImage(ImageIO.read(new File(bigMarioRightImagePath)), "bigMarioRightImage");
			bigMarioLeftWalkingImage = new MyImage(ImageIO.read(new File(bigMarioLeftWalkingImagePath)), "bigMarioLeftWalkingImage");
			bigMarioRightWalkingImage = new MyImage(ImageIO.read(new File(bigMarioRightWalkingImagePath)), "bigMarioRightWalkingImage");
			bigMarioLeftJumpingImage = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingImagePath)), "bigMarioLeftJumpingImage");
			bigMarioRightJumpingImage = new MyImage(ImageIO.read(new File(bigMarioRightJumpingImagePath)), "bigMarioRightJumpingImage");
			bigMarioLeftJumpingDownImage = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingDownImagePath)), "bigMarioLeftJumpingDownImage");
			bigMarioRightJumpingDownImage = new MyImage(ImageIO.read(new File(bigMarioRightJumpingDownImagePath)), "bigMarioRightJumpingDownImage");
			bigMarioLeftCrouchingImage = new MyImage(ImageIO.read(new File(bigMarioLeftCrouchingImagePath)), "bigMarioLeftCrouchingImage");
			bigMarioRightCrouchingImage = new MyImage(ImageIO.read(new File(bigMarioRightCrouchingImagePath)), "bigMarioRightCrouchingImage");
			bigMarioLeftFireImage = new MyImage(ImageIO.read(new File(bigMarioLeftFireImagePath)), "bigMarioLeftFireImage");
			bigMarioRightFireImage = new MyImage(ImageIO.read(new File(bigMarioRightFireImagePath)), "bigMarioRightFireImage");
			bigMarioLeftWalkingFireImage = new MyImage(ImageIO.read(new File(bigMarioLeftWakingFireImagePath)), "bigMarioLeftWalkingFireImage");
			bigMarioRightWalkingFireImage = new MyImage(ImageIO.read(new File(bigMarioRightWalkingFireImagePath)), "bigMarioRightWalkingFireImage");
			bigMarioLeftJumpingFireImage = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingFireImagePath)), "bigMarioLeftJumpingFireImage");
			bigMarioRightJumpingFireImage = new MyImage(ImageIO.read(new File(bigMarioRightJumpingFireImagePath)), "bigMarioRightJumpingFireImage");
			bigMarioLeftJumpingDownFireImage = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingDownFireImagePath)), "bigMarioLeftJumpingDownFireImage");
			bigMarioRightJumpingDownFireImage = new MyImage(ImageIO.read(new File(bigMarioRightJumpingDownFireImagePath)), "bigMarioRightJumpingDownFireImage");
			bigMarioLeftCrouchingFireImage = new MyImage(ImageIO.read(new File(bigMarioLeftCrouchingFireImagePath)), "bigMarioLeftCrouchingFireImage");
			bigMarioRightCrouchingFireImage= new MyImage(ImageIO.read(new File(bigMarioRightCrouchingFireImagePath)), "bigMarioRightCrouchingFireImage");
			bigMarioLeftFireShooting1Image = new MyImage(ImageIO.read(new File(bigMarioLeftFireShooting1ImagePath)), "bigMarioLeftFireShooting1Image");
			bigMarioLeftFireShooting2Image = new MyImage(ImageIO.read(new File(bigMarioLeftFireShooting2ImagePath)), "bigMarioLeftFireShooting2Image");
			bigMarioRightFireShooting1Image = new MyImage(ImageIO.read(new File(bigMarioRightFireShooting1ImagePath)), "bigMarioRightFireShooting1Image");
			bigMarioRightFireShooting2Image  = new MyImage(ImageIO.read(new File(bigMarioRightFireShooting2ImagePath)), "bigMarioRightFireShooting2Image");
			bigMarioLeftJumpingFireShooting1Image = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingFireShooting1ImagePath)), "bigMarioLeftJumpingFireShooting1Image");
			bigMarioLeftJumpingFireShooting2Image = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingFireShooting2ImagePath)), "bigMarioLeftJumpingFireShooting2Image");
			bigMarioLeftJumpingFireShooting3Image = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingFireShooting3ImagePath)), "bigMarioLeftJumpingFireShooting3Image");
			bigMarioRightJumpingFireShooting1Image = new MyImage(ImageIO.read(new File(bigMarioRightJumpingFireShooting1ImagePath)), "bigMarioRightJumpingFireShooting1Image");
			bigMarioRightJumpingFireShooting2Image = new MyImage(ImageIO.read(new File(bigMarioRightJumpingFireShooting2ImagePath)), "bigMarioRightJumpingFireShooting2Image");
			bigMarioRightJumpingFireShooting3Image = new MyImage(ImageIO.read(new File(bigMarioRightJumpingFireShooting3ImagePath)), "bigMarioRightJumpingFireShooting3Image");

			bigMarioLeftCatImage = new MyImage(ImageIO.read(new File(bigMarioLeftCatImagePath)), "bigMarioLeftCatImage");
			bigMarioRightCatImage = new MyImage(ImageIO.read(new File(bigMarioRightCatImagePath)), "bigMarioRightCatImage");
			bigMarioLeftWakingCatImage = new MyImage(ImageIO.read(new File(bigMarioLeftWakingCatImagePath)), "bigMarioLeftWakingCatImage");
			bigMarioRightWalkingCatImage = new MyImage(ImageIO.read(new File(bigMarioRightWalkingCatImagePath)), "bigMarioRightWalkingCatImage");
			bigMarioLeftJumpingCatImage = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingCatImagePath)), "bigMarioLeftJumpingCatImage");
			bigMarioRightJumpingCatImage = new MyImage(ImageIO.read(new File(bigMarioRightJumpingCatImagePath)), "bigMarioRightJumpingCatImage");
			bigMarioRightJumpingDownCatImage = new MyImage(ImageIO.read(new File(bigMarioRightJumpingDownCatImagePath)), "bigMarioRightJumpingDownCatImage");
			bigMarioLeftJumpingDownCatImage = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingDownCatImagePath)), "bigMarioLeftJumpingDownCatImage");
			bigMarioLeftCrouchingCatImage = new MyImage(ImageIO.read(new File(bigMarioLeftCrouchingCatImagePath)), "bigMarioLeftCrouchingCatImage");
			bigMarioRightCrouchingCatImage = new MyImage(ImageIO.read(new File(bigMarioRightCrouchingCatImagePath)), "bigMarioRightCrouchingCatImage");
			bigMarioLeftJumpingCatTail1Image = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingCatTail1ImagePath)), "bigMarioLeftJumpingCatTail1Image");
			bigMarioRightJumpingCatTal1Image = new MyImage(ImageIO.read(new File(bigMarioRightJumpingCatTal1ImagePath)), "bigMarioRightJumpingCatTal1Image");
			bigMarioLeftJumpingCatTail2Image = new MyImage(ImageIO.read(new File(bigMarioLeftJumpingCatTail2ImagePath)), "bigMarioLeftJumpingCatTail2Image");
			bigMarioRightJumpingCatTal2Image = new MyImage(ImageIO.read(new File(bigMarioRightJumpingCatTail2ImagePath)), "bigMarioRightJumpingCatTal2Image");
			bigMarioTail1 = new MyImage(ImageIO.read(new File(bigMarioTail1Path)), "bigMarioTail1");
			bigMarioLeftTail2 = new MyImage(ImageIO.read(new File(bigMarioLeftTail2Path)), "bigMarioLeftTail2");
			bigMarioRightTail2 = new MyImage(ImageIO.read(new File(bigMarioRightTail2Path)), "bigMarioRightTail2");
			bigMarioTail3 = new MyImage(ImageIO.read(new File(bigMarioTail3Path)), "bigMarioTail3");

			leftTanookiMario = new MyImage(ImageIO.read(new File(leftTanookiMarioPath)), "leftTanookiMario");
			rightTanookiMario = new MyImage(ImageIO.read(new File(rightTanookiMarioPath)), "rightTanookiMario");
			leftTanookiMarioWalking = new MyImage(ImageIO.read(new File(leftTanookiMarioWalkingPath)), "leftTanookiMarioWalking");
			rightTanookiMarioWalking = new MyImage(ImageIO.read(new File(rightTanookiMarioWalkingPath)), "rightTanookiMarioWalking");
			leftTanookiMarioJumpingUp = new MyImage(ImageIO.read(new File(leftTanookiMarioJumpingUpPath)), "leftTanookiMarioJumpingUp");
			rightTanookiMarioJumpingUp = new MyImage(ImageIO.read(new File(rightTanookiMarioJumpingUpPath)), "rightTanookiMarioJumpingUp");
			rightTanookiMarioJumpingDown = new MyImage(ImageIO.read(new File(rightTanookiMarioJumpingDownPath)), "rightTanookiMarioJumpingDown");
			leftTanookiMarioJumpingDown = new MyImage(ImageIO.read(new File(leftTanookiMarioJumpingDownPath)), "leftTanookiMarioJumpingDown");
			leftTanookiMarioCrouching = new MyImage(ImageIO.read(new File(leftTanookiMarioCrouchingPath)), "leftTanookiMarioCrouching");
			rightTanookiMarioCrouching = new MyImage(ImageIO.read(new File(rightTanookiMarioCrouchingPath)), "rightTanookiMarioCrouching");
			leftTanookiMarioJumpTail1 = new MyImage(ImageIO.read(new File(leftTanookiMarioJumpTail1Path)), "leftTanookiMarioJumpTail1");
			rightTanookiMarioJumpTail1 = new MyImage(ImageIO.read(new File(rightTanookiMarioJumpTail1Path)), "rightTanookiMarioJumpTail1");
			leftTanookiMarioJumpTail2 = new MyImage(ImageIO.read(new File(leftTanookiMarioJumpTail2Path)), "leftTanookiMarioJumpTail2");
			rightTanookiMarioJumpTail1Image = new MyImage(ImageIO.read(new File(rightTanookiMarioJumpTail1ImagePath)), "rightTanookiMarioJumpTail1Image");
			tanookiMarioTail1 = new MyImage(ImageIO.read(new File(tanookiMarioTail1Path)), "tanookiMarioTail1");
			leftTanookiMarioTail2 = new MyImage(ImageIO.read(new File(leftTanookiMarioTail2Path)), "leftTanookiMarioTail2");
			rightTanookiMarioTail2 = new MyImage(ImageIO.read(new File(rightTanookiMarioTail2Path)), "rightTanookiMarioTail2");
			tanookiTail3 = new MyImage(ImageIO.read(new File(tanookiTail3Path)), "tanookiTail3");

			smallMarioPipe = new MyImage(ImageIO.read(new File(smallMarioPipePath)), "smallMarioPipe");
			bigMarioPipe = new MyImage(ImageIO.read(new File(bigMarioPipePath)), "bigMarioPipe");
			fireMarioPipe = new MyImage(ImageIO.read(new File(fireMarioPipePath)), "fireMarioPipe");

			smallLuigiLeftImage = new MyImage(ImageIO.read(new File(smallLuigiLeftImagePath)), "smallLuigiLeftImage");
			smallLuigiRightImage = new MyImage(ImageIO.read(new File(smallLuigiRightImagePath)), "smallLuigiRightImage");
			smallLuigiRightWalkingImage = new MyImage(ImageIO.read(new File(smallLuigiRightWalkingImagePath)), "smallLuigiRightWalkingImage");
			smallLuigiLeftWalkingImage = new MyImage(ImageIO.read(new File(smallLuigiLeftWalkingImagePath)), "smallLuigiLeftWalkingImage");
			smallLuigiLeftJumpingImage = new MyImage(ImageIO.read(new File(smallLuigiLeftJumpingImagePath)), "smallLuigiLeftJumpingImage");
			smallLuigiRightJumpingImage = new MyImage(ImageIO.read(new File(smallLuigiRightJumpingImagePath)), "smallLuigiRightJumpingImage");
			luigiDeadImage = new MyImage(ImageIO.read(new File(luigiDeadImagePath)), "luigiDeadImage");

			bigLuigiLeftImage = new MyImage(ImageIO.read(new File(bigLuigiLeftImagePath)), "bigLuigiLeftImage");
			bigLuigiRightImage = new MyImage(ImageIO.read(new File(bigLuigiRightImagePath)), "bigLuigiRightImage");
			bigLuigiRightWalkingImage = new MyImage(ImageIO.read(new File(bigLuigiRightWalkingImagePath)), "bigLuigiRightWalkingImage");
			bigLuigiLeftWalkingImage = new MyImage(ImageIO.read(new File(bigLuigiLeftWalkingImagePath)), "bigLuigiLeftWalkingImage");
			bigLuigiLeftJumpingImage = new MyImage(ImageIO.read(new File(bigLuigiLeftJumpingImagePath)), "bigLuigiLeftJumpingImage");
			bigLuigiRightJumpingImage = new MyImage(ImageIO.read(new File(bigLuigiRightJumpingImagePath)), "bigLuigiRightJumpingImage");
			bigLuigiLeftJumpingDownImage = new MyImage(ImageIO.read(new File(bigLuigiLeftJumpingDownImagePath)), "bigLuigiLeftJumpingDownImage");
			bigLuigiRightJumpingDownImage = new MyImage(ImageIO.read(new File(bigLuigiRightJumpingDownImagePath)), "bigLuigiRightJumpingDownImage");
			bigLuigiLeftCrouchingImage = new MyImage(ImageIO.read(new File(bigLuigiLeftCrouchingImagePath)), "bigLuigiLeftCrouchingImage");
			bigLuigiRightCrouchingImage = new MyImage(ImageIO.read(new File(bigLuigiRightCrouchingImagePath)), "bigLuigiRightCrouchingImage");

			catLuigiLeftImage = new MyImage(ImageIO.read(new File(catLuigiLeftImagePath)), "catLuigiLeftImage");
			catLuigiRightImage = new MyImage(ImageIO.read(new File(catLuigiRightImagePath)), "catLuigiRightImage");
			catLuigiLeftWalkingImage = new MyImage(ImageIO.read(new File(catLuigiLeftWalkingImagePath)), "catLuigiLeftWalkingImage");
			catLuigiRightWalkingImage = new MyImage(ImageIO.read(new File(catLuigiRightWalkingImagePath)), "catLuigiRightWalkingImage");
			catLuigiLeftJumpingImage = new MyImage(ImageIO.read(new File(catLuigiLeftJumpingImagePath)), "catLuigiLeftJumpingImage");
			catLuigiRightJumpingImage = new MyImage(ImageIO.read(new File(catLuigiRightJumpingImagePath)), "catLuigiRightJumpingImage");
			catLuigiRightJumpingDownImage = new MyImage(ImageIO.read(new File(catLuigiRightJumpingDownImagePath)), "catLuigiRightJumpingDownImage");
			catLuigiLeftJumpingDownImage = new MyImage(ImageIO.read(new File(catLuigiLeftJumpingDownImagePath)), "catLuigiLeftJumpingDownImage");
			catLuigiLeftCrouchingImage = new MyImage(ImageIO.read(new File(catLuigiLeftCrouchingImagePath)), "catLuigiLeftCrouchingImage");
			catLuigiRightCrouchingImage = new MyImage(ImageIO.read(new File(catLuigiRightCrouchingImagePath)), "catLuigiRightCrouchingImage");
			catLuigiLeftJumpingTail1Image = new MyImage(ImageIO.read(new File(catLuigiLeftJumpingTail1ImagePath)), "catLuigiLeftJumpingTail1Image");
			catLuigiRightJumpingTail1Image = new MyImage(ImageIO.read(new File(catLuigiRightJumpingTail1ImagePath)), "catLuigiRightJumpingTail1Image");
			catLuigiLeftJumpingTail2Image = new MyImage(ImageIO.read(new File(catLuigiLeftJumpingTail2ImagePath)), "catLuigiLeftJumpingTail2Image");
			catLuigiRightJumpingTail2Image = new MyImage(ImageIO.read(new File(catLuigiRightJumpingTail2ImagePath)), "catLuigiRightJumpingTail2Image");
			catLuigiPipeImage = new MyImage(ImageIO.read(new File(catLuigiPipeImagePath)), "catLuigiPipeImage");
			catLuigiLeftTail2Image = new MyImage(ImageIO.read(new File(catLuigiLeftTail2ImagePath)), "catLuigiLeftTail2Image");
			catLuigiRightTail2Image = new MyImage(ImageIO.read(new File(catLuigiRightTail2ImagePath)), "catLuigiRightTail2Image");
			catLuigiTail3 = new MyImage(ImageIO.read(new File(catLuigiTail3Path)), "catLuigiTail3");

			smallLuigiPipe = new MyImage(ImageIO.read(new File(smallLuigiPipePath)), "smallLuigiPipe");
			bigLuigiPipe = new MyImage(ImageIO.read(new File(bigLuigiPipePath)), "bigLuigiPipe");

			leftFireBall1 = new MyImage(ImageIO.read(new File(leftFireBall1Path)), "leftFireBall1");
			rightFireBall1 = new MyImage(ImageIO.read(new File(rightFireBall1Path)), "rightFireBall1");
			leftFireBall2 = new MyImage(ImageIO.read(new File(leftFireBall2Path)), "leftFireBall2");
			rightFireBall2 = new MyImage(ImageIO.read(new File(rightFireBall2Path)), "rightFireBall2");
			leftFireBall3 = new MyImage(ImageIO.read(new File(leftFireBall3Path)), "leftFireBall3");
			rightFireBall3 = new MyImage(ImageIO.read(new File(rightFireBall3Path)), "rightFireBall3");
			leftFireBall4 = new MyImage(ImageIO.read(new File(leftFireBall4Path)), "leftFireBall4");
			rightFireBall4 = new MyImage(ImageIO.read(new File(rightFireBall4Path)), "rightFireBall4");

			mushroomImage = new MyImage(ImageIO.read(new File(mushroomImagePath)), "mushroomImage");
			fireFlowerImage = new MyImage(ImageIO.read(new File(fireFlowerImagePath)), "fireFlowerImage");
			leftLeafImage = new MyImage(ImageIO.read(new File(leftLeafImagePath)), "leftLeafImage");
			rightLeafImage = new MyImage(ImageIO.read(new File(rightLeafImagePath)), "rightLeafImage");
			tanookiPowerUp = new MyImage(ImageIO.read(new File(tanookiPowerUpPath)), "tanookiPowerUp");

			mysteryBox1 = new MyImage(ImageIO.read(new File(mysteryBox1Path)), "mysteryBox1");
			mysteryBox2 = new MyImage(ImageIO.read(new File(mysteryBox2Path)), "mysteryBox2");
			mysteryBox3 = new MyImage(ImageIO.read(new File(mysteryBox3Path)), "mysteryBox3");
			mysteryBox4 = new MyImage(ImageIO.read(new File(mysteryBox4Path)), "mysteryBox4");
			mysteryBoxFinal = new MyImage(ImageIO.read(new File(mysteryBoxFinalPath)), "mysteryBoxFinal");

			grassLeftTopImage = new MyImage(ImageIO.read(new File(grassLeftTopImagePath)), "grassLeftTopImage");
			grassRightTopImage = new MyImage(ImageIO.read(new File(grassRightTopImagePath)), "grassRightTopImage");
			grassMidleTopImage = new MyImage(ImageIO.read(new File(grassMiddleTopImagePath)), "grassMiddleTopImage");
			grassLeftImage = new MyImage(ImageIO.read(new File(grassLeftImagePath)), "grassLeftImage");
			grassRightImage = new MyImage(ImageIO.read(new File(grassRightImagePath)), "grassRightImage");
			grassMiddleImage = new MyImage(ImageIO.read(new File(grassMiddleImagePath)), "grassMiddleImage");

			pipeUpTopLeft = new MyImage(ImageIO.read(new File(pipeUpTopLeftPath)), "pipeUpTopLeft");
			pipeUpTopRight = new MyImage(ImageIO.read(new File(pipeUpTopRightPath)), "pipeUpTopRight");
			pipeDownMiddleLeft = new MyImage(ImageIO.read(new File(pipeDownMiddleLeftPath)), "pipeDownMiddleLeft");
			pipeDownMiddleRight = new MyImage(ImageIO.read(new File(pipeDownMiddleRightPath)), "pipeDownMiddleRight");
			pipeDownTopLeft = new MyImage(ImageIO.read(new File(pipeDownTopLeftPath)), "pipeDownTopLeft");
			pipeDownTopRight = new MyImage(ImageIO.read(new File(pipeDownTopRightPath)), "pipeDownTopRight");
			pipeUpMiddleLeft = new MyImage(ImageIO.read(new File(pipeUpMiddleLeftPath)), "pipeUpMiddleLeft");
			pipeUpMiddleRight = new MyImage(ImageIO.read(new File(pipeUpMiddleRightPath)), "pipeUpMiddleRight");

			shootingFlowerLeftDownClosed = new MyImage(ImageIO.read(new File(shootingFlowerLeftDownClosedPath)), "shootingFlowerLeftDownClosed");
			shootingFlowerLeftDownOpen = new MyImage(ImageIO.read(new File(shootingFlowerLeftDownOpenPath)), "shootingFlowerLeftDownOpen");
			shootingFlowerLeftUpClosed = new MyImage(ImageIO.read(new File(shootingFlowerLeftUpClosedPath)), "shootingFlowerLeftUpClosed");
			shootingFlowerLeftUpOpen = new MyImage(ImageIO.read(new File(shootingFlowerLeftUpOpenPath)), "shootingFlowerLeftUpOpen");
			shootingFlowerRightDownClosed = new MyImage(ImageIO.read(new File(shootingFlowerRightDownClosedPath)), "shootingFlowerRightDownClosed");
			shootingFlowerRightDownOpen = new MyImage(ImageIO.read(new File(shootingFlowerRightDownOpenPath)), "shootingFlowerRightDownOpen");
			shootingFlowerRightUpClosed = new MyImage(ImageIO.read(new File(shootingFlowerRightUpClosedPath)), "shootingFlowerRightUpClosed");
			shootingFlowerRightUpOpen = new MyImage(ImageIO.read(new File(shootingFlowerRightUpOpenPath)), "shootingFlowerRightUpOpen");

			DOWNshootingFlowerLeftDownClosed = new MyImage(ImageIO.read(new File(DOWNshootingFlowerLeftDownClosedPath)), "DOWNshootingFlowerLeftDownClosed");
			DOWNshootingFlowerLeftDownOpen = new MyImage(ImageIO.read(new File(DOWNshootingFlowerLeftDownOpenPath)), "DOWNshootingFlowerLeftDownOpen");
			DOWNshootingFlowerLeftUpClosed = new MyImage(ImageIO.read(new File(DOWNshootingFlowerLeftUpClosedPath)), "DOWNshootingFlowerLeftUpClosed");
			DOWNshootingFlowerLeftUpOpen = new MyImage(ImageIO.read(new File(DOWNshootingFlowerLeftUpOpenPath)), "DOWNshootingFlowerLeftUpOpen");
			DOWNshootingFlowerRightDownClosed = new MyImage(ImageIO.read(new File(DOWNshootingFlowerRightDownClosedPath)), "DOWNshootingFlowerRightDownClosed");
			DOWNshootingFlowerRightDownOpen = new MyImage(ImageIO.read(new File(DOWNshootingFlowerRightDownOpenPath)), "DOWNshootingFlowerRightDownOpen");
			DOWNshootingFlowerRightUpClosed = new MyImage(ImageIO.read(new File(DOWNshootingFlowerRightUpClosedPath)), "DOWNshootingFlowerRightUpClosed");
			DOWNshootingFlowerRightUpOpen = new MyImage(ImageIO.read(new File(DOWNshootingFlowerRightUpOpenPath)), "DOWNshootingFlowerRightUpOpen");

			redTurtleSpinning1 = new MyImage(ImageIO.read(new File(redTurtleSpinning1Path)), "redTurtleSpinning1");
			redTurtleSpinning2 = new MyImage(ImageIO.read(new File(redTurtleSpinning2Path)), "redTurtleSpinning2");
			redTurtleSpinning3 = new MyImage(ImageIO.read(new File(redTurtleSpinning3Path)), "redTurtleSpinning3");
			redTurtleSpinning4 = new MyImage(ImageIO.read(new File(redTurtleSpinning4Path)), "redTurtleSpinning4");
			redTurtleStandingLeft = new MyImage(ImageIO.read(new File(redTurtleStandingLeftPath)), "redTurtleStandingLeft");
			redTurtleStandingRight = new MyImage(ImageIO.read(new File(redTurtleStandingRightPath)), "redTurtleStandingRight");
			redTurtleWalkingLeft = new MyImage(ImageIO.read(new File(redTurtleWalkingLeftPath)), "redTurtleWalkingLeft");
			redTurtleWalkingRight = new MyImage(ImageIO.read(new File(redTurtleWalkingRightPath)), "redTurtleWalkingRight");

			leftBulletBill = new MyImage(ImageIO.read(new File(leftBulletBillPath)), "leftBulletBill");
			rightBulletBill = new MyImage(ImageIO.read(new File(rightBulletBillPath)), "rightBulletBill");
			billBlasterTop = new MyImage(ImageIO.read(new File(billBlasterTopPath)), "billBlasterTop");
			billBlasterMiddle = new MyImage(ImageIO.read(new File(billBlasterMiddlePath)), "billBlasterMiddle");
			billBlasterBottom = new MyImage(ImageIO.read(new File(billBlasterBottomPath)), "billBlasterBottom");

			goombaLeft = new MyImage(ImageIO.read(new File(goombaLeftPath)), "goombaLeft");
			goombaRight = new MyImage(ImageIO.read(new File(goombaRightPath)), "goombaRight");
			goombaSquished = new MyImage(ImageIO.read(new File(goombaSquishedPath)), "goombaSquished");


			coin1 = new MyImage(ImageIO.read(new File(coin1Path)), "coin1");
			coin2 = new MyImage(ImageIO.read(new File(coin2Path)), "coin2");
			coin3 = new MyImage(ImageIO.read(new File(coin3Path)), "coin3");


			timedilatingsmallMarioLeftImage = new MyImage(ImageIO.read(new File(timedilatingsmallMarioLeftImagePath)), "timedilatingsmallMarioLeftImage");
			timedilatingbigLuigiLeftJumpingDownImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiLeftJumpingDownImagePath)), "timedilatingbigLuigiLeftJumpingDownImage");
			timedilatingbigMarioLeftJumpingImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioLeftJumpingImagePath)), "timedilatingbigMarioLeftJumpingImage");
			timedilatingsmallMarioLeftWalkingImage = new MyImage(ImageIO.read(new File(timedilatingsmallMarioLeftWalkingImagePath)), "timedilatingsmallMarioLeftWalkingImage");
			timedilatingbigLuigiLeftImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiLeftImagePath)), "timedilatingbigLuigiLeftImage");
			timedilatingsmallLuigiLRightJumpingImage = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiLRightJumpingImagePath)), "timedilatingsmallLuigiLRightJumpingImage");
			timedilatingbigLuigiRightJumpingImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiRightJumpingImagePath)), "timedilatingbigLuigiRightJumpingImage");
			timedilatingbigMarioRightWalkingImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioRightWalkingImagePath)), "timedilatingbigMarioRightWalkingImage");
			timedilatingsmallLuigiRightWalkingImage = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiRightWalkingImagePath)), "timedilatingsmallLuigiRightWalkingImage");
			timedilatingbigLuigiLeftJumpingImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiLeftJumpingImagePath)), "timedilatingbigLuigiLeftJumpingImage");
			timedilatingsmallMarioRightJumpingImage = new MyImage(ImageIO.read(new File(timedilatingsmallMarioRightJumpingImagePath)), "timedilatingsmallMarioRightJumpingImage");
			timedilatingsmallMarioRightImage = new MyImage(ImageIO.read(new File(timedilatingsmallMarioRightImagePath)), "timedilatingsmallMarioRightImage");
			timedilatingbigMarioRightCrouchingImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioRightCrouchingImagePath)), "timedilatingbigMarioRightCrouchingImage");
			timedilatingsmallLuigiLeftWalkingImage = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiLeftWalkingImagePath)), "timedilatingsmallLuigiLeftWalkingImage");
			timedilatingbigLuigiRightCrouchingImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiRightCrouchingImagePath)), "timedilatingbigLuigiRightCrouchingImage");
			timedilatingbigLuigiRightImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiRightImagePath)), "timedilatingbigLuigiRightImage");
			timedilatingbigMarioLeftWalkingImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioLeftWalkingImagePath)), "timedilatingbigMarioLeftWalkingImage");
			timedilatingsmallLuigiLeftImage = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiLeftImagePath)), "timedilatingsmallLuigiLeftImage");
			timedilatingsmallLuigiPipe = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiPipePath)), "timedilatingsmallLuigiPipe");
			timedilatingbigMarioPipe = new MyImage(ImageIO.read(new File(timedilatingbigMarioPipePath)), "timedilatingbigMarioPipe");
			timedilatingbigMarioLeftImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioLeftImagePath)), "timedilatingbigMarioLeftImage");
			timedilatingsmallMarioLeftJumpingImage = new MyImage(ImageIO.read(new File(timedilatingsmallMarioLeftJumpingImagePath)), "timedilatingsmallMarioLeftJumpingImage");
			timedilatingbigMarioLeftJumpingDownImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioLeftJumpingDownImagePath)), "timedilatingbigMarioLeftJumpingDownImage");
			timedilatingsmallMarioRightWalkingImage = new MyImage(ImageIO.read(new File(timedilatingsmallMarioRightWalkingImagePath)), "timedilatingsmallMarioRightWalkingImage");
			timedilatingbigLuigiLeftWalkingImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiLeftWalkingImagePath)), "timedilatingbigLuigiLeftWalkingImage");
			timedilatingbigMarioRightJumpingImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioRightJumpingImagePath)), "timedilatingbigMarioRightJumpingImage");
			timedilatingbigMarioRightImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioRightImagePath)), "timedilatingbigMarioRightImage");
			timedilatingbigLuigiRightWalkingImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiRightWalkingImagePath)), "timedilatingbigLuigiRightWalkingImage");
			timedilatingsmallMarioPipe = new MyImage(ImageIO.read(new File(timedilatingsmallMarioPipePath)), "timedilatingsmallMarioPipe");
			timedilatingbigLuigiLeftCrouchingImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiLeftCrouchingImagePath)), "timedilatingbigLuigiLeftCrouchingImage");
			timedilatingbigMarioLeftCrouchingImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioLeftCrouchingImagePath)), "timedilatingbigMarioLeftCrouchingImage");
			timedilatingbigLuigiPipe = new MyImage(ImageIO.read(new File(timedilatingbigLuigiPipePath)), "timedilatingbigLuigiPipe");
			timedilatingsmallLuigiRightImage = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiRightImagePath)), "timedilatingsmallLuigiRightImage");
			timedilatingbigMarioRightJumpingDownImage = new MyImage(ImageIO.read(new File(timedilatingbigMarioRightJumpingDownImagePath)), "timedilatingbigMarioRightJumpingDownImage");
			timedilatingsmallLuigiLeftJumpingImage = new MyImage(ImageIO.read(new File(timedilatingsmallLuigiLeftJumpingImagePath)), "timedilatingsmallLuigiLeftJumpingImage");
			timedilatingbigLuigiRightJumpingDownImage = new MyImage(ImageIO.read(new File(timedilatingbigLuigiRightJumpingDownImagePath)), "timedilatingbigLuigiRightJumpingDownImage");

			timeDilationPowerup = new MyImage(ImageIO.read(new File(timeDilationPowerupPath)), "timeDilationPowerup");
			
			
			
			
			greenMushroomPlatformLeft = new MyImage(ImageIO.read(new File(greenMushroomPlatformLeftPath)), "greenMushroomPlatformLeft");
			redMushroomPlatformLeft = new MyImage(ImageIO.read(new File(redMushroomPlatformLeftPath)), "redMushroomPlatformLeft");
			greenMushroomPlatformMiddle = new MyImage(ImageIO.read(new File(greenMushroomPlatformMiddlePath)), "greenMushroomPlatformMiddle");
			yellowMushroomPlatformMiddle = new MyImage(ImageIO.read(new File(yellowMushroomPlatformMiddlePath)), "yellowMushroomPlatformMiddle");
			redMushroomPlatformRight = new MyImage(ImageIO.read(new File(redMushroomPlatformRightPath)), "redMushroomPlatformRight");
			mushroomPlatformBottom = new MyImage(ImageIO.read(new File(mushroomPlatformBottomPath)), "mushroomPlatformBottom");
			yellowMushroomPlatformLeft = new MyImage(ImageIO.read(new File(yellowMushroomPlatformLeftPath)), "yellowMushroomPlatformLeft");
			yellowMushroomPlatformRight = new MyImage(ImageIO.read(new File(yellowMushroomPlatformRightPath)), "yellowMushroomPlatformRight");
			greenMushroomPlatformRight = new MyImage(ImageIO.read(new File(greenMushroomPlatformRightPath)), "greenMushroomPlatformRight");
			redMushroomPlatformMiddle = new MyImage(ImageIO.read(new File(redMushroomPlatformMiddlePath)), "redMushroomPlatformMiddle");
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("IMAGES SUCCESSFULLY LOADED!");
		ThreadSafeGImage.initializeIDGenerator();




		GameStatsController.setPauses(10);//10 works well

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
				bigMarioTail1, bigMarioLeftTail2,
				bigMarioRightTail2, bigMarioTail3,


				leftTanookiMario, rightTanookiMario, leftTanookiMarioWalking, rightTanookiMarioWalking,
				leftTanookiMarioJumpingUp, rightTanookiMarioJumpingUp, rightTanookiMarioJumpingDown,
				leftTanookiMarioJumpingDown, leftTanookiMarioCrouching, rightTanookiMarioCrouching,
				leftTanookiMarioJumpTail1, rightTanookiMarioJumpTail1, leftTanookiMarioJumpTail2,
				rightTanookiMarioJumpTail1Image,
				tanookiMarioTail1, leftTanookiMarioTail2,
				rightTanookiMarioTail2, tanookiTail3,


				smallMarioPipe, bigMarioPipe, fireMarioPipe, 

				timedilatingsmallMarioLeftImage,
				timedilatingbigMarioLeftJumpingImage,
				timedilatingsmallMarioLeftWalkingImage,
				timedilatingbigMarioRightWalkingImage,
				timedilatingsmallMarioRightJumpingImage,
				timedilatingsmallMarioRightImage,
				timedilatingbigMarioRightCrouchingImage,
				timedilatingbigMarioLeftWalkingImage,
				timedilatingbigMarioPipe,
				timedilatingbigMarioLeftImage,
				timedilatingsmallMarioLeftJumpingImage,
				timedilatingbigMarioLeftJumpingDownImage,
				timedilatingsmallMarioRightWalkingImage,
				timedilatingbigMarioRightJumpingImage,
				timedilatingbigMarioRightImage,
				timedilatingsmallMarioPipe,
				timedilatingbigMarioLeftCrouchingImage,
				timedilatingbigMarioRightJumpingDownImage,



				Mario.CHARACTER.MARIO
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


				catLuigiLeftImage, catLuigiRightImage, catLuigiLeftWalkingImage, catLuigiRightWalkingImage,
				catLuigiLeftJumpingImage, catLuigiRightJumpingImage, catLuigiRightJumpingDownImage,
				catLuigiLeftJumpingDownImage, catLuigiLeftCrouchingImage, catLuigiRightCrouchingImage,
				catLuigiLeftJumpingTail1Image, catLuigiRightJumpingTail1Image, catLuigiLeftJumpingTail2Image,
				catLuigiRightJumpingTail2Image,
				catLuigiPipeImage, catLuigiLeftTail2Image,
				catLuigiRightTail2Image, catLuigiTail3,



				leftTanookiMario, rightTanookiMario, leftTanookiMarioWalking, rightTanookiMarioWalking,
				leftTanookiMarioJumpingUp, rightTanookiMarioJumpingUp, rightTanookiMarioJumpingDown,
				leftTanookiMarioJumpingDown, leftTanookiMarioCrouching, rightTanookiMarioCrouching,
				leftTanookiMarioJumpTail1, rightTanookiMarioJumpTail1, leftTanookiMarioJumpTail2,
				rightTanookiMarioJumpTail1Image,
				tanookiMarioTail1, leftTanookiMarioTail2,
				rightTanookiMarioTail2, tanookiTail3,

				smallLuigiPipe, bigLuigiPipe, fireMarioPipe,

				timedilatingsmallLuigiLeftImage,
				timedilatingbigLuigiLeftJumpingImage,
				timedilatingsmallLuigiLeftWalkingImage,
				timedilatingbigLuigiRightWalkingImage,
				timedilatingsmallLuigiLRightJumpingImage,
				timedilatingsmallLuigiRightImage,
				timedilatingbigLuigiRightCrouchingImage,
				timedilatingbigLuigiLeftWalkingImage,
				timedilatingbigLuigiPipe,
				timedilatingbigLuigiLeftImage,
				timedilatingsmallLuigiLeftJumpingImage,
				timedilatingbigLuigiLeftJumpingDownImage,
				timedilatingsmallLuigiRightWalkingImage,
				timedilatingbigLuigiRightJumpingImage,
				timedilatingbigLuigiRightImage,
				timedilatingsmallLuigiPipe,
				timedilatingbigLuigiLeftCrouchingImage,
				timedilatingbigLuigiRightJumpingDownImage,

				Mario.CHARACTER.LUIGI);
		int numCharacters = 2;//number of players in game. could add toad peach etc for more characters (all playing at the same time in same level!)
		Mario[] characters = new Mario[numCharacters];
		characters[0] = luigi;
		characters[1] = mario;

		GameStatsController.setCharacters(mario, luigi);

		int fallDy = (int) (smallMarioLeftImage.getHeight(canvas)/(10.0));
		GameStatsController.setMarioFallDy(fallDy);
		GameStatsController.setMarioMoveDx(1.5*fallDy);
		GameStatsController.setLuigiFallDy(fallDy);
		GameStatsController.setLuigiMoveDx(1.5*fallDy);

		GameStatsController.setMovingObjectBaseLineXSpeed(1.5*fallDy);









		CharacterStatsController.initializeStats(characters);
		MovingObject.setObjects(canvas, characters);
		Coin.setObjects(coin1, coin2, coin3, canvas);
		Goomba.setObjects(goombaRight, goombaLeft, goombaSquished);
		BulletBill.setObjects(leftBulletBill, rightBulletBill);
		RedTurtle.setObjects(redTurtleSpinning1, redTurtleSpinning2, 
				redTurtleSpinning3, redTurtleSpinning4, redTurtleStandingLeft,
				redTurtleStandingRight, redTurtleWalkingLeft, redTurtleWalkingRight);
		UpShootingFlower.setObjects(shootingFlowerLeftDownClosed, shootingFlowerLeftDownOpen,
				shootingFlowerLeftUpClosed, shootingFlowerLeftUpOpen,
				shootingFlowerRightDownClosed, shootingFlowerRightDownOpen,
				shootingFlowerRightUpClosed, shootingFlowerRightUpOpen);
		DownShootingFlower.setObjects(DOWNshootingFlowerLeftDownClosed, DOWNshootingFlowerLeftDownOpen,
				DOWNshootingFlowerLeftUpClosed, DOWNshootingFlowerLeftUpOpen,
				DOWNshootingFlowerRightDownClosed, DOWNshootingFlowerRightDownOpen,
				DOWNshootingFlowerRightUpClosed, DOWNshootingFlowerRightUpOpen);
		MysteryBox.setObjects(mysteryBox1, mysteryBox2, mysteryBox3, mysteryBox4, mysteryBoxFinal);
		Tanooki.setObjects(tanookiPowerUp);
		Mushroom.setObjects(mushroomImage);
		FireBall.setObjects(leftFireBall1, rightFireBall1,leftFireBall2,
				rightFireBall2, leftFireBall3,
				rightFireBall3, leftFireBall4,
				rightFireBall4);
		FireFlower.setObjects(fireFlowerImage);
		Leaf.setObjects(rightLeafImage, leftLeafImage);
		Hourglass.setObject(timeDilationPowerup);
		DynamicFactory.setCanvas(canvas);
		StaticFactory.setObjects(grassLeftTopImage,grassRightTopImage, grassMidleTopImage, grassLeftImage, 
				grassRightImage,grassMiddleImage, 
				pipeUpTopLeft, pipeUpTopRight, pipeDownMiddleLeft, pipeDownMiddleRight,
				pipeDownTopLeft, pipeDownTopRight, pipeUpMiddleLeft, 
				pipeUpMiddleRight, billBlasterMiddle, billBlasterBottom,
				greenMushroomPlatformLeft,
				redMushroomPlatformLeft,
				greenMushroomPlatformMiddle,
				yellowMushroomPlatformMiddle,
				redMushroomPlatformRight,
				mushroomPlatformBottom,
				yellowMushroomPlatformLeft,
				yellowMushroomPlatformRight,
				greenMushroomPlatformRight,
				redMushroomPlatformMiddle,
				canvas);
		BillBlasterTop.setImage(billBlasterTop);
		LevelController.setObjects(canvas);
		BillBlasterController.setCanvas(canvas);
		if (!runningOnTomcatServer) canvas.addKeyListener(new MyKeyListener(characters));
		else VirtualClientKeyboard.setCharacters(characters);


		//mario.setToFire();
		LevelController.playLevel("5");

		//LevelController.playLevel2();


		//canvas.add(mario, 10, 10);
		//ServerToClientMessenger.sendAddImageToScreenMessage(mario);



		




	}
}
