import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GCanvas;
import acm.graphics.GImage;

public class LevelController {
	//spawns a level and sets currLevel so mario can have access to it and move
	//level depending on how close to the edges of the screen he is
	private static Image grassLeftTopImage, grassRightTopImage, 
	grassMiddleTopImage, grassLeftImage, grassRightImage, grassMiddleImage;
	private static GCanvas canvas;
	public static Level currLevel;//only one currLevel mario is playing at a time
	public static void setObjects(Image grassLeftTopImage1, Image grassRightTopImage1, Image grassMiddleTopImage1, 
			Image grassLeftImage1, Image grassRightImage1, Image grassMiddleImage1, GCanvas canvas1) {
		grassLeftTopImage = grassLeftTopImage1;
		grassRightTopImage = grassRightTopImage1;
		grassMiddleTopImage = grassMiddleTopImage1;
		grassLeftImage = grassLeftImage1;
		grassRightImage = grassRightImage1;
		grassMiddleImage = grassMiddleImage1;
		canvas = canvas1;
	}
	
	public static void restartCurrentLevel(Mario mario) {
		if (currLevel.getNumber()==1) playLevel1(mario);
		else if (currLevel.getNumber()==2) playLevel2(mario);
	}

	/***
	 * This function spawns a grass mountain with given location and size (adds all images to canvas)
	 * Adds LevelPart to levelParts and increments xCounter (leftmost available place for a new LevelPart)
	 * @param x coordinate of top left Image
	 * @param w width in number of images from left to right (expected >=3)
	 * @param h height in number of images from top to bottom (expected >=2)
	 * @returns width of entire LevelPart
	 */
	public static double spawnGrassMountain(double x,  double w, double h, ArrayList<LevelPart> levelParts) {
		ArrayList<GImage> images = new ArrayList<GImage>();
		for (int i = 1; i<w-1; i++) {
			for (int j = 1; j<h; j++) {
				Platform g1 = new Platform(grassMiddleImage);
				canvas.add(g1, x+i*g1.getWidth(), canvas.getHeight()-j*g1.getHeight());
				images.add(g1);
			}
			
		}
		for (int i = 1; i<w-1; i++) {	
			Platform g1 = new Platform(grassMiddleTopImage);
			canvas.add(g1, x+i*g1.getWidth(), canvas.getHeight()-h*g1.getHeight());
			images.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassLeftImage);
			canvas.add(g1, x, canvas.getHeight()-j*g1.getHeight());
			images.add(g1);
		}
		for (int j = 1; j<h; j++) {
			Platform g1 = new Platform(grassRightImage);
			canvas.add(g1, x+(w-1)*g1.getWidth(), canvas.getHeight()-j*g1.getHeight());
			images.add(g1);
		}
		Platform g1 = new Platform(grassRightTopImage);
		canvas.add(g1, x+(w-1)*g1.getWidth(), canvas.getHeight()-h*g1.getHeight());
		images.add(g1);
		Platform g2 = new Platform(grassLeftTopImage);
		canvas.add(g2, x, canvas.getHeight()-h*g1.getHeight());
		images.add(g2);
		double height = g1.getHeight();
		levelParts.add(new LevelPart(images, x, canvas.getHeight()-height*h ));
		return w*g1.getWidth();
	}
	
	public static double spawnMysteryBox(double x, double y, ArrayList<LevelPart> levelParts) {
		ArrayList<GImage> images = new ArrayList<GImage>();
		MysteryBox b = new MysteryBox();
		canvas.add(b, x, canvas.getHeight()-y*b.getHeight());
		images.add(b);
		levelParts.add(new LevelPart(images, x, canvas.getHeight()-y*b.getHeight()));
		return b.getWidth();
	}

	
	
	public static void playLevel1(Mario mario) {
		//adds each GImage for each LevelPart to the canvas at their starting positions
		//adds LevelParts from left to right so helpful to have xCounter to keep track of
		//smallest left index where a new LevelPart could be spawned
		//to have white space in between level parts need to increment xCounter by width of whitespace
		canvas.removeAll();
		canvas.add(mario, 0,canvas.getHeight()-3*mario.getHeight());
		double xCounter = 0.0;
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		xCounter += spawnGrassMountain(xCounter, 8, 2, levelParts);
		spawnMysteryBox(xCounter-200, 5, levelParts);
		xCounter += 200; 
		xCounter += spawnGrassMountain(xCounter, 4, 2, levelParts);
		xCounter += 200; 
		spawnMysteryBox(xCounter+100, 7, levelParts);
		xCounter += spawnGrassMountain(xCounter, 5, 4, levelParts);
		xCounter += 200;
		spawnMysteryBox(xCounter+100, 9, levelParts);
		xCounter += spawnGrassMountain(xCounter, 8, 6, levelParts);
		xCounter += 200; 
		spawnMysteryBox(xCounter+100, 7, levelParts);
		xCounter += spawnGrassMountain(xCounter, 5, 4, levelParts);
		xCounter += 200;
		spawnMysteryBox(xCounter+100, 5, levelParts);
		xCounter += spawnGrassMountain(xCounter, 8, 2, levelParts);
		xCounter += 200;
		Level level1 = new Level(1, levelParts, xCounter);
		currLevel = level1;//set currLevel
	}

	public static void playLevel2(Mario mario) {
		canvas.removeAll();
		canvas.add(mario, 0,canvas.getHeight()-3*mario.getHeight());
		double xCounter = 0.0;
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		//xCounter+=200;
		spawnMysteryBox(xCounter+201, 5, levelParts);
//		spawnMysteryBox(xCounter+400, 5, levelParts);
		spawnMysteryBox(xCounter+650, 5, levelParts);
//		spawnMysteryBox(xCounter+800, 5, levelParts);
		spawnMysteryBox(xCounter+1050, 5, levelParts);
//		spawnMysteryBox(xCounter+1200, 5, levelParts);
		spawnMysteryBox(xCounter+1450, 5, levelParts);
		xCounter += spawnGrassMountain(xCounter, 12, 2, levelParts);
		xCounter+=200;
	
		Level level2 = new Level(2, levelParts, xCounter);
		currLevel = level2;//set currLevel
	}

}
