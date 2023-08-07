import jakarta.websocket.Session;
public class ServerToClientMessenger {
	//this class will hanle sending messages to the client: when to play sound, when to move images, when to switch images, showImageAndSetlocation, and hideImage
	private static String sessionId;
	private static Session session;
	public static void setSessionId(String id) {
		sessionId = id;
		session = MyWebSocketServer.getSession(id);
	}

	public static void sendMessage(String message) {
		//Server should contact client to:
		//MOVE AN IMAGE, PLAY A SOUND, REPLACE AN IMAGE WITH ANOTHER (SETIMAGE)
		//showImageAndSetlocation, and hideImage
		//total of 5 message types
		if (session!=null)
			MyWebSocketServer.sendMessage(message, session);
		else System.out.println("SESSION NULLL");
	}
	//5 types of messages that server sends to client:
	//1. moveImage, 2. playSound, 3. replaceImage 
	//4. addImageToScreen, and 5. removeImageFromScreen
	//all 5 messages are correctly sent and received by the client
	//Message 1 is sent in ThreadSafeGImage.move
	//Message 2 is sent in SoundController.playSound
	//Message 3 is sent in ThreadSafeGImage.setImage
	//Message 4 is sent in Level constructor (to add static and dynamic level parts at start of level) 
	//	and LevelController.addCharactersAtStartOfLevel (to add mario/luigi at beginning of level)
	//	and in DynamicFactory.sendMessageToClient (to add powerups, fireballs, bulletbillss, etc while level is being played)
	//Message 5 is sent in MyGCanvas.remove and removeAll
}