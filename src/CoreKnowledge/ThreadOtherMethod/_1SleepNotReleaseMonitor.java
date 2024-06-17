package CoreKnowledge.ThreadOtherMethod;

/**
 * 展示线程 sleep 的时候，不释放 synchronized 的 monitor。
 * 等 sleep 时间结束以后，继续运行，直到锁被线程释放。
 */
public class _1SleepNotReleaseMonitor implements Runnable {

	public static void main(String[] args) {
		Runnable sleepNotReleaseMonitor = new _1SleepNotReleaseMonitor();
		// 第一个线程启动以后，拿到锁并进入休眠。
		// 因为 sleep() 不释放锁，所以第二个线程无法拿到锁。
		// 第二个线程需要等到第一个线程执行完毕，才能获取到锁。
		new Thread(sleepNotReleaseMonitor).start();
		new Thread(sleepNotReleaseMonitor).start();
	}

	@Override
	public void run() {
		syn();
	}

	private synchronized void syn() {
		System.out.println(Thread.currentThread().getName() + "获取到了 monitor 。");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + "退出同步代码块。");
	}
}
