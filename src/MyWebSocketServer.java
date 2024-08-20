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
import java.util.Map;
import java.util.HashMap;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;


import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

//@ServerEndpoint("/websocket/{username}")
@ServerEndpoint("/websocket/{lobbyId}/{numCharacters}/{username}")
public class MyWebSocketServer {

	// Store all active WebSocket sessions
	//private static final Set<Session> activeSessions = Collections.synchronizedSet(new HashSet<>());

	//Store all active lobbies
	private static final Map<String, Lobby> lobbies = Collections.synchronizedMap(new HashMap<>());


	//System.out.println can be seen from apache-tomcat-10.1.11/logs/catalina.out
	//when application is running


	@OnOpen
	public void onOpen(Session session, @PathParam("lobbyId") String lobbyId, @PathParam("numCharacters") String numCharacters, @PathParam("username") String username) {

		


		Lobby lobby = lobbies.computeIfAbsent(lobbyId, id -> new Lobby(id));
		/*
		 * This method is a convenience method introduced in Java 8. It checks if the specified key (lobbyId) is already present in the map (lobbies).
If the key is present, it returns the corresponding value (the existing Lobby object).
If the key is not present, it computes a new value using the given mapping function (in this case, id -> new Lobby(id)), inserts the new key-value pair into the map, and returns the newly created value.
		 */

		// Store the lobbyid in the session's user properties for later use
		session.getUserProperties().put("lobbyId", lobbyId);


		lobby.addSession(session);

		//TODO NEED TO MAKE SURE THAT two (or more) people who join the same lobby inputed the same number of numCharacteers and different usernames



		sendMessage("Lobby ID: "+lobbyId, session);
		sendMessage("TOTAL OF "+lobbies.size()+" lobies:", session);

		// Notify all players in the lobby
		for (Session s : lobby.getSessions()) {
			sendMessage(username + " has joined the lobby.", s);
		}

		// Start the game for the lobby, if needed
		int numPlayers;
		try {
			numPlayers = Integer.parseInt(numCharacters);
			if (numPlayers < 1 || numPlayers > Lobby.getMaxNumCharacters()) {
				numPlayers = 1;
				//if client gives numPlayers that is out of range, such as 0 or 100,
				//then default value of 1 numPlayer is used
			}
		} catch (Exception e) {
			//NumberFormatException
			numPlayers = 1;
		}
	
		if (lobby.getSessions().size() == numPlayers) {
			// Start the game, e.g., start a new thread to handle game logic
			//TODO UNCOMMENT MarioBrosGame.main(new String[] {lobbyId});
			//TODO NEED TO FIGURE OUT WHICH PLAYER IS MARIO AND WHICH IS LUIGI
			sendMessage("can start playing!", session);
		} else {
			//NOT ENOUGH PLAYERS, NEED TO WAIT FOR MORE
			//DONT DO ANYTHING
			sendMessage("not enough players yet! Waiting on " + ( numPlayers - lobby.getSessions().size())+ " more players...", session);
		}




		///////////////////////////////////////////////////////////









		/*
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
		 */




	}

	@OnMessage
	public void onMessage(String message, Session session) {
		//System.out.println("Received message from client: " + message);

		//String lobbyId = (String) session.getUserProperties().get("lobbyId");
		//Lobby lobby = lobbies.get(lobbyId);
		//
		//if (lobby != null) {
	//UNCOMMENT TODO TODO TODO	processMessage(message, session);
		//}


//TODO TODO BUGS COULD BE COMING FROM HERE, DEADLING WITH SESSION NOT LOBBY






		// Send the response back to the client
		sendMessage("received message: "+message, session);
	}

	@OnClose
	public void onClose(Session session) {

		//WEBSOCKET CONNECTION CLOSES WHEN CLIENT RELOADS THE PAGE
		System.out.println("<<<<<<<\t\tWebSocket connection closed: " + session.getId());

		String lobbyId = (String) session.getUserProperties().get("lobbyId");
		Lobby lobby = lobbies.get(lobbyId);

		if (lobby != null) {
			lobby.removeSession(session);

			// Notify remaining players in the lobby
			for (Session s : lobby.getSessions()) {
				sendMessage("A player has left the lobby.", s);
			}

			// Remove the lobby if it's empty
			if (lobby.getSessions().isEmpty()) {
				lobbies.remove(lobbyId);
				//UNCOMMENT TODO TODO GameThread.interruptAllMarioThreads();//TODO TODO BUGS COULD BE COMING FROM HERE
			}
		}





		//WEBSOCKET CONNECTION CLOSES WHEN CLIENT RELOADS THE PAGE
		//	System.out.println("<<<<<<<\t\tWebSocket connection closed: " + session.getId());
		// Remove the closed session from the activeSessions set
		//activeSessions.remove(session);
		//System.out.println("INTERRUPTING ALL GAME THREADS");

		//GameThread.interruptAllMarioThreads();
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

	public synchronized static void sendMessage(String message, Lobby lobby) {
		//send message to every session in the lobby
		for (Session s : lobby.getSessions()) {
			sendMessage(message, s);
		}
	}

	public synchronized static void sendMessage(String message, Session session) {
		try {
			//WEBSOCKET CONNECTION CLOSES WHEN CLIENT RELOADS THE PAGE
			if (session.isOpen())
				session.getBasicRemote().sendText(message);
			else {
				System.out.println("\t\t---------Trying to send message when session is closed!");
				/*
				 * 
				 *

		java.io.IOException: The transformer has been closed and may no longer be used
	at org.apache.tomcat.websocket.PerMessageDeflate.sendMessagePart(PerMessageDeflate.java:354)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendMessageBlock(WsRemoteEndpointImplBase.java:283)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendMessageBlock(WsRemoteEndpointImplBase.java:250)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendString(WsRemoteEndpointImplBase.java:192)
	at org.apache.tomcat.websocket.WsRemoteEndpointBasic.sendText(WsRemoteEndpointBasic.java:36)
	at MyWebSocketServer.sendMessage(MyWebSocketServer.java:148)

	  OR 

	  java.io.IOException: The current thread was interrupted while waiting for a blocking send to complete
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendMessageBlock(WsRemoteEndpointImplBase.java:303)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendMessageBlock(WsRemoteEndpointImplBase.java:250)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendString(WsRemoteEndpointImplBase.java:192)
	at org.apache.tomcat.websocket.WsRemoteEndpointBasic.sendText(WsRemoteEndpointBasic.java:36)
	at MyWebSocketServer.sendMessage(MyWebSocketServer.java:148)

	OR 

		java.lang.IllegalStateException: Message will not be sent because the WebSocket session has been closed
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.writeMessagePart(WsRemoteEndpointImplBase.java:450)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendMessageBlock(WsRemoteEndpointImplBase.java:308)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendMessageBlock(WsRemoteEndpointImplBase.java:250)
	at org.apache.tomcat.websocket.WsRemoteEndpointImplBase.sendString(WsRemoteEndpointImplBase.java:192)
	at org.apache.tomcat.websocket.WsRemoteEndpointBasic.sendText(WsRemoteEndpointBasic.java:36)
	at MyWebSocketServer.sendMessage(MyWebSocketServer.java:148)
				 */


				//System.exit(1);
				//INSTEAD OF EXITING when trying to send message on closed connection, JUST DONT SEND MESSAGE (can't anyways)! and keep playing
				//message doesn't have to be sent anyways cause client reloaded page so new session will be started

				//Since a new WebSocket session will be established with the page reload, any unsent messages are no longer relevant.
			}
			//session.getAsyncRemote().sendText(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Lobby getLobby(String lobbyId) {
		Lobby lobby = lobbies.get(lobbyId);
		return lobby;
	}
}
