import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class Mushroom extends GImage {
	private GCanvas canvas;
	public Mushroom(Image mushroomImage, GCanvas canvas) {
		super(mushroomImage);
		this.canvas = canvas;
	}
	
	public void move(double dx, double dy) {
		//if touches mario he must become big
	}
}
