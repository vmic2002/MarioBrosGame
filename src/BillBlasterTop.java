

public class BillBlasterTop extends Platform {
	//top part of bill blaster which bullet bills come out of
	//see LevelController.startMovingObjects() why this class exists
	private static MyImage billBlasterTop;
	public BillBlasterTop() {
		super(billBlasterTop);
	}

	public static void setImage(MyImage i) {
		billBlasterTop = i;
	}
}
