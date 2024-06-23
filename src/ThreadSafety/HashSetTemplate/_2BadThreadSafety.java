package ThreadSafety.HashSetTemplate;

/**
 * Demonstrate the thread-safet issue about MyBadHashSet.
 * Multiple threads try to add elements to a hash set concurrently.
 * Referenced from http://www.doc.ic.ac.uk/~afd/teaching/Concurrency/JavaCode.tgz
 */
public class _2BadThreadSafety {
	public static void main(String[] args) throws InterruptedException {

		// 声明自己的 hash set
		final _1MyBadHashSet<String> hs = new _1MyBadHashSet<>();

		// 向 hash set 中逐个添加元素
		Runnable addElements = () -> {
			for (int i = 0; i < 1000; i++) {
				hs.add("word" + i);
			}
		};

		// 开启 3 个线程，同时向 hash set 中插入元素
		Thread writer1 = new Thread(addElements);
		Thread writer2 = new Thread(addElements);
		Thread writer3 = new Thread(addElements);
		writer1.start();
		writer2.start();
		writer3.start();

		// 等待 3 个线程执行完毕
		writer1.join();
		writer2.join();
		writer3.join();

		// 理论上，每一个 word 都只应该被加入到 hash set 中 1 次，并可以打印整个 hash set。
		// 实际，hash set 的 size 大于 1000，且访问 hash set 会抛出 NPE。
		// 因为在 MyBadHashSet ，使用 LinkedList 来实现 bucket。
		// LinkedList 不是线程安全的，所以多个线程并发地向 LinkedList 中添加元素时会导致未知的结果。
		System.out.println(hs.size());
		System.out.println(hs);
	}
}
