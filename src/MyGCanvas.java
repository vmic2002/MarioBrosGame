import acm.graphics.GCanvas;
import acm.graphics.GObject;
import java.util.Iterator;
public class MyGCanvas extends GCanvas {

	public MyGCanvas() {
		super();
	}

	@Override
	public void remove(GObject o){
		try {
			ServerToClientMessenger.sendRemoveImageFromScreenMessage(((ThreadSafeGImage) o).getImageID());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		super.remove(o);
	}

	@Override
	public void removeAll() {
		ServerToClientMessenger.sendRemoveAllImagesFromScreenMessage();
		super.removeAll();
	}
}