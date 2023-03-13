import acm.graphics.GImage;
import java.util.ArrayList;
public class Level {
	private int number;
	public ArrayList<LevelPart> levelParts;
	public double yBaseLine;//changes when mario jumps up or down too close to edges
	//if yBaseLine > 0 then some of the levelParts are below their initial position (and may be off screen)
	public double xBaseLine;//if xBaseLine == 0 then mario is at leftmost spot in the level so can't move the level more left
	//if xbaseline == canvas width-level width then mario is at right most portion of level
	//private GImage background;
	public double width;
	public Level(int number, ArrayList<LevelPart> levelParts, double width){//, GImage background) {
		this.number = number;
		this.levelParts = levelParts;
		yBaseLine = 0;
		xBaseLine = 0;
		this.width = width;
		//this.background = background;
	}
	public int getNumber() {
		return number;
	}
	public void removeFireBall(FireBall f) {
		for (LevelPart p: levelParts) {
			if (p.part.get(0) instanceof FireBall) {
				//System.out.println("REMOVING FIREBALL FROM LEVEL");
				//once fireball dies (out of gas, runs into platform from the side, runs into turtle etc,)
				//there is no need to keep it in level parts
				levelParts.remove(p);
				return;
			}
		}
	}
	public void moveLevel(double dx, double dy) {
		//TODO this function works but does not scale when a level is long need to only move the level parts that are visible on screen
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<levelParts.size(); i++) {
					levelParts.get(i).move(dx, dy);
				}
				xBaseLine+=dx;
				yBaseLine+=dy;
			}
		});  
		t1.start();
	}

	public void addNewLevelPart(GImage i) {
		//to add power ups or fireballs to level
		ArrayList<GImage> l = new ArrayList<GImage>();
		l.add(i);
		levelParts.add(new LevelPart(l));
	}
}
