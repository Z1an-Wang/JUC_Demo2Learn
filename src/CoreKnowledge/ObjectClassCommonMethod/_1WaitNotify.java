package CoreKnowledge.ObjectClassCommonMethod;

/**
 * 展示 wait 和 notify 的基本用法。
 * 1. 研究代码的执行顺序。
 * 2. 证明 wait 释放锁。
 */
public class _1WaitNotify {
	// 一个共享的资源，用来执行 wait 和 notify 的对象。
	public static Object obj = new Object();

	public static void main(String[] args) throws InterruptedException {
		T1 t1 = new T1();
		T2 t2 = new T2();
		t1.start();
		Thread.sleep(100);
		t2.start();
	}

	static class T1 extends Thread {
		@Override
		public void run() {
			// 获取到了 obj 的 monitor 锁，并进入代码块。
			synchronized (obj) {
				System.out.println(Thread.currentThread().getName() + "获取到了 obj 的锁，开始执行 ... ");
				// wait() 执行等待期间，会响应中断请求。
				try {
					// wait() 方法会释放掉 obj 的 monitor 锁。
					obj.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "再次获取到了 obj 的锁");
			}
		}
	}

	static class T2 extends Thread {
		@Override
		public void run() {
			// 需要获取到 obj 的 monitor 锁才能进入代码块。
			synchronized (obj) {
				// 虽然 notify 唤醒了一个等待该对象 monitor 的线程。
				obj.notify();
				// 但是要等待当前线程释放该对象的 monitor 锁，唤醒的线程（T1）才能再次获取 obj 的 monitor 锁 。
				System.out.println(Thread.currentThread().getName() + "调用了 notify()");
			}
		}
	}
}
