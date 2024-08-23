

//import java.util.HashMap;
//import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import jakarta.websocket.Session;

public class Lobby {


	//each Lobby cannot have more than "MarioBrosGame.numPlayers" players, but only 1 player can still play on his own
	//so a Lobby can have in between [1 and "MarioBrosGame.numPlayers"] players, inclusive
	//(player = character)

	private String lobbyId;
	private Set<Session> sessions;// = new HashSet<>();
	//each Lobby has its own set of sessions (1 session for each player in the lobby)



	public ServerToClientMessenger messenger;
	public DynamicFactory dFactory;
	public StaticFactory sFactory;
	public LevelController levelController;
	public MyGCanvas canvas;
	public BillBlasterController billBlasterController;
	public Mario[] characters;
	public SoundController soundController;
	public VirtualClientKeyboard virtualClientKeyboard;

	//TODO TODO ADD CHARACTERSTATSCONTROLLER
	// TODO TODO ADD GameStatsController
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

	public void setObjects(ServerToClientMessenger messenger, DynamicFactory dFactory,  
			StaticFactory sFactory, LevelController levelController, MyGCanvas canvas, BillBlasterController billBlasterController
			, Mario[] characters, SoundController soundController, VirtualClientKeyboard virtualClientKeyboard) {
		this.messenger = messenger; 
		this.dFactory = dFactory; 
		this.sFactory = sFactory; 
		this.levelController = levelController;
		this.canvas = canvas;
		this.billBlasterController = billBlasterController;
		this.characters = characters;
		this.soundController = soundController;
		this.virtualClientKeyboard = virtualClientKeyboard;
	}
}
