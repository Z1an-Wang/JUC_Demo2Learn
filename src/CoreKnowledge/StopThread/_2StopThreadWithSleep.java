package CoreKnowledge.StopThread;

public class _2StopThreadWithSleep {
	public static void main(String[] args) throws InterruptedException {
		Runnable r = () -> {
			int num = 0;
			try {
				while( !Thread.currentThread().isInterrupted() && num <= 300){
					if( num % 100 == 0){
						System.out.println(num+" 是 100 的倍数。");
					}
					num++;
				}
				// 在通过 sleep() 进入休眠中（WAITING状态）的线程，依然可以响应中断：通过抛出 InterruptedException 异常的方式：
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		Thread t = new Thread(r);
		t.start();
		Thread.sleep(500);
		t.interrupt();
	}
}
