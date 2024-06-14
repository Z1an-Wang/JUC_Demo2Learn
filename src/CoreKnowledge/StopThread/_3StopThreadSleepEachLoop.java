package CoreKnowledge.StopThread;

public class _3StopThreadSleepEachLoop {
	public static void main(String[] args) throws InterruptedException {
		Runnable r = () -> {
			int num = 0;
			try {
				while (num <= 10000) {
					if (num % 100 == 0) {
						System.out.println(num + " 是 100 的倍数。");
					}
					num++;
					// 如果每次迭代后子线程都被阻塞，相当于在循环体中放入了 Thread.currentThread().isInterrupted() 判断语句。
					Thread.sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		Thread t = new Thread(r);
		t.start();
		Thread.sleep(5000);
		t.interrupt();
	}
}
