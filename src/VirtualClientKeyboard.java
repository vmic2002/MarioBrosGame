import java.awt.event.KeyEvent;

import jakarta.websocket.Session;

public class VirtualClientKeyboard {
	//replaces MyKeyListener when playing game on tomcat server instead of from eclipse or terminal
	private static Mario[] characters;
	//characters[0] = luigi;
	//characters[1] = mario;
	public static void setCharacters(Mario[] characters1) {
		characters = characters1;
	}


	//up/down/left/right arrows and q to shoot/swing tail...

	public static void keyPressed(boolean keyPressedOrKeyReleased, String key, String character) {
		//key is either ArrowUp, ArrowDown, ArrowLeft, ArrowRight, q
		//character is either Mario, Luigi
		Mario mario = characters[character.equals("Mario")?1:0];
		//ServerToClientMessenger.sendMessage("VIRTUALKEYBOARD: key: "+key+ " character: "+character+ " on server");
		if (keyPressedOrKeyReleased) {
			if (!mario.alive) return;
			if (mario.goingIntoPipe) return;
			if (key.equals("ArrowRight")) {
				mario.move(true);
			} else if (key.equals("ArrowLeft")){
				mario.move(false);
			} else if (key.equals("ArrowDown")){
				mario.setToCrouching();
			} else if (key.equals("q")){
				if (mario.isFire)
					mario.shootFireBall();
				else if (mario.isCat || mario.isTanooki)	
					mario.swingTail();
			} else if (key.equals("ArrowUp")) {
				mario.jump();
			}
		} else {
			if (!mario.alive) return;
			if (mario.goingIntoPipe) return;
			if (key.equals("ArrowRight")) {
				mario.movingRight = false;
				if (mario.isCrouching) {
					return;
				}
				if (!mario.isJumping) mario.setToStanding(true);
			} else if (key.equals("ArrowLeft")){
				mario.movingLeft = false;
				if (mario.isCrouching) {
					return;
				}
				if (!mario.isJumping) mario.setToStanding(false);
			} else if (key.equals("ArrowDown")){
				mario.stopCrouching();
			} else if (key.equals("ArrowUp")) {
				mario.wayUpOrWayDown = false;
			} else if (key.equals("q")) {
				if (mario.isFire) {mario.isShooting = false;System.out.println("\n\n1111111111111isSHootingsetto false for character[0]\n\n");}
				else if (mario.isCat || mario.isTanooki) mario.isSwinging = false;	
			}
		}
	}

}
