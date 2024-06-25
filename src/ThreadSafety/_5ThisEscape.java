package ThreadSafety;

/**
 * this 指针逸出：在构造函数中，未初始化完毕就赋值 this 引用。
 */
public class _5ThisEscape {
	public static Point point;

	public static void main(String[] args) throws InterruptedException {
		new PonitMaker().start();
		// 我们期待，point 一旦被发布，在未经修改的情况下，其值是稳定不变的。
		// this 指针的逸出导致在不同时刻，看到的 y 的值不同。
		// 如果在多线程环境中，调度器可以在任意时刻切换两个线程的执行，无法保证主线程的运行时刻。
		Thread.sleep(105);
		if (point != null) {
			System.out.println(point);
		}
	}
}

class Point {
	private final int x, y;

	public Point(int x, int y) throws InterruptedException {
		this.x = x;
		// 由于过早的把 this 指针发布给了 static 变量【逸出】
		_5ThisEscape.point = this;
		Thread.sleep(100);
		this.y = y;
	}

	@Override
	public String toString() {
		return x + ", " + y;
	}
}

class PonitMaker extends Thread {
	@Override
	public void run() {
		try {
			new Point(1, 1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
