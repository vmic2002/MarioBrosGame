
public class Level {
	private int number;
	private LevelPart[] levelParts;
	public Level(int number, LevelPart[] levelParts) {
		this.number = number;
		this.levelParts = levelParts;
	}
	public int getNumber() {
		return number;
	}
}
