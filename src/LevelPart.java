import acm.graphics.GImage;

public class LevelPart {
	private int x,y;
	private GImage image;
	public LevelPart(GImage image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}
	public void move(double dx , double dy) {
		image.move(dx, dy);
		x += dx;
		y += dy;
	}
	
}
