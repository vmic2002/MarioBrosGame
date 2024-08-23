

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
			System.out.println("-------------------------------");
			CharacterStatsController.printAllStats();
			System.out.println("-------------------------------");
			//System.out.println("There are "+LevelController.currLevel.dynamicLevelParts.values().size()+" elements in dynamicLevelParts");
			//GameThread.interruptAllMarioThreads();

			//GameStatsController.setToLongPause();
			//	System.out.print("Time dilating: ");
			//for (Mario m:characters) System.out.println(m.isTimeDilating+ " ");
			//	System.out.println();
			/*for (int i=0; i<LevelController.currLevel.staticLevelParts.size(); i++) {
				StaticLevelPart l = LevelController.currLevel.staticLevelParts.get(i);
				System.out.println("STATIC LEVEL PARTS #"+i);
				for (int j=0; j<l.platforms.size(); j++) {
					System.out.println("\tX: "+l.platforms.get(j).getX()+" Y: "+l.platforms.get(j).getY());
				}
			}*/
			//System.out.println(MoveLevelRunnable.getNumMoveLevelRunnables());
			//System.out.println("y baseline: "+LevelController.currLevel.yBaseLine);
			//System.out.println("x baseline: "+LevelController.currLevel.xBaseLine);
			//System.out.println("max num threads moving level"+MoveLevelRunnable.maxNumMoveLevelRunnable);


			//System.out.println("\n\nnumber of floatingcoins blocks: "+LevelController.currLevel.floatingCoinsBlocks.size());
			//for (int i=0; i<LevelController.currLevel.floatingCoinsBlocks.size(); i++) {
			//	System.out.println("floating coins block "+i+" has "+LevelController.currLevel.floatingCoinsBlocks.get(i).coins.size()+ " coins");
			//}
		}
		if (keyCode == KeyEvent.VK_9) {
			//print all threads
			System.out.println("\n\nPrinting all threads:");
			Map<Thread, StackTraceElement[]> threads = Thread.getAllStackTraces();
			//System.out.println("Number of threads: "+threads.keySet().size());
			System.out.printf("\t%-15s \t %-15s \t %-15s \t %s\n", "Name", "State", "Priority", "isDaemon");
			for (Thread t : threads.keySet()) {
				System.out.printf("\t%-15s \t %-15s \t %-15d \t %s\n", t.getName(), t.getState(), t.getPriority(), t.isDaemon());
			}
			System.out.println("Number of threads: "+threads.keySet().size());
			System.out.println("\n\n");
			//Thread.getAllStackTraces().keySet().size();
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
		}  else if (keyCode == KeyEvent.VK_6) {
			for (Mario m:characters)
				m.setToTimeDilating();//every mario character luigi, peach etc is set to time dilating as well	
			GameStatsController.setToLongPause();//will make everything move slower except for mario (see Mario.sleep func)

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
				if (mario.isFire) {mario.isShooting = false;
				//System.out.println("\n\n1111111111111isSHootingsetto false for character[0]\n\n");
				}
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
