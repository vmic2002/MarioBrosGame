import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class FireBall extends GImage {
	private Image leftFireBall1;
	private Image rightFireBall1;
	public FireBall(Image leftFireBall1, Image rightFireBall1) {
		super(leftFireBall1);
		this.leftFireBall1 = leftFireBall1;
		this.rightFireBall1 = rightFireBall1;
	}
	
	//TODO need a class that returns a new fire ball, a factory
	
	public void move(double dx, double dy) {
		super.move(dx, dy);
		//TODO when fireball runs into turtle etc it kills them
	}
}
