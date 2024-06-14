package CoreKnowledge.CreateThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 描述：使用线程池 ThreadPool 创建线程，其底层还是调用 new Thread()。
 */
public class ThreadPool {
	public static void main (String[] args){
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < 1000; i++) {
			es.submit(new Task());
		}

	}
}

class Task implements Runnable {
	@Override
	public void run() {
		try{
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName());
	}
}
