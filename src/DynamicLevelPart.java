import java.util.ArrayList;
import acm.graphics.GImage;

public class DynamicLevelPart {
	//for level parts that are added dynamically (while level is playing) to a Level's dynamicLevelParts
	//private long id;
	//each dynamic level part needs to be differentiated from another because
	//they are added/removed to/from dynamicLevelParts (hashmap) at the same time
	//id of dynamic level part is key in hashmap
	public ArrayList<ThreadSafeGImage> part;
	public DynamicLevelPart(ArrayList<ThreadSafeGImage> part){
		this.part = part;
	}

	public void move(double dx , double dy) {
		for (ThreadSafeGImage image: part) {
			image.move(dx, dy);
			if (image instanceof FireBall) {
				((FireBall) image).hoppingX+=dx;
				((FireBall) image).hoppingY+=dy;
			} else if (image instanceof Tanooki) {
				((Tanooki) image).xBaseline+=dx;
				((Tanooki) image).yBaseline+=dy;
			}
		}
	}
}
