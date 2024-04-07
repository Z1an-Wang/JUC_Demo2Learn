package CoreKnowledge.StopThread;

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
