package CoreKnowledge.ThreadState;

/**
 *   描述：展示线程的NEW、RUNNABLE、Terminated状态。即使是正在运行，也是Runnable状态，而不是Running。
 */
public class NewRunnableTerminated implements Runnable {

	public static void main(String[] args) {
		Thread thread = new Thread(new NewRunnableTerminated());
		// 打印出 NEW 的状态
		System.out.println(thread.getState());
		thread.start();
		// 调用 start() 方法后，立即进入 RUNNABLE 状态。
		System.out.println(thread.getState());
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 打印出 RUNNABLE 的状态，即使是正在运行，也是 RUNNABLE，而不是 RUNNING
		System.out.println(thread.getState());

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 打印出 TERMINATED 状态
		System.out.println(thread.getState());
	}

	@Override
	public void run() {
		for (int i = 0; i < 1000; i++) {
			System.out.println(i);
		}
	}
}
