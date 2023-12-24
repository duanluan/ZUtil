package top.csaf.jmh.base.map;

import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.springframework.cglib.beans.BeanMap;
import top.csaf.junit.BeanUtilTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 循环 Map 键值对的性能测试
 */
@State(Scope.Benchmark)
@Threads(1)
@Fork(1)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@BenchmarkMode(Mode.All)
public class KeySetEntrySetTest {

  public static void main(String[] args) {
    // 结果是否相等
    KeySetEntrySetTest test = new KeySetEntrySetTest();
    System.out.println(test.keySet().equals(test.entrySet()));
    System.out.println(test.beanMapKeySet().equals(test.beanMapEntrySet()));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{KeySetEntrySetTest.class.getName()});
  }

  @Benchmark
  public String keySet() {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "张三");
    map.put("age", 18);
    String age = "";
    for (String key : map.keySet()) {
      Object value = map.get(key);
      if ("age".equals(key)) {
        age = value.toString();
      }
    }
    return age;
  }

  @Benchmark
  public String entrySet() {
    Map<String, Object> map = new HashMap<>();
    map.put("name", "张三");
    map.put("age", 18);
    String age = "";
    for (Map.Entry<String, Object> entry : map.entrySet()) {
      String key = entry.getKey();
      Object value = entry.getValue();
      if ("age".equals(key)) {
        age = value.toString();
      }
    }
    return age;
  }

  @Benchmark
  public String beanMapKeySet() {
    BeanUtilTest.TestBean testBean = new BeanUtilTest.TestBean();
    testBean.setName("1");

    Map<String, Object> map = new HashMap<>();
    BeanMap beanMap = BeanMap.create(testBean);
    for (Object key : beanMap.keySet()) {
      map.put(key.toString(), beanMap.get(key));
    }
    return map.get("name").toString();
  }

  @Benchmark
  public String beanMapEntrySet() {
    BeanUtilTest.TestBean testBean = new BeanUtilTest.TestBean();
    testBean.setName("1");

    Map<String, Object> map = new HashMap<>();
    BeanMap beanMap = BeanMap.create(testBean);
    for (Object entryObj : beanMap.entrySet()) {
      Map.Entry<String, Object> entry = (Map.Entry<String, Object>) entryObj;
      map.put(entry.getKey(), entry.getValue());
    }
    return map.get("name").toString();
  }
}

// Benchmark                                       Mode     Cnt     Score    Error   Units
// Test.beanMapEntrySet                           thrpt       5     5.599 ±  0.057  ops/us
// Test.beanMapKeySet                             thrpt       5     8.343 ±  1.120  ops/us
// Test.entrySet                                  thrpt       5    17.807 ±  0.555  ops/us
// Test.keySet                                    thrpt       5    17.309 ±  0.812  ops/us
// Test.beanMapEntrySet                            avgt       5     0.195 ±  0.003   us/op
// Test.beanMapKeySet                              avgt       5     0.121 ±  0.001   us/op
// Test.entrySet                                   avgt       5     0.056 ±  0.001   us/op
// Test.keySet                                     avgt       5     0.057 ±  0.001   us/op
// Test.beanMapEntrySet                          sample  105980     0.227 ±  0.010   us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.00    sample             0.100            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.50    sample             0.200            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.90    sample             0.300            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.95    sample             0.300            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.99    sample             0.400            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.999   sample             3.500            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p0.9999  sample            17.242            us/op
// Test.beanMapEntrySet:beanMapEntrySet·p1.00    sample           285.184            us/op
// Test.beanMapKeySet                            sample  148939     0.184 ±  0.032   us/op
// Test.beanMapKeySet:beanMapKeySet·p0.00        sample             0.100            us/op
// Test.beanMapKeySet:beanMapKeySet·p0.50        sample             0.200            us/op
// Test.beanMapKeySet:beanMapKeySet·p0.90        sample             0.200            us/op
// Test.beanMapKeySet:beanMapKeySet·p0.95        sample             0.200            us/op
// Test.beanMapKeySet:beanMapKeySet·p0.99        sample             0.300            us/op
// Test.beanMapKeySet:beanMapKeySet·p0.999       sample             3.306            us/op
// Test.beanMapKeySet:beanMapKeySet·p0.9999      sample            27.134            us/op
// Test.beanMapKeySet:beanMapKeySet·p1.00        sample          1224.704            us/op
// Test.entrySet                                 sample  173439     0.093 ±  0.017   us/op
// Test.entrySet:entrySet·p0.00                  sample               ≈ 0            us/op
// Test.entrySet:entrySet·p0.50                  sample             0.100            us/op
// Test.entrySet:entrySet·p0.90                  sample             0.100            us/op
// Test.entrySet:entrySet·p0.95                  sample             0.100            us/op
// Test.entrySet:entrySet·p0.99                  sample             0.200            us/op
// Test.entrySet:entrySet·p0.999                 sample             0.500            us/op
// Test.entrySet:entrySet·p0.9999                sample            21.892            us/op
// Test.entrySet:entrySet·p1.00                  sample           638.976            us/op
// Test.keySet                                   sample  158461     0.092 ±  0.018   us/op
// Test.keySet:keySet·p0.00                      sample               ≈ 0            us/op
// Test.keySet:keySet·p0.50                      sample             0.100            us/op
// Test.keySet:keySet·p0.90                      sample             0.100            us/op
// Test.keySet:keySet·p0.95                      sample             0.100            us/op
// Test.keySet:keySet·p0.99                      sample             0.200            us/op
// Test.keySet:keySet·p0.999                     sample             0.600            us/op
// Test.keySet:keySet·p0.9999                    sample             8.558            us/op
// Test.keySet:keySet·p1.00                      sample           850.944            us/op
// Test.beanMapEntrySet                              ss       5    58.680 ± 49.284   us/op
// Test.beanMapKeySet                                ss       5    61.600 ± 30.464   us/op
// Test.entrySet                                     ss       5    15.440 ± 26.077   us/op
// Test.keySet                                       ss       5    23.000 ± 13.458   us/op
