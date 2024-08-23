

import java.util.ArrayList;
import acm.graphics.GImage;

public class StaticLevelPart {
	public ArrayList<Platform> platforms;
	public StaticLevelPart(ArrayList<Platform> platforms){
		this.platforms = platforms; 
	}
	public void move(double dx , double dy) {
		for (ThreadSafeGImage image: platforms) image.moveAsPartOfLevel(dx, dy);
	}
}
