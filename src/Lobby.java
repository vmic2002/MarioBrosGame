

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
	public CharacterStatsController characterStatsController;//TODO ACTUALLY USE THE STATS AND POWERUPS IN RESERVE ETC
	public GameStatsController gameStatsController;
	
	
	
	private boolean started;
	
	
	
	public Lobby(String lobbyId) {
		this.lobbyId = lobbyId;
		sessions = new HashSet<>();
		started = false;
	}
	
	public void start() {
		started = true;

		//every session is ready!

		Mario.CHARACTER[] characters = Mario.CHARACTER.values();
		int i = 0;
		for (Session s : this.getSessions()) {
			//tell each session what their character will be
			MyWebSocketServer.sendMessage("{ \"type\": \"yourCharacter\", \"character\": \""+characters[i++]+"\"}", s);
		}
		
		
		
		//start game!
		MarioBrosGame.main(new String[] {lobbyId});
	}
	
	public boolean hasStarted() {return started;}

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
			, Mario[] characters, SoundController soundController, VirtualClientKeyboard virtualClientKeyboard,
			CharacterStatsController characterStatsController, GameStatsController gameStatsController) {
		this.messenger = messenger; 
		this.dFactory = dFactory; 
		this.sFactory = sFactory; 
		this.levelController = levelController;
		this.canvas = canvas;
		this.billBlasterController = billBlasterController;
		this.characters = characters;
		this.soundController = soundController;
		this.virtualClientKeyboard = virtualClientKeyboard;
		this.characterStatsController = characterStatsController;
		this.gameStatsController = gameStatsController;
	}
}
