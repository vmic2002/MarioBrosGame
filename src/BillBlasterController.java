
import java.util.ArrayList;

public class BillBlasterController{
	//when level is spawned, everytime a BillBlaster is added to the level,
	//the shoot(Platform p) func is called and a new thread is started to periodically shoot a BillBullet
	//out of the BillBlaster (Platform p is to keep track of current position of BillBlaster)
	//at start of level, LevelController calls startOfLevel()
	//at end of level, LevelControlelr calls endOfLevel() to stop all threads from shooting more BulletBills
	private static final long pause = 100;
	private ArrayList<Thread> threads;
	//private MyGCanvas canvas;
	private Lobby lobby;
	public BillBlasterController(Lobby lobby) {this.lobby = lobby;}
	public void startOfLevel() {threads = new ArrayList<Thread>();}
	@SuppressWarnings("deprecation")
	public void endOfLevel() {for (Thread t: threads) t.interrupt();
	System.out.println("\tTOTAL OF "+threads.size()+ " threads");}
	
	//number of threads == number of BillBlaster in currLevel

	public void startShooting(BillBlasterTop p) {
		//p is top of bill blaster (where BulletBill needs to be shot from)
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				ThreadSleep.sleep(pause, lobby);
				//pause above is to wait for current level to be fully spawned
				while (true) {
					boolean rightOrLeft = Math.random()>0.5;
					//TODO to shoot from both sides call addBulletBill twice, once with true and once with false (rightOrLeft)
					if (p.getX()<=MarioBrosGame.WIDTH && p.getX()+p.getWidth()>=0
							&& p.getY()<=MarioBrosGame.HEIGHT && p.getY()+p.getHeight()>=0) {
						//only BillBlasters on screen shoot a BulletBill
						lobby.dFactory.addBulletBill(p.getX(), p.getY(), rightOrLeft);
					}
					//System.out.println("\n\nSHOOTING BULLET BILL\n\n\n");
					ThreadSleep.sleep(5*pause, lobby);
				}
			}
		},"Bill Blaster", lobby.getLobbyId());
		threads.add(t1);
	}
}
