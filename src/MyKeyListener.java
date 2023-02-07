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
		int keyCode = e.getKeyCode();
		System.out.println("KEY TYPPPPPPPED");
		if (keyCode == KeyEvent.VK_UP) {
			System.out.println("SHOULD JUMP HERE");
		} 
		System.out.println(mario.movingLeft+"     "+mario.movingRight);
		System.out.println(mario.getX() + "       "+mario.getY());
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
	
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			if (mario.movingRight) {
				return;
			}
			mario.move(true);
			System.out.println("pressed move right");
		} else if (keyCode == KeyEvent.VK_LEFT){
			if (mario.movingLeft) {
				return;
			}
			mario.move(false);
			System.out.println("pressed move left");
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
		//System.out.println("Key released");
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_RIGHT) {
			System.out.println("released right");
			mario.movingRight = false;
		} else if (keyCode == KeyEvent.VK_LEFT){
			System.out.println("released left");
			mario.movingLeft = false;
		}

	}

}
