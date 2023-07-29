import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.awt.Image;
public class FireFlower extends PowerUp {
	private static MyImage fireFlowerImage;
	public FireFlower() {
		super(fireFlowerImage);
	}
	
	@Override
	public void move() {
		System.out.println("ADDED A FIRE FLOWER");
	}
	
	public static void setObjects(MyImage fireFlowerImage1) {
		fireFlowerImage = fireFlowerImage1;
	}

	@Override
	public boolean inContactWith(GObject x, boolean b) {
		//not really needed since fire flower does not move
		return false;
	}

	@Override
	public void setID(long id) {
		this.id = id;
	}

	@Override
	public long getID() {
		return id;
	}
}
