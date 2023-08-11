//import java.awt.Image;

//import acm.graphics.GObject;

public abstract class PowerUp extends MovingObject implements Dynamic{
	//Leaf, Mushroom, FireFlower extend PowerUp
	public long dynamicId;
	public PowerUp(MyImage arg0) {
		super(arg0);
	}
	//public abstract void move();
	//public abstract boolean inContactWith(GObject x, boolean horizontalOrVertical);
	
	@Override
	public void setID(long id) {
		this.dynamicId = id;
	}

	@Override
	public long getID() {
		return this.dynamicId;
	}
	
	public void kill() {
		canvas.remove(this);
		alive = false;
		LevelController.currLevel.removeDynamic(this);
	}
}