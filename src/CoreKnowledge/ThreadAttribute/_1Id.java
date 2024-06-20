package CoreKnowledge.ThreadAttribute;

/**
 * 线程 ID 从 1 开始，JVM 运行起来后，用户创建的子线程早已不是 2（ JVM 已经创建了很多个线程）。
 */
public class _1Id {
	public static void main(String[] args) {
		Thread t = new Thread();
		System.out.println("主线程的 ID：" + Thread.currentThread().getId());
		System.out.println("子线程的 ID：" + t.getId());
	}
}
