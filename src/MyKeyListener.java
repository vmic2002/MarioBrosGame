import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*

 * TODO when more than one mario are playing in same level (mario and luigi for example)
 * TODO when user1 will hold down a key and then user2 holds down another one,
 * TODO user2 is canceling whaterver user1 was doing (spamming fireballs as firemario or swinging tail as cat mario)
 * TODO could fix this using while (key not released) instead of while (key pressed) 
 */
public class MyKeyListener implements KeyListener {
	private Mario[] characters;
	public MyKeyListener(Mario[] characters) {
		super();
		this.characters = characters;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//int keyCode = e.getKeyCode();
	}

	/*
	 * characters[0] is reserved the up/down/left/right arrows and K to shoot/swing tail...
	 * characters[1] is reserved the T/G/F/H keys and Q to shoot/swing tail...
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_1) {
			for (Mario m : characters) m.setToSmall();
		} else if (keyCode == KeyEvent.VK_2) {
			for (Mario m : characters) m.setToBig();
		} else if (keyCode == KeyEvent.VK_3) {
			for (Mario m : characters) m.setToFire();
		} else if (keyCode == KeyEvent.VK_4) {
			for (Mario m : characters) m.setToCat();
		} else {
			if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT ||
					keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_K || keyCode == KeyEvent.VK_UP) {
				//characters[0] is manipulated by these keys
				Mario mario = characters[0];
				if (!mario.alive) return;
				if (mario.goingIntoPipe) return;
				if (keyCode == KeyEvent.VK_RIGHT) {
					mario.move(true);
				} else if (keyCode == KeyEvent.VK_LEFT){
					mario.move(false);
				} else if (keyCode == KeyEvent.VK_DOWN){
					mario.setToCrouching();
				} else if (keyCode == KeyEvent.VK_K){
					if (mario.isFire)
						mario.shootFireBall();
					else if (mario.isCat)	
						mario.swingTail();
				} else if (keyCode == KeyEvent.VK_UP) {
					mario.jump();
				}
			} else if (keyCode == KeyEvent.VK_T || keyCode == KeyEvent.VK_F ||
					keyCode == KeyEvent.VK_G || keyCode == KeyEvent.VK_H || keyCode == KeyEvent.VK_Q) {
				//characters[1] is manipulated by these keys
				Mario mario = characters[1];
				if (!mario.alive) return;
				if (mario.goingIntoPipe) return;
				if (keyCode == KeyEvent.VK_H) {
					mario.move(true);
				} else if (keyCode == KeyEvent.VK_F){
					mario.move(false);
				} else if (keyCode == KeyEvent.VK_G){
					mario.setToCrouching();
				} else if (keyCode == KeyEvent.VK_Q){
					if (mario.isFire)
						mario.shootFireBall();
					else if (mario.isCat)	
						mario.swingTail();
				} else if (keyCode == KeyEvent.VK_T) {
					mario.jump();
				}
			}
		} 
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_LEFT ||
				keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP) {
			//characters[0] is manipulated by these keys
			Mario mario = characters[0];
			if (!mario.alive) return;
			if (mario.goingIntoPipe) return;
			if (keyCode == KeyEvent.VK_RIGHT) {
				mario.movingRight = false;
				if (mario.isCrouching) {
					return;
				}
				if (!mario.isJumping) mario.setToStanding(true);
			} else if (keyCode == KeyEvent.VK_LEFT){
				mario.movingLeft = false;
				if (mario.isCrouching) {
					return;
				}
				if (!mario.isJumping) mario.setToStanding(false);
			} else if (keyCode == KeyEvent.VK_DOWN){
				mario.stopCrouching();
			} else if (keyCode == KeyEvent.VK_UP) {
				mario.wayUpOrWayDown = false;
			}
		} else if (keyCode == KeyEvent.VK_H || keyCode == KeyEvent.VK_F ||
				keyCode == KeyEvent.VK_G || keyCode == KeyEvent.VK_T){
			//characters[1] is manipulated by these keys
			Mario mario = characters[1];
			if (!mario.alive) return;
			if (mario.goingIntoPipe) return;
			if (keyCode == KeyEvent.VK_H) {
				mario.movingRight = false;
				if (mario.isCrouching) {
					return;
				}
				if (!mario.isJumping) mario.setToStanding(true);
			} else if (keyCode == KeyEvent.VK_F){
				mario.movingLeft = false;
				if (mario.isCrouching) {
					return;
				}
				if (!mario.isJumping) mario.setToStanding(false);
			} else if (keyCode == KeyEvent.VK_G){
				mario.stopCrouching();
			} else if (keyCode == KeyEvent.VK_T) {
				mario.wayUpOrWayDown = false;
			}
		}
	}
}
