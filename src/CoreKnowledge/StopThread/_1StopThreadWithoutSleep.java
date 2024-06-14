package CoreKnowledge.StopThread;

public class _1StopThreadWithoutSleep implements Runnable {
	@Override
	public void run() {
		int num = 0;
		// 通过 Thread.currentThread().isInterrupted() 来查看主线程是否发出 interrupt 中断。
		while( !Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE/2 ){
			if(num % 10000 == 0){
				System.out.println(num + "是 10000 的倍数");
			}
			num++;
		}
		System.out.println("Finished!");
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new _1StopThreadWithoutSleep());
		t.start();
		Thread.sleep(1000);
		t.interrupt();
	}
}
