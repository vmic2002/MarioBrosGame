import java.awt.Image;

import acm.graphics.GObject;

public abstract class BadGuy extends MovingObject {
	//class BadGuy: ShootingFlower, BitingFlower, Turtle, BulletBill and
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
	
	public void kill() {
		//called when mario (or anything) kills the bad guy
		//TODO not really todo just a note. if a badguy is indestructible
		//TODO then he could override this kill() function to do nothing (therefore the bad guy is unkillable)
		this.alive = false;
		canvas.remove(this);
	}
}