import java.awt.Image;

import acm.graphics.GImage;

public class ThreadSafeGImage extends GImage {
	//movingObject extend ThreadSafeGImage because movingObjects can call move function
	//from multiple threads (when moving level or moving itself for example)
	//platforms extend ThreadSafeGImage because level can be moved from multiple threads
	//by multiple different marios
	public ThreadSafeGImage(Image arg0) {
		super(arg0);
	}
	//TODO still bugs ThreadSafeGImage
	@Override
	public synchronized void move(double dx, double dy) {
	//public void move(double dx, double dy) {
		//System.out.println("!!!!!!");
		super.move(dx, dy);
	}
}