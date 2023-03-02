import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.awt.Image;
public class FireFlower extends MovingObject {
	private static Image fireFlowerImage;
	public FireFlower() {
		super(fireFlowerImage);
	}
	
	@Override
	public void move() {
		System.out.println("ADDED A FIRE FLOWER");
	}
	
	public static void setObjects(Image fireFlowerImage1) {
		fireFlowerImage = fireFlowerImage1;
	}

	@Override
	public void inContactWith(GObject x, boolean b) {
		//not really needed since fire flower does not move
	}
}
