public abstract class PowerUp extends MovingObject implements Dynamic{
	//Leaf, Mushroom, Tanooki, FireFlower, Hourglass extend PowerUp 
	public PowerUp(MyImage arg0) {
		super(arg0);
	}
	
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