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
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			if (mario.movingRight) {
				return;
			}
			mario.move(true);
		} else if (keyCode == KeyEvent.VK_LEFT){
			if (mario.movingLeft) {
				return;
			}
			mario.move(false);
		} else if (keyCode == KeyEvent.VK_UP) {
			mario.jump();
		} else if (keyCode == KeyEvent.VK_1) {
			mario.setToBig();
		} else if (keyCode == KeyEvent.VK_2) {
			mario.setToSmall();
		} else if (keyCode == KeyEvent.VK_3) {
			mario.setToFire();
		}
	}
																																																																																																															
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("Key released");
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			mario.movingRight = false;
			if (!mario.isJumping) mario.setToStanding(true);
		} else if (keyCode == KeyEvent.VK_LEFT){
			mario.movingLeft = false;
			if (!mario.isJumping) mario.setToStanding(false);
		}
	}
}
