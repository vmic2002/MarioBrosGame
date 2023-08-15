import acm.graphics.GObject;

public class Hourglass extends PowerUp {
	private static MyImage hourGlassImage;
	public Hourglass() {
		super(hourGlassImage);
	}

	@Override
	public void move() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		// TODO Auto-generated method stub
		return false;
	}

	public static void setObject(MyImage hourGlassImage1) {
		hourGlassImage = hourGlassImage1;
	}
	
}
