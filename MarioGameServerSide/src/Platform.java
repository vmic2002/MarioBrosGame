import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public class Platform extends ThreadSafeGImage {
	//a Platform is something mario cannot run through or jump through
	//MysteryBox, WoodBox, Pipe, ground, etc basically anything that if mario tries to walk into,
	//it will halt him
	//TODO need to add more platforms for now only mystery box, grass mountain, bill blaster
	public Platform(Image arg0) {
		super(arg0);
	}
}