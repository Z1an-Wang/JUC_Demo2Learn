package ThreadSafety;

/**
 * 使用工厂模式解决注册监听事件中的隐式逸出。
 * 在工厂模式中，对象一旦发布，就是一个完整的对象！
 */
public class _8ListenerEscapeImproved {
	private int count;
	private EventListener listener;

	// 构造函数使用 private 保护起来。构造函数负责执行冗杂的初始化工作，最终获取到一个完整的对象。
	private _8ListenerEscapeImproved(MySource source) {
		this.listener = new EventListener() {
			@Override
			public void onEvent(Event e) {
				System.out.println("\nThe number I get is : " + count);
			}
		};
		for (int i = 0; i < 10000; i++) {
			System.out.print(i);
		}
		count = 100;
	}

	// 不再使用构造函数创建目标对象，而是使用静态的工厂方法！在工厂方法中调用构造函数实现整个构造过程，再把整个对象发布出去。
	public static _8ListenerEscapeImproved getInstance(MySource source) {
		// 先创建一个完整的目标对象，其中包含 listener 和 已经赋值的 count。
		_8ListenerEscapeImproved completedObj = new _8ListenerEscapeImproved(source);
		// 在注册监听器之前，如果其他线程调用 onEvent 会返回错误信息字符串。
		// 此时已经完成了对象的创建，再注册对象的监听器，之后调用的 onEvent 访问到的是完整的对象。【这里发布对象是安全的】
		source.registerListener(completedObj.listener);
		return completedObj;
	}

	public static void main(String[] args) throws InterruptedException {
		MySource mySource = new MySource();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mySource.eventCome(new Event() {
				});
			}
		}).start();

		ThreadSafety._8ListenerEscapeImproved le = _8ListenerEscapeImproved.getInstance(mySource);
	}

	/**
	 * 观察者模式
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
				System.out.println("\n监听器还未初始化！");
			}
		}
	}

	interface EventListener {
		void onEvent(Event e);
	}

	interface Event {
	}
}
