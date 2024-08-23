


public interface Dynamic {
	//an object in mario game implement Dynamic if it is dynamically added to level (as level is being played, NOT loaded)
	//for now FireBall, PowerUp, Coin, BadGuy implement Dynamic
	//anything added to DynamicLevelPart implement Dynamic

	long getID();//Dynamic ID uses same ID as unique ThreadSafeGImage unique ID
	void kill();
	/*
	 * @Override
	public void kill() {
		//LevelController.endCurrentLevel will loop through all dynamicLevelParts and set alive to alive = false
		//but we dont want to call kill() func because this will remove Dynamic from dynamiclevelparts as
		//it is being looped through in endCurrentLevel func
		//should only execute kill func if level is being played (!endingLevel)
		if (!LevelController.endingLevel()) {
			canvas.remove(this);
			alive = false;
			LevelController.currLevel.removeDynamic(this);
		}
	}
	 */
}
