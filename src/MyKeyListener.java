import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {
	private Mario mario;
	public MyKeyListener(Mario mario) {
		super();
		this.mario = mario;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		//int keyCode = e.getKeyCode();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!mario.alive) return;
		if (mario.goingIntoPipe) return;
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			mario.move(true);
		} else if (keyCode == KeyEvent.VK_LEFT){
			mario.move(false);
		} else if (keyCode == KeyEvent.VK_DOWN){
			mario.setToCrouching();
		} else if (keyCode == KeyEvent.VK_F){
			if (mario.isFire)
				mario.shootFireBall();
			else if (mario.isCat)	
				mario.swingTail();
		} else if (keyCode == KeyEvent.VK_UP) {
			mario.jump();
		} else if (keyCode == KeyEvent.VK_1) {
			mario.setToSmall();
		} else if (keyCode == KeyEvent.VK_2) {
			mario.setToBig();
		} else if (keyCode == KeyEvent.VK_3) {
			mario.setToFire();
		} else if (keyCode == KeyEvent.VK_4) {
			mario.setToCat();
		}
	}
																																																																																																															
	@Override
	public void keyReleased(KeyEvent e) {
		if (!mario.alive) return;
		if (mario.goingIntoPipe) return;
		int keyCode = e.getKeyCode();
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
	}
}
