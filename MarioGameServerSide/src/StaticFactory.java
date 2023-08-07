import java.awt.Image;
import java.util.ArrayList;


import acm.graphics.GCanvas;
import acm.graphics.GImage;

public class StaticFactory {
	//adds images to levelParts (adds platforms and ShootingFlower, RedTurtle etc to levelParts)
	private static MyGCanvas canvas;
	private static MyImage grassLeftTopImage, grassRightTopImage, 
	grassMiddleTopImage, grassLeftImage, grassRightImage, grassMiddleImage,
	pipeUpTopLeftImage, pipeUpTopRightImage, pipeUpMiddleLeftImage, pipeUpMiddleRightImage,
	pipeDownMiddleLeftImage, pipeDownMiddleRightImage,
	pipeDownTopLeftImage, pipeDownTopRightImage, billBlasterTopImage,
	billBlasterMiddleImage, billBlasterBottomImage;
	
	public static void setObjects(MyImage grassLeftTopImage1, MyImage grassRightTopImage1, MyImage grassMiddleTopImage1, 
			MyImage grassLeftImage1, MyImage grassRightImage1, MyImage grassMiddleImage1,
			MyImage pipeUpTopLeftImage1, MyImage pipeUpTopRightImage1, MyImage pipeDownMiddleLeftImage1, MyImage pipeDownMiddleRightImage1,
			MyImage pipeDownTopLeftImage1, MyImage pipeDownTopRightImage1, MyImage pipeUpMiddleLeftImage1, MyImage pipeUpMiddleRightImage1,
			MyImage billBlasterTopImage1, 
			MyImage billBlasterMiddleImage1,
			MyImage billBlasterBottomImage1,
			MyGCanvas canvas1) {
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
		billBlasterTopImage = billBlasterTopImage1; 
		billBlasterMiddleImage = billBlasterMiddleImage1; 
		billBlasterBottomImage = billBlasterBottomImage1;
		canvas = canvas1;
	}
	
	//bill blaster is cannon that shoots bullets (BulletBill)
	//h>3
	public static double spawnBillBlaster(XCounter xCounter, double h, ArrayList<LevelPart> levelParts) {
		if (h<3) h=3;
		ArrayList<ThreadSafeGImage> images = new ArrayList<ThreadSafeGImage>();
		for (int i=1; i<=h-2; i++) {
			Platform g1 = new Platform(billBlasterBottomImage);
			canvas.add(g1, xCounter.v, canvas.getHeight()-i*g1.getHeight());
			images.add(g1);
		}
		Platform g2 = new Platform(billBlasterMiddleImage);
		canvas.add(g2, xCounter.v, canvas.getHeight()-(h-1)*g2.getHeight());
		images.add(g2);
		
		Platform g3 = new Platform(billBlasterTopImage);
		canvas.add(g3, xCounter.v, canvas.getHeight()-h*g2.getHeight());
		images.add(g3);
		
		BillBlasterController.shoot(g3);
		
		double width = g3.getWidth();
		xCounter.v += width;
		levelParts.add(new LevelPart(images));
		return width;
	}
	

	/***
	 * This function spawns a grass mountain with given location and size (adds all images to canvas)
	 * Adds LevelPart to levelParts and increments xCounter.v (leftmost available place for a new LevelPart)
	 * xCounter.v is x coordinate of top left Image
	 * @param w width in number of images from left to right (expected >=3)
	 * @param h height in number of images from top to bottom (expected >=2)

	 */
	public static double spawnGrassMountain(XCounter xCounter, double w, double h, LevelController.BADGUY_TYPE type, ArrayList<LevelPart> levelParts) {
		if (w<3) w=3;
		if (h<2) h=2;
		ArrayList<ThreadSafeGImage> images = new ArrayList<ThreadSafeGImage>();
		for (int i = 1; i<w-1; i++) {
			for (int j = 1; j<h; j++) {
				Platform g1 = new Platform(grassMiddleImage);
				canvas.add(g1, xCounter.v+i*g1.getWidth(), canvas.getHeight()-j*g1.getHeight());
				images.add(g1);
			}
		}
		for (int i = 1; i<w-1; i++) {	
			Platform g1 = new Platform(grassMiddleTopImage);
			canvas.add(g1, xCounter.v+i*g1.getWidth(), canvas.getHeight()-h*g1.getHeight());
			images.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassLeftImage);
			canvas.add(g1, xCounter.v, canvas.getHeight()-j*g1.getHeight());
			images.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassRightImage);
			canvas.add(g1, xCounter.v+(w-1)*g1.getWidth(), canvas.getHeight()-j*g1.getHeight());
			images.add(g1);
		}
		Platform g1 = new Platform(grassRightTopImage);
		canvas.add(g1, xCounter.v+(w-1)*g1.getWidth(), canvas.getHeight()-h*g1.getHeight());
		images.add(g1);
		Platform g2 = new Platform(grassLeftTopImage);
		canvas.add(g2, xCounter.v, canvas.getHeight()-h*g1.getHeight());
		images.add(g2);
		//double height = g1.getHeight();
		double width = w*g1.getWidth(); 

		if (type == LevelController.BADGUY_TYPE.RED_TURTLE) {
			//TODO need to do green turtles too
			RedTurtle turtle = new RedTurtle(width);
			canvas.add(turtle, xCounter.v, canvas.getHeight()-g1.getHeight()*h-turtle.getHeight());
			images.add(turtle);
			turtle.move();
		} else if (type == LevelController.BADGUY_TYPE.GOOMBA) {
			Goomba goomba = new Goomba();
			canvas.add(goomba, xCounter.v, canvas.getHeight()-g1.getHeight()*h-goomba.getHeight());
			images.add(goomba);
			goomba.move();
		}
		xCounter.v += width;
		levelParts.add(new LevelPart(images));
		return width;
	}

	/***
	 * This function spawns a pipe facing up (mario can go down into) from the bottom of the screen
	 * Adds LevelPart to levelParts and increments xCounter.v (leftmost available place for a new LevelPart)
	 * xCounter.v is x coordinate of top left Image
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @param flowerType type of flower coming in/out of pipe (could be NO_FLOWER)
	 * @param timeOffset to coordinate some flowers to come in/out at the same time (if they have the same timeOffset)
	 * @param subLevelID is non empty if pipe allows mario to go into it and transport him
	 * @param levelParts to add every image to the currLevel
	 * @return width of pipe
	 */
	public static double spawnUpPipe(XCounter xCounter, double h, LevelController.FLOWER_TYPE flowerType, int timeOffset, String subLevelID, ArrayList<LevelPart> levelParts) {
		//TODO FIX BUG WHERE UP PIPE DOESNT RETURN CORRECT WIDTH, xCounter.v FOR LEVEL IS INCREMENTED PROPERLY
		//SO MARIO CAN WALK PAST THE END OF THE LEVEL
		//loophope around this problem: dont do xCounter.v += spawnUpPipe
		//do : spawnUpPipe(); xCounter.v+=200;
		if (h<2) h=2;
		ArrayList<ThreadSafeGImage> images = new ArrayList<ThreadSafeGImage>();	
		for (int i=1; i<h; i++) {
			Platform middleLeft = new Platform(pipeUpMiddleLeftImage);
			canvas.add(middleLeft, xCounter.v, canvas.getHeight()-middleLeft.getHeight()*i);
			images.add(middleLeft);
			Platform middleRight = new Platform(pipeUpMiddleRightImage);
			canvas.add(middleRight, xCounter.v+middleLeft.getWidth(), canvas.getHeight()-middleRight.getHeight()*i);
			images.add(middleRight);
		}

		Platform topLeft = subLevelID.length()>0?new LeftPipePart(pipeUpTopLeftImage, subLevelID, true):new Platform(pipeUpTopLeftImage);
		Platform topRight = subLevelID.length()>0?new RightPipePart(pipeUpTopRightImage, subLevelID, true):new Platform(pipeUpTopRightImage);

		Platform middleRight = new Platform(pipeUpMiddleRightImage);
		double dx = topLeft.getWidth()-middleRight.getWidth();
		//dx is to offset the top part so it sits in middle of pipe
		canvas.add(topLeft, xCounter.v-dx, canvas.getHeight()-h*topLeft.getHeight());
		images.add(topLeft);
		canvas.add(topRight, xCounter.v-dx+topLeft.getWidth(), canvas.getHeight()-h*topRight.getHeight());
		images.add(topRight);

		if (flowerType == LevelController.FLOWER_TYPE.SHOOTING) {
			//ShootingFlower is part of the same LevelPart as all the other images of the up Pipe
			ShootingFlower flower = new UpShootingFlower(timeOffset);
			double width = flower.getWidth();
			canvas.add(flower, topLeft.getX()+topLeft.getWidth()-width/2, topLeft.getY());
			flower.sendToBack();
			images.add(flower);
			flower.move();
		}

		levelParts.add(new LevelPart(images));
		double width = 2.0*middleRight.getWidth(); 
		xCounter.v += width;//width is always 2 images
		return width;
	}


	/***
	 * This function spawns a pipe facing down (mario can jump into) from the top of the screen
	 * Adds LevelPart to levelParts and increments xCounter.v (leftmost available place for a new LevelPart)
	 * xCounter.v is x coordinate of top left Image
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @param flowerType type of flower coming in/out of pipe (could be NO_FLOWER)
	 * @param timeOffset to coordinate some flowers to come in/out at the same time (if they have the same timeOffset)
	 * @param subLevelID is non empty if pipe allows mario to go into it and transport him
	 * @param levelParts to add every image to the currLevel
	 * @return width of pipe
	 */
	public static double spawnDownPipe(XCounter xCounter, double h, LevelController.FLOWER_TYPE flowerType, int timeOffset, String subLevelID, ArrayList<LevelPart> levelParts) {
		if (h<2) h=2;
		ArrayList<ThreadSafeGImage> images = new ArrayList<ThreadSafeGImage>();	
		for (int i=0; i<h-1; i++) {
			Platform middleLeft = new Platform(pipeDownMiddleLeftImage);
			canvas.add(middleLeft, xCounter.v, middleLeft.getHeight()*i);
			images.add(middleLeft);
			Platform middleRight = new Platform(pipeDownMiddleRightImage);
			canvas.add(middleRight, xCounter.v+middleLeft.getWidth(), middleRight.getHeight()*i);
			images.add(middleRight);
		}

		Platform topLeft = subLevelID.length()>0?new LeftPipePart(pipeDownTopLeftImage, subLevelID, false):new Platform(pipeDownTopLeftImage);
		Platform topRight = subLevelID.length()>0?new RightPipePart(pipeDownTopRightImage, subLevelID, false):new Platform(pipeDownTopRightImage);

		Platform middleRight = new Platform(pipeDownMiddleRightImage);
		double dx = topLeft.getWidth()-middleRight.getWidth();
		//dx is to offset the top part so it sits in middle of pipe
		canvas.add(topLeft, xCounter.v-dx, (h-1)*topLeft.getHeight());
		images.add(topLeft);
		canvas.add(topRight, xCounter.v-dx+topLeft.getWidth(), (h-1)*topRight.getHeight());
		images.add(topRight);

		if (flowerType == LevelController.FLOWER_TYPE.SHOOTING) {
			//ShootingFlower is part of the same LevelPart as all the other images of the up Pipe
			ShootingFlower flower = new DownShootingFlower(timeOffset);
			double width = flower.getWidth();
			double height = flower.getHeight();
			canvas.add(flower, topLeft.getX()+topLeft.getWidth()-width/2, topLeft.getY()+topLeft.getHeight()-height);
			flower.sendToBack();
			images.add(flower);
			flower.move();
		}

		levelParts.add(new LevelPart(images));
		double width = 2.0*topLeft.getWidth();
		xCounter.v += width;//width is always 2 images
		return width;
	}

	//this func does not increase xCounter.v by the width of the mysterybox
	//since usually mysterybox is on top of platform (like grass mountain)
	public static double spawnMysteryBox(double x, double y, ArrayList<LevelPart> levelParts) {
		ArrayList<ThreadSafeGImage> images = new ArrayList<ThreadSafeGImage>();
		MysteryBox b = new MysteryBox();
		canvas.add(b, x, canvas.getHeight()-y*b.getHeight());
		images.add(b);
		levelParts.add(new LevelPart(images));
		return b.getWidth();
	}

	//spawns a down pipe on top of an up pipe, xCounter.v is modified only once
	public static void spawnUpAndDownPipes(XCounter xCounter, double hUp, String subLevelIDUp, LevelController.FLOWER_TYPE tUp,  double hDown, String subLevelIDDown, LevelController.FLOWER_TYPE tDown, ArrayList<LevelPart> levelParts) {
		double width = spawnDownPipe(xCounter, hDown, tDown, 0, subLevelIDDown, levelParts);
		xCounter.v -= width;//so that down pipe is on top of up pipe
		spawnUpPipe(xCounter, hUp, tUp, 0, subLevelIDUp, levelParts);
	}
}