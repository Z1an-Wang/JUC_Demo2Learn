package CoreKnowledge.StopThread;

/**
 * 最佳实践1： catch 了 InterruptedException 之后的优先选择：不处理异常，而是在方法签名中抛出异常，那么在 run() 就会强制 try/catch
 */
public class _5ReSendInterrupt implements Runnable {
	// 因为 run() 方法是重写 Runnable 接口的方法，不能抛出比父类更大的异常。
	// 所以所有 InterruptedException 异常必需在 run() 方法中处理掉。
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				System.out.println("go");
				// 方法中抛出了 InterruptedException 异常，这里的开发者明确知道怎么处理异常。
				throwInMethod();
			} catch (InterruptedException e) {

				// 捕获到了 interrupt 中断异常，保存日志，停止程序 ...

				// 因为在 while 块中嵌套了 try/catch 块，所以重新发出中断信号，以便 while 循环可以检测到。
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

	// 子线程中调用的其他方法中，不应该私自 try/catch 处理异常，而是应该在方法签名中抛出异常，将中断异常交给子线程的最顶层（ run()方法中 ）中处理。
	private void throwInMethod() throws InterruptedException {
		Thread.sleep(2000);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new _5ReSendInterrupt());
		thread.start();
		Thread.sleep(1000);
		thread.interrupt();
	}
}
