import java.awt.Image;

import acm.graphics.GObject;

public abstract class PowerUp extends MovingObject implements Dynamic{
	//Leaf, Mushroom, FireFlower extend PowerUp
	public long id;
	public PowerUp(Image arg0) {
		super(arg0);
	}
	public abstract void move();
	//public abstract boolean inContactWith(GObject x, boolean horizontalOrVertical);
}
