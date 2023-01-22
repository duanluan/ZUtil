package top.csaf.jmh.base;

import com.alibaba.fastjson2.JSON;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.springframework.cglib.beans.BeanMap;
import top.csaf.junit.BeanUtilsTest;

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
public class ToMapTest {

  public static void main(String[] args) {
    // 结果是否相等
    ToMapTest test = new ToMapTest();
    System.out.println(test.putAll().equals(test.beanMapAndConstructor()) && test.beanMapAndConstructor().equals(test.beanMapAndForeach()));
    System.out.println(((List<BeanUtilsTest.TestBean>) (test.beanMapAndForeach().get("beanList"))).get(0).getName().equals(((List<Map<String, Object>>) (test.json().get("beanList"))).get(0).get("name")));
  }

  @Test
  public void benchmark() throws Exception {
    org.openjdk.jmh.Main.main(new String[]{ToMapTest.class.getName()});
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
  public Map<String, Object> beanMapAndConstructor() {
    return new HashMap<String, Object>(BeanMap.create(testBean));
  }

  @Benchmark
  public Map<String, Object> beanMapAndForeach() {
    Map<String, Object> map = new HashMap<>();
    BeanMap beanMap = BeanMap.create(testBean);
    for (Object key : beanMap.keySet()) {
      map.put(key.toString(), beanMap.get(key));
    }
    return map;
  }

  @Benchmark
  public Map<String, Object> json() {
    // 强转后深层仍是 JSONObject、JSONArray 类型，可以继续强转
    return (Map<String, Object>) JSON.toJSON(testBean);
  }
}

// Benchmark                                                        Mode     Cnt     Score     Error   Units
// ToMapTest.beanMapAndConstructor                                 thrpt       5     5.301 ±   0.067  ops/us
// ToMapTest.beanMapAndForeach                                     thrpt       5     8.175 ±   0.061  ops/us
// ToMapTest.json                                                  thrpt       5     3.229 ±   0.141  ops/us
// ToMapTest.putAll                                                thrpt       5     5.512 ±   0.173  ops/us
// ToMapTest.beanMapAndConstructor                                  avgt       5     0.190 ±   0.004   us/op
// ToMapTest.beanMapAndForeach                                      avgt       5     0.118 ±   0.003   us/op
// ToMapTest.json                                                   avgt       5     0.343 ±   0.099   us/op
// ToMapTest.putAll                                                 avgt       5     0.170 ±   0.002   us/op
// ToMapTest.beanMapAndConstructor                                sample  108620     0.222 ±   0.031   us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.00    sample             0.100             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.50    sample             0.200             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.90    sample             0.200             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.95    sample             0.300             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.99    sample             0.400             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.999   sample             4.636             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p0.9999  sample            30.090             us/op
// ToMapTest.beanMapAndConstructor:beanMapAndConstructor·p1.00    sample          1020.928             us/op
// ToMapTest.beanMapAndForeach                                    sample  162808     0.160 ±   0.025   us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.00            sample             0.100             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.50            sample             0.100             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.90            sample             0.200             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.95            sample             0.200             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.99            sample             0.300             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.999           sample             1.600             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p0.9999          sample            12.831             us/op
// ToMapTest.beanMapAndForeach:beanMapAndForeach·p1.00            sample          1212.416             us/op
// ToMapTest.json                                                 sample  120235     0.375 ±   0.042   us/op
// ToMapTest.json:json·p0.00                                      sample             0.300             us/op
// ToMapTest.json:json·p0.50                                      sample             0.300             us/op
// ToMapTest.json:json·p0.90                                      sample             0.400             us/op
// ToMapTest.json:json·p0.95                                      sample             0.400             us/op
// ToMapTest.json:json·p0.99                                      sample             0.800             us/op
// ToMapTest.json:json·p0.999                                     sample             5.375             us/op
// ToMapTest.json:json·p0.9999                                    sample            39.679             us/op
// ToMapTest.json:json·p1.00                                      sample          1513.472             us/op
// ToMapTest.putAll                                               sample  122079     0.229 ±   0.034   us/op
// ToMapTest.putAll:putAll·p0.00                                  sample             0.100             us/op
// ToMapTest.putAll:putAll·p0.50                                  sample             0.200             us/op
// ToMapTest.putAll:putAll·p0.90                                  sample             0.200             us/op
// ToMapTest.putAll:putAll·p0.95                                  sample             0.300             us/op
// ToMapTest.putAll:putAll·p0.99                                  sample             0.400             us/op
// ToMapTest.putAll:putAll·p0.999                                 sample             3.500             us/op
// ToMapTest.putAll:putAll·p0.9999                                sample            13.517             us/op
// ToMapTest.putAll:putAll·p1.00                                  sample          1060.864             us/op
// ToMapTest.beanMapAndConstructor                                    ss       5    47.960 ±   8.570   us/op
// ToMapTest.beanMapAndForeach                                        ss       5    37.300 ±  27.371   us/op
// ToMapTest.json                                                     ss       5   109.100 ± 540.645   us/op
// ToMapTest.putAll                                                   ss       5    42.520 ±  50.011   us/op
