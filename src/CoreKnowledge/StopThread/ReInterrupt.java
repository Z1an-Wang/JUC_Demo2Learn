package CoreKnowledge.StopThread;

/**
 * 描述：最佳实践2：
 * 在 catch 子语句中调用 Thread.currentThread().interrupt() 来恢复设置中断状态，以便
 * 于在后续的执行中，依然能够检查到刚才发生了中断。
 */
public class ReInterrupt implements Runnable {

	@Override
	public void run() {
		while (true) {
			// do something ... ...
			// 判断是否有中断信号。
			if (Thread.currentThread().isInterrupted()) {
				// 检测到中断信号，跳出 while 循环。
				break;
			}
			// 捕获中断信号，然后再次发出中断信号。
			reInterrupt();
		}
	}

	private void reInterrupt() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// 捕获中断信号，然后再次发出中断信号。
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new ReInterrupt());
		thread.start();
		Thread.sleep(1000);
		thread.interrupt();
	}
}
