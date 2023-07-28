import java.util.ArrayList;
import acm.graphics.GImage;

public class LevelPart {
	public ArrayList<GImage> part;//TODO MAYBE CHANGE LEVEL PART TO ONE GIMAGE NOT ARRAYLIST
	public LevelPart(ArrayList<GImage> part){
		this.part = part; 
	}
	public void move(double dx , double dy) {
		for (GImage image: part) image.move(dx, dy);		
	}
}