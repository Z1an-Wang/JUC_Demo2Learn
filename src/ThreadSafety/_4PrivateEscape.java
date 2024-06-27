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

	// 要解决上述的逸出问题，返回对象的副本
	public Map<String, String> getStatesImproved() {
		return new HashMap<>(states);
	}

	public static void main(String[] args) {
		_4PrivateEscape pe = new _4PrivateEscape();

		// 错误地拿到了 private 数据，并对其进行修改。
		Map<String, String> states = pe.getStates();
		states.put("bad devil", "I succeed");
		System.out.println(states);

		// 配置列表被修改，可能返回 null 。
//		System.out.println(states.get("apple"));
//		states.remove("apple");
//		System.out.println(states.get("apple"));

		// （需注释掉上面报错的代码）测试 【逸出】 的解决方案。
		System.out.println(pe.getStatesImproved().get("apple"));
		pe.getStatesImproved().remove("apple");
		System.out.println(pe.getStatesImproved().get("apple"));
	}
}
