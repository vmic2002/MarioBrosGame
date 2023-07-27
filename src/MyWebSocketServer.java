/*export CATALINA_HOME=/Users/victormicha/Desktop/apache-tomcat-10.1.11

export JRE_HOME=/Library/Java/JavaVirtualMachines/jdk-15.0.1.jdk/Contents/Home

$CATALINA_HOME/bin/startup.sh

$CATALINA_HOME/bin/shutdown.sh


 * 
 * In ~/Desktop, MarioGameClientSide is used to run python command to see website client side (where video game will be played and seen)
 * and MarioGameServerSide is used to create the .war file
 * 
 * 
 *  Within ~/Desktop/apache-tomcat-10.1.11/webapps, putting a MarioGameServerSide.war file and then running $CATALINA_HOME/bin/startup.sh will 
 *  create a directory MarioGameServerSide within webapps and deploy the application.
	
	Go to http://localhost:8080/MarioGameServerSide/ to see the website

	Use http://localhost:8080/manager/html to see if it is running and more details
	for manager/html, use username="admin" password="victor1". can configure/change this in apache-tomcat-10.1.11/conf/tomcat-users.xml
	
	Need to use python command to see website on port 8081 to send messages from client side
	!!!!Sending messages from client side works! Server responds back!!!!!
	TODO need to try to send messages to move images on client side!
	TODO could write very short script to cp all .java and .class files to MarioGameServerSide to automate process of making .war file
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


@ServerEndpoint("/websocket/{username}")
public class MyWebSocketServer {

    // Store all active WebSocket sessions
    private static final Set<Session> activeSessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        System.out.println("WebSocket connection opened: " + session.getId());
        System.out.println("Username: "+username);
        // Add the new session to the activeSessions set
        activeSessions.add(session);
        //sendMessage("CONNECTION OPENED!", session);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("Received message from client: " + message);

        // Process the received message
        String response = "Server response: " + message.toUpperCase();

        // Send the response back to the client
        sendMessage(response, session);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("WebSocket connection closed: " + session.getId());

        // Remove the closed session from the activeSessions set
        activeSessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("WebSocket error occurred: " + session.getId());
        error.printStackTrace();
    }

    private void sendMessage(String message, Session session) {
        try {
            session.getAsyncRemote().sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}