//import acm.graphics.GCanvas;
//import acm.graphics.GImage;
import acm.graphics.GObject;

//import java.awt.Image;
import java.util.ArrayList;
public abstract class MovingObject extends ThreadSafeGImage {//GImage {
	//fireball, mushroom, fire flower, leaf, mario, turtle, BulletBill extend MovingObject
	public static MyGCanvas canvas;
	private static double baseLineSpeed;
	public static Mario[] characters;
	public boolean alive;

	
	//moveDx is to ensure that the movement of images is scaled proportionally
	//depending on their size
	public MovingObject(MyImage arg0) {
		super(arg0);
		alive = true;
	}
	
	public static double getBaseLineSpeed() {return baseLineSpeed;}
	public static void setBaseLineSpeed(double d) {baseLineSpeed = d;}
	
	public final void startMove(String threadName) {
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				move();
			}
		}, threadName+" start move");//"moving object move function");
	}
	
	public abstract void move() throws InterruptedException;//should not start new thread to run, call startMove instead

	public abstract boolean inContactWith(GObject x, boolean horizontalOrVertical);
	//returns true if in contact with object and action taken (no need to check other points)
	//inContactWith is called multiple times with multiple different GObjects from different points on the screen
	//if one point works, return true and no need to check other points

	public ArrayList<GObject> checkAtPositions(Point[] points) {
		ArrayList<GObject> result = new ArrayList<GObject>();
		for (Point p : points) {
			GObject a = canvas.getElementAt(p.x, p.y);
			if (a!=null) {
				result.add(a);
			}
		}
		return result;
	}
	
	public void setImageAndRelocate(MyImage newImage) {
		//this function is called instead of setImage when mario (or any MovingObject with multiple sprites of different dimensions)
		//changes from big to small
		//or small to big since they have different heights they need to be readjusted
		//when mario changes from walking to standing technically could also relocate because
		//those sprites have slightly different widths, but it is negligible
		double relativeY = this.getY()+ this.getHeight();
		//need line above because big mario and small mario dont have the same height
		//and so need to shift vertically Mario when going from small to big or vice versa
		double previousWidth = this.getWidth();//(needed for horizontal shift)
		super.setImage(newImage);
		//X shift needed to keep big mario and small mario centered since their
		//widths can differ
		double xShift = (this.getWidth()-previousWidth)/2;
		this.setLocation(getX()-xShift, relativeY-this.getHeight());
	}
	
	public static void setObjects(MyGCanvas canvas1, Mario[] characters1) {
		canvas = canvas1;
		characters = characters1;
	}
}
