import java.util.ArrayList;
import acm.graphics.GImage;

public class LevelPart {
	public ArrayList<Platform> part;//TODO MAYBE CHANGE LEVEL PART TO ONE GIMAGE NOT ARRAYLIST
	public LevelPart(ArrayList<Platform> part){
		this.part = part; 
	}
	public void move(double dx , double dy) {
		for (ThreadSafeGImage image: part) image.move(dx, dy);		
	}
}