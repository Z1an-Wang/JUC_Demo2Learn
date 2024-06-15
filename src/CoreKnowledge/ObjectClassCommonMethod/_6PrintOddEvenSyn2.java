package CoreKnowledge.ObjectClassCommonMethod;

/**
 * 两个线程交替打印 0～100 的奇偶数，用 wait/notify 实现，减少空操作。
 * 1. 一旦获取锁，就打印，不再判断奇偶（哪个线程先启动，哪个线程是打印偶数的线程）。
 * 2. 打印完成后，唤醒其他线程，自己休眠（放弃锁）。
 * 两个线程轮番获取锁，轮番打印奇偶数。
 */
public class _6PrintOddEvenSyn2 {
	private static int count = 0;
	private static final Object lock = new Object();

	public static void main(String[] args) {
		// 哪个线程先启动，哪个线程是打印偶数的线程。
		new Thread(new TurningRunner()).start();
		new Thread(new TurningRunner()).start();
	}

	static class TurningRunner implements Runnable {
		@Override
		public void run() {
			while (count <= 100) {
				synchronized (lock) {
					// 拿到锁，直接打印数字，并自增。
					System.out.println(Thread.currentThread().getName() + " : " + count++);
					// 唤醒另一个线程。
					lock.notify();
					// 如果任务还没结束，就进入等待状态，并释放锁。
					if (count <= 100) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
