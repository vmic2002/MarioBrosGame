import java.awt.Image;
import java.util.ArrayList;

public class BillBlasterController{
	//when level is spawned, everytime a BillBlaster is added to the level,
	//the shoot(Platform p) func is called and a new thread is started to periodically shoot a BillBullet
	//out of the BillBlaster (Platform p is to keep track of current position of BillBlaster)
	//at start of level, LevelController calls startOfLevel()
	//at end of level, LevelControlelr calls endOfLevel() to stop all threads from shooting more BulletBills
	private static long pause = 1000;
	private static ArrayList<Thread> threads;
	public static void startOfLevel() {threads = new ArrayList<Thread>();}
	@SuppressWarnings("deprecation")
	public static void endOfLevel() {for (Thread t: threads) t.stop();
	System.out.println("\tTOTAL OF "+threads.size()+ " threads");}
	//number of threads == number of BillBlaster in currLevel
	
	public static void shoot(Platform p) {
		//p is top of bill blaster (where BulletBill needs to be shot from)
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {Thread.sleep(pause);} catch (Exception e) {e.printStackTrace();}
				//pause above is to wait for current level to be fully spawned
				//since shoot function is called as level is being spawned
				while (true) {
					boolean rightOrLeft = Math.random()>0.5;
					//TODO to shoot from both sides call addBulletBill twice, once with true and once with false (rightOrLeft)
					DynamicFactory.addBulletBill(p.getX(), p.getY(), rightOrLeft);
					//System.out.println("\n\nSHOOTING BULLET BILL\n\n\n");
					try {Thread.sleep(5*pause);} catch (Exception e) {e.printStackTrace();}
				}
			}
		});
		threads.add(t1);
		t1.start();
	}
}