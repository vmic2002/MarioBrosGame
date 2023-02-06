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
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			mario.move(10,  0);
		} else if (keyCode == KeyEvent.VK_LEFT){
			mario.move(-10, 0);	
		} else if (keyCode == KeyEvent.VK_UP) {
			mario.jump();
		} else if (keyCode == KeyEvent.VK_0) {
			mario.makeBig();
		} else if (keyCode == KeyEvent.VK_1) {
			mario.makeSmall();
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
