package ThreadSafety;

/**
 * 演示死锁现象的发生：两个线程循对两个对象环拿锁。
 */
public class _3Deadlock {
	private static final Object resource1 = new Object();        //资源 1
	private static final Object resource2 = new Object();        //资源 2

	public static void main(String[] args) {
		new Thread(() -> {
			synchronized (resource1) {
				System.out.println(Thread.currentThread().getName() + " get resource1!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "waiting get resource2 ... ");
				synchronized (resource2) {
					System.out.println(Thread.currentThread().getName() + " get resource2!");
				}
			}
		}, "线程 1").start();

		new Thread(() -> {
			synchronized (resource2) {
				System.out.println(Thread.currentThread().getName() + " get resource2!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "waiting get resource1 ... ");
				synchronized (resource1) {
					System.out.println(Thread.currentThread().getName() + " get resource1!");
				}
			}
		}, "线程 2").start();
	}
}
