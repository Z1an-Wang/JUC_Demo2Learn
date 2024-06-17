package CoreKnowledge.ThreadOtherMethod;

/**
 * 查看 join 时的线程状态，先 join 再 mainThread.getState()
 * 通过 debugger 查看线程 join 前后的状态。
 */
public class _6JoinThreadState {
	public static void main(String[] args) throws InterruptedException {
		Thread mainThread = Thread.currentThread();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println(mainThread.getState());   // 输出 WAITING
					System.out.println(Thread.currentThread().getName() + "子线程结束运行！");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t1.start();
		System.out.println("开始等待子线程运行完毕 ... ");
		t1.join();
		System.out.println("子线程执行完毕！");
	}
}
