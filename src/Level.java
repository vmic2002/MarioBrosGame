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
	public void moveLevel(double dx, double dy) {
		//TODO this function works but does not scale when a level is long
		//need to only move the level parts that are visible on screen
		for (LevelPart l : levelParts) {
			l.move(dx, dy);
		}
		xBaseLine+=dx;
		yBaseLine+=dy;
		//System.out.println("MOVING LEVEL "+dx+ " "+dy);
		//background.move(dx/2, dy/2);
	}
}
