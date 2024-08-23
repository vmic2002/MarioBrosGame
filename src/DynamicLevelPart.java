

public class DynamicLevelPart {
	//for level parts that are added dynamically (while level is playing) to a Level's dynamicLevelParts
	//see Dynamic.java
	public Dynamic part;
	public DynamicLevelPart(Dynamic part){
		this.part = part;
	}

	public void move(double dx , double dy) {
		ThreadSafeGImage image = (ThreadSafeGImage) part;
		image.moveAsPartOfLevel(dx, dy);
		if (image instanceof FireBall) {
			((FireBall) image).hoppingX+=dx;
			((FireBall) image).hoppingY+=dy;
		} else if (image instanceof Tanooki) {
			((Tanooki) image).xBaseline+=dx;
			((Tanooki) image).yBaseline+=dy;
		}
	}
}
