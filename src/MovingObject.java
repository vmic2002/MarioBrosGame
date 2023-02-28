import acm.graphics.GCanvas;
import acm.graphics.GImage;
import acm.graphics.GObject;

import java.awt.Image;
public class MovingObject extends GImage {
//fireball, mushroom, fire flower, leaf extends MovingObject
	public MovingObject(Image arg0) {
		super(arg0);
	}
	public void move() {
		//subclass will define this
	}

}
