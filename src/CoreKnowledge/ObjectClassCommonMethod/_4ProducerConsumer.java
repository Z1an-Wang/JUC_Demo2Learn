package CoreKnowledge.ObjectClassCommonMethod;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 用 wait / notify 来实现生产者消费者模式
 */
public class _4ProducerConsumer {

	public static void main(String[] args) {
		EventStorage<Integer> es = new EventStorage<>();
		Producer producer = new Producer(es);
		Consumer consumer = new Consumer(es);
		new Thread(producer).start();
		new Thread(consumer).start();
	}
}

class Producer implements Runnable {
	private final EventStorage<Integer> storage;

	public Producer(EventStorage<Integer> storage) {
		this.storage = storage;
	}

	@Override
	public void run() {
		Random rand = new Random(0);
		for (int i = 0; i < 100; i++) {
			storage.put(rand.nextInt(128));
		}
	}
}

class Consumer implements Runnable {
	private final EventStorage<Integer> storage;

	public Consumer(EventStorage<Integer> storage) {
		this.storage = storage;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			storage.take();
		}
	}
}

class EventStorage<T> {
	private int maxSize;
	private List<T> storage;

	public EventStorage() {
		maxSize = 10;
		storage = new LinkedList<T>();
	}

	public synchronized void put(T d) {
		// 如果队列满了，就阻塞。
		while (storage.size() == maxSize) {
			try {
				System.out.println("仓库满了，生产者阻塞...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// 队列还有空间，则添加对象，并 notify 消费者可以进行消费。
		storage.add(d);
		System.out.println("生产成功！仓库里已经有" + storage.size() + "个物品。");
		notify();
	}

	public synchronized void take() {
		// 如果队列为空，就阻塞。
		while (storage.size() == 0) {
			try {
				System.out.println("仓库为空，消费者阻塞...");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 队列中还有元素，则取出第一个元素，并 notify 生产者进行生产。
		System.out.println("取到了" + storage.get(0));
		storage.remove(0);
		System.out.println("现在仓库中还有" + storage.size() + "个物品。");
		notify();
	}
}
