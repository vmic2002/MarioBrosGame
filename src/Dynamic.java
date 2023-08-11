
public interface Dynamic {
	//an object in mario game implement Dynamic if it is dynamically added to level (as level is being played, NOT loaded)
	//for now FireBall, PowerUp, Coin, BadGuy implement Dynamic
	//anything added to DynamicLevelPart implement Dynamic

	void setID(long id);
	long getID();
	void kill();
}