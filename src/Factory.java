import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class Factory {
	private GCanvas canvas;
	
	public Factory(GCanvas canvas) {
		this.canvas = canvas;
	}
	
	public void addFireBall(double x, double y, boolean rightOrLeft) {
		//called when fire mario launches a fireball
		FireBall fireBall = new FireBall(rightOrLeft);
		canvas.add(fireBall, x, y);
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				//code here runs concurrently
				fireBall.move();
			}
		});  
		t1.start();
	}
	
	public Mushroom addMushroom(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		Mushroom mushroom = new Mushroom();
		canvas.add(mushroom, x+(mysteryBoxWidth-mushroom.getWidth())/2, y-mushroom.getHeight());
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				//code here runs concurrently
				mushroom.move(mysteryBoxWidth);
			}
		});  
		t1.start();
		return mushroom;
	}
	
	public FireFlower addFireFlower(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		FireFlower fireFlower = new FireFlower();
		canvas.add(fireFlower, x+(mysteryBoxWidth-fireFlower.getWidth())/2, y-fireFlower.getHeight());
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				//code here runs concurrently
				fireFlower.move();
			}
		});  
		t1.start();
		return fireFlower;
	}
	
	public Leaf addLeaf(double x, double y, double mysteryBoxWidth) {
		//x, y are coordinates of MysteryBox
		//System.out.println("ADdINF LEAF");
		Leaf leaf = new Leaf();
		canvas.add(leaf, x+(mysteryBoxWidth-leaf.getWidth())/2, y-leaf.getHeight());
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				//code here runs concurrently
				leaf.move();
			}
		});  
		t1.start();
		return leaf;
	}
}
//TODO also maybe if user holds fireball key then the fireball could charge until it is really big  
