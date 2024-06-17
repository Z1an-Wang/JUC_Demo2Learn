package CoreKnowledge.ThreadOtherMethod;

/**
 * 演示 join 期间被中断的效果。
 */
public class _5JoinInterrupt {

	public static void main(String[] args) {
		Thread mainThread = Thread.currentThread();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					// 子线程对主线程发出中断。
					mainThread.interrupt();
					Thread.sleep(5000);
					System.out.println(Thread.currentThread().getName() + "执行完毕！");
				} catch (InterruptedException e) {
					System.out.println(Thread.currentThread().getName() + "停止执行！");
					e.printStackTrace();

				}
			}
		};
		Thread t1 = new Thread(r);
		t1.start();
		System.out.println("开始等待子线程运行完毕 ... ");
		// 在 join 期间发生 interrupt 中断！【是谁遇到中断，是谁抛出异常！】
		// 主线程被阻塞！向主线程抛出中断信号！主线程在 join() 的地方抛出中断异常！
		try {
			t1.join();
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + "被中断了！");
			e.printStackTrace();
			// 最优实践：应该在主线程被中断之后，向子线程发出中断信号，让子线程停止执行。否则就会出现不一致的情况。
			t1.interrupt();
		}

		// 这里因为子线程向主线程发出了中断信号，主线程结束 join 阻塞并打印下面的话。
		// 然而子线程实际仍在执行 Thread.sleep(5000); 并没有结束（子线程独立运行）。
		// 如果主线程同时向子线程发出中断信号，两个线程并行结束进程，则线程结束的打印顺序由调度器（执行时间）决定。
		System.out.println("子线程执行完毕！");
	}
}
