import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GImage;

public class LevelController {
	//spawns a level and sets currLevel so mario can have access to it and move
	//level depending on how close to the edges of the screen he is
	private static Image grassLeftTopImage, grassRightTopImage, 
	grassMiddleTopImage, grassLeftImage, grassRightImage, grassMiddleImage,
	pipeUpTopLeftImage, pipeUpTopRightImage, pipeUpMiddleLeftImage, pipeUpMiddleRightImage,
	pipeDownMiddleLeftImage, pipeDownMiddleRightImage,
	pipeDownTopLeftImage, pipeDownTopRightImage;
	private static GCanvas canvas;
	public static Level currLevel;//only one currLevel mario is playing at a time
	public static Mario mario;
	public static double space;
	public static double xCounter;//used for adding images to a level and keeping track of the level's width
	public static void setObjects(Image grassLeftTopImage1, Image grassRightTopImage1, Image grassMiddleTopImage1, 
			Image grassLeftImage1, Image grassRightImage1, Image grassMiddleImage1,
			Image pipeUpTopLeftImage1, Image pipeUpTopRightImage1, Image pipeDownMiddleLeftImage1, Image pipeDownMiddleRightImage1,
			Image pipeDownTopLeftImage1, Image pipeDownTopRightImage1, Image pipeUpMiddleLeftImage1, Image pipeUpMiddleRightImage1,
			Mario mario1,
			GCanvas canvas1) {
		grassLeftTopImage = grassLeftTopImage1;
		grassRightTopImage = grassRightTopImage1;
		grassMiddleTopImage = grassMiddleTopImage1;
		grassLeftImage = grassLeftImage1;
		grassRightImage = grassRightImage1;
		grassMiddleImage = grassMiddleImage1;
		pipeUpTopLeftImage = pipeUpTopLeftImage1;
		pipeUpTopRightImage = pipeUpTopRightImage1;
		pipeDownMiddleLeftImage = pipeDownMiddleLeftImage1;
		pipeDownMiddleRightImage = pipeDownMiddleRightImage1;
		pipeDownTopLeftImage = pipeDownTopLeftImage1;
		pipeDownTopRightImage = pipeDownTopRightImage1;
		pipeUpMiddleLeftImage = pipeUpMiddleLeftImage1;
		pipeUpMiddleRightImage = pipeUpMiddleRightImage1;
		mario = mario1;
		canvas = canvas1;
		space = mario.moveDx*10.0;
	}

	public static void endCurrentLevel() {
		//sets all moving objects of currLevel to dead
		System.out.println("Setting all power ups from previous level to DEAD");
		for (LevelPart l : currLevel.levelParts) {
			for (GImage image : l.part) {
				if (image instanceof MovingObject) {
					((MovingObject) image).alive = false;
					//this fixes bug where power up from previous level is removed from canvas
					//but when restarting level when mario walks into it it still affects him
					//or dead fireball to kill turtles etc
					System.out.println("pow`er up/fireball dead");
				}
			}
		}
	}

	public static void restartCurrentLevel() {
		playLevel(currLevel.getID());
	}

	public static void playLevel(String subLevelID) {
		endCurrentLevel();
		canvas.removeAll();
		if (subLevelID.equals("1a")) playLevel1a();
		else if (subLevelID.equals("1")) playLevel1();
		else if (subLevelID.equals("2")) playLevel2();
		else System.out.println("NO SUBLEVEL WITH ID "+subLevelID);
	}


	/***
	 * This function spawns a grass mountain with given location and size (adds all images to canvas)
	 * Adds LevelPart to levelParts and increments xCounter (leftmost available place for a new LevelPart)
	 * XCounter is x coordinate of top left Image
	 * @param w width in number of images from left to right (expected >=3)
	 * @param h height in number of images from top to bottom (expected >=2)

	 */
	public static double spawnGrassMountain(double w, double h, ArrayList<LevelPart> levelParts) {
		ArrayList<GImage> images = new ArrayList<GImage>();
		for (int i = 1; i<w-1; i++) {
			for (int j = 1; j<h; j++) {
				Platform g1 = new Platform(grassMiddleImage);
				canvas.add(g1, xCounter+i*g1.getWidth(), canvas.getHeight()-j*g1.getHeight());
				images.add(g1);
			}

		}
		for (int i = 1; i<w-1; i++) {	
			Platform g1 = new Platform(grassMiddleTopImage);
			canvas.add(g1, xCounter+i*g1.getWidth(), canvas.getHeight()-h*g1.getHeight());
			images.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassLeftImage);
			canvas.add(g1, xCounter, canvas.getHeight()-j*g1.getHeight());
			images.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassRightImage);
			canvas.add(g1, xCounter+(w-1)*g1.getWidth(), canvas.getHeight()-j*g1.getHeight());
			images.add(g1);
		}
		Platform g1 = new Platform(grassRightTopImage);
		canvas.add(g1, xCounter+(w-1)*g1.getWidth(), canvas.getHeight()-h*g1.getHeight());
		images.add(g1);
		Platform g2 = new Platform(grassLeftTopImage);
		canvas.add(g2, xCounter, canvas.getHeight()-h*g1.getHeight());
		images.add(g2);
		//double height = g1.getHeight();
		levelParts.add(new LevelPart(images));
		double width = w*g1.getWidth(); 
		xCounter += width;
		return width;
	}

	/***
	 * This function spawns a pipe facing up (mario can go down into) from the bottom of the screen
	 * Adds LevelPart to levelParts and increments xCounter (leftmost available place for a new LevelPart)
	 * xCounter is x coordinate of top left Image
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @param transportable true if pipe allows mario to go into it and transport him
	 * 
	 */
	public static double spawnUpPipe(double h, boolean transportable, String subLevelID, ArrayList<LevelPart> levelParts) {
		//TODO FIX BUG WHERE UP PIPE DOESNT RETURN CORRECT WIDTH, XCOUNTER FOR LEVEL IS INCREMENTED PROPERLY
		//SO MARIO CAN WALK PAST THE END OF THE LEVEL
		//loophope around this problem: dont do xCounter += spawnUpPipe
		//do : spawnUpPipe(); xCounter+=200;
		if (h<2) h=2;
		ArrayList<GImage> images = new ArrayList<GImage>();	
		for (int i=1; i<h; i++) {
			Platform middleLeft = new Platform(pipeUpMiddleLeftImage);
			canvas.add(middleLeft, xCounter, canvas.getHeight()-middleLeft.getHeight()*i);
			images.add(middleLeft);
			Platform middleRight = new Platform(pipeUpMiddleRightImage);
			canvas.add(middleRight, xCounter+middleLeft.getWidth(), canvas.getHeight()-middleRight.getHeight()*i);
			images.add(middleRight);
		}

		Platform topLeft = transportable?new LeftPipePart(pipeUpTopLeftImage, subLevelID, true):new Platform(pipeUpTopLeftImage);
		Platform topRight = transportable?new RightPipePart(pipeUpTopRightImage, subLevelID, true):new Platform(pipeUpTopRightImage);

		Platform middleRight = new Platform(pipeUpMiddleRightImage);
		double dx = topLeft.getWidth()-middleRight.getWidth();
		//dx is to offset the top part so it sits in middle of pipe
		canvas.add(topLeft, xCounter-dx, canvas.getHeight()-h*topLeft.getHeight());
		images.add(topLeft);
		canvas.add(topRight, xCounter-dx+topLeft.getWidth(), canvas.getHeight()-h*topRight.getHeight());
		images.add(topRight);

		levelParts.add(new LevelPart(images));
		double width = 2.0*middleRight.getWidth(); 
		xCounter += width;//width is always 2 images
		return width;
	}


	/***
	 * This function spawns a pipe facing down (mario can jump into) from the top of the screen
	 * Adds LevelPart to levelParts and increments xCounter (leftmost available place for a new LevelPart)
	 * xCounter is x coordinate of top left Image
	 *
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @param transportable true if pipe allows mario to go into it and transport him
	 */
	public static double spawnDownPipe(double h, boolean transportable, String subLevelID, ArrayList<LevelPart> levelParts) {
		if (h<2) h=2;
		ArrayList<GImage> images = new ArrayList<GImage>();	
		for (int i=0; i<h-1; i++) {
			Platform middleLeft = new Platform(pipeDownMiddleLeftImage);
			canvas.add(middleLeft, xCounter, middleLeft.getHeight()*i);
			images.add(middleLeft);
			Platform middleRight = new Platform(pipeDownMiddleRightImage);
			canvas.add(middleRight, xCounter+middleLeft.getWidth(), middleRight.getHeight()*i);
			images.add(middleRight);
		}

		Platform topLeft = transportable?new LeftPipePart(pipeDownTopLeftImage, subLevelID, false):new Platform(pipeDownTopLeftImage);
		Platform topRight = transportable?new RightPipePart(pipeDownTopRightImage, subLevelID, false):new Platform(pipeDownTopRightImage);

		Platform middleRight = new Platform(pipeDownMiddleRightImage);
		double dx = topLeft.getWidth()-middleRight.getWidth();
		//dx is to offset the top part so it sits in middle of pipe
		canvas.add(topLeft, xCounter-dx, (h-1)*topLeft.getHeight());
		images.add(topLeft);
		canvas.add(topRight, xCounter-dx+topLeft.getWidth(), (h-1)*topRight.getHeight());
		images.add(topRight);

		levelParts.add(new LevelPart(images));
		double width = 2.0*topLeft.getWidth();
		xCounter += width;//width is always 2 images
		return width;
	}

	//this func does not increase xCounter by the width of the mysterybox
	//since usually mysterybox is on top of platform (like grass mountain)
	public static double spawnMysteryBox(double x, double y, ArrayList<LevelPart> levelParts) {
		ArrayList<GImage> images = new ArrayList<GImage>();
		MysteryBox b = new MysteryBox();
		canvas.add(b, x, canvas.getHeight()-y*b.getHeight());
		images.add(b);
		levelParts.add(new LevelPart(images));
		return b.getWidth();
	}

	//this function adds white space to level by increasing xCounter without adding any images
	public static void spawnWhiteSpace(double numSpaces) {
		xCounter += numSpaces*space;
	}
	
	//spawns a down pipe on top of an up pipe, xCounter is modified only once
	public static void spawnUpAndDownPipes(double hUp, String subLevelIDUp, double hDown, String subLevelIDDown, ArrayList<LevelPart> levelParts) {
		double width = spawnUpPipe(hUp, subLevelIDUp.length()>0, subLevelIDUp, levelParts);
		xCounter -= width;//so that down pipe is on top of up pipe
		spawnDownPipe(hDown, subLevelIDDown.length()>0, subLevelIDDown, levelParts);
	}
	
	public static void playLevel1() {
		//adds each GImage for each LevelPart to the canvas at their starting positions
		//adds LevelParts from left to right so helpful to have xCounter to keep track of
		//smallest left index where a new LevelPart could be spawned
		//to have white space in between level parts need to increment xCounter by width of whitespace
		//at the end of function XCounter will be the width of the level

		canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		xCounter = 0.0;//need to re initialize xCounter to 0 at the beginning of each level
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		spawnGrassMountain(8, 2, levelParts);
		spawnWhiteSpace(3);
		spawnMysteryBox(xCounter+2.0*space, 6, levelParts);
		spawnGrassMountain(4, 3, levelParts);
		spawnWhiteSpace(2);
		spawnUpPipe(4, true, "1a", levelParts);
		spawnDownPipe(3, true, "2", levelParts);
		spawnWhiteSpace(2);
		spawnGrassMountain(8, 2, levelParts);
		spawnMysteryBox(xCounter-4.0*space, 6, levelParts);
		spawnMysteryBox(xCounter-2.0*space, 6, levelParts);
		spawnWhiteSpace(2);
		Level level1 = new Level("1", levelParts, xCounter);
		currLevel = level1;//set currLevel
		mario.fall(5);
	}

	public static void playLevel2() {
		canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		xCounter = 0.0;
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		spawnGrassMountain(20, 2, levelParts);
		spawnMysteryBox(2.0*space, 5, levelParts);
		spawnMysteryBox(4.0*space, 5, levelParts);
		spawnMysteryBox(6.0*space, 5, levelParts);
		spawnMysteryBox(8.0*space, 5, levelParts);
		spawnUpAndDownPipes(5, "1", 3, "2", levelParts);
		Level level2 = new Level("2", levelParts, xCounter);
		currLevel = level2;//set currLevel
		mario.fall(5);
	}

	public static void playLevel1a() {
		canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		xCounter = 0.0;
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		spawnGrassMountain(20, 3, levelParts);
		spawnUpPipe(2, true, "1a", levelParts);
		spawnWhiteSpace(3);
		spawnUpAndDownPipes(4, "1", 3, "2", levelParts);
		Level level1a = new Level("1a", levelParts, xCounter);
		currLevel = level1a;//set currLevel
		mario.fall(5);
	}
}
