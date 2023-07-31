import java.awt.Image;
import java.util.concurrent.atomic.AtomicLong;

import acm.graphics.GImage;

public abstract class ThreadSafeGImage extends GImage {
	//movingObject extend ThreadSafeGImage because movingObjects can call move function
	//from multiple threads (when moving level or moving itself for example)
	//platforms extend ThreadSafeGImage because level can be moved from multiple threads
	//by multiple different marios

	private long id;//for JavaScript on client side to know which image to move etc
	public static AtomicLong ID_GENERATOR = new AtomicLong();//Atomic for concurrency
	public ThreadSafeGImage(MyImage arg0) {
		super(arg0);
		id =  ID_GENERATOR.getAndIncrement();
	}
	//TODO still bugs ThreadSafeGImage
	@Override
	public synchronized void move(double dx, double dy) {
		//public void move(double dx, double dy) {
		//System.out.println("!!!!!!");
		super.move(dx, dy);
		//System.out.println("<<<<<MOVING IMAGE: "+getMyImageName());

		String messageToClient = "{ \"type\": \"moveImage\", \"imageId\": \""+getID()+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		//System.out.println(messageToClient);
		ServerToClientMessenger.sendMessage(messageToClient);
		//need to tell client by how much to move image
		//ServerToClientMessenger.sendMessage("<<<<<<IMAGE MOVING: "+this.getImage().toString());
		//{ "type": "moveImage", "imageId": "123", "dx":"10", "dy":"20" }
	}

	@Override
	public void setImage(Image i) {
		if (this.getImage()!=null) {
			//this.getImage is null when a ThreadSafeGImage is instantiated. I am assuming that the super constructor calls the setImage function
			//when a GImage is instantiated. so when a GImage is instantiated, setImage is called for the first time and this.getImage() is null.
			//in this case we dont want to send message to client to replace image
			String messageToClient = "{ \"type\": \"replaceImage\", \"oldImageId\":\""+getID()+"\", \"newImageName\":\""+((MyImage) i).getName()+"\" }";
			//System.out.println(messageToClient);
			ServerToClientMessenger.sendMessage(messageToClient);
			//need to use ServerToClientMessenger.sendMessage to notify JS to change image when setImage is called
		}
		super.setImage(i);
		//{ "type": "replaceImage", "oldImageId":"luigiStanding", "newImageName":"luigiWalking" }
	}

	public String getMyImageName() {
		//this works as long as all ThreadSafeGImage use MyImage instead of Image
		return ((MyImage) this.getImage()).getName();
	}

	public long getID() {return id;}
}