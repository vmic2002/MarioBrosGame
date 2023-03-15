import java.awt.Image;

public class PipePart extends Platform{
	//see LevelController.spawnUpPipe/spawnDownPipe
	//a pipe that does not allow mario to "teleport" to another pipe
	//is constructed only of Platform instances, not PipePart instances
	//If a pipe does allow mario to teleport to another pipe, then the part of the
	//pipe that mario is in contact with must be a PipePart
	//imagine a pipe that mario could crouch and go into:
	//    ________ 
	//    |      |   << THIS PART needs to be created from PipePart instances
	//      |  |
	//      |  |
	
	//using PipePart will help mario use instanceof PipePart to know whether or not the pipe is one
	//that will allow him to go into or not
	//if mario goes into pipe, a new sub-level should be spawned
	//PipePart needs to keep track of which sub-level should be loaded when mario goes into it
	String subLevelID;
	public PipePart(Image arg0, String l) {
		super(arg0);
		subLevelID = l;
		// TODO Auto-generated constructor stub
	}

}
