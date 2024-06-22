package CoreKnowledge.ThreadException;

/**
 * 描述：是用 try-catch 块尝试捕获子线程中抛出的 RuntimeException 。
 * 期望：捕获到第一个线程的异常，打印出堆栈的错误信息，线程 234 停止运行。
 * 实际：运行时发现，主线程中无法打印出 线程1 的错误信息，线程 234 也没有停止运行。
 * 说明线程的异常不能用传统方法捕获。
 */
public class _2CannotCatchDirectly implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		try {
			new Thread(new _2CannotCatchDirectly(), "MyThread-1").start();
			Thread.sleep(300);
			new Thread(new _2CannotCatchDirectly(), "MyThread-2").start();
			Thread.sleep(300);
			new Thread(new _2CannotCatchDirectly(), "MyThread-3").start();
			Thread.sleep(300);
			new Thread(new _2CannotCatchDirectly(), "MyThread-4").start();
			Thread.sleep(300);
			System.out.println("Done!");
		} catch (RuntimeException e) {
			e.printStackTrace();
			System.out.println("Caught Exception!");
		}
	}

	@Override
	public void run() {
		throw new RuntimeException("Exception in " + Thread.currentThread().getName());
	}
}
