
public interface Dynamic {
	//an object in mario game implements Dynamic if it is dynamically added to level (as level is being played, NOT loaded)
	//for now FireBall and PowerUp and BulletBill implement Dynamic
	//anything added to DynamicLevelPart implements Dynamic. see DynamicLevelPart for more details on id
	void setID(long id);
	long getID();
}