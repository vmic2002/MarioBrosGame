//import java.util.HashMap;
//import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import jakarta.websocket.Session;

public class Lobby {
	
	
	//each Lobby cannot have more than "MarioBrosGame.numPlayers" players, but only 1 player can still play on his own
    //so a Lobby can have in between [1 and "MarioBrosGame.numPlayers"] players in one session, inclusive
	//(player = character)
	
	private String lobbyId;
    private Set<Session> sessions;// = new HashSet<>();
    //each Lobby has its own set of sessions (1 session for each player in the lobby)

    public Lobby(String lobbyId) {
        this.lobbyId = lobbyId;
        sessions = new HashSet<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public String getLobbyId() {
        return lobbyId;
    }
    
    public static int getMaxNumCharacters() {
    	return MarioBrosGame.numPlayers;
    }
    
}
