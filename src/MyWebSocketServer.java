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
import java.io.IOException;
import java.util.Collections;
//import java.util.Set;
//import java.util.HashSet;
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

//@ServerEndpoint("/websocket/{lobbyId}/{numCharacters}/{username}")
@ServerEndpoint("/websocket/{lobbyId}/{username}")
public class MyWebSocketServer {


	//Store all active lobbies
	private static final Map<String, Lobby> lobbies = Collections.synchronizedMap(new HashMap<>());


	//System.out.println can be seen from apache-tomcat-10.1.11/logs/catalina.out
	//when application is running


	@OnOpen
	public void onOpen(Session session, @PathParam("lobbyId") String lobbyId, @PathParam("username") String username) {
		//int numPlayersInLobby;
		//int numPlayersWantedToPlay;
		synchronized (lobbies) { 		
			//USE SYNCHRONIZE {} BLOCK IN ONOPEN AND ONCLOSE ON LOBBIES for thread safety

			Lobby lobby = lobbies.computeIfAbsent(lobbyId, id -> new Lobby(id));
			/*
			 * This method is a convenience method introduced in Java 8. It checks if the specified key (lobbyId) is already present in the map (lobbies).
If the key is present, it returns the corresponding value (the existing Lobby object).
If the key is not present, it computes a new value using the given mapping function (in this case, id -> new Lobby(id)), inserts the new key-value pair into the map, and returns the newly created value.
			 */

			if (lobby.getSessions().size()  == Lobby.getMaxNumCharacters()) {
				//lobby already has max number of characters
				//cannot accept a new session, so just close the session
				//
				sendMessage("{ \"type\": \"lobbyAlreadyFull\"}", session);
				try {
					session.close();
				} catch (IOException e) {
					System.out.println("Error encountered when trying to close session.");
					e.printStackTrace();
				}
				return;
			}

			lobby.addSession(session);
			// Store the lobbyid and username in the session's user properties for later use
			session.getUserProperties().put("lobbyId", lobbyId);
			session.getUserProperties().put("username", username);



			sendMessage("Lobby ID: "+lobbyId, session);//FOR TESTING TODO comment
			sendMessage("TOTAL OF "+lobbies.size()+" lobies:", session);//FOR TESTING TODO comment
			// Notify all players in the lobby
			for (Session s : lobby.getSessions()) {
				sendMessage(username + " has joined the lobby!", s);
			}




			//TODO NEED TO MAKE SURE THAT two (or more) people who join the same lobby inputed different usernames
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		//System.out.println("Received message from client: " + message);
		synchronized (lobbies) {
			String lobbyId = (String) session.getUserProperties().get("lobbyId");
			Lobby lobby = lobbies.get(lobbyId);




			if (message.equals("ready")) {
				
				//need to wait for all sessions in a lobby to be ready
				session.getUserProperties().put("ready", true);
				
				for (Session s : lobby.getSessions()) {
					if (s.getUserProperties().get("ready") == null) {
						//a session isn't ready yet, so return
						return;
					}
				}
				
				//every session is ready!
				
				Mario.CHARACTER[] characters = Mario.CHARACTER.values();
				int i = 0;
				for (Session s : lobby.getSessions()) {
					//tell each session what their character will be
					sendMessage("{ \"type\": \"yourCharacter\", \"character\": \""+characters[i++]+"\"}", s);
				}

				//start game!
				MarioBrosGame.main(new String[] {lobbyId});
				
				//TODO CAN EACH LOBBY HAVE ITS OWN SEPERATE MAIN? OR DO WE HAVE TO CREATE NEW CONTAINER? IDK
			} else {
				processMessage(message, session);
				//TODO TODO BUGS COULD BE COMING FROM HERE, DEADLING WITH SESSION NOT LOBBY
			}
		}



	}

	@OnClose
	public void onClose(Session session) {
		//WEBSOCKET CONNECTION CLOSES WHEN CLIENT RELOADS THE PAGE
		System.out.println("<<<<<<<\t\tWebSocket connection closed: " + session.getId());

		synchronized (lobbies) {
			//USE SYNCHRONIZE {} BLOCK IN ONOPEN AND ONCLOSE ON LOBBIES for thread safety
			String lobbyId = (String) session.getUserProperties().get("lobbyId");
			Lobby lobby = lobbies.get(lobbyId);

			if (lobby != null) {
				lobby.removeSession(session);

				// Notify remaining players in the lobby
				for (Session s : lobby.getSessions()) {
					sendMessage(session.getUserProperties().get("username")+" has left the lobby.", s);
				}

				// Remove the lobby if it's empty
				if (lobby.getSessions().isEmpty()) {
					lobbies.remove(lobbyId);
					//TODO FREE UP THE LOBBY AND ASSOCIATED THREADS, CHARACTERS, CANVAS ETC
					GameThread.interruptAllMarioThreads();//TODO TODO BUGS COULD BE COMING FROM HERE, make sure to interrupt only threads from this lobby
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
			}
		}
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
		synchronized (lobbies) {
			Lobby lobby = lobbies.get(lobbyId);
			return lobby;
		}
	}
}
