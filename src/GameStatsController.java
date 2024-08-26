


public class GameStatsController {
	//sets baseline speeds of MovingObjects + baseline TIME between move function calls (Thread.sleep(TIME))	

	private static final long baseLinePause = 10;//=10;//in milliseconds (val of 10 works well) SHOULD BE DIVISIBLE BY 2
	private static final long longerPause = 2*baseLinePause;///to make everything slower
	private static final long shorterPause = (long) (0.5*baseLinePause);//to make everything faster
	private static enum PAUSE_STATE {BASELINE, LONG, SHORT};
	private PAUSE_STATE pauseState;
	
	private static boolean setBaselineSpeed = false;
	
	public GameStatsController() {
		pauseState = PAUSE_STATE.BASELINE;
	}
	

	public void setToBaseLinePause() {pauseState=PAUSE_STATE.BASELINE;}
	public void setToLongPause() {pauseState=PAUSE_STATE.LONG;}
	//public void setToShortPause() {pauseState=PAUSE_STATE.SHORT;}//TODO CALL FUNC TO MAKE EVERYTHING MOVE FASTER (EXCEPT MARIO characters)
	public static long getBaseLinePause() {return baseLinePause;}//used so that mario moves at normal speed
	public static long getShortPause() {return shorterPause;}//used when mario moves really fast

	public long getPause() {
		if (pauseState==PAUSE_STATE.BASELINE)
			return baseLinePause;
		else if (pauseState==PAUSE_STATE.LONG)
			return longerPause;
		else
			return shorterPause;
	}

	public static void setMovingObjectBaseLineXSpeed(double dx) {
		//this func is only called once, the first game/lobby will set the baselineSpeed
		if (setBaselineSpeed) return;
		setBaselineSpeed = true;
		MovingObject.setBaseLineSpeed(dx);
	}
}
