package CoreKnowledge.ThreadState;

/**
 * 描述：     展示Blocked, Waiting, TimedWaiting
 */
public class BlockedWaitingTimedWaiting implements Runnable{
	public static void main(String[] args) {
		BlockedWaitingTimedWaiting runnable = new BlockedWaitingTimedWaiting();
		Thread thread1 = new Thread(runnable);
		Thread thread2 = new Thread(runnable);
		thread1.start();
		thread2.start();
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 打印出 Timed_Waiting 状态，因为正在执行 Thread.sleep(1000);
		System.out.println(thread1.getState());
		// 打印出 BLOCKED 状态，因为 thread2 想拿得到 sync() 的锁却拿不到
		System.out.println(thread2.getState());
		try {
			Thread.sleep(1300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 打印出 WAITING 状态，因为执行了 wait()
		System.out.println(thread1.getState());
	}

	@Override
	public void run() {
		syn();
	}

	private synchronized void syn() {
		try {
			Thread.sleep(1000);
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
