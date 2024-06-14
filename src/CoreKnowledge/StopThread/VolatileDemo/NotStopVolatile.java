package CoreKnowledge.StopThread.VolatileDemo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 *   演示用 volatile 的局限性：
 *   陷入阻塞时，volatile 是无法停止线程的。
 *   此例中，生产者的生产速度很快，消费者消费速度慢，
 *   所以阻塞队列满了以后，生产者会阻塞，等待消费者进一步消费。
 */
public class NotStopVolatile {
	public static void main(String[] args) throws InterruptedException{
		ArrayBlockingQueue<Integer> storage = new ArrayBlockingQueue<>(10);
		Producer p = new Producer(storage);
		Thread t = new Thread(p);
		// 生产者开始生产，塞满阻塞队列后阻塞后续生产。
		t.start();
		Thread.sleep(1000);

		// 消费者开始消费，直至消费结束。
		Consumer c = new Consumer(storage);
		System.out.println(c.takeNumbers(10).toString());

		// 关闭生产者，但实际情况：程序仍在运行。
		p.canceled=true;
		System.out.println("volatile flag set : " + p.canceled);
		System.out.println("Thread state : " + t.getState());
	}
}


class Producer implements Runnable{
	public volatile boolean canceled = false;
	public BlockingQueue<Integer> bq;
	public Producer(BlockingQueue<Integer> bq)  { this.bq = bq; }
	@Override
	public void run() {
		int num = 0;
		try {
			while (num <= 100000 && !canceled) {
				if (num % 100 == 0) {
					// BlockingQueue 中的 put() 和 take() 方法 可以在阻塞中响应中断，并抛出异常。
					// 但是使用 volatile 标志位的话，程序会一直阻塞在这里，无法检查 canceled 变量，也无法退出。
					bq.put(num);
					System.out.println(num + "是100的倍数,被放到仓库中了。");
				}
				num++;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			System.out.println("生产者结束运行");
		}
	}
}

class Consumer {
	public BlockingQueue<Integer> bq;
	public Consumer(BlockingQueue<Integer> bq) { this.bq = bq; }
	public List<Integer> takeNumbers (int num) throws InterruptedException{
		LinkedList<Integer> res = new LinkedList<>();
		for(int i=0; i<num; i++){
			res.addLast(bq.take());
			System.out.println(res.peekLast() + "被消费了");
			// 保证消费完成后，生产者继续生产，保持队列阻塞。
			Thread.sleep(100);
		}
		System.out.println("消费者完毕！");
		return res;
	}
}