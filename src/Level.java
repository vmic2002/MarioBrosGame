import acm.graphics.GImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
public class Level {
	private String id;
	public final ArrayList<LevelPart> levelParts;//for levelParts added at level creation time, when playLevelX() is called
	//levelParts is never modified after the playLevelX() function
	public HashMap<Long, DynamicLevelPart> dynamicLevelParts;//buffer for level parts that need to be added dynamically (while playing level) to level parts (fireballs and powerups for example)
	public static AtomicLong ID_GENERATOR = new AtomicLong();
	
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
		dynamicLevelParts = new HashMap<Long, DynamicLevelPart>();
		//this.background = background;
	}
	
	public String getID() {
		return id;
	}
	
	public void removeDynamic(Dynamic f) {
		//removes Object that implements Dynamic (powerup and fireball for now) from dynamicLevelParts, 	
		if (dynamicLevelParts.get(f.getID())==null) {
			System.out.println("Tried to remove "+f.getID()+" from dynamicLevelParts");
			return;
		}
		dynamicLevelParts.remove(f.getID());
		System.out.println("Successfully removed "+f.getID()+" from dynmiacLevelParts");		
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
				for (DynamicLevelPart d : dynamicLevelParts.values()){
					d.move(dx, dy);
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
		if (!(i instanceof Dynamic)) {
			System.out.println("CAN ONLY ADD Objects who implement Dynamic");
			System.exit(1);
		}
		ArrayList<GImage> l = new ArrayList<GImage>();
		l.add(i);
		long newID = ID_GENERATOR.getAndIncrement();
		if (dynamicLevelParts.get(newID)!=null){
			System.out.println("ID for dynamic level part already used!");
			System.exit(1);
		}
		dynamicLevelParts.put(newID, new DynamicLevelPart(l, newID));
		((Dynamic) i).setID(newID);
		System.out.println("NEW DYNAMIC LEVEL PART ADDDED: "+newID);
	}
}
