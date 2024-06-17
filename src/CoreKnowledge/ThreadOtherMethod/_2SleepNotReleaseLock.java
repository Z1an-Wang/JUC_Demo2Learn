package CoreKnowledge.ThreadOtherMethod;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 展示线程 sleep 的时候，不释放 Lock （Lock 需要手动释放）。
 */
public class _2SleepNotReleaseLock implements Runnable {
	// 独占锁
	private static final Lock lock = new ReentrantLock();

	public static void main(String[] args) {
		Runnable sleepNotReleaseLock = new _2SleepNotReleaseLock();

		new Thread(sleepNotReleaseLock).start();
		new Thread(sleepNotReleaseLock).start();
	}

	@Override
	public void run() {
		lock.lock();
		System.out.println(Thread.currentThread().getName() + "线程获取到了 Lock");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// finally 块无论如何都会被执行。
		// 将 lock 解锁。
		finally {
			System.out.println(Thread.currentThread().getName() + "休眠结束。");
			lock.unlock();
		}
	}
}
