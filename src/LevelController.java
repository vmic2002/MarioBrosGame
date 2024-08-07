import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import acm.graphics.GCanvas;
import acm.graphics.GImage;

public class LevelController {
	//spawns a level and sets currLevel so mario can have access to it and move
	//level depending on how close to the edges of the screen he is

	private static MyGCanvas canvas;
	public static Level currLevel;//only one currLevel mario is playing at a time
	public static double space;
	public static enum FLOWER_TYPE {NO_FLOWER, SHOOTING, BITING};
	public static enum BADGUY_TYPE {NO_BADGUY, RED_TURTLE, GREEN_TURTLE, GOOMBA};//type of bad guy on platform (such as grass mountain)
	public static enum MUSHROOM_PLATFORM_TYPE {GREEN, RED, YELLOW};//type of mushroom platform
	private static XCounter xCounter;
	private static boolean endingLevel=false;

	public static boolean endingLevel() {return endingLevel;}

	public static void setObjects(MyGCanvas canvas1) {canvas=canvas1;
	space = MovingObject.getBaseLineSpeed()*10.0;xCounter = new XCounter();}

	private static void endCurrentLevel() {
		endingLevel = true;
		//sets all moving objects of currLevel to dead
		//sets all mysteryboxs to final state (so the thread that changes
		//its state doesnt change picture of mysterybox from previous level)
		//stops all billblastercontroller threads
		System.out.println("ENDING CURR LEVEL");
		//for (LevelPart l : currLevel.staticLevelParts) {
		for (int i=0; i<currLevel.staticLevelParts.size(); i++) {
			StaticLevelPart l = currLevel.staticLevelParts.get(i);
			if (l.platforms.size()==1 && l.platforms.get(0) instanceof MysteryBox) {
				((MysteryBox) l.platforms.get(0)).setToFinalState();
				//to set all MysteryBox (which are added to staticLevelParts) to final state
				System.out.println("mysterybox set to final state");
			}
		}

		try {
			for (DynamicLevelPart l : currLevel.dynamicLevelParts.values()) {
				((MovingObject) l.part).alive = false;
				//this fixes bug where dynamiclevelpart from previous level is removed from canvas
				//but when restarting level when mario walks into it it still affects him
				//or dead fireball to kill turtles etc
				//see Dynamic.java for more details
				System.out.println("dynamiclevelpart dead");
			}
		} catch(Exception e) {
			System.out.println("Error occured when ending current level and looping through dynamic level parts");
			e.printStackTrace();
			System.exit(1);
		}

		for (FloatingCoinsBlock f : currLevel.floatingCoinsBlocks) {
			f.stopThread = true;//STOP ALL FLOATINGCOINSBLOCK THREADS
		}
		BillBlasterController.endOfLevel();
		System.out.println("CURR LEVEL ENDED");
		//currLevel = null;
		endingLevel = false;
	}

	public static void restartCurrentLevel() {
		playLevel(currLevel.getID());
	}

	public static void playLevel(String subLevelID) {
		System.out.println("STARTING LEVEL "+subLevelID);
		canvas.removeAll();

		//FOR TESTING
		//try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		//FOR TESTING



		if (currLevel!=null) endCurrentLevel();
		xCounter.initialize();//need to re initialize xCounter.v to 0 at the beginning of each level
		BillBlasterController.startOfLevel();
		if (subLevelID.equals("0")) playLevel0();
		else if (subLevelID.equals("1")) playLevel1();
		else if (subLevelID.equals("1a")) playLevel1a();
		else if (subLevelID.equals("1b")) playLevel1b();
		else if (subLevelID.equals("2")) playLevel2();
		else if (subLevelID.equals("3")) playLevel3();
		else if (subLevelID.equals("4")) playLevel4();
		else if (subLevelID.equals("5")) playLevel5();
		else {System.out.println("NO SUBLEVEL WITH ID "+subLevelID);return;}
		//by now currLevel = new Level and need to add characters to level and start moving all the moving objects
		addCharactersAtStartOfLevel();
		startMovingObjects();
	}

	private static void startMovingObjects() {
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				for (StaticLevelPart l : currLevel.staticLevelParts) {
					if (l.platforms.size()==1 && l.platforms.get(0) instanceof MysteryBox)
						((MysteryBox) l.platforms.get(0)).startChangingState();//START CHANGING STATES FOR MYSTERYBOXES
					else {
						//in StaticFactory.spawnBillBlaster, BillBlasterTop is added at end of platforms arraylist
						//for staticlevelpart.platforms, so need to check if last platforms in list is BillBlasterTop
						//to "activate" billblasters when level is instantiated
						Platform p = l.platforms.get(l.platforms.size()-1);
						if (p instanceof BillBlasterTop) {
							BillBlasterController.startShooting((BillBlasterTop) p);
						}
					}
				}

				for (DynamicLevelPart l : currLevel.dynamicLevelParts.values()) {
					ThreadSafeGImage image = (ThreadSafeGImage) l.part;
					if (image instanceof BadGuy)
						((BadGuy) image).startMove();
					else if (image instanceof PowerUp)
						((PowerUp) image).startMove();
				}
				for (FloatingCoinsBlock f : currLevel.floatingCoinsBlocks) {
					f.startSpinningBlock();//START SPINNING ALL COINS AT BEGINNING OF LEVEL SO THEY ALL SPIN AT SAME TIME
				}

			}
		}, "starting moving objects at beginning of level");
	}

	//this function adds white space to level by increasing xCounter.v without adding any images
	private static void spawnWhiteSpace(XCounter xCounter, double numSpaces) {
		xCounter.v += numSpaces*space;
	}

	private static void playLevel0() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 3, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		//	DynamicFactory.addFloatingCoinsRectangle(xCounter.v, canvas.getHeight()/4, 10, 4, dynamicLevelParts, floatingCoinsBlocks);
		//DynamicFactory.addFloatingCoinsTriangle(xCounter.v+3.0*space, canvas.getHeight()/2-space, 3, dynamicLevelParts, floatingCoinsBlocks);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.NO_FLOWER, 0, "1a", staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnGrassMountain(xCounter, 4, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnMysteryBox(xCounter.v-4.0*space, 8, staticLevelParts, MysteryBox.SPAWN.Coin);
		StaticFactory.spawnMysteryBox(xCounter.v-3.0*space, 8, staticLevelParts, MysteryBox.SPAWN.Coin);
		StaticFactory.spawnMysteryBox(xCounter.v-2.0*space, 8, staticLevelParts, MysteryBox.SPAWN.FireFlower);

		Level level0 = new Level("0", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level0;//set currLevel
	}

	private static void playLevel1() {
		//adds each GImage for each LevelPart to the canvas at their starting positions
		//adds LevelParts from left to right so helpful to have xCounter to keep track of
		//smallest left index where a new LevelPart could be spawned
		//to have white space in between level parts need to increment xCounter by width of whitespace
		//at the end of function XCounter will be the width of the level
		//list of staticlevelparts is ordered, smallest indices corresponding to leftmost level parts

		//	canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());

		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 3, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		DynamicFactory.addFloatingCoinsRectangle(xCounter.v+3.0*space, canvas.getHeight()/5, 2, 2, dynamicLevelParts, floatingCoinsBlocks);
		StaticFactory.spawnBillBlaster(xCounter, 6, staticLevelParts);
		StaticFactory.spawnGrassMountain(xCounter, 8, 4, BADGUY_TYPE.GOOMBA, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 4);
		StaticFactory.spawnMysteryBox(xCounter.v+2.0*space, 8, staticLevelParts, MysteryBox.SPAWN.RANDOM);
		StaticFactory.spawnGrassMountain(xCounter, 4, 5, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		StaticFactory.spawnBillBlaster(xCounter, 9, staticLevelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.SHOOTING, 0, "5", staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnDownPipe(xCounter, 3, FLOWER_TYPE.SHOOTING, 1000, "", staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.SHOOTING, 0, "1b", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnGrassMountain(xCounter, 8, 2, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
		DynamicFactory.addFloatingCoinsTriangle(xCounter.v-3.0*space, canvas.getHeight()/4-space, 4, dynamicLevelParts, floatingCoinsBlocks);
		StaticFactory.spawnMysteryBox(xCounter.v-4.0*space, 6, staticLevelParts, MysteryBox.SPAWN.Coin);
		StaticFactory.spawnMysteryBox(xCounter.v-2.0*space, 6, staticLevelParts, MysteryBox.SPAWN.FireFlower);//FIRE FLOWERS ITS FUN TO SHOOT AT SHOOTING FLOWERS
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.NO_FLOWER, 0, "4", staticLevelParts, dynamicLevelParts);
		Level level1 = new Level("1", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level1;//set currLevel
	}


	private static void playLevel1a() {
		//	canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 6, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		//StaticFactory.spawnGrassMountain(xCounter, 4, 4, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "1a", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 3);
		StaticFactory.spawnUpAndDownPipes(xCounter, 4, "1b", FLOWER_TYPE.NO_FLOWER, 3, "2", FLOWER_TYPE.NO_FLOWER, staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnGrassMountain(xCounter, 8, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		Level level1a = new Level("1a", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level1a;//set currLevel
	}

	private static void playLevel1b() {
		//canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		int x = 2;
		for (int i=0; i<x; i++) {
			StaticFactory.spawnUpPipe(xCounter, 7, FLOWER_TYPE.NO_FLOWER, 0, "2", staticLevelParts, dynamicLevelParts);
			StaticFactory.spawnMysteryBox(xCounter.v+2.0*space, 7, staticLevelParts, MysteryBox.SPAWN.Tanooki);
			StaticFactory.spawnGrassMountain(xCounter, 10, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
			StaticFactory.spawnUpPipe(xCounter, 7, FLOWER_TYPE.NO_FLOWER, 0, "3", staticLevelParts, dynamicLevelParts);
			if (i!=x-1) spawnWhiteSpace(xCounter, 2);
		}
		System.out.println(staticLevelParts.size());
		Level level1a = new Level("1b", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level1a;//set currLevel
	}

	private static void playLevel2() {
		//canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 4, 2, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnMysteryBox(2.0*space, 5, staticLevelParts, MysteryBox.SPAWN.RANDOM);
		spawnWhiteSpace(xCounter, 2);
		double xCounterTemp = xCounter.v;
		for (int i=0; i<3; i++) {
			StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.SHOOTING, 200*i, "", staticLevelParts, dynamicLevelParts);
			spawnWhiteSpace(xCounter, 2);
			StaticFactory.spawnGrassMountain(xCounter, 3, 2, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
			spawnWhiteSpace(xCounter, 2);
		}
		xCounter.v -= xCounter.v-xCounterTemp;
		for (int i=0; i<5; i++) {
			spawnWhiteSpace(xCounter, 3);
			StaticFactory.spawnDownPipe(xCounter, 3, FLOWER_TYPE.SHOOTING, 200*i, "", staticLevelParts, dynamicLevelParts);
		}
		spawnWhiteSpace(xCounter, 2);
		xCounterTemp = xCounter.v;
		for (int i=0; i<4; i++) {
			spawnWhiteSpace(xCounter, 2);
			StaticFactory.spawnDownPipe(xCounter, 2, FLOWER_TYPE.SHOOTING, i%2==0?0:100, "", staticLevelParts, dynamicLevelParts);
		}
		xCounter.v -= xCounter.v-xCounterTemp;
		StaticFactory.spawnGrassMountain(xCounter, 17, 2, BADGUY_TYPE.GOOMBA, staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnUpAndDownPipes(xCounter, 4, "1", FLOWER_TYPE.NO_FLOWER, 4, "2", FLOWER_TYPE.NO_FLOWER,  staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		Level level2 = new Level("2", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level2;//set currLevel
	}


	private static void playLevel3() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 4, 5, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnUpAndDownPipes(xCounter, 2, "", FLOWER_TYPE.SHOOTING, 2, "", FLOWER_TYPE.SHOOTING,  staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "2", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpAndDownPipes(xCounter, 2, "", FLOWER_TYPE.SHOOTING, 2, "", FLOWER_TYPE.SHOOTING,  staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnGrassMountain(xCounter, 5, 5, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		Level level3 = new Level("3", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level3;//set currLevel
	}

	private static void playLevel4() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		for (int i=0; i<4; i++) {
			StaticFactory.spawnBillBlaster(xCounter, i+5, staticLevelParts);
			spawnWhiteSpace(xCounter, 2);
		}
		StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "1", staticLevelParts, dynamicLevelParts);
		StaticFactory.spawnGrassMountain(xCounter, 5, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		Level level4 = new Level("4", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level4;//set currLevel
	}

	private static void playLevel5() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		//Level.addLevelPartDynamically(GImage i, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {

		//FOLLOWING LINE IS HOW TO ADD "FLOATING" COINS IN LEVEL
		//to add 1 coin DynamicFactory.addFloatingCoin(xCounter.v+space, canvas.getHeight()/2, dynamicLevelParts);

		//to add 2x2 coins in a rectangle DynamicFactory.addFloatingCoins(xCounter.v+3*space, canvas.getHeight()/3, 2, 2, dynamicLevelParts);

		//DynamicFactory.addFloatingCoinsRectangle(xCounter.v+10*space, canvas.getHeight()/5, 5, 3, dynamicLevelParts);
		//DynamicFactory.addFloatingCoinsTriangle(xCounter.v+3*space, canvas.getHeight()/3, 4, dynamicLevelParts);
		StaticFactory.spawnGrassMountain(xCounter, 6, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		//StaticFactory.spawnGrassMountain(xCounter, 6, 4, BADGUY_TYPE.GOOMBA, staticLevelParts, dynamicLevelParts);
		//StaticFactory.spawnMysteryBox(xCounter.v-1.0*space, 6, staticLevelParts);
		StaticFactory.spawnUpPipe(xCounter, 5, FLOWER_TYPE.NO_FLOWER, 0, "1a", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnMushroomPlatform(xCounter, 2, 3, MUSHROOM_PLATFORM_TYPE.YELLOW, staticLevelParts);
		spawnWhiteSpace(xCounter, 1);
		StaticFactory.spawnMushroomPlatform(xCounter, 1, 4, MUSHROOM_PLATFORM_TYPE.RED, staticLevelParts);
		spawnWhiteSpace(xCounter, 1);
		StaticFactory.spawnMushroomPlatform(xCounter, 3, 1, MUSHROOM_PLATFORM_TYPE.GREEN, staticLevelParts);
		spawnWhiteSpace(xCounter, 1);
		StaticFactory.spawnMushroomPlatform(xCounter, 6, 2, MUSHROOM_PLATFORM_TYPE.YELLOW, staticLevelParts);


		Level level5 = new Level("5", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks);
		currLevel = level5;//set currLevel
	}


	private static void addCharactersAtStartOfLevel() {
		//GET THE HEIGHT OF THE 0TH ELEMENT OF STATICLEVELPARTS AND START THE CHARACTERS
		//ON THE GROUND OF THE 0TH PLATFORM, this is also how it is done on the NES
		double startY = canvas.getHeight();
		for (Platform p : currLevel.staticLevelParts.get(0).platforms) {
			if (p.getY()<startY) startY = p.getY();
		}
		//now startY is the highest Y coordinate of the 0th element of staticlevelparts
		//so marios start on top of first staticlevelparts


		for (int i=0; i<MovingObject.characters.length; i++) {
			Mario m = MovingObject.characters[i];
			//m.setToAlive(false);
			m.lookingRightOrLeft = true;
			m.lookInCorrectDirection(true);//sets back to standing sprite looking in correct direction
			canvas.add(m, 2*i*m.getWidth(), startY-m.getHeight());
			//String messageToClient = "{ \"type\": \"addImageToScreen\", \"imageName\": \""+m.getMyImageName()+"\", \"id\":\""+m.getImageID()+"\", \"x\":\""+m.getX()+"\", \"y\":\""+m.getY()+"\" }";
			ServerToClientMessenger.sendAddCharacterImageToScreenMessage(m);
		}
	}
}
