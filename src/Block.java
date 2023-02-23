import acm.graphics.GCanvas;
import acm.graphics.GImage;
import java.awt.Image;
public abstract class Block extends GImage {
	//a Block is something mario cannot run through or jump through
	//MysteryBox, WoodBox, Pipe, etc basically anything that if mario tries to walk into,
	//it will halt him
	//TODO need to add more blocks for now only mystery box
	public Block(Image arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	
	
	
	
}
