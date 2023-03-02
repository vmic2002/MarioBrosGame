import java.util.ArrayList;

import acm.graphics.GImage;

public class LevelPart {
	private double x,y;
	//x, y are the top left coordinate of leftmost and topmost image of the LevelPart
	public ArrayList<GImage> part;
	public LevelPart(ArrayList<GImage> part, double x, double y) {
		this.part = part; 
		this.x = x;
		this.y = y;
	}
	public void move(double dx , double dy) {
		for (GImage image: part) {
			image.move(dx, dy);
		}
		x += dx;
		y += dy;
	}
}
