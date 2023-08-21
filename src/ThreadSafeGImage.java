import java.awt.Image;
import java.util.concurrent.atomic.AtomicLong;

import acm.graphics.GImage;

public abstract class ThreadSafeGImage extends GImage {
	//movingObject extend ThreadSafeGImage because movingObjects can call move function
	//from multiple threads (when moving level or moving itself for example)
	//platforms extend ThreadSafeGImage because level can be moved from multiple threads
	//by multiple different marios

	private long id;//for JavaScript on client side to know which image to move etc
	public static AtomicLong ID_GENERATOR;//Atomic for concurrency
	public ThreadSafeGImage(MyImage arg0) {
		super(arg0);
		id =  ID_GENERATOR.getAndIncrement();
	}
	
	public static void initializeIDGenerator() {
		ID_GENERATOR = new AtomicLong(0);
	}
	
	public synchronized void moveMario(double dx, double dy) {
		super.move(dx, dy);
		ServerToClientMessenger.sendMoveMarioMessage(getImageID(), dx, dy);
	}
	
	
	public synchronized void moveAsPartOfLevel(double dx, double dy) {
		//see DynamicLevelPart and StaticLevelPart move functions, they call this func
		//one websocket message to move entire level by dx, dy (except other mario characters) 
		//instead of one websocket message PER image when moving level
		super.move(dx, dy);
	}
	
	//TODO still bugs ThreadSafeGImage
	@Override
	public synchronized void move(double dx, double dy) {
		//public void move(double dx, double dy) {
		//System.out.println("!!!!!!");
		super.move(dx, dy);
		//System.out.println("<<<<<MOVING IMAGE: "+getMyImageName());

		//String messageToClient = "{ \"type\": \"moveImage\", \"imageId\": \""+getImageID()+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		//System.out.println(messageToClient);
		ServerToClientMessenger.sendMoveImageMessage(getImageID(), dx, dy);
		//need to tell client by how much to move image
		//ServerToClientMessenger.sendMessage("<<<<<<IMAGE MOVING: "+this.getImage().toString());
		//{ "type": "moveImage", "imageId": "123", "dx":"10", "dy":"20" }
	}

	@Override
	public synchronized void setImage(Image i) {
		if (this.getImage()!=null) {
			//this.getImage is null when a ThreadSafeGImage is instantiated. I am assuming that the super constructor calls the setImage function
			//when a GImage is instantiated. so when a GImage is instantiated, setImage is called for the first time and this.getImage() is null.
			//in this case we dont want to send message to client to replace image
			
			//System.out.println(messageToClient);
			ServerToClientMessenger.sendReplaceImageMessage(getImageID(), ((MyImage) i).getName());
			//need to use ServerToClientMessenger.sendMessage to notify JS to change image when setImage is called
		}
		super.setImage(i);
		//{ "type": "replaceImage", "oldImageId":"luigiStanding", "newImageName":"luigiWalking" }
	}
	
	@Override
	public synchronized void setVisible(boolean b) {
		super.setVisible(b);
		
		//System.out.println(messageToClient);
		ServerToClientMessenger.sendSetVisibilityMessage(getImageID(), b);
	}
	

	public String getMyImageName() {
		//this works as long as all ThreadSafeGImage use MyImage instead of Image
		return ((MyImage) this.getImage()).getName();
	}

	public long getImageID() {return id;}
}