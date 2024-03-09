
public class FloatingCoin extends Coin {
//COIN PART OF TRIANGLE OR RECTANGLE COIN GRIDS, NOT SPAWNED FROM MYSTERYBOX OR BRICK
	@Override 
	public void collectedByMario(Mario mario){
		if (collected()) return;
		CharacterStatsController.collectCoin(mario);
		SoundController.playCoinSound();
		coinState = Coin.COIN_STATE.COLLECTED;
		kill();
	}

	@Override
	public void move() throws InterruptedException {
		//this function should never be called, meant for MysteryBoxCoin
		//FloatingCoin is moved because its in dynamicLevelParts and spinned in FloatingCoinsBlock
	}
}
