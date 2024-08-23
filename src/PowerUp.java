

public abstract class PowerUp extends MovingObject implements Dynamic{
	//Leaf, Mushroom, Tanooki, FireFlower, Hourglass extend PowerUp 
	public PowerUp(MyImage arg0, Lobby lobby) {
		super(arg0, lobby);
	}
	
	@Override
	public long getID() {
		return this.getImageID();
	}
	
	@Override
	public void kill() {
		if (!lobby.levelController.endingLevel()) {
			lobby.canvas.remove(this);
			alive = false;
			lobby.levelController.currLevel.removeDynamic(this);
		}
	}
}
