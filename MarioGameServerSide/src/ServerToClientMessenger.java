import jakarta.websocket.Session;
public class ServerToClientMessenger {
	//this class will hanle sending messages to the client: when to play sound, when to move images
	private static String sessionId;
	private static Session session;
	public static void setSessionId(String id) {
		sessionId = id;
		session = MyWebSocketServer.getSession(id);
	}

	public static void sendMessage(String message) {
		//Server shoul contact client to:
		//MOVE AN IMAGE, PLAY A SOUND, REPLACE AN IMAGE WITH ANOTHER (SETIMAGE)
		if (session!=null)
			MyWebSocketServer.sendMessage(message, session);
		else System.out.println("SESSION NULLL");
	}
}