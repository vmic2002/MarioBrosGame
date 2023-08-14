public class ThreadSleep {
	public static void sleep(double l) {
		//sleeps thread by l*baselinePause
		try {
			Thread.sleep((long) (l*GameStatsController.getBaseLinePause()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
