package CoreKnowledge.ObjectClassCommonMethod;

/**
 * 证明 wait 只释放当前对象的 monitor 锁。
 */
public class _3OnlyReleaseSelfMonitor {
	// 创建 2 个资源，其 monitor 锁相互独立。
	private static final Object resourceA = new Object();
	private static final Object resourceB = new Object();

	public static void main(String[] args) throws InterruptedException {
		// 在 线程1 中先后获取 资源a、b 的两把锁，并且通过调用 wait() 只释放 资源a 的锁，并持有 资源b 的锁。
		Thread t1 = new Thread(() -> {
			synchronized (resourceA) {
				System.out.println(Thread.currentThread().getName() + " get resourceA lock.");
				synchronized (resourceB) {
					System.out.println(Thread.currentThread().getName() + " get resourceB lock.");
					try {
						// 在这里释放了 resourceA 的锁，仍持有 resourceB 的锁。
						resourceA.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 这里可以获得 资源a 的锁，但无法获得 资源b 的锁。
			synchronized (resourceA) {
				System.out.println(Thread.currentThread().getName() + " can obtain resourceA lock.");
				// resourceB.notify();     // 这里没有获取到 resourceB 的锁，会抛出 IllegalMonitorStateException 异常。
				synchronized (resourceB) {
					System.out.println(Thread.currentThread().getName() + " can obtain resourceB lock.");
				}
			}
		});

		t1.start();
		t2.start();  // 饥饿，线程一直阻塞。
	}
}
