
public class XCounter {
	public double v;
	//used for adding images to a level and keeping track of the level's width
	//to keep track of smallest left index where a new LevelPart could be spawned
	//to have white space in between level parts need to increment xCounter by width of whitespace
	//at the end of function XCounter will be the width of the level
	public XCounter() {
		v = 0;
	}
	public void initialize() {v=0;}//called at beginning of each playLevelX() func in LevelController
}
