package CoreKnowledge.StopThread;

public class StopThreadWithoutSleep implements Runnable {
	@Override
	public void run() {
		int num = 0;
		while( !Thread.currentThread().isInterrupted() && num <= Integer.MAX_VALUE/2 ){
			if(num % 10000 == 0){
				System.out.println(num + "是 10000 的倍数");

			}
			num++;
		}
		System.out.println("Finished!");
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new StopThreadWithoutSleep());
		t.start();
		Thread.sleep(1000);
		t.interrupt();
	}
}
