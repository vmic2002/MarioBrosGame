public class ThreadSleep {
	public static void sleep(double t) {
		//sleeps thread by l*baselinePause
		//to make everything move faster or slower depending on length of pause
		//see GameStatsController.java
		try {
			Thread.sleep((long) (t*GameStatsController.getPause()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleepMarioNormal(double t) {
		//Mario, luigi etc only call sleepMario so that they are not affected by pause change
		//TODO make mario NOT call sleep func instead he must call sleepMarioNormal or sleepMarioFastMode depending on if mario should go
		//TODO at normal speed or should be very fast
		//TODO mario needs to have boolean fastMode and func sleep that calls sleepMarioNormal of sleepMarioFastMode depending on fastMode bool 
		try {
			Thread.sleep((long) (t*GameStatsController.getBaseLinePause()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void sleepMarioFastMode(double t) {
		//TODO could have two powerups that make mario fast: flash (fastest) and star (2nd fastest)
		try {
			Thread.sleep((long) (t*GameStatsController.getShortPause()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
