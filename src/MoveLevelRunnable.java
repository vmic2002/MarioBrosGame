import java.util.concurrent.atomic.AtomicInteger;


public class MoveLevelRunnable extends MyRunnable {

	private Level l;
	private double dx, dy;
	private Mario mario;
	private int id;

	//	FOR TESTING:
	public static int maxNumMoveLevelRunnable = 0;//THIS NUMBER HAS EVER ONLY REACHED 2 EVEN FOR PRETTY LONG LEVEL (WHEN TESTES ON ECLIPSE NOT ON APACHE SERVER)
	//TESTING
	
	
	
		//KEEPs TRACK OF ALL THREADS THAT ARE MOVING A LEVEL BECAUSE WE DONT WANT A NEWER THREAD TO
			//CATCH UP TO AN OLDER THREAD WHILE THEY ARE BOTH MOVING THE LEVEL


	//[  1  ] is a movelevelrunnable with id 1, time goes from left to right
	//so [ means the movelevelrunnable has just been created and ] means it is dead
	//if one move level runnable exists at any time, its id will be 0
	//ex: [  0  ] [  0 ] [  0  ] ...
	//as long as numMoveLevelRunnables>0, ids keep on incrementing by one
	//as soon as numMoveLevelRunnables==0, ids start again at 0
	//if multiple moveLevelRunnables exist at same time:
	/*
	   			[   2   ] [  4  ]
	 	[  0  ]
	 		[  1  ]  [  3  ]      	[  0  ] ...


	 */



	private static AtomicInteger numMoveLevelRunnables = new AtomicInteger(0);
	private static AtomicInteger moveLevelIDGenerator = new AtomicInteger(-1);


	private static final int MAX_MOVE_LEVEL_RUNNABLE_ID = 1000;
	private static int[] currentLevelPartForMoveLevelRunnables = new int[MAX_MOVE_LEVEL_RUNNABLE_ID];
	//array of zeroes by default which is what we want
	//currentLevelPartForMoveLevelRunnables[i] = movelevelrunnable with id "i"'s current static level part index its moving
	//reset to all zeroes when numMoveLevelRunnables.get()==0
	//example: movelevelrunnable with id 2 is currently moving staticlevel part with index currentLevelPartForMoveLevelRunnables[2]

	public MoveLevelRunnable(Level l, double dx, double dy, Mario mario) {
		this.l = l;
		this.dx = dx;
		this.dy = dy;
		this.mario = mario;
		if (numMoveLevelRunnables.get()==0) {
			moveLevelIDGenerator.set(-1);
			currentLevelPartForMoveLevelRunnables = new int[MAX_MOVE_LEVEL_RUNNABLE_ID];
		}
		this.id = moveLevelIDGenerator.incrementAndGet();
		numMoveLevelRunnables.incrementAndGet();

		//FOR TESTING:
		if (numMoveLevelRunnables.get()>maxNumMoveLevelRunnable) maxNumMoveLevelRunnable = numMoveLevelRunnables.get();
	}

	public static int getNumMoveLevelRunnables() {
		//FUNC IS FOR TESTING
		return numMoveLevelRunnables.get();
	}



	@Override
	public final void doWork() throws InterruptedException {
		moveLevel();
		numMoveLevelRunnables.decrementAndGet();
	}

	public void moveStaticLevelPart(StaticLevelPart staticLevelPart, int staticLevelPartIndex) {
		synchronized (staticLevelPart) {
			if (id==0 || id>0 && currentLevelPartForMoveLevelRunnables[id-1]>currentLevelPartForMoveLevelRunnables[id]) {
				//if statement is to make sure that new thread doesnt catch to old thread in terms of looping throguh
				//static level parts in right order to move them
				//wE DONT WANT A NEWER THREAD (bigger ID) TO
				//CATCH UP TO AN OLDER THREAD (lower id) WHILE THEY ARE BOTH MOVING THE LEVEL
				//syncrhonized nature of moving images (moving images func are syhnchronized)
				//doesnt guarantee this will happen so we have to make sure,
				//need to keep track of which static level part (grassmountain, up pipe, mushroom platform etc) is being moved by current runnable
				//if runnable id 1 is moving static level part 2 (already moved parts 0 and 1)
				//then runnable id 2 can only move static level part 0 or 1 not 2 or above
				//lets say level has 4 static level parts, graphically:
				//id:2      [ (0) (1) (2) (3)]
				//id:1 [ (0) (1) (2) (3)]
				//once movelevelrunnable with id 1 has moved all of static level part num 0 and is moving on to part 1,
				//then and only then can movelevelrunnable with id 2 can move static part level 0



				//TODO SHOULD ALSO MAKE SURE THAT STATIC LEVEL PARTon screen IS ALWAYS PRIORATIZED NOT JUST MOVELEVELRUNNABLE 0
				//IN OTHER WORDS MOVELEVELRUNABLE i SHOULDNT BE MOVING STATICLEVELPART far offscreen IF
				//MOVELEVELRUNNABLE i+1 HASNT MOVED STATICLEVEL PART on screen
				//in other words its good that new thread doesnt catch up to old thread but we dont want
				//old thread to be way too far ahead (moving static level parts far offscreen)



				//System.out.println("ID: "+id+", move staticlevelpart #"+staticLevelPartIndex);
				staticLevelPart.move(dx, dy);
				//System.out.println("ID: "+id+", ending move staticlevelpart #"+staticLevelPartIndex);

				currentLevelPartForMoveLevelRunnables[id] = staticLevelPartIndex+1;
			}



		}
		/* no two threads can move the same staticlevelpart at the same time
			-> a static level part is moved by one thread at a time
		 * 
		 * link explaining what synchronized block means:
		 * https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html
		 * 
		 * 
		 * Every object has an intrinsic lock associated with it.
		 * By convention, a thread that needs exclusive and consistent access to an object's 
		 * fields has to acquire the object's intrinsic lock before accessing them, and then release the 
		 * intrinsic lock when it's done with them. A thread is said to own the intrinsic lock between the time it has acquired 
		 * the lock and released the lock. As long as a thread owns an intrinsic lock, no other thread can acquire 
		 * the same lock. The other thread will block when it attempts to acquire the lock.
		 */
	}

	public void moveLevel() {

		l.xBaseLine+=dx;
		l.yBaseLine+=dy;
		//TODO NEED TO CHANGE X AND Y BASELINE ATOMICALLY!!! TO PREVENT DATA RACES
		//TODO NEED TO  periodically check if currenthread interrupt bit is activated to throw interuppted exception






		////this should fix bug where sometimes dynamic level parts is being looped through to move 
		//level as a dynamic level part is being added/removed
		synchronized (l.dynamicLevelParts) {

			for (DynamicLevelPart d : l.dynamicLevelParts.values()){
				d.move(dx, dy);
			}
		}
		//}
		for (Mario m : MovingObject.characters) {
			if (m!=mario) m.moveOnlyMario(dx, dy);
		}


		//THROUGH TESTING NOTICED THAT NUMLEVELMOVERUNNABLES DOESNT GET THAT HIGH LIKE ONLY 2 SO I MAY BE OVERTHINKING THIS CLASS
		

		//SEE LEVEL MOVELEVELASYNCHRONOSULY FOR COMMENTS

		//TODO ALSO NEED TO TAKE INTO ACCOUNT THAT HAVE TO START MOVING LEVEL PART ON SCREEN AND THEN THE ONES SLIGHTLY
		//OFF SCREEN AND THEN THE ONES EVEN MORE OFF SCREEN ETC. maybe need another data structure to keep track of which 
		//static level parts are closest on screen. IF IN MIDDLE OF LEVEL DOESNT MAKE SENSE TO START MOVING LEVEL PART #0 SINCE IT IS
		//OFF SCREEN TO THE LEFT. keep track of closest level parts on screen in list so static level part with index 0
		//is the one closest on screen, that is how moveStaticLevelPart is written, second param for that func 
		// is static level part index. it assumes static levelpart iwth index 0
		//is the closest on screen and bigger indicies are far off screen
		//for now looping through staticlevelparts from left to right so left most static level part is always considered on screen






		//CAN TOY AROUND WITH REALLY LARGE LEVELS AND SEE HOW MANY RUNNABLES ARE ALIVE, WILL HELP TO
		//MAKE EVERYTHING REALLY SLOW WITH GAMESTATSCONTOLLER LONGER PAUSE
		//System.out.println("num move level runnables: "+numMoveLevelRunnables.get()+" id: "+id+ " numStaticLevelPart: "+l.staticLevelParts.size());

		ServerToClientMessenger.sendMoveLevelMessage(dx, dy);
		//TODO WILL NEED TO NOT SEND MOVELEVELMESSAGE OR ELSE JAVASCRIPT WILL HAVE TO DO SAME LOGIC AS THIS CLASS
		//SEND MOVEIMAGE MESSAGE FOR EACH IMAGE ORRRRRRR MAKE SURE JS KEEPS TRACK OF EACH STATIC LEVEL PART
		//JAVASCRIPT COULD HAVE a list of static level part (which is itself of list of images)
		//one message could be sent per static level part instead of 1 per image

		for (int i=0; i<l.staticLevelParts.size(); i++) {
			//	l.staticLevelParts.get(i).move(dx, dy);

			moveStaticLevelPart(l.staticLevelParts.get(i), i);

		}


	}

	/*
	 * GameThread t1 = new GameThread(new MoveLevelRunnable(),"movelevellll");
	 */
}
