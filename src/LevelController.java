

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Map;

//import acm.graphics.GCanvas;
//import acm.graphics.GImage;

public class LevelController {
	//spawns a level and sets currLevel so mario can have access to it and move
	//level depending on how close to the edges of the screen he is


	private Lobby lobby;
	
	public Level currLevel;//only one currLevel mario is playing at a time
	public static final double space = MovingObject.getBaseLineSpeed()*10.0;
	public static enum FLOWER_TYPE {NO_FLOWER, SHOOTING, BITING};
	public static enum BADGUY_TYPE {NO_BADGUY, RED_TURTLE, GREEN_TURTLE, GOOMBA};//type of bad guy on platform (such as grass mountain)
	public static enum MUSHROOM_PLATFORM_TYPE {GREEN, RED, YELLOW};//type of mushroom platform
	private XCounter xCounter;
	private boolean endingLevel;

	public boolean endingLevel() {return endingLevel;}

	public LevelController(Lobby lobby) {this.lobby = lobby;
	xCounter = new XCounter();endingLevel=false;}

	private void endCurrentLevel() {
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
		lobby.billBlasterController.endOfLevel();
		System.out.println("CURR LEVEL ENDED");
		//currLevel = null;
		endingLevel = false;
	}

	public void restartCurrentLevel() {
		playLevel(currLevel.getID());
	}

	public void playLevel(String subLevelID) {
		System.out.println("STARTING LEVEL "+subLevelID);
		lobby.canvas.removeAll();

		//FOR TESTING
		//try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
		//FOR TESTING



		if (currLevel!=null) endCurrentLevel();
		xCounter.initialize();//need to re initialize xCounter.v to 0 at the beginning of each level
		lobby.billBlasterController.startOfLevel();
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

	private void startMovingObjects() {
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				for (StaticLevelPart l : currLevel.staticLevelParts) {
					if (l.platforms.size()==1 && l.platforms.get(0) instanceof MysteryBox)
						((MysteryBox) l.platforms.get(0)).startChangingState();//START CHANGING STATES FOR MYSTERYBOXES
					else {
						//in lobby.sFactory.spawnBillBlaster, BillBlasterTop is added at end of platforms arraylist
						//for staticlevelpart.platforms, so need to check if last platforms in list is BillBlasterTop
						//to "activate" billblasters when level is instantiated
						Platform p = l.platforms.get(l.platforms.size()-1);
						if (p instanceof BillBlasterTop) {
							lobby.billBlasterController.startShooting((BillBlasterTop) p);
						}
					}
				}

				for (DynamicLevelPart l : currLevel.dynamicLevelParts.values()) {
					ThreadSafeGImage image = (ThreadSafeGImage) l.part;
					if (image instanceof BadGuy)
						((BadGuy) image).startMove("bad guy");
					else if (image instanceof PowerUp)
						((PowerUp) image).startMove("power up");
				}
				for (FloatingCoinsBlock f : currLevel.floatingCoinsBlocks) {
					f.startSpinningBlock(lobby);//START SPINNING ALL COINS AT BEGINNING OF LEVEL SO THEY ALL SPIN AT SAME TIME
				}

			}
		}, "starting moving objects at beginning of level", lobby.getLobbyId());
	}

	//this function adds white space to level by increasing xCounter.v without adding any images
	private void spawnWhiteSpace(XCounter xCounter, double numSpaces) {
		xCounter.v += numSpaces*space;
	}

	private void playLevel0() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		lobby.sFactory.spawnGrassMountain(xCounter, 3, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
	
		lobby.sFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.NO_FLOWER, 0, "1a", staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnGrassMountain(xCounter, 4, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnMysteryBox(xCounter.v-4.0*space, 8, staticLevelParts, MysteryBox.SPAWN.FireFlower);
		///lobby.sFactory.spawnMysteryBox(xCounter.v-3.0*space, 8, staticLevelParts, MysteryBox.SPAWN.Coin);
		///lobby.sFactory.spawnMysteryBox(xCounter.v-2.0*space, 8, staticLevelParts, MysteryBox.SPAWN.Mushroom);//FireFlower);

		
		
		
		Level level0 = new Level("0", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level0;//set currLevel
	}

	private void playLevel1() {
		//adds each GImage for each LevelPart to the canvas at their starting positions
		//adds LevelParts from left to right so helpful to have xCounter to keep track of
		//smallest left index where a new LevelPart could be spawned
		//to have white space in between level parts need to increment xCounter by width of whitespace
		//at the end of function XCounter will be the width of the level
		//list of staticlevelparts is ordered, smallest indices corresponding to leftmost level parts

		

		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		lobby.sFactory.spawnGrassMountain(xCounter, 3, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		lobby.dFactory.addFloatingCoinsRectangle(xCounter.v+3.0*space, MarioBrosGame.HEIGHT/5, 2, 2, dynamicLevelParts, floatingCoinsBlocks);
		lobby.sFactory.spawnBillBlaster(xCounter, 6, staticLevelParts);
		lobby.sFactory.spawnGrassMountain(xCounter, 8, 4, BADGUY_TYPE.GOOMBA, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 4);
		lobby.sFactory.spawnMysteryBox(xCounter.v+2.0*space, 8, staticLevelParts, MysteryBox.SPAWN.RANDOM);
		lobby.sFactory.spawnGrassMountain(xCounter, 4, 5, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		lobby.sFactory.spawnBillBlaster(xCounter, 9, staticLevelParts);
		spawnWhiteSpace(xCounter, 2);
		lobby.sFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.SHOOTING, 0, "5", staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnDownPipe(xCounter, 3, FLOWER_TYPE.SHOOTING, 1000, "", staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.SHOOTING, 0, "1b", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		lobby.sFactory.spawnGrassMountain(xCounter, 8, 2, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
		lobby.dFactory.addFloatingCoinsTriangle(xCounter.v-3.0*space, MarioBrosGame.HEIGHT/4-space, 4, dynamicLevelParts, floatingCoinsBlocks);
		lobby.sFactory.spawnMysteryBox(xCounter.v-4.0*space, 6, staticLevelParts, MysteryBox.SPAWN.Coin);
		lobby.sFactory.spawnMysteryBox(xCounter.v-2.0*space, 6, staticLevelParts, MysteryBox.SPAWN.FireFlower);//FIRE FLOWERS ITS FUN TO SHOOT AT SHOOTING FLOWERS
		spawnWhiteSpace(xCounter, 2);
		lobby.sFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.NO_FLOWER, 0, "4", staticLevelParts, dynamicLevelParts);
		Level level1 = new Level("1", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level1;//set currLevel
	}


	private void playLevel1a() {
		
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		lobby.sFactory.spawnGrassMountain(xCounter, 6, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		//lobby.sFactory.spawnGrassMountain(xCounter, 4, 4, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "1a", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 3);
		lobby.sFactory.spawnUpAndDownPipes(xCounter, 4, "1b", FLOWER_TYPE.NO_FLOWER, 3, "2", FLOWER_TYPE.NO_FLOWER, staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnGrassMountain(xCounter, 8, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		Level level1a = new Level("1a", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level1a;//set currLevel
	}

	private void playLevel1b() {
		
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		int x = 2;
		for (int i=0; i<x; i++) {
			lobby.sFactory.spawnUpPipe(xCounter, 7, FLOWER_TYPE.NO_FLOWER, 0, "2", staticLevelParts, dynamicLevelParts);
			lobby.sFactory.spawnMysteryBox(xCounter.v+2.0*space, 7, staticLevelParts, MysteryBox.SPAWN.Mushroom);//Tanooki);
			lobby.sFactory.spawnGrassMountain(xCounter, 10, 4, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
			lobby.sFactory.spawnUpPipe(xCounter, 7, FLOWER_TYPE.NO_FLOWER, 0, "3", staticLevelParts, dynamicLevelParts);
			if (i!=x-1) spawnWhiteSpace(xCounter, 2);
		}
		System.out.println(staticLevelParts.size());
		Level level1a = new Level("1b", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level1a;//set currLevel
	}

	private void playLevel2() {
		
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		lobby.sFactory.spawnGrassMountain(xCounter, 4, 2, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnMysteryBox(2.0*space, 5, staticLevelParts, MysteryBox.SPAWN.Leaf);
		spawnWhiteSpace(xCounter, 2);
		double xCounterTemp = xCounter.v;
		for (int i=0; i<2; i++) {
			lobby.sFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.SHOOTING, 200*i, "", staticLevelParts, dynamicLevelParts);
			spawnWhiteSpace(xCounter, 2);
			lobby.sFactory.spawnGrassMountain(xCounter, 3, 2, BADGUY_TYPE.RED_TURTLE, staticLevelParts, dynamicLevelParts);
			spawnWhiteSpace(xCounter, 2);
		}
		xCounter.v -= xCounter.v-xCounterTemp;
		for (int i=0; i<2; i++) {
			spawnWhiteSpace(xCounter, 3);
			lobby.sFactory.spawnDownPipe(xCounter, 3, FLOWER_TYPE.SHOOTING, 200*i, "", staticLevelParts, dynamicLevelParts);
		}
		spawnWhiteSpace(xCounter, 2);
		xCounterTemp = xCounter.v;
		for (int i=0; i<2; i++) {
			spawnWhiteSpace(xCounter, 2);
			lobby.sFactory.spawnDownPipe(xCounter, 2, FLOWER_TYPE.SHOOTING, i%2==0?0:100, "", staticLevelParts, dynamicLevelParts);
		}
		xCounter.v -= xCounter.v-xCounterTemp;
		spawnWhiteSpace(xCounter, 4);
		lobby.sFactory.spawnGrassMountain(xCounter, 6, 2, BADGUY_TYPE.GOOMBA, staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnUpAndDownPipes(xCounter, 4, "1", FLOWER_TYPE.NO_FLOWER, 4, "2", FLOWER_TYPE.NO_FLOWER,  staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 1);
		Level level2 = new Level("2", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level2;//set currLevel
	}


	private void playLevel3() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		lobby.sFactory.spawnGrassMountain(xCounter, 4, 5, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnUpAndDownPipes(xCounter, 2, "", FLOWER_TYPE.SHOOTING, 2, "", FLOWER_TYPE.SHOOTING,  staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		lobby.sFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "2", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		lobby.sFactory.spawnUpAndDownPipes(xCounter, 2, "", FLOWER_TYPE.SHOOTING, 2, "", FLOWER_TYPE.SHOOTING,  staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnGrassMountain(xCounter, 5, 5, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		Level level3 = new Level("3", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level3;//set currLevel
	}

	private void playLevel4() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		for (int i=0; i<4; i++) {
			lobby.sFactory.spawnBillBlaster(xCounter, i+5, staticLevelParts);
			spawnWhiteSpace(xCounter, 2);
		}
		lobby.sFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "1", staticLevelParts, dynamicLevelParts);
		lobby.sFactory.spawnGrassMountain(xCounter, 5, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		Level level4 = new Level("4", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level4;//set currLevel
	}

	private void playLevel5() {
		ArrayList<StaticLevelPart> staticLevelParts = new ArrayList<StaticLevelPart>();
		ArrayList<FloatingCoinsBlock> floatingCoinsBlocks = new ArrayList<FloatingCoinsBlock>();
		HashMap<Long, DynamicLevelPart> dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		//Level.addLevelPartDynamically(GImage i, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {

		//FOLLOWING LINE IS HOW TO ADD "FLOATING" COINS IN LEVEL
		//to add 1 coin DynamicFactory.addFloatingCoin(xCounter.v+space, MarioBrosGame.HEIGHT/2, dynamicLevelParts);

		//to add 2x2 coins in a rectangle DynamicFactory.addFloatingCoins(xCounter.v+3*space, MarioBrosGame.HEIGHT/3, 2, 2, dynamicLevelParts);

		//DynamicFactory.addFloatingCoinsRectangle(xCounter.v+10*space, MarioBrosGame.HEIGHT/5, 5, 3, dynamicLevelParts);
		//DynamicFactory.addFloatingCoinsTriangle(xCounter.v+3*space, MarioBrosGame.HEIGHT/3, 4, dynamicLevelParts);
		lobby.sFactory.spawnGrassMountain(xCounter, 6, 3, BADGUY_TYPE.NO_BADGUY, staticLevelParts, dynamicLevelParts);
		//lobby.sFactory.spawnGrassMountain(xCounter, 6, 4, BADGUY_TYPE.GOOMBA, staticLevelParts, dynamicLevelParts);
		//lobby.sFactory.spawnMysteryBox(xCounter.v-1.0*space, 6, staticLevelParts);
		lobby.sFactory.spawnUpPipe(xCounter, 5, FLOWER_TYPE.NO_FLOWER, 0, "1a", staticLevelParts, dynamicLevelParts);
		spawnWhiteSpace(xCounter, 2);
		lobby.sFactory.spawnMushroomPlatform(xCounter, 2, 3, MUSHROOM_PLATFORM_TYPE.YELLOW, staticLevelParts);
		spawnWhiteSpace(xCounter, 1);
		lobby.sFactory.spawnMushroomPlatform(xCounter, 1, 4, MUSHROOM_PLATFORM_TYPE.RED, staticLevelParts);
		spawnWhiteSpace(xCounter, 1);
		lobby.sFactory.spawnMushroomPlatform(xCounter, 3, 1, MUSHROOM_PLATFORM_TYPE.GREEN, staticLevelParts);
		spawnWhiteSpace(xCounter, 1);
		lobby.sFactory.spawnMushroomPlatform(xCounter, 6, 2, MUSHROOM_PLATFORM_TYPE.YELLOW, staticLevelParts);


		Level level5 = new Level("5", staticLevelParts, dynamicLevelParts, xCounter.v, floatingCoinsBlocks, lobby);
		currLevel = level5;//set currLevel
	}


	private void addCharactersAtStartOfLevel() {
		//GET THE HEIGHT OF THE 0TH ELEMENT OF STATICLEVELPARTS AND START THE CHARACTERS
		//ON THE GROUND OF THE 0TH PLATFORM, this is also how it is done on the NES
		double startY = MarioBrosGame.HEIGHT;
		for (Platform p : currLevel.staticLevelParts.get(0).platforms) {
			if (p.getY()<startY) startY = p.getY();
		}
		//now startY is the highest Y coordinate of the 0th element of staticlevelparts
		//so marios start on top of first staticlevelparts


		for (int i=0; i<lobby.characters.length; i++) {
			Mario m = lobby.characters[i];
			//m.setToAlive(false);
			m.lookingRightOrLeft = true;
			m.lookInCorrectDirection(true);//sets back to standing sprite looking in correct direction
			lobby.canvas.add(m, 2*i*m.getWidth(), startY-m.getHeight());
			//String messageToClient = "{ \"type\": \"addImageToScreen\", \"imageName\": \""+m.getMyImageName()+"\", \"id\":\""+m.getImageID()+"\", \"x\":\""+m.getX()+"\", \"y\":\""+m.getY()+"\" }";
			lobby.messenger.sendAddCharacterImageToScreenMessage(m);
		}
	}
}
