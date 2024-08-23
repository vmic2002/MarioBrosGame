

import acm.graphics.GCanvas;
import acm.graphics.GObject;
import java.util.Iterator;
public class MyGCanvas extends GCanvas {
	private ServerToClientMessenger messenger;
	public MyGCanvas() {
		super();
		
	}
	
	public void setMessenger(ServerToClientMessenger messenger) {
		this.messenger = messenger;
	}

	@Override
	public void remove(GObject o){
		try {
			messenger.sendRemoveImageFromScreenMessage(((ThreadSafeGImage) o).getImageID());
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		super.remove(o);
	}

	@Override
	public void removeAll() {
		messenger.sendRemoveAllImagesFromScreenMessage();
		super.removeAll();
	}
}
