

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import acm.graphics.GCanvas;
import acm.graphics.GImage;

public class StaticFactory {
	//adds images to staticLevelParts (adds platforms to staticLevelParts)
	private Lobby lobby;
	private static MyImage grassLeftTopImage, grassRightTopImage, 
	grassMiddleTopImage, grassLeftImage, grassRightImage, grassMiddleImage,
	pipeUpTopLeftImage, pipeUpTopRightImage, pipeUpMiddleLeftImage, pipeUpMiddleRightImage,
	pipeDownMiddleLeftImage, pipeDownMiddleRightImage,
	pipeDownTopLeftImage, pipeDownTopRightImage,// billBlasterTopImage,
	billBlasterMiddleImage, billBlasterBottomImage,
	greenMushroomPlatformLeft,
	redMushroomPlatformLeft,
	greenMushroomPlatformMiddle,
	yellowMushroomPlatformMiddle,
	redMushroomPlatformRight,
	mushroomPlatformBottom,
	yellowMushroomPlatformLeft,
	yellowMushroomPlatformRight,
	greenMushroomPlatformRight,
	redMushroomPlatformMiddle;

	private static boolean imagesSet = false; // so setObjects is not called everytime a new lobby starts a game

	public StaticFactory(Lobby lobby) {
		this.lobby = lobby;
	}

	public static boolean imagesSet() {
		return imagesSet;
	}

	public static void setObjects(MyImage grassLeftTopImage1, MyImage grassRightTopImage1, MyImage grassMiddleTopImage1, 
			MyImage grassLeftImage1, MyImage grassRightImage1, MyImage grassMiddleImage1,
			MyImage pipeUpTopLeftImage1, MyImage pipeUpTopRightImage1, MyImage pipeDownMiddleLeftImage1, MyImage pipeDownMiddleRightImage1,
			MyImage pipeDownTopLeftImage1, MyImage pipeDownTopRightImage1, MyImage pipeUpMiddleLeftImage1, MyImage pipeUpMiddleRightImage1,
			//MyImage billBlasterTopImage1, 
			MyImage billBlasterMiddleImage1,
			MyImage billBlasterBottomImage1,
			MyImage greenMushroomPlatformLeft1,
			MyImage redMushroomPlatformLeft1,
			MyImage greenMushroomPlatformMiddle1,
			MyImage yellowMushroomPlatformMiddle1,
			MyImage redMushroomPlatformRight1,
			MyImage mushroomPlatformBottom1,
			MyImage yellowMushroomPlatformLeft1,
			MyImage yellowMushroomPlatformRight1,
			MyImage greenMushroomPlatformRight1,
			MyImage redMushroomPlatformMiddle1)

	{

		//MyGCanvas canvas1) {
		imagesSet = true;
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
		//billBlasterTopImage = billBlasterTopImage1; 
		billBlasterMiddleImage = billBlasterMiddleImage1; 
		billBlasterBottomImage = billBlasterBottomImage1;


		greenMushroomPlatformLeft = greenMushroomPlatformLeft1; 
		redMushroomPlatformLeft = redMushroomPlatformLeft1; 
		greenMushroomPlatformMiddle = greenMushroomPlatformMiddle1;
		yellowMushroomPlatformMiddle = yellowMushroomPlatformMiddle1;
		redMushroomPlatformRight = redMushroomPlatformRight1;
		mushroomPlatformBottom = mushroomPlatformBottom1;
		yellowMushroomPlatformLeft = yellowMushroomPlatformLeft1;
		yellowMushroomPlatformRight = yellowMushroomPlatformRight1;
		greenMushroomPlatformRight = greenMushroomPlatformRight1;
		redMushroomPlatformMiddle = redMushroomPlatformMiddle1;

		//canvas = canvas1;
	}





	/***
	 * mushroomplatform is trampoline-like platform 
	 * @param xCounter
	 * @param h: h>=1, h is num of mushroomPlatformBottom
	 * @param w: w>=1, w is num of mushroomPlatformMiddle
	 * @param type: red, yellow, or green mushroom platform
	 * @param staticLevelParts
	 * @return width of mushroom platform
	 */
	public double spawnMushroomPlatform(XCounter xCounter, double w, double h, LevelController.MUSHROOM_PLATFORM_TYPE type, ArrayList<StaticLevelPart> staticLevelParts) {
		if (h<1) h=1;
		if (w<1) w=1;
		MyImage mushroomLeft, mushroomMiddle, mushroomRight;
		if (type==LevelController.MUSHROOM_PLATFORM_TYPE.GREEN) {
			mushroomLeft = greenMushroomPlatformLeft;
			mushroomMiddle = greenMushroomPlatformMiddle;
			mushroomRight = greenMushroomPlatformRight;
		} else if (type==LevelController.MUSHROOM_PLATFORM_TYPE.RED) {
			mushroomLeft = redMushroomPlatformLeft;
			mushroomMiddle = redMushroomPlatformMiddle;
			mushroomRight = redMushroomPlatformRight;
		} else {//must be yellow
			mushroomLeft = yellowMushroomPlatformLeft;
			mushroomMiddle = yellowMushroomPlatformMiddle;
			mushroomRight = yellowMushroomPlatformRight;
		}

		ArrayList<Platform> platforms = new ArrayList<Platform>();
		double width = 0.0;
		Platform g1 = new Platform(mushroomPlatformBottom);


		BouncyPlatform g3 = new BouncyPlatform(mushroomLeft);
		lobby.canvas.add(g3, xCounter.v, MarioBrosGame.HEIGHT-h*g1.getHeight()-g3.getHeight());
		platforms.add(g3);
		width+=g3.getWidth();

		for (int i=0; i<w;i++) {
			BouncyPlatform g4 = new BouncyPlatform(mushroomMiddle);
			lobby.canvas.add(g4, xCounter.v+g3.getWidth()+i*g4.getWidth(), MarioBrosGame.HEIGHT-h*g1.getHeight()-g4.getHeight());
			platforms.add(g4);
			width+=g4.getWidth();
		}


		BouncyPlatform g5 = new BouncyPlatform(mushroomRight);
		lobby.canvas.add(g5, xCounter.v+width, MarioBrosGame.HEIGHT-h*g1.getHeight()-g5.getHeight());
		platforms.add(g5);
		width+=g5.getWidth();

		////////
		double middlePlatformX = width/2-g1.getWidth()/2;
		lobby.canvas.add(g1, xCounter.v+middlePlatformX, MarioBrosGame.HEIGHT-g1.getHeight());
		platforms.add(g1);


		for (int i=2; i<=h; i++) {
			g1 = new Platform(mushroomPlatformBottom);
			lobby.canvas.add(g1, xCounter.v+middlePlatformX, MarioBrosGame.HEIGHT-i*g1.getHeight());
			platforms.add(g1);
		}




		xCounter.v += width;
		staticLevelParts.add(new StaticLevelPart(platforms));
		return width;
	}




	//bill blaster is cannon that shoots bullets (BulletBill)
	//h>3
	public double spawnBillBlaster(XCounter xCounter, double h, ArrayList<StaticLevelPart> staticLevelParts) {
		if (h<3) h=3;
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		for (int i=1; i<=h-2; i++) {
			Platform g1 = new Platform(billBlasterBottomImage);
			lobby.canvas.add(g1, xCounter.v, MarioBrosGame.HEIGHT-i*g1.getHeight());
			platforms.add(g1);
		}
		Platform g2 = new Platform(billBlasterMiddleImage);
		lobby.canvas.add(g2, xCounter.v, MarioBrosGame.HEIGHT-(h-1)*g2.getHeight());
		platforms.add(g2);

		BillBlasterTop billBlasterTop = new BillBlasterTop();//new Platform(billBlasterTopImage);
		lobby.canvas.add(billBlasterTop, xCounter.v, MarioBrosGame.HEIGHT-h*g2.getHeight());
		platforms.add(billBlasterTop);

		double width = billBlasterTop.getWidth();
		xCounter.v += width;
		staticLevelParts.add(new StaticLevelPart(platforms));
		return width;
	}


	/***
	 * This function spawns a grass mountain with given location and size (adds all images to canvas)
	 * Adds LevelPart to staticLevelParts and increments xCounter.v (leftmost available place for a new LevelPart)
	 * xCounter.v is x coordinate of top left Image
	 * @param w width in number of images from left to right (expected >=3)
	 * @param h height in number of images from top to bottom (expected >=2)

	 */
	public double spawnGrassMountain(XCounter xCounter, double w, double h, LevelController.BADGUY_TYPE type, ArrayList<StaticLevelPart> staticLevelParts, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		if (w<3) w=3;
		if (h<2) h=2;
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		for (int i = 1; i<w-1; i++) {
			for (int j = 1; j<h; j++) {
				Platform g1 = new Platform(grassMiddleImage);
				lobby.canvas.add(g1, xCounter.v+i*g1.getWidth(), MarioBrosGame.HEIGHT-j*g1.getHeight());
				platforms.add(g1);
			}
		}
		for (int i = 1; i<w-1; i++) {	
			Platform g1 = new Platform(grassMiddleTopImage);
			lobby.canvas.add(g1, xCounter.v+i*g1.getWidth(), MarioBrosGame.HEIGHT-h*g1.getHeight());
			platforms.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassLeftImage);
			lobby.canvas.add(g1, xCounter.v, MarioBrosGame.HEIGHT-j*g1.getHeight());
			platforms.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassRightImage);
			lobby.canvas.add(g1, xCounter.v+(w-1)*g1.getWidth(), MarioBrosGame.HEIGHT-j*g1.getHeight());
			platforms.add(g1);
		}
		Platform g1 = new Platform(grassRightTopImage);
		lobby.canvas.add(g1, xCounter.v+(w-1)*g1.getWidth(), MarioBrosGame.HEIGHT-h*g1.getHeight());
		platforms.add(g1);
		Platform g2 = new Platform(grassLeftTopImage);
		lobby.canvas.add(g2, xCounter.v, MarioBrosGame.HEIGHT-h*g1.getHeight());
		platforms.add(g2);
		//double height = g1.getHeight();
		double width = w*g1.getWidth(); 

		if (type == LevelController.BADGUY_TYPE.RED_TURTLE) {
			lobby.dFactory.addRedTurtle(xCounter.v, MarioBrosGame.HEIGHT-g1.getHeight()*h, width, dynamicLevelParts);
		} else if (type == LevelController.BADGUY_TYPE.GREEN_TURTLE) {
			lobby.dFactory.addGreenTurtle(xCounter.v, MarioBrosGame.HEIGHT-g1.getHeight()*h, dynamicLevelParts);
		} else if (type == LevelController.BADGUY_TYPE.GOOMBA) {
			lobby.dFactory.addGoomba(xCounter.v+width/3, MarioBrosGame.HEIGHT-g1.getHeight()*h, dynamicLevelParts);
		}
		xCounter.v += width;
		staticLevelParts.add(new StaticLevelPart(platforms));
		return width;
	}

	/***
	 * This function spawns a pipe facing up (mario can go down into) from the bottom of the screen
	 * Adds LevelPart to staticLevelParts and increments xCounter.v (leftmost available place for a new LevelPart)
	 * xCounter.v is x coordinate of top left Image
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @param flowerType type of flower coming in/out of pipe (could be NO_FLOWER)
	 * @param timeOffset to coordinate some flowers to come in/out at the same time (if they have the same timeOffset)
	 * @param subLevelID is non empty if pipe allows mario to go into it and transport him
	 * @param staticLevelParts to add every image to the currLevel
	 * @return width of pipe
	 */
	public double spawnUpPipe(XCounter xCounter, double h, LevelController.FLOWER_TYPE flowerType, int timeOffset, String subLevelID, ArrayList<StaticLevelPart> staticLevelParts, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		//TODO FIX BUG WHERE UP PIPE DOESNT RETURN CORRECT WIDTH, xCounter.v FOR LEVEL IS INCREMENTED PROPERLY
		//SO MARIO CAN WALK PAST THE END OF THE LEVEL
		//loophope around this problem: dont do xCounter.v += spawnUpPipe
		//do : spawnUpPipe(); xCounter.v+=200;
		if (h<2) h=2;
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		for (int i=1; i<h; i++) {
			Platform middleLeft = new Platform(pipeUpMiddleLeftImage);
			lobby.canvas.add(middleLeft, xCounter.v, MarioBrosGame.HEIGHT-middleLeft.getHeight()*i);
			platforms.add(middleLeft);
			Platform middleRight = new Platform(pipeUpMiddleRightImage);
			lobby.canvas.add(middleRight, xCounter.v+middleLeft.getWidth(), MarioBrosGame.HEIGHT-middleRight.getHeight()*i);
			platforms.add(middleRight);
		}

		Platform topLeft = subLevelID.length()>0?new LeftPipePart(pipeUpTopLeftImage, subLevelID, true):new Platform(pipeUpTopLeftImage);
		Platform topRight = subLevelID.length()>0?new RightPipePart(pipeUpTopRightImage, subLevelID, true):new Platform(pipeUpTopRightImage);

		Platform middleRight = new Platform(pipeUpMiddleRightImage);
		double dx = topLeft.getWidth()-middleRight.getWidth();
		//dx is to offset the top part so it sits in middle of pipe
		lobby.canvas.add(topLeft, xCounter.v-dx, MarioBrosGame.HEIGHT-h*topLeft.getHeight());
		platforms.add(topLeft);
		lobby.canvas.add(topRight, xCounter.v-dx+topLeft.getWidth(), MarioBrosGame.HEIGHT-h*topRight.getHeight());
		platforms.add(topRight);

		if (flowerType == LevelController.FLOWER_TYPE.SHOOTING) {
			lobby.dFactory.addUpShootingFlower(topLeft.getX()+topLeft.getWidth(), topLeft.getY(), timeOffset, dynamicLevelParts);
		}

		staticLevelParts.add(new StaticLevelPart(platforms));
		double width = 2.0*middleRight.getWidth(); 
		xCounter.v += width;//width is always 2 images
		return width;
	}


	/***
	 * This function spawns a pipe facing down (mario can jump into) from the top of the screen
	 * Adds LevelPart to staticLevelParts and increments xCounter.v (leftmost available place for a new LevelPart)
	 * xCounter.v is x coordinate of top left Image
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @param flowerType type of flower coming in/out of pipe (could be NO_FLOWER)
	 * @param timeOffset to coordinate some flowers to come in/out at the same time (if they have the same timeOffset)
	 * @param subLevelID is non empty if pipe allows mario to go into it and transport him
	 * @param staticLevelParts to add every image to the currLevel
	 * @return width of pipe
	 */
	public double spawnDownPipe(XCounter xCounter, double h, LevelController.FLOWER_TYPE flowerType, int timeOffset, String subLevelID, ArrayList<StaticLevelPart> staticLevelParts, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		if (h<2) h=2;
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		for (int i=0; i<h-1; i++) {
			Platform middleLeft = new Platform(pipeDownMiddleLeftImage);
			lobby.canvas.add(middleLeft, xCounter.v, middleLeft.getHeight()*i);
			platforms.add(middleLeft);
			Platform middleRight = new Platform(pipeDownMiddleRightImage);
			lobby.canvas.add(middleRight, xCounter.v+middleLeft.getWidth(), middleRight.getHeight()*i);
			platforms.add(middleRight);
		}

		Platform topLeft = subLevelID.length()>0?new LeftPipePart(pipeDownTopLeftImage, subLevelID, false):new Platform(pipeDownTopLeftImage);
		Platform topRight = subLevelID.length()>0?new RightPipePart(pipeDownTopRightImage, subLevelID, false):new Platform(pipeDownTopRightImage);

		Platform middleRight = new Platform(pipeDownMiddleRightImage);
		double dx = topLeft.getWidth()-middleRight.getWidth();
		//dx is to offset the top part so it sits in middle of pipe
		lobby.canvas.add(topLeft, xCounter.v-dx, (h-1)*topLeft.getHeight());
		platforms.add(topLeft);
		lobby.canvas.add(topRight, xCounter.v-dx+topLeft.getWidth(), (h-1)*topRight.getHeight());
		platforms.add(topRight);

		if (flowerType == LevelController.FLOWER_TYPE.SHOOTING) {
			lobby.dFactory.addDownShootingFlower(topLeft.getX()+topLeft.getWidth(), topLeft.getY()+topLeft.getHeight(), timeOffset, dynamicLevelParts);
		}

		staticLevelParts.add(new StaticLevelPart(platforms));
		double width = 2.0*topLeft.getWidth();
		xCounter.v += width;//width is always 2 images
		return width;
	}

	//this func does not increase xCounter.v by the width of the mysterybox
	//since usually mysterybox is on top of platform (like grass mountain)
	//POWERUP PARAM SO A MYSTERY BOX OF A CERTAIN LEVEL ALWAYS OUTPUTS SAME POWERUP (or random)
	//y param in units of mysterybox height
	public double spawnMysteryBox(double x, double y, ArrayList<StaticLevelPart> staticLevelParts, MysteryBox.SPAWN s) {
		ArrayList<Platform> platforms = new ArrayList<Platform>();
		MysteryBox b = new MysteryBox(s, lobby);
		lobby.canvas.add(b, x, MarioBrosGame.HEIGHT-y*b.getHeight());
		platforms.add(b);
		staticLevelParts.add(new StaticLevelPart(platforms));
		return b.getWidth();
	}

	//spawns a down pipe on top of an up pipe, xCounter.v is modified only once
	public void spawnUpAndDownPipes(XCounter xCounter, double hUp, String subLevelIDUp, LevelController.FLOWER_TYPE tUp,  double hDown, String subLevelIDDown, LevelController.FLOWER_TYPE tDown, ArrayList<StaticLevelPart> staticLevelParts,
			HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		double width = spawnDownPipe(xCounter, hDown, tDown, 0, subLevelIDDown, staticLevelParts, dynamicLevelParts);
		xCounter.v -= width;//so that down pipe is on top of up pipe
		spawnUpPipe(xCounter, hUp, tUp, 0, subLevelIDUp, staticLevelParts, dynamicLevelParts);
	}
}
