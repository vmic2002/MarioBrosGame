import java.awt.Image;

import acm.graphics.GObject;

public abstract class PowerUp extends MovingObject {
	//Leaf, Mushroom, FireFlower extend PowerUp
	public PowerUp(Image arg0) {
		super(arg0);
	}
	public abstract void move();
	public abstract void inContactWith(GObject x, boolean horizontalOrVertical);
}
