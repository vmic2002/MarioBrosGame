import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class FireBallFactory {
	private Image leftFireBall1;
	private Image rightFireBall1;
	private GCanvas canvas;
	
	public FireBallFactory(Image leftFireBall1, Image rightFireBall1, GCanvas canvas) {
		this.leftFireBall1 = leftFireBall1;
		this.rightFireBall1 = rightFireBall1;
		this.canvas = canvas;
	}
	
	public void addFireBall(double x, double y, boolean rightOrLeft) {
		Image fireBallImage = rightOrLeft?rightFireBall1:leftFireBall1;
		double dx = rightOrLeft?10.0:-10.0;
		FireBall fireBall = new FireBall(fireBallImage, rightOrLeft);
		canvas.add(fireBall, x, y);
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				//code here runs concurrently
				fireBall.move();
			}
		});  
		t1.start();
	}
}
//TODO also maybe if user holds fireball key then the fireball could charge until it is really big  
