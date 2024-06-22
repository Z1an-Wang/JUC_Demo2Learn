package CoreKnowledge.ThreadException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 实现自己的未捕获异常处理器
 */
public class _3UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

	private String name;

	public _3UncaughtExceptionHandler(String name) {
		this.name = name;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		// 将错误记录到日志中。
		Logger logger = Logger.getAnonymousLogger();
		logger.log(Level.WARNING, "线程异常，已终止 " + t.getName(), e);
		// 将异常信息输出到控制台。
		System.out.println(name + " 捕获了线程 " + t.getName() + " 的异常： " + e.toString());
	}
}
