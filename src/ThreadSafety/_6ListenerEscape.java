package ThreadSafety;

/**
 * 在构造函数中使用匿名内部类，而匿名内部类持有外部类的引用。不当的调用会造成【逸出】。
 */
public class _6ListenerEscape {
	private int count;

	public _6ListenerEscape(MySource source) {
		// 向目标（被观察者）中添加 Listener（观察者）。
		// 理论上 count 的作用域应该是在 _6ListenerEscape 中，且在构造函数执行完毕后，才能被访问。
		source.registerListener(new EventListener() {
			@Override
			public void onEvent(Event e) {
				System.out.println("\nThe number I get is : " + count);
			}
		});
		// 在这里 new 的 EventListener 就已经把 ListenerEscape 对象隐式发布了【逸出】，因为匿名内部类持有外部类的引用。
		// 而 ListenerEscape 对象尚未初始化完成，所以输出结果为 0 。

		// 执行业务逻辑（一定时间后）
		for (int i = 0; i < 10000; i++) {
			System.out.print(i);
		}
		// 再对 count 赋值
		count = 100;
	}

	public static void main(String[] args) throws InterruptedException {
		// 创建一个目标（被观察者）。
		MySource mySource = new MySource();        // 此时 listener = null

		// 其他线程发现一个需要上报 观察者 的事件。
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 如果线程运行到此时，还未注册监听器，则打印：监听器还未初始化！
				// 我们让线程休眠一段时间后执行，期望可以打印正确的 count 值 （100）！
				mySource.eventCome(new Event() {
				});
			}
		}).start();

		// 将目标作为参数传入构造函数，在此时注册监听器，并赋值 count。
		_6ListenerEscape le = new _6ListenerEscape(mySource);
	}


	/**
	 * 观察者模式
	 * 被观察对象：MySource。    观察者：EventListener。
	 * 被观察对象发生一个事件 Event，调用 eventCome() 告知观察者进行处理。
	 */
	static class MySource {
		private EventListener listener;

		void registerListener(EventListener eventListener) {
			this.listener = eventListener;
		}

		void eventCome(Event e) {
			if (listener != null) {
				listener.onEvent(e);
			} else {
				System.out.println("监听器还未初始化！");
			}
		}
	}

	interface EventListener {
		void onEvent(Event e);
	}

	interface Event {
	}
}
