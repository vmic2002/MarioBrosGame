import jakarta.websocket.Session;
public class ServerToClientMessenger {
	//this class will hanle sending messages to the client: when to play sound, when to move images, when to switch images, showImageAndSetlocation, and hideImage
	private static String sessionId;
	private static Session session;
	public static void setSessionId(String id) {
		sessionId = id;
		session = MyWebSocketServer.getSession(id);
	}
	//11 types of messages that server sends to client:
	//1. moveImage, 2. playSound, 3. replaceImage 
	//4. addLevelImageToScreen, and 5. removeImageFromScreen, 6. setVisibility
	//7. moveLevel, 8. moveMarioCharacter, 9. addCharacterImageToScreen, 10. removeAllImagesFromScreen, 11. sendImageFrontOrBack
	//Message 1 is sent in ThreadSafeGImage.move 
	//-> called when moving object (NOT mario characters) calls the ThreadSafeGImage.move func and NOT when moving whole level (=moving static level parts and dynamic level parts)
	//-> see Message 7 to moveLevel and Message 8 to move mario character
	//Message 2 is sent in SoundController.playSound
	//Message 3 is sent in ThreadSafeGImage.setImage
	//Message 4 is sent in Level constructor (to add static and dynamic level parts at start of level) 
	//	and in DynamicFactory (to add powerups, fireballs, bulletbillss, etc while level is being played)
	//Message 5 is sent in MyGCanvas.remove
	//Message 6 is when mario/luigi flashes after being hit. it is sent in ThreadSafeGImage.setVisible()
	//Message 7 is sent in Level.moveLevelAsynchonously
	// -> see ThreadSafeGImage.moveAsPartOfLevel
	//Message 8 is sent in ThreadSafeGImage.moveMario
	//Message 9 is sent in LevelController.addCharactersAtStartOfLevel (to add mario/luigi at beginning of level)
	//Message 10 is sent in MyGCanvas.removeAll()
	//Message 11 is sent in ThreadSafeGImage

	//we differentiate between levelimages and characterimages because on the client side (javascript)
	//client keeps track of images in two hashmaps: one for levelimages and one for characterimages (mario, luigi, etc)

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

	public static void sendAddLevelImageToScreenMessage(ThreadSafeGImage image) {
		String messageToClient = "{ \"type\": \"addLevelImageToScreen\", \"imageName\": \""+image.getMyImageName()+"\", \"id\":\""+image.getImageID()+"\", \"x\":\""+image.getX()+"\", \"y\":\""+image.getY()+"\" }";
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

	public static void sendMoveLevelMessage(double dx, double dy) {
		String messageToClient = "{ \"type\": \"moveLevel\", \"dx\": \""+dx+"\", \"dy\":\""+dy+"\" }";
		sendMessage(messageToClient);
	}

	public static void sendMoveMarioMessage(long imageID, double dx, double dy) {
		String messageToClient = "{ \"type\": \"moveMarioCharacter\", \"imageId\": \""+imageID+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		sendMessage(messageToClient);
	}

	public static void sendAddCharacterImageToScreenMessage(ThreadSafeGImage image) {
		String messageToClient = "{ \"type\": \"addCharacterImageToScreen\", \"imageName\": \""+image.getMyImageName()+"\", \"id\":\""+image.getImageID()+"\", \"x\":\""+image.getX()+"\", \"y\":\""+image.getY()+"\" }";
		sendMessage(messageToClient);
	}
	
	public static void sendRemoveAllImagesFromScreenMessage() {
		String messageToClient = "{ \"type\": \"removeAllImagesFromScreen\"}";
		sendMessage(messageToClient);
	}
	
	public static void sendImageFrontOrBack(long imageID, boolean frontOrBack) {
		String messageToClient = "{ \"type\": \"setFrontOrBack\", \"imageId\": \""+imageID+"\", \"bool\":\""+frontOrBack+"\" }";
		sendMessage(messageToClient);
	}
	
}