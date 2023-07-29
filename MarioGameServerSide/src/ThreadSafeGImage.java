import java.awt.Image;

import acm.graphics.GImage;

public abstract class ThreadSafeGImage extends GImage {
	//movingObject extend ThreadSafeGImage because movingObjects can call move function
	//from multiple threads (when moving level or moving itself for example)
	//platforms extend ThreadSafeGImage because level can be moved from multiple threads
	//by multiple different marios

	public ThreadSafeGImage(MyImage arg0) {
		super(arg0);
	}
	//TODO still bugs ThreadSafeGImage
	@Override
	public synchronized void move(double dx, double dy) {
		//public void move(double dx, double dy) {
		//System.out.println("!!!!!!");
		super.move(dx, dy);
		//System.out.println("<<<<<MOVING IMAGE: "+getMyImageName());
		
		String messageToClient = "{ \"type\": \"moveImage\", \"imageName\": \""+getMyImageName()+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		System.out.println(messageToClient);
		ServerToClientMessenger.sendMessage(messageToClient);
		//TODO need to tell client by how much to move image (not exactly dx, dy if browser window is different size than GCanvas)
		//ServerToClientMessenger.sendMessage("<<<<<<IMAGE MOVING: "+this.getImage().toString());
		//{ "type": "moveImage", "imageName": "imageName", "dx":"10", "dy":"20" }
	}

	@Override
	public void setImage(Image i) {
		String oldImageName = "";
		try {
			oldImageName = getMyImageName();
		} catch (Exception e) {
			//this will happen when a ThreadSafeGImage is instantiated. I am assuming that the super constructor calls the setImage function
			//so calling getMyImageName() wont work since this.getImage() will be null (there can't be an oldImageName when the first image is being set!)
		}
		super.setImage(i);
		if (oldImageName.length()>0) {
			String messageToClient = "{ \"type\": \"replaceImage\", \"oldImageName\":\""+oldImageName+"\", \"newImageName\":\""+getMyImageName()+"\" }";
			System.out.println(messageToClient);
			ServerToClientMessenger.sendMessage(messageToClient);
			//TODO need to use ServerToClientMessenger.sendMessage to notify JS to change image when setImage is called

		}
		//{ "type": "replaceImage", "oldImageName":"luigiStanding", "newImageName":"luigiWalking" }
	}

	public String getMyImageName() {
		//this works as long as all ThreadSafeGImage use MyImage instead of Image
		return ((MyImage) this.getImage()).getName();
	}
}