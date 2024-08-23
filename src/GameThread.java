

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
//import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class GameThread extends Thread {
	//private static final Set<GameThread> gameThreads = Collections.synchronizedSet(new HashSet<>());
	
	private static Map<String, Set<GameThread>> gameThreads = new ConcurrentHashMap<String, Set<GameThread>>();
	//GameThread is for threads created by mario game (red turtle, coin etc), not native JVM threads
	//gameThreads is map from lobbyId -> set of GameThreads


	
	public GameThread(MyRunnable runnable, String threadName, String lobbyId) {
		super(runnable);
		this.setName(threadName);
		
		
		if (gameThreads.get(lobbyId) == null) {
		    gameThreads.put(lobbyId, Collections.synchronizedSet(new HashSet<>()));
		}
	   

	    // Add the thread to the set
	    gameThreads.get(lobbyId).add(this);
		
		//gameThreads.add(this);
		this.start();
	}

	public static void interruptAllMarioThreads(String lobbyId) {
		//interrupt all mario threads for this lobby
		for (GameThread t:gameThreads.get(lobbyId)) {
				t.interrupt();
		}
		gameThreads.remove(lobbyId);
	}
}
