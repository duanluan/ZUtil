package top.csaf.thread;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.HashMap;
import java.util.Map;

/**
 * ThreadLocal 工具类，使用 TransmittableThreadLocal
 */
public class ThreadLocalUtil {

  private static final TransmittableThreadLocal<Map<String, Object>> THREAD_LOCAL = new TransmittableThreadLocal<Map<String, Object>>() {
    @Override
    protected Map<String, Object> initialValue() {
      return new HashMap<>();
    }
  };

  /**
   * 设置变量
   */
  public static void set(String key, Object value) {
    THREAD_LOCAL.get().put(key, value);
  }

  /**
   * 获取变量
   */
  public static Object get(String key) {
    return THREAD_LOCAL.get().get(key);
  }

  /**
   * 删除变量
   */
  public static void remove(String key) {
    THREAD_LOCAL.get().remove(key);
  }

  /**
   * 清除当前线程的所有变量
   */
  public static void clear() {
    THREAD_LOCAL.remove();
  }
}
