import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;


public class MyKeyListener implements KeyListener {
	//MyKeyListener is for when game is played on eclipse or terminal, not on tomcat server
	//this means that mario and luigi are playing on the same keyboard and computer
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
		if (keyCode == KeyEvent.VK_0) {
			//StatsController.printAllStats();
			//System.out.println("There are "+LevelController.currLevel.dynamicLevelParts.values().size()+" elements in dynamicLevelParts");
			//Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
			//System.out.println("Number of threads: "+threads.keySet().size());
			GameStatsController.setBaseLinePause(GameStatsController.getBaseLinePause()*2);
		}
		if (keyCode == KeyEvent.VK_9) {
			//print all threads
			System.out.println("\n\nPrinting all threads:");
			Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
			System.out.println("Number of threads: "+threads.keySet().size());
			System.out.printf("\t%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
			for (Thread t : threads.keySet()) {
			    System.out.printf("\t%-15s \t %-15s \t %-15d \t %s\n", t.getName(), t.getState(), t.getPriority(), t.isDaemon());
			}
			System.out.println("Number of threads: "+threads.keySet().size());
			System.out.println("\n\n");
		}
		
		if (keyCode == KeyEvent.VK_1) {
			for (Mario m : characters) m.setToSmall();
		} else if (keyCode == KeyEvent.VK_2) {
			for (Mario m : characters) m.setToBig();
		} else if (keyCode == KeyEvent.VK_3) {
			for (Mario m : characters) m.setToFire();
		} else if (keyCode == KeyEvent.VK_4) {
			for (Mario m : characters) m.setToCat();
		} else if (keyCode == KeyEvent.VK_5) {
			for (Mario m : characters) m.setToTanooki();
		}  else {
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
					else if (mario.isCat || mario.isTanooki)	
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
					else if (mario.isCat || mario.isTanooki)	
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
				keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_K) {
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
			} else if (keyCode == KeyEvent.VK_K) {
				if (mario.isFire) {mario.isShooting = false;System.out.println("\n\n1111111111111isSHootingsetto false for character[0]\n\n");}
				else if (mario.isCat || mario.isTanooki) mario.isSwinging = false;	
			}
		} else if (keyCode == KeyEvent.VK_H || keyCode == KeyEvent.VK_F ||
				keyCode == KeyEvent.VK_G || keyCode == KeyEvent.VK_T || keyCode == KeyEvent.VK_Q){
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
			} else if (keyCode == KeyEvent.VK_Q) {
				if (mario.isFire) {mario.isShooting = false;System.out.println("\n\n1111111111111isSHootingsetto false for character[1]\n\n");}
				else if (mario.isCat || mario.isTanooki) mario.isSwinging = false;	
			}
		}
	}
}
