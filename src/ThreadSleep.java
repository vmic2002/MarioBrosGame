

public class ThreadSleep {
	//see GameThread.java and MyRunnable.java to see how InterruptedException is handled
	public static void sleep(double t) throws InterruptedException {
		//sleeps thread by l*baselinePause
		//to make everything move faster or slower depending on length of pause
		//see GameStatsController.java
		Thread.sleep((long) (t*GameStatsController.getPause()));
	}

	public static void sleepMarioNormal(double t) throws InterruptedException {
		//Mario, luigi etc only call sleepMario so that they are not affected by pause change
		//TODO make mario NOT call sleep func instead he must call sleepMarioNormal or sleepMarioFastMode depending on if mario should go
		//TODO at normal speed or should be very fast
		//TODO mario needs to have boolean fastMode and func sleep that calls sleepMarioNormal of sleepMarioFastMode depending on fastMode bool 
		Thread.sleep((long) (t*GameStatsController.getBaseLinePause()));
	}

	public static void sleepMarioFastMode(double t) throws InterruptedException {
		//TODO could have two powerups that make mario fast: flash (fastest) and star (2nd fastest)
		Thread.sleep((long) (t*GameStatsController.getShortPause()));
	}
}
