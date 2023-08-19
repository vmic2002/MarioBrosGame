import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
public class GameThread extends Thread {
	private static final Set<GameThread> gameThreads = Collections.synchronizedSet(new HashSet<>());
	//GameThread is for threads created by mario game (red turtle, coin etc), not native JVM threads
	//marioThreads keeps track of these threads

	public GameThread(MyRunnable runnable, String threadName) {
		super(runnable);
		this.setName(threadName);
		gameThreads.add(this);
		this.start();
	}

	public static void interruptAllMarioThreads() {
		//TODO NEED TO CALL THIS FUNC IN MYWEBSOCKETSERVER.ONCLOSE
		for (GameThread t:gameThreads)
			t.interrupt();
		gameThreads.clear();
	}
}