package ThreadSafety;

import java.util.HashMap;
import java.util.Map;

public class _4PrivateEscape {

	// map 假设是一个读取的配置文件，是固定的。仅供读取，不希望被修改。
	private Map<String, String> states;

	public _4PrivateEscape() {
		this.states = new HashMap<>();
		states.put("apple", "good");
		states.put("banana", "normal");
		states.put("cherry", "bad");
	}

	// 不小心发布了 private 对象。【逸出】
	public Map<String, String> getStates() {
		return states;
	}

	public static void main(String[] args) {
		_4PrivateEscape pe = new _4PrivateEscape();

		// 错误地拿到了 private 数据，并对其进行修改。
		Map<String, String> states = pe.getStates();
		states.put("bad devil", "I succeed");
		System.out.println(states);

		// 配置列表被修改，可能返回 null 。
		System.out.println(states.get("apple"));
		states.remove("apple");
		System.out.println(states.get("apple"));
	}
}
