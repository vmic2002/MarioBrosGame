import java.awt.Image;
import java.util.ArrayList;

import acm.graphics.GObject;

public abstract class ShootingFlower extends BadGuy {
	//UpShootingFlower and DownShootingFlower extend ShootingFlower
	//UpShootingFlower comes in and out of Up Pipe, down pipe for downShootingFlower
	//shootingflower is added to level parts when creating/spawning an up/down pipe
	//shootingflower is part of the same level part as an up/down pipe
	private static final double DY = 10.0;
	private int numMoves;
	public double dy;
	public ShootingFlower(Image arg0) {
		super(arg0);
		if (this instanceof UpShootingFlower) dy = -DY;
		else dy = DY;//(this instanceof DownShootingFlower)
		numMoves = (int) (getHeight()/DY);
	}

	public abstract Point[] getPoints();

	public void move() {
		//shootingflower.move() is called when shootingflower is added to levelParts
		//this func makes the flower move out of the pipe, shoot a fireball at mario,
		//and come back into the pipe depending on if is a up/down shootingflower
		//TODO make shooting flower shoot at mario
		System.out.println("In move function for shooting flower");
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (alive) {
					try {
						for (int i=0; i<numMoves; i++) {
							ArrayList<GObject> o1 = checkAtPositions(getPoints(), canvas);
							for (GObject x : o1) {
								inContactWith(x, false);
							}
							move(0, dy);
							//flower comes out of pipe
							Thread.sleep(40);
						}
						Thread.sleep(500);
						//this is where flower needs to locate mario and shoot fireball at him
						for (int i=0; i<numMoves; i++) {
							ArrayList<GObject> o1 = checkAtPositions(getPoints(), canvas);
							for (GObject x : o1) {
								inContactWith(x, false);
							}
							move(0, -dy);
							//flower goes back into pipe
							Thread.sleep(40);
						}
						Thread.sleep(4000);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});  
		t1.start();

	}
}