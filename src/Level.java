import acm.graphics.GImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;
public class Level {
	private String id;
	public final ArrayList<StaticLevelPart> staticLevelParts;//for levelParts added at level creation time, when playLevelX() is called
	//levelParts is never modified after the playLevelX() function
	//staticLevelParts hold all Platforms of level
	public HashMap<Long, DynamicLevelPart> dynamicLevelParts;
	//dynamicLevelParts is buffer for level parts that need to be added dynamically 
	//(while playing level) to level parts (see Dynamic.java)
	//dynamicLevelParts is also for level parts that could be killed/removed from screen so would be
	//inefficient to keep them in static levelParts (like coins for example)
	//public static AtomicLong ID_GENERATOR = new AtomicLong(0);//Atomic for concurrency, id of dynamic level parts, used for key of hashmap

	public double yBaseLine;//changes when mario jumps up or down too close to edges
	//if yBaseLine > 0 then some of the levelParts are below their initial position (and may be off screen)
	public double xBaseLine;//if xBaseLine == 0 then mario is at leftmost spot in the level so can't move the level more left
	//if xbaseline == canvas width-level width then mario is at right most portion of level
	//private GImage background;
	public double width;

	public Level(String id, ArrayList<StaticLevelPart> staticLevelParts, HashMap<Long, DynamicLevelPart> dynamicLevelParts, double width){//, GImage background) {
		this.id = id;
		this.staticLevelParts = staticLevelParts;
		yBaseLine = 0.0;
		xBaseLine = 0.0;
		this.width = width;
		this.dynamicLevelParts = dynamicLevelParts;
		//this.background = background;

		for (StaticLevelPart l : this.staticLevelParts)
			for (Platform platform : l.part)
				ServerToClientMessenger.sendAddImageToScreenMessage(platform);
		
		for (DynamicLevelPart l : this.dynamicLevelParts.values()) {
			ThreadSafeGImage image = (ThreadSafeGImage) l.part;
			ServerToClientMessenger.sendAddImageToScreenMessage(image);
			if (image instanceof Coin)//START SPINNING ALL COINS AT BEGINNING OF LEVEL SO THEY ALL SPIN AT SAME TIME
				((Coin) image).startSpinning();
			else if (image instanceof BadGuy)
				((BadGuy) image).move();
			else if (image instanceof PowerUp)
				((PowerUp) image).move();
		}
		
		//TODO could "activate" billblasters when level is instantiated for now it is activated before
	}

	public String getID() {
		return id;
	}

	public void removeDynamic(Dynamic d) {
		//removes Object that implement Dynamic from dynamicLevelParts, 	
		if (dynamicLevelParts.get(d.getID())==null) {
			//System.out.println("Tried to remove "+f.getID()+" from dynamicLevelParts");
			return;
		}
		dynamicLevelParts.remove(d.getID());
		//System.out.println("Successfully removed "+f.getID()+" from dynmiacLevelParts");
		//System.out.println("\ndynamicLevelParts size: "+dynamicLevelParts.size()+"\n");
		//TODO bug where dynamic level parts is -1 at some point, maybe concurrency bug
	}
	//@param mario is mario who called this function, so every other Mario in 
	//MovingObject.characters needs to move with level
	//characters aren't part of levelParts or dynamicLevelParts but they move with the level
	public void moveLevel(double dx, double dy, Mario mario) {
		//this function works but does not scale when a level is long need to only move the level parts that are visible on screen
		//this level could "scale" using pipes to connect different sub levels so each level doesnt become too long to move at once
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				//if (xBaseLine+dx<=0.0 && yBaseLine+dy>=0.0) {
				moveLevelAsynchronously(dx, dy, mario);
				//} else {System.out.println("MOVELEVEL");System.exit(1);}
			}
		});
		t1.setName("moving level");
		t1.start();
	}

	private synchronized void moveLevelAsynchronously(double dx, double dy, Mario mario) {
		for (int i=0; i<staticLevelParts.size(); i++) {
			staticLevelParts.get(i).move(dx, dy);
		}
		for (DynamicLevelPart d : dynamicLevelParts.values()){
			d.move(dx, dy);
		}
		for (Mario m : MovingObject.characters) {
			if (m!=mario) m.moveOnlyMario(dx, dy);
		}
		xBaseLine+=dx;
		yBaseLine+=dy;
	}



	public static void addLevelPartDynamically(Dynamic d, HashMap<Long, DynamicLevelPart> dynamicLevelParts) {
		//THIS FUNCTION IS USED IN LEVELCONTROLLER AT LEVEL CREATION TIME (IN PLAYLEVELX FUNCTION)
		//TO ADD DYNAMICLEVEL PARTS LIKE COINS FOR EXAMPLE TO A TEMP HASHMAP BEFORE LEVEL IS INSTANTIATED
		//if (!(i instanceof Dynamic)) {
			//System.out.println("CAN ONLY ADD Objects who implement Dynamic");
			//System.exit(1);
		//}
		//ArrayList<ThreadSafeGImage> l = new ArrayList<ThreadSafeGImage>();
		//l.add(i);
		//long newID = ID_GENERATOR.getAndIncrement();
		if (dynamicLevelParts.get(d.getID())!=null){
			//System.out.println("ID for dynamic level part already used!");
			System.exit(1);
		}
		dynamicLevelParts.put(d.getID(), new DynamicLevelPart(d));//, newID));
		//i.setID(newID);
		//System.out.println("\ndynamicLevelParts size: "+dynamicLevelParts.size()+"\n");
	}

	public void addLevelPartDynamically(Dynamic i) {
		//to add level parts dynamically (power ups or fireballs) to level
		//while level is being played
		Level.addLevelPartDynamically(i, dynamicLevelParts);
	}
}
