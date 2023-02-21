import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class Leaf extends GImage {
	private GCanvas canvas;
	public Leaf(Image leaf, GCanvas canvas) {
		super(leaf);
		this.canvas = canvas;
	}
	//TODO once box with question mark is done will need to create a factory class that returns
	//a mushroom leaf or fireflower, maybe merge fireball factory with that factory for one big factory
	public void move(double dx, double dy) {
		//TODO leaf needs to fall down left and right
		super.move(dx, dy);
	}
}
