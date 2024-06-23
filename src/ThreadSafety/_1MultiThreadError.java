package ThreadSafety;

/**
 * 运行结果出错
 * 演示 计数不准确（少计），找出具体出错位置
 */
public class _1MultiThreadError {
	static int index = 0;
	static Runnable instance = new Runnable() {
		@Override
		public void run() {
			for (int i = 0; i < 10000; i++) {
				index++;
			}
		}
	};

	public static void main(String[] args) throws InterruptedException {

		// 开启两个线程，每个线程都自增 10000 次。
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		// 预期结果为 20000，实际结果每次不同且小于 20000.
		System.out.println(index);
	}
}
