

import acm.graphics.GObject;

public class Hourglass extends PowerUp {
	private static MyImage hourGlassImage;
	public Hourglass(Lobby lobby) {
		super(hourGlassImage, lobby);
	}

	@Override
	public void move() throws InterruptedException {

	}

	@Override
	public boolean inContactWith(GObject x, boolean horizontalOrVertical) {
		return false;
	}

	public static void setObject(MyImage hourGlassImage1) {
		hourGlassImage = hourGlassImage1;
	}
	
}
