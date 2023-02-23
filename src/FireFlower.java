import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class FireFlower extends GImage {
	//this class is helpful so we can do (obj instanceof FireFlower)
	//easier for knowing if mario walks into flower
	private static GCanvas canvas;
	private static Image fireFlowerImage;
	public FireFlower() {
		super(fireFlowerImage);
	}
	
	public void move() {
		System.out.println("ADDED A FIRE FLOWER");
	}
	
	public static void setObjects(Image fireFlowerImage1, GCanvas canvas1) {
		fireFlowerImage = fireFlowerImage1;
		canvas = canvas1;
	}
}
