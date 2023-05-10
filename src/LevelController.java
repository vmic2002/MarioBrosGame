import java.util.ArrayList;
import acm.graphics.GCanvas;
import acm.graphics.GImage;

public class LevelController {
	//spawns a level and sets currLevel so mario can have access to it and move
	//level depending on how close to the edges of the screen he is

	private static GCanvas canvas;
	public static Level currLevel;//only one currLevel mario is playing at a time
	public static double space;
	public static enum FLOWER_TYPE {NO_FLOWER, SHOOTING, BITING};
	public static enum TURTLE_TYPE {NO_TURTLE, RED, GREEN};
	private static XCounter xCounter;

	public static void setObjects(GCanvas canvas1, double scalingFactor) {canvas=canvas1;
	space = scalingFactor*10.0;xCounter = new XCounter();}

	public static void endCurrentLevel() {
		//sets all moving objects of currLevel to dead
		//sets all mysteryboxs to final state (so the thread that changes
		//its state doesnt change picture of mysterybox from previous level)
		System.out.println("ENDING CURR LEVEL");
		//for (LevelPart l : currLevel.levelParts) {
		for (int i=0; i<currLevel.levelParts.size(); i++) {
			LevelPart l = currLevel.levelParts.get(i);
			for (GImage image : l.part) {
				if (image instanceof MovingObject) {
					((MovingObject) image).alive = false;
					//to set all BadGuys (which are added to levelParts) to alive=false
				} else if (image instanceof MysteryBox) {
					((MysteryBox) image).setToFinalState();
					//to set all MysteryBox (which are added to levelParts) to final state
					System.out.println("mysterybox set to final state");
				}
			}
		} 

		try {
			for (DynamicLevelPart l : currLevel.dynamicLevelParts.values()) {
				for (GImage image : l.part) {
					if (image instanceof MovingObject) {
						((MovingObject) image).alive = false;
						//this fixes bug where power up/fireball/bulletbill from previous level is removed from canvas
						//but when restarting level when mario walks into it it still affects him
						//or dead fireball to kill turtles etc
						if (image instanceof BulletBill)
							System.out.println("\n\n\tBULLETBILL SET TO DEAD\n\n");
						System.out.println("pow`er up/fireball/BulletBill dead");
					}
				}
			}
		} catch(Exception e) {
			System.out.println("Error occured when ending current level and looping through dynamic level parts");
			e.printStackTrace();
			System.exit(1);
		}
		BillBlasterController.endOfLevel();
		System.out.println("CURR LEVEL ENDED");
		//currLevel = null;
	}

	public static void restartCurrentLevel() {
		playLevel(currLevel.getID());
	}

	public static void playLevel(String subLevelID) {
		System.out.println("STARTING LEVEL "+subLevelID);
		if (currLevel!=null) endCurrentLevel();
		canvas.removeAll();
		xCounter.initialize();//need to re initialize xCounter.v to 0 at the beginning of each level
		BillBlasterController.startOfLevel();
		if (subLevelID.equals("1")) playLevel1();
		else if (subLevelID.equals("1a")) playLevel1a();
		else if (subLevelID.equals("1b")) playLevel1b();
		else if (subLevelID.equals("2")) playLevel2();
		else if (subLevelID.equals("3")) playLevel3();
		else if (subLevelID.equals("4")) playLevel4();
		else {System.out.println("NO SUBLEVEL WITH ID "+subLevelID);return;}
	}

	//this function adds white space to level by increasing xCounter.v without adding any images
	public static void spawnWhiteSpace(XCounter xCounter, double numSpaces) {
		xCounter.v += numSpaces*space;
	}


	public static void playLevel1() {
		//adds each GImage for each LevelPart to the canvas at their starting positions
		//adds LevelParts from left to right so helpful to have xCounter to keep track of
		//smallest left index where a new LevelPart could be spawned
		//to have white space in between level parts need to increment xCounter by width of whitespace
		//at the end of function XCounter will be the width of the level

		//	canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 3, 3, TURTLE_TYPE.NO_TURTLE, levelParts);
		spawnWhiteSpace(xCounter, 1);
		StaticFactory.spawnBillBlaster(xCounter, 6, levelParts);
		StaticFactory.spawnGrassMountain(xCounter, 8, 4, TURTLE_TYPE.RED, levelParts);
		spawnWhiteSpace(xCounter, 4);
		StaticFactory.spawnMysteryBox(xCounter.v+2.0*space, 8, levelParts);
		StaticFactory.spawnGrassMountain(xCounter, 4, 5, TURTLE_TYPE.RED, levelParts);
		spawnWhiteSpace(xCounter, 1);
		StaticFactory.spawnBillBlaster(xCounter, 9, levelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.SHOOTING, 0, "1a", levelParts);
		StaticFactory.spawnDownPipe(xCounter, 3, FLOWER_TYPE.SHOOTING, 1000, "", levelParts);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.SHOOTING, 0, "1b", levelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnGrassMountain(xCounter, 8, 2, TURTLE_TYPE.RED, levelParts);
		StaticFactory.spawnMysteryBox(xCounter.v-4.0*space, 6, levelParts);
		StaticFactory.spawnMysteryBox(xCounter.v-2.0*space, 6, levelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpPipe(xCounter, 4, FLOWER_TYPE.NO_FLOWER, 0, "4", levelParts);
		Level level1 = new Level("1", levelParts, xCounter.v);
		currLevel = level1;//set currLevel
		//mario.fall(5);
		addCharactersAtStartOfLevel(new double[] {0.0, 2*MovingObject.characters[1].getWidth()});
	}


	public static void playLevel1a() {
		//	canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 20, 3, TURTLE_TYPE.RED, levelParts);
		StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "1a", levelParts);
		spawnWhiteSpace(xCounter, 3);
		StaticFactory.spawnUpAndDownPipes(xCounter, 4, "1b", FLOWER_TYPE.NO_FLOWER, 3, "2", FLOWER_TYPE.NO_FLOWER, levelParts);
		Level level1a = new Level("1a", levelParts, xCounter.v);
		currLevel = level1a;//set currLevel
		//	mario.fall(5);
		addCharactersAtStartOfLevel(new double[] {0.0, 2*MovingObject.characters[1].getWidth()});
	}

	public static void playLevel1b() {
		//canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		for (int i=0; i<4; i++) {
			StaticFactory.spawnUpPipe(xCounter, 7, FLOWER_TYPE.NO_FLOWER, 0, "2", levelParts);
			StaticFactory.spawnMysteryBox(xCounter.v+2.0*space, 7, levelParts);
			StaticFactory.spawnGrassMountain(xCounter, 10, 4, TURTLE_TYPE.RED, levelParts);
			StaticFactory.spawnUpPipe(xCounter, 7, FLOWER_TYPE.NO_FLOWER, 0, "3", levelParts);
			if (i!=3) spawnWhiteSpace(xCounter, 2);
		}

		Level level1a = new Level("1b", levelParts, xCounter.v);
		currLevel = level1a;//set currLevel
		//mario.fall(5);
		addCharactersAtStartOfLevel(new double[] {0.0, 2*MovingObject.characters[1].getWidth()});
	}

	public static void playLevel2() {
		//canvas.add(mario, 0, 0);//canvas.getHeight()-4*mario.getHeight());
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 4, 2, TURTLE_TYPE.NO_TURTLE, levelParts);
		StaticFactory.spawnMysteryBox(2.0*space, 5, levelParts);
		spawnWhiteSpace(xCounter, 2);
		double xCounterTemp = xCounter.v;
		for (int i=0; i<3; i++) {
			StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.SHOOTING, 200*i, "", levelParts);
			spawnWhiteSpace(xCounter, 2);
			StaticFactory.spawnGrassMountain(xCounter, 3, 2, TURTLE_TYPE.RED, levelParts);
			spawnWhiteSpace(xCounter, 2);
		}
		xCounter.v -= xCounter.v-xCounterTemp;
		for (int i=0; i<5; i++) {
			spawnWhiteSpace(xCounter, 3);
			StaticFactory.spawnDownPipe(xCounter, 3, FLOWER_TYPE.SHOOTING, 200*i, "", levelParts);
		}
		spawnWhiteSpace(xCounter, 2);
		xCounterTemp = xCounter.v;
		for (int i=0; i<4; i++) {
			spawnWhiteSpace(xCounter, 2);
			StaticFactory.spawnDownPipe(xCounter, 2, FLOWER_TYPE.SHOOTING, i%2==0?0:100, "", levelParts);
		}
		xCounter.v -= xCounter.v-xCounterTemp;
		StaticFactory.spawnGrassMountain(xCounter, 17, 2, TURTLE_TYPE.RED, levelParts);
		StaticFactory.spawnUpAndDownPipes(xCounter, 4, "1", FLOWER_TYPE.NO_FLOWER, 4, "2", FLOWER_TYPE.NO_FLOWER,  levelParts);
		spawnWhiteSpace(xCounter, 1);
		Level level2 = new Level("2", levelParts, xCounter.v);
		currLevel = level2;//set currLevel
		//mario.fall(5);
		addCharactersAtStartOfLevel(new double[] {0.0, 2*MovingObject.characters[1].getWidth()});
	}


	public static void playLevel3() {
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		StaticFactory.spawnGrassMountain(xCounter, 4, 5, TURTLE_TYPE.NO_TURTLE, levelParts);
		StaticFactory.spawnUpAndDownPipes(xCounter, 2, "", FLOWER_TYPE.SHOOTING, 2, "", FLOWER_TYPE.SHOOTING,  levelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "2", levelParts);
		spawnWhiteSpace(xCounter, 2);
		StaticFactory.spawnUpAndDownPipes(xCounter, 2, "", FLOWER_TYPE.SHOOTING, 2, "", FLOWER_TYPE.SHOOTING,  levelParts);
		StaticFactory.spawnGrassMountain(xCounter, 5, 5, TURTLE_TYPE.NO_TURTLE, levelParts);
		Level level3 = new Level("3", levelParts, xCounter.v);
		currLevel = level3;//set currLevel
		addCharactersAtStartOfLevel(new double[] {0.0, canvas.getWidth()-MovingObject.characters[1].getWidth()});
	}
	
	public static void playLevel4() {
		ArrayList<LevelPart> levelParts = new ArrayList<LevelPart>();
		for (int i=0; i<4; i++) {
			StaticFactory.spawnBillBlaster(xCounter, i+5, levelParts);
			spawnWhiteSpace(xCounter, 2);
		}
		StaticFactory.spawnUpPipe(xCounter, 2, FLOWER_TYPE.NO_FLOWER, 0, "1", levelParts);
		StaticFactory.spawnGrassMountain(xCounter, 5, 3, TURTLE_TYPE.NO_TURTLE, levelParts);
		Level level4 = new Level("4", levelParts, xCounter.v);
		currLevel = level4;//set currLevel
		addCharactersAtStartOfLevel(new double[] {0.0, 2*MovingObject.characters[1].getWidth()});
	}



	public static void addCharactersAtStartOfLevel(double[] xPositions) {
		//xPositions.length expected to be equal to MovingObject.characters.length
		//for now this function drops all characters at top left of level
		//and makes them fall at the same time
		for (int i=0; i<MovingObject.characters.length; i++) {
			Mario m = MovingObject.characters[i];
			canvas.add(m, xPositions[i], 0);
			Thread t1 = new Thread(new Runnable() {
				@Override
				public void run() {
					m.setToAlive(false);//this is in case another mario died after the first mario who died and is still going up/down in dead sprite 
					if (m.bigOrSmall) m.setToJumpingDown(true);
					m.fall(5);
					if (!m.isCrouching ) {
						m.lookingRightOrLeft = true;
						m.lookInCorrectDirection(true);//sets back to standing sprite looking in correct direction
					}
					m.isJumping = false;
				}
			});
			t1.start();
		}
	}
}
