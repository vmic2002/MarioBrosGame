import java.awt.Image;

import acm.graphics.GObject;

public abstract class BadGuy extends MovingObject implements Dynamic {
	//class BadGuy: ShootingFlower, BitingFlower, Turtle, BulletBill, Goomba and
	//any other characters in this game that hurt mario will extend BadGuy
	public long dynamicId;
	public BadGuy(MyImage arg0) {
		super(arg0);
	}

	public abstract void jumpedOnByMario(Mario mario);//if mario jumps on badguy
	public abstract void contactFromSideByMario(Mario mario);//if mario comes into contact with bad guy horizontally (from the side)

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		//need to "hit" mario aka make him smaller if flower comes out of pipe and hits him
		if (x instanceof Mario) {
			System.out.println("BAG GUY IN CONTACT WITH MARIO");
			((Mario) x).marioHit();
			return true;
		}
		return false;
	}

	public void kill() {
		//called when mario (or anything) kills the bad guy
		//TODO not really todo just a note. if a badguy is indestructible
		//TODO then he could override this kill() function to do nothing (therefore the bad guy is unkillable)
		
		canvas.remove(this);
		alive = false;
		LevelController.currLevel.removeDynamic(this);
	}
	
	@Override
	public void setID(long id) {
		this.dynamicId = id;
	}

	@Override
	public long getID() {
		return this.dynamicId;
	}

}