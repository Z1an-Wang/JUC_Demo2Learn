package CoreKnowledge.ThreadOtherMethod;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 每隔 1 秒钟输出当前时间，被中断，观察。
 * 两种方法（方法二包装了普通的 sleep 方法）：
 * 1. Thread.sleep(1000)
 * 2. TimeUnit.SECONDS.sleep(1) 【更推荐】
 *    工具类方便线程休眠指定时间（以时分秒为单位）。
 *    如果参数小于零，会抛出异常（更安全）。
 */
public class _3SleepInterrupted implements Runnable {

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(new _3SleepInterrupted());
		t1.start();
		Thread.sleep(5000);
		// 发出中断信号。
		t1.interrupt();
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			System.out.println(new Date());
			try {
				// 在 sleep 期间接收主线程发来的 interrupt 信号，并响应。
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// 这里捕获异常后，并没有 传递中断 or 恢复中断，所以程序继续执行。
				System.out.println("线程响应中断！");
				e.printStackTrace();
			}
		}
	}
}
