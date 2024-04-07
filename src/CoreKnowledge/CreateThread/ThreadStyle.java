package CoreKnowledge.CreateThread;

/**
 * 描述：用继承 Thread 的方式创建线程。
 */
public class ThreadStyle extends Thread {
	public static void main(String[] args) {
		new ThreadStyle().start();
		new Thread(() -> System.out.println("Hello World!")).start();
	}
	@Override
	public void run(){
		System.out.println("Starting a thread from extended thread!");
	}
}
