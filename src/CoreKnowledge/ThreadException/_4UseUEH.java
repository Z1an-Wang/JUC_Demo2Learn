package CoreKnowledge.ThreadException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class _4UseUEH {
	public static void main(String[] args) throws InterruptedException {
		Runnable r = () -> {
			throw new RuntimeException("Exception in " + Thread.currentThread().getName());
		};

		// 设置默认的 UEH 来处理全局的 未捕获异常。
		Thread.setDefaultUncaughtExceptionHandler(new _3UncaughtExceptionHandler("【全局】未捕获异常处理器"));
		new Thread(r, "MyThread-1").start();
		Thread.sleep(1000);
		new Thread(r, "MyThread-2").start();
		Thread.sleep(1000);

		// 设置线程的 UEH 。
		Thread t = new Thread(r, "MyThread-3");
		t.setUncaughtExceptionHandler(new _3UncaughtExceptionHandler("【线程】未捕获异常处理器"));
		t.start();
		Thread.sleep(1000);

		// 设置线程池的 UEH 。
		_3UncaughtExceptionHandler handler = new _3UncaughtExceptionHandler("【线程池】未捕获异常处理器");
		ExecutorService executor = Executors.newCachedThreadPool(new MyThreadFactory(handler));
		executor.execute(r);
		executor.execute(r);
		executor.shutdown();
		Thread.sleep(1000);

		System.out.println("Done!");
	}
}

class MyThreadFactory implements ThreadFactory {
	private _3UncaughtExceptionHandler handler;

	// 通过工厂函数，为每一个线程设置 UEH 。
	public MyThreadFactory(_3UncaughtExceptionHandler handler) {
		this.handler = handler;
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		// 在这里设置异常处理器
		thread.setUncaughtExceptionHandler(handler);
		return thread;
	}
}
