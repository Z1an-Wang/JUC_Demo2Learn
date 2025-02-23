package CoreKnowledge.CreateThread;

/**
 * 描述：用 Runnable 的方式创建线程。
 */
public class RunnableStyle implements Runnable{

	public static void main(String[] args) {
		// 将 Runnable 对象通过 Thread 的构造函数赋值给 target 对象。
		Thread thread = new Thread(new RunnableStyle());
		// 启动一个线程。
		thread.start();
	}

	// 重写 Runnable 接口中的 run() 方法。
	@Override
	public void run() {
		System.out.println("Use RunnableStyle to start a thread!");
	}
}
