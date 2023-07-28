import java.awt.Image;

import acm.graphics.GImage;

public class ThreadSafeGImage extends GImage {
	//movingObject extend ThreadSafeGImage because movingObjects can call move function
	//from multiple threads (when moving level or moving itself for example)
	//platforms extend ThreadSafeGImage because level can be moved from multiple threads
	//by multiple different marios
	public ThreadSafeGImage(Image arg0) {
		super(arg0);
	}
	//TODO still bugs ThreadSafeGImage
	@Override
	public synchronized void move(double dx, double dy) {
	//public void move(double dx, double dy) {
		//System.out.println("!!!!!!");
		super.move(dx, dy);
		//ServerToClientMessenger.sendMessage("MOVING IMAGE");
		//TODO need to tell client by how much to move image (not exactly dx, dy if browser window is different size than GCanvas)
		//ServerToClientMessenger.sendMessage("<<<<<<IMAGE MOVING: "+this.getImage().toString());
		//{ "type": "moveImage", "imageName": "imageName", "dx":"10", "dy":"20" }
	}
	
	@Override
	public void setImage(Image i) {
		super.setImage(i);
		//TODO need to use ServerToClientMessenger.sendMessage to notify JS to change image when setImage is called
		//ServerToClientMessenger.sendMessage("NEED TO CHANGE IMAGE (in setImage func)");\
		//{ "type": "replaceImage", "oldImageName":"luigiStanding", "newImageName":"luigiWalking" }
	}
}