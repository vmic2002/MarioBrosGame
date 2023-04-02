import acm.graphics.GImage;
import java.util.ArrayList;
public class Level {
	private String id;
	public ArrayList<LevelPart> levelParts;//for levelParts added at level creation time, when playLevelX() is called
	public ArrayList<LevelPart> dynamicLevelParts;//buffer for level parts that need to be added dynamically (while playing level) to level parts (fireballs and powerups for example)
	//levelParts is never modified after the playLevelX() function

	public double yBaseLine;//changes when mario jumps up or down too close to edges
	//if yBaseLine > 0 then some of the levelParts are below their initial position (and may be off screen)
	public double xBaseLine;//if xBaseLine == 0 then mario is at leftmost spot in the level so can't move the level more left
	//if xbaseline == canvas width-level width then mario is at right most portion of level
	//private GImage background;
	public double width;
	public Level(String id, ArrayList<LevelPart> levelParts, double width){//, GImage background) {
		this.id = id;
		this.levelParts = levelParts;
		yBaseLine = 0;
		xBaseLine = 0;
		this.width = width;
		dynamicLevelParts = new ArrayList<LevelPart>();
		//this.background = background;
	}
	public String getID() {
		return id;
	}



	public void removeFireBall(FireBall f) {
		//removes fireball from dynamicLevelParts, buffer for dynamically added levelparts
		for (int i=0; i<dynamicLevelParts.size(); i++) {
			LevelPart p = dynamicLevelParts.get(i);
			if (p!=null) {
				GImage image = p.part.get(0); 
				if (image instanceof FireBall && ((FireBall) image).equals(f)) {
					//System.out.println("REMOVING FIREBALL FROM LEVEL");
					//once fireball dies (out of gas, runs into platform from the side, runs into turtle etc,)
					//there is no need to keep it in level parts

					dynamicLevelParts.remove(p);
					return;
				}
			}
		}
	}

	public void moveLevel(double dx, double dy) {
		//TODO this function works but does not scale when a level is long need to only move the level parts that are visible on screen
		//this level could "scale" using pipes to connect different sub levels so each level doesnt become too long to move at once
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=0; i<levelParts.size(); i++) {
					levelParts.get(i).move(dx, dy);
				}
				for (int i=0; i<dynamicLevelParts.size(); i++) {
					dynamicLevelParts.get(i).move(dx, dy);
				}
				xBaseLine+=dx;
				yBaseLine+=dy;
			}
		});  
		t1.start();
	}

	public void addLevelPartDynamically(GImage i) {
		//to add level parts dynamically (power ups or fireballs) to level
		//while level is being played
		ArrayList<GImage> l = new ArrayList<GImage>();
		l.add(i);
		dynamicLevelParts.add(new LevelPart(l));
		System.out.println("NEW LEVEL PART ADDDED");
	}
}
