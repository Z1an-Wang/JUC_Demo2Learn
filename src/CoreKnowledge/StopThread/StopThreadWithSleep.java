package CoreKnowledge.StopThread;

public class StopThreadWithSleep {
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
