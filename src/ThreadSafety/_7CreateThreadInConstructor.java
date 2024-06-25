package ThreadSafety;

import java.util.HashMap;
import java.util.Map;

/**
 * 在构造函数中新建线程，会导致对象【逸出】。
 */
public class _7CreateThreadInConstructor {
	private Map<Integer, String> students;

	public _7CreateThreadInConstructor() {
		// 在构造函数中，开启一个线程，在线程中对 students 进行赋值。
		// 子线程中的赋值操作是需要时间的，然后构造函数开启线程后就认为 students 对象已经初始化完毕了。
		new Thread(new Runnable() {
			@Override
			public void run() {
				students = new HashMap<>();
				students.put(1, "王小美");
				students.put(2, "钱二宝");
				students.put(3, "周三");
				students.put(4, "赵四");
			}
		}).start();
	}

	public Map<Integer, String> getStudents() {
		return students;
	}

	public static void main(String[] args) throws InterruptedException {
		_7CreateThreadInConstructor createThreadInConstructor = new _7CreateThreadInConstructor();
		Map<Integer, String> s = createThreadInConstructor.getStudents();
		// 由于创建对象在子线程中进行，主线程开启子线程后直接返回，此时 Map 还未得到初始化，会有 NPE 异常。
		System.out.println(s.get(1));
	}
}
