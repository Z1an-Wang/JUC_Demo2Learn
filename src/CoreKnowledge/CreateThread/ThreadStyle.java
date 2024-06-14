package CoreKnowledge.CreateThread;

/**
 * 描述：用继承 Thread 的方式创建线程。
 */
public class ThreadStyle extends Thread {
	public static void main(String[] args) {
		// 标准的通过继承 Thread 方式创建线程。
		new ThreadStyle().start();

		// 通过函数式接口创建匿名 Runnable 对象，然后调用 Thread 的构造函数给 target 对象赋值。
		new Thread(() -> System.out.println("Hello World!")).start();
	}
	@Override
	public void run(){
		System.out.println("Starting a thread from extended thread!");
	}
}
