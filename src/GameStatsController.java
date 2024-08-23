


public class GameStatsController {
	//sets baseline speeds of MovingObjects + baseline TIME between move function calls (Thread.sleep(TIME))	
	private static Mario mario;
	private static Luigi luigi;
	private static long baseLinePause;//=10;//in milliseconds (val of 10 works well) SHOULD BE DIVISIBLE BY 2
	private static long longerPause;///to make everything slower
	private static long shorterPause;//to make everything faster
	private static enum PAUSE_STATE {BASELINE, LONG, SHORT};
	private static PAUSE_STATE pauseState;	
	public static void setCharacters(Mario mario1, Luigi luigi1) {
		mario = mario1;
		luigi = luigi1;
	}

	public static void setPauses(long x) {
		baseLinePause = x;
		longerPause = 2*baseLinePause;
		shorterPause = (long) (0.5*baseLinePause);
		pauseState = PAUSE_STATE.BASELINE;
	}
	
	public static void setToBaseLinePause() {pauseState=PAUSE_STATE.BASELINE;}
	public static void setToLongPause() {pauseState=PAUSE_STATE.LONG;}
	public static void setToShortPause() {pauseState=PAUSE_STATE.SHORT;}
	public static long getBaseLinePause() {return baseLinePause;}//used so that mario moves at normal speed
	public static long getShortPause() {return shorterPause;}//used when mario moves really fast
	
	public static long getPause() {
		if (pauseState==PAUSE_STATE.BASELINE)
			return baseLinePause;
		else if (pauseState==PAUSE_STATE.LONG)
			return longerPause;
		else
			return shorterPause;
	}

	//these funcs could be used to increase mario/luigi speeds
	public static void setMarioFallDy(double dy) {mario.setFallDy(dy);}
	public static void setMarioMoveDx(double dx) {mario.setMoveDx(dx);}
	public static void setLuigiFallDy(double dy) {luigi.setFallDy(dy);}
	public static void setLuigiMoveDx(double dx) {luigi.setMoveDx(dx);}

	public static void setMovingObjectBaseLineXSpeed(double dx) {MovingObject.setBaseLineSpeed(dx);}
}
