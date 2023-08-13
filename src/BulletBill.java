import java.util.ArrayList;
import acm.graphics.GObject;

public class BulletBill extends BadGuy {
	private static MyImage leftBulletBill, rightBulletBill;
	private static long pause = 20;
	private static int MAX_GAS_LEFT = 500;
	private static double MAX_DX = MovingObject.scalingFactor*1.3;
	private int gasLeft;
	private double dx;
	private boolean jumpedOn;//true when jumped on by mario
	public BulletBill(boolean rightOrLeft) {
		super(rightOrLeft?rightBulletBill:leftBulletBill);
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
		StatsController.killBulletBillByJumpingOnIt(mario);
		jumpedOn = true;
		this.sendToFront();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (alive && getY()<=canvas.getHeight()+LevelController.currLevel.yBaseLine){
					move(dx, 0.5*MAX_DX);
					checkIfRunIntoSomething();
					//TODO falling bulletbill doesnt check below when falling
					try {Thread.sleep(pause);} catch (Exception e) {e.printStackTrace();}
				}
				kill();
			}
		});
		t1.setName("BulletBill Falling");
		t1.start();	
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
	public void move() {
		System.out.println("Added BulletBill!!");
		while (gasLeft>0 && alive && !jumpedOn) {
			move(dx, 0);
			//System.out.println("\n\nBULLET BILL MOVING\n\n");
			gasLeft--;
			checkIfRunIntoSomething();
			try {Thread.sleep(pause);} catch (Exception e) {e.printStackTrace();}
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
