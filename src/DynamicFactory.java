

import java.util.ArrayList;
import java.util.HashMap;

//import acm.graphics.GCanvas;

public class DynamicFactory {
	//this class spawns MovingObjects that implement Dynamic and adds them to dynamicLevelParts
	//dynamically while a level is being played and calls their move function
	//powerups, fireballs, bulletbill etc
	//private MyGCanvas canvas;
	//private LevelController levelController;
	//private ServerToClientMessenger messenger;
	private Lobby lobby;
	public DynamicFactory(Lobby lobby) {

		this.lobby = lobby;
	}

	private void addPowerUp(double x, double y, double mysteryBoxWidth, PowerUp powerUp) {
		//adds power up behing mystery box and makes it move up
		//so it looks like power up is coming out of mysteryBox
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				lobby.levelController.currLevel.addLevelPartDynamically(powerUp);
				lobby.canvas.add(powerUp, x+(mysteryBoxWidth-powerUp.getWidth())/2, y);
				lobby.messenger.sendAddLevelImageToScreenMessage(powerUp);
				powerUp.sendToBack();
				while (powerUp.getY()>y-powerUp.getHeight()) {
					powerUp.move(0, -MovingObject.getBaseLineSpeed()/2.0);

					ThreadSleep.sleep(2, lobby);

				}
				powerUp.setLocation(powerUp.getX(), y-powerUp.getHeight());
				powerUp.move();//instead of calling powerUp.startMove(), 
				//to call move func in new thread, call move func in current thread
			}
		},"power up move function", lobby.getLobbyId());
	}

	public void addMushroom(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Mushroom mushroom = new Mushroom(lobby);
		addPowerUp(x, y, mysteryBoxWidth, mushroom);
	}

	public void addFireFlower(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		FireFlower fireFlower = new FireFlower(lobby);
		addPowerUp(x, y, mysteryBoxWidth, fireFlower);
	}

	public void addLeaf(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Leaf leaf = new Leaf(Math.random()>0.5, lobby);
		addPowerUp(x, y, mysteryBoxWidth, leaf);
	}

	public void addTanooki(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Tanooki tanooki = new Tanooki(lobby);
		addPowerUp(x, y, mysteryBoxWidth, tanooki);
	}


	public void addHourglass(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Hourglass hourglass = new Hourglass(lobby);
		addPowerUp(x, y, mysteryBoxWidth, hourglass);
	}

	public void addMysteryBoxCoin(double x, double y, double mysteryBoxWidth, Mario m) {
		//called when Mario jumps into mysterybox or brick
		Coin coin = new MysteryBoxCoin(lobby);
		lobby.canvas.add(coin, x+(mysteryBoxWidth-coin.getWidth())/2, y-coin.getHeight());
		lobby.messenger.sendAddLevelImageToScreenMessage(coin);
		lobby.levelController.currLevel.addLevelPartDynamically(coin);
		lobby.characterStatsController.collectCoin(m);
		lobby.soundController.playCoinSound();
		coin.startMove("mysterybox coin");
	}
	public void addFireBall(double x, double y, boolean rightOrLeft) {
		//called when fire mario launches a fireball
		FireBall fireBall = new FireBall(rightOrLeft, lobby);
		lobby.canvas.add(fireBall, x, y);
		lobby.messenger.sendAddLevelImageToScreenMessage(fireBall);
		lobby.levelController.currLevel.addLevelPartDynamically(fireBall);
		fireBall.startMove("mario fireball");
	}

	public void addFlowerFireBall(double x, double y, boolean rightOrLeft, Mario mario) {
		//called when flower in pipe shoots a fireball at mario
		FireBall fireBall = new FireBall(rightOrLeft, lobby);
		lobby.canvas.add(fireBall, x, y);
		lobby.messenger.sendAddLevelImageToScreenMessage(fireBall);
		lobby.levelController.currLevel.addLevelPartDynamically(fireBall);
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{
				fireBall.shootAtMario(mario);
			}
		},"shootingflower fireball", lobby.getLobbyId());
	}

	public void addBulletBill(double x, double y, boolean rightOrLeft) {
		//called when BillBlaster shoots a BulletBill
		BulletBill bulletBill = new BulletBill(rightOrLeft, lobby);
		lobby.canvas.add(bulletBill, x, y);
		lobby.messenger.sendAddLevelImageToScreenMessage(bulletBill);
		bulletBill.sendToBack();//spawns behind BillBlaster
		lobby.levelController.currLevel.addLevelPartDynamically(bulletBill);
		bulletBill.startMove("bullet bill");
	}


	//!!!!!
	//functions below are called at level creation time (in LevelController.playLevelX func) 
	//add levelparts to temp hashmap
	//!!!!!

	public void addGoomba(double x, double y, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		Goomba goomba = new Goomba(lobby);
		lobby.canvas.add(goomba, x, y-goomba.getHeight());
		Level.addLevelPartDynamically(goomba, dynamicLevelParts);
	}

	public void addRedTurtle(double x, double y, double width, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		RedTurtle turtle = new RedTurtle(width, lobby);
		lobby.canvas.add(turtle, x, y-turtle.getHeight());
		Level.addLevelPartDynamically(turtle, dynamicLevelParts);
	}
	
	public void addGreenTurtle(double x, double y, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		GreenTurtle turtle = new GreenTurtle(lobby);
		lobby.canvas.add(turtle, x, y-turtle.getHeight());
		Level.addLevelPartDynamically(turtle, dynamicLevelParts);
	}

	public void addUpShootingFlower(double x, double y, int timeOffset, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		ShootingFlower flower = new UpShootingFlower(timeOffset, lobby);
		double width = flower.getWidth();
		lobby.canvas.add(flower, x-width/2, y);
		flower.sendToBack();
		Level.addLevelPartDynamically(flower, dynamicLevelParts);
	}

	public void addDownShootingFlower(double x, double y, int timeOffset, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		ShootingFlower flower = new DownShootingFlower(timeOffset, lobby);
		double width = flower.getWidth();
		double height = flower.getHeight();
		lobby.canvas.add(flower, x-width/2, y-height);
		flower.sendToBack();
		Level.addLevelPartDynamically(flower, dynamicLevelParts);
	}


	private double addFloatingCoin(double x, double y, HashMap<Long, DynamicLevelPart> dynamicLevelParts, FloatingCoinsBlock floatingCoinsBlock) {
		//called at level creation time (in LevelController.playLevelX func) for coins that float in air
		//a floating coin is part of a FloatingCoinsBlock
		FloatingCoin coin = new FloatingCoin(lobby);
		double height = coin.getHeight();//this is height of coin in stage 1 (when it is tallest)
		lobby.canvas.add(coin, x, y);
		Level.addLevelPartDynamically(coin, dynamicLevelParts);
		//coins start spinning in LevelController.startMovingObjects()
		//1 thread per FloatingCoinsBlock

		floatingCoinsBlock.addCoin(coin);
		return height;
	}

	public void addFloatingCoinsRectangle(double x, double y, int w, int h, HashMap<Long, DynamicLevelPart> dynamicLevelParts, ArrayList<FloatingCoinsBlock> floatingCoinsBlocks) {
		//called at level creation time
		//adds a 2D array of floating coins of width w (num coins wide) and height h (num coins high)
		//to dynamicLevelParts
		//1 FloatingCoinsBlock per floating coins rectangle
		FloatingCoinsBlock floatingCoinsBlock = new FloatingCoinsBlock();

		double coinHeight = addFloatingCoin(x, y, dynamicLevelParts, floatingCoinsBlock);
		double space = coinHeight/4;
		//assuming coinWidth is equal to coinHeight (it basically is, coin1.png is very close to being a square)
		if (w<2) w=2;
		if (h<2) h=2;
		for (int i=0; i<w; i++) {
			for (int j=1; j<h; j++) {
				addFloatingCoin(x+i*(coinHeight+space), y+j*(coinHeight+space), dynamicLevelParts, floatingCoinsBlock);
			}
		}
		for (int i=1; i<w; i++) {
			addFloatingCoin(x+i*(coinHeight+space), y, dynamicLevelParts, floatingCoinsBlock);
		}

		floatingCoinsBlocks.add(floatingCoinsBlock);

	}

	public void addFloatingCoinsTriangle(double x, double y, int h, HashMap<Long, DynamicLevelPart> dynamicLevelParts, ArrayList<FloatingCoinsBlock> floatingCoinsBlocks) {
		//called at level creation time
		//adds floating coins in triangle pattern
		//if h=2, 4 coins total, if h=3, 9 coins total...
		//there are h^2 coins total!!!
		//1 FloatingCoinsBlock per floating coins triangle
		FloatingCoinsBlock floatingCoinsBlock = new FloatingCoinsBlock();

		if (h<2) h=2;
		double coinHeight = addFloatingCoin(x, y, dynamicLevelParts, floatingCoinsBlock);
		double space = coinHeight/4;
		int i;
		for (i=1; i<h; i++) {
			for (int j=i; j>=0; j--) {
				addFloatingCoin(x+i*(coinHeight+space), y-j*(coinHeight+space), dynamicLevelParts, floatingCoinsBlock);
			}
		}
		for (int newI=h-2; i<2*h; i++) {
			for (int j=newI; j>=0; j--) {
				addFloatingCoin(x+i*(coinHeight+space), y-j*(coinHeight+space), dynamicLevelParts, floatingCoinsBlock);
			}
			newI--;
		}

		floatingCoinsBlocks.add(floatingCoinsBlock);
	}
}
