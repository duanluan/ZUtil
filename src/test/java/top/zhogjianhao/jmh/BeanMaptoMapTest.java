package top.zhogjianhao.jmh;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.springframework.cglib.beans.BeanMap;
import top.zhogjianhao.BeanUtilsTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * BeanMap 转 Map 的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class BeanMaptoMapTest {

  public static void main(String[] args) {
    // 结果是否相等
    BeanMaptoMapTest test = new BeanMaptoMapTest();
    System.out.println(test.putAll().equals(test.constructor()) && test.constructor().equals(test.foreach()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{BeanMaptoMapTest.class.getName()});
  }

  private static final BeanUtilsTest.TestBean testBean;

  static {
    testBean = new BeanUtilsTest.TestBean();
    List<BeanUtilsTest.TestBean> beanList = new ArrayList<>();
    beanList.add(new BeanUtilsTest.TestBean("1"));
    testBean.setBeanList(beanList);
  }

  @Benchmark
  public Map<String, Object> putAll() {
    HashMap<String, Object> map = new HashMap<>();
    map.putAll(BeanMap.create(testBean));
    return map;
  }

  @Benchmark
  public Map<String, Object> constructor() {
    return new HashMap<String, Object>(BeanMap.create(testBean));
  }

  @Benchmark
  public Map<String, Object> foreach() {
    Map<String, Object> map = new HashMap<>();
    BeanMap beanMap = BeanMap.create(testBean);
    for (Object key : beanMap.keySet()) {
      map.put(key.toString(), beanMap.get(key));
    }
    return map;
  }
}
