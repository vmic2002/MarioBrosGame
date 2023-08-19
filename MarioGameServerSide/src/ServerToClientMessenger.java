import jakarta.websocket.Session;
public class ServerToClientMessenger {
	//this class will hanle sending messages to the client: when to play sound, when to move images, when to switch images, showImageAndSetlocation, and hideImage
	private static String sessionId;
	private static Session session;
	public static void setSessionId(String id) {
		sessionId = id;
		session = MyWebSocketServer.getSession(id);
	}
	
	//6 types of messages that server sends to client:
	//1. moveImage, 2. playSound, 3. replaceImage 
	//4. addImageToScreen, and 5. removeImageFromScreen, 6. setVisibility
	//all 5 messages are correctly sent and received by the client
	//Message 1 is sent in ThreadSafeGImage.move
	//Message 2 is sent in SoundController.playSound
	//Message 3 is sent in ThreadSafeGImage.setImage
	//Message 4 is sent in Level constructor (to add static and dynamic level parts at start of level) 
	//	and LevelController.addCharactersAtStartOfLevel (to add mario/luigi at beginning of level)
	//	and in DynamicFactory (to add powerups, fireballs, bulletbillss, etc while level is being played)
	//Message 5 is sent in MyGCanvas.remove and removeAll
	//Message 6 is when mario/luigi flashes after being hit. it is sent in ThreadSafeGImage.setVisible()
	
	private static void sendMessage(String message) {
		if (session!=null)
			MyWebSocketServer.sendMessage(message, session);
		//else System.out.println("SESSION NULLL");
	}
	
	public static void sendMoveImageMessage(long imageID, double dx, double dy) {
		String messageToClient = "{ \"type\": \"moveImage\", \"imageId\": \""+imageID+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		sendMessage(messageToClient);
	}
	
	public static void sendPlaySoundMessage(String soundName) {
		String messageToClient = "{\"type\": \"playSound\", \"soundName\": \""+soundName+"\" }";
		sendMessage(messageToClient);
	}
	
	public static void sendReplaceImageMessage(long oldImageID, String newImageName) {
		String messageToClient = "{ \"type\": \"replaceImage\", \"oldImageId\":\""+oldImageID+"\", \"newImageName\":\""+newImageName+"\" }";
		sendMessage(messageToClient);
	}
	
	public static void sendAddImageToScreenMessage(ThreadSafeGImage image) {
		String messageToClient = "{ \"type\": \"addImageToScreen\", \"imageName\": \""+image.getMyImageName()+"\", \"id\":\""+image.getImageID()+"\", \"x\":\""+image.getX()+"\", \"y\":\""+image.getY()+"\" }";
		sendMessage(messageToClient);
	}
	
	public static void sendRemoveImageFromScreenMessage(long imageID) {
		String messageToClient = "{ \"type\": \"removeImageFromScreen\", \"id\": \""+imageID+"\"}";
		sendMessage(messageToClient);
	}
	
	public static void sendSetVisibilityMessage(long imageID, boolean visibility) {
		String messageToClient = "{ \"type\": \"setVisible\", \"imageId\": \""+imageID+"\", \"bool\":\""+visibility+"\" }";
		sendMessage(messageToClient);
	}
}