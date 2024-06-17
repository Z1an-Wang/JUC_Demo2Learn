package CoreKnowledge.ThreadOtherMethod;

/**
 * 演示 Join ，要注意语句的输出顺序，会变化。
 */
public class _4Join {

	public static void main(String[] args) throws InterruptedException {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "执行完毕！");
			}
		};
		// 开启两个线程
		Thread t1 = new Thread(r);
		Thread t2 = new Thread(r);
		t1.start();
		t2.start();

		// 等待两个线程都执行完毕（先后顺序取决于线程调度）。
		System.out.println("开始等待子线程运行完毕 ... ");
		t1.join();
		t2.join();

		// 输出结束语句。
		System.out.println("所有子线程执行完毕！");
	}
}
