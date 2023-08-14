
public class GameStatsController {
	//keeps track of baseline speeds of MovingObjects + baseline TIME between move function calls (Thread.sleep(TIME))
	//could even keep track of stats (private fields) for turtle, mario, bullet bill, etc
	
	
	
	private static Mario mario;
	private static Luigi luigi;
	private static long baseLinePause=10;//in milliseconds (val of 10 works well)
	//TODO can slow down/SPEED UP whole game by changing val of baseLinePause
	//TODO GameStatsController.setBaseLinePause(GameStatsController.getBaseLinePause()*2);
	//TODO with this can finally make mario power up where he is really fast or slows down time
	
	public static void setCharacters(Mario mario1, Luigi luigi1) {
		mario = mario1;
		luigi = luigi1;
	}
	
	public static void setBaseLinePause(long x) {baseLinePause=x;}
	public static long getBaseLinePause() {return baseLinePause;}
	
	
	public static void setMarioFallDy(double dy) {mario.setFallDy(dy);}
	public static void setMarioMoveDx(double dx) {mario.setMoveDx(dx);}
	public static void setLuigiFallDy(double dy) {luigi.setFallDy(dy);}
	public static void setLuigiMoveDx(double dx) {luigi.setMoveDx(dx);}
	
	public static void setMovingObjectBaseLineXSpeed(double dx) {MovingObject.setBaseLineSpeed(dx);}
}
