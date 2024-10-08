

import java.util.ArrayList;
import acm.graphics.GObject;

public class BulletBill extends BadGuy {
	private static MyImage leftBulletBill, rightBulletBill;
	private static long pause = 2;
	private static int MAX_GAS_LEFT = 500;
	private static double MAX_DX = MovingObject.getBaseLineSpeed()*1.3;
	private int gasLeft;
	private double dx;
	private boolean jumpedOn;//true when jumped on by mario
	public BulletBill(boolean rightOrLeft, Lobby lobby) {
		super(rightOrLeft?rightBulletBill:leftBulletBill, lobby);
		gasLeft = MAX_GAS_LEFT;
		dx = rightOrLeft?MAX_DX:-MAX_DX;
		jumpedOn = false;
	}

	@Override
	public void contactFromSideByMario(Mario mario) {mario.marioHit();}
	
	@Override
	public void jumpedOnByMario(Mario mario) {
		//called when mario jumps on BulletBill
		mario.hop();
		if (jumpedOn) return;
		lobby.characterStatsController.killBulletBillByJumpingOnIt(mario);
		jumpedOn = true;
		this.sendToFront();
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{
				while (alive && getY()<=MarioBrosGame.HEIGHT+lobby.levelController.currLevel.yBaseLine){
					move(dx, 0.5*MAX_DX);
					checkIfRunIntoSomething();
					//TODO falling bulletbill doesnt check below when falling
					ThreadSleep.sleep(pause, lobby);
				}
				kill();
			}
		}, "BulletBill Falling", lobby.getLobbyId());
	}
	
	private void checkIfRunIntoSomething() {
		double newX = dx>0?getX()+getWidth()+10:getX()-10;
		Point[] pointsSide = new Point[] {
				new Point(newX, getY()+10),
				new Point(newX, getY()+getHeight()/2),
				new Point(newX, getY()+getHeight()-10)
		};
		ArrayList<GObject> o1 = checkAtPositions(pointsSide);
		for (GObject x : o1) {
			inContactWith(x, true);
		}
	}
	
	@Override
	public void move() throws InterruptedException {
		System.out.println("Added BulletBill!!");
		while (gasLeft>0 && alive && !jumpedOn) {
			move(dx, 0);
			//System.out.println("\n\nBULLET BILL MOVING\n\n");
			gasLeft--;
			checkIfRunIntoSomething();
			ThreadSleep.sleep(pause, lobby);
		}
		if (gasLeft<=0 || !alive) this.kill();
		System.out.println("\n\nBulletBill END OF MOVE\n\n");

	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//TODO need to check to see if two BulletBills run into each other
		if (x instanceof FireBall) {((FireBall) x).kill();return true;}//fireball dies if BulletBill runs into it
		else return super.inContactWith(x, horizontalOrVertical);//checks to see if inContactWith Mario
	}

	public static void setObjects(MyImage leftBulletBill1, MyImage rightBulletBill1){
		leftBulletBill = leftBulletBill1;
		rightBulletBill = rightBulletBill1;
	}
}
