//import java.awt.Image;

//import acm.graphics.GObject;

public abstract class PowerUp extends MovingObject implements Dynamic{
	//Leaf, Mushroom, FireFlower extend PowerUp
	//public long dynamicId;
	public PowerUp(MyImage arg0) {
		super(arg0);
	}
	//public abstract void move();
	//public abstract boolean inContactWith(GObject x, boolean horizontalOrVertical);
	
	

	@Override
	public long getID() {
		return this.getImageID();
	}
	
	@Override
	public void kill() {
		if (!LevelController.endingLevel()) {
			canvas.remove(this);
			alive = false;
			LevelController.currLevel.removeDynamic(this);
		}
	}
}