import java.awt.Image;
import java.awt.event.ActionListener;

import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Mario extends GImage {
	private Image smallMarioImage;
	private Image bigMarioImage;
	private GCanvas canvas;
	public Mario(Image smallMarioImage, Image bigMarioImage, GCanvas canvas) {
		super(smallMarioImage);
		this.smallMarioImage = smallMarioImage;
		this.bigMarioImage = bigMarioImage;
		this.canvas = canvas;
		// TODO Auto-generated constructor stub
	}

	public void makeBig() {
		this.setImage(bigMarioImage);
	}

	public void makeSmall() {
		this.setImage(smallMarioImage);
	}

	public void jump() {
		System.out.println("JUMP not implemented");
		//TODO MORE COMPLICATED MIGHT NEED TO DO THIS IN ANOTHER THREAD
	}

	public void move(double dx, double dy) {
		double newX = 0;
		if (dx < 0) {
			newX = getX()-dx;
		} else {
			newX = getX()+getWidth()+dx;
		}
		GObject a = canvas.getElementAt(newX, getY()+getHeight()-10); 
		if (a!=null) {
			System.out.println("NOT NULL");
			if (a instanceof Mushroom) {
				canvas.remove(a);
				makeBig();
			}
		}
		super.move(dx, dy);
		System.out.println("MOVING MARIO");
		//TODO NEED TO CHECK IF MARIO JUMPS INTO
		//MYSTERY BOX OR OUT OF BOUNDS
		//if mario touches mushroom he becomes big
	}



}
