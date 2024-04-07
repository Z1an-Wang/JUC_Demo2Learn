package CoreKnowledge.StartThread;

public class StartThread {
	public static void main(String[] args) {

		Runnable r = () -> {
			System.out.println(Thread.currentThread().getName());
		};

		r.run();					// call run() only execute in main thread

		new Thread(r).start();		// right way to create a thread

		// due to the thread state check, can't start a thread twice.
		Thread thread = new Thread(r);
		thread.start();
		thread.start();
	}
}
