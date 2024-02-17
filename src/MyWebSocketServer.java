/*export CATALINA_HOME=/Users/victormicha/Desktop/apache-tomcat-10.1.11

export JRE_HOME=/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home

$CATALINA_HOME/bin/startup.sh
$CATALINA_HOME/bin/startup.sh
$CATALINA_HOME/bin/shutdown.sh


 * 
 * MarioGameClientSide is used to run python command to see website client side (where video game will be played and seen)
 * and MarioGameServerSide is used to create the .war file
 * 
 * 
 *  Within ~/Desktop/apache-tomcat-10.1.11/webapps, putting a MarioGameServerSide.war file and then running $CATALINA_HOME/bin/startup.sh will 
 *  create a directory MarioGameServerSide within webapps and deploy the application.

	Go to http://localhost:8080/MarioGameServerSide/ to see the website

	Use http://localhost:8080/manager/html to see if it is running and more details
	for manager/html, use username="admin" password="victor1". can configure/change this in apache-tomcat-10.1.11/conf/tomcat-users.xml

	Need to use python command to see website on port 8081 to send messages from client side

	Can use makeWARFileAndPlaceInWebappsDir.sh in MarioBrosGame directory to create new .war file from
	.class and .java files in bin and src directories and place .war file in webapps dir automatically!

	!!!!Sending messages from client side works! Server responds back!!!!!
	
	TODO NEED TO DO ONLINE MULTIPLAYER each new window that connects to the server has its own session ID, for online multiplayer (one plays mario one plays luigi)
	
	
	
 * 
 *  
 */
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

@ServerEndpoint("/websocket/{username}")
public class MyWebSocketServer {

	// Store all active WebSocket sessions
	private static final Set<Session> activeSessions = Collections.synchronizedSet(new HashSet<>());


	//System.out.println can be seen from apache-tomcat-10.1.11/logs/catalina.out
	//when application is running
	

	@OnOpen
	public void onOpen(Session session, @PathParam("username") String username) {
		//System.out.println("WebSocket connection opened: " + session.getId());
		//System.out.println("Username: "+username);
		// Add the new session to the activeSessions set
		activeSessions.add(session);
		//System.out.println("CALLING MAIN FUNCTION");
		
		sendMessage("SESSION ID: "+session.getId(), session);
		sendMessage("TOTAL OF "+activeSessions.size()+" sessions:", session);
		String message = "IDs: ";
		for (Session s:activeSessions) {
			message+=s.getId()+", ";
		}
		sendMessage(message, session);
		
		MarioBrosGame.main(new String[] {session.getId()});

		
		
		
	
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		//System.out.println("Received message from client: " + message);

		// Process the received message
		//String response = "Server response: " + message;

		processMessage(message, session);
		
		// Send the response back to the client
		//sendMessage(response, session);
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("<<<<<<<\t\tWebSocket connection closed: " + session.getId());
		// Remove the closed session from the activeSessions set
		activeSessions.remove(session);
		System.out.println("INTERRUPTING ALL GAME THREADS");
		//TODO MAYHBE TRY INTERRUPTING ALL THREADS NOT ONLY GAMETHREADS
		GameThread.interruptAllMarioThreads();
		//see MyRunnable.java and GameThread.java
		//interrupting all game threads fixes bug when client reloads page, all threads from previous session have to be interrupted
		//calling System.exit doesnt work
		//OR ELSE WILL GET ERROR MESSAGE: 
		/*
		 * WARNING [Thread-1] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesThreads The web     
		 * application [MarioGameServerSide] appears to have started a thread named [mystery box changing states OR AWT-EventQueue-0 (FOR EXAMPLE)] 
		 * but has failed to stop it. This is very likely to create a memory leak. Stack trace of thread
		 */
		
	}

	@OnError
	public void onError(Session session, Throwable error) {
		System.out.println("WebSocket error occurred: " + session.getId());
		error.printStackTrace();
	}
	
	private void processMessage(String message, Session session) {
		//expected JSON of form: { keyEvent: "keyEvent", key: "key", character: "character" }
		//JSON format will probably change since no character field is needed if it can be determined by sessionID for multiplayer online play
		//keyEvent is keyPressed or keyReleased
		//key is either ArrowUp, ArrowDown, ArrowLeft, ArrowRight, q
		//character is either Mario, Luigi
		JSONObject json = (JSONObject) JSONValue.parse(message);
		
		//sendMessage("MESSAGE PROCESSED: "+json.get("keyEvent")+" " +json.get("key") + " "+ json.get("character"), session);
		boolean keyPressedOrReleased = ((String) json.get("keyEvent")).equals("keyPressed");
		VirtualClientKeyboard.keyPressed(keyPressedOrReleased, (String) json.get("key"), (String) json.get("character"));
		//sendMessage("AFTERKEYABORD", session);
		//System.out.println(json.toString());  
		//String technology = json.getString("technology");  
	}

	public synchronized static void sendMessage(String message, Session session) {
		try {
			if (session.isOpen())
				session.getBasicRemote().sendText(message);
			else
				System.exit(1);
			//session.getAsyncRemote().sendText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Session getSession(String sessionId) {
		for (Session s: activeSessions)
			if (s.getId().equals(sessionId))
				return s;
		return null;
	}
}
