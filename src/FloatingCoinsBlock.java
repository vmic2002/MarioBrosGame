import java.util.ArrayList;

public class FloatingCoinsBlock {
	//FIXES PROBLEM OF EACH COIN SPINNING IN OWN THREAD
	//WITH THIS CLASS, EACH BLOCK OF FLOATING COINS (1 RECTANGLE, 1 TRIANGLE) SPINS IN 1 THREAD
	//EACH COIN IS IN DYNAMICLEVEL PARTS TO BE MOVED WHEN LEVEL IS MOVED
	//THIS CLASS IS ONLY TO SPIN THE FLOATING COINS
	private ArrayList<Coin> coins;
	public boolean stopThread;//set to true in LevelController.endCurrentLevel
	public FloatingCoinsBlock() {
		coins = new ArrayList<Coin>();
		stopThread = false;
	}

	public void addCoin(Coin c) {
		coins.add(c);
	}

	//START SPINNING ALL COINS IN THIS BLOCK (at start of level)
	//called in LevelController.startMovingObjects()
	public void startSpinningBlock() {
		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException{
				while (coins.size()>0 && !stopThread) {
					for (int i=0; i<coins.size(); i++) {
						Coin c = coins.get(i);
						if (!c.collected() && c.alive) {
							c.toggleState();
						} else {
							//kill and remove collected coins from the block
							c.kill();
							coins.remove(i);
							i--;
						}
					}
					ThreadSleep.sleep(Coin.pauseBetweenStates);
				}
				//THREAD DIES WHEN ALL COINS IN BLOCK ARE COLLECTED OR WHEN STOP THREAD SET TO TRUE WHEN ENDING CURRENT LEVEL IN LEVELCONTROLLER
			}
		},"floating coins block");
	}
}
