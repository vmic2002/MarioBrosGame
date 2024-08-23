

import java.awt.image.BufferedImage;

public class MyImage extends BufferedImage {
	private String imageName;//needed to send image name to client when playing game on website so JavaScript knows which image to move etc
	public MyImage(BufferedImage bi, String imageName) {
		super(bi.getColorModel(), bi.getRaster(), bi.isAlphaPremultiplied(), null);
		this.imageName = imageName;
	}
	public String getName() {return imageName;}
}
