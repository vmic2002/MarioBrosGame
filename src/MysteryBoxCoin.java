public class MysteryBoxCoin extends Coin {
	//coin spawned by mystery box (TODO should also be spwaned from brick)
	public static double dy = -0.2*MovingObject.getBaseLineSpeed();
	@Override 
	public void collectedByMario(Mario mario){
		if (collected()) return;
		//CharacterStatsController.collectCoin(mario); and SoundController.playCoinSound();
		//are already called when the mysterybox spawns a coin
		//so that the stat is not double counted and sound played twice if mario collects a coin from a mysterybox (which disappears on its own)
		coinState = Coin.COIN_STATE.COLLECTED;
		kill();
	}

	@Override
	public void move() throws InterruptedException {
		sendToBack();
		for (int i=0; i<20; i++) {
			this.move(0, dy);
			if (i%6==0) toggleState();
			if (collected())
				return;
			ThreadSleep.sleep(2);
		}
		coinState = COIN_STATE.COLLECTED;
		kill();
	}
}