package ThreadSafety;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 演示 i++ 操作，在多线程环境中会出现错误结果。
 * 通过工具类，统计出错次数，和位置。
 */
public class _2ObtainErrorHappenPos implements Runnable {
	int index = 0;
	final boolean[] marked = new boolean[350000];
	AtomicInteger realIndex = new AtomicInteger();
	AtomicInteger wrongCount = new AtomicInteger();

	// 可以让线程执行的时候，通过 barrier（闸门/屏障）来控制不同线程中代码的执行速度，即：让多个线程在某个共同点上同步等待，然后同时继续执行。
	volatile CyclicBarrier cyclicBarrier1 = new CyclicBarrier(3);
	volatile CyclicBarrier cyclicBarrier2 = new CyclicBarrier(3);

	final static _2ObtainErrorHappenPos instance = new _2ObtainErrorHappenPos();

	@Override
	public void run() {
		for (int i = 0; i < 100000; i++) {
			// 需要三个线程同时到达此处，才可放行。
			// 第一个栅栏防止 线程1 执行完同步代码块后，线程2 进入同步代码块中时，线程1 进行 index++
			try {
				cyclicBarrier2.reset();            // reset 重制栅栏，让下一次代码同步生效。
				cyclicBarrier1.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			index++;
			// 第二个栅栏，确保所有线程都进行了 index++ 操作，再放行到同步代码块。
			try {
				cyclicBarrier1.reset();            // reset 重制栅栏，让下一次代码同步生效。
				cyclicBarrier2.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}

			realIndex.incrementAndGet();        // 通过工具类，计算准确的 a++ 的值。

			// 使用 synchronized 同步代码块，保证代码块中的 marked 只能被一个线程查看并修改。
			// 但是 线程1 进入同步代码块中，线程2 虽然进不去，但是可以执行 index++ 的操作。导致 线程1 访问到错误的数据。
			synchronized (instance) {
				// 如果 线程1 执行完循环体后，在把 marked[index] 设置为 true 之前，切换到 线程2，
				// 就会导致统计到的错误次数，少于实际发生的次数。
				if ((marked[index - 1] || marked[index - 2]) && !marked[index]) {        // 如果三个线程都经过 barrier 同步，达到此处，则 index 已经被自增了 3 次！
					System.out.println("Error occurred at " + index);
					wrongCount.incrementAndGet();        // 通过工具类，统计发生错误的次数。
				}
				marked[index] = true;
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		// 开启 3 个线程（发生线程错误的概率更大），每个线程都自增 100000 次。
		Thread t1 = new Thread(instance);
		Thread t2 = new Thread(instance);
		Thread t3 = new Thread(instance);
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();

		// 预期结果为 300000。
		System.out.println("a++ 运行的结果：" + instance.index);
		System.out.println("真正的运行结果：" + instance.realIndex.get());
		System.out.println("错误发生次数：" + instance.wrongCount.get());
	}
}
