import java.util.ArrayList;
import acm.graphics.GImage;

public class StaticLevelPart {
	public ArrayList<Platform> part;
	public StaticLevelPart(ArrayList<Platform> part){
		this.part = part; 
	}
	public void move(double dx , double dy) {
		for (ThreadSafeGImage image: part) image.move(dx, dy);		
	}
}