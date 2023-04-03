import java.util.ArrayList;
import acm.graphics.GImage;

public class DynamicLevelPart extends LevelPart {
	//for now dynamiclevel part is made up of power up or fireball
	//for level parts that are added dynamically (while level is playing) to a Level's dynamicLevelParts
	private long id;
	//each dynamic level part needs to be differentiated from another because
	//they are added/removed to/from dynamicLevelParts (hashmap) at the same time
	//id of dynamic level part is key in hashmap
	public DynamicLevelPart(ArrayList<GImage> part, long id) {
		super(part);
		this.id = id;
	}

	public long getID() {return id;}
	
	@Override
	public void move(double dx , double dy) {
		for (GImage image: part) {
			image.move(dx, dy);
			if (image instanceof FireBall) {
				((FireBall) image).hoppingX+=dx;
				((FireBall) image).hoppingY+=dy;
			}
		}
	}
}
