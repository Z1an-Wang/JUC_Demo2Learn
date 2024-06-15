package CoreKnowledge.ObjectClassCommonMethod;

/**
 * 两个线程交替打印 0～100 的奇偶数，用 synchrinized 关键字实现。
 * 1. 创建两个线程，线程1 只处理偶数，线程2 只处理奇数。
 * 2. 只用 synchronized 来处理不同线程间的通信。
 */
public class _5PrintOddEverSyn {
	private static int count = 0;
	private static final Object lock = new Object();

	public static void main(String[] args) {
		// 如果只使用 synchronized 关键字，两个线程启动后，
		// 如果奇线程的 count 符合要求，则打印自增，否则直接释放锁，并加入到下一次的锁争抢中。偶线程亦然。
		// 在经历数次锁争抢后，奇线程或偶线程总会得到锁并轮番处理数据。
		// 如果偶/奇线程连续获取到锁，则会造成数次空操作，导致性能浪费。
		Thread odd = new Thread(new Runnable() {
			@Override
			public void run() {
				while (count < 100) {
					synchronized (lock) {
						// 如果发现它是一个偶数，则打印偶数并自增。
						if (count % 2 == 0)
							System.out.println(Thread.currentThread().getName() + " : " + count++);
					}
				}
			}
		});

		Thread even = new Thread(new Runnable() {
			@Override
			public void run() {
				while (count < 100) {
					synchronized (lock) {
						// 如果发现它是一个奇数数，则打印奇数并自增。
						if (count % 2 == 1)
							System.out.println(Thread.currentThread().getName() + " : " + count++);
					}
				}
			}
		});

		even.start();
		odd.start();
	}
}
