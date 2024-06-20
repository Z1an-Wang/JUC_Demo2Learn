package CoreKnowledge.ThreadAttribute;

import java.util.Date;

/**
 * 演示创建守护线程，守护线程不需要发出中断信号去退出。
 */
public class _2Daemon implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new _2Daemon());
		t.setDaemon(true);    // 设置为守护线程
		t.start();

		// 守护线程不需要发出中断信号去退出，等主线程执行完毕，JVM 自动清理守护线程。
		Thread.sleep(5000);
	}

	@Override
	public void run() {
		System.out.println("进入守护线程...");

		Integer count = 0;
		while (true) {
			String time = new Date().toString();
			System.out.println("当前:" + time + " | 守护线程进行中,目前计数至【" + count++ + "】");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
