import java.awt.Image;

import acm.graphics.GObject;

public abstract class BadGuy extends MovingObject {
	//class BadGuy: ShootingFlower, BitingFlower, Turtle, and
	//any other characters in this game that hurt mario will extend BadGuy
	public BadGuy(Image arg0) {
		super(arg0);
	}
	
	@Override
	public void inContactWith(GObject x, boolean horizontalOrVertical) {
		//need to "hit" mario aka make him smaller if flower comes out of pipe and hits him
		if (x instanceof Mario) {
			System.out.println("BAG GUY IN CONTACT WITH MARIO");
			((Mario) x).marioHit();
		}
	}
}