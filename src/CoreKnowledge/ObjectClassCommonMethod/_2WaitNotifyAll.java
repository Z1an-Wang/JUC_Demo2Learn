package CoreKnowledge.ObjectClassCommonMethod;

/**
 * 将会有 3 个线程，线程 1、2 被阻塞，线程 3 调用 notifyAll 去唤醒所有线程。
 * start 先执行，并不代表线程先启动。
 */
public class _2WaitNotifyAll implements Runnable {
	// 一个共享的资源，用来执行 wait 和 notify 的对象。
	private static final Object resource = new Object();

	public static void main(String[] args) throws InterruptedException {
		Runnable r = new _2WaitNotifyAll();
		// 两个线程都持有 resource 对象的 monitor。
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);

		Thread t3 = new Thread(() -> {
			synchronized (resource) {
				// 调用 notifyAll 去唤醒所有持有对象 monitor 的线程。
				resource.notifyAll();
				System.out.println("Thread 3 notified all.");
			}
		});

		t1.start();
		t2.start();
		// 如果没有这句，线程1获取到了锁又释放，此时线程 2/3 竞争这个对象的锁，有可能 线程3 先执行。
		Thread.sleep(200);
		t3.start();
	}

	@Override
	public void run() {
		synchronized (resource) {
			System.out.println(Thread.currentThread().getName() + " get resource lock.");
			try {
				resource.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " get resource lock again.");
		}
	}
}
