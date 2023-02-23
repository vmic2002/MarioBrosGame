import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class Mushroom extends GImage {
	private static GCanvas canvas;
	private static Image mushroomImage;
	//images for Mushroom, MysteryBox, FireBall, FireFlower, Leaf etc are static
	//so we don't have to keep on providing them each time we want a new leaf, mushroom etc
	public Mushroom() {
		super(mushroomImage);
	}
	
	public void move() {
		//if touches mario he must become big
		//TODO need to make mushroom move left or right\
		//and fall down from mystery box (assume is on top of mysteryBox)
		
		System.out.println("ADDED MUSHROOM");
	}
	public static void setObjects(Image mushroomImage1, GCanvas canvas1) {
		mushroomImage = mushroomImage1;
		canvas = canvas1;
	}
}
