package CoreKnowledge;

/**
 * 描述：用 Runnable 的方式创建线程。
 */
public class RunnableStyle implements Runnable{

	public static void main(String[] args) {
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
