import acm.graphics.GCanvas;
import acm.graphics.GObject;
import java.util.Iterator;
public class MyGCanvas extends GCanvas {

	public MyGCanvas() {
		super();
	}

	@Override
	public void remove(GObject o){
		super.remove(o);
		try {
			ServerToClientMessenger.sendRemoveImageFromScreenMessage(((ThreadSafeGImage) o).getImageID());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public void removeAll() {
		Iterator it = this.iterator();
		try {
			while (it.hasNext()) {
				ServerToClientMessenger.sendRemoveImageFromScreenMessage(((ThreadSafeGImage) it.next()).getImageID());
			}
		}  catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		super.removeAll();
	}

	//instead of Override canvas.add(GObject o) to call ServerToClientMessenger.sendAddImageToScreenMessage 
	//each time a GObject is added to the canvas, 
	//a message is sent in the Level constructor when the level is being instantiated (images are added to canvas before level is instantiated)
}
