//import jakarta.websocket.Session;
public class ServerToClientMessenger {
	//this class will hanle sending messages to the client: when to play sound, when to move images, when to switch images, showImageAndSetlocation, and hideImage (etc)
	//private static String lobbyId;
	private Lobby lobby;
	
	public ServerToClientMessenger(String lobbyId) {
		lobby = MyWebSocketServer.getLobby(lobbyId);
	}
	//public void setLobbyId(String id) {
		//lobbyId = id;
		//lobby = MyWebSocketServer.getLobby(id);
	//}
	
	
	//13 types of messages that server sends to client:
	//1. moveImage, 2. playSound, 3. replaceImage 
	//4. addLevelImageToScreen, and 5. removeImageFromScreen, 6. setVisibility
	//7. moveLevel, 8. moveMarioCharacter, 9. addCharacterImageToScreen, 10. removeAllImagesFromScreen,
	//11. sendImageFrontOrBack, 12. yourCharacter, 13. lobbyAlreadyFull
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
	//Message 12 is sent in MyWebSocketServer onMessage() sendMessage("{ \"type\": \"yourCharacter\", \"character\": \""+yourCharacter+"\"}", session);
	// -> (no function in this class needed for it)
	// -> it is to let the client know which character he will play as (mario, luigi, etc)
	//Message 13 is sent in MyWebSocketServer onOpen() (no function in this class needed for it)
	// -> it is to let client know that he has tried to join a lobby that is already full
	// -> session will be closed and client will try joining another lobby
	
	
	//we differentiate between levelimages and characterimages because on the client side (javascript)
	//client keeps track of images in two hashmaps: one for levelimages and one for characterimages (mario, luigi, etc)

	private void sendMessage(String message) {
		if (lobby!=null)
			MyWebSocketServer.sendMessage(message, lobby);
		//else System.out.println("SESSION NULLL");
	}

	public  void sendMoveImageMessage(long imageID, double dx, double dy) {
		String messageToClient = "{ \"type\": \"moveImage\", \"imageId\": \""+imageID+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		sendMessage(messageToClient);
	}

	public void sendPlaySoundMessage(String soundName) {
		String messageToClient = "{\"type\": \"playSound\", \"soundName\": \""+soundName+"\" }";
		sendMessage(messageToClient);
	}

	public void sendReplaceImageMessage(long oldImageID, String newImageName) {
		String messageToClient = "{ \"type\": \"replaceImage\", \"oldImageId\":\""+oldImageID+"\", \"newImageName\":\""+newImageName+"\" }";
		sendMessage(messageToClient);
	}

	public void sendAddLevelImageToScreenMessage(ThreadSafeGImage image) {
		String messageToClient = "{ \"type\": \"addLevelImageToScreen\", \"imageName\": \""+image.getMyImageName()+"\", \"id\":\""+image.getImageID()+"\", \"x\":\""+image.getX()+"\", \"y\":\""+image.getY()+"\" }";
		sendMessage(messageToClient);
	}

	public void sendRemoveImageFromScreenMessage(long imageID) {
		String messageToClient = "{ \"type\": \"removeImageFromScreen\", \"id\": \""+imageID+"\"}";
		sendMessage(messageToClient);
	}

	public void sendSetVisibilityMessage(long imageID, boolean visibility) {
		String messageToClient = "{ \"type\": \"setVisible\", \"imageId\": \""+imageID+"\", \"bool\":\""+visibility+"\" }";
		sendMessage(messageToClient);
	}

	public void sendMoveLevelMessage(double dx, double dy) {
		String messageToClient = "{ \"type\": \"moveLevel\", \"dx\": \""+dx+"\", \"dy\":\""+dy+"\" }";
		sendMessage(messageToClient);
	}

	public void sendMoveMarioMessage(long imageID, double dx, double dy) {
		String messageToClient = "{ \"type\": \"moveMarioCharacter\", \"imageId\": \""+imageID+"\", \"dx\":\""+dx+"\", \"dy\":\""+dy+"\" }";
		sendMessage(messageToClient);
	}

	public void sendAddCharacterImageToScreenMessage(ThreadSafeGImage image) {
		String messageToClient = "{ \"type\": \"addCharacterImageToScreen\", \"imageName\": \""+image.getMyImageName()+"\", \"id\":\""+image.getImageID()+"\", \"x\":\""+image.getX()+"\", \"y\":\""+image.getY()+"\" }";
		sendMessage(messageToClient);
	}
	
	public void sendRemoveAllImagesFromScreenMessage() {
		String messageToClient = "{ \"type\": \"removeAllImagesFromScreen\"}";
		sendMessage(messageToClient);
	}
	
	public void sendImageFrontOrBack(long imageID, boolean frontOrBack) {
		String messageToClient = "{ \"type\": \"setFrontOrBack\", \"imageId\": \""+imageID+"\", \"bool\":\""+frontOrBack+"\" }";
		sendMessage(messageToClient);
	}
	
}