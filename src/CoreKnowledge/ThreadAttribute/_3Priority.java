package CoreKnowledge.ThreadAttribute;

public class _3Priority {
	private static final Object lock = new Object();

	public static void main(String[] args) throws InterruptedException {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				// 每个子线程进入之后先阻塞，并释放所持有的锁。
				synchronized (lock) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.print(Thread.currentThread().getName() + " - ");
				}
			}
		};
		Thread t0 = new Thread(r);
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		Thread t3 = new Thread(r);
		Thread t4 = new Thread(r);

		// 设置线程优先级，并查看
		t0.setPriority(10);
		t1.setPriority(8);
		t2.setPriority(5);
		t3.setPriority(3);
		t4.setPriority(1);
		System.out.println("t0的优先级为：" + t0.getPriority());
		System.out.println("t1的优先级为：" + t1.getPriority());
		System.out.println("t2的优先级为：" + t2.getPriority());
		System.out.println("t3的优先级为：" + t3.getPriority());
		System.out.println("t4的优先级为：" + t4.getPriority());

		// 开启所有线程
		t4.start();
		t3.start();
		t2.start();
		t1.start();

		// 设置一定时间休眠，让 5 个线程进入阻塞状态。
		Thread.sleep(1000);
		// 通知所有线程开始抢锁，查看是否按照线程优先级运行。
		synchronized (lock) {
			lock.notifyAll();
		}
	}
}