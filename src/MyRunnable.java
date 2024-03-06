public class MyRunnable implements Runnable {

	@Override
	public final void run() {
		try {
			doWork();
		} catch (InterruptedException e) {
			System.out.println("THREAD INTERRUPTED -> threads stops");
			return;//stops thread when interrupted
		}
		//THREAD DIES WHEN RUN FUNCTION RETURNS
		//System.out.println("THREAD STOPS");
	}

	public void doWork() throws InterruptedException {
		//NEED TO OVERRIDE THIS
	}
}
/*

 use interrupts:https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html
 "Many methods that throw InterruptedException, 
 such as (Thread.)sleep, are designed to cancel their current operation and return immediately when an interrupt is received."
 in websocket.onClose, need to call GameThread.interruptAllMarioThreads() on all mario threads (see GameThread.java)
  next time Thread.sleep is called, it will throw an InterruptedException which will be caught in run function
  and thread will stop!




 point of MyRunnable and GameThread is to stop all mario threads when websocket connection closes
//thread.start and thread.setName are called in MyThread constructor

		GameThread t1 = new GameThread(new MyRunnable() {
			@Override
			public void doWork() throws InterruptedException {
				//System.out.println("IN THREAD");
				//NO NEED TO SURROUND SLEEP WITH TRY CATCH SINCE RUN FUNC HAS TRY CATCH
				ThreadSleep.sleep(10000);
			}
		}, "THREADNAMEEEE!!!!!!!E");
		
 
		
 */