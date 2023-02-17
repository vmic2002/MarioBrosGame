import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class FireFlower extends GImage {
	//this class is helpful so we can do (obj instanceof FireFlower)
	//easier for knowing if mario walks into flower
	//private GCanvas canvas;
	public FireFlower(Image fireImage) {
		super(fireImage);
		//this.canvas = canvas;
	}
	
	/*public void move(double dx, double dy) {
		super.move(dx, dy);
	}*/
}
