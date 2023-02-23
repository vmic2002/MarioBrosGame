import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class Leaf extends GImage {
	private static GCanvas canvas;
	private static Image leftLeafImage;
	private static Image rightLeafImage;
	public Leaf() {
		super(Math.random()>0.5?rightLeafImage:leftLeafImage);
	}
	 
	public void move() {
		//TODO leaf needs to fall down left and right
		System.out.println("ADDED LEAF");
	}
	
	public static void setObjects(Image rightLeafImage1, Image leftLeafImage1, GCanvas canvas1) {
		rightLeafImage = rightLeafImage1;
		leftLeafImage = leftLeafImage1;
		canvas = canvas1;
	}
}
