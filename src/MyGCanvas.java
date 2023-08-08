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
			sendHideMessage((ThreadSafeGImage) o);
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
				sendHideMessage((ThreadSafeGImage) it.next());
			}
		}  catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		super.removeAll();
	}

	private void sendHideMessage(ThreadSafeGImage i) {
		String messageToClient = "{ \"type\": \"removeImageFromScreen\", \"id\": \""+i.getImageID()+"\"}";
		ServerToClientMessenger.sendMessage(messageToClient);
		System.out.println(messageToClient);
	}

	//instead of Override canvas.add(GObject o) to call ServerToClientMessenger.sendMessage 
	//of type showImageAndSetlocation each time a GObject is added to the canvas, 
	//a message is sent in the Level constructor when the level is being instantiated (images are added to canvas before level is instantiated)
}
