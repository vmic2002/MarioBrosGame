import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.awt.Image;
import java.util.ArrayList;
public abstract class MovingObject extends GImage {
	//fireball, mushroom, fire flower, leaf, mario (turtle) extend MovingObject
	public static GCanvas canvas;
	public boolean alive;
	public MovingObject(Image arg0) {
		super(arg0);
		alive = true;
	}
	
	public abstract void move();//called by factory class once added to canvas

	public abstract void inContactWith(GObject x, boolean horizontalOrVertical);

	public ArrayList<GObject> checkAtPositions(Point[] points, GCanvas canvas) {
		ArrayList<GObject> result = new ArrayList<GObject>();
		for (Point p : points) {
			GObject a = canvas.getElementAt(p.x, p.y);
			if (a!=null) {
				result.add(a);
			}
		}
		return result;
	}
	
	public void setImageAndRelocate(Image newImage) {
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
	
	public static void setCanvas(GCanvas canvas1) {
		canvas = canvas1;
	}
}
