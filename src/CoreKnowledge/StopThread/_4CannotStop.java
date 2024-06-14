package CoreKnowledge.StopThread;

/**
 * 描述：如果 while 里面放 try/catch，会导致中断失效
 */
public class _4CannotStop {
	public static void main(String[] args) throws InterruptedException {
		Runnable r = () -> {
			int num = 0;
			while (num <= 10000 && !Thread.currentThread().isInterrupted()) {
				if (num % 100 == 0) {
					System.out.println(num + "是100的倍数");
				}
				num++;
				// 在 while 循环里面捕获 InterruptedException 异常会清除中断信号，进而导致中断失效。
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread thread = new Thread(r);
		thread.start();
		Thread.sleep(5000);
		thread.interrupt();
	}
}
