package CoreKnowledge.StopThread;

public class ReSendInterrupt implements Runnable {
	// 因为 run() 方法是重写 Runnable 接口的方法，不能抛出比父类更大的异常。
	// 所以所有 InterruptedException 异常必需在 run() 方法中处理掉。
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			try {
				// 方法中抛出了 InterruptedException 异常，这里的开发者明确知道怎么处理异常。
				throwInMethod();
			} catch (InterruptedException e) {
				// 因为在 while 块中嵌套了 try/catch 块，所以重新发出中断信号，以便 while 判断可以检测到。
				Thread.currentThread().interrupt();
				e.printStackTrace();
			}
		}
	}

	// 将抛出的 InterruptedException 异常返回上一层级处理。
	private void throwInMethod() throws InterruptedException {
		Thread.sleep(2000);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new ReInterrupt());
		thread.start();
		Thread.sleep(1000);
		thread.interrupt();
	}
}
