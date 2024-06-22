package CoreKnowledge.ThreadException;

/**
 * 如果单线程遇到异常，抛出异常，打印异常堆栈信息，停止运行。
 * 如果多线程遇到异常，子线程抛出异常后停止运行，而主线程依然正常运行。
 */
public class _1ExceptionInChildThread implements Runnable {
	public static void main(String[] args) throws InterruptedException {
		new Thread(new _1ExceptionInChildThread()).start();
		Thread.sleep(500);
		System.out.println("Still Running ...");
		Thread.sleep(500);
		System.out.println("Still Running ...");
		Thread.sleep(500);
		System.out.println("Still Running ...");
	}

	@Override
	public void run() {
		throw new RuntimeException("子线程发生异常！");
	}
}
