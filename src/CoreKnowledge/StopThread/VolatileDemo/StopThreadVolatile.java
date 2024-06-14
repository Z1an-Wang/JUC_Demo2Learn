package CoreKnowledge.StopThread.VolatileDemo;

/**
 *     演示用 volatile 的局限：part1 看似可行
 */
public class StopThreadVolatile implements Runnable {
	// volatile keyword is used to indicate that a variable's value may be
	// changed by multiple threads simultaneously.
	// 用 volatile 修饰的变量，表示不启用线程缓存，每次访问内存获取数据。换句话说，每个
	// 线程看到的变量值都是一样的，且为任意一个线程最后写入的值。
	private volatile boolean canceled = false;
	public void setCanceled(boolean state) { this.canceled = state; }
	@Override
	public void run() {
		int num = 0;
		try {
			while (num <= 100000 && !canceled) {
				if (num % 100 == 0) {
					System.out.println(num + "是100的倍数。");
				}
				num++;
				Thread.sleep(1);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		StopThreadVolatile r = new StopThreadVolatile();
		Thread t = new Thread(r);
		t.start();
		Thread.sleep(1000);
		// 对于主线程修改的值，子线程立即可见
		r.setCanceled(true);
	}
}
